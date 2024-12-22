package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "grave")
@Getter
@Setter
public class Grave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grave_id")
    private Long id;
    
    @Column(name = "grave_location")
    private String location; // row number in a cemetery?

    @OneToOne(mappedBy = "grave")
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
}
