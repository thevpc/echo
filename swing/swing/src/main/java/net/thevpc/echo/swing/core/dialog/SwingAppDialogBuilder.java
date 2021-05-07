/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core.dialog;

import net.thevpc.echo.AppDialogResult;
import net.thevpc.echo.AppDialogInputPanel;
import net.thevpc.echo.AppDialogAction;
import net.thevpc.echo.AppDialogBuilder;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JLabel;
import net.thevpc.echo.AppDialogContext;
import net.thevpc.echo.Application;

/**
 *
 * @author vpc
 */
public class SwingAppDialogBuilder implements AppDialogBuilder {

    private Application app;
    private String titleId;
    private List<Object> titleParameters=new ArrayList<>();
    private JComponent mainComponent;
    private List<String> buttonIds;
    private String defaultId;
    private Map<String, AppDialogAction> consMap = new HashMap<>();
    private Dimension preferredSize;
    private AppDialogAction onClose = c -> c.getDialog().closeDialog();

    public SwingAppDialogBuilder(Application app) {
        this.app = app;
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    public SwingAppDialogBuilder setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
        return this;
    }

    public SwingAppDialogBuilder setPreferredSize(int width, int heigth) {
        this.preferredSize = new Dimension(width, heigth);
        return this;
    }

    public SwingAppDialogBuilder setTitleId(String titleId,Object ...params) {
        this.titleId = titleId;
        this.titleParameters.clear();
        this.titleParameters.addAll(Arrays.asList(params));
        return this;
    }

    public SwingAppDialogBuilder setInputTextFieldContent(String headerId, String initialValue) {
        return setInputContent(new SwingAppDialog.InputTextFieldPanel(app, headerId, initialValue));
    }

    public SwingAppDialogBuilder setInputTextAreadContent(String headerId, String initialValue) {
        return setInputContent(new SwingAppDialog.InputTextAreaPanel(app, headerId, initialValue));
    }

    public SwingAppDialogBuilder setInputContent(AppDialogInputPanel inputPanel) {
        return setContent((JComponent) inputPanel);
    }

    public SwingAppDialogBuilder setContentText(String labelId) {
        return setContent(new JLabel(labelId));
    }

    public SwingAppDialogBuilder setContentTextId(String labelId) {
        return setContent(new JLabel(app.i18n().getString(labelId)));
    }

    public SwingAppDialogBuilder setContent(JComponent mainComponent) {
        this.mainComponent = mainComponent;
        return this;
    }

    public SwingAppDialogBuilder withOkOnlyButton() {
        return withOkOnlyButton(onClose);
    }

    public SwingAppDialogBuilder withOkOnlyButton(AppDialogAction ok) {
        buttonIds = Arrays.asList("ok");
        consMap.put("ok", ok);
        if (defaultId == null) {
            setDefaultId("ok");
        }
        return this;
    }

    public SwingAppDialogBuilder withOkCancelButtons() {
        return withOkCancelButtons(onClose, onClose);
    }

    public SwingAppDialogBuilder withOkCancelButtons(AppDialogAction ok, AppDialogAction cancel) {
        buttonIds = Arrays.asList("ok", "cancel");
        consMap.put("ok", ok);
        consMap.put("cancel", cancel);
        if (defaultId == null) {
            setDefaultId("ok");
        }
        return this;
    }

    public SwingAppDialogBuilder withYesNoButtons() {
        return withYesNoButtons(onClose, onClose);
    }

    public SwingAppDialogBuilder withYesNoButtons(AppDialogAction yes, AppDialogAction no) {
        buttonIds = Arrays.asList("yes", "no");
        consMap.put("yes", yes);
        consMap.put("no", no);
        if (defaultId == null) {
            setDefaultId("yes");
        }
        return this;
    }

    public SwingAppDialogBuilder withYesNoCancelButtons(AppDialogAction yes, AppDialogAction no, AppDialogAction cancel) {
        buttonIds = Arrays.asList("yes", "no", "cancel");
        consMap.put("yes", yes);
        consMap.put("no", no);
        consMap.put("cancel", cancel);
        if (defaultId == null) {
            setDefaultId("yes");
        }
        return this;
    }

    public SwingAppDialogBuilder withButtons(String... buttonIds) {
        this.buttonIds = buttonIds == null ? null : Arrays.asList(buttonIds);
        return this;
    }

    public SwingAppDialogBuilder setDefaultId(String defaultId) {
        this.defaultId = defaultId;
        return this;
    }

    public SwingAppDialogBuilder setButtonHandler(String s, AppDialogAction r) {
        consMap.put(s, r);
        return this;
    }

    public String showDialog() {
        return build().showDialog();
    }

    public AppDialogResult showInputDialog() {
        return build().showInputDialog();
    }

    public SwingAppDialog build() {
        String _titleId = titleId;
        JComponent _mainComponent = mainComponent;
        List<String> _buttonIds = buttonIds == null ? null : new ArrayList<>(buttonIds);
        String _defaultId = defaultId;
        AppDialogAction _cons = null;
        if (_titleId == null) {
            _titleId = "Message.defaultTitle";
        }
        if (_mainComponent == null) {
            _mainComponent = new JLabel();
        }
        if (_buttonIds.isEmpty()) {
            _buttonIds.add("ok");
        }
        if (_cons == null) {
            _cons = new AppDialogAction() {
                @Override
                public void onAction(AppDialogContext context) {
                    AppDialogAction a = consMap.get(context.getButtonId());
                    if (a != null) {
                        a.onAction(context);
                    }
                }
            };
        }
        SwingAppDialog a = new SwingAppDialog(app, _titleId,titleParameters);
        if (preferredSize != null) {
            a.setPreferredSize(preferredSize);
        }
        a.build(_mainComponent, _buttonIds.toArray(new String[0]), _defaultId, _cons);
        return a;
    }

}
