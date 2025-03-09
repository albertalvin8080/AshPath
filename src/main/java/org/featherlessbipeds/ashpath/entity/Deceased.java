package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import java.util.Objects;
import org.featherlessbipeds.ashpath.validator.DeathDateAfterBirthDate;

@Entity
@Table(name = "deceased")
@NamedQuery(
    name = "Deceased.FindByName",
    query = "SELECT d FROM Deceased d WHERE d.name = :name"
)
@DeathDateAfterBirthDate
public class Deceased
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deceased_id")
    private Long id;

    // NOTE: You didn't use SecondaryTable because it doesn't allow for null images. (?)
    @OneToOne(mappedBy = "deceased", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private DeceasedImage deceasedImage;

    @NotBlank
    @Pattern(regexp = "^[\\p{L}\\s']{2,40}$", message = "{org.featherlessbipeds.ashpath.entity.Deceased.name}")
    @Column(name = "deceased_name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "deceased_cause_of_death", nullable = false)
    private String causeOfDeath;

    @NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deceased_birth_date", nullable = false)
    private Date birthDate;

    @NotNull
    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deceased_death_date", nullable = false)
    private Date deathDate;

    @OneToOne
    @JoinColumn(name = "grave_id", nullable = true)
    private Grave grave;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cremation_queue_id", nullable = true)
    private CremationQueue cremationQueue;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "necrotomist_id", nullable = true)
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
        necrotomist.addDeceased(this);
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
