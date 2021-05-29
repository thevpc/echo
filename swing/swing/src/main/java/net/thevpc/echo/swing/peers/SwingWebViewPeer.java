package net.thevpc.echo.swing.peers;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.UncheckedException;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppEditTextControl;
import net.thevpc.echo.api.components.AppWebView;
import net.thevpc.echo.spi.peers.AppWebViewPeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.net.URL;

public class SwingWebViewPeer implements SwingPeer, AppWebViewPeer {
    private AppWebView appWebView;
    private JEditorPane browser;

    @Override
    public void install(AppComponent comp) {
        this.appWebView = (AppWebView) comp;
        browser = new JEditorPane("text/html", "");
        browser.setEditable(false);
        SwingPeerHelper.installComponent(comp, browser);
        new InstallWebViewComponent(appWebView, browser).install();
    }

    @Override
    public Object toolkitComponent() {
        return browser;
    }

    @Override
    public void navigate(String url) {
        try {
            if (url == null || url.isEmpty()) {
                browser.setText("");
            } else {
                browser.setPage(new URL(url));
            }
        } catch (Exception e) {
            throw UncheckedException.wrap(e);
        }
    }

    @Override
    public void refresh() {
        try {
            URL old = browser.getPage();
            navigate(null);
            if (old != null) {
                browser.setPage(old);
            }
        } catch (Exception e) {
            throw UncheckedException.wrap(e);
        }
    }

    private static class InstallWebViewComponent {

        private boolean updatingDocument;
        private AppWebView appComponent;
        private JTextComponent swingComponent;

        public InstallWebViewComponent(AppWebView appComponent, JTextComponent swingComponent) {
            this.appComponent = appComponent;
            this.swingComponent = swingComponent;
        }

        void install() {
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
                    if (!updatingDocument) {
                        try {
                            updatingDocument = true;
                            appComponent.text().set(Str.of(swingComponent.getText()));
                        } finally {
                            updatingDocument = false;
                        }
                    }
                }
            };
            swingComponent.addPropertyChangeListener("document", e -> {
                Document o = (Document) e.getOldValue();
                Document n = (Document) e.getNewValue();
                if (o != null) {
                    o.removeDocumentListener(documentListener);
                }
                if (n != null) {
                    n.addDocumentListener(documentListener);
                }
            });
            swingComponent.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent e) {
                    int d = e.getDot();
                    int m = e.getMark();
                    if (appComponent instanceof AppEditTextControl) {
                        if (d != m) {
                            ((AppEditTextControl) appComponent).textSelection().set(
                                    swingComponent.getSelectedText()
                            );
                        } else {
                            ((AppEditTextControl) appComponent).textSelection().set("");
                        }
                    }
                }
            });
            swingComponent.getDocument().addDocumentListener(documentListener);

            //ignore locale!!
            appComponent.text().onChangeAndInit(new Runnable() {
                boolean updating = false;

                @Override
                public void run() {
                    if (!updating) {
                        updating = true;
                        try {
                            if (!updatingDocument) {
                                swingComponent.setText(
                                        appComponent.text().getOr(x -> x == null ? "" : x.toString())
                                );
                            }
                        } finally {
                            updating = false;
                        }
                    }
                }
            });
            appComponent.editable().onChangeAndInit(
                    () -> swingComponent.setEditable(appComponent.editable().get())
            );
        }

    }
}
