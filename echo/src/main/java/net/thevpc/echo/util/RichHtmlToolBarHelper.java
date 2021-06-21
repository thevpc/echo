package net.thevpc.echo.util;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Path;
import net.thevpc.echo.*;
import net.thevpc.echo.api.TextAlignment;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.impl.Applications;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RichHtmlToolBarHelper {

    public static void prepareMenu(Frame frame, boolean prefixSeparator, boolean suffixSeparator) {
        AppMenu e = (AppMenu) frame.children().addFolder(Path.of("/menuBar/Edit"));
        prepareContainer(e, frame, prefixSeparator, suffixSeparator);
    }

    public static void prepareToolBar(Frame frame, boolean prefixSeparator, boolean suffixSeparator) {
        frame.findOrCreateToolBar("RichHtml", editToolBar -> {
            prepareContainer(editToolBar, frame, prefixSeparator, suffixSeparator);
        });
    }

    public static void prepareContainer(AppContainer editToolBar, AppComponent owner, boolean prefixSeparator, boolean suffixSeparator) {
        AppFrame frame = Applications.frameOf(editToolBar);
        if (frame == null) {
            throw new IllegalArgumentException("missing frame");
        }
        Application app = frame.app();
        WithEnableIfRichHtmlEditor enableIfRichHtmlEditor = new WithEnableIfRichHtmlEditor(frame);
        prepareContainer0(editToolBar, owner, prefixSeparator, suffixSeparator,
                () -> app.toolkit().focusOwner().get(),
                enableIfRichHtmlEditor);
    }

    public static void prepareContextMenu(RichHtmlEditor editor, boolean prefixSeparator, boolean suffixSeparator) {
        AppContextMenu contextMenu = editor.contextMenu().getOrCompute(() -> new ContextMenu(editor.app()));
        prepareContainer0(contextMenu, editor, prefixSeparator, suffixSeparator, () -> editor, (e) -> {
        });
    }

    private static void prepareContainer0(AppContainer editToolBar, AppComponent owner, boolean prefixSeparator, boolean suffixSeparator,
                                          Supplier<AppComponent> selected,
                                          Consumer<AppComponent> enableIfRichHtmlEditor
    ) {
        if (editToolBar.userObjects().get(RichHtmlToolBarHelper.class) != null) {
            return;
        }
        editToolBar.userObjects().put(RichHtmlToolBarHelper.class, true);
        Application app = editToolBar.app();
        if (prefixSeparator) {
            editToolBar.children().addSeparator().with(enableIfRichHtmlEditor);
        }
        AppComponent folder = editToolBar.children().addFolder(Path.of("Styles"));
        folder.icon().set(Str.i18n("Action.Styles.icon"));
        if (folder instanceof Menu) {
            ((Menu) folder).text().set(Str.i18n("Action.Styles"));
        }
        folder.with(enableIfRichHtmlEditor);
        for (String tag : new String[]{"h1", "h2", "h3", "h5", "h6", "pre", "div", "p", "ol", "ul", "hr"}) {
            editToolBar.children().add(new Button("insert-" + tag, event -> {
                AppComponent fo = selected.get();
                if (fo instanceof AppRichHtmlEditor) {
                    RichHtmlEditor t = (RichHtmlEditor) fo;
                    t.runTextInsertTag(tag);
                }
            }, app).with(enableIfRichHtmlEditor), Path.of("Styles/*"));
        }

        editToolBar.children().add(new Button("font-bold", event -> {
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runTextBold();
            }
        }, app).with(enableIfRichHtmlEditor));
        editToolBar.children().add(new Button("font-italic", event -> {
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runTextItalic();
            }
        }, app).with(enableIfRichHtmlEditor));
        editToolBar.children().add(new Button("font-underline", event -> {
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runTextUnderline();
            }
        }, app).with(enableIfRichHtmlEditor));
        editToolBar.children().add(new Button("font-strike-through", event -> {
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runTextStrikeThrough();
            }
        }, app).with(enableIfRichHtmlEditor));
        for (TextAlignment align : TextAlignment.values()) {
            editToolBar.children().add(new Button("align-" + (align.name().toLowerCase()), event -> {
                AppComponent fo = selected.get();
                if (fo instanceof AppRichHtmlEditor) {
                    RichHtmlEditor t = (RichHtmlEditor) fo;
                    t.runTextAlignment(align);
                }
            }, app).with(enableIfRichHtmlEditor));
        }
        editToolBar.children().add(new ColorButton("foregroundColor", app)
                .with(enableIfRichHtmlEditor)
                .with((ColorButton c) -> {
                    c.value().onChange(cc -> {
                        AppComponent fo = selected.get();
                        if (fo instanceof AppRichHtmlEditor) {
                            RichHtmlEditor t = (RichHtmlEditor) fo;
                            if (t.textSelection().get().length() > 0) {
                                t.runTextForegroundColor(c.value().get());
                            } else {
                                t.foregroundColor().set(c.value().get());
                            }
                        }
                    });
                })
        );
        editToolBar.children().add(new ColorButton("backgroundColor", app)
                .with(enableIfRichHtmlEditor)
                .with((ColorButton c) -> {
                    c.value().onChange(cc -> {
                        AppComponent fo = selected.get();
                        if (fo instanceof AppRichHtmlEditor) {
                            RichHtmlEditor t = (RichHtmlEditor) fo;
                            if (t.textSelection().get().length() > 0) {
                                t.runTextBackgroundColor(c.value().get());
                            } else {
                                t.backgroundColor().set(c.value().get());
                            }
                        }
                    });
                })
        );
        editToolBar.children().add(new FontButton("font", owner, app)
                .with(enableIfRichHtmlEditor)
                .with((FontButton c) -> {
                    c.selection().onChange(cc -> {
                        AppComponent fo = selected.get();
                        if (fo instanceof AppRichHtmlEditor) {
                            RichHtmlEditor t = (RichHtmlEditor) fo;
                            if (t.textSelection().get().length() > 0) {
                                t.runTextFont(c.selection().get());
                            } else {
                                t.textStyle().font().set(c.selection().get());
                            }
                        }
                    });
                })
        );

        ///////////////////
        AppComponent tableFolder = editToolBar.children().addFolder(Path.of("Table"));
        tableFolder.icon().set(Str.i18n("Action.Table.icon"));
        if (tableFolder instanceof Menu) {
            ((Menu) tableFolder).text().set(Str.i18n("Action.Table"));
        }

//        tableFolder.title().set(Str.i18n("Action.table"));
//        tableFolder.icon().set(Str.of("table"));
        tableFolder.with(enableIfRichHtmlEditor);
        editToolBar.children().add(new Button("insert-table", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runInsertTable();
            }
        }, app).with(enableIfRichHtmlEditor), Path.of("Table/*"));
        editToolBar.children().add(new Button("insert-table-row", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runInsertTableRow();
            }
        }, app).with(enableIfRichHtmlEditor), Path.of("Table/*"));
        editToolBar.children().add(new Button("insert-table-column", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runInsertTableColumn();
            }
        }, app).with(enableIfRichHtmlEditor), Path.of("Table/*"));
        editToolBar.children().add(new Button("delete-table-row", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runDeleteTableRow();
            }
        }, app).with(enableIfRichHtmlEditor), Path.of("Table/*"));
        editToolBar.children().add(new Button("delete-table-column", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runDeleteTableColumn();
            }
        }, app).with(enableIfRichHtmlEditor), Path.of("Table/*"));
        editToolBar.children().add(new Button("delete-table", event -> {
            //KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()
            AppComponent fo = selected.get();
            if (fo instanceof AppRichHtmlEditor) {
                RichHtmlEditor t = (RichHtmlEditor) fo;
                t.runDeleteTable();
            }
        }, app).with(enableIfRichHtmlEditor), Path.of("Table/*"));

        editToolBar.children().add(new Button("insert-image",
                () -> {
                    AppComponent fo = selected.get();
                    if (fo instanceof AppRichHtmlEditor) {
                        RichHtmlEditor t = (RichHtmlEditor) fo;
                        t.runInsertImage();
                    }
                },
                app)
                .with(enableIfRichHtmlEditor)
        );
        if (suffixSeparator) {
            editToolBar.children().addSeparator().with(enableIfRichHtmlEditor);
        }

    }

    private static class WithEnableIfRichHtmlEditor implements Consumer<AppComponent> {

        private final AppFrame frame;

        public WithEnableIfRichHtmlEditor(AppFrame frame) {
            this.frame = frame;
        }

        @Override
        public void accept(AppComponent x) {
            frame.app().toolkit().focusOwner().onChangeAndInit(() -> {
                AppComponent fo = frame.app().toolkit().focusOwner().get();
                boolean copyEnabled = false;
                if (fo instanceof AppRichHtmlEditor && Applications.isDeepChildOf(fo, frame)) {
                    copyEnabled = true;
                }
                x.enabled().set(copyEnabled);
                x.visible().set(
                        !frame.app().hideDisabled().get() || copyEnabled
                );
            });
        }
    }
}
