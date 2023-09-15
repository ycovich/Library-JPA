package by.ycovich.controller;

import by.ycovich.dao.BookDAO;
import by.ycovich.dao.PersonDAO;
import by.ycovich.model.Person;
import by.ycovich.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    private final BookDAO bookDAO;
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookDAO = bookDAO;
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping()
    public String getPeople(Model model){
        model.addAttribute("people", personDAO.getPeople());
        return "people/all";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.getPerson(id));
        model.addAttribute("user_books", bookDAO.getBooks(id));
        return "people/user";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }



}
