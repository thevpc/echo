package net.thevpc.echo.swing.peers;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFileChooser;
import net.thevpc.echo.api.tools.AppFileFilter;
import net.thevpc.echo.api.peers.AppFileChooserPeer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SwingAppFileChooserPeer implements AppFileChooserPeer {
    private AppFileChooser chooser;
    private Application application;
    private JFileChooser toolkitComponent;

    public SwingAppFileChooserPeer() {
    }

    @Override
    public void install(AppComponent comp) {
        this.application = comp.app();
        this.chooser = (AppFileChooser) comp;
        newInstance();
    }

    @Override
    public Object toolkitComponent() {
        return toolkitComponent;
    }

    private JFileChooser newInstance() {
        if (toolkitComponent == null) {
            toolkitComponent = new JFileChooser();
        }
        boolean acceptAll = chooser.model().filters().size() == 0;
        for (FileFilter ff : toolkitComponent.getChoosableFileFilters()) {
            toolkitComponent.removeChoosableFileFilter(ff);
        }
        for (AppFileFilter filter : chooser.model().filters()) {
            if (filter.getExtensions().size() > 0) {
                if (filter.getExtensions().size() == 1 && filter.getExtensions().contains("*.*")) {
                    acceptAll = true;
                } else {
                    toolkitComponent.addChoosableFileFilter(new SwingFileFilter(filter));
                }
            }
        }
        toolkitComponent.setAcceptAllFileFilterUsed(acceptAll);
        toolkitComponent.setCurrentDirectory(chooser.model().currentDirectory().get() == null ? null : new File(chooser.model().currentDirectory().get()));
        if (chooser.model().acceptDirectories().get() && chooser.model().acceptFiles().get()) {
            toolkitComponent.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        } else if (chooser.model().acceptDirectories().get()) {
            toolkitComponent.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            toolkitComponent.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        toolkitComponent.setMultiSelectionEnabled(chooser.model().multipleValues().get());
        return toolkitComponent;
    }

    private Component getComponentOwner(Object parent) {
        if (parent instanceof Component) {
            return (Component) parent;
        }
        parent = application.mainFrame().get().peer().toolkitComponent();
        if (parent instanceof Component) {
            return (Component) parent;
        }
        return null;
    }

    @Override
    public boolean showOpenDialog(Object parent) {
        chooser.model().selection().clear();
        JFileChooser jfc = newInstance();
        int d = jfc.showOpenDialog(getComponentOwner(parent));
        String[] selection = {};
        if (d == JFileChooser.APPROVE_OPTION) {
            chooser.model().selection().clear();
            chooser.model().selection().addAll(
                    Arrays.stream(jfc.getSelectedFiles())
                            .map(x -> x.getPath())
                            .toArray(String[]::new)
            );
            chooser.model().selection().set(
                    chooser.model().selection().size() == 0 ? null :
                            chooser.model().selection().get(0)
            );
            return true;
        } else {
            chooser.model().selection().clear();
            chooser.model().selection().set(null);
        }
        return false;
    }

    @Override
    public boolean showSaveDialog(Object parent) {
        chooser.model().selection().clear();
        JFileChooser jfc = newInstance();
        int d = jfc.showSaveDialog(getComponentOwner(parent));
        if (d == JFileChooser.APPROVE_OPTION) {
            chooser.model().selection().clear();
            chooser.model().selection().addAll(
                    Arrays.stream(jfc.getSelectedFiles())
                            .map(x -> x.getPath())
                            .toArray(String[]::new)
            );
            chooser.model().selection().set(
                    chooser.model().selection().size() == 0 ? null :
                            chooser.model().selection().get(0)
            );
            return true;
        } else {
            chooser.model().selection().clear();
            chooser.model().selection().set(null);
        }
        return false;
    }

    private class SwingFileFilter extends FileFilter {
        private final AppFileFilter filter;
        Pattern p;

        public SwingFileFilter(AppFileFilter filter) {
            this.filter = filter;
            StringBuilder pattern = new StringBuilder();
            for (String extension : filter.getExtensions()) {
                if (pattern.length() > 0) {
                    pattern.append("|");
                } else {
                    pattern.append("^");
                }
                for (char c : extension.toCharArray()) {
                    switch (c) {
                        case '*': {
                            pattern.append(".*");
                            break;
                        }
                        case '?': {
                            pattern.append(".");
                            break;
                        }
                        case '.': {
                            pattern.append("[.]");
                            break;
                        }
                        case '\\':
                        case '[':
                        case ']':
                        case '(':
                        case ')':
                        case '{':
                        case '}':
                        case '^':
                        case '$': {
                            pattern.append("\\").append(c);
                            break;
                        }
                        default: {
                            pattern.append(c);
                        }
                    }
                }
            }
            pattern.append("$");
            p = Pattern.compile(pattern.toString());
        }

        @Override
        public boolean accept(File f) {
            return p.matcher(f.getName()).matches();
        }

        @Override
        public String getDescription() {
            return filter.getDescription().getValue(application.i18n());
        }
    }
}
