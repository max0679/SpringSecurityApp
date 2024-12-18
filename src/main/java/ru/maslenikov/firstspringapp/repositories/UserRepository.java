package ru.maslenikov.firstspringapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maslenikov.firstspringapp.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findUserByName(String name);

}
