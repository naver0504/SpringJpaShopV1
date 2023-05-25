package jpabook.jpashop.controller.dto;

import jpabook.jpashop.domain.ItemImg;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    @NotNull
    private MultipartFile file;
    private String path;

    public static BookForm ItemToForm(Book item, ItemImg itemImg) {
        BookForm bookForm = new BookForm();
        bookForm.id = item.getId();
        bookForm.author = item.getAuthor();
        bookForm.isbn = item.getIsbn();;
        bookForm.name = item.getName();;
        bookForm.price = item.getPrice();;
        bookForm.stockQuantity = item.getStockQuantity();
        bookForm.path = itemImg.getFilePath();

        return bookForm;
    }

}
