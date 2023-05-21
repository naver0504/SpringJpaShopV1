package jpabook.jpashop.domain;


import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    private String memberName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;


    private String content;


    public void setItem(Item item) {
        this.item = item;
        item.getComments().add(this);
    }

    public static Comment createComment(Member member, Item item, String content) {

        Comment comment = new Comment();
        comment.memberName = member.getName();
        comment.setItem(item);
        comment.setContent(content);
        comment.setCreatedTime(new Date());
        return comment;
    }
}
