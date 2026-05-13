package io.github.enerccio.llllm.ui.components.ai;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

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

        aiGrid = new Grid<>();
        aiGrid.setSizeFull();

        aiGrid.addColumn(AITableItem::getName).setHeader(loc.getValue(L.AITABLE_COLUMN_NAME));
        aiGrid.addColumn(AITableItem::getType).setHeader(loc.getValue(L.AITABLE_COLUMN_TYPE));

        layout.add(aiGrid);

        return layout;
    }

    public void refresh() throws Exception {
        aiTableProvider = new AITableProvider();
        aiTableProvider.setIds(aiService.findAll());
        aiGrid.setDataProvider(aiTableProvider);
    }

}
