/**
 * ====================================================================
 * Echo : Simple Desktop Application Framework
 * <br>
 * Echo is a simple Desktop Application Framework witj productivity in mind.
 * Currently Echo has two ports : swing and javafx
 * <br>
 * <p>
 * Copyright [2021] [thevpc] Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * <br> ====================================================================
 */


package net.thevpc.echo.iconset;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableValueImpl;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.Applications;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author vpc
 */
public class WritableImage extends WritableValueImpl<AppImage> {

    private Str id;
    private Application app;
    private AppComponent component;

    public WritableImage(String name, Application app, AppComponent component) {
        super(name, PropertyType.of(String.class), null);
        this.app = app;
        this.component = component;
        component.iconSet().onChange(e -> {
            reEvalValue();
        });
        component.iconConfig().onChange(e -> {
            reEvalValue();
        });
    }

    public void reEvalValue() {
        if (id != null) {
            Str iconId = this.id;
            String iconId0 = null;
            if (iconId == null || iconId.isEmpty()) {
                superSet(null);
                return;
            } else if (iconId.is18n()) {
                Locale loc = null;
                if (component != null) {
                    loc = component.locale().get();
                }
                String id2 = app.i18n().locale(loc).getString(iconId.value()/*, x -> null*/);
                if (id2 != null) {
                    iconId0 = id2.trim();
                    if (iconId0.isEmpty()) {
                        superSet(null);
                        return;
                    }
                } else {
                    Logger.getLogger(WritableImage.class.getName()).log(
                            Level.FINE, "icon locale missing : " + iconId.value()
                    );
                    superSet(null);
                    return;
                }
            } else {
                iconId0 = iconId.value();
            }
            superSet(app.iconSets().icon(iconId0, component));
        }
    }

    public Str getId() {
        return id;
    }

    public Application app() {
        return app;
    }

    public void load(String url) {
        app().executorService().get().submit(
                () -> {
                    try {
                        final AppImage image = Applications.loadIcon(url, -1, -1, app());
                        app().runUI(() -> {
                            superSet(image);
                        });
                    } catch (Exception ex) {
                        app().runUI(() -> {
                            superSet(null);
                        });
                    }
                }
        );
    }

    public void unset() {
        this.id = null;
        superSet(null);
    }

    public void set(Str id) {
        this.id = id;
        if (id != null) {
            reEvalValue();
        }
    }


    private void superSet(AppImage image) {
        super.set(image);
    }

    @Override
    public void set(AppImage image) {
//        if (id == null) {
        superSet(image);
//        }
    }

}
