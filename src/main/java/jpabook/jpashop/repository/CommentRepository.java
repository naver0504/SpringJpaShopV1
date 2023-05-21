package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepository {

    @Autowired
    private final EntityManager em;

    public void save(Comment comment) {

        em.persist(comment);
    }






}
