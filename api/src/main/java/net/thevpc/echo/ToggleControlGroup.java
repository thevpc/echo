package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.*;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.AllMargins;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.components.ChoiceBase;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class ToggleControlGroup<T> extends ChoiceBase<T> {

    public static final String VALUE_PROPERTY_NAME = ToggleControlGroup.class.getName() + ":value";
    private Panel container;
    private PropertyListener toggleChangeListener;

    public ToggleControlGroup(Class<T> itemType, Application app) {
        this(null, itemType, app);
    }

    public ToggleControlGroup(String id, Class<T> itemType, Application app) {
        this(id, PropertyType.of(itemType), app);
    }

    public ToggleControlGroup(String id, PropertyType itemType, Application app) {
        super(id, itemType, false, app,
                (Class<? extends AppComponent>) AppChoiceControl.class, AppComponentPeer.class);
        //defaults to non editable
        editable().set(false);
        container = new Panel(id, Layout.HORIZONTAL, app);
        container.parentConstraints().addAll(AllMargins.of(2, 4, 2, 4));
        Applications.bindContent(this, container);
        values().onChange(event -> valuesChanged(event));
        toggleChangeListener = e -> {
            AppToggleControl b = (AppToggleControl) e.property().userObjects().get("owner");
            T tt = (T) b.userObjects().get(VALUE_PROPERTY_NAME);
            if (selection().multipleSelection().get()) {
                if (b.selected().get()) {
                    ToggleControlGroup.this.selection().add(tt);
                } else {
                    ToggleControlGroup.this.selection().remove(tt);
                }
            } else {
                if (b.selected().get()) {
                    ToggleControlGroup.this.selection().set(tt);
                } else {
                    if (!selection().noSelection().get()) {
                        if (ToggleControlGroup.this.selection().get() == b) {
                            //do not accept no selection, change back to selected
                            b.selected().set(true);
                        }
                    }
                }
            }
        };
        selection().onChange(() -> {
            for (AppComponent child : container().children()) {
                T tt = (T) child.userObjects().get(VALUE_PROPERTY_NAME);
                boolean selectedValue = selection().contains(tt);
                ((AppToggleControl) child).selected().set(selectedValue);
            }
        });
    }

    public Panel container() {
        return container;
    }

    protected void prepareShowing(AppToggleControl b, int index) {
        AppChoiceItemRenderer<T> r = itemRenderer().get();
        b.selected().onChange(toggleChangeListener);
        b.selected().userObjects().put("owner", b);
        T t = values().get(index);
        b.userObjects().put(ToggleControlGroup.class.getName() + ":value", t);
        if (r == null) {
            b.text().set(Str.of(t == null ? "" : String.valueOf(t)));
        } else {
            r.render(new SimpleItemContext(index, b));
        }
    }

    protected void prepareUnshowing(AppToggleControl b, int index) {
        b.selected().events().remove(toggleChangeListener);
    }

    protected AppToggleControl createToggleControl() {
        return new RadioButton(null, null, null, app());
    }

    protected void valuesChanged(PropertyEvent event) {
        switch (event.eventType()) {
            case ADD: {
                AppToggleControl c = createToggleControl();
                prepareShowing(c, event.index());
                container.children().add(c);
                break;
            }
            case REMOVE: {
                Integer index = (Integer) event.index();
                AppToggleControl c = (AppToggleControl) container.children().removeAt(index);
                prepareUnshowing(c, index);
                break;
            }
            case UPDATE: {
                AppToggleControl c = (AppToggleControl) container.children().get((Integer) event.index());
                prepareShowing(c, event.index());
                break;
            }
        }
    }

    private class SimpleItemContext implements AppChoiceItemContext<T> {

        private final int index;
        private final AppToggleControl b;

        public SimpleItemContext(int index, AppToggleControl b) {
            this.index = index;
            this.b = b;
        }

        @Override
        public AppChoiceControl<T> getChoice() {
            return ToggleControlGroup.this;
        }

        @Override
        public Application getApplication() {
            return b.app();
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public T getValue() {
            return values().get(index);
        }

        @Override
        public void setText(String text) {
            b.text().set(Str.of(text));
        }

        @Override
        public void setOpaque(boolean opaque) {
            b.opaque().set(opaque);
        }

        @Override
        public void setTextColor(AppColor color) {
            b.textStyle().foregroundColor().set(color);
        }

        @Override
        public void setTextFont(AppFont font) {
            b.textStyle().font().set(font);
        }

        @Override
        public void setTextUnderline(boolean underline) {
            b.textStyle().underline().set(underline);
        }

        @Override
        public void setTextStrikeThrough(boolean strikeThrough) {
            b.textStyle().strikethrough().set(strikeThrough);
        }

        @Override
        public void setTextStrokeSize(int size) {
            b.textStyle().strokeSize().set(size);
        }

        @Override
        public void setTextAlign(Anchor align) {
            b.textStyle().align().set(align);
        }

        @Override
        public void setIcon(AppImage icon) {
            b.icon().set(icon);
        }

        @Override
        public void setIcon(Str icon) {
            b.icon().set(icon);
        }

        @Override
        public boolean isSelected() {
            return b.selected().get();
        }

        @Override
        public boolean isFocused() {
            return b.focused().get();
        }

        @Override
        public AppFont getFont() {
            return b.textStyle().font().get();
        }

        @Override
        public AppColor getColor() {
            return b.textStyle().foregroundColor().get();
        }

        @Override
        public AppColor getBackgroundColor() {
            return b.backgroundColor().get();
        }

        @Override
        public void setBackgroundColor(AppColor color) {
            b.backgroundColor().set(color);
        }

        @Override
        public void renderDefault() {

        }

        @Override
        public void setValue(Object value) {
            setText(String.valueOf(value));
        }

        @Override
        public boolean isDisabled() {
            return !b.enabled().get();
        }

    }
}
