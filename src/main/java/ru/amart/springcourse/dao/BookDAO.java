package ru.amart.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.amart.springcourse.models.Book;
import ru.amart.springcourse.models.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

//    public Optional<Book> show(String name) {
//        return jdbcTemplate.query("SELECT * FROM Book WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Book.class))
//                .stream().findAny();
//    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (name, author, year) VALUES (?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update(" UPDATE Book SET name = ?, author = ?, year = ? WHERE id=?", updatedBook.getName(), updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id =?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id=Person.id " +
                "WHERE Book.id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE  Book SET person_id=NULL WHERE id=?", id);
    }

    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE id=?", selectedPerson.getId(), id);
    }
//    //////////////////////////////////
//    ///////  Тест пакетной вставки
//    /////////////////////////////////
//
//
//    public void testMultipleUpdate() {
//        List<Person> people = create1000People();
//        long before = System.currentTimeMillis();
//        for (Person person:people) {
//            jdbcTemplate.update("INSERT INTO Person VALUES (?, ?, ?)", person.getId(), person.getName(), person.getYear());
//        }
//        long after = System.currentTimeMillis();
//        System.out.println("Time: " + (after-before));
//    }
//
//    private List<Person> create1000People() {
//        List<Person> people = new ArrayList<>();
//
//        for (int i =0; i<1000; i++) {
//            people.add(new Person(i, "Name" +i, 30+i));
//        }
//        return people;
//    }
//
//    public void testBatchUpdate() {
//        List<Person> people = create1000People();
//        long before = System.currentTimeMillis();
//
//        jdbcTemplate.batchUpdate("INSERT INTO Person VALUES (?, ?, ?)",
//                new BatchPreparedStatementSetter() {
//                    @Override
//                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                        preparedStatement.setInt(1, people.get(i).getId());
//                        preparedStatement.setString(2, people.get(i).getName());
//                        preparedStatement.setInt(3, people.get(i).getYear());
//                    }
//
//                    @Override
//                    public int getBatchSize() {
//                        return people.size();
//                    }
//                });
//        long after = System.currentTimeMillis();
//        System.out.println("Time: " + (after-before));
//    }
}
