package by.ycovich.service;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import by.ycovich.repository.BooksRepository;
import by.ycovich.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Book> getPersonalBooks(Person person){
        return booksRepository.findByOwner(person);
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
}
