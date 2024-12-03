package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ADMIN")
@Table(name = "admin")
public class Admin extends User
{
    @Column(name = "admin_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdminRole role;
}
