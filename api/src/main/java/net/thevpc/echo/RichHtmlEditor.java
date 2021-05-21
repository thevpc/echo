package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.*;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppRichHtmlEditor;
import net.thevpc.echo.api.TextAlignment;
import net.thevpc.echo.impl.components.TextBase;
import net.thevpc.echo.spi.peers.AppRichHtmlEditorPeer;

public class RichHtmlEditor extends TextBase implements AppRichHtmlEditor {

    private WritableString textSelection = Props.of("textSelection").stringOf("");
    private WritableInt caretPosition = Props.of("caretPosition").intOf(-1);
    private WritableBoolean zoomOnMouseWheel = Props.of("zoomOnMouseWheel").booleanOf(false);
    private WritableBoolean highlightSelectionDuplicates = Props.of("highlightSelectionDuplicates").booleanOf(true);
    private WritableValue<AppColor> highlightSelectionDuplicatesColor = Props.of("highlightSelectionDuplicatesColor").valueOf(AppColor.class);


    public RichHtmlEditor(String str, Application app) {
        this(null, str, app);
    }

    public RichHtmlEditor(String id, String str, Application app) {
        super(id, Str.of(str), app, AppRichHtmlEditor.class, AppRichHtmlEditorPeer.class);
        propagateEvents(textSelection, caretPosition, highlightSelectionDuplicates, highlightSelectionDuplicatesColor, zoomOnMouseWheel);
    }

    public RichHtmlEditor(Application app) {
        this(null,null,app);
    }

    public AppRichHtmlEditorPeer peer() {
        return (AppRichHtmlEditorPeer) super.peer();
    }

    public void installDefaults() {
        registerAccelerator("bold", "control B", this::runTextBold);
        registerAccelerator("italic", "control I", this::runTextItalic);
        registerAccelerator("underline", "control U", this::runTextUnderline);
        registerAccelerator("strike", "control K", this::runTextStrikeThrough);
        registerAccelerator("left-justify", "control L", () -> runTextAlignment(TextAlignment.LEFT));
        registerAccelerator("right-justify", "control R", () -> runTextAlignment(TextAlignment.RIGHT));
        registerAccelerator("center-justify", "control E", () -> runTextAlignment(TextAlignment.CENTER));
        registerAccelerator("justify-justify", "control G", () -> runTextAlignment(TextAlignment.JUSTIFY));
        registerAccelerator("reset-highlights", "ESCAPE", () -> removeAllHighlights(null));
        registerAccelerator("duplicate-text", "control D", this::runTextDuplicate);
    }

    public void removeAllHighlights(Object userObjectKey) {
        peer().removeAllHighlights(userObjectKey);
    }

    public void highlight(int from, int to, AppColor c, Object userObjectKey) {
        peer().highlight(from, to, c, userObjectKey);
    }

    public void runTextBold() {
        peer().runTextBold();
    }

    public void runTextItalic() {
        peer().runTextItalic();
    }

    public void runTextUnderline() {
        peer().runTextUnderline();
    }

    public void runTextStrikeThrough() {
        peer().runTextStrikeThrough();
    }

    public void runSelectLine() {
        peer().runSelectLine();
    }

    public void runTextAlignment(TextAlignment value) {
        peer().runTextAlignment(value);
    }

    public void runTextDuplicate() {
        peer().runTextDuplicate();
    }

    public void registerAccelerator(String actionId, String accelerator, Runnable action) {
        peer().registerAccelerator(actionId, accelerator, action);
    }

    @Override
    public WritableString textSelection() {
        return textSelection;
    }

    @Override
    public WritableInt caretPosition() {
        return caretPosition;
    }

    @Override
    public WritableBoolean zoomOnMouseWheel() {
        return zoomOnMouseWheel;
    }

    @Override
    public WritableBoolean highlightSelectionDuplicates() {
        return highlightSelectionDuplicates;
    }

    @Override
    public WritableValue<AppColor> highlightSelectionDuplicatesColor() {
        return highlightSelectionDuplicatesColor;
    }

    @Override
    public String getText(int from, int to) {
        return peer().getText(from, to);
    }

    @Override
    public int getTextLength() {
        return peer().getTextLength();
    }

}
