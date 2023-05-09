package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Test
    void findAll() {

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setMemberName("baek");

        Member member = new Member();
        member.setName("kim");
        member.setAddress(new Address("anyang", "aaa", "123123"));
        em.persist(member);

        Item item = new Book();
        item.setStockQuantity(1000);
        item.setPrice(10000);
        item.setName("jpa");
        em.persist(item);

        Long order = orderService.order(member.getId(), item.getId(), 3);
        orderRepository.findAll(orderSearch);
        em.flush();




    }
}