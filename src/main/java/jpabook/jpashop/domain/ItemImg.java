package jpabook.jpashop.domain;


import jpabook.jpashop.ImagePath;
import jpabook.jpashop.domain.item.Item;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.UUID;

import static jpabook.jpashop.ImagePath.*;

@Entity
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemImg {

    @Id @GeneratedValue
    @Column(name = "itemImg_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private String originalFileName;

    private String storedFileName;

    private String filePath;

    public static ItemImg createItemImg(MultipartFile multipartFile, Item item) {
        ItemImg itemImg = new ItemImg();
        String originalFilename = multipartFile.getOriginalFilename();
        itemImg.setOriginalFileName(originalFilename);
        itemImg.setStoredFileName(createStoreFileName(originalFilename));
        itemImg.setItem(item);
        itemImg.setFilePath(ImagePath.filePath+itemImg.getStoredFileName());
        log.info("filePath = {}", itemImg.getFilePath());
        return itemImg;

    }

    public void setItem(Item item) {
        this.item = item;
        item.setItemImg(this);
    }

    private static String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFilename;
    }





}
