package com.app.tGolachowski.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE
    })
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shop", fetch = FetchType.EAGER)
    private Set<Stock> stocks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop)) return false;

        Shop shop = (Shop) o;

        if (getId() != null ? !getId().equals(shop.getId()) : shop.getId() != null) return false;
        return getName() != null ? getName().equals(shop.getName()) : shop.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
