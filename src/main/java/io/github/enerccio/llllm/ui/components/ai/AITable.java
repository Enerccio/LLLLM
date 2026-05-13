package io.github.enerccio.llllm.ui.components.ai;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.service.AIService;
import io.github.enerccio.llllm.ui.dialogs.ListSelectDialog;
import io.github.enerccio.llllm.ui.forms.ai.ai.OpenAICompatibleAIForm;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

@Configurable
public class AITable {

    @Autowired
    private Localization loc;

    @Autowired
    private AIService aiService;

    private Grid<AITableItem> aiGrid;
    private AITableProvider aiTableProvider;

    public Component create() throws Exception {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setPadding(false);
        buttons.setSpacing(false);
        layout.add(buttons);
        layout.setFlexShrink(0, buttons);

        Button newAI = new Button(VaadinIcon.PLUS.create());
        newAI.addClickListener(e -> {
            ListSelectDialog<AIType> dialog = new ListSelectDialog<>(loc.getValue(L.AITABLE_SELECT_AI_PROVIDER), loc.getValue(L.AITABLE_SELECT_AI_PROVIDER_LABEL), t -> loc.getValue(loc.getAIType(t)), type -> {
                try {
                    AI ai = aiService.create(type);
                    openAiDialog(ai);
                } catch (Exception ex) {
                    UIUtils.internalServerError(loc, ex);
                }
            });
            dialog.create();
            dialog.setItems(List.of(AIType.values()));
            dialog.open();
        });
        buttons.add(newAI);

        aiGrid = new Grid<>();
        aiGrid.setSizeFull();

        aiGrid.addColumn(AITableItem::getName).setHeader(loc.getValue(L.AITABLE_COLUMN_NAME));
        aiGrid.addColumn(AITableItem::getType).setHeader(loc.getValue(L.AITABLE_COLUMN_TYPE));

        layout.add(aiGrid);
        layout.expand(aiGrid);

        return layout;
    }

    private void openAiDialog(AI ai) throws Exception {
        switch (ai.getAiType()) {
            case OPEN_AI_COMPATIBLE:
                OpenAICompatibleAIForm dialog = new OpenAICompatibleAIForm();
                dialog.create();
                dialog.setModel(ai);
                dialog.open();
                break;
            default:
                throw new IllegalArgumentException("Unsupported AI type: " + ai.getAiType());
        }
    }

    public void refresh() throws Exception {
        aiTableProvider = new AITableProvider();
        aiTableProvider.setIds(aiService.findAll());
        aiGrid.setDataProvider(aiTableProvider);
    }

}
