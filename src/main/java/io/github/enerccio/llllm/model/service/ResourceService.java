package io.github.enerccio.llllm.model.service;

import io.github.enerccio.llllm.model.domain.Resource;

public interface ResourceService extends ExtendedContentService<Resource> {

    Resource upload(String filename, byte[] content, String contentType) throws Exception;
    Resource findByHash(String hash) throws Exception;
    byte[] getResourceData(Resource resource) throws Exception;

}
