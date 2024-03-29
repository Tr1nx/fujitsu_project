package com.example.fujitsuproject.weather;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlElement;

@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String wmocode;
    private Double airtemperature;
    private Double windspeed;
    private String phenomenon;
    private String timestamp;


    /**
     * Retrieves the name of the weather station.
     *
     * @return The name of the weather station.
     */
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }


    /**
     * Sets the name of the weather station.
     *
     * @param name The name of the weather station.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Retrieves the WMO code of the weather station.
     *
     * @return The WMO code of the weather station.
     */
    @XmlElement(name = "wmocode")
    public String getWmocode() {
        return wmocode;
    }


    /**
     * Sets the WMO code of the weather station.
     *
     * @param wmocode The WMO code of the weather station.
     */
    public void setWmocode(String wmocode) {
        this.wmocode = wmocode;
    }


    /**
     * Retrieves the air temperature recorded by the weather station.
     *
     * @return The air temperature recorded by the weather station.
     */
    @XmlElement(name = "airtemperature")
    public Double getAirtemperature() {
        return airtemperature;
    }


    /**
     * Sets the air temperature recorded by the weather station.
     *
     * @param airtemperature The air temperature recorded by the weather station.
     */
    public void setAirtemperature(Double airtemperature) {
        this.airtemperature = airtemperature;
    }


    /**
     * Retrieves the wind speed recorded by the weather station.
     *
     * @return The wind speed recorded by the weather station.
     */
    @XmlElement(name = "windspeed")
    public Double getWindspeed() {
        return windspeed;
    }


    /**
     * Sets the wind speed recorded by the weather station.
     *
     * @param windspeed The wind speed recorded by the weather station.
     */
    public void setWindspeed(Double windspeed) {
        this.windspeed = windspeed;
    }


    /**
     * Retrieves the weather phenomenon recorded by the weather station.
     *
     * @return The weather phenomenon recorded by the weather station.
     */
    @XmlElement(name = "phenomenon")
    public String getPhenomenon() {
        return phenomenon;
    }


    /**
     * Sets the weather phenomenon recorded by the weather station.
     *
     * @param phenomenon The weather phenomenon recorded by the weather station.
     */
    public void setPhenomenon(String phenomenon) {
        this.phenomenon = phenomenon;
    }


    /**
     * Retrieves the timestamp when the weather data was recorded.
     *
     * @return The timestamp when the weather data was recorded.
     */
    @XmlElement(name = "timestamp")
    public String getTimestamp() {
        return timestamp;
    }


    /**
     * Sets the timestamp when the weather data was recorded.
     *
     * @param timestamp The timestamp when the weather data was recorded.
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * Generates a string representation of the WeatherData object.
     *
     * @return A string representation of the WeatherData object.
     */
    @Override
    public String toString() {
        return "WeatherData{" +
                "name='" + name + '\'' +
                ", wmocode='" + wmocode + '\'' +
                ", airtemperature='" + airtemperature + '\'' +
                ", windspeed='" + windspeed + '\'' +
                ", phenomenon='" + phenomenon + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

}