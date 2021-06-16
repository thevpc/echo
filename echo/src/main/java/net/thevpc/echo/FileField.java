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


package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.AllAnchors;
import net.thevpc.echo.constraints.AllMargins;
import net.thevpc.echo.constraints.Fill;
import net.thevpc.echo.constraints.Grow;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.components.FileBase;
import net.thevpc.echo.spi.peers.AppComponentPeer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vpc
 */
public class FileField extends FileBase implements AppFileField, AppContentAdapter {
    private AppPanel panel;
    private AppTextField textField;
    private AppButton browse;
    private AppFileChooser chooser;

    public FileField(Application app) {
        this(null, app);
    }

    public FileField(String id, Application app) {
        super(id, app, AppFileField.class, AppComponentPeer.class);
        chooser = new FileChooser(app);
        textField = new TextField(app())
                .with(t -> {
                    t.childConstraints().addAll(Grow.HORIZONTAL, Fill.HORIZONTAL);
                });
        selection().onChange(() -> {
                    textField.text().set(Str.of(selection().stream().collect(Collectors.joining(";"))));
                }
        );
        textField.text().onChange(() -> {
            String s = textField.text().getOr(x -> x == null ? "" : x.value());
            selection().setAll(toFilePaths(s));
        });
        panel = new GridPane(app)
                .with(p -> {
                    p.parentConstraints().addAll(AllMargins.of(2), AllAnchors.LEFT);
                    p.children().addAll(
                            textField,
                            browse = new Button(app())
                                    .with(t -> {
                                        t.text().set(Str.of("..."));
                                        t.action().set(() -> {
                                            List<String> old = selection().toList();
                                            if (old.stream().anyMatch(x -> x == null || x.startsWith("data:"))) {
                                                //ignore
                                            } else {
                                                chooser().selection().setCollection(old);
                                            }
                                            if (chooser().showOpenDialog(FileField.this)) {
                                                selection().setCollection(chooser().selection().toList());
                                                textField().text().set(
                                                        Str.of(chooser().selection().stream().collect(Collectors.joining(";")))
                                                );
                                            }
                                        });
                                    })

                    );
                })
        ;
        Applications.bindContent(this);
    }

    public AppTextField textField() {
        return textField;
    }

    public AppButton browse() {
        return browse;
    }

    public AppFileChooser chooser() {
        return chooser;
    }

    @Override
    public AppComponent content() {
        return panel;
    }

    private String[] toFilePaths(String s) {
        if (s.startsWith("data:")) {
            return new String[]{s};
        }
        String[] split = s.split(";");
        return Arrays.stream(
                split
        ).map(String::trim).filter(x -> x.length() > 0).toArray(String[]::new);
    }
}
