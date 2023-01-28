package com.export.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.export.dtos.UserDetailDto;
import com.export.services.UserServices;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController @RequestMapping(method = RequestMethod.GET, path = "/user")
public class UserController {

    private final UserServices userServices;

    @GetMapping(path = "/all")
    private ResponseEntity<List<UserDetailDto>> findAList(){
        return this.userServices.findAllUserDetail();
    }

    @GetMapping(path = "/create/{size}")
    private ResponseEntity<Void> generateFakeUser(@PathVariable(name = "size") int size) {
        return this.userServices.generateFakeUser(size);
    }

    @GetMapping(path = "/exportDetailUser")
    private ResponseEntity<?> exportDetailUser(){
        return this.userServices.exportToExcelDetailUser();
    }

    @PostMapping(path = "/insert")
    public void insert(@RequestParam(name = "file") MultipartFile file) throws SQLException, IOException{
        this.userServices.insert(file);
    }

}
