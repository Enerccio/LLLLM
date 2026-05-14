package io.github.enerccio.llllm.ui.forms.ai.ai.sections;

import io.github.enerccio.llllm.synthesis.domain.VoiceProfile;

class VoiceMatrixRowItem {
    private final VoiceProfile profile;
    private String providerVoiceId;

    public VoiceMatrixRowItem(VoiceProfile profile, String providerVoiceId) {
        this.profile = profile;
        this.providerVoiceId = providerVoiceId != null ? providerVoiceId : "";
    }

    public VoiceProfile getProfile() {
        return profile;
    }
    
    public String getProviderVoiceId() {
        return providerVoiceId;
    }

    public void setProviderVoiceId(String providerVoiceId) {
        this.providerVoiceId = providerVoiceId;
    }

    public String getSearchableText() {
        return (profile.toProfileKey() + " " + providerVoiceId).toLowerCase();
    }
}
