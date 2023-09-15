package by.ycovich.dao;

import by.ycovich.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO book(name, author, year) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updBook){
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year=? WHERE id=?",
                updBook.getName(),updBook.getAuthor(),updBook.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }
}
