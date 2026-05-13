package io.github.enerccio.llllm.ui.components.lorebook;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import io.github.enerccio.llllm.ui.workspace.WorkspaceComponent;

public class LorebookComponent implements WorkspaceComponent {

    @Override
    public Component create() throws Exception {
        return new Div();
    }

    @Override
    public void refresh() throws Exception {

    }

    @Override
    public void onTabSwitched() throws Exception {

    }

    @Override
    public void onTabClosed() throws Exception {

    }
}
