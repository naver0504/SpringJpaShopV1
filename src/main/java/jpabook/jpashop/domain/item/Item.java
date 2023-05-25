package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.Comment;
import jpabook.jpashop.domain.ItemImg;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;


    @ManyToOne(fetch =  LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, mappedBy = "item", cascade = CascadeType.ALL)
    @JoinColumn(name = "itemImg_id")
    private ItemImg itemImg;


    @ManyToMany(mappedBy = "items", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();


    @OneToMany(mappedBy = "item")
    private List<Comment> comments = new ArrayList<>();


    //비즈니스 로직//
    public void addStock(int quantity) {

        this.stockQuantity += quantity;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getItems().add(this);
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
