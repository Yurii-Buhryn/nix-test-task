package com.nix.phoneservice.domain;

import com.nix.phoneservice.dto.PhoneDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "phone_catalog")
public class PhoneEntity extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(length = 1000)
    private String description;

    @Column
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    public PhoneDto toDto() {
        return PhoneDto.builder()
                .id(this.getId())
                .uuid(this.getUuid())
                .name(this.getName())
                .description(this.getDescription())
                .price(this.getPrice())
                .imageUrl(this.getImage().url())
                .build();
    }

}
