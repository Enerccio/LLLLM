package io.github.enerccio.llllm.synthesis.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.enerccio.llllm.model.domain.collections.Gender;
import io.github.enerccio.llllm.model.domain.collections.VoiceTone;
import io.github.enerccio.llllm.model.domain.collections.VoiceType;

import java.util.Map;
import java.util.TreeMap;

public class VoiceMatrix {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    
    // Maps a unique VoiceProfile to its concrete provider voice identifier string
    private final Map<VoiceProfile, String> mappings = new TreeMap<>();

    public VoiceMatrix() {}

    public void addMapping(VoiceProfile profile, String concreteId) {
        this.mappings.put(profile, concreteId);
    }

    public String getConcreteId(VoiceProfile profile) {
        return this.mappings.get(profile);
    }

    public Map<VoiceProfile, String> getMappings() {
        return mappings;
    }

    public static VoiceMatrix createEmptyMatrix() {
        VoiceMatrix matrix = new VoiceMatrix();
        for (VoiceType type : VoiceType.values()) {
            for (VoiceTone tone : VoiceTone.values()) {
                for (Gender gender : Gender.values()) {
                    matrix.addMapping(new VoiceProfile(type, tone, gender), "");
                }
            }
        }
        return matrix;
    }

    public static String serialize(VoiceMatrix voiceMatrix) {
        JsonObject serialized = new JsonObject();
        for (VoiceProfile profile : voiceMatrix.mappings.keySet()) {
            serialized.addProperty(profile.toProfileKey(), voiceMatrix.getConcreteId(profile));
        }
        return gson.toJson(serialized);
    }

    public static VoiceMatrix deserialize(String json) {
        JsonObject object = gson.fromJson(json, JsonObject.class);
        VoiceMatrix matrix = new VoiceMatrix();
        for (String key : object.keySet()) {
            VoiceProfile profile = VoiceProfile.fromProfileKey(key);
            matrix.addMapping(profile, object.get(key).getAsString());
        }
        return matrix;
    }
}