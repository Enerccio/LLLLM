package io.github.enerccio.llllm.ui.forms.ai.ai.sections;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.collections.AudioFormat;
import io.github.enerccio.llllm.synthesis.domain.VoiceMatrix;

public class VoiceOutputSection {

    private final TextField audioEngine;
    private final TextField fallbackVoice;
    private final NumberField voiceSpeed;
    private final ComboBox<AudioFormat> audioOutputFormat;
    private final IntegerField samplingOutputRate;
    private final VerticalLayout layout;
    private final VoiceMatrixGridSection matrixGridSection;

    // In-memory cache reference to prevent continuous JSON re-serialization issues
    private VoiceMatrix workingVoiceMatrix;

    public VoiceOutputSection(Localization loc) {
        layout = new VerticalLayout();
        layout.setPadding(false);
        layout.setMargin(false);
        layout.setSizeFull();

        audioEngine = new TextField("Speech Synthesis Engine Model");
        audioEngine.setPlaceholder("e.g., tts-1-hd or eleven_multilingual_v3");
        audioEngine.setWidth("100%");

        fallbackVoice = new TextField("Global Catch-all Fallback Voice ID");
        fallbackVoice.setPlaceholder("e.g., alloy or Rachel");
        fallbackVoice.setWidth("100%");

        voiceSpeed = new NumberField("Voice Speed Multiplier");
        voiceSpeed.setMin(0.25);
        voiceSpeed.setMax(4.0);
        voiceSpeed.setStep(0.05);
        voiceSpeed.setWidth("100%");

        HorizontalLayout matrixControlsRow = new HorizontalLayout(audioEngine, fallbackVoice, voiceSpeed);
        matrixControlsRow.setWidth("100%");

        audioOutputFormat = new ComboBox<>("Output Audio Delivery Format");
        audioOutputFormat.setItems(AudioFormat.values());
        audioOutputFormat.setWidth("100%");

        samplingOutputRate = new IntegerField("Output Sampling Delivery Rate (Hz)");
        samplingOutputRate.setPlaceholder("e.g., 24000 or 44100");
        samplingOutputRate.setWidth("100%");

        HorizontalLayout formattingRow = new HorizontalLayout(audioOutputFormat, samplingOutputRate);
        formattingRow.setWidth("100%");

        matrixGridSection = new VoiceMatrixGridSection(loc);
        layout.add(matrixControlsRow, formattingRow, matrixGridSection.getLayout());
    }

    public Component getLayout() {
        return layout;
    }

    public void model2view(AI entity) {
        audioEngine.setValue(entity.getAudioEngine() == null ? "" : entity.getAudioEngine());
        fallbackVoice.setValue(entity.getFallbackVoice() == null ? "" : entity.getFallbackVoice());
        voiceSpeed.setValue(entity.getVoiceSpeed()); // Exposes safe 1.0 baseline fallback
        audioOutputFormat.setValue(entity.getAudioOutputFormat());
        samplingOutputRate.setValue(entity.getSamplingOutputRate());

        matrixGridSection.model2view(entity);
    }

    public void view2model(AI entity) {
        entity.setAudioEngine(audioEngine.getValue());
        entity.setFallbackVoice(fallbackVoice.getValue());
        entity.setVoiceSpeed(voiceSpeed.getValue());
        entity.setAudioOutputFormat(audioOutputFormat.getValue());
        entity.setSamplingOutputRate(samplingOutputRate.getValue());

        // Persist the full multi-combination tree back down through the entity string boundaries
        matrixGridSection.view2model(entity);
    }

    /**
     * Optional utility accessor so your sub-views or mapping buttons can easily read
     * or edit specific combinations inside your Vaadin edit dialog grids.
     */
    public VoiceMatrix getWorkingVoiceMatrix() {
        if (this.workingVoiceMatrix == null) {
            this.workingVoiceMatrix = VoiceMatrix.createEmptyMatrix();
        }
        return this.workingVoiceMatrix;
    }
}
