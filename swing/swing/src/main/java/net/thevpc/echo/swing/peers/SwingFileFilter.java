package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.AppFileFilter;
import net.thevpc.echo.api.components.AppFileControl;
import net.thevpc.echo.impl.Applications;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.regex.Pattern;

public class SwingFileFilter extends FileFilter {
    private final AppFileFilter filter;
    private final AppFileControl chooser;
    Pattern p;

    public SwingFileFilter(AppFileFilter filter, AppFileControl chooser) {
        this.filter = filter;
        this.chooser = chooser;
        if (filter instanceof net.thevpc.echo.FileFilter) {
            StringBuilder pattern = new StringBuilder();
            for (String extension : ((net.thevpc.echo.FileFilter) filter).getExtensions()) {
                if (pattern.length() > 0) {
                    pattern.append("|");
                } else {
                    pattern.append("^");
                }
                for (char c : extension.toCharArray()) {
                    switch (c) {
                        case '*': {
                            pattern.append(".*");
                            break;
                        }
                        case '?': {
                            pattern.append(".");
                            break;
                        }
                        case '.': {
                            pattern.append("[.]");
                            break;
                        }
                        case '\\':
                        case '[':
                        case ']':
                        case '(':
                        case ')':
                        case '{':
                        case '}':
                        case '^':
                        case '$': {
                            pattern.append("\\").append(c);
                            break;
                        }
                        default: {
                            pattern.append(c);
                        }
                    }
                }
            }
            pattern.append("$");
            p = Pattern.compile(pattern.toString());
        }
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        return p != null && p.matcher(f.getName()).matches();
    }

    @Override
    public String getDescription() {
        return Applications.rawString(filter.getDescription(), chooser);
    }
}
