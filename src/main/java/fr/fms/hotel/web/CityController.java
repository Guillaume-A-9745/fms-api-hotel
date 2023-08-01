package fr.fms.hotel.web;

import fr.fms.hotel.entities.City;
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
import java.util.List;

@CrossOrigin("*")
@RestController
@Slf4j
public class CityController {
    @Autowired
    private HotelServiceImpl hotelService;

    @GetMapping("cities")
    public List<City> allCities() { return hotelService.getCities(); }

    @GetMapping("cities/search/{keyword}")
    public List<City> searchByKeyword(@PathVariable String keyword) {
        try {
            return hotelService.getCitiesByKeyword(keyword);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @GetMapping(path="/photo/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getPhoto(@PathVariable("id") Long id) throws IOException {
        byte[] file = null;
        try {
            City city = hotelService.readCity(id).get();
            if(city.getPhoto() == null) city.setPhoto("unknown.png");
            file = Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/super-hotel/cities/" + city.getPhoto()));
        }
        catch (Exception e) {
            log.error("pb avec download de l'image correspondant à la ville d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        //log.info("file download ok {}",id);
        return ResponseEntity.ok().body(file);
    }

    @PostMapping(path="/photo/{id}")
    public ResponseEntity<?> uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception {
        try {
            City city= hotelService.readCity(id).get();
            city.setPhoto(file.getOriginalFilename());
            Files.write(Paths.get(System.getProperty("user.home")+"/super-hotel/cities/" + city.getPhoto()),file.getBytes());
            hotelService.saveCity(city);
        }
        catch(Exception e) {
            log.error("pb avec upload de l'image correspondant à la ville d'id : {}",id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("file upload ok {}",id);
        return ResponseEntity.ok().build();
    }
}
