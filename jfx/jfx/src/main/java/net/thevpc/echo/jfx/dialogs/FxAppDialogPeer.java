///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.thevpc.echo.jfx.dialogs;
//
//import net.thevpc.echo.*;
//
//import java.text.MessageFormat;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Supplier;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.VBox;
//import javafx.stage.Window;
//import net.thevpc.common.props.ObservableValue;
//import net.thevpc.echo.api.Path;
//import net.thevpc.echo.api.components.AppAlert;
//import net.thevpc.echo.api.components.AppComponent;
//import net.thevpc.echo.api.tools.AppComponentModel;
//import net.thevpc.echo.impl.DefaultAppDialogContext;
//import net.thevpc.echo.impl.dialog.DefaultAppDialogResult;
//import net.thevpc.echo.jfx.FxPeer;
//import net.thevpc.echo.api.peers.AppAlertPeer;
//
///**
// *
// * @author vpc
// */
//public class FxAppDialogPeer extends Alert implements AppAlertPeer {
//
//    private Supplier<?> valueEvaluator;
//    private Node mainComponent;
//    private AppDialogAction cons;
//    private LinkedHashMap<String, ButtonType> buttons;
//    protected Application app;
//    protected String selectedButton;
//    protected String[] buttonIds;
//    protected AppAlert binding;
//
//    public FxAppDialogPeer(Application app, AppAlert binding,String titleId, Node mainComponent, String[] buttonIds, String defaultId, AppDialogAction cons,
//                           Supplier<?> valueEvaluator,
//                           Object... params) {
//        this(app, binding,titleId, valueEvaluator, params);
//        build(mainComponent, buttonIds, defaultId, cons);
//    }
//
//    public FxAppDialogPeer(Application app, AppAlert binding,String titleId, Supplier<?> valueEvaluator, Object... params) {
//        super(AlertType.CONFIRMATION, "Title");
//        this.setTitle(buildMessage(app, titleId, params));
//        this.setHeaderText(buildMessage(app, titleId, params));
//        this.setContentText("I have a great message for you!");
//        this.app = app;
//        this.valueEvaluator = valueEvaluator;
//        this.binding = binding;
//    }
//
//    private static String buildMessage(Application app1, String titleId, Object... params) {
//        String m = app1.i18n().getString(titleId);
//        if (params.length > 0) {
//            m = MessageFormat.format(m, params);
//        }
//        return m;
//    }
//
//    @Override
//    public void closeDialog() {
//        this.hide();
//    }
//
//    @Override
//    public AppDialogResult showInputDialog() {
//        String a = showDialog();
//        return new DefaultAppDialogResult(a,valueEvaluator, app);
//    }
//
//    public String showDialog() {
//        this.selectedButton = null;
//        Optional<ButtonType> result = this.showAndWait();
////        AppFrame f = app.mainFrame().get();
////        if (f instanceof SwingFrameAppWindow) {
////            setLocationRelativeTo(((SwingFrameAppWindow) f).getFrame());
////        }
////        setVisible(true);
//        int index = 0;
//        for (Map.Entry<String, ButtonType> entry : buttons.entrySet()) {
//            if (result.get() == entry.getValue()) {
//                if (cons != null) {
//                    cons.onAction(new DefaultAppDialogContext(binding, entry.getKey(), index));
//                }
//                return selectedButton = entry.getKey();
//            }
//            index++;
//        }
//        return selectedButton==null?"":selectedButton;
//    }
//
//    public void build(Node mainComponent, String[] buttonIds, String defaultId, AppDialogAction cons) {
//        this.mainComponent = mainComponent;
//        this.cons = cons;
//        this.getDialogPane().setContent(mainComponent);
//        buttons = new LinkedHashMap<>();
//        for (int i = 0; i < buttonIds.length; i++) {
//            String id = buttonIds[i];
//            ButtonType b = new ButtonType(app.i18n().getString("Message." + id));
////            b.getButtonData().putClientProperty("ActionInfo", new ActionInfo(i, id));
//            buttons.put(id, b);
//            //b.addActionListener(actionListenerImpl);
//        }
//        getButtonTypes().setAll(buttons.values());
//        Window window = this.getDialogPane().getScene().getWindow();
//        window.setOnCloseRequest(e -> this.hide());
//
////        getRootPane().setLayout(new BorderLayout());
////        footer = new GenFooter(app, cons, buttonIds);
////        JPanel withBorder = new JPanel(new BorderLayout());
////        withBorder.add(mainComponent);
////        withBorder.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
////        getRootPane().add(withBorder, BorderLayout.CENTER);
////        getRootPane().add(footer, BorderLayout.SOUTH);
////        SwingUtilities3.addEscapeBindings(this);
////        if (defaultId != null) {
////            this.getRootPane().setDefaultButton(footer.getButton(defaultId));
////        }
////        this.setLocationRelativeTo(getOwner());
////        pack();
//    }
//
//}
