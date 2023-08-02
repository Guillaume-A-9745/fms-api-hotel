package fr.fms.hotel.web;

import fr.fms.hotel.entities.Hotel;
import fr.fms.hotel.entities.Room;
import fr.fms.hotel.service.HotelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@Slf4j
public class RoomController {
    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping("rooms")
    public List<Room> allRooms() { return hotelService.getRoom(); }


    @GetMapping(path="/rooms/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getPhoto(@PathVariable("id") Long id) throws IOException {
        byte[] file = null;
        try {
            Room room = hotelService.readRoom(id).get();
            if(room.getPhoto() == null) room.setPhoto("unknown.png");
            file = Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/super-hotel/rooms/" + room.getPhoto()));
        }
        catch (Exception e) {
            log.error("pb avec download de l'image correspondant à la chambre d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        //log.info("file download ok {}",id);
        return ResponseEntity.ok().body(file);
    }

    @PostMapping(path="/rooms/photo/{id}")
    public ResponseEntity<?> uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception {
        try {
            Room room= hotelService.readRoom(id).get();
            room.setPhoto(file.getOriginalFilename());
            Files.write(Paths.get(System.getProperty("user.home")+"/super-hotel/hotels/" + room.getPhoto()),file.getBytes());
            hotelService.saveRoom(room);
        }
        catch(Exception e) {
            log.error("pb avec upload de l'image correspondant à la chambre d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("file upload ok {}",id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/rooms/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") Long id) {
        try {
            hotelService.deleteRoom(id);
        } catch (Exception e) {
            log.error("Problème avec la suppression de la chambre d'id : {}", id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("Suppression de la chambre d'id : {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rooms/{id}")
    public Room getRoomById(@PathVariable("id") Long id) {
        return hotelService.readRoom(id).orElseThrow( () -> new RuntimeException("Id de la chambre " + id + " n'existe pas"));
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> saveRoom(@RequestBody Room r) {
        Room hotel = hotelService.saveRoom(r);
        if (Objects.isNull(hotel)) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hotel.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/rooms")
    public ResponseEntity<Room> updateRoom(@RequestBody Room r){
        Room room = hotelService.readRoom(r.getId()).get();
        room.setNumber(r.getNumber());
        room.setPrice(r.getPrice());
        room.setReserved(r.isReserved());
        room.setHotel(r.getHotel());
        room.setPhoto(r.getPhoto());

        if(Objects.isNull(hotelService.saveRoom(room))) {
            return ResponseEntity.noContent().build();
        }
        URI location =  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(room.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
