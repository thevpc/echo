package net.thevpc.echo.api.tools;

import net.thevpc.common.i18n.Str;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class AppFileFilter {
    private Str description;
    private Set<String> extensions;

    public AppFileFilter(Str description, String ... extensions) {
        this.description = description;
        this.extensions = new LinkedHashSet<>(Arrays.asList(extensions));
    }

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
        AppFileFilter that = (AppFileFilter) o;
        return Objects.equals(description, that.description) && Objects.equals(extensions, that.extensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, extensions);
    }
}
