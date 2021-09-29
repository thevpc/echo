package net.thevpc.echo;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppContainerChildren;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataPane<T> extends BorderPane {
    public static PaneLayout VERTICAL = new Vertical(1);
    public static PaneLayout HORIZONTAL = new Horizontal(1);
    public static PaneLayout TABS = new Tabs();
    private final WritableValue<PaneLayout> paneLayout = Props.of("paneLayout").valueOf(PaneLayout.class, VERTICAL);
    private final WritableList<T> values;
    private final DataPaneRenderer<T> renderer;
    private final List<AppComponent> renderedComponents = new ArrayList<>();
    private final WritableValue<AppContainer> container = Props.of("container").valueOf(AppContainer.class);
    boolean withSeparator = true;
    boolean containerAcceptsSeparator = false;

    public DataPane(Class componentType, DataPaneRenderer<T> renderer, Application app) {
        super(app);
        values = Props.of("value").listOf(componentType);
        this.renderer = renderer;
        values.onChange(this::valuesChanged);
        paneLayout().onChangeAndInit(this::updateContainer);
//        updateContainer();
    }

    public List<AppComponent> renderedComponents() {
        return renderedComponents;
    }

    public WritableValue<PaneLayout> paneLayout() {
        return paneLayout;
    }

    public WritableValue<AppContainer<AppComponent>> container() {
        return (WritableValue) container;
    }

    public void updateContainer() {
        //TODO FIX ME: this method should update container children as well !!!
//        System.out.println("start updateContainer");
//        checkInvariants();
        AppContainer container = this.container.get();

        PaneLayout oldLayout = container == null ? null : (PaneLayout) container.userObjects().get(PaneLayout.class.getName());
        if (oldLayout == null) {
            oldLayout = VERTICAL;
        }
        PaneLayout newLayout = this.paneLayout.get();
        if (newLayout == null) {
            newLayout = VERTICAL;
        }
        if (container == null || !oldLayout.equals(newLayout)) {
//            List<AppComponent> old = null;
            if (container != null) {
                container.children().clear();
            }
            if (newLayout instanceof Vertical) {
                int columns = ((Vertical) newLayout).columns;
                GridPane p = new GridPane(columns, app());
                p.parentConstraints().addAll(
                        AllGrow.HORIZONTAL,
                        AllMargins.of(3),
                        AllFill.HORIZONTAL,
                        ContainerGrow.TOP_ROW
                );
                container = p;
                containerAcceptsSeparator = true;
            } else if (newLayout instanceof Horizontal) {
                int rows = ((Horizontal) newLayout).rows;
                if (rows == 1) {
                    HorizontalPane p = new HorizontalPane(app());
                    container = p;
                    p.parentConstraints().addAll(AllMargins.of(3), ContainerGrow.TOP_ROW);
                } else {
                    HorizontalPane p = new HorizontalPane(app());
                    p.parentConstraints().add(new ColumnCount(rows));
                    container = p;
                    p.parentConstraints().addAll(AllMargins.of(3), ContainerGrow.TOP_LEFT_CORNER);
                }
                containerAcceptsSeparator = true;
            } else if (newLayout instanceof Tabs) {
                container = new TabPane(app());
                containerAcceptsSeparator = false;
            } else {
                throw new IllegalArgumentException("not supported yet " + newLayout);
            }
            children().clear();
            container.userObjects().put(PaneLayout.class.getName(), newLayout);
            container.anchor().set(Anchor.CENTER);
            children().add(container);
            this.container.set(container);
//            if (old != null) {
//                container.children().addCollection(old);
            //re-render
            for (int i = 0; i < renderedComponents.size(); i++) {
                //T value=(T) old.get(i).userObjects().get(DataPane.class.getName()+":value");
                T value = values.get(i);
                renderer.set(i, value, renderedComponents.get(i), this);
            }
//            }
        }
//        System.out.println("end updateContainer");
//        checkInvariants();
    }

    private boolean isEffectiveWithSeparator() {
        return withSeparator && containerAcceptsSeparator;
    }

    private int indexOfElement(int i) {
        if (i < 0) {
            return i;
        }
        if (isEffectiveWithSeparator()) {
            return i * 2;
        }
        return i;
    }

    protected void valuesChanged(PropertyEvent event) {
//        System.out.println("start valuesChanged "+event);
//        checkInvariants();
        AppContainerChildren<AppComponent> containerChildren = this.container().get().children();
        switch (event.eventType()) {
            case ADD: {
                AppComponent c = renderer.create(this);
                T newValue = event.newValue();
                c.userObjects().put(DataPane.class.getName() + ":value", newValue);
                int _index = event.index();
                renderer.set(_index, newValue, c, this);
                if (_index < 0) {
                    _index = renderedComponents.size();
                }
                renderedComponents.add(_index, c);
                if (isEffectiveWithSeparator()) {
                    if (containerChildren.size() > 0) {
                        int index = containerChildren.size() / 2;
                        containerChildren.add(createSeparator(index, c));
                    }
                }
                containerChildren.add(c);
                break;
            }
            case REMOVE: {
                int _index = event.index();
                renderedComponents.remove(_index);
                int ii = indexOfElement(_index);
                AppComponent c = containerChildren.removeAt(ii);
                if (isEffectiveWithSeparator()) {
                    if (_index == 0) {
                        if (containerChildren.size() > 0) {
                            //remove separator
                            containerChildren.removeAt(0);
                        }
                    } else {
                        containerChildren.removeAt(ii - 1);
                    }
                }
                renderer.dispose(c, this);
                c.userObjects().put(DataPane.class.getName() + ":value", null);
                break;
            }
            case UPDATE: {
                Integer _index = event.index();
                int ii = indexOfElement(_index);
                if (ii < containerChildren.size()) {
                    AppComponent c = containerChildren.get(ii);
                    T newValue = event.newValue();
                    c.userObjects().put(DataPane.class.getName() + ":value", newValue);
                    renderer.set(event.index(), newValue, c, this);
                }
                break;
            }
        }
//        System.out.println("end valuesChanged "+event);
//        checkInvariants();
    }

    private void checkInvariants() {
        AppContainer<AppComponent> containerChildren = this.container().get();
        int containerChildrenSize = containerChildren==null?0:containerChildren.children().size();
        int renderedComponentsSize = renderedComponents.size();
        int valuesSize = values.size();
        System.out.println("checkInvariants: containerChildrenSize="+containerChildrenSize
                +", renderedComponentsSize="+renderedComponentsSize
                +", valuesSize="+valuesSize
                +", withSeparator="+withSeparator
                +", containerAcceptsSeparator="+containerAcceptsSeparator
                +", isEffectiveWithSeparator="+isEffectiveWithSeparator()
        );
        if (renderedComponentsSize > 1 && isEffectiveWithSeparator()) {
            if ((2*renderedComponentsSize -1) != containerChildrenSize) {
                System.out.println("Why");
            }
        } else {
            if (renderedComponentsSize != containerChildrenSize) {
                System.out.println("Why");
            }
        }

    }

    protected AppComponent createSeparator(int index, AppComponent c) {
        return new Separator(app());
    }


    public WritableList<T> values() {
        return values;
    }


    public interface PaneLayout {

    }

    public static class Vertical implements PaneLayout {
        private final int columns;

        public Vertical(int columns) {
            this.columns = columns;
        }

        @Override
        public int hashCode() {
            return Objects.hash(columns);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertical vertical = (Vertical) o;
            return columns == vertical.columns;
        }
    }

    public static class Horizontal implements PaneLayout {
        private final int rows;

        public Horizontal(int rows) {
            this.rows = rows;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rows);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Horizontal that = (Horizontal) o;
            return rows == that.rows;
        }
    }

    public static class Tabs implements PaneLayout {
        public Tabs() {
        }

        @Override
        public int hashCode() {
            return Objects.hash(getClass().getName());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tabs that = (Tabs) o;
            return true;
        }
    }
}
