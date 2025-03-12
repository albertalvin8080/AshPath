package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import org.featherlessbipeds.ashpath.validator.ValidGraveLocation;

@Entity
@Table(name = "grave")
public class Grave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grave_id")
    private Long id;
    
    @NotNull(message = "{grave.location.notnull}")
    @Size(min = 1, max = 30, message = "{grave.location.size}")
    @ValidGraveLocation(message = "{grave.location.no.float}")
    @Column(name = "grave_location")
    private String location;

    @OneToOne(mappedBy = "grave", cascade = CascadeType.ALL)
    private Deceased deceased;

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Grave other = (Grave) obj;
        return Objects.equals(this.id, other.id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDeceased(Deceased deceased) {
        this.deceased = deceased;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public Deceased getDeceased() {
        return deceased;
    }
    
    
}
