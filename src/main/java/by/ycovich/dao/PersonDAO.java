package by.ycovich.dao;

import by.ycovich.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople(){
        return jdbcTemplate.query("SELECT * FROM person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO person(name, year) VALUES (?,?)", person.getName(), person.getYear());
    }

    public void update(int id, Person updPerson){
        jdbcTemplate.update("UPDATE person SET name=?, year=? WHERE id=?",
                updPerson.getName(), updPerson.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
