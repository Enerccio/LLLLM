package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Location;
import io.github.enerccio.llllm.model.domain.Lorebook;
import io.github.enerccio.llllm.model.service.LocationService;
import io.github.enerccio.llllm.model.service.LorebookService;

public class LocationServiceImpl extends ExtendedContentServiceImpl<Location> implements LocationService {

    @Override
    protected Class<Location> getEntityClass() {
        return Location.class;
    }
    
}
