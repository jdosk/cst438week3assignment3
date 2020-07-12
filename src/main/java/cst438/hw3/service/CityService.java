package cst438.hw3.service;

import cst438.hw3.domain.City;
import cst438.hw3.domain.CityInfo;
import cst438.hw3.domain.CityRepository;
import cst438.hw3.domain.CountryRepository;
import cst438.hw3.domain.TempAndTime;
import java.util.List;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
  @Autowired
  private CityRepository cityRepository;
  @Autowired
  private CountryRepository countryRepository;
  @Autowired
  private WeatherService weatherService;
  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private FanoutExchange fanout;

  public CityInfo getCityInfo(String cityName) {
    List<City> cities = cityRepository.findByName(cityName);

    // If city doesnt exist return null
    if (cities.size() == 0) {
      return null;
    } else {
      // If city exists get index 0 of list from repository, get country name from repository,
      // and get temp and time from weatherService
      City city = cities.get(0);
      String country = countryRepository.findByCode(city.getCountryCode()).getName();
      TempAndTime tempAndTime = weatherService.getTempAndTime(cityName);

      // return a CityInfo object but call ConvertTempToFahrenheit and convertTimeToString to
      // get correct formats for temp and time
      return new CityInfo(city, country, tempAndTime.ConvertTempToFahrenheit(), tempAndTime.convertTimeToString());
    }
  }

  public void requestReservation(String cityName, String level, String email) {
    String msg = "{\"cityName\": \"" + cityName + "\" \"level\": \"" + level + "\" \"email\": \"" +email+ "\"}";
        System.out.println("Sending message: " + msg);
        rabbitTemplate.convertAndSend(fanout.getName(), "", msg);
  }
}
