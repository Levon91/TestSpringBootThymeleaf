package com.example.manager.impl;

import com.example.data.form.RegisterForm;
import com.example.data.model.Country;
import com.example.data.model.CurrentUser;
import com.example.data.model.User;
import com.example.exception.DatabaseException;
import com.example.manager.UserManager;
import com.example.repository.UserRepository;
import com.example.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class UserMangerImpl implements UserManager, ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserRepository repository;

    @Override
    public User save(RegisterForm form) throws DatabaseException {
        try {
            User user = new User();
            user.setFullName(form.getFullName());
            user.setCountry(form.getCountry());
            user.setDob(form.getDob());
            user.setEmail(form.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));

            return repository.save(user);
        } catch (DataAccessException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Long getCountByCountry(String country) {
        return repository.countByCountry(country);
    }

    @Override
    public List<User> getList(long id) {
        return repository.getList(id);
    }

    @Override
    public List<Country> getUsersCountByCountries() {
        return repository.getUsersCountByCountries();
    }

    @Override
    public Boolean isEmailExist(String email) {
        return repository.isEmailExist(email);
    }

    @Override
    public void delete(long id) throws DatabaseException {
        repository.delete(id);
    }

    @Override
    public void deleteByEmail(String email) throws DatabaseException {
        repository.deleteByEmail(email);
    }

    @Override
    public List<User> getAllUsers() throws DatabaseException {
        return repository.getAllUsers();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findFirstOneByEmail(username);

        if (user == null) {
            throw new EntityNotFoundException();
        }

        return new CurrentUser(user);
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String userName = ((UserDetails) event.getAuthentication().
                getPrincipal()).getUsername();
        User user = repository.findFirstOneByEmail(userName);
        user.setLastLogin(new Date());
        repository.save(user);
    }
}
