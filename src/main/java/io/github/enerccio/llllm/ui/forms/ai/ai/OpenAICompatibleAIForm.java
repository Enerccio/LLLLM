package io.github.enerccio.llllm.ui.forms.ai.ai;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.openai.models.models.Model;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
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
import io.github.enerccio.llllm.model.service.AIService;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import io.github.enerccio.llllm.ui.widgets.FormBase;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class OpenAICompatibleAIForm extends FormBase<AI, AIService> {

    private TextField name;
    private TextField uri;
    private PasswordField apiKey;
    private ComboBox<Model> model;
    private TextArea additionalParams;
    private boolean inRefresh = false;
    private Button save;

    @Override
    protected void createContents() throws Exception {
        setHeaderTitle(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_MODEL_HEADER));
        setHeight("490px");
        setWidth("700px");

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
        layout = new HorizontalLayout(models, UIUtils.voidComponent());
        layout.setWidth("100%");
        main.add(layout);
        main.add(additionalParams);

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
        add(main);
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
        this.entity.getOpenAICompatible().setAdditionalParameters(additionalParams.getValue());

        this.service.save(this.entity);
    }
}
