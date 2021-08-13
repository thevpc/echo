package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.*;
import net.thevpc.echo.api.components.AppComboBox;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppEventType;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.components.ChoiceBase;
import net.thevpc.echo.spi.peers.AppComboBoxPeer;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ComboBox<T> extends ChoiceBase<T> implements AppComboBox<T> {
    private WritableString textContentType;
    private WritableBoolean lastWasEdit = Props.of("lastWasEdit").booleanOf(false);
    private WritableStr text;
    private WritableTextStyle textStyle;
    private WritableString textSelection = Props.of("textSelection").stringOf("");
    private WritableInt caretPosition = Props.of("caretPosition").intOf(-1);


    public ComboBox(Class<T> itemType, Application app) {
        this(null, itemType, app);
    }

    public ComboBox(String id, Class<T> itemType, Application app) {
        this(id, PropertyType.of(itemType), app);
    }

    public ComboBox(String id, PropertyType itemType, Application app) {
        super(id, itemType, false, app,
                (Class<? extends AppComponent>) AppComboBox.class, AppComboBoxPeer.class);
        //defaults to non editable
        text = AppProps.of("text", app()).strOf(null);
        textContentType = AppProps.of("text", app()).stringOf("text/plain");
        textStyle = new WritableTextStyle("textStyle");
        text.set(Str.empty());
        editable().set(false);
        events().add(e -> {
            if (e.code().isNavigationKey()) {
                return;
            }
            lastWasEdit.set(true);
        }, AppEventType.KEY_PRESSED);
        selection().onChange(e -> {
            lastWasEdit.set(false);
        });
        Class typeClass = itemType.getTypeClass();
        if (typeClass != null) {
            if (typeClass.isEnum()) {
                values().add(null);
                values().addAll(
                        Arrays.asList(typeClass.getEnumConstants()).toArray(
                                (T[]) Array.newInstance(typeClass, 0)
                        )
                );
                itemRenderer().set(context -> {
                    context.setText(Applications.rawString(
                            Str.i18n("Enum." + typeClass.getName() + "." +
                                    (context.getValue() == null ? "null" : context.getValue().toString())
                            ), ComboBox.this
                    ));
                    context.renderDefault();
                });
            } else if (typeClass.equals(Boolean.class)) {
                values().addAll(null, (T) Boolean.FALSE, (T) Boolean.TRUE);
                itemRenderer().set(context -> {
                    context.setText(Applications.rawString(
                            Str.i18n("Enum." + typeClass.getName() + "." +
                                    (context.getValue() == null ? "null" : context.getValue().toString())
                            ), ComboBox.this
                    ));
                    context.renderDefault();
                });
            } else if (typeClass.equals(boolean.class)) {
                values().addAll((T) Boolean.FALSE, (T) Boolean.TRUE);
                itemRenderer().set(context -> {
                    context.setText(Applications.rawString(
                            Str.i18n("Enum." + typeClass.getName() + "." +
                                    (context.getValue() == null ? "null" : context.getValue().toString())
                            ), ComboBox.this
                    ));
                    context.renderDefault();
                });
            }
        }
    }

    @Override
    public WritableBoolean lastWasEdit() {
        return lastWasEdit;
    }

    @Override
    public AppComboBoxPeer peer() {
        return (AppComboBoxPeer) super.peer();
    }

    @Override
    public WritableString textContentType() {
        return textContentType;
    }

    @Override
    public WritableStr text() {
        return text;
    }

    @Override
    public WritableTextStyle textStyle() {
        return textStyle;
    }

    public WritableString textSelection() {
        return textSelection;
    }

    @Override
    public WritableInt caretPosition() {
        return caretPosition;
    }

    @Override
    public void replaceSelection(String newValue) {
        peer().replaceSelection(newValue);
    }


}

