package net.thevpc.echo.util;

import net.thevpc.common.props.Path;
import net.thevpc.echo.Application;
import net.thevpc.echo.Button;
import net.thevpc.echo.Frame;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.impl.Applications;

public class ClipboardHelper {

    public static void prepareMenu(Frame frame, boolean prefixSeparator, boolean suffixSeparator) {
        AppMenu e = (AppMenu) frame.children().addFolder(Path.of("/menuBar/Edit"));
        prepareContainer(e, prefixSeparator, suffixSeparator);
    }

    public static void prepareToolBar(Frame frame, boolean prefixSeparator, boolean suffixSeparator) {
        frame.findOrCreateToolBar("Edit", editToolBar -> {
            prepareContainer(editToolBar, prefixSeparator, suffixSeparator);
        });
    }

    public static void prepareContainer(AppContainer editToolBar, boolean prefixSeparator, boolean suffixSeparator) {
        if (editToolBar.userObjects().get(ClipboardHelper.class) != null) {
            return;
        }
        editToolBar.userObjects().put(ClipboardHelper.class, true);
        Application app = editToolBar.app();
        AppFrame frame = Applications.frameOf(editToolBar);
        if (frame == null) {
            throw new IllegalArgumentException("missing frame");
        }
        EnableIfTextControl enableIfTextControl = new EnableIfTextControl(frame);
        if (prefixSeparator) {
            editToolBar.children().addSeparator().with(enableIfTextControl);
        }
        editToolBar.children().add(new Button("copy", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = app.toolkit().focusOwner().get();
            if (fo instanceof AppEditTextControl) {
                AppEditTextControl t = (AppEditTextControl) fo;
                app.clipboard().putString(t.textSelection().get());
            }
        }, app).with(enableIfTextControl));
        editToolBar.children().add(new Button("paste", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = app.toolkit().focusOwner().get();
            if (fo instanceof AppEditTextControl) {
                if (fo.editable().get()) {
                    AppEditTextControl t = (AppEditTextControl) fo;
                    t.replaceSelection(app.clipboard().getString());
                }
            }
        }, app).with(enableIfTextControl));
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
        }, app).with(enableIfTextControl));
        if (suffixSeparator) {
            editToolBar.children().addSeparator().with(enableIfTextControl);
        }
    }

}
