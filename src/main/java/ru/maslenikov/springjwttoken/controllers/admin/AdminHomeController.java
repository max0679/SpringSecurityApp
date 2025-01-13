package ru.maslenikov.springjwttoken.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maslenikov.springjwttoken.services.AdminService;

@Controller
@RequestMapping(value = {"/admin", "/admin/"})
public class

AdminHomeController {

    private final AdminService adminService;

    public AdminHomeController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("")
    public String index() {
        adminService.doAdminFunc();
        return "admin/index";
    }

}
