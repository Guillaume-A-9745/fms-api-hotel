package fr.fms.hotel.web;

import fr.fms.hotel.entities.City;
import fr.fms.hotel.entities.Hotel;
import fr.fms.hotel.service.HotelServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@CrossOrigin("*")
@RestController
@Slf4j
public class HotelController {
    @Autowired
    private HotelServiceImpl hotelService;


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
}
