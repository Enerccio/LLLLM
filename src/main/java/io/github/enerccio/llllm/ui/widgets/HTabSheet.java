package io.github.enerccio.llllm.ui.widgets;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.shared.HasPrefix;
import com.vaadin.flow.component.shared.HasSuffix;
import com.vaadin.flow.component.shared.HasThemeVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.DomListenerRegistration;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.shared.Registration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class HTabSheet extends HorizontalLayout implements HasPrefix, HasStyle, HasSize,
        HasSuffix, HasThemeVariant<TabSheetVariant> {

    private DomListenerRegistration scrollreg;
    private final Tabs tabs = new Tabs();
    private final HorizontalLayout content = new HorizontalLayout();

    private final Map<Tab, Component> tabToContent = new HashMap<>();

    public HTabSheet() {
        tabs.setOrientation(Orientation.VERTICAL);
        tabs.setHeight("calc(100% - 0.5rem)");
        content.setSizeFull();
        super.add(tabs, content);
        setFlexGrow(1, content);

        addSelectedChangeListener(e -> {
            getElement().setProperty("selected", tabs.getSelectedIndex());
            updateContent();
        });
    }

    public void setTabsWidth(String width) {
        tabs.setWidth(width);
    }

    /**
     * Adds a tab created from the given text and content.
     *
     * @param tabText the text of the tab
     * @param content the content related to the tab
     * @return the created tab
     */
    public Tab add(String tabText, Component content) {
        return add(new Tab(tabText), content);
    }

    /**
     * Adds a tab created from the given tab content and content.
     *
     * @param tabContent the content of the tab
     * @param content    the content related to the tab
     * @return the created tab
     */
    public Tab add(Component tabContent, Component content) {
        return add(new Tab(tabContent), content);
    }

    /**
     * Adds a tab with the given content.
     *
     * @param tab     the tab
     * @param content the content related to the tab
     * @return the added tab
     */
    public Tab add(Tab tab, Component content) {
        return add(tab, content, -1);
    }

    /**
     * Adds a tab with the given content to the given position.
     *
     * @param tab      the tab
     * @param content  the content related to the tab
     * @param position the position where the tab should be added. If negative, the
     *                 tab is added at the end.
     * @return the added tab
     */
    public Tab add(Tab tab, Component content, int position) {
        Objects.requireNonNull(tab, "The tab to be added cannot be null");
        Objects.requireNonNull(content,
                "The content to be added cannot be null");

        if (content instanceof Text) {
            throw new IllegalArgumentException(
                    "Text as content is not supported. Consider wrapping the Text inside a Div.");
        }

        if (position < 0) {
            tabs.add(tab);
        } else {
            tabs.addTabAtIndex(position, tab);
        }

        // Make sure possible old content related to the same tab gets removed
        if (tabToContent.containsKey(tab)) {
            tabToContent.get(tab).removeFromParent();
        }

        linkTabToContent(tab, content);

        tabToContent.put(tab, content);

        updateContent();

        return tab;
    }

    private void linkTabToContent(Tab tab, Component content) {
        runBeforeClientResponse(ui -> {
            // On the client, content is associated with a tab by id
            var tabId = tab.getId().orElse("tabsheet-tab-" + UUID.randomUUID());
            tab.setId(tabId);
            content.getElement().setAttribute("tab", tabId);
        });
    }

    private void runBeforeClientResponse(SerializableConsumer<UI> command) {
        getElement().getNode().runWhenAttached(ui -> ui
                .beforeClientResponse(this, context -> command.accept(ui)));
    }

    /**
     * Removes a tab.
     *
     * @param tab the non-null tab to be removed
     */
    public void remove(Tab tab) {
        Objects.requireNonNull(tab, "The tab to be removed cannot be null");
        var content = tabToContent.remove(tab);
        content.removeFromParent();
        tabs.remove(tab);
    }

    /**
     * Removes a tab based on the content
     *
     * @param content the non-null content related to the tab to be removed
     */
    public void remove(Component content) {
        Objects.requireNonNull(content,
                "The content of the tab to be removed cannot be null");

        if (content instanceof Text) {
            throw new IllegalArgumentException(
                    "Text as content is not supported.");
        }

        var tab = getTab(content);

        if (tab != null) {
            remove(tab);
        }
    }

    /**
     * Removes the tab at the given position.
     *
     * @param position the position of the tab to be removed
     */
    public void remove(int position) {
        remove(getTabAt(position));
    }

    /**
     * Gets the zero-based index of the currently selected tab.
     *
     * @return the zero-based index of the selected tab, or -1 if none of the
     * tabs is selected
     */
    public int getSelectedIndex() {
        return tabs.getSelectedIndex();
    }

    /**
     * Selects a tab based on its zero-based index.
     *
     * @param selectedIndex the zero-based index of the selected tab, -1 to unselect all
     */
    public void setSelectedIndex(int selectedIndex) {
        tabs.setSelectedIndex(selectedIndex);
    }

    /**
     * Gets the currently selected tab.
     *
     * @return the selected tab, or {@code null} if none is selected
     */
    public Tab getSelectedTab() {
        return tabs.getSelectedTab();
    }

    /**
     * Selects the given tab.
     *
     * @param selectedTab the tab to select, {@code null} to unselect all
     * @throws IllegalArgumentException if {@code selectedTab} is not a child of this component
     */
    public void setSelectedTab(Tab selectedTab) {
        tabs.setSelectedTab(selectedTab);
    }

    /**
     * Gets the number of tabs.
     *
     * @return the number of tabs
     */
    public int getTabCount() {
        return tabs.getTabCount();
    }

    /**
     * Returns the tab at the given position.
     *
     * @param position the position of the tab, must be greater than or equals to 0
     *                 and less than the number of tabs
     * @return The tab at the given index
     * @throws IllegalArgumentException if the index is less than 0 or greater than or equals to the
     *                                  number of tabs
     */
    public Tab getTabAt(int position) {
        return tabs.getTabAt(position);
    }

    /**
     * Returns the index of the given tab.
     *
     * @param tab the tab to look up, can not be <code>null</code>
     * @return the index of the tab or -1 if the tab is not added
     */
    public int getIndexOf(Tab tab) {
        return tabs.indexOf(tab);
    }

    /**
     * Returns the {@link Tab} associated with the given component.
     *
     * @param content the component to look up, can not be <code>null</code>
     * @return The tab instance associated with the given component, or
     * <code>null</code> if the {@link TabSheet} does not contain the
     * component.
     */
    public Tab getTab(Component content) {
        Objects.requireNonNull(content,
                "The component to look for the tab cannot be null");

        return tabToContent.entrySet().stream()
                .filter(entry -> entry.getValue().equals(content))
                .map(Map.Entry::getKey).findFirst().orElse(null);
    }

    /**
     * Returns the {@link Component} instance associated with the given tab.
     *
     * @param tab the tab to look up, can not be <code>null</code>
     * @return The component instance associated with the given tab, or
     * <code>null</code> if the {@link TabSheet} does not contain the
     * tab.
     */
    public Component getComponent(Tab tab) {
        Objects.requireNonNull(tab,
                "The tab to look for the component cannot be null");

        var tabContent = tabToContent.get(tab);
        if (tabContent == null) {
            return null;
        }
        return tabContent;
    }

    /**
     * Adds a listener for {@link TabSheet.SelectedChangeEvent}.
     *
     * @param listener the listener to add, not <code>null</code>
     * @return a handle that can be used for removing the listener
     */
    public Registration addSelectedChangeListener(
            ComponentEventListener<SelectedChangeEvent> listener) {

        return tabs.addSelectedChangeListener(event -> {
            listener.onComponentEvent(new SelectedChangeEvent(HTabSheet.this,
                    event.getPreviousTab(), event.isFromClient(),
                    event.isInitialSelection()) {
                @Override
                public void unregisterListener() {
                    event.unregisterListener();
                }
            });
        });

    }

    /**
     * Marks the content related to the selected tab as enabled and adds it to
     * the component if it is not already added. All the other content panels
     * are disabled so they can't be interacted with.
     */
    private void updateContent() {
        for (Map.Entry<Tab, Component> entry : tabToContent.entrySet()) {
            var tab = entry.getKey();
            var content = entry.getValue();

            if (tab.equals(tabs.getSelectedTab())) {
                if (content.getParent().isEmpty()) {
                    this.content.removeAll();
                    this.content.add(content);
                }
                content.getElement().setEnabled(true);
            } else {
                // Can't use setEnabled(false) because it would also mark the
                // elements as disabled in the DOM. Navigating between tabs
                // would then briefly show the content as disabled.
                content.getElement().getNode().setEnabled(false);
            }
        }
    }

    public Tab addTab(String caption, Component component) {
        return add(caption, component);
    }

    public void removeTab(Tab tab) {
        remove(tab);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getElement().executeJs(
                """
                         var self = this;
                         var el = this.querySelector("vaadin-horizontal-layout");
                            el.addEventListener("scroll", function(e) {
                                if(el.scrollTop + el.clientHeight === el.scrollHeight) {
                                    self.$server.onScrollToEnd();
                                }
                            });
                        """
        );
    }

    @ClientCallable
    private void onScrollToEnd() {
        getEventBus().fireEvent(new ScrollToEndEvent(this));
    }

    /**
     * Adds a listener that is called when a users scrolls the component to the
     * end of its scrollable area.
     *
     * @param listener the listener
     * @return the {@link Registration} you can use to remove this listener.
     */
    public Registration addScrollToEndListener(ComponentEventListener<ScrollToEndEvent> listener) {
        return addListener(ScrollToEndEvent.class, listener);
    }

    public Registration addScrollListener(ComponentEventListener<ScrollEvent> listener) {
        if (scrollreg == null) {
            getElement().executeJs("""
                    var el = this;
                    this.querySelector("vaadin-horizontal-layout").addEventListener('scroll', e => {
                        const event = new CustomEvent('myscroll', { detail: e.target.scrollTop + ',' + e.target.scrollLeft });
                        el.dispatchEvent(event);
                    })""");
            scrollreg = getElement().addEventListener("myscroll", (DomEvent de) -> {
                getEventBus().fireEvent(new ScrollEvent(
                        this,
                        (String) de.getEventData().getString("event.detail")
                ));
            });
            scrollreg.debounce(100); // use reasonable debouncing
            scrollreg.addEventData("event.detail");
        }
        return addListener(ScrollEvent.class, listener);
    }

    public void scrollToTop() {
        setScrollTop(0);
    }

    public void scrollToBottom() {
        getElement().executeJs("this.scrollTop = this.scrollHeight");
    }

    public void setScrollTop(int pixelsFromTop) {
        getElement().executeJs("this.scrollTop = $0", pixelsFromTop);
    }

    public void setScrollLeft(int pixelsFromLeft) {
        getElement().executeJs("this.scrollLeft = $0", pixelsFromLeft);
    }

    public void scrollIntoView(Component c) {
        c.scrollIntoView();
    }


    /**
     * An event to mark that the selected tab has changed.
     */
    public static class SelectedChangeEvent extends ComponentEvent<HTabSheet> {
        private final Tab selectedTab;
        private final Tab previousTab;
        private final boolean initialSelection;

        /**
         * Creates a new selected change event.
         *
         * @param source      The TabSheet that fired the event.
         * @param previousTab The previous selected tab.
         * @param fromClient  <code>true</code> for client-side events,
         *                    <code>false</code> otherwise.
         */
        public SelectedChangeEvent(HTabSheet source, Tab previousTab,
                                   boolean fromClient, boolean initialSelection) {
            super(source, fromClient);
            this.selectedTab = source.getSelectedTab();
            this.initialSelection = initialSelection;
            this.previousTab = previousTab;
        }

        /**
         * Get selected tab for this event. Can be {@code null} when autoselect
         * is set to false.
         *
         * @return the selected tab for this event
         */
        public Tab getSelectedTab() {
            return this.selectedTab;
        }

        /**
         * Get previous selected tab for this event. Can be {@code null} when
         * autoselect is set to false.
         *
         * @return the selected tab for this event
         */
        public Tab getPreviousTab() {
            return this.previousTab;
        }

        /**
         * Checks if this event is initial TabSheet selection.
         *
         * @return <code>true</code> if the event is initial TabSheet selection,
         * <code>false</code> otherwise
         */
        public boolean isInitialSelection() {
            return this.initialSelection;
        }

    }

    public static class ScrollToEndEvent extends ComponentEvent<HTabSheet> {

        /**
         * Creates a new event using the given source and indicator whether the
         * event originated from the client side or the server side.
         *
         * @param source the source component
         */
        ScrollToEndEvent(HTabSheet source) {
            super(source, true);
        }
    }

    public static class ScrollEvent extends ComponentEvent<HTabSheet> {

        private final int scrollTop;
        private final int scrollLeft;

        /**
         * Creates a new event using the given source and indicator whether the
         * event originated from the client side or the server side.
         *
         * @param source the source component
         */
        ScrollEvent(HTabSheet source, String details) {
            super(source, true);
            String[] split = details.split(",");
            this.scrollTop = Integer.parseInt(split[0]);
            this.scrollLeft = Integer.parseInt(split[1]);
        }

        public int getScrollLeft() {
            return scrollLeft;
        }

        public int getScrollTop() {
            return scrollTop;
        }

    }

}
