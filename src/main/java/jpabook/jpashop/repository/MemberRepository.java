package jpabook.jpashop.repository;

import jpabook.jpashop.controller.LoginForm;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }


    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);

    }

    public Optional<Member> findByLoginForm(LoginForm loginForm) {

        String loginId = loginForm.getLoginId();
        String password = loginForm.getPassword();

        List<Member> member = em.createQuery("select m from Member m where m.loginId = :loginId and m.password = :password", Member.class)
                .setParameter("loginId", loginId)
                .setParameter("password", password)
                .getResultList();

        return member.stream().findAny();

    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
