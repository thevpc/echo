
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
        mwt.addAction().path("/menuBar/File/Exit").bind(() -> JOptionPane.showMessageDialog(null, "Exit")).tool();
        mwt.addAction().path("/toolBar/File/B1").tool();
        mwt.addAction().path("/toolBar/File/B2").tool();
        mwt.addAction().path("/toolBar/File/B3").tool();

        mwt.addCustomTool("/toolBar/File/B4",
                context -> new JComboBox<>(new String[]{"A", "B", "C"}), "/toolBar/File/B4", 0
        );
//        mwt.addAction(null, "/toolBar/Other/B1");
//        mwt.addAction(null, "/toolBar/Other/B2");
//        mwt.addAction(null, "/toolBar/Other/B3");
//        a.addToolAction(("/toolBar/Exit"))
//                .tool().getActionListener().set(e -> JOptionPane.showMessageDialog(null, "Exit"));
        mwt.addSeparator(("/menuBar/File/Sep"));
        mwt.addToggle().group("group1").path("/menuBar/File/Check").tool();
        mwt.addToggle().buttonType(AppToolButtonType.RADIO).path("/menuBar/File/Radio").tool().group().set("group1");
        mwt.addToggle().buttonType(AppToolButtonType.RADIO).path("/menuBar/File/Folder/Radio").tool();
        mwt.addSeparator("/statusBar/Default/Fill").tool().width().set(Integer.MAX_VALUE);
        mwt.addToggle().buttonType(AppToolButtonType.RADIO).path("/statusBar/Default/Radio").tool();

        dump(a.rootNode(), "");
    }

    public static void dump(AppNode n, String prefix) {
        System.out.println(prefix + n.path());
        for (AppNode child : n.getChildren()) {
            dump(child, prefix + "  ");
        }
    }
}
