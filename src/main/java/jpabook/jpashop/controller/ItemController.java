package jpabook.jpashop.controller;

import jpabook.jpashop.argumentresolver.Login;
import jpabook.jpashop.controller.dto.BookForm;
import jpabook.jpashop.controller.dto.CommentDTO;
import jpabook.jpashop.domain.Comment;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.CommentService;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final MemberRepository memberRepository;
    private final CommentService commentService;

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "/items/createItemForm";
    }

    @PostMapping("/new")
    @Transactional
    public String create(BookForm form, @Login Member member) {

        Member findMember = memberRepository.findOne(member.getId());
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setIsbn(form.getIsbn());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setMember(findMember);
        itemService.saveItem(book);

        return "redirect:/home";
    }

    @GetMapping("/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {

        Item item = itemService.findWithComment(itemId);
        Book book = (Book) item;
        CommentDTO commentDTO = new CommentDTO();
        model.addAttribute("item", book);
        model.addAttribute("commentDTO", commentDTO);
        return "/items/item";
    }

    @PostMapping("/{itemId}")
    public String addComment(@PathVariable Long itemId, @RequestParam("content") String content, @Login Member member,
                            RedirectAttributes redirect, Model model) {

        Item item = itemService.findOne(itemId);
        Comment comment = Comment.createComment(member, item, content);

        redirect.addAttribute("itemId", itemId);
        commentService.save(comment);
        itemService.findWithComment(itemId);

        model.addAttribute("item", item);
        return "redirect:/items/{itemId}";
    }

    @GetMapping
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId, Model model, @Login Member member) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "/items/updateItemForm";

    }

    @PostMapping("/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute BookForm form) {

        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";

    }


}
