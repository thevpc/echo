package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.PropertyBase;
import net.thevpc.echo.*;
import net.thevpc.echo.api.AppChildConstraints;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppParentConstraints;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentEvents;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppContextMenu;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.iconset.WritableImage;
import net.thevpc.echo.impl.DefaultAppComponentEvents;
import net.thevpc.echo.spi.peers.AppComponentPeer;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ComponentBase extends PropertyBase implements AppComponent {

    private final WritableBoolean focused;
    private final WritableBoolean editing;
    private final WritableBoolean enabled;
    private final WritableBoolean visible;
    private final WritableInt mnemonic;
    private final WritableString accelerator;
    private final WritableStr title;
    private final WritableStr tooltip;
    private final WritableImage icon;
    private final WritableImage largeIcon;
    private final WritableBoolean editable;
    private final WritableBoolean active;
    private final WritableValue<AppFont> font;
    private final WritableValue<AppColor> foregroundColor;
    private final WritableValue<AppColor> backgroundColor;
    private final WritableValue<Dimension> prefSize;
    private final WritableValue<Bounds> bounds;
    private final WritableValue<Locale> locale = Props.of("path").valueOf(Locale.class, null);
    private final WritableValue<String> iconSet = Props.of("path").valueOf(String.class, null);
    private final WritableBoolean shown;
    private final WritableBoolean opaque;
    protected AppComponent parent;
    protected WritableValue<Path> path = Props.of("path").valueOf(Path.class, Path.of());
    protected WritableValue<AppContextMenu> contextMenu = Props.of("contextMenu").valueOf(AppContextMenu.class);
    protected AppComponentPeer peer;
    protected Class<? extends AppComponent> itemType;
    protected Class<? extends AppComponentPeer> peerType;
    protected WritableMap<Object, Object> properties = Props.of("properties").mapOf(Object.class, Object.class);
    protected WritableValue<Anchor> anchor = Props.of("anchor").valueOf(Anchor.class);
    private WritableValue<Integer> order = Props.of("order").valueOf(Integer.class, null);
    private DefaultParentConstraints parentConstraints = new DefaultParentConstraints("parentConstraints");
    private DefaultAppChildConstraints childConstraints = new DefaultAppChildConstraints("childConstraints");
    private WritableTextStyle titleStyle;
    private Application app;

    public ComponentBase(String id, Application app,
                         Class<? extends AppComponent> itemType,
                         Class<? extends AppComponentPeer> peerType) {
        super(id == null ? UUID.randomUUID().toString() : id);
        boolean doConfig = id != null && !id.startsWith(".");
        this.app = app;
        if (app == null) {
            throw new NullPointerException();
        }
        prefSize = AppProps.of("prefSize", app).valueOf(Dimension.class);
        focused = AppProps.of("focused", app).booleanOf(false);
        editing = AppProps.of("editing", app).booleanOf(false);
        editable = AppProps.of("editable", app).booleanOf(true);
        active = AppProps.of("active", app).booleanOf(true);
        enabled = AppProps.of("enabled", app).booleanOf(true);
        visible = AppProps.of("visible", app).booleanOf(true);
        title = AppProps.of("title", app).strOf(id == null ? Str.of("") : Str.i18n(id));
        this.titleStyle = new WritableTextStyle("titleStyle");
        tooltip = AppProps.of("tooltip", app).strOf(
                doConfig/*&&model.config().configurableTooltip().get()*/ ? Str.i18n(id + ".tooltip") : null);
        icon = new WritableImage("icon", app, this);
        icon.set(doConfig/*&&model.config().configurableSmallIcon().get()*/ ? Str.i18n(id + ".icon") : null);
        largeIcon = new WritableImage("largeIcon", app, this);
        largeIcon.set(doConfig/*&&model.config().configurableLargeIcon().get()*/ ? Str.i18n(id + ".largeIcon") : null);
        accelerator = AppProps.of("accelerator", app).stringOf(null);
        mnemonic = AppProps.of("mnemonic", app).intOf(0);
        font = AppProps.of("font", app).valueOf(AppFont.class);
        foregroundColor = AppProps.of("foregroundColor", app).valueOf(AppColor.class);
        backgroundColor = AppProps.of("backgroundColor", app).valueOf(AppColor.class);
        opaque = AppProps.of("opaque", app).booleanOf(true);
        shown = AppProps.of("shown", app).booleanOf(false);
        bounds = AppProps.of("bounds", app).valueOf(Bounds.class);
        this.itemType = itemType;
        this.peerType = peerType;
        this.path.set(Path.of(id()));
        propagateEvents(anchor, enabled, visible, editable, active, title, titleStyle,
                tooltip, icon, largeIcon, accelerator, mnemonic, prefSize,
                focused, editing, bounds, shown, locale, iconSet,properties,opaque,
                foregroundColor,backgroundColor,font,
                contextMenu,parentConstraints,childConstraints,order
        );
        focused.onChange(event -> {
            if (focused.get()) {
                ((WritableValue<AppComponent>) app.toolkit().focusOwner()).set(this);
            }
        });
    }

    @Override
    protected PropertyListeners createListeners() {
        return new DefaultAppComponentEvents(this);
    }

    @Override
    public WritableValue<AppFont> font() {
        return font;
    }

    @Override
    public WritableValue<AppColor> foregroundColor() {
        return foregroundColor;
    }

    @Override
    public AppComponentEvents events() {
        return (AppComponentEvents) super.events();
    }

    public AppChildConstraints childConstraints() {
        return childConstraints;
    }

    @Override
    public AppParentConstraints parentConstraints() {
        return parentConstraints;
    }

    @Override
    public AppComponent setOptions(AppComponentOptions options) {
        if (options != null) {
            order.set(options.order());
        }
        return this;
    }

    @Override
    public AppComponent parent() {
        return parent;
    }

    public WritableValue<Path> path() {
        return path;
    }

    public ObservableValue<Integer> order() {
        return order.readOnly();
    }

    @Override
    public WritableValue<AppContextMenu> contextMenu() {
        return contextMenu;
    }

    @Override
    public Application app() {
        return app;
    }

    @Override
    public AppComponentPeer peer() {
        return peer(true);
    }

    @Override
    public AppComponentPeer peer(boolean prepareShowing) {
        if (!prepareShowing) {
            return peer;
        }
        if (peer == null) {
            AppComponentPeer p = app().toolkit().createComponentPeer(this);
            peerType().cast(p);
            peer = p;
            p.install(this);
        }
        return peer;
    }

    @Override
    public Class<? extends AppComponent> componentType() {
        return itemType;
    }

    @Override
    public Class<? extends AppComponentPeer> peerType() {
        return peerType;
    }

    @Override
    public String id() {
        return propertyName();
    }

    @Override
    public WritableValue<Dimension> prefSize() {
        return prefSize;
    }

    @Override
    public WritableBoolean focused() {
        return focused;
    }

    @Override
    public WritableBoolean editing() {
        return editing;
    }

    @Override
    public WritableBoolean active() {
        return active;
    }

    @Override
    public WritableValue<Anchor> anchor() {
        return anchor;
    }

    @Override
    public WritableImage icon() {
        return icon;
    }

    @Override
    public WritableStr title() {
        return title;
    }

    public WritableTextStyle titleStyle() {
        return titleStyle;
    }

    @Override
    public WritableImage largeIcon() {
        return largeIcon;
    }

    public WritableInt mnemonic() {
        return mnemonic;
    }

    public WritableString accelerator() {
        return accelerator;
    }

    @Override
    public WritableBoolean enabled() {
        return enabled;
    }

    public WritableBoolean editable() {
        return editable;
    }

    @Override
    public WritableBoolean visible() {
        return visible;
    }

    @Override
    public WritableStr tooltip() {
        return tooltip;
    }

    @Override
    public WritableMap<Object, Object> properties() {
        return properties;
    }

    @Override
    public WritableValue<AppColor> backgroundColor() {
        return backgroundColor;
    }

    @Override
    public WritableBoolean opaque() {
        return opaque;
    }

    public WritableValue<Bounds> bounds() {
        return bounds;
    }

    @Override
    public WritableValue<Locale> locale() {
        return locale;
    }

    @Override
    public WritableValue<String> iconSet() {
        return iconSet;
    }

    @Override
    public WritableBoolean shown() {
        return shown;
    }

    @Override
    public AppComponent copy(boolean bind) {
        return copyDefault();
    }

    @Override
    public void requestFocus() {
        peer().requestFocus();
    }

    protected void prepareUnshowing() {
        if (peer != null) {
            app().toolkit().runUI(() -> {
                AppComponentPeer p = peer();
                p.uninstall();
//                peer = null;
            });
        }
    }

    protected AppComponent copyDefault() {
        Constructor c = resolveDefaultComponentConstructor(getClass(), Application.class);
        c.setAccessible(true);
        try {
            return (AppComponent) c.newInstance(app());
        } catch (Exception ex) {
            throw new IllegalArgumentException("invalid constructor " + getClass().getSimpleName() + "(Application)", ex);
        }
    }

    protected Constructor resolveDefaultComponentConstructor(Class componentType, Class modelClass) {
        List<Constructor> possibilities = new ArrayList<>();
        for (Constructor cc : componentType.getDeclaredConstructors()) {
            if (cc.getParameterCount() == 1) {
                Class t1 = cc.getParameterTypes()[0];
                if (t1.isAssignableFrom(modelClass)) {
                    possibilities.add(cc);
                }
            }
        }
        if (possibilities.isEmpty()) {
            throw new IllegalArgumentException("Missing constructor " + componentType.getSimpleName() + "(" + modelClass.getSimpleName() + ")");
        }
        if (possibilities.size() > 1) {
            throw new IllegalArgumentException("Ambiguous constructors " + componentType.getSimpleName() + "(" + modelClass.getSimpleName() + ") : " + possibilities);
        }
        return possibilities.get(0);
    }

    public void setParent(AppComponent other) {
        this.parent = other;
    }

    public void setPeer(AppComponentPeer peer) {
        this.peer = peer;
    }
}
