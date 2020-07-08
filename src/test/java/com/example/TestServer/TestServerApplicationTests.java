package com.example.TestServer;

import com.example.TestServer.entities.RequestLog;
import com.example.TestServer.entities.Statistics;
import com.example.TestServer.entities.User;
import com.example.TestServer.exceptions.ResourceNotFoundException;
import com.example.TestServer.models.StatisticsModel;
import com.example.TestServer.models.Triple;
import com.example.TestServer.models.UserModel;
import com.example.TestServer.repos.StatisticsRepository;
import com.example.TestServer.repos.UserRepository;
import com.example.TestServer.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.atMost;

@SpringBootTest
class TestServerApplicationTests {
	@MockBean
	private UserRepository userRepository;

	@MockBean
	private StatisticsRepository statisticsRepository;

	@Autowired
	private UserService userService;

	@Test
	public void whenUserIdNotExist_throwsResourceNotFoundException() {
		Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		Long id = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
		try {
			userService.getUserById(id);
			Assertions.fail("exception wasn't thrown");
		} catch (Exception e) {
			Assertions.assertEquals(ResourceNotFoundException.class, e.getClass(), "wrong exception type");
			Assertions.assertEquals("user ID:" + id + " not found", e.getMessage(), "wrong message");
		}
		Mockito.verify(userRepository).findById(eq(id));
		Mockito.verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void whenUserIdExists_thenReturnUserModel() {
		final User user = new User(100L, 0, "", "");
		UserModel userModel = new UserModel(user.getUserId(), user.getUsername(), user.getEmail());
		Mockito.when(userRepository.findById(100L)).thenReturn(Optional.of(user));

		final UserModel result = userService.getUserById(100L);
		Assertions.assertEquals(new UserModel(100L, "", ""), result, "user was modified");

		Mockito.verify(userRepository, atMost(1)).findById(eq(100L));
		Mockito.verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void whenUserAdded_thenReturnIdJson() {
		User user = new User("User1", "User1@mail.ru");
		Mockito.when(userRepository.save(user)).thenReturn(new User(100, 0, "User1", "User1@mail.ru"));

		UserModel userModel = new UserModel("User1", "User1@mail.ru");
		Assertions.assertEquals(Collections.singletonMap("userId", 100).toString(), userService.addUser(userModel).toString(), "wrong id returned");

		Mockito.verify(userRepository, atMost(1))
				.save(eq(new User("User1", "User1@mail.ru")));
		Mockito.verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void whenExistingUserChangeStatus_thenReturnUserModel() {
		final User user = new User(100L, 0, "User1", "User1@mail.ru");
		UserModel userModel = new UserModel(user.getUserId(), user.getUsername(), user.getEmail());
		Mockito.when(userRepository.findById(100L)).thenReturn(Optional.of(user));

		final UserModel result = userService.setStatusForId(100, 1);
		Mockito.when(userRepository.save(user)).thenReturn(new User(100, 1, "User1", "User1@mail.ru"));
		Assertions.assertEquals(new UserModel(100L, 0, 1), result, "status wasn't changed");

		Mockito.verify(userRepository, atMost(1)).findById(eq(100L));
		Mockito.verify(userRepository, atMost(1))
				.save(eq(new User(100, 1, "User1", "User1@mail.ru")));
		Mockito.verifyNoMoreInteractions(userRepository);
	}

	@Test
	public void whenNotExistingUserChangeStatus_throwsResourceNotFoundException() {
		Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		Long id = ThreadLocalRandom.current().nextLong(1, Long.MAX_VALUE);
		try {
			userService.getUserById(id);
			Assertions.fail("exception wasn't thrown");
		} catch (Exception e) {
			Assertions.assertEquals(ResourceNotFoundException.class, e.getClass(), "wrong exception type");
			Assertions.assertEquals("user ID:" + id + " not found", e.getMessage(), "wrong message");
		}
		Mockito.verify(userRepository).findById(eq(id));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
}
