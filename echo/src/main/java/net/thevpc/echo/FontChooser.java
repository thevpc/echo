package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.components.AppChoiceItemContext;
import net.thevpc.echo.api.components.AppChoiceItemRenderer;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.constraints.*;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.components.FontBase;
import net.thevpc.echo.spi.peers.AppComponentPeer;

import java.util.Objects;
import java.util.stream.Collectors;

public class FontChooser extends FontBase implements AppFontChooser, AppContentAdapter {

    public static final FontStyle FONT_STYLE_BOLD_ITALIC = new FontStyle(FontWeight.BOLD, FontPosture.ITALIC);
    public static final FontStyle FONT_STYLE_ITALIC = new FontStyle(FontWeight.NORMAL, FontPosture.ITALIC);
    public static final FontStyle FONT_STYLE_BOLD = new FontStyle(FontWeight.BOLD, FontPosture.REGULAR);
    public static final FontStyle FONT_STYLE_REGULAR = new FontStyle(FontWeight.NORMAL, FontPosture.REGULAR);

    private GridPane container;
    private ChoiceList<AppFont> fontNames;
    private ChoiceList<FontStyle> fontStyles;
    private ComboBox<Integer> fontSizes;
    private Label preview;
    private boolean adjusting_font;

    public FontChooser(Application app) {
        this(null, app);
    }

    public FontChooser(String id, Application app) {
        super(id, app, AppFontChooser.class, AppComponentPeer.class);
        container = new GridPane(2, app)
                .with(p -> {
                    p.parentConstraints().addAll(
                            AllMargins.of(3), AllAnchors.LEFT
                    );
                    p.children()
                            .addAll(new BorderPane(app())
                                            .with((BorderPane fn) -> {
                                                fn.children().addAll(
                                                        new Label(Str.i18n("FontChooser.family"), app())
                                                                .with((Label l) -> {
                                                                    l.anchor().set(Anchor.TOP);
                                                                }),
                                                        new ScrollPane(
                                                                fontNames = new ChoiceList<>(AppFont.class, app())
                                                                        .with((ChoiceList<AppFont> ff) -> {
                                                                            ff.values().addCollection(app().fonts().toList().stream().distinct().collect(Collectors.toList()));
                                                                            ff.childConstraints().addAll(
                                                                                    Fill.BOTH,
                                                                                    Grow.BOTH
                                                                            );
                                                                            ff.anchor().set(Anchor.CENTER);
                                                                        })
                                                        )
                                                );
                                                fn.childConstraints().addAll(
                                                        Fill.BOTH,
                                                        Grow.BOTH
                                                );
                                            }),
                                    new BorderPane(app())
                                            .with((BorderPane fn) -> {
                                                fn.children().addAll(new Label(Str.i18n("FontChooser.style"), app())
                                                                .with((Label l) -> {
                                                                    l.anchor().set(Anchor.TOP);
                                                                }),
                                                        new ScrollPane(
                                                                fontStyles = new ChoiceList<>(FontStyle.class, app())
                                                                        .with((ChoiceList<FontStyle> ff) -> {
                                                                            ff.values().addAll(FONT_STYLE_REGULAR, FONT_STYLE_BOLD, FONT_STYLE_ITALIC, FONT_STYLE_BOLD_ITALIC);
                                                                            ff.childConstraints().addAll(
                                                                                    Fill.BOTH,
                                                                                    Grow.BOTH
                                                                            );
                                                                            ff.anchor().set(Anchor.CENTER);
                                                                        })
                                                        ),
                                                        new GridPane(2, app())
                                                                .with((GridPane g) -> {
                                                                    g.children().addAll(
                                                                            new Label(Str.i18n("FontChooser.size"), app()),
                                                                            fontSizes = new ComboBox<>(Integer.class, app())
                                                                                    .with((ComboBox<Integer> ff) -> {
                                                                                        ff.values().addAll(
                                                                                                4, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                                                                                                22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50,
                                                                                                56, 64, 72, 144
                                                                                        );
                                                                                        ff.editable().set(true);
                                                                                        ff.childConstraints().addAll(
                                                                                                Fill.HORIZONTAL,
                                                                                                Grow.HORIZONTAL
                                                                                        );
                                                                                    })
                                                                    );
                                                                    g.anchor().set(Anchor.BOTTOM);
                                                                })
                                                );
//                                                fn.anchor().set(Anchor.CENTER);
                                                fn.childConstraints().addAll(
                                                        Fill.BOTH,
                                                        Grow.BOTH
                                                );
                                            }),
                                    new BorderPane(app())
                                            .with((BorderPane pp) -> {
                                                pp.anchor().set(Anchor.BOTTOM);
                                                pp.parentConstraints().add(ContainerGrow.CENTER);
                                                pp.childConstraints().addAll(
                                                        Fill.HORIZONTAL,
                                                        Grow.HORIZONTAL,
                                                        Span.col(2),
                                                        Margin.of(30, 5, 5, 5)
                                                );
                                                pp.children().add(preview = new Label(Str.of("AaBbCcliPpQq12369$€?.;!/()"), app())
                                                        .with((Label l) -> {
                                                            l.anchor().set(Anchor.CENTER);
                                                            l.childConstraints().addAll(
                                                                    Fill.HORIZONTAL,
                                                                    Grow.HORIZONTAL
                                                            );
                                                        }));
                                            })
                            );
                });
        fontNames.selection().onChange(() -> rebuildFontSelection());
        fontStyles.selection().onChange(() -> rebuildFontSelection());
        fontSizes.selection().onChange(() -> rebuildFontSelection());
        fontSizes.text().onChange(() -> rebuildFontSelection());
        fontNames.itemRenderer().set(new AppChoiceItemRenderer<AppFont>() {
            @Override
            public void render(AppChoiceItemContext<AppFont> context) {
                context.setText(context.getValue().family());
                context.renderDefault();
            }
        });
        fontStyles.itemRenderer().set(new AppChoiceItemRenderer<FontStyle>() {
            @Override
            public void render(AppChoiceItemContext<FontStyle> context) {
                FontStyle f = context.getValue();
                boolean bold = f.weight.ordinal() >= FontWeight.BOLD.ordinal();
                boolean italic = f.posture == FontPosture.ITALIC;
                if (bold && italic) {
                    context.setText("Bold Italic");
                } else if (bold) {
                    context.setText("Bold");
                } else if (italic) {
                    context.setText("Italic");
                } else {
                    context.setText("Regular");
                }
                context.renderDefault();
            }
        });
        selection().onChange(() -> {
            if (!adjusting_font) {
                try {
                    adjusting_font = true;
                    AppFont curr = selection().get();
                    preview.textStyle().font().set(curr);
                    preview.textStyle().font().set(curr);
                    fontNames.selection().set(
                            fontNames.values().stream().filter(
                                    x -> x.family().equals(curr == null ? "" : curr.family())
                            ).findFirst().orElse(null)
                    );
                    int idealSize=curr == null ? 12 : ((int) curr.size());
                    Integer foundSize = fontSizes.values().stream().filter(
                            x -> x.equals(idealSize)
                    ).findFirst().orElse(null);
                    if (foundSize != null) {
                        fontSizes.selection().set(foundSize);
                        fontSizes.text().set(Str.of(String.valueOf(foundSize)));
                    } else {
                        fontSizes.text().set(Str.of(String.valueOf(idealSize)));
                    }
                    fontStyles.selection().set(FontStyle.of(curr));
                } finally {
                    adjusting_font = false;
                }
            }
        });
        Applications.bindContent(this);
        selection().set(fontNames.values().get(0));
    }

    @Override
    public AppComponent content() {
        return container;
    }

    @Override
    public boolean showDialog(AppComponent owner) {
        return new Alert(owner == null ? this : owner)
                .with((Alert t) -> {
                    t.title().set(Str.i18n("FontChooser.title"));
                    t.headerText().set(Str.i18n("FontChooser.header"));
                    t.headerIcon().set(Str.of("font-style"));
                    t.content().set(FontChooser.this);
                    t.withOkCancelButtons();
                }).showDialog().isOkButton();
    }

    private void rebuildFontSelection() {
        if (!adjusting_font) {
            AppFont f = fontNames.selection().get();
            Integer size = getSelectedSize();
            FontStyle st = fontStyles.selection().get();
            if (size == null) {
                size = 12;
            }
            if (f != null && size != null && st != null) {
                f = f.derive(null, ((Number) size).doubleValue(), st.weight, st.posture);
                selection().set(f);
            } else {
                //selection().set(null);
            }
        }
    }

    private Integer getSelectedSize() {
        if (fontSizes.lastWasEdit().get()) {
            return Applications.parseInt(fontSizes.text().get().value());
        } else {
            return fontSizes.selection().get();
        }
    }

    private static class FontStyle {

        private FontWeight weight;
        private FontPosture posture;

        public FontStyle(FontWeight weight, FontPosture posture) {
            this.weight = weight;
            this.posture = posture;
        }

        public static FontStyle of(AppFont f) {
            if (f == null) {
                return FONT_STYLE_REGULAR;
            }
            boolean bold = f.weight().ordinal() >= FontWeight.BOLD.ordinal();
            boolean italic = f.posture() == FontPosture.ITALIC;
            if (bold && italic) {
                return FONT_STYLE_BOLD_ITALIC;
            }
            if (bold) {
                return FONT_STYLE_BOLD;
            }
            if (italic) {
                return FONT_STYLE_ITALIC;
            }
            return FONT_STYLE_REGULAR;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + Objects.hashCode(this.weight);
            hash = 43 * hash + Objects.hashCode(this.posture);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FontStyle other = (FontStyle) obj;
            if (this.weight != other.weight) {
                return false;
            }
            if (this.posture != other.posture) {
                return false;
            }
            return true;
        }

    }
}
