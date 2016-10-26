package com.jmessenger.manager;

import com.jmessenger.data.form.RegisterForm;
import com.jmessenger.data.model.Country;
import com.jmessenger.data.model.User;
import com.jmessenger.exception.DataBaseException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserManager extends UserDetailsService {

    User save(RegisterForm form) throws DataBaseException;

    Long getCountByCountry(String country);

    List<Country> getUsersCountByCountries();

    List<User> getList(long id);

    Boolean isEmailExist(String email);
}
