package com.nix.phoneservice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;


@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "image_catalog")
public class ImageEntity extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String url() {
        return "https://someserver/images/" + uuid;
    }
}
