package fr.fms.hotel.doa;

import fr.fms.hotel.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long>{
    List<Room> findByHotelId(Long id);
}
