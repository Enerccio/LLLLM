package io.github.enerccio.llllm.ui.forms.ai.ai.sections;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.collections.ReasoningEffort;

public class ReasoningSection {

    private final Checkbox enabledReasoning;
    private final ComboBox<ReasoningEffort> reasoningEffort;
    private final IntegerField maxCompletionTokens;
    private final VerticalLayout layout;

    public ReasoningSection(Localization loc) {
        layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setMargin(false);

        enabledReasoning = new Checkbox("Enable Reasoning Layer");
        enabledReasoning.setWidth("100%");

        reasoningEffort = new ComboBox<>("Reasoning Effort Budget");
        reasoningEffort.setItems(ReasoningEffort.values());
        reasoningEffort.setWidth("100%");

        maxCompletionTokens = new IntegerField("Max Completion Tokens");
        maxCompletionTokens.setWidth("100%");

        enabledReasoning.addValueChangeListener(e -> toggleVisibility(e.getValue()));

        layout.add(enabledReasoning, reasoningEffort, maxCompletionTokens);
        toggleVisibility(false);
    }

    private void toggleVisibility(boolean visible) {
        reasoningEffort.setVisible(visible);
        maxCompletionTokens.setVisible(visible);
    }

    public Component getLayout() { return layout; }

    public void model2view(AI entity) {
        enabledReasoning.setValue(entity.getEnabledReasoning());
        reasoningEffort.setValue(entity.getReasoningEffort());
        maxCompletionTokens.setValue(entity.getMaxCompletionTokens());
        toggleVisibility(entity.getEnabledReasoning());
    }

    public void view2model(AI entity) {
        entity.setEnabledReasoning(enabledReasoning.getValue());
        entity.setReasoningEffort(reasoningEffort.getValue());
        entity.setMaxCompletionTokens(maxCompletionTokens.getValue());
    }
}
