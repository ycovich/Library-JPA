package by.ycovich.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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


    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(int id, String title, String author, int year, Person owner) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
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

    @Override
    public String toString() {
        return "\n[title] " + title +
                "\n [keeper] " + owner.getFullName() + ", " + owner.getEmail();
    }


}
