package fr.fms.hotel;

import fr.fms.hotel.entities.City;
import fr.fms.hotel.entities.Hotel;
import fr.fms.hotel.entities.Room;
import fr.fms.hotel.service.HotelServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private HotelServiceImpl hotelService;

	@Transactional
	@Test
	void addCity() {
		hotelService.saveCity(new City(null,"Marseille","",null));
	}

	@Transactional
	@Test
	void addHotel() {
		hotelService.saveHotel(new Hotel(null,"Le lumière","1 rue de la lumière","0102030405",2,4,"unknown.png",null,null));
	}

	@Transactional
	@Test
	void addRoom() {
		hotelService.saveRoom(new Room(null,01,55.00,false,1,null,"unknown.png"));
	}

	@Test
	void findInDB() {
		hotelService.getCitiesByKeyword("pa");
		hotelService.getHotelsbyKeyword("pa");
	}

	@Test
	void testGetTrainingsAndTestName() throws Exception {
		mockMvc.perform(get("/cities"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name",is("Paris")));
	}
}
