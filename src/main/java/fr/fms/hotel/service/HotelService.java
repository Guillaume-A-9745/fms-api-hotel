package fr.fms.hotel.service;

import fr.fms.hotel.entities.City;
import fr.fms.hotel.entities.Hotel;
import fr.fms.hotel.entities.Room;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    /**
     * Méthode qui permet d'enregistrer une ville en base de donnée
     * @param city
     * @return
     */
    City saveCity(City city);

    /**
     * Méthode qui permet de récupérer touts les villes
     * @return list de villes
     */
    List<City> getCities();

    /**
     * Méthode qui permet de récupérer une ville par son id
     * @param id
     * @return la ville ayant l'id
     */
    Optional<City> readCity(Long id);

    /**
     * Méthode qui permet d'effacer une ville par son id
     * @param id
     */
    void deleteCity(Long id);

    /**
     * Méthode qui permet d'enregistrer un hotel en base de donnée
     * @param hotel
     * @return
     */
    Hotel saveHotel(Hotel hotel);

    /**
     * Méthode qui permet de récupérer touts les hotels
     * @return liste d'hotels
     */
    List<Hotel> getHotel();

    /**
     * Méthode qui permet de récupérer un hotel par son id
     * @param id
     * @return l'hotel ayant l'id
     */
    Optional<Hotel> readHotel(Long id);

    /**
     * Méthode qui permet d'effacer un hotel par son id
     * @param id
     */
    void deleteHotel(Long id);

    /**
     * Méthode qui permet d'enregistrer une chambre en base de donnée
     * @param room
     * @return
     */
    Room saveRoom(Room room);

    /**
     * Méthode qui permet de récupérer touts les chambres
     * @return liste de chambres
     */
    List<Room> getRoom();

    /**
     * Méthode qui permet de récupérer une chambre par son id
     * @param id
     * @return la chambre ayant l'id
     */
    Optional<Room> readRoom(Long id);

    /**
     * Méthode qui permet d'effacer une chambre par son id
     * @param id
     */
    void deleteRoom(Long id);
}
