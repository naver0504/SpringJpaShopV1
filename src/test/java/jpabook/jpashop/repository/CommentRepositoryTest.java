package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Comment;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static jpabook.jpashop.domain.Comment.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    CommentService commentService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void Comment() {
        Member member = new Member();
        member.setName("Test1");
        member.setLoginId("Test1");
        memberRepository.save(member);

        Member member2 = new Member();
        member2.setName("Test12");
        member2.setLoginId("Test21");
        memberRepository.save(member2);

        System.out.println("member2 = " + member2.getName());

        Book book = new Book();
        book.setName("JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        itemRepository.save(book);

        Comment comment1 = createComment(member, book, "Test11");
        Comment comment2 = createComment(member2, book, "Test1122");

        commentService.save(comment1);
        commentService.save(comment2);

        Item withComment = itemRepository.findWithComment(book.getId());


        List<Comment> comments = withComment.getComments();

        for (Comment comment : comments) {
            System.out.println("comment = " + comment);
        }

    }

    @Test
    @Transactional
    void testComment() {
        Member member = new Member();
        member.setName("Test");
        member.setLoginId("Test");
        em.persist(member);

        Book book = new Book();
        book.setName("JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        Comment comment = createComment(member, book, "연습");
        em.persist(comment);
        System.out.println("comment = " + comment);
    }

}