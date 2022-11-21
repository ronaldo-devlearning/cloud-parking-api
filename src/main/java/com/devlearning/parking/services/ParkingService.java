package com.devlearning.parking.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devlearning.parking.domain.Parking;
import com.devlearning.parking.exceptions.ParkingNotFoundException;
import com.devlearning.parking.repositories.ParkingRepository;

@Service
public class ParkingService {
	
	private final ParkingRepository parkingRepository;
	
	
	public ParkingService(ParkingRepository parkingRepository) {
		this.parkingRepository = parkingRepository;
	}

	/*static {
		var id = getUUID();
		var id1 = getUUID();

		Parking parking = new Parking(id, "DMS-1111", "SC", "Celta", "Preto");
		Parking parking1 = new Parking(id1, "WAS-1234", "SP", "Gol", "Vermelho");

		parkingMap.put(id, parking);
		parkingMap.put(id1, parking1);
	}*/
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Parking> findAll(){
		return parkingRepository.findAll();
	}

	private static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	@Transactional(readOnly = true)
	public Parking findById(String id) {
		return parkingRepository.findById(id).orElseThrow(() ->
				new ParkingNotFoundException(id));
	}

	@Transactional
	public Parking create(Parking parkingCreate) {
		String uuid = getUUID();
		parkingCreate.setId(uuid);
		parkingCreate.setEntryDate(LocalDateTime.now());
		parkingRepository.save(parkingCreate);
		return parkingCreate;
	}

	@Transactional
	public void delete(String id) {
		findById(id);
		parkingRepository.deleteById(id); 
	}

	@Transactional
	public Parking update(String id, Parking parkingCreate) {
		Parking parking = findById(id);
		parking.setColor(parkingCreate.getColor());
		parking.setState(parkingCreate.getState());
		parking.setModel(parkingCreate.getModel());
		parking.setLicense(parkingCreate.getLicense());
		parkingRepository.save(parking);
		return parking;
	}

	@Transactional
	public Parking checkOut(String id) {
		Parking parking = findById(id);
		parking.setExitDate(LocalDateTime.now());
		parking.setBill(ParkingCheckout.getBill(parking));
		parkingRepository.save(parking);
		return parking;
	}
}
