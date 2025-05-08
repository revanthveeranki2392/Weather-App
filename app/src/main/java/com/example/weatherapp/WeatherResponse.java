package com.example.weatherapp;

import java.util.List;

public class WeatherResponse {
    private Main main;
    private List<Weather> weather;
    private String name;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getName() {
        return name;
    }

    public static class Main {
        private double temp;
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class Weather {
        private String icon;

        public String getIcon() {
            return icon;
        }
    }
}
