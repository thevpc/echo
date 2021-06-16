package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.*;
import net.thevpc.echo.api.components.AppRichHtmlEditor;
import net.thevpc.echo.constraints.*;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.components.EditTextBase;
import net.thevpc.echo.model.InsertImageInfo;
import net.thevpc.echo.model.InsertTableInfo;
import net.thevpc.echo.spi.peers.AppRichHtmlEditorPeer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class RichHtmlEditor extends EditTextBase implements AppRichHtmlEditor {

    private WritableBoolean zoomOnMouseWheel = Props.of("zoomOnMouseWheel").booleanOf(false);
    private WritableBoolean highlightSelectionDuplicates = Props.of("highlightSelectionDuplicates").booleanOf(true);
    private WritableValue<AppColor> highlightSelectionDuplicatesColor = Props.of("highlightSelectionDuplicatesColor").valueOf(AppColor.class);

    public RichHtmlEditor(String str, Application app) {
        this(null, str, app);
    }

    public RichHtmlEditor(String id, String str, Application app) {
        super(id, Str.of(str), app, AppRichHtmlEditor.class, AppRichHtmlEditorPeer.class);
        propagateEvents(highlightSelectionDuplicates, highlightSelectionDuplicatesColor, zoomOnMouseWheel);
    }

    public RichHtmlEditor(Application app) {
        this(null, null, app);
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

    public void runTextFont(AppFont font) {
        peer().runTextFont(font);
    }

    public void runTextBold() {
        peer().runTextBold();
    }

    public void runTextSup() {
        peer().runAction("text-sup");
    }

    public void runTextSub() {
        peer().runAction("text-sub");
    }

    public void runTextInsertTag(String type) {
        peer().runAction("insert-" + type.toLowerCase());
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

    public void runTextForegroundColor(AppColor color) {
        peer().runTextForegroundColor(color);
    }

    public void runTextBackgroundColor(AppColor color) {
        peer().runTextBackgroundColor(color);
    }

    public void runInsertImage() {
        AppAlertResult r = new Alert(this)
                .with((Alert t) -> {
                    t.title().set(Str.i18n("RichHtmlEditor.InsertImagePane.title"));
                    t.headerText().set(Str.i18n("RichHtmlEditor.InsertImagePane.header"));
                    t.headerIcon().set(Str.of("image"));
                    t.withOkCancelButtons();
                    t.content().set(new InsertImagePane(app()));
                })
                .showDialog();
        if (r.isOkButton()) {
            runInsertImage(r.value());
        }
    }

    public void runInsertTable() {
        AppAlertResult r = new Alert(this)
                .with((Alert t) -> {
                    t.title().set(Str.i18n("RichHtmlEditor.InsertTablePane.title"));
                    t.headerText().set(Str.i18n("RichHtmlEditor.InsertTablePane.header"));
                    t.headerIcon().set(Str.of("table"));
                    t.withOkCancelButtons();
                    t.content().set(new InsertTablePane(app()));
                })
                .showDialog();
        if (r.isOkButton()) {
            runInsertTable(r.value());
        }
    }

    public void runInsertTable(InsertTableInfo info) {
        peer().runInsertTable(info);
    }

    public void runInsertImage(InsertImageInfo info) {
        peer().runInsertImage(info);
    }

    public void runDeleteTable() {
        peer().runDeleteTable();
    }

    public void runInsertTableRow() {
        peer().runInsertTableRow();
    }

    public void runInsertTableColumn() {
        peer().runInsertTableColumn();
    }

    public void runDeleteTableRow() {
        peer().runDeleteTableRow();
    }

    public void runDeleteTableColumn() {
        peer().runDeleteTableColumn();
    }

    public void registerAccelerator(String actionId, String accelerator, Runnable action) {
        peer().registerAccelerator(actionId, accelerator, action);
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

    @Override
    public void replaceSelection(String newValue) {
        peer().replaceSelection(newValue);
    }

    private static class InsertTablePane extends GridPane implements AppAlertInputPane {

        NumberField<Integer> columns;
        NumberField<Integer> rows;

        public InsertTablePane(Application app) {
            super(2, app);
            columns = new NumberField<>(Integer.class, app)
                    .with(t -> {
                        t.childConstraints().addAll(Grow.HORIZONTAL);
                        t.value().set(2);
                    });
            rows = new NumberField<>(Integer.class, app)
                    .with(t -> {
                        t.childConstraints().addAll(Grow.HORIZONTAL);
                        t.value().set(2);
                    });
            parentConstraints().addAll(AllAnchors.LEFT, AllFill.HORIZONTAL, AllMargins.of(5));
            children().addAll(
                    new Label(Str.i18n("RichHtmlEditor.InsertTablePane.columnsLabel"), app),
                    columns,
                    new Label(Str.i18n("RichHtmlEditor.InsertTablePane.rowsLabel"), app),
                    rows
            );
        }

        @Override
        public Object getValue() {
            return new InsertTableInfo(
                    columns.value().get(),
                    rows.value().get(),
                    null
            );
        }
    }

    private static class InsertImagePane extends GridPane implements AppAlertInputPane {

        FileField url;
        NumberField<Integer> width;
        NumberField<Integer> height;
        CheckBox embedded;

        public InsertImagePane(Application app) {
            super(2, app);
            url = new FileField(app)
                    .with(t -> {
                        t.childConstraints().addAll(Grow.HORIZONTAL);
                    });
            width = new NumberField<>(Integer.class, app)
                    .with(t -> {
                        t.childConstraints().addAll(Grow.HORIZONTAL);
                    });
            height = new NumberField<>(Integer.class, app)
                    .with(t -> {
                        t.childConstraints().addAll(Grow.HORIZONTAL);
                    });
            embedded = new CheckBox(Str.i18n("RichHtmlEditor.InsertImagePane.embedded"), app)
                    .with(t -> {
                        t.childConstraints().addAll(
                                Grow.HORIZONTAL, Span.col(2)
                        );
                    });
            ;
            parentConstraints().addAll(AllAnchors.LEFT, AllFill.HORIZONTAL, AllMargins.of(5));
            children().addAll(
                    new Label(Str.i18n("RichHtmlEditor.InsertImagePane.urlLabel"), app),
                    url,
                    new Label(Str.i18n("RichHtmlEditor.InsertImagePane.widthLabel"), app),
                    width,
                    new Label(Str.i18n("RichHtmlEditor.InsertImagePane.heightLabel"), app),
                    height,
                    embedded
            );
        }

        @Override
        public Object getValue() {
            String ss = url.selection().getOr(s -> s == null ? "" : s);
            if (embedded.selected().get()) {
                if (!ss.startsWith("data:")) {
                    String b64 = null;
                    if (b64 == null) {
                        try {
                            URL u = null;
                            u = new URL(ss.trim());
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] buffer = new byte[2048];
                            int r = 0;
                            try (InputStream is = u.openStream()) {
                                while ((r = is.read(buffer)) > 0) {
                                    bos.write(buffer, 0, r);
                                }
                            }
                            b64 = Base64.getEncoder().encodeToString(bos.toByteArray());
                        } catch (Exception ex) {
                            //ignore
                        }
                    }
                    if (b64 == null) {
                        try {
                            File u = new File(ss.trim());
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            byte[] buffer = new byte[2048];
                            int r = 0;
                            try (InputStream is = new FileInputStream(u)) {
                                while ((r = is.read(buffer)) > 0) {
                                    bos.write(buffer, 0, r);
                                }
                            }
                            b64 = Base64.getEncoder().encodeToString(bos.toByteArray());
                        } catch (Exception ex) {
                            //ignore
                        }
                    }

                    if (b64 != null) {
                        String ct = Applications.probeContentType(ss.trim());
                        if (ct == null || "application/octet-stream".equals(ct)) {
                            ct = "image/jpeg";
                        }
                        ss = "data:" + ct + ";charset=utf-8;base64, " + b64;
                    }
                }
            }
            return new InsertImageInfo(
                    ss,
                    null,
                    width.value().get(),
                    height.value().get()
            );
        }
    }

}
