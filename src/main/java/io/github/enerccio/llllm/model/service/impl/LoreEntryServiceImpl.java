package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.LoreEntry;
import io.github.enerccio.llllm.model.domain.Lorebook;
import io.github.enerccio.llllm.model.service.LoreEntryService;
import io.github.enerccio.llllm.model.service.LorebookService;

public class LoreEntryServiceImpl extends ExtendedContentServiceImpl<LoreEntry> implements LoreEntryService {

    @Override
    protected Class<LoreEntry> getEntityClass() {
        return LoreEntry.class;
    }
    
}
