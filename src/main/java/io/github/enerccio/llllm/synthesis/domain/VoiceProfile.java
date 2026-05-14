package io.github.enerccio.llllm.synthesis.domain;

import io.github.enerccio.llllm.model.domain.collections.Gender;
import io.github.enerccio.llllm.model.domain.collections.VoiceTone;
import io.github.enerccio.llllm.model.domain.collections.VoiceType;
import org.jspecify.annotations.NonNull;

import java.util.Objects;
import java.util.regex.Pattern;

public class VoiceProfile implements Comparable<VoiceProfile> {

    private final VoiceType type;
    private final VoiceTone tone;
    private final Gender gender;

    public VoiceProfile(VoiceType type, VoiceTone tone, Gender gender) {
        this.type = Objects.requireNonNull(type, "VoiceType cannot be null");
        this.tone = Objects.requireNonNull(tone, "VoiceTone cannot be null");
        this.gender = Objects.requireNonNull(gender, "Gender cannot be null");
    }

    public static VoiceProfile fromProfileKey(String key) {
        String[] parts = key.split(Pattern.quote("_"));
        return new VoiceProfile(VoiceType.valueOf(parts[0]), VoiceTone.valueOf(parts[1]), Gender.valueOf(parts[2]));
    }

    public VoiceType getType() { return type; }
    public VoiceTone getTone() { return tone; }
    public Gender getGender() { return gender; }

    public String toProfileKey() {
        return String.format("%s_%s_%s", type.name(), tone.name(), gender.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoiceProfile that = (VoiceProfile) o;
        return type == that.type && tone == that.tone && gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, tone, gender);
    }

    @Override
    public String toString() {
        return toProfileKey();
    }

    @Override
    public int compareTo(@NonNull VoiceProfile o) {
        return toProfileKey().compareTo(o.toProfileKey());
    }
}
