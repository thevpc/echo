package net.thevpc.echo.jfx;

import net.thevpc.echo.FontPosture;
import net.thevpc.echo.FontWeight;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.spi.peers.AppFontPeer;

import java.awt.*;

public class FxFontPeer implements AppFontPeer {
    private final javafx.scene.text.Font toolkitFont;

    public FxFontPeer(AppFont font) {
        int style = ((font.weight().ordinal()
                > FontWeight.NORMAL.ordinal()) ? Font.BOLD : 0)
                +
                ((font.posture() == FontPosture.ITALIC) ? Font.ITALIC : 0);
        toolkitFont = javafx.scene.text.Font.font(
                font.family(),
                javafx.scene.text.FontWeight.valueOf(font.weight().toString()),
                javafx.scene.text.FontPosture.valueOf(font.posture().toString()),
                font.size()
        );
    }

    @Override
    public Object toolkitFont() {
        return toolkitFont;
    }
}
