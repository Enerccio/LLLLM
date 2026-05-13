package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Language;
import io.github.enerccio.llllm.model.domain.Location;
import io.github.enerccio.llllm.model.service.LanguageService;
import io.github.enerccio.llllm.model.service.LocationService;

public class LanguageServiceImpl extends ExtendedContentServiceImpl<Language> implements LanguageService {

    @Override
    protected Class<Language> getEntityClass() {
        return Language.class;
    }
    
}
