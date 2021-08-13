package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableString;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppAlertAction;
import net.thevpc.echo.api.AppAlertInputPane;
import net.thevpc.echo.api.AppAlertResult;
import net.thevpc.echo.api.components.AppAlert;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.iconset.WritableImage;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.impl.dialog.InputTextAreaPanel;
import net.thevpc.echo.impl.dialog.InputTextFieldPanel;
import net.thevpc.echo.spi.peers.AppAlertPeer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alert extends ControlBase implements AppAlert {

    protected WritableValue<AppComponent> content;
    protected WritableValue<AppComponent> owner;
    protected List<String> buttonIds;
    protected WritableString defaultButton;
    protected WritableImage headerIcon;
    protected Map<String, AppAlertAction> consMap = new HashMap<>();
    //    protected AppAlertAction onClose = c -> c.getAlert().closeAlert();
    protected WritableStr headerText;
    protected AppAlertAction action = null;

    public Alert(AppComponent owner) {
        this(owner.app());
        owner().set(owner);
        iconSet().set(owner.iconSet().get());
        iconConfig().set(owner.iconConfig().get());
        locale().set(owner.locale().get());
    }

    public Alert(Application app) {
        this(null, app);
        headerText = AppProps.of("headerText", app).strOf(Str.empty());
        defaultButton = Props.of("defaultButton").stringOf(null);
        content = Props.of("content").valueOf(AppComponent.class);
        headerIcon = new WritableImage("headerIcon", app, this);
        owner = AppProps.of("owner", app).valueOf(AppComponent.class);
        propagateEvents(headerText);
    }

    public Alert(String id, Application app) {
        super(id, app, AppAlert.class, AppAlertPeer.class);
    }

    @Override
    public WritableValue<AppComponent> owner() {
        return owner;
    }

    @Override
    public WritableStr headerText() {
        return headerText;
    }

    @Override
    public WritableImage headerIcon() {
        return headerIcon;
    }

    @Override
    public WritableString defaultButton() {
        return defaultButton;
    }

    @Override
    public AppAlert setInputTextFieldContent(Str headerId, Str initialValue) {
        content().set(new InputTextFieldPanel(app(), headerId, initialValue));
        return this;
    }

    @Override
    public AppAlert setInputTextAreaContent(Str headerId, Str initialValue) {
        content().set(new InputTextAreaPanel(app(), headerId, initialValue));
        return this;
    }

    @Override
    public AppAlert setInputContent(AppAlertInputPane inputPanel) {
        setContent(inputPanel);
        return this;
    }

    @Override
    public WritableValue<AppComponent> content() {
        return content;
    }

    @Override
    public AppAlert setContent(AppComponent mainComponent) {
        this.content.set(mainComponent);
        return this;
    }

    @Override
    public AppAlert setContent(Object mainComponent) {
        this.content.set(
                mainComponent == null ? null : app().toolkit().createComponent(mainComponent)
        );
        return this;
    }

    @Override
    public AppAlert setContentText(Str labelId) {
        AppLabel label = new Label(app());
        label.text().set(labelId);
        content().set(label);
        return this;
    }

    @Override
    public AppAlert withOkOnlyButton() {
        return withOkOnlyButton(null);
    }

    @Override
    public AppAlert withOkOnlyButton(AppAlertAction ok) {
        buttonIds = Arrays.asList("ok");
        setButtonAction("ok", ok);
        if (defaultButton.get() == null) {
            defaultButton().set("ok");
        }
        return this;
    }

    @Override
    public AppAlert withOkCancelButtons() {
        return withOkCancelButtons(null, null);
    }

    @Override
    public AppAlert withOkCancelButtons(AppAlertAction ok, AppAlertAction cancel) {
        buttonIds = Arrays.asList("ok", "cancel");
        setButtonAction("ok", ok);
        setButtonAction("cancel", cancel);
        if (defaultButton.get() == null) {
            defaultButton().set("ok");
        }
        return this;
    }

    @Override
    public AppAlert withYesNoButtons() {
        return withYesNoButtons(null, null);
    }

    @Override
    public AppAlert withYesNoButtons(AppAlertAction yes, AppAlertAction no) {
        buttonIds = Arrays.asList("yes", "no");
        setButtonAction("yes", yes);
        setButtonAction("no", no);
        if (defaultButton.get() == null) {
            defaultButton().set("yes");
        }
        return this;
    }

    @Override
    public AppAlert withYesNoCancelButtons() {
        return withYesNoCancelButtons(null, null, null);
    }

    @Override
    public AppAlert withYesNoCancelButtons(AppAlertAction yes, AppAlertAction no, AppAlertAction cancel) {
        buttonIds = Arrays.asList("yes", "no", "cancel");
        setButtonAction("yes", yes);
        setButtonAction("no", no);
        setButtonAction("cancel", cancel);
        if (defaultButton.get() == null) {
            defaultButton().set("yes");
        }
        return this;
    }

    @Override
    public AppAlert withButtons(String... buttonIds) {
        this.buttonIds = buttonIds == null ? null : Arrays.asList(buttonIds);
        for (String buttonId : buttonIds) {
            setButtonAction(buttonId, null);
        }
        return this;
    }

    @Override
    public AppAlert setDefaultButton(String defaultId) {
        this.defaultButton.set(defaultId);
        return this;
    }

    @Override
    public AppAlert setButtonAction(String s, AppAlertAction r) {
        if (r == null) {
            consMap.remove(s);
        } else {
            consMap.put(s, r);
        }
        return this;
    }

    @Override
    public AppAlertResult showDialog(AppComponent owner) {
        return peer().showDialog(owner);
    }

    @Override
    public void closeAlert() {
        peer().closeDialog();
    }

    //    public AppAlert build() {
//        if (peer == null) {
//            Str _titleId = title;
//            List<String> _buttonIds = buttonIds == null ? new ArrayList<>() : new ArrayList<>(buttonIds);
//            String _defaultId = defaultId;
//            if (_titleId == null) {
//                _titleId = Str.i18n("Message.defaultTitle");
//            }
//            if (_buttonIds.isEmpty()) {
//                _buttonIds.add("ok");
//            }
//            buttonIds = _buttonIds;
//            title = _titleId;
//            if (action == null) {
//                action = new AppAlertAction() {
//                    @Override
//                    public void onAction(AppDialogContext context) {
//                        AppAlertAction a = consMap.get(context.getButtonId());
//                        if (a != null) {
//                            a.onAction(context);
//                        }
//                    }
//                };
//            }
//            peer = (AppAlertPeer) app().toolkit().createComponentPeer(this);
//        }
//        return this;
//    }
    public AppAlertAction getAction(String buttonId) {
        return consMap.get(buttonId);
    }

    public AppAlertAction getAction() {
        return action;
    }

    public List<String> getButtonIds() {
        return buttonIds;
    }

    @Override
    public AppAlertPeer peer() {
        return (AppAlertPeer) super.peer();
    }
}
