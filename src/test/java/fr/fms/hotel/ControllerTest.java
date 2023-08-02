package fr.fms.hotel;

import fr.fms.hotel.service.HotelServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HotelServiceImpl hotelService;

    @Test
    public void testGetCities() throws Exception {
        mockMvc.perform(get("/cities")).andExpect(status().isOk());
    }

    @Test
    public void testGetHotels() throws Exception {
        mockMvc.perform(get("/hotels")).andExpect(status().isOk());
    }

    @Test
    public void testGetRooms() throws Exception {
        mockMvc.perform(get("/rooms")).andExpect(status().isOk());
    }
}
