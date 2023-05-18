package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.QMember;
import jpabook.jpashop.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static jpabook.jpashop.domain.QMember.*;
import static jpabook.jpashop.domain.QOrder.*;

@Repository
public class OrderRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public OrderRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public void save(Order order) {
        em.persist(order);
    }
    public List<Order> findAll(OrderSearch orderSearch) {


        OrderStatus orderStatus = orderSearch.getOrderStatus();
        String memberName = orderSearch.getMemberName();

        QOrder order = QOrder.order;
        QMember member = QMember.member;


        return query.selectFrom(order)
                .join(order.member, member)
                .where(nameLike(memberName), statusEq(orderStatus))
                .limit(1000)
                .fetch();



    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        if (orderStatus != null) {
            return order.status.eq(orderStatus);
        }
        return null;
    }

    private BooleanExpression nameLike(String memberName) {
        if (StringUtils.hasText(memberName)) {
            return member.name.like("%" + memberName + "%");
        }
        return null;

    }
}
