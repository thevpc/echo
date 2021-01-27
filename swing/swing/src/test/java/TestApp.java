import net.thevpc.echo.*;
import net.thevpc.echo.swing.Applications;

import javax.swing.*;

public class TestApp {
    public static void main(String[] args) {
        Application a = Applications.Apps.Default();
//        a.builder().mainWindowBuilder().get().workspaceFactory().set(new FlexdockWorkspaceFactory());
        Applications.Helper.addViewActions(a);

        a.start();
        AppTools mwt = a.mainWindow().get().tools();
        mwt.addFolder(("/menuBar/File"));
        mwt.addFolder(("/menuBar/Edit"));
        mwt.addAction(null, "/menuBar/File/Exit")
                .tool().action().set(e -> JOptionPane.showMessageDialog(null, "Exit"));
        mwt.addAction(null, "/toolBar/File/B1");
        mwt.addAction(null, "/toolBar/File/B2");
        mwt.addAction(null, "/toolBar/File/B3");

        mwt.addTool(AppToolComponent.of(new CustomAppTool("rr"), "/toolBar/File/B4", 0,
                context -> new JComboBox<>(new String[]{"A","B","C"})
        ));
//        mwt.addAction(null, "/toolBar/Other/B1");
//        mwt.addAction(null, "/toolBar/Other/B2");
//        mwt.addAction(null, "/toolBar/Other/B3");
//        a.addToolAction(("/toolBar/Exit"))
//                .getTool().getActionListener().set(e -> JOptionPane.showMessageDialog(null, "Exit"));
        mwt.addSeparator(("/menuBar/File/Sep"));
        mwt.addCheck(null, "/menuBar/File/Check").tool().group().set("group1");
        mwt.addRadio(null, "/menuBar/File/Radio").tool().group().set("group1");
        mwt.addRadio(null, "/menuBar/File/Folder/Radio");
        mwt.addSeparator("/statusBar/Default/Fill").tool().width().set(Integer.MAX_VALUE);
        mwt.addRadio(null, "/statusBar/Default/Radio");

        for (AppNode node : a.nodes()) {
            dump(node, "");
        }
    }

    public static void dump(AppNode n, String prefix) {
        System.out.println(prefix + n.getPath());
        for (AppNode child : n.getChildren()) {
            dump(child, prefix + "  ");
        }
    }
}
