package io.github.enerccio.llllm.session;

import io.github.enerccio.llllm.ui.workspace.Workspace;

public class SessionPoint {

    private volatile Workspace workspace = null;

    public synchronized Workspace getWorkspace() {
        if (workspace == null) {
            workspace = new Workspace();
        }
        return workspace;
    }

}
