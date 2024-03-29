package com.example.fujitsuproject.delivery;

import com.example.fujitsuproject.weather.WeatherData;
import org.springframework.stereotype.Service;
import com.example.fujitsuproject.weather.WeatherDataRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryFeeCalculationService {

    private final WeatherDataRepository weatherDataRepository;

    private final Map<String, String> cityToStationMapping;


    public DeliveryFeeCalculationService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;

        this.cityToStationMapping = new HashMap<>();
        cityToStationMapping.put("Tallinn", "Tallinn-Harku");
        cityToStationMapping.put("Tartu", "Tartu-Tõravere");
        cityToStationMapping.put("Pärnu", "Pärnu");
    }

    /**
     * Calculates the delivery fee based on the specified city and vehicle type.
     *
     * @param city        The city for which the delivery fee is calculated.
     * @param vehicleType The type of vehicle used for delivery.
     * @return The calculated delivery fee.
     * @throws RuntimeException if no weather data is found for the specified city.
     */
    public double calculateDeliveryFee(String city, String vehicleType) {
        String station = cityToStationMapping.getOrDefault(city, "");

        List<WeatherData> weatherDataList = weatherDataRepository.findAllByNameOrderByTimestampDesc(station);

        if (!weatherDataList.isEmpty()) {
            WeatherData latestWeatherData = weatherDataList.get(0);
            double regionalBaseFee = calculateRegionalBaseFee(city, vehicleType);
            double extraFees = calculateExtraFees(latestWeatherData, vehicleType);
            return regionalBaseFee + extraFees;
        } else {
            throw new RuntimeException("No weather data found for station: " + station);
        }
    }

    private double calculateRegionalBaseFee(String city, String vehicleType) {
        switch (city.toLowerCase()) {
            case "tallinn" -> {
                switch (vehicleType.toLowerCase()) {
                    case "car" -> {
                        return 4.0;
                    }
                    case "scooter" -> {
                        return 3.5;
                    }
                    case "bike" -> {
                        return 3.0;
                    }
                    default -> {
                    }
                }
            }
            case "tartu" -> {
                switch (vehicleType.toLowerCase()) {
                    case "car" -> {
                        return 3.5;
                    }
                    case "scooter" -> {
                        return 3.0;
                    }
                    case "bike" -> {
                        return 2.5;
                    }
                    default -> {
                    }
                }
            }
            case "pärnu" -> {
                switch (vehicleType.toLowerCase()) {
                    case "car" -> {
                        return 3.0;
                    }
                    case "scooter" -> {
                        return 2.5;
                    }
                    case "bike" -> {
                        return 2.0;
                    }
                    default -> {
                    }
                }
            }
            default -> {
            }
        }
        return 0.0;
    }

    private double calculateExtraFees(WeatherData weatherData, String vehicleType) {
        double extraFees = 0.0;
        if (vehicleType.equalsIgnoreCase("Scooter") || vehicleType.equalsIgnoreCase("Bike")) {
            if (weatherData.getAirtemperature() < -10.0) {
                extraFees += 1.0;
            } else if (weatherData.getAirtemperature() > -10.0 && weatherData.getAirtemperature() < 0.0) {
                extraFees += 0.5;
            }

            if (vehicleType.equalsIgnoreCase("Bike")) {
                if (weatherData.getWindspeed() > 20.0) {
                    throw new RuntimeException("Usage of selected vehicle \n" + "type is forbidden");
                } else if (weatherData.getWindspeed() > 10.0 && weatherData.getWindspeed() < 20.0) {
                    extraFees += 0.5;
                }
            }

            if (vehicleType.equalsIgnoreCase("Scooter") || vehicleType.equalsIgnoreCase("Bike")) {
                String phenomenon = weatherData.getPhenomenon().toLowerCase();
                if (phenomenon.contains("rain")) {
                    extraFees += 0.5;
                } else if (phenomenon.contains("snow") || phenomenon.contains("sleet")) {
                    extraFees += 1.0;
                } else if (phenomenon.equals("glaze") || phenomenon.equals("hail") || phenomenon.equals("thunder")) {
                    throw new RuntimeException("Usage of selected vehicle type is forbidden");
                }
            }
        }
        return extraFees;
    }
}