package net.thevpc.echo.swing.print;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.print.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ComponentPrintable implements Printable, Pageable {
    private Component componentToBePrinted;
    private PageFormat format;
    private int numPages;
    int headerOffset=0;
    int footerOffset=0;

    public ComponentPrintable(Component componentToBePrinted) {
        boolean changes = true;
        while (changes) {
            changes = false;
            if (componentToBePrinted instanceof JScrollPane) {
                componentToBePrinted = ((JScrollPane) componentToBePrinted).getViewport().getView();
                changes = true;
            }
            if (componentToBePrinted instanceof Container
                    && ((Container) componentToBePrinted).getComponentCount() == 1) {
                componentToBePrinted = ((Container) componentToBePrinted).getComponent(0);
                changes = true;
            }
            if (!changes) {
                if (componentToBePrinted instanceof Container) {
                    java.util.List<Component> c = new ArrayList<>(
                            Arrays.asList(
                                    ((Container) componentToBePrinted).getComponents()
                            )
                    ).stream().filter(x -> (!(x instanceof Box.Filler))).collect(Collectors.toList());
                    if (c.size() == 1) {
                        componentToBePrinted = c.get(0);
                        changes = true;
                    }
                }
            }
        }
        this.componentToBePrinted = componentToBePrinted;

    }

    public static double getScaleFactorToFit(Dimension original, Dimension toFit) {
        double dScale = 1d;
        if (original != null && toFit != null) {
            double dScaleWidth = getScaleFactor(original.width, toFit.width);
            double dScaleHeight = dScaleWidth;//getScaleFactor(original.height, toFit.height);
            dScale = Math.min(dScaleHeight, dScaleWidth);
        }
        return dScale;
    }

    public static double getScaleFactor(int iMasterSize, int iTargetSize) {
        double scale = 1;
        if (iMasterSize > iTargetSize) {
            scale = (double) iTargetSize / (double) iMasterSize;
        } else {
            scale = (double) iTargetSize / (double) iMasterSize;
        }
        return scale;

    }

    public static void disableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    public static void enableDoubleBuffering(Component c) {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }

    public void print() {
        PrinterJob printJob = PrinterJob.getPrinterJob();

        printJob.setPrintable(this);
        printJob.setPageable(this);

        if (printJob.printDialog()) {
            try {
                format = printJob.defaultPage();

                Dimension compSize = getComponentSize();
                componentToBePrinted.setSize(compSize);
                double imageableHeight = format.getImageableHeight()-headerOffset-footerOffset;
                numPages = (int) Math.ceil(compSize.height / imageableHeight);
                printJob.print();
            } catch (PrinterException pe) {
                System.out.println("Error printing: " + pe);
            }
        }
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        if ((pageIndex < 0) | (pageIndex >= numPages)) {
            return (NO_SUCH_PAGE);
        } else {
            disableDoubleBuffering(componentToBePrinted);
            Dimension compSize = getComponentSize();
            componentToBePrinted.setSize(compSize);
            Dimension printSize = new Dimension();
            printSize.setSize(pageFormat.getImageableWidth(), pageFormat.getImageableHeight());

            double scaleFactor = getScaleFactorToFit(compSize, printSize);
            if (scaleFactor > 1d) {
                scaleFactor = 1d;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            double x = pageFormat.getImageableX();
            double y = pageFormat.getImageableY()+headerOffset
                    - pageIndex * (pageFormat.getImageableHeight()-headerOffset-footerOffset);

            AffineTransform at = new AffineTransform();
            at.translate(x, y);
            at.scale(scaleFactor, scaleFactor);
            g2.transform(at);
            componentToBePrinted.printAll(g2);
            if(headerOffset>0) {
                g2.drawString("Hello", 20, -10);
            }
            if(footerOffset>0) {
                g2.drawString("Bye", 20, (int) (pageFormat.getImageableHeight() - headerOffset));
            }
            g2.dispose();

            componentToBePrinted.revalidate();
            enableDoubleBuffering(componentToBePrinted);
            return (PAGE_EXISTS);
        }
    }

    private Dimension getComponentSize() {
        return componentToBePrinted.getSize();
//        return componentToBePrinted.getPreferredSize();
    }

    @Override
    public int getNumberOfPages() {
        return numPages;
    }

    @Override
    public PageFormat getPageFormat(int pageIndex) {
        return format;
    }

    @Override
    public Printable getPrintable(int pageIndex) {
        return this;
    }
}