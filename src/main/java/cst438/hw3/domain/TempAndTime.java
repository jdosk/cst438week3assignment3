package cst438.hw3.domain;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TempAndTime {
  public double temp;
  public long time;
  public int timeZone;

  public TempAndTime(double temp, long time, int timeZone) {
    this.temp = temp;
    this.time = time;
    this.timeZone = timeZone;
  }

  // Converts temp form Kelvin to Farenheit and returns new value
  public double ConvertTempToFahrenheit() {
    // Shortened this line up a little
    return Math.round((temp - 273.15) * 9.0 / 5.0 + 32.0);
  }


  // Converts the time into the correct string format and returns it
  public String convertTimeToString() {
    // Where are you adding in the time zone and adjusting for time offset?
    // Specifications request local time.

    // FIXED Per Code Review from Daniel (and his code suggestion)
    Instant instant = Instant.ofEpochSecond(this.time);
    ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(this.timeZone);
    OffsetDateTime offsetDateTime = instant.atOffset(zoneOffset);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    return offsetDateTime.format(dateTimeFormatter);
  }

}
