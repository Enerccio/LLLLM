package io.github.enerccio.llllm.ui.components.ai;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.github.enerccio.llllm.loc.L;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.Protocol;
import io.github.enerccio.llllm.model.domain.collections.ProtocolType;
import io.github.enerccio.llllm.model.domain.protocol.ChatCompletion;
import io.github.enerccio.llllm.model.service.ProtocolService;
import io.github.enerccio.llllm.ui.dialogs.ListSelectDialog;
import io.github.enerccio.llllm.ui.forms.ai.protocol.ChatCompletionProtocolForm;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;

@Configurable
public class ProtocolTable {

    @Autowired
    private Localization loc;

    @Autowired
    private ProtocolService protocolService;

    private Grid<ProtocolTableItem> grid;
    private ProtocolProvider tableProvider;

    public Component create() throws Exception {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setPadding(false);
        buttons.setSpacing(false);
        layout.add(buttons);
        layout.setFlexShrink(0, buttons);

        Button newProtocol = new Button(VaadinIcon.PLUS.create());
        newProtocol.addClickListener(e -> {
            ListSelectDialog<ProtocolType> dialog = new ListSelectDialog<>(loc.getValue(L.PROTOCOL_SELECT_AI_PROVIDER), loc.getValue(L.PROTOCOL_SELECT_AI_PROVIDER_LABEL), t -> loc.getValue(loc.getProtocolType(t)), type -> {
                try {
                    Protocol protocol = protocolService.create(type);
                    openProtocolDialog(protocol);
                } catch (Exception ex) {
                    UIUtils.internalServerError(loc, ex);
                }
            });
            dialog.create();
            dialog.setItems(List.of(ProtocolType.values()));
            dialog.open();
        });
        buttons.add(newProtocol);

        grid = new Grid<>();
        grid.setSizeFull();

        grid.addColumn(ProtocolTableItem::getName).setHeader(loc.getValue(L.PROTOCOL_COLUMN_NAME));
        grid.addColumn(ProtocolTableItem::getType).setHeader(loc.getValue(L.PROTOCOL_COLUMN_TYPE));
        grid.addComponentColumn(item -> {
            Button edit = new Button(VaadinIcon.PENCIL.create());
            edit.addClickListener(event -> {
                try {
                    if (item != null) {
                        Protocol protocol = protocolService.findById(item.getId());
                        openProtocolDialog(protocol);
                    }
                } catch (Exception e) {
                    UIUtils.internalServerError(loc, e);
                }
            });
            return edit;
        }).setFlexGrow(0).setWidth("50px");

        layout.add(grid);
        layout.expand(grid);

        return layout;
    }

    private void openProtocolDialog(Protocol protocol) throws Exception {
        switch (protocol.getProtocolType()) {
            case CHAT_COMPLETION -> {
                ChatCompletionProtocolForm form = new ChatCompletionProtocolForm();
                form.create();
                form.setModel((ChatCompletion) protocol);
                form.open();
            }
            default -> throw new RuntimeException("Unknown protocol type " + protocol.getProtocolType());
        }
    }

    public void refresh() throws Exception {
        tableProvider = new ProtocolProvider();
        tableProvider.setIds(protocolService.findAll());
        grid.setDataProvider(tableProvider);
    }

}
