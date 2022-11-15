package com.devlearning.parking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devlearning.parking.domain.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {

}
