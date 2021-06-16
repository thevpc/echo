package net.thevpc.echo.swing.peers;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFileChooser;
import net.thevpc.echo.api.AppFileFilter;
import net.thevpc.echo.api.components.AppFileControl;
import net.thevpc.echo.spi.peers.AppFileChooserPeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class SwingFileChooserPeer implements AppFileChooserPeer,SwingPeer {
    private AppFileChooser chooser;
    private Application application;
    private JFileChooser swingFileChooser;

    public SwingFileChooserPeer() {
    }

    @Override
    public void install(AppComponent comp) {
        this.application = comp.app();
        this.chooser = (AppFileChooser) comp;
        newInstance();
    }

    @Override
    public Object toolkitComponent() {
        return swingFileChooser;
    }

    public static JFileChooser prepareChooser(JFileChooser swingFileChooser, AppFileControl chooser) {
        boolean acceptAll = chooser.filters().size() == 0;
        for (FileFilter ff : swingFileChooser.getChoosableFileFilters()) {
            swingFileChooser.removeChoosableFileFilter(ff);
        }
        for (AppFileFilter filter : chooser.filters()) {
            if(filter instanceof net.thevpc.echo.FileFilter) {
                net.thevpc.echo.FileFilter f=(net.thevpc.echo.FileFilter) filter;
                if (f.getExtensions().size() > 0) {
                    if (f.getExtensions().size() == 1 && f.getExtensions().contains("*.*")) {
                        acceptAll = true;
                    } else {
                        swingFileChooser.addChoosableFileFilter(new SwingFileFilter(filter,chooser));
                    }
                }
            }
        }
        swingFileChooser.setAcceptAllFileFilterUsed(acceptAll);
        swingFileChooser.setCurrentDirectory(chooser.currentDirectory().get() == null ? null : new File(chooser.currentDirectory().get()));
        if (chooser.acceptDirectories().get() && chooser.acceptFiles().get()) {
            swingFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        } else if (chooser.acceptDirectories().get()) {
            swingFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            swingFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        swingFileChooser.setMultiSelectionEnabled(chooser.multipleValues().get());
        return swingFileChooser;
    }
    private JFileChooser newInstance() {
        if (swingFileChooser == null) {
            swingFileChooser = new JFileChooser();
        }
        prepareChooser(swingFileChooser,chooser);
        return swingFileChooser;
    }


    @Override
    public boolean showOpenDialog(Object parent) {
        chooser.selection().clear();
        JFileChooser jfc = newInstance();
        int d = jfc.showOpenDialog(SwingPeerHelper.getComponentOwner(parent,chooser));
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
        int d = jfc.showSaveDialog(SwingPeerHelper.getComponentOwner(parent,chooser));
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

}
