package io.github.enerccio.llllm.ui.components.ai;

import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.service.ProtocolService;
import io.github.enerccio.llllm.ui.widgets.BackendTableProviderBase;

public class ProtocolProvider extends BackendTableProviderBase<ProtocolTableItem, Protocol, ProtocolService> {

    @Override
    protected ProtocolTableItem entityToTableItem(Protocol entity) throws Exception {
        ProtocolTableItem item = new ProtocolTableItem(entity.getId());
        item.setName(entity.getName());
        item.setType(loc.getValue(loc.getProtocolType(entity.getProtocolType())));
        return item;
    }
}
