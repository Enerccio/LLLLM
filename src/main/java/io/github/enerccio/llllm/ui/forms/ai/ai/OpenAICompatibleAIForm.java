package io.github.enerccio.llllm.ui.forms.ai.ai;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.openai.models.models.Model;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.model.domain.ai.OpenAICompatible;
import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.service.AIService;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import io.github.enerccio.llllm.ui.widgets.FormBase;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.firitin.layouts.VTabSheet;

import java.util.List;

public class OpenAICompatibleAIForm extends FormBase<AI, AIService> {

    private TextField name;
    private TextField uri;
    private IntegerField duration;
    private PasswordField apiKey;
    private ComboBox<Model> model;
    private TextArea additionalParams;

    private Checkbox needsJailbreak;
    private TextArea jailbreak;

    private Button save;

    private boolean inRefresh = false;

    @Override
    protected void createContents() throws Exception {
        setHeaderTitle(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_MODEL_HEADER));
        setHeight("540px");
        setWidth("700px");

        VTabSheet sheet = new VTabSheet();
        sheet.setSizeFull();
        add(sheet);

        sheet.add(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_TAB_MAIN), createMainTab());
        sheet.add(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_TAB_ADVANCED), createAdvancedTab());

        Button exit = new Button(loc.getValue(L.BUTTON_EXIT));
        exit.addClickListener(event -> {
            close();
            try {
                sessionPoint.getWorkspace().getAIComponent().refresh();
            } catch (Exception e) {
                UIUtils.internalServerError(loc, e);
            }
        });

        save = new Button(loc.getValue(L.BUTTON_SAVE));
        save.addClickListener(event -> {
            try {
                save();
                close();
                sessionPoint.getWorkspace().getAIComponent().refresh();
            } catch (Exception e) {
                UIUtils.internalServerError(loc, e);
            }
        });

        HorizontalLayout buttons = new HorizontalLayout(exit, save);
        getFooter().add(buttons);
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
        duration = new IntegerField(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_DURATION));
        duration.setWidth("100%");
        duration.setSuffixComponent(new Span("sec."));
        duration.setMin(0);
        duration.setMax(300);
        duration.setValue(20);
        Button reloadModels = new Button(VaadinIcon.REFRESH.create());
        reloadModels.addClickListener(event -> reloadModelList());
        additionalParams = new TextArea(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_ADDITIONAL_PARAMS));
        additionalParams.setMinRows(6);
        additionalParams.setWidth("100%");
        additionalParams.addValueChangeListener(event -> validateAdditionalParams());
        additionalParams.setValueChangeMode(ValueChangeMode.EAGER);

        main.add(name);
        HorizontalLayout layout = new HorizontalLayout(uri, apiKey);
        layout.setWidth("100%");
        main.add(layout);
        HorizontalLayout models = new HorizontalLayout(model, reloadModels);
        models.setWidth("100%");
        models.setAlignItems(Alignment.BASELINE);
        layout = new HorizontalLayout(models, duration);
        layout.setWidth("100%");
        main.add(layout);
        main.add(additionalParams);
        return main;
    }

    private Component createAdvancedTab() {
        VerticalLayout main = new VerticalLayout();
        main.setMargin(false);
        main.setPadding(false);
        main.setSizeFull();

        needsJailbreak = new Checkbox(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_NEEDS_JAILBREAK));
        needsJailbreak.setWidth("100%");
        jailbreak = new TextArea(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_JAILBREAK));
        jailbreak.setMinRows(6);
        jailbreak.setWidth("100%");

        main.add(needsJailbreak);
        main.add(jailbreak);
        return main;
    }

    private void validateAdditionalParams() {
        if (inRefresh)
            return;

        this.additionalParams.setErrorMessage(null);
        this.additionalParams.setInvalid(false);
        save.setEnabled(true);

        String additionalParams = this.additionalParams.getValue();
        if (StringUtils.isBlank(additionalParams)) {
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            gson.fromJson(additionalParams, JsonObject.class);
        } catch (Exception e) {
            // failed to parse
            this.additionalParams.setErrorMessage(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_ADDITIONAL_PARAMS_UNPARSEABLE));
            this.additionalParams.setInvalid(true);
            save.setEnabled(false);
        }
    }

    private void reloadModelList() {
        if (inRefresh)
            return;

        String uri = this.uri.getValue();
        String apiKey = this.apiKey.getValue();
        if (StringUtils.isBlank(apiKey))
            apiKey = this.entity.getOpenAICompatible().getApiKey();

        if (StringUtils.isBlank(uri) || StringUtils.isBlank(apiKey)) {
            model.setItems();
            return;
        }

        try {
            AI temporaryCopy = new AI();
            OpenAICompatible openAICompatible = new OpenAICompatible();
            temporaryCopy.setOpenAICompatible(openAICompatible);
            openAICompatible.setUri(uri);
            openAICompatible.setApiKey(apiKey);
            temporaryCopy.setAiType(AIType.OPEN_AI_COMPATIBLE);

            List<Model> models = service.getInferenceProvider(temporaryCopy).getModels(temporaryCopy);
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
            additionalParams.setValue(getModel().getOpenAICompatible().getAdditionalParameters() == null ? "" : getModel().getOpenAICompatible().getAdditionalParameters());
            duration.setValue(getModel().getMaxDuration());
            needsJailbreak.setValue(getModel().getNeedsJailbreak());
            jailbreak.setValue(getModel().getJailbreak() == null ? "" : getModel().getJailbreak());
            inRefresh = false;
            reloadModelList();
        } catch (Exception e) {
            UIUtils.internalServerError(loc, e);
        } finally {
            inRefresh = false;
        }
    }

    protected void save() throws Exception {
        this.entity = this.service.findById(entity.getId());

        this.entity.setName(name.getValue());
        this.entity.getOpenAICompatible().setUri(uri.getValue());
        if (StringUtils.isNotBlank(apiKey.getValue()))
            this.entity.getOpenAICompatible().setApiKey(apiKey.getValue());
        this.entity.getOpenAICompatible().setModel(model.getValue() == null ? null : model.getValue().id());
        this.entity.setCapabilities(this.model.getValue() == null ? null : new GsonBuilder().create().toJson(model.getValue()._additionalProperties()));
        this.entity.getOpenAICompatible().setAdditionalParameters(additionalParams.getValue());
        this.entity.setMaxDuration(duration.getValue());
        this.entity.setJailbreak(jailbreak.getValue());
        this.entity.setNeedsJailbreak(needsJailbreak.getValue());

        this.service.save(this.entity);
    }
}
