package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "D_REGISTRAR")
@Table(name = "death_registrar")
@Getter
@Setter
// The guy who uses the system daily to register/unregister things.
public class DeathRegistrar extends User
{
    
}
