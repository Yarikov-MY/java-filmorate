package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUserShouldReturn200() throws Exception {
        User user = new User(
                null, "mail@mail.ru", "login", "name", LocalDate.now()
        );

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.login").value(user.getLogin()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.birthday").value(user.getBirthday().toString()));
    }

    @Test
    public void shouldReturn400WhenCreateUserLoginWhitespace() throws Exception {
        User user = new User(
                null, "mail@mail.ru", "login login", "name", LocalDate.now()
        );
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400WhenCreateUserIllegalEmail() throws Exception {
        User user = new User(
                null, "mailmail.ru", "login", "name", LocalDate.now()
        );
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn400WhenCreateUserFutureBirthday() throws Exception {
        User user = new User(
                null, "mail@mail.ru", "login", "name", LocalDate.now().plusDays(1)
        );

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
