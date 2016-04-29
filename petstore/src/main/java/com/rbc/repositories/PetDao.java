package com.rbc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rbc.model.Pet;

@Transactional
@Repository
public interface PetDao extends JpaRepository<Pet, Integer> {
	
}
