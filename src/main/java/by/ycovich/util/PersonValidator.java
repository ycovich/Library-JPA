package by.ycovich.util;

import by.ycovich.model.Person;
import by.ycovich.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (peopleService.getPerson(person.getName().trim(), person.getLastname().trim(), person.getEmail().trim()).isPresent()) {
            errors.rejectValue("name", "", "account with this name already exists");
            errors.rejectValue("lastname", "", "account with this lastname already exists");
            errors.rejectValue("email", "", "account with this email already exists");
        }
    }
}
