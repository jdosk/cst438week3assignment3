package cst438.hw3.controller;

import cst438.hw3.service.CityService;
import cst438.hw3.domain.CityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityRestController {

  @Autowired
  private CityService cityService;

  @GetMapping("/api/cities/{city}")
  public ResponseEntity<CityInfo> getWeather(@PathVariable("city") String cityName) {
    CityInfo cityInfo = cityService.getCityInfo(cityName);

    // If city doesnt exist then dislpay not found otherwise display object in json format
    if (cityInfo == null) {
      return new ResponseEntity<CityInfo>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<CityInfo>(cityInfo, HttpStatus.OK);
    }
  }
}
