package by.ycovich.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "enter your full name")
    @Size(min = 2, max = 45, message = "2-45 characters")
    private String name;
    @Min(value = 1900, message = "invalid year input")
    private int year;

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Person(){}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}


    public String getName() {return name;}
    public void setName(String name) {this.name = name;}


    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

}
