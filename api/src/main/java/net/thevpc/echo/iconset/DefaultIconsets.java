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

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.DefaultPropertyListeners;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppImage;

/**
 *
 * @author vpc
 */
public class DefaultIconsets extends WritableLiMapAdapter<String, IconSet> implements IconSets {

    private WritableLiMap<String, IconSet> base;
    protected WritableString id = Props.of("id").stringOf( null);
    protected WritableValue<AppIconResolver> resolver = Props.of("id").valueOf(AppIconResolver.class, null);
    private Map<String, WritableValue<AppImage>> icons = new HashMap<>();
    private WritableValue<IconSetConfig> config = Props.of("id").valueOf(IconSetConfig.class, IconSetConfig.of(16));
    private Supplier<IconSetBuilder> builderSupplier;

    public DefaultIconsets(String name) {
        this.base = Props.of(name).lmapOf(String.class, IconSet.class, x -> x.getId());
        id.onChange(this::updateAllLoadedIcons);
        config.onChange(this::updateAllLoadedIcons);
    }

    @Override
    protected ObservableMap<String, IconSet> getObservableMapBase() {
        return base;
    }

    private void updateAllLoadedIcons(PropertyEvent event) {
        IconSet s = this.get(id().get());
        if (s == null) {
            for (Map.Entry<String, WritableValue<AppImage>> entry : icons.entrySet()) {
                entry.getValue().set(null);
            }
        } else {
            IconSetConfig cnf = config.get();
            for (Map.Entry<String, WritableValue<AppImage>> entry : icons.entrySet()) {
                AppImage i = s.getIcon(entry.getKey(), cnf);
                if (i != null) {
                    int iw = (int) i.getHeight();
                    int cw = cnf.getWidth();
                    if (iw != cw) {
                        i = s.getIcon(entry.getKey(), cnf);
                    }
                }
                entry.getValue().set(i);
            }

        }
        ((DefaultPropertyListeners) this.events()).firePropertyUpdated(
                new PropertyEvent(this, -1, false, true,
                        Path.of(propertyName()), PropertyUpdate.UPDATE, "activeIconSetChanged",
                        true
                ));
    }

    @Override
    public WritableValue<IconSetConfig> config() {
        return config;
    }

    @Override
    public WritableString id() {
        return id;
    }

    @Override
    public ObservableValue<AppImage> icon(String id) {
        WritableValue<AppImage> i = icons.get(id);
        if (i == null) {
            IconSet s = iconSet();
            i = Props.of("icon-" + id).valueOf(AppImage.class, s == null ? null : s.getIcon(id, config.get()));
            icons.put(id, i);
        }
        return i;
    }

    @Override
    public AppImage icon(String id, String iconSet) {
        IconSet i = get(iconSet);
        if(i==null){
            i=iconSet();
        }
        if(i==null){
            return null;
        }
        return i.getIcon(id,config().get());
    }

    @Override
    public IconSet iconSet() {
        return this.get(this.id.get());
    }

    @Override
    public WritableValue<AppIconResolver> resolver() {
        return resolver;
    }

    @Override
    public String iconIdForFile(File f, boolean selected, boolean expanded) {
        AppIconResolver r = resolver.get();
        if (r == null) {
            return null;
        }
        return r.iconIdForFile(f, selected, expanded);
    }

    @Override
    public String iconIdForFileName(String f, boolean selected, boolean expanded) {
        AppIconResolver r = resolver.get();
        if (r == null) {
            return null;
        }
        return r.iconIdForFileName(f, selected, expanded);
    }

    @Override
    public ObservableValue<AppImage> iconForFile(File f, boolean selected, boolean expanded) {
        return icon(iconIdForFile(f, selected, expanded));
    }

    @Override
    public ObservableValue<AppImage> iconForFileName(String f, boolean selected, boolean expanded) {
        return icon(iconIdForFileName(f, selected, expanded));
    }

    public Supplier<IconSetBuilder> getBuilderSupplier() {
        return builderSupplier;
    }

    public void setBuilderSupplier(Supplier<IconSetBuilder> builderSupplier) {
        this.builderSupplier = builderSupplier;
    }

    public IconSetBuilder add() {
        if (builderSupplier == null) {
            throw new IllegalArgumentException("missing buildersupplier");
        }
        return new AutoCommitIconSetBuilder(builderSupplier.get());
    }

    public IconSetBuilder builder() {
        if (builderSupplier == null) {
            throw new IllegalArgumentException("missing buildersupplier");
        }
        return builderSupplier.get();
    }

    private class AutoCommitIconSetBuilder implements IconSetBuilder {

        private final IconSetBuilder b;

        public AutoCommitIconSetBuilder(IconSetBuilder b) {
            this.b = b;
        }

        @Override
        public IconSetBuilder name(String name) {
            b.name(name);
            return this;
        }

        @Override
        public IconSetBuilder path(String path) {
            b.path(path);
            return this;
        }

        @Override
        public IconSetBuilder classLoader(ClassLoader loader) {
            b.classLoader(loader);
            return this;
        }

        @Override
        public IconSetBuilder transform(IconTransform transform) {
            b.transform(transform);
            return this;
        }

        @Override
        public IconSetBuilder replaceColor(AppColor from, AppColor to) {
            b.replaceColor(from, to);
            return this;
        }

        @Override
        public IconSet build() {
            IconSet s = b.build();
            add(s);
            return s;
        }
    }
}
