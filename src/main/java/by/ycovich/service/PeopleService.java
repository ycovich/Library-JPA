package by.ycovich.service;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import by.ycovich.repository.BooksRepository;
import by.ycovich.repository.PeopleRepository;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> getPeople(){
        return peopleRepository.findAll();
    }

    public Person getPerson(int id){
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElse(null);
    }

    public Optional<Person> getPerson(String name, String lastname, String email){
        return peopleRepository.findByNameAndLastnameAndEmail(name, lastname, email);
    }

    public List<Book> getPersonalBooks(Person person){
        List<Book> books = booksRepository.findByOwner(person);
        checkIfOverdue(books);
        return books;
    }

    @Transactional
    public void save(Person person){
        person.setCreationTime(new Date());
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    @Transient
    public void checkIfOverdue(List<Book> books){
        for (Book book : books){
            if (book.getBorrowTime() == null) return;
            LocalDateTime currentTime = LocalDateTime.now();
            long daysDifference = ChronoUnit.DAYS.between(book.getBorrowTime().toLocalDate(), currentTime.toLocalDate());
            book.setOverdue(daysDifference > 10);
        }
    }
}
