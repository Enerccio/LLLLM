package io.github.enerccio.llllm.ui.forms.ai.protocol;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class ProtocolBaseFields {

    @Autowired
    private Localization loc;

    private TextField name;
    private IntegerField maxContext;
    private IntegerField maxTokens;
    private Checkbox temperatureEnabled;
    private NumberField temperature;
    private Checkbox topPEnabled;
    private NumberField topP;
    private Checkbox frequencyPenaltyEnabled;
    private NumberField frequencyPenalty;
    private Checkbox presencePenaltyEnabled;
    private NumberField presencePenalty;

    public VerticalLayout create() {
        VerticalLayout main = new VerticalLayout();
        main.setMargin(false);
        main.setPadding(false);
        main.setSizeFull();

        name = new TextField(loc.getValue(L.PROTOCOL_BASE_FIELDS_NAME));
        name.setWidth("100%");
        maxContext = new IntegerField(loc.getValue(L.PROTOCOL_BASE_FIELDS_MAX_CONTEXT));
        maxContext.setWidth("100%");
        maxContext.setMin(1);
        maxContext.setMax(Integer.MAX_VALUE);
        maxTokens = new IntegerField(loc.getValue(L.PROTOCOL_BASE_FIELDS_MAX_TOKENS));
        maxTokens.setMin(1);
        maxTokens.setWidth("100%");
        maxTokens.setMax(Integer.MAX_VALUE);

        HorizontalLayout tokens = new HorizontalLayout(maxContext, maxTokens);
        tokens.setWidth("100%");

        temperatureEnabled = new Checkbox(loc.getValue(L.PROTOCOL_BASE_FIELDS_ENABLED));
        temperature = new NumberField(loc.getValue(L.PROTOCOL_BASE_FIELDS_TEMPERATURE));
        temperature.setWidth("100%");
        temperature.setMin(0.0);
        temperature.setMax(2.0);
        HorizontalLayout tempLayout = new HorizontalLayout(temperatureEnabled, temperature);
        tempLayout.setWidth("100%");
        tempLayout.setAlignItems(Alignment.BASELINE);

        topPEnabled = new Checkbox(loc.getValue(L.PROTOCOL_BASE_FIELDS_ENABLED));
        topP = new NumberField(loc.getValue(L.PROTOCOL_BASE_FIELDS_TOP_P));
        topP.setWidth("100%");
        topP.setMin(0.0);
        topP.setMax(1.0);
        HorizontalLayout topPLayout = new HorizontalLayout(topPEnabled, topP);
        topPLayout.setWidth("100%");
        topPLayout.setAlignItems(VerticalLayout.Alignment.BASELINE);

        HorizontalLayout tempTop = new HorizontalLayout(tempLayout, topPLayout);
        tempTop.setWidth("100%");

        frequencyPenaltyEnabled = new Checkbox(loc.getValue(L.PROTOCOL_BASE_FIELDS_ENABLED));
        frequencyPenalty = new NumberField(loc.getValue(L.PROTOCOL_BASE_FIELDS_FREQ));
        frequencyPenalty.setWidth("100%");
        frequencyPenalty.setMin(-2.0);
        frequencyPenalty.setMax(2.0);
        HorizontalLayout freqLayout = new HorizontalLayout(frequencyPenaltyEnabled, frequencyPenalty);
        freqLayout.setWidth("100%");
        freqLayout.setAlignItems(VerticalLayout.Alignment.BASELINE);

        presencePenaltyEnabled = new Checkbox(loc.getValue(L.PROTOCOL_BASE_FIELDS_ENABLED));
        presencePenalty = new NumberField(loc.getValue(L.PROTOCOL_BASE_FIELDS_PRES));
        presencePenalty.setWidth("100%");
        presencePenalty.setMin(-2.0);
        presencePenalty.setMax(2.0);
        HorizontalLayout presLayout = new HorizontalLayout(presencePenaltyEnabled, presencePenalty);
        presLayout.setWidth("100%");
        presLayout.setAlignItems(VerticalLayout.Alignment.BASELINE);

        HorizontalLayout penalties = new HorizontalLayout(freqLayout, presLayout);
        penalties.setWidth("100%");

        main.add(name, tokens, tempTop, penalties);

        return main;
    }

    public void model2view(Protocol protocol) throws Exception {
        name.setValue(protocol.getName() == null ? "" : protocol.getName());
        maxTokens.setValue(protocol.getReplyTokens());
        maxContext.setValue(protocol.getMaxTokens());

        temperatureEnabled.setValue(protocol.getTemperatureEnabled() != null && protocol.getTemperatureEnabled());
        temperature.setValue(protocol.getTemperature());

        topPEnabled.setValue(protocol.getTopPEnabled() != null && protocol.getTopPEnabled());
        topP.setValue(protocol.getTopP());

        frequencyPenaltyEnabled.setValue(protocol.getFrequencyPenaltyEnabled() != null && protocol.getFrequencyPenaltyEnabled());
        frequencyPenalty.setValue(protocol.getFrequencyPenalty());

        presencePenaltyEnabled.setValue(protocol.getPresencePenaltyEnabled() != null && protocol.getPresencePenaltyEnabled());
        presencePenalty.setValue(protocol.getPresencePenalty());
    }

    public void view2model(Protocol protocol) throws Exception {
        protocol.setName(name.getValue());
        protocol.setReplyTokens(maxTokens.getValue() != null ? maxTokens.getValue() : 1);
        protocol.setMaxTokens(maxContext.getValue() != null ? maxContext.getValue() : 1);

        protocol.setTemperatureEnabled(temperatureEnabled.getValue());
        protocol.setTemperature(temperature.getValue());

        protocol.setTopPEnabled(topPEnabled.getValue());
        protocol.setTopP(topP.getValue());

        protocol.setFrequencyPenaltyEnabled(frequencyPenaltyEnabled.getValue());
        protocol.setFrequencyPenalty(frequencyPenalty.getValue());

        protocol.setPresencePenaltyEnabled(presencePenaltyEnabled.getValue());
        protocol.setPresencePenalty(presencePenalty.getValue());
    }

}
