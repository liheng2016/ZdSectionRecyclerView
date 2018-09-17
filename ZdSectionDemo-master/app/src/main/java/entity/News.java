package entity;

import java.io.Serializable;

public class News implements Serializable {

    private String title;

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public News(String title) {
        this.title = title;
    }

    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}