package fr.fnegre.domain.model.downloading;

import java.time.LocalDateTime;

public class Vdm {
    private String author;
    private LocalDateTime publishingDate;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Vdm{" +
                "author='" + author + '\'' +
                ", publishingDate=" + publishingDate +
                ", content='" + content + '\'' +
                '}';
    }

    public LocalDateTime getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDateTime publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
