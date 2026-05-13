package io.github.enerccio.llllm.ui.components.ai;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.github.enerccio.llllm.ui.workspace.WorkspaceComponent;

public class AIComponent implements WorkspaceComponent {

    private AITable table;

    @Override
    public Component create() throws Exception {
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        vl.setHeight("90vh");

        table = new AITable();
        Component tableComponent = table.create();
        vl.add(tableComponent);
        vl.expand(tableComponent);

        return vl;
    }

    @Override
    public void refresh() throws Exception {
        table.refresh();
    }

    @Override
    public void onTabSwitched() throws Exception {
        refresh();
    }

    @Override
    public void onTabClosed() throws Exception {

    }
}
