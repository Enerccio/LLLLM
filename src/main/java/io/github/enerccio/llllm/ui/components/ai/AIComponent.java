package io.github.enerccio.llllm.ui.components.ai;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.github.enerccio.llllm.ui.workspace.WorkspaceComponent;

public class AIComponent implements WorkspaceComponent {

    private AITable aiTable;
    private ProtocolTable protocolTable;

    @Override
    public Component create() throws Exception {
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.setHeight("90vh");

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();

        aiTable = new AITable();
        Component tableComponent = aiTable.create();
        hl.add(tableComponent);

        protocolTable = new ProtocolTable();
        Component protocolTableComponent = protocolTable.create();
        hl.add(protocolTableComponent);

        vl.add(hl);
        vl.expand(hl);

        return vl;
    }

    @Override
    public void refresh() throws Exception {
        aiTable.refresh();
        protocolTable.refresh();
    }

    @Override
    public void onTabSwitched() throws Exception {
        refresh();
    }

    @Override
    public void onTabClosed() throws Exception {

    }
}
