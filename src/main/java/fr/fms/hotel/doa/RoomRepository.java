package fr.fms.hotel.doa;

import fr.fms.hotel.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long>{
}
