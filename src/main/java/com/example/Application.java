package com.example;

import com.example.data.form.RegisterForm;
import com.example.data.model.User;
import com.example.enumeration.Role;
import com.example.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserManager userManager;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        RegisterForm user = new RegisterForm();
//        user.setRole(Role.ADMIN);
//        user.setEmail("admin@mail.ru");
//        user.setPassword("admin");
//        user.setFullName("Administrator");

//        userManager.save(user);
    }
}
