package com.rbc.services.impl;

import com.rbc.exceptions.PetNotFoundException;
import com.rbc.model.Pet;
import com.rbc.repositories.PetDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceImplTest {

    @Mock
    PetDao petDao;

    @InjectMocks
    PetServiceImpl petService;

    @Test
    public void addPetTest() {
        Pet pet = new Pet();
        when(petDao.saveAndFlush(any(Pet.class))).thenReturn(pet);

        Pet resultPet = petService.addPet(pet);

        assertEquals(pet, resultPet);

        verify(petDao).saveAndFlush(any(Pet.class));
        verifyNoMoreInteractions(petDao);
    }

    @Test
    public void getPetByIdTest() {
        Pet pet = new Pet();
        when(petDao.findOne(anyInt())).thenReturn(pet);

        Pet resultPet = petService.getPetById(1);

        assertNotNull(resultPet);

        verify(petDao).findOne(anyInt());
        verifyNoMoreInteractions(petDao);
    }

    @Test(expected = PetNotFoundException.class)
    public void getPetByIdExceptionTest() {
        when(petDao.findOne(anyInt())).thenReturn(null);

        petService.getPetById(1);
    }

    @Test
    public void deletePetTest() {
        petService.deletePet(1);

        verify(petDao).delete(anyInt());
        verifyNoMoreInteractions(petDao);
    }
}
