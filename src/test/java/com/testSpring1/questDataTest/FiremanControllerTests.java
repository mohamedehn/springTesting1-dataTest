package com.testSpring1.questDataTest;

import com.testSpring1.questDataTest.controller.FiremanController;
import com.testSpring1.questDataTest.repository.FireRepository;
import com.testSpring1.questDataTest.repository.FiremanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.testSpring1.questDataTest.entity.Fireman;


import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataJpaTest
@WebMvcTest(FiremanController.class)
public class FiremanControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FiremanRepository firemanRepository;

    public void getAllVeteran() throws Exception {

        var fireman = mock(Fireman.class);
        when(fireman.getId()).thenReturn(1L);
        when(fireman.getName()).thenReturn("champion");
        when(firemanRepository.getVeteran()).thenReturn(Optional.of(fireman));

        mockMvc.perform(MockMvcRequestBuilders.get("/fireman/veteran"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.id").value(fireman.getId()))
                .andExpect((ResultMatcher) jsonPath("$.name").value("champion"));
    }
}
