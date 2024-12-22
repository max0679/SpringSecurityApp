package ru.maslenikov.firstspringapp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doAdminFunc() {
        System.out.println("метод админа!");
    }

}
