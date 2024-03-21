package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FilmController.class)
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addFilmShouldReturn200() throws Exception {
        Film film = new Film(null, "name", "description", LocalDate.now(), 200);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(film.getName()))
                .andExpect(jsonPath("$.description").value(film.getDescription()))
                .andExpect(jsonPath("$.releaseDate").value(film.getReleaseDate().toString()))
                .andExpect(jsonPath("$.duration").value(film.getDuration()));
    }

    @Test
    public void shouldReturn400WhenCreateFilmBlankName() throws Exception {
        Film film = new Film(null, "   ", "description", LocalDate.now(), 200);

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400WhenCreateFilmWithLargeDescription() throws Exception {
        Film film = new Film(null, "name", "descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription", LocalDate.now(), 200);


        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400WhenCreateFilmWithIllegalReleaseDate() throws Exception {
        Film film = new Film(
                null, "name", "description",
                LocalDate.of(1895, 12, 27), 200
        );

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestWhenCreateFilmWithNegativeDuration() throws Exception {
        Film film = new Film(
                null, "name", "description",
                LocalDate.now(), -200
        );

        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsBytes(film))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
