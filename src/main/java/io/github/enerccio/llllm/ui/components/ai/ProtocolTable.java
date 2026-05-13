package io.github.enerccio.llllm.ui.components.ai;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.service.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class ProtocolTable {

    @Autowired
    private Localization loc;

    @Autowired
    private ProtocolService protocolService;

    private Grid<ProtocolTableItem> grid;
    private ProtocolProvider tableProvider;

    public Component create() throws Exception {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        grid = new Grid<>();
        grid.setSizeFull();

        grid.addColumn(ProtocolTableItem::getName).setHeader(loc.getValue(L.PROTOCOL_COLUMN_NAME));
        grid.addColumn(ProtocolTableItem::getType).setHeader(loc.getValue(L.PROTOCOL_COLUMN_TYPE));

        layout.add(grid);

        return layout;
    }

    public void refresh() throws Exception {
        tableProvider = new ProtocolProvider();
        tableProvider.setIds(protocolService.findAll());
        grid.setDataProvider(tableProvider);
    }

}
