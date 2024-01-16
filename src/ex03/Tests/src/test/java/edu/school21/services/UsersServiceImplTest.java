package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.services.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class UsersServiceImplTest {
    private User user;
    private UsersRepository repository;
    private UsersServiceImpl service;

    private final String CORRECT_LOGIN = "CORRECT_LOGIN";
    private final String INCORRECT_LOGIN = "INCORRECT_LOGIN";
    private final String CORRECT_PASSWORD = "CORRECT_PASSWORD";
    private final String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";

    @BeforeEach
    public void init() {
        repository = Mockito.mock(UsersRepository.class);
        service = new UsersServiceImpl(repository);
        user = new User(1, CORRECT_LOGIN, CORRECT_PASSWORD, false);
        Mockito.when(repository.findByLogin(CORRECT_LOGIN)).thenReturn(user);
        Mockito.when(repository.findByLogin(INCORRECT_LOGIN)).thenThrow(new EntityNotFoundException());
        Mockito.doNothing().when(repository).update(Mockito.any(User.class));
    }

    @Test
    public void correctAuthenticateTest() throws AlreadyAuthenticatedException {
        assertTrue(service.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
        Mockito.verify(repository).findByLogin(CORRECT_LOGIN);
        Mockito.verify(repository).update(user);
    }

    @Test
    public void incorrectLoginTest() throws AlreadyAuthenticatedException {
        assertThrows(EntityNotFoundException.class, () -> service.authenticate(INCORRECT_LOGIN, CORRECT_PASSWORD));
        Mockito.verify(repository).findByLogin(INCORRECT_LOGIN);
    }

    @Test
    public void incorrectPasswordTest() throws AlreadyAuthenticatedException {
        assertFalse(service.authenticate(CORRECT_LOGIN, INCORRECT_PASSWORD));
        Mockito.verify(repository).findByLogin(CORRECT_LOGIN);
    }
}
