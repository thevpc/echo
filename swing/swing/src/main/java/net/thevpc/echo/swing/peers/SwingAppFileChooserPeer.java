package net.thevpc.echo.swing.peers;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFileChooser;
import net.thevpc.echo.api.AppFileFilter;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppFileChooserPeer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SwingAppFileChooserPeer implements AppFileChooserPeer,SwingPeer {
    private AppFileChooser chooser;
    private Application application;
    private JFileChooser swingComponent;

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
        return swingComponent;
    }

    private JFileChooser newInstance() {
        if (swingComponent == null) {
            swingComponent = new JFileChooser();
        }
        boolean acceptAll = chooser.filters().size() == 0;
        for (FileFilter ff : swingComponent.getChoosableFileFilters()) {
            swingComponent.removeChoosableFileFilter(ff);
        }
        for (AppFileFilter filter : chooser.filters()) {
            if(filter instanceof net.thevpc.echo.FileFilter) {
                net.thevpc.echo.FileFilter f=(net.thevpc.echo.FileFilter) filter;
                if (f.getExtensions().size() > 0) {
                    if (f.getExtensions().size() == 1 && f.getExtensions().contains("*.*")) {
                        acceptAll = true;
                    } else {
                        swingComponent.addChoosableFileFilter(new SwingFileFilter(filter));
                    }
                }
            }
        }
        swingComponent.setAcceptAllFileFilterUsed(acceptAll);
        swingComponent.setCurrentDirectory(chooser.currentDirectory().get() == null ? null : new File(chooser.currentDirectory().get()));
        if (chooser.acceptDirectories().get() && chooser.acceptFiles().get()) {
            swingComponent.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        } else if (chooser.acceptDirectories().get()) {
            swingComponent.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            swingComponent.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        swingComponent.setMultiSelectionEnabled(chooser.multipleValues().get());
        return swingComponent;
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
        chooser.selection().clear();
        JFileChooser jfc = newInstance();
        int d = jfc.showOpenDialog(getComponentOwner(parent));
        if (d == JFileChooser.APPROVE_OPTION) {
            File sf = jfc.getSelectedFile();
            if(sf==null){
                chooser.selection().clear();
            }else{
                chooser.selection().set(sf.getPath());
            }
            return true;
        } else {
            chooser.selection().clear();
        }
        return false;
    }

    @Override
    public boolean showSaveDialog(Object parent) {
        chooser.selection().clear();
        JFileChooser jfc = newInstance();
        int d = jfc.showSaveDialog(getComponentOwner(parent));
        if (d == JFileChooser.APPROVE_OPTION) {
            File sf = jfc.getSelectedFile();
            if(sf==null){
                chooser.selection().clear();
            }else{
                chooser.selection().set(sf.getPath());
            }
            return true;
        } else {
            chooser.selection().clear();
        }
        return false;
    }

    private class SwingFileFilter extends FileFilter {
        private final AppFileFilter filter;
        Pattern p;

        public SwingFileFilter(AppFileFilter filter) {
            this.filter = filter;
            if(filter instanceof net.thevpc.echo.FileFilter) {
                StringBuilder pattern = new StringBuilder();
                for (String extension : ((net.thevpc.echo.FileFilter)filter).getExtensions()) {
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
        }

        @Override
        public boolean accept(File f) {
            return p!=null && p.matcher(f.getName()).matches();
        }

        @Override
        public String getDescription() {
            return Applications.rawString(filter.getDescription(),chooser);
        }
    }
}
