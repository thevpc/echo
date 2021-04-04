
import net.thevpc.echo.*;
import net.thevpc.echo.swing.SwingApplications;

import javax.swing.*;

public class TestApp {

    public static void main(String[] args) {
        Application a = SwingApplications.Apps.Default();
//        a.builder().mainWindowBuilder().get().workspaceFactory().set(new FlexdockWorkspaceFactory());
        SwingApplications.Helper.addViewActions(a);

        a.start();
        AppTools mwt = a.mainWindow().get().tools();
        mwt.addFolder(("/menuBar/File"));
        mwt.addFolder(("/menuBar/Edit"));
        mwt.addAction("/menuBar/File/Exit")
                .tool().action().set(e -> JOptionPane.showMessageDialog(null, "Exit"));
        mwt.addAction("/toolBar/File/B1");
        mwt.addAction("/toolBar/File/B2");
        mwt.addAction("/toolBar/File/B3");

        mwt.addCustomTool("/toolBar/File/B4", 
                context -> new JComboBox<>(new String[]{"A", "B", "C"}),"/toolBar/File/B4", 0
        );
//        mwt.addAction(null, "/toolBar/Other/B1");
//        mwt.addAction(null, "/toolBar/Other/B2");
//        mwt.addAction(null, "/toolBar/Other/B3");
//        a.addToolAction(("/toolBar/Exit"))
//                .getTool().getActionListener().set(e -> JOptionPane.showMessageDialog(null, "Exit"));
        mwt.addSeparator(("/menuBar/File/Sep"));
        mwt.addCheck("/menuBar/File/Check").tool().group().set("group1");
        mwt.addRadio("/menuBar/File/Radio").tool().group().set("group1");
        mwt.addRadio("/menuBar/File/Folder/Radio");
        mwt.addSeparator("/statusBar/Default/Fill").tool().width().set(Integer.MAX_VALUE);
        mwt.addRadio("/statusBar/Default/Radio");

        dump(a.rootNode(), "");
    }

    public static void dump(AppNode n, String prefix) {
        System.out.println(prefix + n.path());
        for (AppNode child : n.getChildren()) {
            dump(child, prefix + "  ");
        }
    }
}
