package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.*;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentEvent;
import net.thevpc.echo.api.components.AppComponentEventListener;
import net.thevpc.echo.api.components.AppEventType;
import net.thevpc.echo.constraints.*;
import net.thevpc.echo.iconset.IconConfig;
import net.thevpc.echo.impl.Applications;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FolderView extends GridPane {
    private static FolderViewThumbResolver DEFAULT_THUMB_RESOLVER = new FolderViewThumbResolver() {

        @Override
        public AppImage resolveFastThumb(String filePath, FileType fileType, FolderView folderView) {
            Application app = folderView.app();
            int imageSize = folderView.imageSize;
            IconConfig iconSizeConfig = IconConfig.of(imageSize);
            if (fileType == FileType.UP) {
                return app.iconSets().iconSet().getIcon("folder-up", iconSizeConfig);
            } else if (fileType == FileType.DIRECTORY) {
                return app.iconSets().iconSet().getIcon("folder", iconSizeConfig);
            } else if (ImageView.isImage(filePath)) {
                return app.iconSets().iconSet().getIcon("datatype.image", iconSizeConfig);
            } else {
                return app.iconSets().iconSet().getIcon("file", iconSizeConfig);
            }
        }

        public AppImage resolveThumb(String filePath, FileType fileType, FolderView folderView) {
            Application app = folderView.app();
            int imageSize = folderView.imageSize;
            IconConfig iconSizeConfig = IconConfig.of(imageSize);
            if (fileType == FileType.UP) {
                return null;//reuse fast icon
            } else if (fileType == FileType.DIRECTORY) {
                return null;//reuse fast icon
            } else if (ImageView.isImage(filePath)) {
                AppImage icon2 = null;
                try {
                    icon2 = Applications.loadIcon(filePath, imageSize, imageSize, app);
                } catch (Exception ex) {
                    //
                }
                return icon2;//reuse fast icon
            } else {
                AppImage icon2 = null;
                try {
                    String cp = Applications.probeContentType(filePath);
                    icon2 = app.iconSets()
                            .iconSet().getIcon("content-type." + cp, iconSizeConfig);
                } catch (Exception ex) {
                    //
                }
                return icon2;//reuse fast icon
            }
        }
    };
    private final List<FolderItemView> cachedItems = new ArrayList<>();
    private int imageSize = 64;
    private int cellWidth = 80;
    private int cellHeight = 80;
    private WritableString folder = Props.of("folder").stringOf(null);
    private WritableValue<String> selection = Props.of("selection").stringOf(null);
    private WritableValue<FolderViewThumbResolver> thumbResolver = Props.of("thumbResolver").valueOf(FolderViewThumbResolver.class, null);

    public FolderView(Application app) {
        this(null, app);
    }

    public FolderView(String id, Application app) {
        super(id, app);
        parentConstraints().addAll(AllMargins.of(5), AllFill.HORIZONTAL, AllAnchors.LEFT, AllGrow.HORIZONTAL,
                ContainerGrow.TOP_LEFT_CORNER
        );
        anchor().set(Anchor.TOP);
        this.bounds().onChangeAndInit(() -> {
            double w = 0;
            Bounds bounds = bounds().get();
            if (bounds != null) {
                w = bounds.getWidth();
            }
            if (parent() != null) {
                Bounds pbounds = parent().bounds().get();
                if (pbounds != null) {
                    double pw = pbounds.getWidth();
                    if (pw < w) {
                        w = pw;
                    }
                }
            }
            int count = (int) w / cellWidth;
            if (count < 2) {
                count = 2;
            }
            parentConstraints().add(ColumnCount.of(count));
        });
        folder.onChange(() -> {
            refresh();
        });
//        app().iconSets().id().onChange(propertyListener);
    }

    public List<File> listFiles(File f) {
        List<File> all = new ArrayList<>();
        if (f != null && f.isDirectory()) {
            File[] listFiles = f.listFiles();
            if (listFiles != null) {
                all.addAll(Arrays.asList(listFiles));
            }
        }
        all.sort((File a, File b) -> {
            int d1 = a.isDirectory() ? 0 : 1;
            int d2 = b.isDirectory() ? 0 : 1;
            int x = d1 - d2;
            if (x != 0) {
                return x;
            }
            return a.getName().toLowerCase().compareTo(b.getName().toLowerCase());
        });

        return all;
    }

    public WritableString folder() {
        return folder;
    }

    public void refresh() {
        children().clear();
        File asFile = Applications.asFile(folder.get());
        if (asFile != null) {
            if (asFile.isDirectory() && asFile.getParentFile() != null) {
                children().add(asComponent(asFile.getParentFile(), "..", children().size()));
            }
            for (File f : listFiles(asFile)) {
                children().add(asComponent(f, null, children().size()));
            }
        }
        app().executorService().get()
                .submit(() -> {
                    for (AppComponent child : children) {
                        FolderItemView f = (FolderItemView) child;
                        f.refreshIfRequired();
                    }
                });
    }

    private AppComponent asComponent(File parentFile, String otherName, int index) {
        synchronized (cachedItems) {
            while (index >= cachedItems.size()) {
                cachedItems.add(new FolderItemView(this, app(), cellWidth, cellHeight, imageSize));
            }
        }
        FolderItemView v = cachedItems.get(index);
        return v.setFile(parentFile, otherName);
    }

    public void disposeComponent() {
//        app().iconSets().id().events().remove(propertyListener);
    }

    public WritableValue<String> selection() {
        return selection;
    }

    protected FolderViewThumbResolver effThumbResolver() {
        FolderViewThumbResolver t = thumbResolver().get();
        return t == null ? DEFAULT_THUMB_RESOLVER : t;
    }

    public WritableValue<FolderViewThumbResolver> thumbResolver() {
        return thumbResolver;
    }

    public enum FileType {
        FILE,
        DIRECTORY,
        UP,
    }

    private static class FolderItemView extends GridPane {
        FolderView viewer;
        File file;
        String otherName;
        int imageSize;
        Label iconLabel;
        Label textLabel;
        FileType fileType;
        boolean requireRefresh;

        public FolderItemView(FolderView viewer0, Application app, int cellWidth, int cellHeight, int imageSize0) {
            super(1, app);
            this.viewer = viewer0;
            this.imageSize = imageSize0;
            parentConstraints().addAll(AllGrow.NONE, AllAnchors.CENTER, AllMargins.of(0), AllFill.NONE, ContainerGrow.ALL);
            iconLabel = new Label(Str.of(""), app());
            iconLabel.textStyle().align().set(Anchor.CENTER);
            iconLabel.icon().unset();
            iconLabel.anchor().set(Anchor.TOP);

            textLabel = new Label(Str.of(""), app());
            textLabel.prefSize().set(new Dimension(50, 20));
            textLabel.textStyle().align().set(Anchor.CENTER);
            textLabel.anchor().set(Anchor.CENTER);

            prefSize().set(new Dimension(cellWidth, cellHeight));
            backgroundColor().set(null);
            //compPanel.opaque().set(false);
            children().addAll(iconLabel, textLabel);
            //textLabel.setHorizontalAlignment(SwingConstants.CENTER);
            AppComponentEventListener eventListener = new AppComponentEventListener() {
                @Override
                public void onEvent(AppComponentEvent event) {
                    switch (event.eventType()) {
                        case MOUSE_ENTER: {
                            Color col = Color.LIGHT_GRAY(app());
                            backgroundColor().set(col);
                            iconLabel.backgroundColor().set(col);
                            textLabel.backgroundColor().set(col);
                            break;
                        }
                        case MOUSE_EXIT: {
                            AppColor col = viewer0.backgroundColor().get();
                            backgroundColor().set(col);
                            iconLabel.backgroundColor().set(col);
                            textLabel.backgroundColor().set(col);
                            break;
                        }
                        case MOUSE_CLICKED: {
                            if(viewer.editable().get()) {
                                if (event.isPrimaryMouseButton() && event.isDoubleClick()) {
                                    viewer0.selection().set(file.getPath());
                                }
                            }
                            break;
                        }
                    }
                }
            };

            this.events().add(eventListener, AppEventType.MOUSE_ENTER, AppEventType.MOUSE_EXIT, AppEventType.MOUSE_CLICKED);
            iconLabel.events().add(eventListener, AppEventType.MOUSE_ENTER, AppEventType.MOUSE_EXIT, AppEventType.MOUSE_CLICKED);
            textLabel.events().add(eventListener, AppEventType.MOUSE_ENTER, AppEventType.MOUSE_EXIT, AppEventType.MOUSE_CLICKED);
        }

        public void refreshIfRequired() {
            if (requireRefresh) {
                requireRefresh = false;
                FolderViewThumbResolver t = viewer.effThumbResolver();
                AppImage a = t.resolveThumb(file.getPath(), fileType, viewer);
                if (a != null) {
                    app().runUI(() -> {
                        iconLabel.icon().set(a);
                    });
                }
            }
        }

        public FolderItemView setFile(File file0, String otherName0) {
            this.file = file0;
            this.requireRefresh = true;
            this.otherName = otherName0;
            FolderViewThumbResolver t = viewer.effThumbResolver();
            this.fileType = FileType.FILE;
            if (file.isDirectory()) {
                if ("..".equals(otherName)) {
                    fileType = FileType.UP;
                } else {
                    fileType = FileType.DIRECTORY;
                }
            }
            AppImage icon = t.resolveFastThumb(file.getPath(), fileType, viewer);
            if (icon == null) {
                icon = DEFAULT_THUMB_RESOLVER.resolveFastThumb(file.getPath(), fileType, viewer);
            }
            iconLabel.icon().set(icon);

            iconLabel.tooltip().set(Str.of(otherName == null ? file.getName() : file.getPath()));
            textLabel.text().set(Str.of(otherName == null ? file.getName() : file.getPath()));
            textLabel.tooltip().set(Str.of(file.getName()));
            return this;
        }
    }
}


