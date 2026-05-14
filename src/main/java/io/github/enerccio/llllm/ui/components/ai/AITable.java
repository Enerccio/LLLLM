package io.github.enerccio.llllm.ui.components.ai;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.service.AIService;
import io.github.enerccio.llllm.ui.dialogs.ConfirmDialog;
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

    private Grid<AITableItem> grid;
    private AITableProvider tableProvider;

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

        grid = new Grid<>();
        grid.setSizeFull();
        grid.setSelectionMode(SelectionMode.SINGLE);

        grid.addColumn(AITableItem::getName).setHeader(loc.getValue(L.AITABLE_COLUMN_NAME));
        grid.addColumn(AITableItem::getType).setHeader(loc.getValue(L.AITABLE_COLUMN_TYPE));
        grid.addComponentColumn(item -> {
            Button edit = new Button(VaadinIcon.PENCIL.create());
            edit.addClickListener(event -> {
                try {
                    if (item != null) {
                        AI ai = aiService.findById(item.getId());
                        openAiDialog(ai);
                    }
                } catch (Exception e) {
                    UIUtils.internalServerError(loc, e);
                }
            });
            return edit;
        }).setFlexGrow(0).setWidth("50px");
        grid.addComponentColumn(item -> {
            Button delete = new Button(VaadinIcon.ERASER.create());
            delete.addClickListener(event -> {
                try {
                    if (item != null) {
                        AI ai = aiService.findById(item.getId());
                        ConfirmDialog.show(loc.getValue(L.AI_DELETE_CONFIRM), () -> {
                            try {
                                aiService.softDelete(ai);
                                refresh();
                            } catch (Exception e) {
                                UIUtils.internalServerError(loc, e);
                            }
                        });
                    }
                } catch (Exception e) {
                    UIUtils.internalServerError(loc, e);
                }
            });
            return delete;
        }).setFlexGrow(0).setWidth("50px");

        layout.add(grid);
        layout.expand(grid);

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
        tableProvider = new AITableProvider();
        tableProvider.setIds(aiService.findAll());
        grid.setDataProvider(tableProvider);
    }

}
