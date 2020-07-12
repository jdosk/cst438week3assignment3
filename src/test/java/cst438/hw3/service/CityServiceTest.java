package cst438.hw3.service;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.given;

import cst438.hw3.domain.City;
import cst438.hw3.domain.CityInfo;
import cst438.hw3.domain.CityRepository;
import cst438.hw3.domain.Country;
import cst438.hw3.domain.CountryRepository;
import cst438.hw3.domain.TempAndTime;
import cst438.hw3.service.CityService;
import cst438.hw3.service.WeatherService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CityServiceTest {
  @MockBean
  private WeatherService weatherService;

  @Autowired
  private CityService cityService;

  @MockBean
  private CityRepository cityRepository;

  @MockBean
  private CountryRepository countryRepository;

  @Test
  public void contextLoads() {
  }

  // First test will test that one city is successfully found
  @Test
  public void testCityFound() throws Exception {
    // Test Variables
    Country country = new Country("TST", "TestCountry");
    City city = new City(1,"TestCity", "TST", "TestDistrict", 1);
    TempAndTime tempAndTime = new TempAndTime(273.15, 0000000000, 00000);
    List<City> cities = new ArrayList<>();
    cities.add(city);

    // Mock Tests
    given(countryRepository.findByCode("TST")).willReturn(country);
    given(cityRepository.findByName("TestCity")).willReturn(cities);
    given(weatherService.getTempAndTime("TestCity")).willReturn(tempAndTime);

    // Return Values
    CityInfo actualResult = cityService.getCityInfo("TestCity");
    CityInfo expectedResult = new CityInfo(city, country.getName(), tempAndTime.ConvertTempToFahrenheit(), tempAndTime.convertTimeToString());

    // Assertion
    assertEquals(expectedResult, actualResult);
  }

  // Test for a city not in database
  @Test
  public void testCityNotFound() {
    // Test Variables
    Country country = new Country("TST", "TestCountry");
    City city = new City(1,"TestCity", "TST", "TestDistrict", 1);
    TempAndTime tempAndTime = new TempAndTime(273.15, 0000000000, 00000);
    List<City> cities = new ArrayList<>();
    cities.add(city);

    // Mock Tests
    given(countryRepository.findByCode("TST")).willReturn(country);
    given(cityRepository.findByName("TestCity")).willReturn(cities);
    given(weatherService.getTempAndTime("TestCity")).willReturn(tempAndTime);

    // Return Values
    CityInfo actualResult = cityService.getCityInfo("InvalidTestCity");
    CityInfo expectedResult = null;

    // Assertion
    assertEquals(expectedResult, actualResult);
  }

  // Test for situation when multiple cities with same name exist
  @Test
  public void testCityMultiple() {
    // Test Variables
    Country country1 = new Country("TS1", "TestCountry1");
    Country country2 = new Country("TS2", "TestCountry2");
    City city1 = new City(1,"TestCity", "TS1", "TestDistrict", 1);
    City city2 = new City(2,"TestCity", "TS2", "TestDistrict", 1);
    TempAndTime tempAndTime = new TempAndTime(273.15, 0000000000, 00000);
    List<City> cities = new ArrayList<>();
    cities.add(city1);
    cities.add(city2);

    // Mock Tests
    given(countryRepository.findByCode("TS1")).willReturn(country1);
    given(countryRepository.findByCode("TS2")).willReturn(country2);
    given(cityRepository.findByName("TestCity")).willReturn(cities);
    given(weatherService.getTempAndTime("TestCity")).willReturn(tempAndTime);

    // Return Values
    CityInfo actualResult = cityService.getCityInfo("TestCity");
    CityInfo expectedResult = new CityInfo(city1, country1.getName(), tempAndTime.ConvertTempToFahrenheit(), tempAndTime.convertTimeToString());

    // Assertion
    assertEquals(expectedResult, actualResult);
  }
}
