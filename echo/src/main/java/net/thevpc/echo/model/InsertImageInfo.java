package net.thevpc.echo.model;

public class InsertImageInfo {
    private String imageURL;
    private String text;
    private Integer width;
    private Integer height;

    public InsertImageInfo(String imageURL, String text, Integer width, Integer height) {
        this.imageURL = imageURL;
        this.text = text;
        this.width = width;
        this.height = height;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getText() {
        return text;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
