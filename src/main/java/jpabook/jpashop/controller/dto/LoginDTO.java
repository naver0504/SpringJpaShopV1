package jpabook.jpashop.controller.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Data
public class LoginDTO {


    @NotBlank
    private String loginId;
    @NotBlank
    private String password;


}
