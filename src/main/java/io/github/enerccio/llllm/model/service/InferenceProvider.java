package io.github.enerccio.llllm.model.service;

import com.openai.models.models.Model;
import io.github.enerccio.llllm.model.domain.AI;

import java.util.List;

public interface InferenceProvider {

    List<Model> getModels(AI ai) throws Exception;

}
