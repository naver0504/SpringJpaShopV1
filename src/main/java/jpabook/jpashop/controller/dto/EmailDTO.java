package jpabook.jpashop.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailDTO {

    @NotBlank
    private String email;
}
