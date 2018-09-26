package com.nix.orderservice.domain;

import com.nix.orderservice.dto.OrderDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "order_catalog")
public class OrderEntity extends BaseDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="order_seq_gen")
    @SequenceGenerator(name="order_seq_gen", sequenceName="ORDER_SEQ")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    //@Cascade(value = {CascadeType.ALL})
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE, CascadeType.PERSIST})
    @OneToMany(mappedBy = "order")
    private List<PhoneOrderEntity> phones = new ArrayList<>();

    public OrderDto toDto() {
        return OrderDto.builder()
                .id(this.id)
                .uuid(this.uuid)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .phones(this.phones.stream().map(PhoneOrderEntity::toDto).collect(Collectors.toList()))
                .build();
    }
}
