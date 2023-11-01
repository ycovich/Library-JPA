package by.ycovich.service;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import by.ycovich.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getBooks(){
        return booksRepository.findAll();
    }

    public List<Book> getBooks(boolean sortByYear){
        if (sortByYear) return booksRepository.findAll(Sort.by("year"));
        else return booksRepository.findAll();
    }

    public List<Book> getBooks(int page, int itemsPerPage, boolean sortByYear){
        if (sortByYear) return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
        else return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }

    public List<Book> getBooks(String keyword){
        return booksRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
    }

    public Book getBook(int id){
        return booksRepository.findById(id).orElse(null);
    }

    public Person getKeeper(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        updatedBook.setBorrowTime(bookToBeUpdated.getBorrowTime());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(Book book, Person person){
        book.setOwner(person);
        book.setBorrowTime(LocalDateTime.now());
        booksRepository.save(book);
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book1 -> {
                    book1.setOwner(null);
                    book1.setBorrowTime(null);
                });
    }

}
