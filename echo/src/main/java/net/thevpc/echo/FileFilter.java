package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.AppFileFilter;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class FileFilter implements AppFileFilter {
    private Str description;
    private Set<String> extensions;

    public FileFilter(Str description, String ... extensions) {
        this.description = description;
        this.extensions = new LinkedHashSet<>(Arrays.asList(extensions));
    }

    @Override
    public Str getDescription() {
        return description;
    }

    public Set<String> getExtensions() {
        return extensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileFilter that = (FileFilter) o;
        return Objects.equals(description, that.description) && Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, extensions);
    }
}
