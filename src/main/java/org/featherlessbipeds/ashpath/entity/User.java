package org.featherlessbipeds.ashpath.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING, columnDefinition = "VARCHAR(20)")
@Table(name = "app_user") // "user" is a reserved word.
public abstract class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[\\p{L}\\p{Digit}.'_-]{4,16}$", message = "{org.featherlessbipeds.ashpath.entity.User.name}")
    @Column(name = "username")
    private String username;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\p{Lu})(?=.*[@$!%*?&])[\\p{L}\\p{Digit}@$!%*?&]{8,20}$",
            message = "{org.featherlessbipeds.ashpath.entity.User.password}"
    )
    @Column(name = "password")
    private String password;

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Long getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
