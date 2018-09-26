package com.nix.phonemarketgatewayservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

    private List<T> content;

    private Integer number;

    private Integer totalPages;

    private Integer totalElements;

    private Integer size;

    private Integer numberOfElements;

}
