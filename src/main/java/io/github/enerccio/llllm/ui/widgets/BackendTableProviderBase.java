package io.github.enerccio.llllm.ui.widgets;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import io.github.enerccio.llllm.loc.Localization;
import io.github.enerccio.llllm.model.domain.ExtendedContentEntity;
import io.github.enerccio.llllm.model.service.ExtendedContentService;
import io.github.enerccio.llllm.ui.utils.UIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Configurable
public abstract class BackendTableProviderBase<
        TI extends BackendTableItem,
        E extends ExtendedContentEntity,
        S extends ExtendedContentService<E>>
        extends AbstractBackEndDataProvider<TI, Void> implements BackendTableProvider<TI> {

    private final static Logger log = LoggerFactory.getLogger(BackendTableProviderBase.class);

    @Autowired
    protected S service;

    @Autowired
    protected Localization loc;

    /**
     * Returns an instance of extended BackendTableItem for concrete entity type
     * <p>
     * @param  entity an instance of <? extends BaseEntity<? extends Serializable>>
     * @return @notnull instance of <? extends BackendTableItem>
     */
    protected abstract TI entityToTableItem(E entity) throws Exception;

    protected final List<Long> ids = new ArrayList<>();

    protected final Set<Long> checked = new HashSet<>();

    public void setIds(List<Long> ids) {
        this.ids.clear();
        this.ids.addAll(ids);
        checked.clear();
    }

    public int size() {
        return ids.size();
    }

    @Override
    protected Stream<TI> fetchFromBackEnd(Query<TI, Void> query) {
        List<TI> items = new ArrayList<>();

        int to = Math.min(query.getOffset() + query.getLimit(), ids.size());

        for (int i = query.getOffset(); i < to; i++) {
            try {
                Long id = ids.get(i);
                E entity = service.findById(id);
                TI item = entityToTableItem(entity);

                items.add(item);
            }  catch (Exception e) {
                UIUtils.internalServerError(loc, e);
                return new ArrayList<TI>().stream();
            }
        }

        return items.stream();
    }

    @Override
    protected int sizeInBackEnd(Query<TI, Void> query) {
        return ids.size();
    }

    @Override
    public void setChecked(TI item, boolean value) {
        if (value)
            checked.add(item.getId());
        else
            checked.remove(item.getId());
    }

    @Override
    public void setChecked(Long id, boolean value) {
        if (ids.contains(id)) {
            if (value)
                checked.add(id);
            else
                checked.remove(id);
        }
    }

    @Override
    public void setChecked(boolean value) {
        if (value)
            checked.addAll(ids);
        else
            checked.clear();
    }

    @Override
    public boolean isChecked(TI item) {
        return checked.contains(item.getId());
    }

    @Override
    public List<Long> getCheckedIds() {
        return new ArrayList<>(checked);
    }

    @Override
    public void remove(Long id) {
        checked.remove(id);
        ids.remove(id);
    }

    public List<Long> getIds() {
        return ids;
    }
}
