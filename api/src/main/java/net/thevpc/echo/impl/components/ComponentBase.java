package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.SimpleProperty;
import net.thevpc.echo.AppProps;
import net.thevpc.echo.Application;
import net.thevpc.echo.Dimension;
import net.thevpc.echo.WritableTextStyle;
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
import java.util.UUID;

public class ComponentBase extends SimpleProperty implements AppComponent {
    private final WritableBoolean focused;
    private final WritableBoolean editing;
    private final WritableBoolean enabled;
    private final WritableBoolean visible;
    private final WritableInt mnemonic;
    private final WritableString accelerator;
    private final WritableStr title;
    private final WritableStr tooltip;
    private final WritableImage smallIcon;
    private final WritableImage largeIcon;
    private final WritableBoolean editable;
    private final WritableBoolean active;
    private final WritableValue<AppFont> font;
    private final WritableValue<AppColor> foregroundColor;
    private final WritableValue<AppColor> backgroundColor;
    private final WritableValue<Dimension> prefSize;
    private final WritableBoolean opaque;
    protected AppComponent parent;
    protected WritableValue<Path> path = Props.of("path").valueOf(Path.class, Path.of());
    protected DefaultAppComponentEvents events;
    protected WritableValue<AppContextMenu> contextMenu = Props.of("contextMenu").valueOf(AppContextMenu.class);
    protected AppComponentPeer peer;
    protected Class<? extends AppComponent> itemType;
    protected Class<? extends AppComponentPeer> peerType;
    protected WritableMap<Object, Object> properties = Props.of("properties").mapOf(Object.class, Object.class);
    protected WritableValue<Anchor> anchor = Props.of("anchor").valueOf(Anchor.class, Anchor.CENTER);
    private WritableValue<Integer> order = Props.of("order").valueOf(Integer.class, null);
    private DefaultParentConstraints parentConstraints = new DefaultParentConstraints("parentConstraints");
    private DefaultAppChildConstraints childConstraints = new DefaultAppChildConstraints("childConstraints");
    private WritableTextStyle titleStyle;
    private String id;
    private Application app;

    public ComponentBase(String id, Application app,
                         Class<? extends AppComponent> itemType,
                         Class<? extends AppComponentPeer> peerType) {
        super(id == null ? UUID.randomUUID().toString() : id);
        boolean doConfig = id != null;
        this.id = propertyName();
        this.app = app;
        prefSize = AppProps.of("prefSize", app).valueOf(Dimension.class);
        focused = AppProps.of("focused", app).booleanOf(false);
        editing = AppProps.of("editing", app).booleanOf(false);
        editable = AppProps.of("editable", app).booleanOf(true);
        active = AppProps.of("active", app).booleanOf(true);
        enabled = AppProps.of("enabled", app).booleanOf(true);
        visible = AppProps.of("visible", app).booleanOf(true);
        title = AppProps.of("title", app).strOf(id==null?Str.of(""):Str.i18n(id));
        this.titleStyle = new WritableTextStyle("titleStyle");
        tooltip = AppProps.of("tooltip", app).strOf(
                doConfig/*&&model.config().configurableTooltip().get()*/ ? Str.i18n(id + ".tooltip") : null);
        smallIcon = AppProps.of("smallIcon", app).iconOf(
                doConfig/*&&model.config().configurableSmallIcon().get()*/ ?
                        Str.i18n(id + ".icon") : null);
        largeIcon = AppProps.of("largeIcon", app).iconOf(
                doConfig/*&&model.config().configurableLargeIcon().get()*/ ?
                        Str.i18n(id + ".largeIcon") : null
        );
        accelerator = AppProps.of("accelerator", app).stringOf(null);
        mnemonic = AppProps.of("mnemonic", app).intOf(0);
        font = AppProps.of("font", app).valueOf(AppFont.class);
        foregroundColor = AppProps.of("foregroundColor", app).valueOf(AppColor.class);
        backgroundColor = AppProps.of("backgroundColor", app).valueOf(AppColor.class);
        opaque = AppProps.of("opaque", app).booleanOf(true);
        this.itemType = itemType;
        this.peerType = peerType;
        this.path.set(Path.of(id()));
        events = new DefaultAppComponentEvents();
        propagateEvents(anchor, enabled, visible, editable, active, title, titleStyle,
                tooltip, smallIcon, largeIcon, accelerator, mnemonic, prefSize,
                focused, editing
        );
        propagateEvents(order, childConstraints);
    }

    @Override
    public AppComponentEvents events() {
        return events;
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

    protected void prepareUnshowing() {
        if (peer != null) {
            app().toolkit().runUI(() -> {
                AppComponentPeer p = peer();
                p.uninstall();
//                peer = null;
            });
        }
    }

    @Override
    public String id() {
        return id;
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
    public WritableImage smallIcon() {
        return smallIcon;
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

    @Override
    public AppComponent copy(boolean bind){
        return copyDefault();
    }

    protected AppComponent copyDefault(){
        Constructor c = resolveDefaultComponentConstructor(getClass(), Application.class);
        c.setAccessible(true);
        try {
            return (AppComponent) c.newInstance(app());
        } catch (Exception ex) {
            throw new IllegalArgumentException("invalid constructor "+getClass().getSimpleName()+"(Application)",ex);
        }
    }
    protected Constructor resolveDefaultComponentConstructor(Class componentType,Class modelClass){
        List<Constructor> possibilities=new ArrayList<>();
        for (Constructor cc : componentType.getDeclaredConstructors()) {
            if(cc.getParameterCount()==1){
                Class t1 = cc.getParameterTypes()[0];
                if(t1.isAssignableFrom(modelClass)){
                    possibilities.add(cc);
                }
            }
        }
        if(possibilities.isEmpty()){
            throw new IllegalArgumentException("Missing constructor "+componentType.getSimpleName()+"("+modelClass.getSimpleName()+")");
        }
        if(possibilities.size()>1){
            throw new IllegalArgumentException("Ambiguous constructors "+componentType.getSimpleName()+"("+modelClass.getSimpleName()+") : "+possibilities);
        }
        return possibilities.get(0);
    }


}
