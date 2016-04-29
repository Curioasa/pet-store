package com.rbc.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rbc.exceptions.PetNotFoundException;
import com.rbc.model.Pet;
import com.rbc.repositories.PetDao;
import com.rbc.services.PetService;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDao petDao;
	
	private final Logger logger = Logger.getLogger(PetServiceImpl.class); 
	
	@Override
	public Pet addPet(Pet pet) {
		logger.info("Saving pet: " + pet.toString());
		Pet createdPet = petDao.saveAndFlush(pet);
		logger.info("Pet created successfully. The automatically generated id associated to it is: " + createdPet.getId());
		
		return createdPet;
	}
	
	@Override
	public Pet getPetById(Integer id) {
		logger.info("Retrieving pet by id: " + id);
		Pet pet = petDao.findOne(id);
		if (pet == null) {
			throw new PetNotFoundException("The pet with the id " + id + " does not exist!");
		}
		logger.info("Pet successfully retrived: " + pet);
		
		return pet;
	}
	
	@Override
	public void deletePet(Integer id) {
		logger.info("Deleting pet for id: " + id);
		petDao.delete(id);
		logger.info("Pet successfully deleted: " + id);
	}
}
