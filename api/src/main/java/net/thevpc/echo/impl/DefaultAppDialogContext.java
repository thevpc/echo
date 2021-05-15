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



package net.thevpc.echo.impl;

import net.thevpc.echo.api.components.AppAlert;
import net.thevpc.echo.AppDialogContext;

/**
 *
 * @author vpc
 */
public class DefaultAppDialogContext implements AppDialogContext {
    
    private String buttonId;
    private int buttonIndex;
    private AppAlert dialog;

    public DefaultAppDialogContext(AppAlert dialog, String buttonId, int buttonIndex) {
        this.dialog = dialog;
        this.buttonId = buttonId;
        this.buttonIndex = buttonIndex;
    }

   

    public AppAlert getDialog() {
        return dialog;
    }

    public String getButtonId() {
        return buttonId;
    }

    public int getButtonIndex() {
        return buttonIndex;
    }
    
}
