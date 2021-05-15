package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.Str;
import net.thevpc.echo.api.components.AppAlert;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.api.tools.AppToolAlert;
import net.thevpc.echo.impl.dialog.InputTextAreaPanel;
import net.thevpc.echo.impl.dialog.InputTextFieldPanel;
import net.thevpc.echo.impl.tools.ToolAlert;
import net.thevpc.echo.api.peers.AppAlertPeer;

import java.util.*;
import java.util.function.Supplier;

public class Alert extends AppComponentBase implements AppAlert {
    protected Str title;
    protected List<Object> titleParameters = new ArrayList<>();
    protected AppComponent content;
    protected Supplier<Object> valueEvaluator;
    protected List<String> buttonIds;
    protected String defaultId;
    protected Map<String, AppDialogAction> consMap = new HashMap<>();
    protected AppDimension preferredSize;
    protected AppDialogAction onClose = c -> c.getDialog().closeDialog();
    AppDialogAction action = null;
    AppAlertPeer peer = null;


    public Alert(Application app) {
        super(new ToolAlert(app));
    }
    public Alert(AppToolAlert tool) {
        super(tool);
    }

    public AppDimension getPreferredSize() {
        return preferredSize;
    }

    public AppAlert setPreferredSize(AppDimension preferredSize) {
        this.preferredSize = preferredSize;
        return this;
    }

    public AppAlert setPreferredSize(int width, int heigth) {
        this.preferredSize = new AppDimension(width, heigth);
        return this;
    }

    public AppAlert setTitle(Str title, Object... params) {
        this.title = title;
        this.titleParameters.clear();
        this.titleParameters.addAll(Arrays.asList(params));
        return this;
    }

    @Override
    public AppAlert setInputTextFieldContent(Str headerId, Str initialValue) {
        setInputContent(new InputTextFieldPanel(app(),headerId, initialValue));
        return this;
    }

    @Override
    public AppAlert setInputTextAreaContent(Str headerId, Str initialValue) {
        setInputContent(new InputTextAreaPanel(app(),headerId, initialValue));
        return this;
    }

    public AppAlert setInputContent(AppDialogInputPanel inputPanel) {
        AppComponent component = inputPanel.getComponent();
        this.valueEvaluator = inputPanel::getValue;
        setContent(component);
        return this;
    }

    @Override
    public AppComponent getContent() {
        return content;
    }

    public AppAlert setContentText(Str labelId) {
        AppLabel label = new Label(app());
        label.tool().text().set(labelId);
        return setContent(label);
    }

    public AppAlert setContent(AppComponent mainComponent) {
        this.content = mainComponent;
        return this;
    }

    public AppAlert setContent(Object mainComponent) {
        this.content = mainComponent == null ? null : app().toolkit().createComponent(mainComponent);
        return this;
    }

    public AppAlert withOkOnlyButton() {
        return withOkOnlyButton(onClose);
    }

    public AppAlert withOkOnlyButton(AppDialogAction ok) {
        buttonIds = Arrays.asList("ok");
        consMap.put("ok", ok);
        if (defaultId == null) {
            setDefaultId("ok");
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
        if (defaultId == null) {
            setDefaultId("ok");
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
        if (defaultId == null) {
            setDefaultId("yes");
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
        if (defaultId == null) {
            setDefaultId("yes");
        }
        return this;
    }

    public AppAlert withButtons(String... buttonIds) {
        this.buttonIds = buttonIds == null ? null : Arrays.asList(buttonIds);
        return this;
    }

    public AppAlert setDefaultId(String defaultId) {
        this.defaultId = defaultId;
        return this;
    }

    public AppAlert setButtonHandler(String s, AppDialogAction r) {
        consMap.put(s, r);
        return this;
    }

    public String showDialog() {
        build();
        return peer.showDialog();
    }

    public AppDialogResult showInputDialog() {
        return build().showInputDialog();
    }

    @Override
    public void closeDialog() {
        build();
        peer.closeDialog();
    }

    public AppAlert build() {
        if (peer == null) {
            Str _titleId = title;
            List<String> _buttonIds = buttonIds == null ? new ArrayList<>() : new ArrayList<>(buttonIds);
            String _defaultId = defaultId;
            if (_titleId == null) {
                _titleId = Str.i18n("Message.defaultTitle");
            }
            if (_buttonIds.isEmpty()) {
                _buttonIds.add("ok");
            }
            buttonIds = _buttonIds;
            title = _titleId;
            if (action == null) {
                action = new AppDialogAction() {
                    @Override
                    public void onAction(AppDialogContext context) {
                        AppDialogAction a = consMap.get(context.getButtonId());
                        if (a != null) {
                            a.onAction(context);
                        }
                    }
                };
            }
            peer = (AppAlertPeer) app().toolkit().createComponentPeer(this);
        }
        return this;
    }


    public Str getTitle() {
        return title;
    }

    public AppDialogAction getAction() {
        return action;
    }

    public List<Object> getTitleParameters() {
        return titleParameters;
    }

    public Supplier<Object> getValueEvaluator() {
        return valueEvaluator;
    }

    public List<String> getButtonIds() {
        return buttonIds;
    }

    public String getDefaultId() {
        return defaultId;
    }

    @Override
    public AppToolAlert tool() {
        return (AppToolAlert) super.tool();
    }
}
