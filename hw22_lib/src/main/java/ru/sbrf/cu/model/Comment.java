package ru.sbrf.cu.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "comment")
public class Comment implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "text")
    private String text;
    @Column(name = "comment_author")
    private String commentAuthor;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Book book;

    public Comment() {
    }

    public Comment(long id, String text, String commentAuthor, Book book) {
        this.id = id;
        this.text = text;
        this.commentAuthor = commentAuthor;
        this.book = book;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", commentAuthor='" + commentAuthor + '\'' +
                ", book_id=" + book.getId() +
                '}';
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(text, comment.text) &&
                Objects.equals(commentAuthor, comment.commentAuthor) &&
                Objects.equals(book, comment.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, commentAuthor, book);
    }
}
