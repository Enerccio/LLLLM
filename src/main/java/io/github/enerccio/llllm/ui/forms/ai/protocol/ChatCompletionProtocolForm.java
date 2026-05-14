package io.github.enerccio.llllm.ui.forms.ai.protocol;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.model.domain.protocol.ChatCompletion;
import io.github.enerccio.llllm.model.service.ChatCompletionService;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import io.github.enerccio.llllm.ui.widgets.FormBase;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.firitin.layouts.VTabSheet;

@Configurable
public class ChatCompletionProtocolForm extends FormBase<ChatCompletion, ChatCompletionService> {

    private final ProtocolBaseFields protocolBaseFields = new ProtocolBaseFields();

    @Override
    protected void createContents() throws Exception {
        setHeaderTitle(loc.getValue(L.CHAT_COMPLETION_FORM_MODEL_HEADER));
        setHeight("540px");
        setWidth("700px");

        VTabSheet sheet = new VTabSheet();
        sheet.setSizeFull();
        add(sheet);

        sheet.add(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_TAB_MAIN), protocolBaseFields.create());
        sheet.add(loc.getValue(L.OPEN_AI_COMPATIBLE_FORM_TAB_ADVANCED), new Div());

        Button exit = new Button(loc.getValue(L.BUTTON_EXIT));
        exit.addClickListener(event -> {
            close();
            try {
                sessionPoint.getWorkspace().getAIComponent().refresh();
            } catch (Exception e) {
                UIUtils.internalServerError(loc, e);
            }
        });

        Button save = new Button(loc.getValue(L.BUTTON_SAVE));
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

    private void save() throws Exception {
        if (entity.getId() != null)
            entity = service.findById(entity.getId());

        protocolBaseFields.view2model(entity);
        service.save(entity);
    }

    @Override
    protected void refresh() throws Exception {
        protocolBaseFields.model2view(this.entity);
    }

}
