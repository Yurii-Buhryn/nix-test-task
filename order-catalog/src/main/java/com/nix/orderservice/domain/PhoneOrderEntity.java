package com.nix.orderservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nix.orderservice.dto.PhoneDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "phone_order_catalog")
public class PhoneOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "phone_order_seq_gen")
    @SequenceGenerator(name = "phone_order_seq_gen", sequenceName = "PHONE_ORDER_SEQ")
    private Long id;

    @Column(name = "phone_uuid")
    private String uuid;

    @Column
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private OrderEntity order;

    public PhoneDto toDto() {
        return PhoneDto.builder()
                .id(this.id)
                .uuid(this.uuid)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .build();
    }
}
