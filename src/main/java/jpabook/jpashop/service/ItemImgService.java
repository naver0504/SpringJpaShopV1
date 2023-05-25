package jpabook.jpashop.service;

import jpabook.jpashop.ImagePath;
import jpabook.jpashop.domain.ItemImg;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;

import static jpabook.jpashop.ImagePath.*;
import static jpabook.jpashop.domain.ItemImg.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ItemImgService {

    private String fileDir = filePath;

    private final ItemImgRepository itemImgRepository;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    @Transactional
    public void save(MultipartFile multipartFile, Item item) throws IOException {
        if (multipartFile.isEmpty()) {
            return;
        }
        log.info("originalName = {}", multipartFile.getOriginalFilename());
        ItemImg itemImg = createItemImg(multipartFile, item);
        multipartFile.transferTo(new File(getFullPath(itemImg.getStoredFileName())));

        itemImgRepository.save(itemImg);
    }


    public ItemImg findByItem(Item item) {
        ItemImg itemImg = itemImgRepository.findByItem(item);
        return itemImg;
    }





}
