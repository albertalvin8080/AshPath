package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deceased")
public class Deceased
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deceased_id")
    private Long id;

    // NOTE: You didn't use SecondaryTable because it doesn't allow for null images. (?)
    @OneToOne(mappedBy = "deceased", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DeceasedImage deceasedImage;

    @Column(name = "deceased_name", nullable = false)
    private String name;

    @Column(name = "deceased_cause_of_death", nullable = false)
    private String causeOfDeath;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deceased_birth_date", nullable = false)
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deceased_death_date", nullable = false)
    private Date deathDate;

    @OneToOne
    @JoinColumn(name = "grave_id", nullable = false)
    private Grave grave;

    @ManyToOne
    @JoinColumn(name = "cremation_queue_id", nullable = true)
    private CremationQueue cremationQueue;

    @ManyToOne
    @JoinColumn(name = "necrotomist_id")
    private Necrotomist necrotomist;

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Deceased other = (Deceased) obj;
        return Objects.equals(this.id, other.id);
    }
}
