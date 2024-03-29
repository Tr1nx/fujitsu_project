package com.example.fujitsuproject.weather;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class WeatherDataController {

    private final WeatherDataService weatherDataViewService;

    public WeatherDataController(WeatherDataService weatherDataViewService) {
        this.weatherDataViewService = weatherDataViewService;
    }

    /**
     * Retrieves all weather data entries.
     *
     * @return a list of all weather data entries
     */
    @GetMapping("/weather-data")
    public List<WeatherData> getAllWeatherData() {
        return weatherDataViewService.getAllWeatherData();
    }
}