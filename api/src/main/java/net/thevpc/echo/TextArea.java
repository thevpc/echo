package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.*;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.components.AppTextArea;
import net.thevpc.echo.impl.components.TextBase;
import net.thevpc.echo.spi.peers.AppTextAreaPeer;

public class TextArea extends TextBase implements AppTextArea {
    private WritableString textSelection = Props.of("textSelection").stringOf("");
    private WritableInt caretPosition = Props.of("caretPosition").intOf(-1);
    private WritableBoolean zoomOnMouseWheel = Props.of("zoomOnMouseWheel").booleanOf(false);
    private WritableBoolean rowNumberRuler = Props.of("rowNumberRuler").booleanOf(false);
    private WritableBoolean highlightSelectionDuplicates = Props.of("highlightSelectionDuplicates").booleanOf(true);
    private WritableValue<AppColor> highlightSelectionDuplicatesColor = Props.of("highlightSelectionDuplicatesColor").valueOf(AppColor.class);


    public TextArea(Application app) {
        this(null,null,app);
    }
    public TextArea(Str str, Application app) {
        this(null, str, app);
    }

    public TextArea(String id, Str str, Application app) {
        super(id, str, app, AppTextArea.class, AppTextAreaPeer.class);
        propagateEvents(textSelection, caretPosition,
                highlightSelectionDuplicates, highlightSelectionDuplicatesColor,
                zoomOnMouseWheel,
                rowNumberRuler);
    }
    public void installDefaults(){
        registerAccelerator("reset-highlights","ESCAPE", () -> removeAllHighlights(null));
        registerAccelerator("duplicate-text","control D", this::runTextDuplicate);
    }

    public String getText(int from, int to) {
        return peer().getText(from, to);
    }

    public int getTextLength() {
        return peer().getTextLength();
    }

    public void removeAllHighlights(Object userKeyObject){
        peer().removeAllHighlights(userKeyObject);
    }

    public void highlight(int from, int to, AppColor c, Object userObjectKey){
        peer().highlight(from,to,c,userObjectKey);
    }

    public void runSelectLine(){
        peer().runSelectLine();
    }

    public void runTextDuplicate(){
        peer().runTextDuplicate();
    }

    public void registerAccelerator(String actionId, String accelerator, Runnable action){
        peer().registerAccelerator(actionId,accelerator,action);
    }

    @Override
    public AppTextAreaPeer peer() {
        return (AppTextAreaPeer) super.peer();
    }

    @Override
    public WritableBoolean rowNumberRuler() {
        return rowNumberRuler;
    }

    @Override
    public WritableBoolean zoomOnMouseWheel() {
        return zoomOnMouseWheel;
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
    public WritableBoolean highlightSelectionDuplicates() {
        return highlightSelectionDuplicates;
    }

    @Override
    public WritableValue<AppColor> highlightSelectionDuplicatesColor() {
        return highlightSelectionDuplicatesColor;
    }
}
