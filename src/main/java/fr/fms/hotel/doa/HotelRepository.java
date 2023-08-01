package fr.fms.hotel.doa;

import fr.fms.hotel.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
    List<Hotel> findByCityId(Long id);

    List<Hotel> findByNameContains(String keyword);
}
