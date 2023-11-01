package by.ycovich.repository;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Page<Book> findAll(Pageable var1);

    List<Book> findAll(Sort var1);

    List<Book> findByOwner(Person person);

    Book findById(int id);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);

}
