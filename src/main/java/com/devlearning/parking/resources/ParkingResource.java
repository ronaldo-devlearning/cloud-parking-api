package com.devlearning.parking.resources;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devlearning.parking.domain.Parking;
import com.devlearning.parking.resources.dto.ParkingDTO;
import com.devlearning.parking.resources.mapper.ParkingMapper;
import com.devlearning.parking.services.ParkingService;

@RestController
@RequestMapping("/parkings")
public class ParkingResource {


	private final ParkingService parkingService;
	private final ParkingMapper parkingMapper;
	
	public ParkingResource(ParkingService parkingService, ParkingMapper parkingMapper) {
		this.parkingService = parkingService;
		this.parkingMapper = parkingMapper;
	}
	
	@GetMapping
	public List<ParkingDTO> findAll(){
		List<Parking> parkingList = parkingService.findAll();
		List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
		return result;
	}
	
}
