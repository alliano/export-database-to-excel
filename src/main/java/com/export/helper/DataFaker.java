package com.export.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Configuration;

import com.export.domains.entities.Role;
import com.export.domains.entities.User;
import com.github.javafaker.Faker;

@Configuration
public class DataFaker {

    public List<User> getUsers(long size){
        List<User> users = new ArrayList<User>();
        Faker faker = new Faker();
        AtomicInteger integer = new AtomicInteger();
        for (int i = 0; i < size; i++) {
            User user = new User();
            user.setId(integer.incrementAndGet());
            user.setName(faker.name().name());
            user.setPassword(faker.code().asin());
            user.setEmail(faker.name().firstName()+"@gmail.com");
            users.add(user);
        }
        setRoles(users);
        return users;
    }

    public List<User> setRoles(List<User> users){
        for (int i = 0; i < users.size(); i++) {
             if(i % 2 != 0){
                 Role role = new Role(i, "Admin", "admin can acces and auditing in dashbord");
                 users.get(i).setRole(List.of(role));
             }
             else {
                 Role role = new Role(i, "User", "user couldn't access dashbord");
                 users.get(i).setRole(List.of(role));
             }
        }
        return users;
     }
}
