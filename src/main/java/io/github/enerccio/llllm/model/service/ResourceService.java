package io.github.enerccio.llllm.model.service;

import io.github.enerccio.llllm.model.domain.Resource;
import io.github.enerccio.llllm.model.domain.User;

public interface ResourceService extends ExtendedContentService<Resource> {

    Resource upload(String filename, byte[] content, String contentType) throws Exception;
    Resource findByHash(String hash, User owner) throws Exception;
    byte[] getResourceData(Resource resource) throws Exception;

}
