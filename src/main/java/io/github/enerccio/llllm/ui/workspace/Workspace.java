package io.github.enerccio.llllm.ui.workspace;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.ui.components.characters.CharactersComponent;
import io.github.enerccio.llllm.ui.components.chat.ChatComponent;
import io.github.enerccio.llllm.ui.components.lorebook.LorebookComponent;
import io.github.enerccio.llllm.ui.components.persona.PersonaComponent;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import io.github.enerccio.llllm.ui.widgets.HTabSheet;
import io.github.enerccio.llllm.ui.components.ai.AIComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.HashMap;
import java.util.Map;

@Configurable
public class Workspace extends Div {

    @Autowired
    private Localization loc;

    private HTabSheet tabs;

    private final ChatComponent chat = new ChatComponent();
    private final CharactersComponent characters = new CharactersComponent();
    private final PersonaComponent persona = new PersonaComponent();
    private final LorebookComponent lorebook = new LorebookComponent();
    private final AIComponent ai = new AIComponent();

    private final Map<Tab, WorkspaceComponent> tabToComponent = new HashMap<>();

    public void create() throws Exception {
        tabs = new HTabSheet();
        tabs.setSizeFull();
        add(tabs);

        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.CHAT.create(), new Span(loc.getValue(L.WORKSPACE_TABS_CHAT))), chat.create()), chat);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.USERS.create(), new Span(loc.getValue(L.WORKSPACE_TABS_CHARACTERS))), characters.create()), characters);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.USER.create(), new Span(loc.getValue(L.WORKSPACE_TABS_PERSONA))), persona.create()), persona);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.BOOK.create(), new Span(loc.getValue(L.WORKSPACE_TABS_LOREBOOK))), lorebook.create()), lorebook);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.AUTOMATION.create(), new Span(loc.getValue(L.WORKSPACE_TABS_AI))), ai.create()), ai);

        tabs.addSelectedChangeListener(e -> {
            if (e.getPreviousTab() != null) {
                WorkspaceComponent workspaceComponent = tabToComponent.get(e.getPreviousTab());
                try {
                    if (workspaceComponent != null)
                        workspaceComponent.onTabClosed();
                } catch (Exception ex) {
                    UIUtils.internalServerError(loc, ex);
                }
            }
            WorkspaceComponent workspaceComponent = tabToComponent.get(e.getSelectedTab());
            try {
                if (workspaceComponent != null)
                    workspaceComponent.onTabSwitched();
            } catch (Exception ex) {
                UIUtils.internalServerError(loc, ex);
            }
        });
    }


}
