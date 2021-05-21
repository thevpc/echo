package net.thevpc.echo.swing;

import net.thevpc.echo.api.ApplicationToolkit;
import net.thevpc.echo.api.ApplicationToolkitFactory;
import net.thevpc.echo.api.SupportSupplier;

public class SwingApplicationToolkitFactory implements ApplicationToolkitFactory {
    @Override
    public SupportSupplier<ApplicationToolkit> createToolkit(String toolkitId) {
        if("swing".equalsIgnoreCase(toolkitId)){
            return new ApplicationToolkitSupportSupplier(10);
        }
        if(toolkitId==null || "".equals(toolkitId)){
            return new ApplicationToolkitSupportSupplier(1);
        }
        return null;
    }

    private static class ApplicationToolkitSupportSupplier implements SupportSupplier<ApplicationToolkit> {
        private int lvl;

        public ApplicationToolkitSupportSupplier(int lvl) {
            this.lvl = lvl;
        }

        @Override
        public int getSupportLevel() {
            return lvl;
        }

        @Override
        public ApplicationToolkit get() {
            return new SwingApplicationToolkit();
        }
    }
}
