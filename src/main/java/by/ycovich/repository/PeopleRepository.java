package by.ycovich.repository;

import by.ycovich.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findByNameStartingWith(String startingWith);

    List<Person> findByLastnameStartingWith(String startingWith);

    List<Person> findByNameOrLastname(String name, String lastname);

    List<Person> findByEmail(String email);

    Person findByBooksId(int book_id);
}
