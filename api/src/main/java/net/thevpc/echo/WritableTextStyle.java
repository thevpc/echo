package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableInt;
import net.thevpc.common.props.WritableValue;
import net.thevpc.common.props.impl.SimpleProperty;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.constraints.Anchor;

import java.util.Objects;

public class WritableTextStyle extends SimpleProperty {
    private WritableValue<AppFont> font = Props.of("font").valueOf(AppFont.class);
    private WritableValue<AppColor> foregroundColor = Props.of("foregroundColor").valueOf(AppColor.class);
    private WritableValue<AppColor> backgroundColor = Props.of("backgroundColor").valueOf(AppColor.class);
    private WritableBoolean underline = Props.of("underline").booleanOf(false);
    private WritableBoolean strikethrough = Props.of("strikethrough").booleanOf(false);
    private WritableInt strokeSize = Props.of("strokeSize").intOf(1);
    private WritableValue<Anchor> align = Props.of("align").valueOf(Anchor.class);

    public WritableTextStyle(String name) {
        super(name);
        this.propagateEvents(font, foregroundColor, backgroundColor, underline, strikethrough, strokeSize);
    }

    public WritableValue<AppFont> font() {
        return font;
    }

    public WritableValue<Anchor> align() {
        return align;
    }

    public WritableValue<AppColor> foregroundColor() {
        return foregroundColor;
    }

    public WritableValue<AppColor> backgroundColor() {
        return backgroundColor;
    }

    public WritableBoolean underline() {
        return underline;
    }

    public WritableBoolean strikethrough() {
        return strikethrough;
    }

    public WritableInt strokeSize() {
        return strokeSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, foregroundColor, underline, strikethrough, strokeSize, align);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WritableTextStyle that = (WritableTextStyle) o;
        return Objects.equals(font.get(), that.font.get())
                && Objects.equals(foregroundColor.get(), that.foregroundColor.get())
                && Objects.equals(underline.get(), that.underline.get())
                && Objects.equals(strikethrough.get(), that.strikethrough.get())
                && Objects.equals(strokeSize, that.strokeSize)
                && Objects.equals(align.get(), that.align);
    }
}
