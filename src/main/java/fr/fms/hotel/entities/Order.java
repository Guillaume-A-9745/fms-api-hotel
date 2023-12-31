package fr.fms.hotel.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;

    @OneToMany(mappedBy = "order")
    private Collection<OrderItem> Rooms;

    @ManyToOne
    private Customer customer;
    private double amount;
}
