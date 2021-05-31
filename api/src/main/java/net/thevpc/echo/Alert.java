package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableString;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppDialogAction;
import net.thevpc.echo.api.AppDialogInputPane;
import net.thevpc.echo.api.AppDialogResult;
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
import java.util.function.Supplier;

public class Alert extends ControlBase implements AppAlert {

    protected WritableValue<AppComponent> content;
    protected Supplier<Object> valueEvaluator;
    protected List<String> buttonIds;
    protected WritableString defaultButton;
    protected WritableImage headerIcon;
    protected Map<String, AppDialogAction> consMap = new HashMap<>();
    protected AppDialogAction onClose = c -> c.getDialog().closeDialog();
    protected WritableStr headerText;
    protected AppDialogAction action = null;

    public Alert(AppComponent owner) {
        this(owner.app());
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
        propagateEvents(headerText);
    }

    public Alert(String id, Application app) {
        super(id, app, AppAlert.class, AppAlertPeer.class);
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
        setInputContent(new InputTextFieldPanel(app(), headerId, initialValue));
        return this;
    }

    @Override
    public AppAlert setInputTextAreaContent(Str headerId, Str initialValue) {
        setInputContent(new InputTextAreaPanel(app(), headerId, initialValue));
        return this;
    }

    public AppAlert setInputContent(AppDialogInputPane inputPanel) {
        AppComponent component = inputPanel.getComponent();
        this.valueEvaluator = inputPanel::getValue;
        setContent(component);
        return this;
    }

    @Override
    public WritableValue<AppComponent> content() {
        return content;
    }

    public AppAlert setContent(AppComponent mainComponent) {
        this.content.set(mainComponent);
        return this;
    }

    public AppAlert setContent(Object mainComponent) {
        this.content.set(
                mainComponent == null ? null : app().toolkit().createComponent(mainComponent)
        );
        return this;
    }

    public AppAlert setContentText(Str labelId) {
        AppLabel label = new Label(app());
        label.text().set(labelId);
        return setContent(label);
    }

    public AppAlert withOkOnlyButton() {
        return withOkOnlyButton(onClose);
    }

    public AppAlert withOkOnlyButton(AppDialogAction ok) {
        buttonIds = Arrays.asList("ok");
        consMap.put("ok", ok);
        if (defaultButton.get() == null) {
            defaultButton().set("ok");
        }
        return this;
    }

    public AppAlert withOkCancelButtons() {
        return withOkCancelButtons(onClose, onClose);
    }

    public AppAlert withOkCancelButtons(AppDialogAction ok, AppDialogAction cancel) {
        buttonIds = Arrays.asList("ok", "cancel");
        consMap.put("ok", ok);
        consMap.put("cancel", cancel);
        if (defaultButton.get() == null) {
            defaultButton().set("ok");
        }
        return this;
    }

    public AppAlert withYesNoButtons() {
        return withYesNoButtons(onClose, onClose);
    }

    public AppAlert withYesNoButtons(AppDialogAction yes, AppDialogAction no) {
        buttonIds = Arrays.asList("yes", "no");
        consMap.put("yes", yes);
        consMap.put("no", no);
        if (defaultButton.get() == null) {
            defaultButton().set("yes");
        }
        return this;
    }

    public AppAlert withYesNoCancelButtons() {
        return withYesNoCancelButtons(onClose, onClose, onClose);
    }

    public AppAlert withYesNoCancelButtons(AppDialogAction yes, AppDialogAction no, AppDialogAction cancel) {
        buttonIds = Arrays.asList("yes", "no", "cancel");
        consMap.put("yes", yes);
        consMap.put("no", no);
        consMap.put("cancel", cancel);
        if (defaultButton.get() == null) {
            defaultButton().set("yes");
        }
        return this;
    }

    public AppAlert withButtons(String... buttonIds) {
        this.buttonIds = buttonIds == null ? null : Arrays.asList(buttonIds);
        return this;
    }

    public AppAlert setDefaultButton(String defaultId) {
        this.defaultButton.set(defaultId);
        return this;
    }

    public AppAlert setButtonHandler(String s, AppDialogAction r) {
        consMap.put(s, r);
        return this;
    }

    public String showDialog(AppComponent owner) {
        return peer().showDialog(owner);
    }

    public AppDialogResult showInputDialog(AppComponent owner) {
        return peer().showInputDialog(owner);
    }

    @Override
    public void closeDialog() {
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
//                action = new AppDialogAction() {
//                    @Override
//                    public void onAction(AppDialogContext context) {
//                        AppDialogAction a = consMap.get(context.getButtonId());
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
    public AppDialogAction getAction(String buttonId) {
        return consMap.get(buttonId);
    }

    public AppDialogAction getAction() {
        return action;
    }

    public Supplier<Object> getValueEvaluator() {
        return valueEvaluator;
    }

    public List<String> getButtonIds() {
        return buttonIds;
    }

    public AppAlertPeer peer() {
        return (AppAlertPeer) super.peer();
    }

    @Override
    public AppAlertPeer peer(boolean prepareShowing) {
        return (AppAlertPeer) super.peer(prepareShowing);
    }
}
