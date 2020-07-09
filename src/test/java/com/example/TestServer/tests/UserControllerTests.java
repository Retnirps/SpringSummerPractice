package com.example.TestServer.tests;

import com.example.TestServer.controllers.UserController;
import com.example.TestServer.dto.*;
import com.example.TestServer.models.StatisticsModel;
import com.example.TestServer.models.Triple;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.services.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atMost;

@SpringBootTest
class UserControllerTests {
    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private UserController userController;

    @Test
    void whenAddUser_thenReturnSingletonMapWithId() {
        Mockito.when(userService.addUser(new UserModel("User1", "User1@mail.ru")))
                .thenReturn(Collections.singletonMap("id", 1));
        final Map result = userController.addUser(new RequestAddUser("User1", "User1@mail.ru"));
        Assertions.assertEquals(Collections.singletonMap("id", 1), result, "incorrect method response");

        Mockito.verify(userService, atMost(1)).addUser(Mockito.eq(new UserModel("User1", "User1@mail.ru")));
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void whenGetUserById_thenReturnResponseWithUserInfo() {
        Mockito.when(userService.getUserById(100))
                .thenReturn(new UserModel(100, "User1", "User1@mail.ru"));
        ResponseGetUserById response = userController.getUserById(100L);
        assertEquals(new ResponseGetUserById(100, "User1", "User1@mail.ru"), response, "incorrect response returned");

        Mockito.verify(userService, atMost(1)).getUserById(100);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void setStatusForId() {
        Mockito.when(userService.setStatusForId(100, 1)).thenReturn(new UserModel(100, 0, 1));
        ResponseSetStatusForId response = userController.setStatusForId(new RequestSetStatusForId(100, "ONLINE"));
        assertEquals(new ResponseSetStatusForId(100, 0, 1), response, "incorrect response returned");

        Mockito.verify(userService, atMost(1)).setStatusForId(100, 1);
        Mockito.verifyNoMoreInteractions(userService);
    }

    @Test
    void getAllUsersChangedStatusAfterTimestamp() {
        long currentTime = Instant.now().getEpochSecond();
        Mockito.when(userService.getUsersChangedStatusAfterTimestamp("OFFLINE", 1594291692))
                .thenReturn(new Triple<>(1, currentTime, Arrays.asList(
                        new StatisticsModel("User4", "User4@mail.ru", 0, 1594291737))));
        Triple<List<ResponseStatisticsDto>> response = userController.getAllUsersChangedStatusAfterTimestamp("OFFLINE", 1594291692);
        assertEquals(new Triple<>(1, currentTime, Arrays.asList(new ResponseStatisticsDto(0, 1594291737, "User4", "User4@mail.ru"))),
                response, "incorrect response returned");

        Mockito.verify(userService, atMost(1)).getUsersChangedStatusAfterTimestamp("OFFLINE", 1594291692);
        Mockito.verifyNoMoreInteractions(userService);
    }
}