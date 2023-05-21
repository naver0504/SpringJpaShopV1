package jpabook.jpashop.controller.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginDTO {


    @NotBlank
    private String loginId;
    @NotBlank
    private String password;


}
