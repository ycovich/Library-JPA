package by.ycovich.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;

    @NotEmpty(message = "enter the book's name")
    @Size(min = 2, max = 60, message = "2-60 characters")
    private String title;
    @NotEmpty(message = "enter the author's name")
    @Size(min = 2, max = 45, message = "2-45 characters")
    private String author;
    @Min(value = 1700, message = "too old book for our library")
    private int year;

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
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
}
