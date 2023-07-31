package fr.fms.hotel;

import fr.fms.hotel.doa.CityRepository;
import fr.fms.hotel.doa.HotelRepository;
import fr.fms.hotel.doa.RoomRepository;
import fr.fms.hotel.entities.City;
import fr.fms.hotel.entities.Hotel;
import fr.fms.hotel.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotelApplication implements CommandLineRunner {
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private RoomRepository roomRepository;

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		generateData();
	}

	private void generateData() {
		City paris = cityRepository.save(new City(null,"Paris",null));
		City bordeaux = cityRepository.save(new City(null,"Bordeaux",null));
		City bayonne = cityRepository.save(new City(null,"Bayonne",null));
		City biarritz = cityRepository.save(new City(null,"Biarritz",null));
		City pau = cityRepository.save(new City(null,"Pau",null));

		Hotel paris1 = hotelRepository.save(new Hotel(null,"paris1","1 rue de la lumière","0102030405",2,4,null,paris));
		roomRepository.save(new Room(null,01,55.00,false,paris1,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,paris1,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,paris1,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,paris1,"unknown.png"));

		Hotel paris2 = hotelRepository.save(new Hotel(null,"paris2","1 rue de la lumière","0102030405",2,4,null,paris));
		roomRepository.save(new Room(null,01,55.00,false,paris2,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,paris2,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,paris2,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,paris2,"unknown.png"));

		Hotel paris3 = hotelRepository.save(new Hotel(null,"paris3","1 rue de la lumière","0102030405",2,4,null,paris));
		roomRepository.save(new Room(null,01,55.00,false,paris3,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,paris3,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,paris3,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,paris3,"unknown.png"));

		Hotel paris4 = hotelRepository.save(new Hotel(null,"paris4","1 rue de la lumière","0102030405",2,4,null,paris));
		roomRepository.save(new Room(null,01,55.00,false,paris4,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,paris4,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,paris4,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,paris4,"unknown.png"));


		Hotel bordeaux1 = hotelRepository.save(new Hotel(null,"bordeaux1","1 rue de la lumière","0102030405",2,4,null,bordeaux));
		roomRepository.save(new Room(null,01,55.00,false,bordeaux1,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,bordeaux1,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,bordeaux1,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,bordeaux1,"unknown.png"));

		Hotel bordeaux2 = hotelRepository.save(new Hotel(null,"bordeaux2","1 rue de la lumière","0102030405",2,4,null,bordeaux));
		roomRepository.save(new Room(null,01,55.00,false,bordeaux2,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,bordeaux2,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,bordeaux2,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,bordeaux2,"unknown.png"));

		Hotel bordeaux3 = hotelRepository.save(new Hotel(null,"bordeaux3","1 rue de la lumière","0102030405",2,4,null,bordeaux));
		roomRepository.save(new Room(null,01,55.00,false,bordeaux3,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,bordeaux3,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,bordeaux3,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,bordeaux3,"unknown.png"));

		Hotel bordeaux4 = hotelRepository.save(new Hotel(null,"bordeaux4","1 rue de la lumière","0102030405",2,4,null,bordeaux));
		roomRepository.save(new Room(null,01,55.00,false,bordeaux4,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,bordeaux4,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,bordeaux4,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,bordeaux4,"unknown.png"));


		Hotel bayonne1 = hotelRepository.save(new Hotel(null,"bayonne1","1 rue de la lumière","0102030405",2,4,null,bayonne));
		roomRepository.save(new Room(null,01,55.00,false,bayonne1,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,bayonne1,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,bayonne1,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,bayonne1,"unknown.png"));

		Hotel bayonne2 = hotelRepository.save(new Hotel(null,"bayonne2","1 rue de la lumière","0102030405",2,4,null,bayonne));
		roomRepository.save(new Room(null,01,55.00,false,bayonne2,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,bayonne2,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,bayonne2,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,bayonne2,"unknown.png"));
		Hotel bayonne3 = hotelRepository.save(new Hotel(null,"bayonne3","1 rue de la lumière","0102030405",2,4,null,bayonne));
		roomRepository.save(new Room(null,01,55.00,false,bayonne3,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,bayonne3,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,bayonne3,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,bayonne3,"unknown.png"));
		Hotel bayonne4 = hotelRepository.save(new Hotel(null,"bayonne4","1 rue de la lumière","0102030405",2,4,null,bayonne));
		roomRepository.save(new Room(null,01,55.00,false,bayonne4,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,bayonne4,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,bayonne4,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,bayonne4,"unknown.png"));


		Hotel biarritz1 = hotelRepository.save(new Hotel(null,"biarritz1","1 rue de la lumière","0102030405",2,4,null,biarritz));
		roomRepository.save(new Room(null,01,55.00,false,biarritz1,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,biarritz1,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,biarritz1,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,biarritz1,"unknown.png"));

		Hotel biarritz2 = hotelRepository.save(new Hotel(null,"biarritz2","1 rue de la lumière","0102030405",2,4,null,biarritz));
		roomRepository.save(new Room(null,01,55.00,false,biarritz2,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,biarritz2,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,biarritz2,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,biarritz2,"unknown.png"));

		Hotel biarritz3 = hotelRepository.save(new Hotel(null,"biarritz3","1 rue de la lumière","0102030405",2,4,null,biarritz));
		roomRepository.save(new Room(null,01,55.00,false,biarritz3,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,biarritz3,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,biarritz3,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,biarritz3,"unknown.png"));

		Hotel biarritz4 = hotelRepository.save(new Hotel(null,"biarritz4","1 rue de la lumière","0102030405",2,4,null,biarritz));
		roomRepository.save(new Room(null,01,55.00,false,biarritz4,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,biarritz4,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,biarritz4,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,biarritz4,"unknown.png"));


		Hotel pau1 = hotelRepository.save(new Hotel(null,"pau1","1 rue de la lumière","0102030405",2,4,null,pau));
		roomRepository.save(new Room(null,01,55.00,false,pau1,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,pau1,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,pau1,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,pau1,"unknown.png"));

		Hotel pau2 = hotelRepository.save(new Hotel(null,"pau2","1 rue de la lumière","0102030405",2,4,null,pau));
		roomRepository.save(new Room(null,01,55.00,false,pau2,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,pau2,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,pau2,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,pau2,"unknown.png"));

		Hotel pau3 = hotelRepository.save(new Hotel(null,"pau3","1 rue de la lumière","0102030405",2,4,null,pau));
		roomRepository.save(new Room(null,01,55.00,false,pau3,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,pau3,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,pau3,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,pau3,"unknown.png"));

		Hotel pau4 = hotelRepository.save(new Hotel(null,"pau4","1 rue de la lumière","0102030405",2,4,null,pau));
		roomRepository.save(new Room(null,01,55.00,false,pau4,"unknown.png"));
		roomRepository.save(new Room(null,02,65.00,false,pau4,"unknown.png"));
		roomRepository.save(new Room(null,03,55.00,false,pau4,"unknown.png"));
		roomRepository.save(new Room(null,04,75.00,false,pau4,"unknown.png"));
	}
}