package com.kiyotakeshi.orderproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer price;
}
