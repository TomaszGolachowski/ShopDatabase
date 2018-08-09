
package com.app.tGolachowski.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    private Integer age;

    private String name;

    private String surname;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<CustomerOrder> customerOrders = new HashSet<>();

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.REMOVE
    })
    @JoinColumn(name = "country_id")
    private Country country;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getId() != null ? !getId().equals(customer.getId()) : customer.getId() != null) return false;
        if (getAge() != null ? !getAge().equals(customer.getAge()) : customer.getAge() != null) return false;
        if (getName() != null ? !getName().equals(customer.getName()) : customer.getName() != null) return false;
        return getSurname() != null ? getSurname().equals(customer.getSurname()) : customer.getSurname() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getAge() != null ? getAge().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        return result;
    }
}
