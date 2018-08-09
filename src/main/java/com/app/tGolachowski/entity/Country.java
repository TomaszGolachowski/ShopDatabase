package com.app.tGolachowski.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<Customer> customers;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<Producer> producers;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<Shop> shops;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        if (getId() != null ? !getId().equals(country.getId()) : country.getId() != null) return false;
        return getName() != null ? getName().equals(country.getName()) : country.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
