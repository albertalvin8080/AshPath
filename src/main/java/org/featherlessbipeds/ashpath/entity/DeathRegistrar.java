package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "death_registrar")
@DiscriminatorValue(value = "D_REGISTRAR")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "user_id")
// The guy who uses the system daily to register/unregister things.
@NamedQueries({
    @NamedQuery(
        name = "DeathRegistrar.RegistrationDateBetween",
        query ="SELECT d FROM DeathRegistrar d WHERE d.registrationDate BETWEEN ?1 AND ?2"
    )
})
public class DeathRegistrar extends User
{
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "death_registrar_contact_numbers", 
        joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "contact_number", nullable = false)
    private Set<String> contactNumbers = new HashSet<>();

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(name = "last_activity_date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivityDate;
    
    public void addContactNumber(String number)
    {
        contactNumbers.add(number);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
    
    

    public String getFullName() {
        return fullName;
    }

    public Set<String> getContactNumbers() {
        return contactNumbers;
    }

    public String getEmail() {
        return email;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }
    
    
}
