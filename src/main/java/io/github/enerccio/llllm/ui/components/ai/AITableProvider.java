package io.github.enerccio.llllm.ui.components.ai;

import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.service.AIService;
import io.github.enerccio.llllm.ui.widgets.BackendTableProviderBase;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class AITableProvider extends BackendTableProviderBase<AITableItem, AI, AIService> {

    @Override
    protected AITableItem entityToTableItem(AI entity) throws Exception {
        AITableItem item = new AITableItem(entity);
        item.setName(entity.getName());
        item.setType(loc.getValue(loc.getAIType(entity.getAiType())));
        return item;
    }
}
