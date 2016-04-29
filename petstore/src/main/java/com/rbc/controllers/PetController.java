package com.rbc.controllers;

import com.rbc.model.Pet;
import com.rbc.services.PetService;
import com.rbc.utils.PetStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;

@RestController(value = "/pet")
public class PetController {

    @Autowired
    private PetService petService;

    private Logger logger = Logger.getLogger(PetController.class);

    @RequestMapping(path = "/pet",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Pet addPet(@RequestBody Pet pet, HttpServletRequest request) {
        logger.info("A request for adding a new pet has been initiated by: " + extractUserName(request));
        return petService.addPet(pet);
    }

    @RequestMapping(path = "/pet/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('USER')")
    public Pet getPetById(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        logger.info("A request for retrieving a pet has been initiated by: " + extractUserName(request));
        return petService.getPetById(id);
    }

    @RequestMapping(path = "/pet/{id}",
            method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePet(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
        logger.info("A request for deleting a pet has been initiated by: " + extractUserName(request));
        petService.deletePet(id);
    }

    /**
     * Added just for testing purposes
     */
    @PostConstruct
    public void setUpInitialData() {
        Pet pet = new Pet();
        pet.setName("Rex");
        pet.setCategory("Dog");
        pet.setPictureUrls(new ArrayList<>(Collections.singletonList("http://g-ecx.images-amazon.com/images/G/01/img15/pet-products/small-tiles/23695_pets_vertical_store_dogs_small_tile_8._CB312176604_.jpg")));
        pet.setTags(new ArrayList<>(Collections.singletonList("beloved")));
        pet.setStatus(PetStatus.AVAILABLE);

        petService.addPet(pet);
    }

    private String extractUserName(HttpServletRequest request) {
        return request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Test User";
    }
}
