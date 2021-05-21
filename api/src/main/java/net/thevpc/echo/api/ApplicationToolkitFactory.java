package net.thevpc.echo.api;

public interface ApplicationToolkitFactory {
    SupportSupplier<ApplicationToolkit> createToolkit(String toolkitId);
}
