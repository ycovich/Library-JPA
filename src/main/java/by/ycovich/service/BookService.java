package by.ycovich.service;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import by.ycovich.repository.BooksRepository;
import by.ycovich.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> getBooks(){
        return booksRepository.findAll();
    }

    public Book getBook(int id){
        Book book = booksRepository.findById(id);
        return book;
    }

    public Person getKeeper(int id){
        return peopleRepository.findByBooksId(id);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(Book book, Person person){
        book.setOwner(person);
        person.setBooks(new ArrayList<>(Collections.singletonList(book)));
        booksRepository.save(book);
        peopleRepository.save(person);
    }

    @Transactional
    public void release(int id){    // todo
        Person person = peopleRepository.findByBooksId(id);
        List<Book> books = booksRepository.findByOwner(person);
        Book book = booksRepository.findById(id);

        books.remove(id);
        book.setOwner(null);
        person.setBooks(books);

        booksRepository.save(book);
        peopleRepository.save(person);
    }

}
