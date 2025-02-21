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
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "deceased")
@NamedQuery(
    name = "Deceased.FindByName",
    query = "SELECT d FROM Deceased d WHERE d.name = :name"
)
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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cremation_queue_id", nullable = true)
    private CremationQueue cremationQueue;

    @ManyToOne(cascade = CascadeType.PERSIST)
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDeceasedImage(DeceasedImage deceasedImage) {
        this.deceasedImage = deceasedImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public void setGrave(Grave grave) {
        this.grave = grave;
    }

    public void setCremationQueue(CremationQueue cremationQueue) {
        this.cremationQueue = cremationQueue;
    }

    public void setNecrotomist(Necrotomist necrotomist) {
        this.necrotomist = necrotomist;
    }
    
    

    public Long getId() {
        return id;
    }

    public DeceasedImage getDeceasedImage() {
        return deceasedImage;
    }

    public String getName() {
        return name;
    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public Grave getGrave() {
        return grave;
    }

    public CremationQueue getCremationQueue() {
        return cremationQueue;
    }

    public Necrotomist getNecrotomist() {
        return necrotomist;
    }
    
    
}
