package by.ycovich.repository;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person person);

    Book findById(int id);

}
