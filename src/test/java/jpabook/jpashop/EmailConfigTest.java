package jpabook.jpashop;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class EmailConfigTest {

    @Autowired
    EmailConfig emailConfig;


    @Test
    void showUsername() {
        log.info("username = {}", emailConfig.getUserName());
        log.info("password = {}", emailConfig.getPassword());

    }


}