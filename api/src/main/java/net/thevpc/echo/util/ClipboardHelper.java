package net.thevpc.echo.util;

import net.thevpc.common.props.Path;
import net.thevpc.echo.Application;
import net.thevpc.echo.Button;
import net.thevpc.echo.Frame;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.components.AppEditTextControl;
import net.thevpc.echo.api.components.AppMenu;

public class ClipboardHelper {
    public static void prepareMenu(Frame frame) {
        AppMenu e = (AppMenu) frame.children().addFolder(Path.of("/menuBar/Edit"));
        prepareContainer(e);
    }

    public static void prepareToolBar(Frame frame) {
        frame.findOrCreateToolBar("Edit", editToolBar -> {
            prepareContainer(editToolBar);
        });
    }

    public static void prepareContainer(AppContainer editToolBar) {
        Application app = editToolBar.app();
        editToolBar.children().add(new Button("copy", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = app.toolkit().focusOwner().get();
            if (fo instanceof AppEditTextControl) {
                AppEditTextControl t = (AppEditTextControl) fo;
                app.clipboard().putString(t.textSelection().get());
            }
        }, app).with(
                (Button x) -> app.toolkit().focusOwner().onChangeAndInit(() -> {
                    AppComponent fo = app.toolkit().focusOwner().get();
                    boolean copyEnabled = false;
                    if (fo instanceof AppEditTextControl) {
                        copyEnabled = true;
                    }
                    x.enabled().set(copyEnabled);
                    x.visible().set(
                            !app.hideDisabled().get() || copyEnabled
                    );
                })
        ));
        editToolBar.children().add(new Button("paste", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = app.toolkit().focusOwner().get();
            if (fo instanceof AppEditTextControl) {
                if (fo.editable().get()) {
                    AppEditTextControl t = (AppEditTextControl) fo;
                    t.replaceSelection(app.clipboard().getString());
                }
            }
        }, app).with(
                (Button x) -> app.toolkit().focusOwner().onChangeAndInit(() -> {
                    AppComponent fo = app.toolkit().focusOwner().get();
                    boolean copyEnabled = false;
                    if (fo instanceof AppEditTextControl) {
                        if (fo.editable().get()) {
                            copyEnabled = true;
                        }
                    }
                    x.enabled().set(copyEnabled);
                    x.visible().set(
                            !app.hideDisabled().get() || copyEnabled
                    );
                })
        ));
        editToolBar.children().add(new Button("cut", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = app.toolkit().focusOwner().get();
            if (fo instanceof AppEditTextControl) {
                if (fo.editable().get()) {
                    AppEditTextControl t = (AppEditTextControl) fo;
                    app.clipboard().putString(t.textSelection().get());
                    t.replaceSelection("");
                }
            }
        }, app).with(
                (Button x) -> app.toolkit().focusOwner().onChangeAndInit(() -> {
                    AppComponent fo = app.toolkit().focusOwner().get();
                    boolean copyEnabled = false;
                    if (fo instanceof AppEditTextControl) {
                        if (fo.editable().get()) {
                            copyEnabled = true;
                        }
                    }
                    x.enabled().set(copyEnabled);
                    x.visible().set(
                            !app.hideDisabled().get() || copyEnabled
                    );
                })
        ));
    }

}
