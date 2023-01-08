package com.export.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.export.domains.entities.User;
import com.export.domains.repositories.UserRepositories;
import com.export.helper.DataFaker;
import com.export.helper.WriteExcel;


@Service
public class UserServices {

    private final DataFaker dataFaker;

    private final UserRepositories userRepo;

    private final WriteExcel writeExcelFile;

    public UserServices(DataFaker dataFaker, UserRepositories userRepositories, WriteExcel excel){
        this.dataFaker = dataFaker;
        this.userRepo = userRepositories;
        this.writeExcelFile = excel;
        exportToExcelDetailUser();
    }

    /**
     * method ini digunakan untuk mengenerate user palsu beserta role nya
     * menggunkan bantukan liberaly dari Faker (com.github.javafaker.Faker)
     */
    public void generateFakeUser() {
        List<User> users = this.dataFaker.getUsers(500);
        this.userRepo.saveAll(users);
    }

    /*
     * method ini digunkan untuk mengquery atau menampilkan semua user
     */
    public List<User> findAll(){
        return this.userRepo.findAll();
    }

    /*
     * method ini digunkan untuk mengekspor data yang berada didalam database menajadi file excel
     */
    public void exportToExcel(){
        writeExcelFile.dataUser(findAll());
    }

    /*
     * methoid ini untuk mengeksport detail user
     */
    public void exportToExcelDetailUser(){
        this.writeExcelFile.userDetails(this.userRepo.findAllUserDetail());
    }

}
