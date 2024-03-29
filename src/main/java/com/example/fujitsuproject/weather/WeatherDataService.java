package com.example.fujitsuproject.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherDataService {

    public static final String WEATHER_DATA_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    private final WeatherDataRepository weatherDataRepository;

    private final RestTemplate restTemplate;

    @Value("${weather.stations}")
    private String[] stations;

    public WeatherDataService(WeatherDataRepository weatherDataRepository, RestTemplate restTemplate) {
        this.weatherDataRepository = weatherDataRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Scheduled method to fetch weather data from the external source and insert it into the database.
     * This method is scheduled to run once every hour, 15 minutes after a full hour (HH:15:00).
     */
    @Scheduled(cron = "${weather.import.cron:0 15 * * * *}")
    public void fetchAndInsertWeatherData() {
        try {
            String xmlData = restTemplate.getForObject(WEATHER_DATA_URL, String.class);
            List<WeatherData> weatherDataList = parseWeatherDataFromXml(xmlData);
            weatherDataRepository.saveAll(weatherDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses weather data from XML string obtained from the external source.
     * @param xmlData The XML data containing weather observations.
     * @return A list of WeatherData objects parsed from the XML data.
     */
    public List<WeatherData> parseWeatherDataFromXml(String xmlData) {
        List<WeatherData> weatherDataList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlData)));

            document.getDocumentElement().normalize();

            NodeList stationNodes = document.getElementsByTagName("station");
            String timestamp = document.getDocumentElement().getAttribute("timestamp");

            List<String> requiredStations = Arrays.asList(stations);

            for (int i = 0; i < stationNodes.getLength(); i++) {
                Element stationElement = (Element) stationNodes.item(i);
                if (requiredStations.contains(stationElement.getElementsByTagName("name").item(0).getTextContent())){
                    String name = stationElement.getElementsByTagName("name").item(0).getTextContent();
                    String wmocode = stationElement.getElementsByTagName("wmocode").item(0).getTextContent();
                    String airtemperatureStr = stationElement.getElementsByTagName("airtemperature").item(0).getTextContent();
                    String windspeedStr = stationElement.getElementsByTagName("windspeed").item(0).getTextContent();
                    String phenomenon = stationElement.getElementsByTagName("phenomenon").item(0).getTextContent();

                    WeatherData weatherData = new WeatherData();
                    weatherData.setName(name);
                    weatherData.setWmocode(wmocode);
                    weatherData.setPhenomenon(phenomenon);
                    weatherData.setTimestamp(timestamp);

                    if (!airtemperatureStr.isEmpty()) {
                        weatherData.setAirtemperature(Double.parseDouble(airtemperatureStr));
                    }
                    if (!windspeedStr.isEmpty()) {
                        weatherData.setWindspeed(Double.parseDouble(windspeedStr));
                    }

                    weatherDataList.add(weatherData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherDataList;
    }

    /**
     * Retrieves all weather data stored in the database.
     * @return A list of all WeatherData objects stored in the database.
     */
    public List<WeatherData> getAllWeatherData() {
        return weatherDataRepository.findAll();
    }


}