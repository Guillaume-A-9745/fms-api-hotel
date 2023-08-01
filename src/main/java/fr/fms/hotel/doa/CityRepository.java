package fr.fms.hotel.doa;

import fr.fms.hotel.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City,Long> {
    List<City> findByNameContains(String keyword);
}
