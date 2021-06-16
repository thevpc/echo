package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.SwingComponentUtils;
import net.thevpc.echo.UncheckedException;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTextArea;
import net.thevpc.echo.spi.peers.AppTextAreaPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.peers.htmleditor.SwingRichHtmlEditorPeer;
import net.thevpc.jeep.editor.ColorResource;
import net.thevpc.jeep.editor.JEditorPaneBuilder;
import net.thevpc.jeep.editor.JSyntaxStyleManager;
import net.thevpc.jeep.editorkits.AvailableEditorKits;
import net.thevpc.more.shef.ShefHelper;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwingTextAreaPeer implements SwingPeer, AppTextAreaPeer {
//    private JTextComponent jTextComponent;

    private JEditorPaneBuilder editorBuilder;
    private AppTextArea appTextArea;

    public SwingTextAreaPeer() {
    }

    public void install(AppComponent component) {
        appTextArea = (AppTextArea) component;
        JEditorPane editor = new JEditorPane();
        for (Map.Entry<String, Supplier<EditorKit>> ee : AvailableEditorKits.getAvailable().entrySet()) {
            editor.setEditorKitForContentType(ee.getKey(), ee.getValue().get());
        }
        appTextArea.textContentType().onChangeAndInit(() -> {
            String contentType = appTextArea.textContentType().get();
            if (contentType == null) {
                contentType = "text/plain";
            }
            editor.setContentType(contentType);
        });
        editorBuilder = new JEditorPaneBuilder().setEditor(editor);
        this.editorBuilder.setNoScroll(true);
        this.editorBuilder.editor().setFont(JSyntaxStyleManager.getDefaultFont());
        if (appTextArea.rowNumberRuler().get()) {
            editorBuilder.addLineNumbers();
        }
        SwingPeerHelper.installTextComponent(appTextArea, editorBuilder.editor());

        SwingPeerHelper.installComponent(appTextArea, this.editorBuilder.editor());

        editorBuilder.editor().addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent evt) {
                appTextArea.caretPosition().set(evt.getDot());
                if (evt.getDot() == evt.getMark()) {
                    return;
                }
                if (appTextArea.highlightSelectionDuplicates().get()) {

                    JEditorPane txtPane = (JEditorPane) evt.getSource();
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
                            AppColor cc = appTextArea.highlightSelectionDuplicatesColor().get();

                            for (int[] is : all) {
                                highlight(is[0], is[1], cc, "duplicates");
                            }
                        }
                    }
                }
            }
        });
        editorBuilder.editor().addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (appTextArea.zoomOnMouseWheel().get()) {
                    SwingComponentUtils.evalZoomTextOnMouseWheel(e, editorBuilder.editor());
                }
            }
        });
    }

    @Override
    public Object toolkitComponent() {
        return editorBuilder.component();
    }

//    public void setCaretPosition(int pos) {
//        editorBuilder.editor().setCaretPosition(pos);
//    }
    @Override
    public void removeAllHighlights(Object userObjectKey) {
        editorBuilder.editor().getHighlighter().removeAllHighlights();
    }

    public void highlight(int from, int to, AppColor c, Object userObjectKey) {
        highlight(from, to, c == null ? null : new Color(c.rgba()));
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

    @Override
    public void registerAccelerator(String actionId, String accelerator, Runnable action) {
        editorBuilder.editor().getActionMap().put("custom-" + actionId, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
        editorBuilder.editor().getInputMap().put(KeyStroke.getKeyStroke(accelerator), "custom-" + actionId);

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

    public void highlight(int from, int to, Color c) {
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

    public void runAction(String action) {
        Action a = editorBuilder.editor().getActionMap().get("action");
        if (a != null) {
            a.actionPerformed(newEvent(action));
        }
    }

    private ActionEvent newEvent(String action) {
        return new ActionEvent(this, 0, action);
    }

    @Override
    public void runSelectLine() {
        runAction("select-line");
    }

    @Override
    public void replaceSelection(String newValue) {
        editorBuilder.editor().replaceSelection(newValue);
    }
}
