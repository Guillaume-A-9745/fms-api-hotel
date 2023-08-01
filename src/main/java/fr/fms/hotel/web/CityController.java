package fr.fms.hotel.web;

import fr.fms.hotel.entities.City;
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

    @DeleteMapping(value = "/cities/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable("id") Long id) {
        try {
            hotelService.deleteCity(id);
        } catch (Exception e) {
            log.error("Problème avec la suppression de la ville d'id : {}", id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        log.info("Suppression de la ville d'id : {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cities/{id}")
    public City getCityById(@PathVariable("id") Long id) {
        return hotelService.readCity(id).orElseThrow( () -> new RuntimeException("Id de ville " + id + " n'existe pas"));
    }

    @PostMapping("/cities")
    public ResponseEntity<City> saveCity(@RequestBody City c) {
        City city = hotelService.saveCity(c);
        if (Objects.isNull(city)) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(city.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/cities")
    public ResponseEntity<City> updateCity(@RequestBody City c){
        City city = hotelService.readCity(c.getId()).get();
        city.setName(c.getName());
        city.setPhoto(c.getPhoto());

        if(Objects.isNull(hotelService.saveCity(city))) {
            return ResponseEntity.noContent().build();
        }
        URI location =  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(city.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
