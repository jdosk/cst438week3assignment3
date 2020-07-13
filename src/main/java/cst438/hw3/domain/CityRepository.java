package cst438.hw3.domain;

import cst438.hw3.domain.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CityRepository extends JpaRepository<City, Long> {
  List<City> findByName(String name);
}

