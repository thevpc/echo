//package net.thevpc.echo.swing.peers;
//
//import net.thevpc.common.i18n.Str;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.components.AppFileField;
//import net.thevpc.echo.spi.peers.AppFileFieldPeer;
//import net.thevpc.echo.swing.SwingPeerHelper;
//
//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.text.Document;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//
//public class SwingFileFieldPeer implements SwingPeer, AppFileFieldPeer {
//    private JFileChooser swingFileChooser;
//    private JTextField jTextField;
//    private JButton browse;
//    private AppFileField chooser;
//    private JPanel panel;
//    private boolean updatingDocument;
//
//    public SwingFileFieldPeer() {
//    }
//
//
//    public void install(AppComponent component) {
//        chooser =(AppFileField) component;
//        jTextField=new JTextField();
//        browse=new JButton("...");
//        panel=new JPanel(new GridBagLayout());
//
//        GridBagConstraints c=new GridBagConstraints();
//        c.fill=GridBagConstraints.HORIZONTAL;
//        c.weightx=1;
//        panel.add(jTextField,c);
//        c=new GridBagConstraints();
//        panel.add(browse,c);
//        SwingPeerHelper.installComponent(chooser,this.jTextField);
//
//
//        DocumentListener documentListener = new DocumentListener() {
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                anyChange(e);
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                anyChange(e);
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                anyChange(e);
//            }
//
//            public void anyChange(DocumentEvent e) {
//                if (!updatingDocument) {
//                    try {
//                        updatingDocument = true;
//                        chooser.selection().set(jTextField.getText());
//                    } finally {
//                        updatingDocument = false;
//                    }
//                }
//            }
//        };
//        jTextField.addPropertyChangeListener("document", e -> {
//            Document o = (Document) e.getOldValue();
//            Document n = (Document) e.getNewValue();
//            if (o != null) {
//                o.removeDocumentListener(documentListener);
//            }
//            if (n != null) {
//                n.addDocumentListener(documentListener);
//            }
//        });
//        jTextField.getDocument().addDocumentListener(documentListener);
//        chooser.selection().onChangeAndInit(new Runnable() {
//            boolean updating = false;
//
//            @Override
//            public void run() {
//                if (!updating) {
//                    updating = true;
//                    try {
//                        if (!updatingDocument) {
//                            //TODO what it multiple selection?
//                            jTextField.setText(chooser.selection().get());
//                        }
//                    } finally {
//                        updating = false;
//                    }
//                }
//            }
//        });
//        chooser.editable().onChangeAndInit(
//                () -> {
//                    boolean editable = chooser.editable().get();
//                    jTextField.setEditable(editable);
//                    browse.setEnabled(editable);
//                }
//        );
//
//        newInstance();
//        browse.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showDialog();
//            }
//        });
//    }
//
//    public boolean showDialog() {
//        Object parent=chooser;
//        JFileChooser jfc = newInstance();
//        Component owner = SwingPeerHelper.getComponentOwner(parent, chooser);
//        boolean openOrSave=true;
//        int d = openOrSave?jfc.showOpenDialog(owner):jfc.showSaveDialog(owner);
//        if (d == JFileChooser.APPROVE_OPTION) {
//            File sf = jfc.getSelectedFile();
//            if(sf==null){
//                chooser.selection().clear();
//            }else{
//                chooser.selection().set(sf.getPath());
//            }
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public Object toolkitComponent() {
//        return panel;
//    }
//
//    private JFileChooser newInstance() {
//        if (swingFileChooser == null) {
//            swingFileChooser = new JFileChooser();
//        }
//        SwingFileChooserPeer.prepareChooser(swingFileChooser, chooser);
//        return swingFileChooser;
//    }
//}
