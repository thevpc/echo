package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.echo.api.components.AppProgressBar;
import net.thevpc.echo.impl.components.NumberBase;
import net.thevpc.echo.spi.peers.AppProgressBarPeer;

public class ProgressBar<T extends Number> extends NumberBase<T> implements AppProgressBar<T> {
    private WritableBoolean indeterminate = Props.of("indeterminate").booleanOf(false);
    private WritableBoolean textPainted = Props.of("textPainted").booleanOf(false);
    private WritableStr text;
    public ProgressBar(String id,
                       Class<T> numberType,
                       Application app) {
        super(id, numberType, app, (Class) AppProgressBar.class, AppProgressBarPeer.class);
        text = AppProps.of("text",app).strOf(Str.empty());
        if(Integer.class.equals(numberType) || int.class.equals(numberType)) {
            value().set((T) Integer.valueOf(0));
            min().set((T) Integer.valueOf(0));
            max().set((T) Integer.valueOf(100));
        }else if(Long.class.equals(numberType) || long.class.equals(numberType)) {
            value().set((T) Long.valueOf(0));
            min().set((T) Long.valueOf(0));
            max().set((T) Long.valueOf(100));
        }else if(Byte.class.equals(numberType) || byte.class.equals(numberType)){
            value().set((T)Byte.valueOf((byte)0));
            min().set((T)Byte.valueOf((byte)0));
            max().set((T)Byte.valueOf((byte) 100));
        }else if(Short.class.equals(numberType) || short.class.equals(numberType)){
            value().set((T)Short.valueOf((short) 0));
            min().set((T)Short.valueOf((short) 0));
            max().set((T)Short.valueOf((short) 100));
        }else if(Float.class.equals(numberType) || float.class.equals(numberType)){
            value().set((T)Float.valueOf(0));
            min().set((T)Float.valueOf(0));
            max().set((T)Float.valueOf( 100));
        }else if(Double.class.equals(numberType) || double.class.equals(numberType)){
            value().set((T)Double.valueOf( 0));
            min().set((T)Double.valueOf( 0));
            max().set((T)Double.valueOf( 100));
        }
        propagateEvents(indeterminate,text,textPainted);
    }

    public ProgressBar(Class<T> numberType,
                       Application app) {
        this(null, numberType, app);
    }

    public WritableStr text() {
        return text;
    }

    public WritableBoolean indeterminate() {
        return indeterminate;
    }

    public WritableBoolean textPainted() {
        return textPainted;
    }
}

