package ru.amart.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.amart.springcourse.dao.BookDAO;
import ru.amart.springcourse.dao.PersonDAO;
import ru.amart.springcourse.models.Book;
import ru.amart.springcourse.models.Person;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if (bookDAO.show(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "This name is already taken");
        }
    }
}
