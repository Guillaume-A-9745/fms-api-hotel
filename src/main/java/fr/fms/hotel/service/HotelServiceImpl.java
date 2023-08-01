package fr.fms.hotel.service;

import fr.fms.hotel.doa.CityRepository;
import fr.fms.hotel.doa.HotelRepository;
import fr.fms.hotel.doa.RoomRepository;
import fr.fms.hotel.entities.City;
import fr.fms.hotel.entities.Hotel;
import fr.fms.hotel.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;


    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> readCity(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getHotel() {
        return hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> readHotel(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public List<Room> getRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> readRoom(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    public List<City> getCitiesByKeyword(String keyword) {
        return cityRepository.findByNameContains(keyword);
    }
}
