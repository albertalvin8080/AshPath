package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cremation_queue")
public class CremationQueue
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cremation_queue_id")
    private Long id;

    @OneToMany(mappedBy = "cremationQueue", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Deceased> deceasedSet = new HashSet<>();

    @ManyToMany(mappedBy = "cremationQueueSet", cascade =
    {
        CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH
    }, fetch = FetchType.LAZY)
    private Set<Necrotomist> necrotomistSet = new HashSet<>();

    @Column(name = "entered_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredDate;

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final CremationQueue other = (CremationQueue) obj;
        return Objects.equals(this.id, other.id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }
    
    

    public Long getId() {
        return id;
    }

    public Set<Deceased> getDeceasedSet() {
        return deceasedSet;
    }

    public Set<Necrotomist> getNecrotomistSet() {
        return necrotomistSet;
    }

    public Date getEnteredDate() {
        return enteredDate;
    }
    
    
}
