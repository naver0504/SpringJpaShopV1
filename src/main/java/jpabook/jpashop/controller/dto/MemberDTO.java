package jpabook.jpashop.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberDTO {

    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    private String city;
    private String street;
    private String zipcode;
}
