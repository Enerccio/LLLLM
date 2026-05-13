package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Resource;
import io.github.enerccio.llllm.model.service.ResourceService;
import io.github.enerccio.llllm.model.tx.CommonTx;
import io.github.enerccio.llllm.model.tx.CommonTxReadOnly;
import io.github.enerccio.llllm.model.tx.NoTx;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class ResourceServiceImpl extends ExtendedContentServiceImpl<Resource> implements ResourceService {

    @Override
    protected Class<Resource> getEntityClass() {
        return Resource.class;
    }

    @Override
    @CommonTx
    public Resource upload(String filename, byte[] content, String contentType) throws Exception {
        Resource resource = new Resource();
        resource.setMimeType(contentType);
        resource.setOriginalName(filename);
        resource.setSize(content.length);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(content);
        resource.setHash(Base64.getEncoder().encodeToString(digest.digest()));

        Resource existing = findByHash(resource.getHash());
        if (existing != null) {
            byte[] existingContent = getResourceData(existing);
            if (existingContent.length == content.length && Arrays.equals(existingContent, content)) {
                resource.setPath(existing.getPath());
            }
        }

        if (resource.getPath() == null) {
            String extension = FilenameUtils.getExtension(filename);
            String name = UUID.randomUUID() + "." + extension;
            File storeFile;
            if (resource.getMimeType().startsWith("image/")) {
                storeFile = new File(configuration.getImagesFolder(currentUser), name);
            } else {
                storeFile = new File(configuration.getResourcesFolder(currentUser), name);
            }
            FileUtils.writeByteArrayToFile(storeFile, content);
            resource.setPath(name);
        }

        return save(resource);
    }

    @Override
    @CommonTxReadOnly
    public Resource findByHash(String hash) throws Exception {
        List<Resource> resources = getEntityManager()
                .createQuery("SELECT r FROM Resource r WHERE r.hash = :hash", Resource.class)
                .setParameter("hash", hash)
                .getResultList();
        return resources.isEmpty() ? null : resources.getFirst();
    }

    @Override
    @NoTx
    public byte[] getResourceData(Resource resource) throws Exception {
        File file;
        if (resource.getMimeType().startsWith("image/")) {
            file = new File(configuration.getImagesFolder(currentUser), resource.getPath());
        } else {
            file = new File(configuration.getResourcesFolder(currentUser), resource.getPath());
        }
        return FileUtils.readFileToByteArray(file);
    }
}
