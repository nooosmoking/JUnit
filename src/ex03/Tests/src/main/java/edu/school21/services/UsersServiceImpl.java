package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;

import javax.persistence.EntityNotFoundException;

import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    public boolean authenticate(String login, String password) throws EntityNotFoundException, AlreadyAuthenticatedException {
        User user = repository.findByLogin(login);
        if (user.isAuthenticated)
            throw new AlreadyAuthenticatedException("User is already authenticated");
        if (!user.password.equals(password))
            return false;
        user.isAuthenticated = true;
        repository.update(user);
        return true;
    }
}
