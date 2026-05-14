package io.github.enerccio.llllm.ui.forms.ai.ai.sections;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.AI;

public class JailbreakSection {

    private final Checkbox needsJailbreak;
    private final TextArea jailbreak;
    private final VerticalLayout layout;

    public JailbreakSection(Localization loc, String labelCheckbox, String labelText) {
        layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setMargin(false);

        needsJailbreak = new Checkbox(labelCheckbox);
        needsJailbreak.setWidth("100%");
        
        jailbreak = new TextArea(labelText);
        jailbreak.setMinRows(5);
        jailbreak.setWidth("100%");

        needsJailbreak.addValueChangeListener(e -> jailbreak.setVisible(e.getValue()));

        layout.add(needsJailbreak, jailbreak);
        jailbreak.setVisible(false);
    }

    public Component getLayout() { return layout; }

    public void model2view(AI entity) {
        needsJailbreak.setValue(entity.getNeedsJailbreak());
        jailbreak.setValue(entity.getJailbreak() == null ? "" : entity.getJailbreak());
        jailbreak.setVisible(entity.getNeedsJailbreak());
    }

    public void view2model(AI entity) {
        entity.setNeedsJailbreak(needsJailbreak.getValue());
        entity.setJailbreak(jailbreak.getValue());
    }
}
