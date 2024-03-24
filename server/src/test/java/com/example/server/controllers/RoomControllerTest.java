package com.example.server.controllers;

import com.example.server.dto.TermDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.services.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoomService roomService;

    private static final String URL = "/api/room";

    @Test
    void shouldReturnBadRequestWhenTermNotFoundException() throws Exception {
        long id = 0;
        List<TermDto> termsDto = new ArrayList<>();
        String requestJson = new ObjectMapper().writeValueAsString(termsDto);
        doThrow(TermNotFoundException.class).when(roomService).assignTerms(id, termsDto);

        mvc.perform(MockMvcRequestBuilders.put(URL + "/" + id + "/terms").contentType(APPLICATION_JSON).content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenRoomNotFoundException() throws Exception {
        long id = 0;
        when(roomService.getRoom(id)).thenThrow(RoomNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.get(URL + "/" + id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnOkWhenStatusOk() throws Exception {
        List<Room> rooms = new ArrayList<>();
        when(roomService.getRooms()).thenReturn(rooms);

        mvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
