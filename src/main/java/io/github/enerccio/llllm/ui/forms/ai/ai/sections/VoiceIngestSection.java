package io.github.enerccio.llllm.ui.forms.ai.ai.sections;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.collections.AudioFormat;

public class VoiceIngestSection {

    private final IntegerField duration;
    private final ComboBox<AudioFormat> audioFormat;
    private final IntegerField samplingRate;
    private final HorizontalLayout layout;

    public VoiceIngestSection(Localization loc) {
        layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);

        duration = new IntegerField("Max Voice Clip Duration");
        duration.setSuffixComponent(new Span("sec."));
        duration.setMin(0);
        duration.setMax(300);
        duration.setWidth("100%");

        audioFormat = new ComboBox<>("Input Audio Format");
        audioFormat.setItems(AudioFormat.values());
        audioFormat.setWidth("100%");

        samplingRate = new IntegerField("Input Sampling Rate (Hz)");
        samplingRate.setWidth("100%");

        layout.add(duration, audioFormat, samplingRate);
    }

    public Component getLayout() { return layout; }

    public void model2view(AI entity) {
        duration.setValue(entity.getMaxVoiceClipDuration());
        audioFormat.setValue(entity.getAudioFormat());
        samplingRate.setValue(entity.getSamplingRate());
    }

    public void view2model(AI entity) {
        entity.setMaxVoiceClipDuration(duration.getValue());
        entity.setAudioFormat(audioFormat.getValue());
        entity.setSamplingRate(samplingRate.getValue());
    }
}