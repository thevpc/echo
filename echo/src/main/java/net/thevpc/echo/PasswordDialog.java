/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.Label;
import net.thevpc.echo.Panel;
import net.thevpc.echo.TextField;
import net.thevpc.echo.*;
import net.thevpc.echo.api.CancelException;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.constraints.AllFill;
import net.thevpc.echo.constraints.AllMargins;
import net.thevpc.echo.constraints.Grow;
import net.thevpc.echo.constraints.ContainerGrow;

/**
 * @author thevpc
 */
public class PasswordDialog {

    private Panel panel;
    private PasswordField typePasswordComponent;
    private PasswordField retypePasswordComponent;
    private AppComponent owner;
    private PasswordErrorHandler passwordErrorHandler;
    private Str lockedItemName;
    private Object lockedItem;

    private boolean ok = false;
    private Label typePasswordLabel;
    private Label retypePasswordLabel;
    private Label lockedItemLabel;
    private TextField lockedItemText;
    private boolean retypePassword;

    public PasswordDialog(AppComponent owner, Object lockedItem,
            Str lockedItemName,
            boolean retypePassword,
            PasswordErrorHandler passwordErrorHandler) {
        this.retypePassword = retypePassword;
        this.lockedItem = lockedItem;
        this.lockedItemName = lockedItemName;
        this.passwordErrorHandler = passwordErrorHandler;
        this.owner = owner;
        typePasswordComponent = new PasswordField(owner.app());
        Str lockedItemLabelText =
                retypePassword?
                        Str.i18n("PasswordDialog.createPassword.forItem"):
                        Str.i18n("PasswordDialog.typePassword.forItem")

                ;
        typePasswordLabel = new Label(Str.i18n("PasswordDialog.typePassword"), owner.app());

        retypePasswordComponent = new PasswordField(owner.app());
        retypePasswordLabel = new net.thevpc.echo.Label(Str.i18n("PasswordDialog.retypePassword"), owner.app());
        retypePasswordComponent.visible().set(retypePassword);
        retypePasswordLabel.visible().set(retypePassword);

        lockedItemLabel = new Label(lockedItemLabelText, owner.app());
        lockedItemText = new TextField(owner.app())
                .with((TextField t) -> {
                    t.editable().set(false);
                    t.text().set(lockedItemName);
                    t.childConstraints().add(Grow.HORIZONTAL);
                });
        lockedItemLabel.visible().set(lockedItem != null);
        lockedItemText.visible().set(lockedItem != null);
        panel = new GridPane(1, owner.app())
                .with(p -> {
                    p.parentConstraints().addAll(AllMargins.of(3), AllFill.HORIZONTAL, ContainerGrow.TOP_ROW);
                    p.children().addAll(
                            typePasswordLabel,
                            typePasswordComponent,
                            retypePasswordLabel,
                            retypePasswordComponent,
                            lockedItemLabel, lockedItemText);
                });
    }

    public Str getLockedItemName() {
        return lockedItemName;
    }

    public Object getLockedItem() {
        return lockedItem;
    }

    protected void install() {
    }

    protected void uninstall() {

    }

    protected void ok() {
        uninstall();
        this.ok = true;
    }

    protected void cancel() {
        uninstall();
        this.ok = false;
    }

    public boolean showAskRetypePassword() {
        return new Alert(owner)
                .with((Alert a) -> {
                    a.title().set(Str.i18n("PasswordDialog.invalidPassword.askRetype"));
                    a.headerText().set(Str.i18n("PasswordDialog.invalidPassword.askRetype"));
                    a.headerIcon().set(Str.of("error"));
                })
                .setContentText(Str.i18n("PasswordDialog.invalidPassword.askRetype"))
                .withYesNoButtons()
                .showDialog().isYesButton();
    }

    public String showDialog() {
        passwordErrorHandler.onStartAskPassword(this);
        while (true) {
            install();
            this.ok = false;
            new Alert(owner)
                    .with((Alert a) -> {
                        a.title().set(
                                retypePassword?
                                        Str.i18n("PasswordDialog.createPassword.title"):
                                        Str.i18n("PasswordDialog.typePassword.title")
                        );
                        a.headerText().set(
                                retypePassword?
                                        Str.i18n("PasswordDialog.createPassword.header"):
                                        Str.i18n("PasswordDialog.typePassword.header")
                        );
                        a.headerIcon().set(retypePassword?Str.of("lock"):Str.of("unlock"));
                        a.content().set(panel);
                        a.withOkCancelButtons(
                                (b) -> {
                                    ok();
                                    b.getAlert().closeAlert();
                                },
                                (b) -> {
                                    cancel();
                                    b.getAlert().closeAlert();
                                }
                        );
                    })
                    .showDialog();
            try {
                return get();
            } catch (Exception ex) {
                if (!passwordErrorHandler.onPasswordError(this, ex)) {
                    throw new CancelException();
                }
                if (!showAskRetypePassword()) {
                    throw new CancelException();
                }
            }
        }
    }

    public String get() {
        if (ok) {
            String s1 = typePasswordComponent.text().get().value();
            if (s1 != null && s1.trim().length() > 0) {
                if (retypePassword) {
                    String s2 = retypePasswordComponent.text().get().value();
                    if (s1.equals(s2)) {
                        return s1;
                    }
                    throw new IllegalArgumentException(owner.app().i18n().getString("PasswordDialog.passwordsDoNotMatch"));
                }
                return s1;
            }
            throw new IllegalArgumentException("missing password");
        }
        return null;
    }

}
