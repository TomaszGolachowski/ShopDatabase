package com.app.tGolachowski.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(mappedBy = "trade", cascade = CascadeType.ALL)
    private Producer producer;
}
