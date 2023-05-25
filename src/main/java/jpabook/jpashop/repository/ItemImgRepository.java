package jpabook.jpashop.repository;

import jpabook.jpashop.domain.ItemImg;
import jpabook.jpashop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    ItemImg findByItem(Item item);
}
