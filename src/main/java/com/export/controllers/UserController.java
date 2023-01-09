package com.export.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.export.dtos.UserDetailDto;
import com.export.services.UserServices;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController @RequestMapping(method = RequestMethod.GET, path = "/user")
public class UserController {

    private final UserServices userServices;

    @GetMapping(path = "/all")
    private List<UserDetailDto> findAList(){
        return this.userServices.exportToExcelDetailUser();
    }

    @GetMapping(path = "/create/{size}")
    private void generateFakeUser(@PathVariable(name = "size") int size) {
        this.userServices.generateFakeUser(size);
    }

}
