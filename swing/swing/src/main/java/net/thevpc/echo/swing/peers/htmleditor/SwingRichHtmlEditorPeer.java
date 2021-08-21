package net.thevpc.echo.swing.peers.htmleditor;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.swing.SwingComponentUtils;
import net.thevpc.echo.FontPosture;
import net.thevpc.echo.FontWeight;
import net.thevpc.echo.UncheckedException;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.TextAlignment;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppRichHtmlEditor;
import net.thevpc.echo.model.InsertImageInfo;
import net.thevpc.echo.model.InsertTableInfo;
import net.thevpc.echo.spi.peers.AppRichHtmlEditorPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;
import net.thevpc.echo.swing.peers.SwingPeer;
import net.thevpc.jeep.editor.ColorResource;
import net.thevpc.jeep.editor.JEditorPaneBuilder;
import net.thevpc.more.shef.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SwingRichHtmlEditorPeer implements SwingPeer, AppRichHtmlEditorPeer {

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

    private static JEditorPane installMin(JEditorPane editor) {
        editor.setContentType("text/html");
        ShefHelper.init(editor);
        SwingRichHtmlEditorKit k = new SwingRichHtmlEditorKit();
        editor.setEditorKitForContentType("text/html", k);
        k.addInstallHelper(new EchoHTMLEditorKitInstallHelper());
        editor.setContentType("text/plain");
        editor.setContentType("text/html");
        return editor;
    }

    @Override
    public void install(AppComponent comp) {
        if (this.appComponent == null) {
            this.appComponent = (AppRichHtmlEditor) comp;
            editorBuilder = new JEditorPaneBuilder().setEditor(installMin(new JEditorPane("text/html", "")));
            final JEditorPane editor = editorBuilder.editor();
            for (BlocEnum e : new BlocEnum[]{
                    BlocEnum.H1, BlocEnum.H2, BlocEnum.H3, BlocEnum.H4, BlocEnum.H5, BlocEnum.H6,
                    BlocEnum.PRE, BlocEnum.DIV, BlocEnum.P, BlocEnum.BLOCKQUOTE, BlocEnum.OL, BlocEnum.UL
            }) {
                registerAction("insert-" + (e.name().toLowerCase()), () -> ShefHelper.runInsertBloc(editor, e));
            }
            registerAction("insert-hr", () -> ShefHelper.runInsertHorizontalRule(editorBuilder.editor()));

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
    public void runTextFont(AppFont font) {
        if (font != null) {
            ShefHelper.runChangeFont(editorBuilder.editor(),
                    new FontDesc(
                            font.family(),
                            (int) font.size(),
                            font.weight().ordinal() >= FontWeight.BOLD.ordinal(),
                            font.posture() == FontPosture.ITALIC,
                            false
                    )
            );
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
        runAction("font-strike-through");
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

    public void registerAccelerator(String actionId, String accelerator, Runnable action) {
        registerAction("custom-" + actionId, action);
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
        StyleConstants.setForeground(attr, SwingHelpers.toAwtColor(color, editorBuilder.editor(), SwingHelpers.DEF_COLOR_FOREGROUND));
        SwingComponentUtils.setCharacterAttributes(editorBuilder.editor(), attr, false);
    }

    @Override
    public void runTextBackgroundColor(AppColor color) {
        MutableAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setBackground(attr, SwingHelpers.toAwtColor(color, editorBuilder.editor(), SwingHelpers.DEF_COLOR_BACKGROUND));
        SwingComponentUtils.setCharacterAttributes(editorBuilder.editor(), attr, false);
    }

    @Override
    public void runInsertImage(InsertImageInfo info) {
        StringBuilder sb = new StringBuilder();
        sb.append("<img ");
        String u = info.getImageURL();
        if(u!=null){
            if(u.startsWith("/")|| u.startsWith("\\") || u.matches("[A-Za-z]:.*")){
                u="file:"+u;
            }
        }
        sb.append(" src=").append(quoted(u));
        if (info.getText() != null) {
            sb.append(" text=").append(quoted(info.getText()));
        }
        if (info.getWidth() != null && info.getWidth().intValue() > 0) {
            sb.append(" width=").append(quoted(String.valueOf(info.getWidth())));
        }
        if (info.getHeight() != null && info.getHeight().intValue() > 0) {
            sb.append(" height=").append(quoted(String.valueOf(info.getHeight())));
        }
        sb.append(">");
        ShefHelper.runInsertImage(editorBuilder.editor(), sb.toString());
    }

    @Override
    public void runDeleteTable() {
        JEditorPane ed = editorBuilder.editor();
        String ta1 = EchoHTMLEditorKitInstallHelper.getDocumentText(ed.getDocument());
        String ta2 = EchoHTMLEditorKitInstallHelper.getDocumentText2(ed.getDocument());
        ShefHelper.runDeleteTable(ed);
        ((SwingRichHtmlEditorKit)ed.getEditorKit()).updateMonitoredViews((HTMLDocument) ed.getDocument());
        String tb1 = EchoHTMLEditorKitInstallHelper.getDocumentText(ed.getDocument());
        String tb2 = EchoHTMLEditorKitInstallHelper.getDocumentText2(ed.getDocument());
    }

    @Override
    public void runInsertTable(InsertTableInfo info) {
        int columns = info.getColumns();
        int rows = info.getRows();
        if (columns < 1) {
            columns = 1;
        }
        if (rows < 1) {
            rows = 1;
        }
        StringBuilder sb = new StringBuilder();
        if (info.getStyleClass() == null) {
            sb.append("<table>");
        } else {
            sb.append("<table style=").append(quoted(info.getStyleClass())).append(">");
        }
        for (int r = 0; r < rows; r++) {
            sb.append("<tr>");
            for (int c = 0; c < columns; c++) {
                if (r == 0) {
                    sb.append("<th>");
                    sb.append("</th>");
                } else {
                    sb.append("<td>");
                    sb.append("</td>");
                }
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        ShefHelper.runInsertTable(editorBuilder.editor(), sb.toString());
    }

    @Override
    public void runInsertTableRow() {
        ShefHelper.runInsertTableRow(editorBuilder.editor());
    }

    @Override
    public void runInsertTableColumn() {
        ShefHelper.runInsertTableCol(editorBuilder.editor());
    }

    @Override
    public void runDeleteTableRow() {
        ShefHelper.runDeleteTableRow(editorBuilder.editor());
    }

    @Override
    public void runDeleteTableColumn() {
        ShefHelper.runDeleteTableCol(editorBuilder.editor());
    }

    public void registerAction(String actionId, Runnable action) {
        editorBuilder.editor().getActionMap().put(actionId, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private String quoted(String any) {
        StringBuilder sb = new StringBuilder("\"");
        for (char c : any.toCharArray()) {
            if (c == '\"') {
                sb.append("\\\"");
            } else if (c == '\\') {
                sb.append("\\\\");
            } else {
                sb.append(c);
            }
        }
        sb.append("\"");
        return sb.toString();
    }

    private ActionEvent newEvent(String action) {
        return new ActionEvent(this, 0, action);
    }

    @Override
    public void requestFocus() {
        editorBuilder.editor().requestFocus();
    }

}
