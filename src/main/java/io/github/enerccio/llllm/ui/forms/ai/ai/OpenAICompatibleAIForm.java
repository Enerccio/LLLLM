package io.github.enerccio.llllm.ui.forms.ai.ai;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.openai.models.models.Model;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.factories.InferenceFactory;
import io.github.enerccio.llllm.model.service.AIService;
import io.github.enerccio.llllm.ui.forms.ai.ai.sections.JailbreakSection;
import io.github.enerccio.llllm.ui.forms.ai.ai.sections.ReasoningSection;
import io.github.enerccio.llllm.ui.forms.ai.ai.sections.VoiceIngestSection;
import io.github.enerccio.llllm.ui.forms.ai.ai.sections.VoiceOutputSection;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import io.github.enerccio.llllm.ui.widgets.FormBase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.firitin.layouts.VTabSheet;

import java.util.List;

@Configurable
public class OpenAICompatibleAIForm extends FormBase<AI, AIService> {

    @Autowired
    private InferenceFactory inferenceFactory;

    private TextField name;
    private TextField uri;
    private PasswordField apiKey;
    private ComboBox<Model> model;
    private TextArea additionalParams;
    private Checkbox dropSystemMessages;
    private Button save;

    // Decoupled Layout Modules
    private VoiceIngestSection voiceIngestSection;
    private ReasoningSection reasoningSection;
    private JailbreakSection jailbreakSection;
    private VoiceOutputSection voiceOutputSection;

    private boolean inRefresh = false;

    @Override
    protected void createContents() throws Exception {
        setHeaderTitle(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_MODEL_HEADER));
        setHeight("600px");
        setWidth("740px");

        voiceIngestSection = new VoiceIngestSection(loc);
        reasoningSection = new ReasoningSection(loc);
        jailbreakSection = new JailbreakSection(loc,
                loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_NEEDS_JAILBREAK),
                loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_JAILBREAK));
        voiceOutputSection = new VoiceOutputSection(loc);

        VTabSheet sheet = new VTabSheet();
        sheet.setSizeFull();
        add(sheet);

        sheet.add(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_TAB_MAIN), createMainTab());
        sheet.add("Reasoning & Safety", createReasoningTab());
        sheet.add("Voice Box Output", createVoiceOutputTab());
        sheet.add(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_TAB_ADVANCED), createAdvancedTab());

        Button exit = new Button(loc.getValue(L.BUTTON_EXIT), event -> handleFormClose());
        save = new Button(loc.getValue(L.BUTTON_SAVE), event -> handleFormSave());
        getFooter().add(new HorizontalLayout(exit, save));
    }

    private Component createMainTab() {
        VerticalLayout main = new VerticalLayout();
        main.setMargin(false);
        main.setPadding(false);
        main.setSizeFull();

        name = new TextField(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_NAME));
        name.setWidth("100%");

        uri = new TextField(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_URI));
        uri.setWidth("100%");
        uri.addValueChangeListener(event -> reloadModelList());
        uri.setValueChangeMode(ValueChangeMode.EAGER);

        apiKey = new PasswordField(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_API_KEY));
        apiKey.setWidth("100%");
        apiKey.addValueChangeListener(event -> reloadModelList());

        model = new ComboBox<>(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_MODEL));
        model.setItemLabelGenerator(Model::id);
        model.setWidth("100%");

        Button reloadModels = new Button(VaadinIcon.REFRESH.create(), event -> reloadModelList());
        HorizontalLayout modelsLayout = new HorizontalLayout(model, reloadModels);
        modelsLayout.setWidth("100%");
        modelsLayout.setAlignItems(HorizontalLayout.Alignment.BASELINE);

        additionalParams = new TextArea(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_ADDITIONAL_PARAMS));
        additionalParams.setMinRows(4);
        additionalParams.setWidth("100%");
        additionalParams.addValueChangeListener(event -> validateAdditionalParams());
        additionalParams.setValueChangeMode(ValueChangeMode.EAGER);

        HorizontalLayout uriApi = new HorizontalLayout(uri, apiKey);
        uriApi.setWidth("100%");

        main.add(name);
        main.add(uriApi);
        main.add(modelsLayout);
        main.add(voiceIngestSection.getLayout());
        main.add(additionalParams);

        return main;
    }

    private Component createReasoningTab() {
        VerticalLayout tab = new VerticalLayout();
        tab.setMargin(false);
        tab.setPadding(false);

        dropSystemMessages = new Checkbox("Scrub System Messages (Required for strict o1 models)");
        dropSystemMessages.setWidth("100%");

        tab.add(reasoningSection.getLayout(), dropSystemMessages);
        return tab;
    }

    private Component createAdvancedTab() {
        return jailbreakSection.getLayout();
    }

    private void validateAdditionalParams() {
        if (inRefresh) return;
        additionalParams.setErrorMessage(null);
        additionalParams.setInvalid(false);
        save.setEnabled(true);

        String params = additionalParams.getValue();
        if (StringUtils.isBlank(params)) return;

        try {
            new GsonBuilder().create().fromJson(params, JsonObject.class);
        } catch (Exception e) {
            additionalParams.setErrorMessage(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_ADDITIONAL_PARAMS_UNPARSEABLE));
            additionalParams.setInvalid(true);
            save.setEnabled(false);
        }
    }

    private void reloadModelList() {
        if (inRefresh) return;
        String uriVal = uri.getValue();
        String keyVal = apiKey.getValue();
        if (StringUtils.isBlank(keyVal)) {
            keyVal = entity.getOpenAICompatible().getApiKey();
        }

        if (StringUtils.isAnyBlank(uriVal, keyVal)) {
            model.setItems();
            return;
        }

        try {
            OpenAICompatible temporaryCopy = new OpenAICompatible();
            temporaryCopy.setUri(uriVal);
            temporaryCopy.setApiKey(keyVal);
            temporaryCopy.setAiType(AIType.OPEN_AI_COMPATIBLE);

            List<Model> models = inferenceFactory.getInferenceProvider(temporaryCopy).getModels(temporaryCopy);
            model.setItems(models);
        } catch (Exception e) {
            UIUtils.internalServerError(loc, e);
        }
    }

    @Override
    protected void refresh() throws Exception {
        inRefresh = true;
        try {
            apiKey.setValue("");
            OpenAICompatible openAICompatible = getModel().getOpenAICompatible();
            name.setValue(getModel().getName() == null ? "" : getModel().getName());
            uri.setValue(openAICompatible.getUri() == null ? "" : openAICompatible.getUri());
            additionalParams.setValue(openAICompatible.getAdditionalParameters() == null ? "" : openAICompatible.getAdditionalParameters());
            dropSystemMessages.setValue(getModel().getDropSystemMessages());

            // Execution: Transfer structural model data straight to view layers
            voiceIngestSection.model2view(getModel());
            reasoningSection.model2view(getModel());
            jailbreakSection.model2view(getModel());
            voiceOutputSection.model2view(getModel());

            inRefresh = false;
            reloadModelList();
        } catch (Exception e) {
            UIUtils.internalServerError(loc, e);
        } finally {
            inRefresh = false;
        }
    }

    private void handleFormSave() {
        try {
            this.entity = this.service.findById(entity.getId());
            this.entity.setName(name.getValue());

            OpenAICompatible openAICompatible = this.entity.getOpenAICompatible();
            openAICompatible.setUri(uri.getValue());
            if (StringUtils.isNotBlank(apiKey.getValue())) {
                openAICompatible.setApiKey(apiKey.getValue());
            }
            openAICompatible.setModel(model.getValue() == null ? null : model.getValue().id());
            openAICompatible.setAdditionalParameters(additionalParams.getValue());
            this.entity.setDropSystemMessages(dropSystemMessages.getValue());

            this.entity.setCapabilities(this.model.getValue() == null ? null :
                    new GsonBuilder().create().toJson(model.getValue()._additionalProperties()));

            // Execution: Pull active view states down into the database entity mapping layers
            voiceIngestSection.view2model(this.entity);
            reasoningSection.view2model(this.entity);
            jailbreakSection.view2model(this.entity);
            voiceOutputSection.view2model(this.entity);

            this.service.save(this.entity);
            handleFormClose();
        } catch (Exception e) {
            UIUtils.internalServerError(loc, e);
        }
    }

    private void handleFormClose() {
        close();
        try {
            sessionPoint.getWorkspace().getAIComponent().refresh();
        } catch (Exception e) {
            UIUtils.internalServerError(loc, e);
        }
    }

    private Component createVoiceOutputTab() {
        VerticalLayout tab = new VerticalLayout();
        tab.setMargin(false);
        tab.setPadding(false);
        tab.setSizeFull();

        // Inject your clean new voice output properties directly here
        tab.add(voiceOutputSection.getLayout());

        // Optional placeholder hook: You can add an extra button right here labeled
        // "Edit Character Trait Matrix" to pop open an admin grid sub-dialog
        // that edits voiceOutputSection.getWorkingVoiceMatrix()!

        return tab;
    }
}

