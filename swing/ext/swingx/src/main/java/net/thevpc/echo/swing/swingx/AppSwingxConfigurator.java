/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.swingx;

import java.awt.Component;
import java.util.function.Supplier;
import javax.swing.JOptionPane;

import net.thevpc.echo.AppEvent;
import net.thevpc.echo.AppShutdownVeto;
import net.thevpc.echo.Application;
import net.thevpc.common.msg.ExceptionMessage;
import net.thevpc.common.msg.Message;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritablePValue;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

/**
 *
 * @author thevpc
 */
public class AppSwingxConfigurator {

    private WritablePValue<Supplier<Boolean>> confirmExit = Props.of("confirmExit").valueOf((Class) Supplier.class, null);

    public AppSwingxConfigurator() {

    }

    public WritablePValue<Supplier<Boolean>> confirmExit() {
        return confirmExit;
    }

    public void configure(Application app) {

        app.shutdownVetos().add(new AppShutdownVeto() {
            @Override
            public void vetoableChange(AppEvent event) {
                int a = JOptionPane.showConfirmDialog(
                        (Component) app.mainWindow().get().component(), "Are you sure you want to exit "
                                +app.mainWindow().get().title().get()
                                +"?", "Exit?", JOptionPane.OK_CANCEL_OPTION);
                if (a == JOptionPane.OK_OPTION) {
                    Supplier<Boolean> e = confirmExit().get();
                    if (e != null) {
                        if (e.get()) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                throw new IllegalArgumentException("Exit Canceled...");
            }
        });

        app.errors().listeners().add(e -> {
            Message v = e.getNewValue();
            if (v != null) {
                app.logs().add(v);
                if (v instanceof ExceptionMessage) {
                    JXErrorPane.showDialog((Component) app.mainWindow().get().component(),
                            new ErrorInfo(null, v.getText(), null, null, ((ExceptionMessage) v).getError(), v.getLevel(), null)
                    );
                } else {
                    JXErrorPane.showDialog(
                            (Component) app.mainWindow().get().component(),
                            new ErrorInfo(null, v.getText(), null, null, null, v.getLevel(), null)
                    );
                }
            }
        });
    }
}
