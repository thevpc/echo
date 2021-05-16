package net.thevpc.echo.swing.raw;

import javax.swing.*;
import java.awt.*;

public class JDockWindow extends JPanel {
    String id;
    String title;
    Icon icon;
    JComponent component;
    boolean closable;
    private JComponent content;
    private JComponent contentReplace;
    private JComponent header;
    private JComponent footer;

    public JDockWindow() {
        super(new BorderLayout());
    }

    public JDockWindow(String id, String title, Icon icon, JComponent component, boolean closable) {
        this();
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.closable = closable;
        setContent(component);
    }

    public JComponent getContent() {
        return content;
    }

    public JDockWindow setContent(JComponent content) {
        if (this.contentReplace != null) {
            this.remove(this.contentReplace);
        }
        this.content = content;
        if (content != null) {
            if (contentReplace == null) {
                JInternalFrame ji = new JInternalFrame();
                contentReplace = ji;
                ji.setVisible(true);
                ji.getContentPane().add(content);
                add(contentReplace, BorderLayout.CENTER);
            } else {
                ((JInternalFrame) contentReplace).getContentPane().removeAll();
                ((JInternalFrame) contentReplace).getContentPane().add(content);
            }
        } else {
            this.remove(contentReplace);
            contentReplace = null;
        }
        return this;
    }

    public JComponent getHeader() {
        return header;
    }

    public JDockWindow setHeader(JComponent header) {
        this.header = header;
        return this;
    }

    public JComponent getFooter() {
        return footer;
    }

    public JDockWindow setFooter(JComponent footer) {
        this.footer = footer;
        return this;
    }
}
