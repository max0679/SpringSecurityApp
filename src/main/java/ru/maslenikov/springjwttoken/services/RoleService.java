package ru.maslenikov.springjwttoken.services;

import org.springframework.stereotype.Service;
import ru.maslenikov.springjwttoken.models.Role;
import ru.maslenikov.springjwttoken.repositories.RoleRepository;

import java.util.NoSuchElementException;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name).orElseThrow(() -> new NoSuchElementException("Роль " + name + " не найдена"));
    }

}
