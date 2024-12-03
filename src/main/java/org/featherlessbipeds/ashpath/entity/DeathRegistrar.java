package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "D_REGISTRAR")
@Table(name = "death_registrar")
// The guy who uses the system daily to register/unregister things.
public class DeathRegistrar extends User
{
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "contact_number", nullable = false)
    private String contactNumber;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(name = "last_activity_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivityDate;
}
