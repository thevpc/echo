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

import java.util.Objects;

/**
 *
 * @author vpc
 */
public class IconSetConfig {

    public static final IconSetConfig DEFAULT = of(-1);

    public static IconSetConfig of(int size) {
        return new IconSetConfig(size, size, null);
    }

    private final int width;
    private final int height;
    private final IconTransform transform;

    public IconSetConfig() {
        this(-1, -1, null);
    }

    public IconSetConfig(int width, int height, IconTransform transform) {
        this.width = width;
        this.height = height;
        this.transform = transform;
    }

    public IconSetConfig setTransform(IconTransform transform) {
        return new IconSetConfig(width, height, transform);
    }

    public IconSetConfig setSize(int size) {
        return new IconSetConfig(size, size, transform);
    }

    public IconSetConfig setWidth(int width) {
        return new IconSetConfig(width, height, transform);
    }

    public IconSetConfig setHeight(int height) {
        return new IconSetConfig(width, height, transform);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public IconTransform getTransform() {
        return transform;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.width;
        hash = 17 * hash + this.height;
        hash = 17 * hash + Objects.hashCode(this.transform);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IconSetConfig other = (IconSetConfig) obj;
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (!Objects.equals(this.transform, other.transform)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IconSetConfig{" + "width=" + width + ", height=" + height + ", transform=" + transform + '}';
    }

}
