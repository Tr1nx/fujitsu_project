package com.example.fujitsuproject;

import com.example.fujitsuproject.weather.WeatherData;
import com.example.fujitsuproject.weather.WeatherDataController;
import com.example.fujitsuproject.weather.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private WeatherDataController weatherDataController;

    @MockBean
    private WeatherDataService weatherDataService;


    @Test
    void testGetAllWeatherData() {
        // Given
        MockitoAnnotations.openMocks(this);

        List<WeatherData> expectedWeatherData = new ArrayList<>();
        WeatherData weatherData = new WeatherData();
        weatherData.setName("Tallinn-Harku");
        weatherData.setPhenomenon("");
        weatherData.setAirtemperature(15.0);
        weatherData.setWindspeed(10.0);
        weatherData.setTimestamp("1711711353");
        expectedWeatherData.add(weatherData);

        when(weatherDataService.getAllWeatherData()).thenReturn(expectedWeatherData);

        List<WeatherData> result = weatherDataController.getAllWeatherData();

        assertEquals(expectedWeatherData, result);
    }

}