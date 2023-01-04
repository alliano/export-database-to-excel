package com.export.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.export.domains.entities.User;
import com.github.javafaker.Faker;

@Configuration
public class DataFaker {

    public List<User> userFake(long size){
        List<User> users = new ArrayList<User>();
        Faker faker = new Faker();
        for (int i = 0; i < size; i++) {
            User user = new User();
            user.setId(i);
            user.setName(faker.name().name());
            user.setPassword(faker.code().asin());
            user.setEmail(faker.name().firstName()+"@gmail.com");
            users.add(user);
        }
        return users;
    }
}
