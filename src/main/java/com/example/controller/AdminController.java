package com.example.controller;

import com.example.data.model.User;
import com.example.exception.DatabaseException;
import com.example.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by levont on 28/10/2016.
 */
@Controller
public class AdminController {

    @Autowired
    private UserManager userManager;

    @RequestMapping("/admin")
    public String actionHome(Model model) {
        try {
            List<User> users = userManager.getAllUsers();
            model.addAttribute("users", users);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return "admin/home";
    }


}
