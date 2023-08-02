package fr.fms.hotel.web;

import fr.fms.hotel.entities.City;
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
public class HotelController {
    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping("hotels")
    public List<Hotel> allHotels() { return hotelService.getHotel(); }

    @GetMapping("hotels/search/{keyword}")
    public List<Hotel> searchByKeyword(@PathVariable String keyword) {
        try {
            return hotelService.getHotelsbyKeyword(keyword);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @GetMapping(path="/hotels/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getPhoto(@PathVariable("id") Long id) throws IOException {
        byte[] file = null;
        try {
            Hotel hotel = hotelService.readHotel(id).get();
            if(hotel.getPhoto() == null) hotel.setPhoto("unknown.png");
            file = Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/super-hotel/hotels/" + hotel.getPhoto()));
        }
        catch (Exception e) {
            log.error("pb avec download de l'image correspondant à l'hotel d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        //log.info("file download ok {}",id);
        return ResponseEntity.ok().body(file);
    }

    @PostMapping(path="/hotels/photo/{id}")
    public ResponseEntity<?> uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception {
        try {
            Hotel hotel= hotelService.readHotel(id).get();
            hotel.setPhoto(file.getOriginalFilename());
            Files.write(Paths.get(System.getProperty("user.home")+"/super-hotel/hotels/" + hotel.getPhoto()),file.getBytes());
            hotelService.saveHotel(hotel);
        }
        catch(Exception e) {
            log.error("pb avec upload de l'image correspondant à l'hotel' d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("file upload ok {}",id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/hotels/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable("id") Long id) {
        try {
            hotelService.deleteHotel(id);
        } catch (Exception e) {
            log.error("Problème avec la suppression de l'hotel d'id : {}", id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("Suppression de l'hotel d'id : {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hotels/{id}")
    public Hotel getHotelById(@PathVariable("id") Long id) {
        return hotelService.readHotel(id).orElseThrow( () -> new RuntimeException("Id de l'hotel " + id + " n'existe pas"));
    }

    @PostMapping("/hotels")
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel h) {
        Hotel hotel = hotelService.saveHotel(h);
        if (Objects.isNull(hotel)) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hotel.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/hotels")
    public ResponseEntity<Hotel> updateHotel(@RequestBody Hotel h){
        Hotel hotel = hotelService.readHotel(h.getId()).get();
        hotel.setName(h.getName());
        hotel.setAddress(h.getAddress());
        hotel.setPhone(h.getPhone());
        hotel.setStar(h.getStar());
        hotel.setNbRoom(h.getNbRoom());
        hotel.setPhoto(h.getPhoto());
        hotel.setCity(h.getCity());

        if(Objects.isNull(hotelService.saveHotel(hotel))) {
            return ResponseEntity.noContent().build();
        }
        URI location =  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hotel.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/hotels/{id}/rooms")
    public List<Room> allRoomByHotelId(@PathVariable("id") Long id) {
        return hotelService.getRoomByHotel(id);
    }
}