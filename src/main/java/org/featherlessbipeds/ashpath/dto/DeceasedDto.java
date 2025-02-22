package org.featherlessbipeds.ashpath.dto;

import java.util.Date;

public class DeceasedDto
{
    private Long id;

    private String name;

    private String causeOfDeath;

    private Date birthDate;

    private Date deathDate;

    public DeceasedDto(Long id, String name, String causeOfDeath, Date birthDate, Date deathDate)
    {
        this.id = id;
        this.name = name;
        this.causeOfDeath = causeOfDeath;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }
    
    

    public void setId(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
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
}
