package net.thevpc.echo;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppContainerChildren;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppEventType;
import net.thevpc.echo.api.components.AppNumberField;
import net.thevpc.echo.constraints.AllMargins;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.components.NumberBase;
import net.thevpc.echo.spi.peers.AppNumberFieldPeer;
import net.thevpc.echo.util.NumberHelper;

public class StarsField<T extends Number> extends NumberBase<T> implements AppNumberField<T> {

    private static StarRenderer DEFAULT = new StarRenderer() {
        @Override
        public AppImage createImage(StarRendererContext context) {
            if (context.getFill() <= 0.5) {
                return context.getApplication().iconSets().icon("star-disabled",
                        context.getStarsField().iconSet().get());
            }
            return context.getApplication().iconSets().icon("star-enabled",
                    context.getStarsField().iconSet().get());
        }
    };
    private Panel container;
    private WritableValue<StarRenderer<T>> renderer;

    public StarsField(String id,
            Class<T> numberType,
            Application app) {
        super(id, numberType, app, (Class) AppNumberField.class, AppNumberFieldPeer.class);
        this.min().set(NumberHelper.valueOf(0, numberType));
        this.max().set(NumberHelper.valueOf(5, numberType));
        renderer = Props.of("renderer").valueOf(PropertyType.of(StarRenderer.class, numberType));
        container = new Panel(id, Layout.HORIZONTAL, app);
        propagateEvents(renderer);
        container.parentConstraints().addAll(AllMargins.of(2, 4, 2, 4));
        Applications.bindContent(this, container);
        revalidateContainer();
        revalidateValue();
        max().onChange(() -> {
            revalidateContainer();
            revalidateValue();
        });
        value().onChange(this::revalidateValue);
    }

    public StarsField(Class<T> numberType,
            Application app) {
        this(null, numberType, app);
    }

    public static StarsField<Integer> create(int max, Application app) {
        StarsField<Integer> s = new StarsField<>(Integer.class, app);
        s.max().set(max);
        return s;
    }

    public WritableValue<StarRenderer<T>> renderer() {
        return renderer;
    }

    private void revalidateContainer() {
        for (AppComponent child : container.children()) {
            ImageView im = (ImageView) child;
            im.events().clear();
        }
        container.children().clear();
        int x = ((Number) max().get()).intValue();
        if (x > 0) {
            for (int i = 0; i < x; i++) {
                ImageView imv = new ImageView(app());
                int finalI = i;
                imv.events().add(e -> {
                    if (e.isSimpleClick() && e.isPrimaryMouseButton()) {
                        if (finalI == 0) {
                            double lastValue = NumberHelper.valueOf(value().get(), Double.class);
                            if (lastValue == 0) {
                                value().set(NumberHelper.valueOf(1, valueType()));
                            } else {
                                value().set(NumberHelper.valueOf(0, valueType()));
                            }
                        } else {
                            value().set(NumberHelper.valueOf(finalI + 1, valueType()));
                        }
                    }
                }, AppEventType.MOUSE_CLICKED);
                container.children().add(imv);
            }
        }
    }

    private void revalidateValue() {
        AppContainerChildren<AppComponent> children = container.children();
        T n = value().get();
        if (n == null) {
            n = NumberHelper.valueOf(0.0, valueType());
        }
        double in = NumberHelper.valueOf(n, Double.class);
        StarRenderer<T> starRenderer = renderer().get();
        if (starRenderer == null) {
            starRenderer = DEFAULT;
        }
        for (int i = 0; i < children.size(); i++) {
            ImageView im = (ImageView) children.get(i);
            double fill = in - i;
            if (fill <= 0) {
                fill = 0;
            }
            if (fill > 1) {
                fill = 1;
            }
            StarsField<T> sf = this;
            NumberStarRendererContext ctx = new NumberStarRendererContext(sf, i, (float) fill);
            AppImage image = starRenderer.createImage(ctx);
            if (image == null) {
                image = DEFAULT.createImage(ctx);
            }
            im.image().set(image);
        }
    }

    public interface StarRendererContext<T extends Number> {

        Application getApplication();

        StarsField<T> getStarsField();

        int getPosition();

        float getFill();
    }

    public interface StarRenderer<T extends Number> {

        AppImage createImage(StarRendererContext<T> context);
    }

    private class NumberStarRendererContext implements StarRendererContext<T> {

        private final StarsField<T> sf;
        private final int i;
        private final float fill;

        public NumberStarRendererContext(StarsField<T> sf, int i, float fill) {
            this.sf = sf;
            this.i = i;
            this.fill = fill;
        }

        @Override
        public Application getApplication() {
            return getStarsField().app();
        }

        @Override
        public StarsField<T> getStarsField() {
            return sf;
        }

        @Override
        public int getPosition() {
            return i;
        }

        @Override
        public float getFill() {
            return fill;
        }
    }
}
