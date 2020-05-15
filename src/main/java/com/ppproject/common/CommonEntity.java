package com.ppproject.common;

import lombok.Getter;

import javax.persistence.*;

@MappedSuperclass
@Getter
public abstract class CommonEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

}
