package ru.maslenikov.springjwttoken.services;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void doAdminFunc() {
        System.out.println("метод админа!");
    }

}
