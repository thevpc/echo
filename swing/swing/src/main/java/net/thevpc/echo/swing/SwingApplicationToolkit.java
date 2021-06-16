/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing;

import net.thevpc.common.swing.color.ColorUtils;
import net.thevpc.echo.*;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppUIPlaf;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.iconset.IconTransform;
import net.thevpc.echo.impl.AbstractApplicationToolkit;
import net.thevpc.echo.spi.peers.*;
import net.thevpc.echo.swing.icons.SwingAppImage;
import net.thevpc.echo.swing.icons.SwingColorIconTransform;
import net.thevpc.echo.swing.peers.*;
import net.thevpc.echo.swing.peers.htmleditor.SwingRichHtmlEditorPeer;
import net.thevpc.echo.swing.print.ComponentPrintable;
import net.thevpc.jeep.editor.ColorResource;
import net.thevpc.swing.plaf.UIPlaf;
import net.thevpc.swing.plaf.UIPlafManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import net.thevpc.echo.swing.helpers.SwingHelpers;

/**
 * @author vpc
 */
public class SwingApplicationToolkit extends AbstractApplicationToolkit {

    private Map<String, ButtonGroup> buttonGroups = new HashMap<>();

    public SwingApplicationToolkit() {
        super("swing");
    }

    public void initialize(Application app) {
        super.initialize(app);
        addPeerFactory(AppLabelPeer.class, SwingLabelPeer.class);
        addPeerFactory(AppAlertPeer.class, SwingAlertPeer.class);
        addPeerFactory(AppFileChooserPeer.class, SwingFileChooserPeer.class);
        addPeerFactory(AppFramePeer.class, SwingFramePeer.class);
        addPeerFactory(AppButtonPeer.class, SwingButtonPeer.class);
        addPeerFactory(AppContextMenuPeer.class, SwingContextMenuPeer.class);
        addPeerFactory(AppUserControlPeer.class, SwingUserControlPeer.class);
        addPeerFactory(AppMenuBarPeer.class, SwingMenuBarPeer.class);
        addPeerFactory(AppMenuPeer.class, SwingMenuPeer.class);
        addPeerFactory(AppSeparatorPeer.class, SwingSeparatorPeer.class);
        addPeerFactory(AppSpacerPeer.class, SwingSpacerPeer.class);
        addPeerFactory(AppToggleButtonPeer.class, SwingToggleButtonPeer.class);
        addPeerFactory(AppCheckBoxPeer.class, SwingToggleButtonPeer.class);
        addPeerFactory(AppRadioButtonPeer.class, SwingToggleButtonPeer.class);
        addPeerFactory(AppToolBarPeer.class, SwingToolBarPeer.class);
        addPeerFactory(AppToolBarGroupPeer.class, SwingToolBarGroupPeer.class);
        addPeerFactory(AppPanelPeer.class, SwingPanelPeer.class);
        addPeerFactory(AppWindowPeer.class, SwingWindowPeer.class);
        addPeerFactory(AppDockPeer.class, SwingDockPeer.class);
        addPeerFactory(AppDesktopPeer.class, SwingDesktopPeer.class);
        addPeerFactory(AppTabPanePeer.class, SwingTabPanePeer.class);
        addPeerFactory(AppTextFieldPeer.class, SwingTextFieldPeer.class);
        addPeerFactory(AppTextAreaPeer.class, SwingTextAreaPeer.class);
        addPeerFactory(AppPasswordFieldPeer.class, SwingPasswordFieldPeer.class);
        addPeerFactory(AppChoiceListPeer.class, SwingChoiceListPeer.class);
        addPeerFactory(AppComboBoxPeer.class, SwingComboBoxPeer.class);
        addPeerFactory(AppRichHtmlEditorPeer.class, SwingRichHtmlEditorPeer.class);
        addPeerFactory(AppScrollPanePeer.class, SwingScrollPanePeer.class);
        addPeerFactory(AppTreePeer.class, SwingTreePeer.class);
        addPeerFactory(AppProgressBarPeer.class, SwingProgressBarPeer.class);
        addPeerFactory(AppColorButtonPeer.class, SwingColorButtonPeer.class);
        addPeerFactory(AppWebViewPeer.class, SwingWebViewPeer.class);
        addPeerFactory(AppBreadCrumbPeer.class, SwingBreadCrumbPeer.class);
//        addPeerFactory(AppFontChooserPeer.class, SwingFontChooserPeer.class);
//        addPeerFactory(AppFontButtonPeer.class, SwingFontButtonPeer.class);
        addPeerFactory(AppNumberFieldPeer.class, SwingNumberFieldPeer.class);
//        addPeerFactory(AppFileFieldPeer.class, SwingFileFieldPeer.class);

        UIPlafManager.getCurrentManager().addListener(x -> {
            app.plaf().set(x.getId());
        });

    }

    public ButtonGroup getButtonGroup(String name) {
        ButtonGroup p = buttonGroups.get(name);
        if (p == null) {
            p = new ButtonGroup();
            buttonGroups.put(name, p);
        }
        return p;
    }

    @Override
    public AppComponent createComponent(Object component) {
        if (component instanceof AppComponent) {
            return (AppComponent) component;
        }
        if (component instanceof Component) {
            return new UserControl(null, component, app);
        }
        throw new IllegalArgumentException("unsupported");
    }

    @Override
    public AppImagePeer createImagePeer(InputStream source) {
        try {
            return new SwingAppImage(
                    ImageIO.read(source)
            );
        } catch (IOException e) {
            throw new UncheckedException(e);
        }
    }

    @Override
    public AppImagePeer createImagePeer(double width, double height, AppColor color) {
        BufferedImage image = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
        if (color != null) {
            Graphics2D g = image.createGraphics();
            g.setColor((Color) color.peer().toolkitColor());
            g.fillRect(0, 0, (int) width, (int) height);
        }
        return new SwingAppImage(image);
    }

    @Override
    public AppImagePeer createImagePeer(URL url) {
        return new SwingAppImage(url);
    }

    @Override
    public AppUIPlaf getPlaf(String id) {
        UIPlaf p = UIPlafManager.INSTANCE.get(id);
        return p == null ? null : new SwingUIPlaf(p);
    }

    public AppUIPlaf[] loadAvailablePlafs() {
        return (UIPlafManager.INSTANCE.items())
                .stream().map(x -> new SwingUIPlaf(x))
                .toArray(AppUIPlaf[]::new);
    }

    @Override
    public void applyPlaf(String plaf) {
        if (plaf != null) {
            UIPlafManager.INSTANCE.apply(plaf);
            //UIPlafManager.getCurrentManager().apply(plaf);
        }
    }

    public IconTransform createReplaceColorTransform(AppColor from, AppColor to) {
        return new SwingColorIconTransform(
                (Color) from.peer().toolkitColor(),
                (Color) to.peer().toolkitColor()
                , app);
    }

    public void runUI(Runnable run) {
        if (SwingUtilities.isEventDispatchThread()) {
            run.run();
        } else {
            SwingUtilities.invokeLater(run);
        }
    }

    public void runWorker(Runnable run) {
        if (SwingUtilities.isEventDispatchThread()) {
            //may be should use executor!
            new Thread(run).start();
        } else {
            run.run();
        }
    }

    @Override
    public void runUILater(Runnable run) {
        SwingUtilities.invokeLater(run);
    }

    @Override
    public void runUIAndWait(Runnable run) {
        if (SwingUtilities.isEventDispatchThread()) {
            run.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(run);
            } catch (Exception ex) {
                throw UncheckedException.wrap(ex);
            }
        }
    }

    @Override
    public <T> T callUIAndWait(Callable<T> run) {
        if (SwingUtilities.isEventDispatchThread()) {
            try {
                return run.call();
            } catch (Exception e) {
                throw UncheckedException.wrap(e);
            }
        }
        Object[] result = new Object[1];
        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    result[0] = run.call();
                } catch (Exception ex) {
                    throw UncheckedException.wrap(ex);
                }
            });
        } catch (Exception ex) {
            throw UncheckedException.wrap(ex);
        }
        return (T) result[0];
    }

    @Override
    public AppColorPeer createColorPeer(AppColor color) {
        return new SwingColorPeer(
                new Color(
                        (float) color.red(),
                        (float) color.green(),
                        (float) color.blue(),
                        (float) color.opacity()
                )
        );
    }

    @Override
    public AppFontPeer createFontPeer(AppFont font) {
        return new SwingFontPeer(font);
    }

    @Override
    public int parseColor(String colorText) {
        if(colorText.contains(";")){
            return ColorResource.of(colorText).get().getRGB();
        }
        return ColorUtils.parseColor(colorText).getRGB();
    }

    private static class SwingFontPeer implements AppFontPeer {
        private final Font toolkitFont;

        public SwingFontPeer(AppFont font) {
            int style = ((font.weight().ordinal()
                    > FontWeight.NORMAL.ordinal()) ? Font.BOLD : 0)
                    +
                    ((font.posture() == FontPosture.ITALIC) ? Font.ITALIC : 0);
            toolkitFont = new Font(font.family(),
                    style
                    , (int) font.size()
            );
        }

        @Override
        public Object toolkitFont() {
            return toolkitFont;
        }
    }

    @Override
    public Clipboard systemClipboard() {
        return new Clipboard() {
            @Override
            public void putString(String value) {
                java.awt.datatransfer.Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection data = new StringSelection(value);
                clip.setContents(data, data);
            }

            @Override
            public String getString() {
                try {
                    java.awt.datatransfer.Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable content = clip.getContents(null);
                    String txt = content.getTransferData(
                            new DataFlavor(String.class, "String")).toString();
                    return txt;
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
                return null;
            }
            @Override
            public void putHtml(String value) {
                java.awt.datatransfer.Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                StringSelection data = new StringSelection(value);
                clip.setContents(data, data);
            }

            @Override
            public String getHtml() {
                try {
                    java.awt.datatransfer.Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable content = clip.getContents(null);
                    String txt = content.getTransferData(
                            new DataFlavor(String.class, "String")).toString();
                    return txt;
                } catch (Exception ex) {
                    //ex.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public PrinterService printerService() {
        return new PrinterService() {
            @Override
            public PrinterJob createJob() {
                return new PrinterJob() {
                    @Override
                    public void printComponent(AppComponent component) {
                        JComponent c=(JComponent)component.peer().toolkitComponent();
                        new ComponentPrintable(c).print();
                    }
                };
            }
        };
    }

    @Override
    public AppFont[] availablefonts() {
        Font[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        return Arrays.stream(fontList)
                .map(x->SwingHelpers.fromAwtFont(x, app))
                .toArray(AppFont[]::new);
    }
    
}
