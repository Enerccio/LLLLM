package io.github.enerccio.llllm.ui.components.ai;

import io.github.enerccio.llllm.ui.widgets.BackendTableItem;

public class ProtocolTableItem extends BackendTableItem {

    private String name;
    private String type;

    public ProtocolTableItem(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
