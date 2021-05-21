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



package net.thevpc.echo.api.components;

import net.thevpc.echo.api.AppDialogAction;
import net.thevpc.echo.api.AppDialogInputPanel;
import net.thevpc.echo.api.AppDialogResult;
import net.thevpc.echo.Dimension;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.spi.peers.AppAlertPeer;

/**
 * @author vpc
 */
public interface AppAlert extends AppControl{

    Dimension getPreferredSize();

    AppAlert setPreferredSize(Dimension preferredSize);

    AppAlert setPreferredSize(int width, int height);

    AppAlert setTitle(Str titleId, Object... params);

    AppAlert setInputTextFieldContent(Str headerId, Str initialValue);

    AppAlert setInputTextAreaContent(Str headerId, Str initialValue);

    AppAlert setInputContent(AppDialogInputPanel inputPanel);

    AppComponent getContent();

    AppAlert setContentText(Str labelId);

    AppAlert setContent(AppComponent mainComponent);

    AppAlert setContent(Object mainComponent);

    AppAlert withOkOnlyButton();

    AppAlert withOkOnlyButton(AppDialogAction ok);

    AppAlert withOkCancelButtons();

    AppAlert withOkCancelButtons(AppDialogAction ok, AppDialogAction cancel);

    AppAlert withYesNoButtons();

    AppAlert withYesNoButtons(AppDialogAction yes, AppDialogAction no);

    AppAlert withYesNoCancelButtons();

    AppAlert withYesNoCancelButtons(AppDialogAction yes, AppDialogAction no, AppDialogAction cancel);

    AppAlert withButtons(String... buttonIds);

    AppAlert setDefaultId(String defaultId);

    AppAlert setButtonHandler(String s, AppDialogAction r);

    String showDialog(AppComponent owner);

    AppDialogResult showInputDialog(AppComponent owner);
    void closeDialog();
    AppAlertPeer peer();
    AppAlertPeer peer(boolean b);


}
