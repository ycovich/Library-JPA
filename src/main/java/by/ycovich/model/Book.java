package by.ycovich.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "enter the book's title")
    @Size(min = 2, max = 100, message = "2-100 characters")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "enter the author's name")
    @Size(min = 2, max = 100, message = "2-100 characters")
    @Column(name = "author")
    private String author;

    @Min(value = 1700, message = "too old book for our library")
    @Column(name = "year")
    private int year;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "borrow_time")
    private LocalDateTime borrowTime;

    @Transient
    private boolean isOverdue;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(String title, String author, int year, LocalDateTime borrowTime, Person owner) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.borrowTime = borrowTime;
        this.owner = owner;
    }

    public Book() {}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}

    public int getYear() {return year;}

    public void setYear(int year) {this.year = year;}

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public LocalDateTime getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(LocalDateTime borrowTime) {
        this.borrowTime = borrowTime;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    @Override
    public String toString() {
        return "\n[title] " + title +
                "\n [keeper] " + owner.getFullName() + ", " + owner.getEmail();
    }


}
