package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void order() {
        //given
        Member member = createMember();

        Item book = createBook("JPA", 10000, 10);
        em.persist(book);

        int count = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(1).isEqualTo(findOrder.getOrderItems().size());
        assertThat(10000 * count).isEqualTo(findOrder.getTotalPrice());
        assertThat(8).isEqualTo(book.getStockQuantity());
    }

    private Item createBook(String name, int price, int quantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원 1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    @Test
    public void orderException() {
        //given
        Member member = createMember();

        Item book = createBook("JPA", 10000, 10);
        em.persist(book);

        int count = 12;

        assertThatThrownBy(() -> orderService.order(member.getId(), book.getId(), count))
                .isInstanceOf(NotEnoughStockException.class);


    }


    @Test
    public void cancel() {

        Member member = createMember();
        Item book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);

    }

    @Test
    public void cancelException() {

    }

}