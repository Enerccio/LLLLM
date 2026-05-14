package io.github.enerccio.llllm.ui.workspace;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.ui.components.ai.AIComponent;
import io.github.enerccio.llllm.ui.components.characters.CharactersComponent;
import io.github.enerccio.llllm.ui.components.chat.ChatComponent;
import io.github.enerccio.llllm.ui.components.location.LocationComponent;
import io.github.enerccio.llllm.ui.components.lorebook.LorebookComponent;
import io.github.enerccio.llllm.ui.components.persona.PersonaComponent;
import io.github.enerccio.llllm.ui.components.settings.SettingsComponent;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import io.github.enerccio.llllm.ui.widgets.HTabSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.HashMap;
import java.util.Map;

@Configurable
public class Workspace {

    @Autowired
    private Localization loc;

    private final boolean readOnly;

    private HTabSheet tabs;

    private final ChatComponent chat = new ChatComponent();
    private final CharactersComponent characters = new CharactersComponent();
    private final PersonaComponent persona = new PersonaComponent();
    private final LorebookComponent lorebook = new LorebookComponent();
    private final LocationComponent location = new LocationComponent();
    private final AIComponent ai = new AIComponent();
    private final SettingsComponent settings = new SettingsComponent();

    private final Map<Tab, WorkspaceComponent> tabToComponent = new HashMap<>();

    public Workspace(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Component create() throws Exception {
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();

        tabs = new HTabSheet();
        tabs.setSizeFull();
        vl.add(tabs);

        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.CHAT.create(), new Span(loc.getValue(L.WORKSPACE_TABS_CHAT))), chat.create()), chat);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.USERS.create(), new Span(loc.getValue(L.WORKSPACE_TABS_CHARACTERS))), characters.create()), characters);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.USER.create(), new Span(loc.getValue(L.WORKSPACE_TABS_PERSONA))), persona.create()), persona);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.PICTURE.create(), new Span(loc.getValue(L.WORKSPACE_TABS_LOCATIONS))), location.create()), location);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.BOOK.create(), new Span(loc.getValue(L.WORKSPACE_TABS_LOREBOOK))), lorebook.create()), lorebook);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.AUTOMATION.create(), new Span(loc.getValue(L.WORKSPACE_TABS_AI))), ai.create()), ai);
        tabToComponent.put(tabs.add(new HorizontalLayout(VaadinIcon.COGS.create(), new Span(loc.getValue(L.WORKSPACE_TABS_SETTINGS))), settings.create()), settings);

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

        return vl;
    }

    public AIComponent getAIComponent() {
        return ai;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    private Component createMultiTabWarningView() {
        VerticalLayout warningLayout = new VerticalLayout();
        warningLayout.setSizeFull();
        warningLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        warningLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        warningLayout.getStyle().set("background-color", "var(--lumo-contrast-5pct)");

        H2 header = new H2("Workspace Session Already Open");
        Paragraph description = new Paragraph(
                "You are already accessing your language RPG workspace inside another browser tab. " +
                        "To protect your active chat sessions and database records from data corruption, " +
                        "this window has been switched to read-only mode."
        );
        description.getStyle().set("text-align", "center");
        description.getStyle().set("max-width", "500px");
        description.getStyle().set("color", "var(--lumo-secondary-text-color)");

        warningLayout.add(header, description);
        return warningLayout;
    }
}
