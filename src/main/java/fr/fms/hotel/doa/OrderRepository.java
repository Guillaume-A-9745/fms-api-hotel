package fr.fms.hotel.doa;

import fr.fms.hotel.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
