package by.ycovich.dao;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }

    public List<Book> getBooks(){
        return jdbcTemplate.query("SELECT * FROM book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public List<Book> getBooks(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public Person getBookKeeper(int book_id){
        Integer keeperId = jdbcTemplate.queryForObject("SELECT person_id FROM book WHERE id=?",
                new Object[]{book_id}, Integer.class);
        if (keeperId != null) return personDAO.getPerson(keeperId);
        else return null;
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES (?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updBook){
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?",
                updBook.getTitle(),updBook.getAuthor(),updBook.getYear(), id);
    }

    public void assign(int book_id, int person_id){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", person_id, book_id);
    }

    public void release(int id){
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?",null, id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }
}
