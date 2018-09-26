package com.nix.phonemarketgatewayservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CalculateOrderDto {

    private String uuid;

    private String firstName;

    private String lastName;

    private String email;

    private List<PhoneDto> phones = new ArrayList<>();

    private Double price;
}
