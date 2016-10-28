package com.example.manager;

import com.example.data.form.RegisterForm;
import com.example.data.model.Country;
import com.example.data.model.User;
import com.example.exception.DatabaseException;
import com.mysql.fabric.xmlrpc.base.Data;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserManager extends UserDetailsService {

    User save(RegisterForm form) throws DatabaseException;

    Long getCountByCountry(String country);

    List<Country> getUsersCountByCountries();

    List<User> getList(long id);

    Boolean isEmailExist(String email);

    List<User> getAllUsers() throws DatabaseException;

    void delete(long id) throws DatabaseException;

    void deleteByEmail(String email) throws DatabaseException;
}
