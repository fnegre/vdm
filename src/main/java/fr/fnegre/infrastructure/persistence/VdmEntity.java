package fr.fnegre.infrastructure.persistence;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name ="vdm")
public class VdmEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String author;
    private Timestamp publishingDate;
    private String content;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(Timestamp publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "VdmEntity{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", publishingDate=" + publishingDate +
                ", content='" + content + '\'' +
                '}';
    }
}
