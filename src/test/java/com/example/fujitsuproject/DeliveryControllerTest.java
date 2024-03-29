package com.example.fujitsuproject;

import com.example.fujitsuproject.delivery.DeliveryFeeCalculationService;
import com.example.fujitsuproject.weather.WeatherData;
import com.example.fujitsuproject.weather.WeatherDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class DeliveryControllerTest {

    @Autowired
    private DeliveryFeeCalculationService deliveryFeeCalculationService;

    @MockBean
    private WeatherDataRepository weatherDataRepository;

    @Test
    void testCalculateDeliveryFee_Tartu_Bike_Snow() {
        // Given
        String cityName = "Tartu";
        String vehicleType = "Bike";
        String station = "Tartu-Tõravere";

        List<WeatherData> weatherDataList = new ArrayList<>();
        WeatherData weatherData = new WeatherData();
        weatherData.setName(station);
        weatherData.setPhenomenon("Light snow shower");
        weatherData.setAirtemperature(-2.1);
        weatherData.setWindspeed(4.7);
        weatherData.setTimestamp("1711711353");
        weatherDataList.add(weatherData);

        when(weatherDataRepository.findAllByNameOrderByTimestampDesc(station)).thenReturn(weatherDataList);

        double actualFee = deliveryFeeCalculationService.calculateDeliveryFee(cityName, vehicleType);

        double expectedFee = 4;
        assertEquals(expectedFee, actualFee);
    }

    @Test
    void testCalculateDeliveryFee_Pärnu_Scooter_Glaze() {
        // Given
        String cityName = "Pärnu";
        String vehicleType = "Scooter";
        String station = "Pärnu";

        List<WeatherData> weatherDataList = new ArrayList<>();
        WeatherData weatherData = new WeatherData();
        weatherData.setName(station);
        weatherData.setPhenomenon("Glaze");
        weatherData.setAirtemperature(-5.0);
        weatherData.setWindspeed(10.0);
        weatherData.setTimestamp("1711711353");
        weatherDataList.add(weatherData);

        when(weatherDataRepository.findAllByNameOrderByTimestampDesc(station)).thenReturn(weatherDataList);

        // When / Then
        assertThrows(
                RuntimeException.class,
                () -> deliveryFeeCalculationService.calculateDeliveryFee(cityName, vehicleType),
                "Usage of selected vehicle type is forbidden"
        );
    }

    @Test
    void testCalculateDeliveryFee_Tallinn_Bike_Wind() {
        // Given
        String cityName = "Tallinn";
        String vehicleType = "Bike";
        String station = "Tallinn-Harku";

        List<WeatherData> weatherDataList = new ArrayList<>();
        WeatherData weatherData = new WeatherData();
        weatherData.setName(station);
        weatherData.setPhenomenon("");
        weatherData.setAirtemperature(15.0);
        weatherData.setWindspeed(25.0);
        weatherData.setTimestamp("1711711353");
        weatherDataList.add(weatherData);

        when(weatherDataRepository.findAllByNameOrderByTimestampDesc(station)).thenReturn(weatherDataList);

        // When / Then
        assertThrows(
                RuntimeException.class,
                () -> deliveryFeeCalculationService.calculateDeliveryFee(cityName, vehicleType),
                "Usage of selected vehicle type is forbidden"
        );
    }
}