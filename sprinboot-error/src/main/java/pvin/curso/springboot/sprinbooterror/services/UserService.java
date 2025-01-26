package pvin.curso.springboot.sprinbooterror.services;

import pvin.curso.springboot.sprinbooterror.controllers.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    List<User> findAll();
}
