/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo;

import java.awt.Dimension;
import javax.swing.JComponent;

/**
 *
 * @author vpc
 */
public interface AppDialogBuilder {

    public Dimension getPreferredSize();

    public AppDialogBuilder setPreferredSize(Dimension preferredSize);

    public AppDialogBuilder setPreferredSize(int width, int heigth);

    public AppDialogBuilder setTitleId(String titleId);

    public AppDialogBuilder setInputTextFieldContent(String headerId, String initialValue);

    public AppDialogBuilder setInputTextAreadContent(String headerId, String initialValue);

    public AppDialogBuilder setInputContent(AppDialogInputPanel inputPanel);

    public AppDialogBuilder setContentText(String labelId);

    public AppDialogBuilder setContentTextId(String labelId);

    public AppDialogBuilder setContent(JComponent mainComponent);

    public AppDialogBuilder withOkOnlyButton();

    public AppDialogBuilder withOkOnlyButton(AppDialogAction ok);

    public AppDialogBuilder withOkCancelButtons();

    public AppDialogBuilder withOkCancelButtons(AppDialogAction ok, AppDialogAction cancel);

    public AppDialogBuilder withYesNoButtons();

    public AppDialogBuilder withYesNoButtons(AppDialogAction yes, AppDialogAction no);

    public AppDialogBuilder withYesNoCancelButtons(AppDialogAction yes, AppDialogAction no, AppDialogAction cancel);

    public AppDialogBuilder withButtons(String... buttonIds);

    public AppDialogBuilder setDefaultId(String defaultId);

    public AppDialogBuilder setButtonHandler(String s, AppDialogAction r);

    public String showDialog();

    public AppDialogResult showInputDialog();

    public AppDialog build();
}
