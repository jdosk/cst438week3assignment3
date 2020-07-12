package cst438.hw3.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import cst438.hw3.controller.CityRestController;
import cst438.hw3.domain.City;
import cst438.hw3.domain.CityInfo;
import cst438.hw3.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpStatus;


@RunWith(SpringRunner.class)
@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {
  @MockBean
  private CityService cityService;

  @Autowired
  private MockMvc mvc;

  private JacksonTester<CityInfo> json;

  @Before
  public void setup() {
    JacksonTester.initFields(this, new ObjectMapper());
  }

  @Test
  public void contextLoads() {
  }

  @Test
  public void getCityInfo() throws Exception {
    City city = new City(1, "TestCity", "TST", "TestDistrict",1);
    CityInfo cityInfo = new CityInfo(city, "TestCountry",0.0,"TestTime");

    given(cityService.getCityInfo("TestCity")).willReturn(cityInfo);
    MockHttpServletResponse response = mvc.perform(get("/api/cities/TestCity"))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    CityInfo cityInfoResult = json.parseObject(response.getContentAsString());
    CityInfo expectedResult = new CityInfo(city, "TestCountry", 0.0, "TestTime");

    assertThat(cityInfoResult).isEqualTo(expectedResult);
  }
}
