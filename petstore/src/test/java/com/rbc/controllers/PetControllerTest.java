package com.rbc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbc.context.PetTestContext;
import com.rbc.model.Pet;
import com.rbc.services.PetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PetTestContext.class)
@WebAppConfiguration
public class PetControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PetService petService;

    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        objectMapper = new ObjectMapper();
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getPetById() throws Exception {
        Pet pet = new Pet();
        pet.setId(1);
        when(petService.getPetById(anyInt())).thenReturn(pet);

        mockMvc.perform(get("/pet/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)));

        verify(petService).getPetById(anyInt());
        verify(petService).addPet(any(Pet.class));
        verifyNoMoreInteractions(petService);
    }

    @Test
    public void addPetTest() throws Exception {
        Pet pet = new Pet();
        pet.setId(1);
        when(petService.addPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(pet))
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(1)));

        verify(petService, times(2)).addPet(any(Pet.class));
        verifyNoMoreInteractions(petService);
    }

    @Test
    public void deletePetTest() throws Exception {
        Pet pet = new Pet();
        pet.setId(1);

        mockMvc.perform(delete("/pet/1"))
                .andExpect(status().isAccepted());

        verify(petService).deletePet(anyInt());
        verify(petService).addPet(any(Pet.class));
        verifyNoMoreInteractions(petService);
    }

}
