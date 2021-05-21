package net.thevpc.echo.demos.demo;

import net.thevpc.common.props.WritableListSelection;
import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppTreeNode;
import net.thevpc.echo.impl.DefaultApplication;
import net.thevpc.echo.impl.TreeNode;

import java.util.Arrays;
import java.util.List;

public class ExampleTree {

    public static void main(String[] args) {
        if (true) {
            createApp(new DefaultApplication("swing"));
        }
        if (false) {
            createApp(new DefaultApplication("javafx"));
        }
    }

    public static void createApp(Application app) {
        Frame frame = new Frame(app);
        frame.addDefaultMenus();
        Tree<String> tree = new Tree<String>(String.class,app);
        tree.childrenFactory().set(parent -> {
            switch (parent) {
                case "root":
                    return Arrays.asList("one", "two", "three");
                case "one":
                    return Arrays.asList("before", "after");
            }
            return null;
        });
        tree.root().set(tree.nodeOf("root"));
        frame.content().set(tree);
        tree.selection().onChange(c->{
//                    System.out.println(c+" ; \n\t");
            WritableListSelection<AppTreeNode<String>> selection=c.property();
            List<AppTreeNode<String>> appTreeNodes = selection.toList();
            if(appTreeNodes.isEmpty()){
                System.out.println("[      TREE] NOTHING-SELECTED");
            }
            for (int i = 0; i < appTreeNodes.size(); i++) {
                TreeNode<String> n = c.newValue();
//                    System.out.print("[SWING-TREE] ");
                if(i==0) {
                    System.out.print("[      TREE] ");
                }else{
                    System.out.print("             ");
                }
                if(n==null){
                    System.out.println("<null>");
                }else {
                    System.out.println(Arrays.asList(n.computeObjectPath()));
                }
            }
        });

        app.mainFrame().set(frame);
        app.start();
        app.waitFor();
//        JOptionPane.showMessageDialog(null,
//                new Workspace(app)
//                        .peer().toolkitComponent()
//        );
    }

}
