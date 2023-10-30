package by.ycovich.service;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import by.ycovich.repository.BooksRepository;
import by.ycovich.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public List<Book> getBooks(boolean toBeSorted){
        return booksRepository.findAll(Sort.by("year"));
    }

    public List<Book> getBooks(int page, int itemsPerPage){
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List<Book> getBooks(int page, int itemsPerPage, boolean toBeSorted){
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
    }


    public Book getBook(int id){
        return booksRepository.findById(id);
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
        booksRepository.save(book);
    }

    @Transactional
    public void release(int id){
        Book book = booksRepository.findById(id);
        book.setOwner(null);
        booksRepository.save(book);
    }

}
