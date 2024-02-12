package com.testSpring1.questDataTest;

import com.testSpring1.questDataTest.controller.FiremanController;
import com.testSpring1.questDataTest.repository.FiremanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.testSpring1.questDataTest.entity.Fireman;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FiremanController.class)
public class FiremanControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FiremanRepository firemanRepository;

    @Test
    public void getAllVeteran() throws Exception {

        var fireman = mock(Fireman.class);
        when(fireman.getId()).thenReturn(1L);
        when(fireman.getName()).thenReturn("champion");
        when(firemanRepository.getVeteran()).thenReturn(Optional.of(fireman));

        mockMvc.perform(MockMvcRequestBuilders.get("/fireman/veteran"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(fireman.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("champion"));
    }

    // Challenge 1 SpringTesting2 Controller - absence de veteran
    @Test
    public void noVeteran() throws Exception {
        when(firemanRepository.getVeteran()).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/fireman/veteran"))
                .andExpect(status().isNotFound());
        System.out.println("no Veteran - 404 Not Found");
    }

    // Challenge 2 SpringTesting2 Controller - statistiques


}
