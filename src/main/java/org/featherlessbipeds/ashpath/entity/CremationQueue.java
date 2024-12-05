package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cremation_queue")
public class CremationQueue
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cremation_queue_id")
    private Long id;

    // Beware of circular reference
    @OneToMany(mappedBy = "cremationQueue", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Deceased> deceasedSet;
    
    @ManyToMany(mappedBy = "cremationQueueSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Necrotomist> necrotomistSet;
    
    public void addNecrotomist(Necrotomist necro){
        
        if(this.necrotomistSet == null){
           this.necrotomistSet = new HashSet<>();
        }
        this.necrotomistSet.add(necro);
        necro.addCremationQueue(this);
    }
    
}
