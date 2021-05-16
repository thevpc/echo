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



package net.thevpc.echo;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyVeto;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;

/**
 *
 * @author vpc
 */
public class DefaultApplicationStartupConfig implements ApplicationStartupConfig {

    private Application app;
    private AppStartedVeto appStartedVeto;
    private final WritableBoolean enableQuit = Props.of("enableQuit").booleanOf(true);
    private final WritableBoolean enableDocking = Props.of("enableDocking").booleanOf(false);
    private final WritableBoolean enablePlaf = Props.of("enablePlaf").booleanOf(true);
    private final WritableBoolean enableIcons = Props.of("enableIcons").booleanOf(true);
    private final WritableValue<String[]> enableLocales = Props.of("enableLocales").valueOf(String[].class, null);
    private final WritableBoolean enableInternalFrames = Props.of("enableInternalFrames").booleanOf(false);
    private final WritableBoolean enableAppearance = Props.of("enableAppearance").booleanOf(true);

    public DefaultApplicationStartupConfig() {
    }

    public void prepare(Application app) {
        this.app = app;
        appStartedVeto = new AppStartedVeto(app);
        enableQuit.vetos().add(appStartedVeto);
        enableDocking.vetos().add(appStartedVeto);
        enablePlaf.vetos().add(appStartedVeto);
        enableIcons.vetos().add(appStartedVeto);
        enableLocales.vetos().add(appStartedVeto);
        enableInternalFrames.vetos().add(appStartedVeto);
        enableAppearance.vetos().add(appStartedVeto);
    }

    @Override
    public WritableBoolean enableDocking() {
        return enableDocking;
    }

    @Override
    public WritableBoolean enablePlaf() {
        return enablePlaf;
    }

    @Override
    public WritableBoolean enableIcons() {
        return enableIcons;
    }

    @Override
    public WritableValue<String[]> enableLocales() {
        return enableLocales;
    }

    @Override
    public WritableBoolean enableDesktop() {
        return enableInternalFrames;
    }

    @Override
    public WritableBoolean enableAppearance() {
        return enableAppearance;
    }

    @Override
    public WritableBoolean enableQuit() {
        return enableQuit;
    }

    private static class AppStartedVeto implements PropertyVeto {

        private final Application app;

        public AppStartedVeto(Application app) {
            this.app = app;
        }

        @Override
        public void vetoableChange(PropertyEvent x) {
            if (app.state().get() != AppState.INIT
                    && app.state().get() != AppState.NONE) {
                throw new IllegalArgumentException("Application already started");
            }
        }
    }

}
