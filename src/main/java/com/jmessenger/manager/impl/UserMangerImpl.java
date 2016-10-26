package com.jmessenger.manager.impl;

import com.jmessenger.data.form.RegisterForm;
import com.jmessenger.data.model.Country;
import com.jmessenger.data.model.CurrentUser;
import com.jmessenger.data.model.User;
import com.jmessenger.exception.DataBaseException;
import com.jmessenger.manager.UserManager;
import com.jmessenger.repository.UserRepository;
import com.jmessenger.util.Generator;
import org.springframework.context.ApplicationListener;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserMangerImpl implements UserManager, ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserRepository repository;

    @Override
    public User save(RegisterForm form) throws DataBaseException {
        try {
            User user = new User();
            user.setFullName(form.getFullName());
            user.setCountry(form.getCountry());
            user.setChannel(Generator.channel());
            user.setDob(form.getDob());
            user.setEmail(form.getEmail());
            user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));

            return repository.save(user);
        } catch (DataAccessException e) {
            throw new DataBaseException(e);
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
