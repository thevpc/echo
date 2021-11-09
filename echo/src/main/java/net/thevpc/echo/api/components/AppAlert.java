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
package net.thevpc.echo.api.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.WritableString;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.iconset.WritableImage;
import net.thevpc.echo.spi.peers.AppAlertPeer;
import net.thevpc.echo.api.AppAlertAction;
import net.thevpc.echo.api.AppAlertInputPane;
import net.thevpc.echo.api.AppAlertResult;

/**
 * @author thevpc
 */
public interface AppAlert extends AppControl {

    WritableValue<AppComponent> owner();

    WritableStr headerText();

    WritableImage headerIcon();

    WritableString defaultButton();

    AppAlert setInputTextFieldContent(Str msg, Str initialValue);

    AppAlert setInputTextAreaContent(Str msg, Str initialValue);

    AppAlert setInputContent(AppAlertInputPane inputPanel);

    /**
     * @see net.thevpc.echo.api.AppAlertInputPane
     * @return content property
     */
    WritableValue<AppComponent> content();

    AppAlert setContent(AppComponent mainComponent);

    AppAlert setContent(Object mainComponent);

    AppAlert setContentText(Str msg);

    AppAlert withOkOnlyButton();

    AppAlert withOkOnlyButton(AppAlertAction ok);

    AppAlert withOkCancelButtons();

    AppAlert withOkCancelButtons(AppAlertAction ok, AppAlertAction cancel);

    AppAlert withYesNoButtons();

    AppAlert withYesNoButtons(AppAlertAction yes, AppAlertAction no);

    AppAlert withYesNoCancelButtons();

    AppAlert withYesNoCancelButtons(AppAlertAction yes, AppAlertAction no, AppAlertAction cancel);

    AppAlert withButtons(String... buttonIds);

    AppAlert setDefaultButton(String s);

    AppAlert setButtonAction(String s, AppAlertAction r);

    default AppAlertResult showDialog() {
        return showDialog(null);
    }

    AppAlertResult showDialog(AppComponent owner);

    void closeAlert();

    AppAlertPeer peer();

}
