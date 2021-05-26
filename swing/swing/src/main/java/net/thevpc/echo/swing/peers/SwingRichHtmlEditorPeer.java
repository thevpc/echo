package net.thevpc.echo.swing.peers;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.swing.SwingComponentUtils;
import net.thevpc.echo.UncheckedException;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.TextAlignment;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppRichHtmlEditor;
import net.thevpc.echo.spi.peers.AppRichHtmlEditorPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;
import net.thevpc.jeep.editor.ColorResource;
import net.thevpc.jeep.editor.JEditorPaneBuilder;
import net.thevpc.more.shef.BlocEnum;
import net.thevpc.more.shef.ShefHelper;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwingRichHtmlEditorPeer implements SwingPeer, AppRichHtmlEditorPeer {

    //    private JInternalFrameHelper helper;
//    private InternalWindowsHelper desktop;
    private AppRichHtmlEditor appComponent;
    private JEditorPaneBuilder editorBuilder;
    DocumentListener documentListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            anyChange(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            anyChange(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            anyChange(e);
        }

        public void anyChange(DocumentEvent e) {
            appComponent.text().set(Str.of(editorBuilder.editor().getText()));
        }
    };

    @Override
    public void install(AppComponent comp) {
        if (this.appComponent == null) {
            this.appComponent = (AppRichHtmlEditor) comp;
            editorBuilder = new JEditorPaneBuilder().setEditor(ShefHelper.installMin(new JEditorPane("text/html", "")));
            final JEditorPane editor = editorBuilder.editor();
            for (BlocEnum e: new BlocEnum[]{
                    BlocEnum.H1,BlocEnum.H2,BlocEnum.H3,BlocEnum.H4,BlocEnum.H5,BlocEnum.H6,
                    BlocEnum.PRE,BlocEnum.DIV,BlocEnum.P,BlocEnum.BLOCKQUOTE,BlocEnum.OL,BlocEnum.UL
            }) {
                registerAction("insert-"+(e.name().toLowerCase()),()->ShefHelper.runInsertBloc(editor, e));
            }

            SwingPeerHelper.installTextComponent(appComponent, editor);
            SwingPeerHelper.installComponent(appComponent, editor);

            editor.addCaretListener(new CaretListener() {
                public void caretUpdate(CaretEvent evt) {
                    appComponent.caretPosition().set(evt.getDot());
                    if (evt.getDot() == evt.getMark()) {
                        return;
                    }
                    if (appComponent.highlightSelectionDuplicates().get()) {

                        JEditorPane txtPane = (JEditorPane) evt.getSource();
                        Highlighter highlighter = txtPane.getHighlighter();
                        String selText = txtPane.getSelectedText();
                        String contText = "";// = jTextPane1.getText();
                        Document document = txtPane.getDocument();
                        try {
                            contText = document.getText(0, document.getLength());
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                        if (contText.length() > 0) {
                            int index = 0;
                            List<int[]> all = new ArrayList<>();
                            while ((index = contText.indexOf(selText, index)) > -1) {
                                all.add(new int[]{index, selText.length() + index});
                                index = index + selText.length();
                            }
                            if (all.size() > 1) {
                                removeAllHighlights("duplicates");
                                AppColor cc = appComponent.highlightSelectionDuplicatesColor().get();

                                for (int[] is : all) {
                                    highlight(is[0], is[1], cc, "duplicates");
                                }
                            }
                        }
                    }
                }
            });
            editor.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    if (appComponent.zoomOnMouseWheel().get()) {
                        SwingComponentUtils.evalZoomTextOnMouseWheel(e, editor);
                    }
                }
            });
        }
    }

    @Override
    public Object toolkitComponent() {
        return editorBuilder.component();
    }

    public void highlight(int from, int to, Color c, Object Object) {
        try {
            if (c == null) {
                c = ColorResource.of(
                        "Button.default.focusColor;OptionPane.questionDialog.titlePane.background;OptionPane.questionDialog.titlePane.background;#green").get();
            }
            javax.swing.text.DefaultHighlighter.DefaultHighlightPainter highlightPainter
                    = new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(c);
            editorBuilder.editor().getHighlighter().addHighlight(from, to, highlightPainter);
        } catch (BadLocationException ex) {
            Logger.getLogger(SwingRichHtmlEditorPeer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void runTextBold() {
        runAction("font-bold");
    }

    @Override
    public void runTextItalic() {
        runAction("font-italic");
    }

    @Override
    public void runTextUnderline() {
        runAction("font-underline");
    }

    @Override
    public void runTextStrikeThrough() {
        runAction("custom-strike");
    }

    @Override
    public void runTextAlignment(TextAlignment value) {
        switch (value) {
            case LEFT: {
                runAction("left-justify");
                break;
            }
            case RIGHT: {
                runAction("right-justify");
                break;
            }
            case CENTER: {
                runAction("center-justify");
                break;
            }
            case JUSTIFY: {
                runAction("full-justify");
                break;
            }
        }
    }

    //    public void setCaretPosition(int pos){
//        editorBuilder.editor().setCaretPosition(pos);
//    }
    @Override
    public void removeAllHighlights(Object userObjectKey) {
        editorBuilder.editor().getHighlighter().removeAllHighlights();
    }

    public void highlight(int from, int to, AppColor c, Object userObjectKey) {
        highlight(from, to, c == null ? null : new Color(c.rgba()), userObjectKey);
    }

    @Override
    public void runSelectLine() {
        runAction("select-line");
    }

    @Override
    public void runTextDuplicate() {
        JEditorPane editor = editorBuilder.editor();
        int ss = editor.getSelectionStart();
        int se = editor.getSelectionEnd();
        if (se > ss) {
            ShefHelper.runCopy(editor);
            editor.select(se, se);
            ShefHelper.runPaste(editor);
        } else {
            Action a = editor.getActionMap().get("select-line");
            if (a != null) {
                a.actionPerformed(newEvent("select-line"));
                ShefHelper.runCopy(editor);
                editor.select(se, se);
                ShefHelper.runPaste(editor);
            }
        }
    }

    public void registerAction(String actionId, Runnable action) {
        editorBuilder.editor().getActionMap().put(actionId, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    public void registerAccelerator(String actionId, String accelerator, Runnable action) {
        registerAction("custom-" + actionId,action);
        editorBuilder.editor().getInputMap().put(KeyStroke.getKeyStroke(accelerator), "custom-" + actionId);

    }

    @Override
    public boolean runAction(String action) {
        Action a = editorBuilder.editor().getActionMap().get(action);
        if (a != null) {
            a.actionPerformed(newEvent(action));
            return true;
        }
        return false;
    }

    @Override
    public String getText(int from, int to) {
        try {
            return editorBuilder.editor().getDocument().getText(from, to - from);
        } catch (BadLocationException e) {
            throw UncheckedException.wrap(e);
        }
    }

    @Override
    public int getTextLength() {
        return editorBuilder.editor().getDocument().getLength();
    }

    @Override
    public void replaceSelection(String newValue) {
        editorBuilder.editor().replaceSelection(newValue);
    }

    @Override
    public void runTextForegroundColor(AppColor color) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, SwingHelpers.toAwtColor(color));
        SwingComponentUtils.setCharacterAttributes(editorBuilder.editor(), attr, false);
    }

    @Override
    public void runTextBackgroundColor(AppColor color) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setBackground(attr, SwingHelpers.toAwtColor(color));
        SwingComponentUtils.setCharacterAttributes(editorBuilder.editor(), attr, false);
    }

    private ActionEvent newEvent(String action) {
        return new ActionEvent(this, 0, action);
    }

    @Override
    public void requestFocus() {
        editorBuilder.editor().requestFocus();
    }
    
}
