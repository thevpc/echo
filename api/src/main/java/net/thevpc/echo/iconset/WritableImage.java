/**
 * ====================================================================
 * Echo : Simple Desktop Application Framework
 * <br>
 * Echo is a simple Desktop Application Framework witj productivity in mind.
 * Currently Echo has two ports : swing and javafx
 * <br>
 *
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

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableValueImpl;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.Application;
import net.thevpc.common.i18n.Str;

/**
 *
 * @author vpc
 */
public class WritableImage extends WritableValueImpl<AppImage> {

    private Str id;
    private Application app;

    public WritableImage(String name, Application app) {
        super(name, PropertyType.of(String.class), null);
        this.app = app;
        app.iconSets().onChange(e -> {
            reevalValue();
        });
    }

    public void reevalValue() {
        if (id != null) {
            Str iconId = this.id;
            String iconId0=null;
            if (iconId==null || iconId.isEmpty()) {
                superSet(null);
                return;
            }else if (iconId.is18n()) {
                String id2 = app.i18n().getString(iconId.value(), x -> null);
                if (id2 != null) {
                    iconId0 = id2.trim();
                    if (iconId0.isEmpty()) {
                        superSet(null);
                        return;
                    }
                } else {
                    superSet(null);
                    return;
                }
            }else{
                iconId0=iconId.value();
            }
            superSet(app.iconSets().icon(iconId0).get());
        }
    }

    public Str getId() {
        return id;
    }

    public void set(Str id) {
        this.id = id;
        if (id != null) {
            reevalValue();
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
