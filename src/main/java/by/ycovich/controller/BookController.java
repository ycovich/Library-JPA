package by.ycovich.controller;

import by.ycovich.model.Book;
import by.ycovich.model.Person;
import by.ycovich.service.BookService;
import by.ycovich.service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value = {"/books", "/books/"})
public class BookController {
    private final BookService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping()
    public String getBooks(Model model,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                           @RequestParam(value = "sortByYear", required = false) boolean sortByYear) {
        if (page == null || booksPerPage == null){
            model.addAttribute("books", booksService.getBooks(sortByYear));
        } else {
            model.addAttribute("books", booksService.getBooks(page, booksPerPage, sortByYear));
        }
        return "books/all";
    }

    @GetMapping("/search")
    public String searchBooks(Model model,
                              @RequestParam(value = "keyword") String keyword) {
        List<Book> books = booksService.getBooks(keyword);
        model.addAttribute("books", books);
        return "books/all";
    }


    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") int id,
                          @ModelAttribute("person") Person person,
                          Model model){
        model.addAttribute("book", booksService.getBook(id));
        model.addAttribute("keeper", booksService.getKeeper(id));
        model.addAttribute("people", peopleService.getPeople());
        return "books/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id,
                       Model model){
        model.addAttribute("book", booksService.getBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("person") Person keeper){
        Person person = peopleService.getPerson(keeper.getId());
        booksService.assign(booksService.getBook(id), person);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

}
