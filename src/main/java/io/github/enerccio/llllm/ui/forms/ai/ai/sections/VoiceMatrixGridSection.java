package io.github.enerccio.llllm.ui.forms.ai.ai.sections;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.AI;
import io.github.enerccio.llllm.synthesis.domain.VoiceMatrix;
import io.github.enerccio.llllm.synthesis.domain.VoiceProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VoiceMatrixGridSection {

    private final Grid<VoiceMatrixRowItem> grid;
    private final TextField searchField;
    private final VerticalLayout layout;
    
    private final List<VoiceMatrixRowItem> itemsList = new ArrayList<>();
    private final ListDataProvider<VoiceMatrixRowItem> dataProvider;

    public VoiceMatrixGridSection(Localization loc) {
        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(true);

        // --- 1. SEARCH FILTER FIELD ---
        searchField = new TextField();
        searchField.setPlaceholder("Filter voice profiles (e.g., CHILD, GRAVELY, MALE)...");
        searchField.setWidth("100%");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        searchField.setClearButtonVisible(true);
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> executeFilter(e.getValue()));

        // --- 2. GRID INSTANTIATION & DESIGN ---
        grid = new Grid<>();
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);

        // Render abstract Age Type cleanly using text transformations
        grid.addColumn(item -> item.getProfile().getType().name())
                .setHeader("Age Variant")
                .setAutoWidth(true)
                .setSortable(true);

        // Render abstract Tone Variant with custom bold typography weighting styling rules
        grid.addColumn(new ComponentRenderer<>(item -> {
            Span span = new Span(item.getProfile().getTone().name());
            span.getElement().getThemeList().add("badge contrast");
            return span;
        })).setHeader("Vocal Texture Tone").setAutoWidth(true).setSortable(true);

        // Render abstract Gender tracking properties
        grid.addColumn(item -> item.getProfile().getGender().name())
                .setHeader("Gender")
                .setAutoWidth(true)
                .setSortable(true);

        // CRITICAL INLINE COMPONENT RENDERER: Renders an active Text input box right inside the mapping row column!
        grid.addColumn(new ComponentRenderer<>(item -> {
            TextField input = new TextField();
            input.setWidthFull();
            input.setValue(item.getProviderVoiceId());
            input.setPlaceholder("Enter Provider Voice ID/Hash...");
            
            // Listen for immediate text modification keys typing changes to push values directly to DTO list records
            input.setValueChangeMode(ValueChangeMode.ON_BLUR);
            input.addValueChangeListener(event -> item.setProviderVoiceId(event.getValue()));
            
            return input;
        })).setHeader("Concrete Provider Voice Name / ID Hash").setAutoWidth(true);

        // Bind the list array reference pointer straight to Vaadin's in-memory data management systems
        dataProvider = new ListDataProvider<>(itemsList);
        grid.setDataProvider(dataProvider);

        layout.add(searchField, grid);
        layout.expand(grid);
    }

    public Component getLayout() {
        return layout;
    }

    private void executeFilter(String query) {
        if (query == null || query.isBlank()) {
            dataProvider.clearFilters();
        } else {
            String lowercaseQuery = query.trim().toLowerCase();
            dataProvider.setFilter(item -> item.getSearchableText().contains(lowercaseQuery));
        }
    }

    // --- STRUCTURAL DATA TRANSFORMATION ARCHITECTURE METHODS ---

    public void model2view(AI entity) {
        itemsList.clear();
        searchField.clear();
        dataProvider.clearFilters();

        // Fetch or create the matrix layer mapping configuration elements
        VoiceMatrix matrix = entity.getVoiceMatrix();
        if (matrix == null || matrix.getMappings().isEmpty()) {
            matrix = VoiceMatrix.createEmptyMatrix();
        }

        // Loop through the sorted TreeMap entries and flatten them into row containers safely
        for (Map.Entry<VoiceProfile, String> entry : matrix.getMappings().entrySet()) {
            itemsList.add(new VoiceMatrixRowItem(entry.getKey(), entry.getValue()));
        }

        // Inform the UI thread to re-draw the cells with fresh hydrated database strings
        dataProvider.refreshAll();
    }

    public void view2model(AI entity) {
        // Build a fresh empty matrix template instance container configuration block
        VoiceMatrix updatedMatrix = new VoiceMatrix();

        // Re-compile all grid rows back up into the matrix object format mapping structure blocks
        for (VoiceMatrixRowItem rowItem : itemsList) {
            String value = rowItem.getProviderVoiceId();
            
            // Optional optimization: Only store non-blank rows to keep final database column JSON payloads tiny
            if (value != null && !value.isBlank()) {
                updatedMatrix.addMapping(rowItem.getProfile(), value.trim());
            }
        }

        // Commit directly back to the target transient/serialization database columns of your AI Class
        entity.setVoiceMatrix(updatedMatrix);
    }
}