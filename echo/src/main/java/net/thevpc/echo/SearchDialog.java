/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.constraints.AllAnchors;
import net.thevpc.echo.constraints.AllFill;
import net.thevpc.echo.constraints.AllMargins;
import net.thevpc.echo.constraints.AllPaddings;

/**
 * @author vpc
 */
public class SearchDialog {

    private Panel panel;
    private AppComponent owner;
    private Label matchModeLabel;

    private ComboBox<String> queryEditor;
    private CheckBox matchCaseEditor;
    private CheckBox wholeWordEditor;
    private ComboBox<SimpleItem> modeEditor;

    private boolean ok = false;
    private Str titleId = Str.i18n("SearchDialog.search");

    public SearchDialog(AppComponent owner) {
        this.owner = owner;
        Application app = owner.app();
        this.matchCaseEditor = new CheckBox(Str.i18n("SearchDialog.matchCase"), app);
        this.wholeWordEditor = new CheckBox(Str.i18n("SearchDialog.wholeWord"), app);
        this.matchModeLabel = new Label(Str.i18n("SearchDialog.matchingText"), app);
        this.modeEditor = new ComboBox<>(SimpleItem.class, app);
        this.modeEditor.values().add(new SimpleItem(SearchQueryStrategy.LITERAL.toString(), Str.i18n("SearchDialog.searchLiteralStrategy")));
        this.modeEditor.values().add(new SimpleItem(SearchQueryStrategy.SIMPLE.toString(), Str.i18n("SearchDialog.searchSimpleStrategy")));
        this.modeEditor.values().add(new SimpleItem(SearchQueryStrategy.REGEXP.toString(), Str.i18n("SearchDialog.searchRegexpStrategy")));

        this.modeEditor.selection().indices().set(0);

        this.queryEditor = new ComboBox<String>(String.class, app);
        this.queryEditor.editable().set(true);
//        queryEditor.setMinimumSize(new Dimension(50, 30));
        this.panel = new GridPane(1, app)
                .with(p -> {
                    p.parentConstraints().addAll(AllMargins.of(5), AllAnchors.LEFT, AllFill.HORIZONTAL);
                });
        panel.children().addAll(new Label(Str.i18n("SearchDialog.searchLabel"), app),
                queryEditor,
                new GridPane(app)
                        .with((Panel hh) -> {
                            hh.parentConstraints().addAll(AllPaddings.of(5));
                            hh.children().addAll(
                                    matchCaseEditor,
                                    wholeWordEditor,
                                    matchModeLabel,
                                    modeEditor
                            );
                        })
        );
    }

    public ComboBox<String> queryEditor() {
        return queryEditor;
    }

    protected void install() {
    }

    protected void uninstall() {
    }

    protected void ok() {
        uninstall();
        this.ok = true;
    }

    protected void cancel() {
        uninstall();
        this.ok = false;
    }

    public Str getTitle() {
        return titleId;
    }

    public SearchDialog setTitle(Str titleId) {
        this.titleId = titleId;
        return this;
    }

    public SearchQuery showDialog() {
        while (true) {
            install();
            this.ok = false;
            new Alert(owner)
                    .with((Alert a) -> {
                        a.title().set(Str.i18n("SearchDialog.title"));
                        a.headerText().set(titleId);
                        a.headerIcon().set(Str.of("search"));
                        a.content().set(panel);
                        a.withOkCancelButtons(
                                (b) -> {
                                    ok();
                                    b.getAlert().closeAlert();
                                },
                                (b) -> {
                                    cancel();
                                    b.getAlert().closeAlert();
                                }
                        );

                    })
                    .showDialog();
            try {
                return get();
            } catch (Exception ex) {
                owner.app().errors().add(ex);
            }
        }
    }

    public SearchQuery get() {
        if (ok) {
            String s = queryEditor.text().get().value();
            if (s != null && s.length() > 0) {
                SearchQueryStrategy mm = SearchQueryStrategy.valueOf(modeEditor.selection().get().getId());
                SearchQuery q = new SearchQuery(
                        s,
                        matchCaseEditor.selected().get(),
                        wholeWordEditor.selected().get(),
                        mm
                );
                return q;
            }
        }
        return null;
    }

    public void setSearchTextElseClipboard(String selectedText) {
        if (selectedText == null || selectedText.length() == 0) {
            String s = owner.app().clipboard().getString();
            if (s != null) {
                selectedText = s;
            }
        }
        setSearchText(selectedText);
    }

    public void setSearchText(String selectedText) {
        queryEditor.text().set(Str.of(selectedText));
        queryEditor.lastWasEdit().set(true);
    }

}
