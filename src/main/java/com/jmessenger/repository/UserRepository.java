package com.jmessenger.repository;

import com.jmessenger.data.model.Country;
import com.jmessenger.data.model.User;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findFirstOneByEmail(String email);

    Long countByCountry(String country);

    @Query("SELECT new com.jmessenger.data.model.Country(country,COUNT(id)  AS COUNT) FROM User GROUP BY country")
    List<Country> getUsersCountByCountries();

    @Query("SELECT u FROM User u WHERE id != :id")
    List<User> getList(@Param("id") long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.email = :email")
    Boolean isEmailExist(@Param("email") String email);
}
