package com.nix.orderservice.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class PhoneDto {

    private Long id;

    @NotBlank
    private String uuid;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private Double price;
}
