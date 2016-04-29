package com.rbc.services;

import com.rbc.model.Pet;

public interface PetService {

	Pet addPet(Pet pet);

	Pet getPetById(Integer id);

	void deletePet(Integer id);

}
