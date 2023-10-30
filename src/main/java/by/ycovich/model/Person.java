package by.ycovich.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "enter your full name")
    @Size(min = 2, max = 20, message = "2-20 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "enter your full name")
    @Size(min = 2, max = 30, message = "2-30 characters")
    @Column(name = "lastname")
    private String lastname;

    @Email
    @NotEmpty(message = "email field should not be empty")
    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(String name, String lastname, String email, Date dateOfBirth, Date creationTime) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.creationTime = creationTime;
    }

    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "[name] " + name +
                "[email] " + email +
                "[date-of-birth] " + dateOfBirth +
                "[books] " + getBooks(books);
    }

    public String getFullName(){
        return name + " " + lastname;
    }

    public String getBooks(List<Book> books){
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : books){
            stringBuilder.append(book.getTitle()).append("; ");
        }
        return stringBuilder.toString();
    }
}
