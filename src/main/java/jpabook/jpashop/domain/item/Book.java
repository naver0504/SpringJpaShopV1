package jpabook.jpashop.domain.item;

import jpabook.jpashop.controller.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("B")
public class Book extends Item{

    private String author;
    private String isbn;

    public static CommentDTO BookToDTO(Item item) {
        CommentDTO bookDTO = new CommentDTO();
        return bookDTO;
    }
}
