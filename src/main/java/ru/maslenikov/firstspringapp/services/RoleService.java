package ru.maslenikov.firstspringapp.services;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maslenikov.firstspringapp.models.Role;
import ru.maslenikov.firstspringapp.repositories.RoleRepository;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

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
