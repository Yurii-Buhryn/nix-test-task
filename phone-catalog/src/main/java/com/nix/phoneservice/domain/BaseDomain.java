package com.nix.phoneservice.domain;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@MappedSuperclass
public abstract class BaseDomain {

    @Column(unique = true, nullable = false)
    public String uuid;

    @PrePersist
    public void onCreate() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
