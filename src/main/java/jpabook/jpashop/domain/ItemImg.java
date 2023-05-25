package jpabook.jpashop.domain;


import jpabook.jpashop.ImagePath;
import jpabook.jpashop.domain.item.Item;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.UUID;

import static jpabook.jpashop.ImagePath.*;

@Entity
@Slf4j
@Data
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

    public  static ItemImg createItemImg(MultipartFile multipartFile, Item item) {

        ItemImg itemImg = new ItemImg();
        String originalFilename = multipartFile.getOriginalFilename();
        itemImg.setOriginalFileName(originalFilename);
        itemImg.setStoredFileName(createStoreFileName(originalFilename));
        itemImg.setItem(item, itemImg);
        itemImg.setFilePath(ImagePath.filePath+itemImg.getStoredFileName());
        return itemImg;

    }

    public void setItem(Item item, ItemImg itemImg) {
        item.setItemImg(itemImg);
        itemImg.setItem(item);
    }

    private static String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFilename;
    }





}
