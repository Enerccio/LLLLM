package io.github.enerccio.llllm.bound;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.User;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import io.github.enerccio.llllm.ui.workspace.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SessionPoint {
    private static final Logger log = LoggerFactory.getLogger(SessionPoint.class);

    @Autowired
    private Localization loc;

    @Autowired
    private User user;

    // Maps: Browser Tab UI ID -> Isolated Workspace Instance
    private final Map<Integer, Workspace> tabWorkspaces = new ConcurrentHashMap<>();

    // Tracks: User ID -> The specific Tab UI ID currently owning the primary write-lock
    private final Map<Long, Integer> activeUserLocks = new ConcurrentHashMap<>();

    // Tracks: User ID -> List of pending tab layout wrappers waiting for promotion
    private final Map<Long, CopyOnWriteArrayList<TabUiContext>> pendingTabs = new ConcurrentHashMap<>();

    public synchronized Workspace getWorkspace() {
        return getWorkspace(user.getId());
    }

    public synchronized Workspace getWorkspace(Long userId) {
        final int currentUiId = UI.getCurrent().getUIId();

        return tabWorkspaces.computeIfAbsent(currentUiId, uiId -> {
            Integer activeUiId = activeUserLocks.get(userId);
            boolean isReadOnly = (activeUiId != null && activeUiId != currentUiId);

            Workspace workspace = new Workspace(isReadOnly);

            if (isReadOnly) {
                // TAB B: Open as read-only, track it in the pending queue matrix
                pendingTabs.computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>())
                        .add(new TabUiContext(currentUiId, UI.getCurrent(), workspace, loc));
            } else {
                // TAB A: Primary lock owner
                activeUserLocks.put(userId, currentUiId);
                registerLeaderDetachListener(userId, currentUiId);
            }

            return workspace;
        });
    }

    /**
     * Call this inside your Main route right after adding workspace.create()
     * to register the root layout container panel boundary.
     */
    public synchronized void registerRootContainer(Long userId, HasComponents container) {
        final int currentUiId = UI.getCurrent().getUIId();

        // Find this specific tab context in the pending queue and link its container layout
        CopyOnWriteArrayList<TabUiContext> standbys = pendingTabs.get(userId);
        if (standbys != null) {
            for (TabUiContext standby : standbys) {
                if (standby.uiId() == currentUiId) {
                    standby.setRootLayoutContainer(container);
                    break;
                }
            }
        }
    }

    private void registerLeaderDetachListener(Long userId, int leaderUiId) {
        UI.getCurrent().addDetachListener(event -> {
            synchronized (this) {
                Integer currentLock = activeUserLocks.get(userId);
                if (currentLock != null && currentLock == leaderUiId) {
                    // 1. Release the old primary lock references
                    activeUserLocks.remove(userId);
                    tabWorkspaces.remove(leaderUiId);

                    // 2. Trigger the takeover for the next oldest pending tab
                    CopyOnWriteArrayList<TabUiContext> standbys = pendingTabs.get(userId);
                    if (standbys != null && !standbys.isEmpty()) {
                        TabUiContext nextLeader = standbys.remove(0);

                        // Promote to new primary write-lock owner
                        activeUserLocks.put(userId, nextLeader.uiId());

                        // 3. EXECUTE REFRESH: Promote and push view swap directly to the screen thread
                        nextLeader.promoteToPrimary(userId, this);
                    }
                }
            }
        });
    }

    // --- SECURE CONTEXT CONTAINER CLASS ---
    private static class TabUiContext {
        private final int uiId;
        private final UI uiContext;
        private Workspace workspace;
        private final Localization loc;
        private HasComponents rootLayoutContainer;

        public TabUiContext(int uiId, UI uiContext, Workspace workspace, Localization loc) {
            this.uiId = uiId;
            this.uiContext = uiContext;
            this.workspace = workspace;
            this.loc = loc;
        }

        public int uiId() { return uiId; }
        public UI uiContext() { return uiContext; }
        public Workspace workspace() { return workspace; }
        public void setRootLayoutContainer(HasComponents container) { this.rootLayoutContainer = container; }

        public void promoteToPrimary(Long userId, SessionPoint sessionPoint) {
            this.uiContext.access(() -> {
                if (this.rootLayoutContainer != null) {
                    // ZERO BOILERPLATE SWAP: Wipe old read-only layout and rebuild from scratch
                    this.rootLayoutContainer.removeAll();

                    // Instantiate a fresh write-authorized Workspace
                    this.workspace = new Workspace(false);
                    sessionPoint.tabWorkspaces.put(this.uiId, this.workspace);

                    try {
                        this.rootLayoutContainer.add(this.workspace.create());

                        // Re-register the leader detach listener on this new active tab view context
                        sessionPoint.registerLeaderDetachListener(userId, this.uiId);
                    } catch (Exception e) {
                        UIUtils.internalServerError(loc, e);
                    }
                }
            });
        }
    }
}