package com.export.services;


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
        exportToExcel();
    }

    public void generateFakeUser(){
        this.userRepo.saveAll(this.dataFaker.userFake(500));
    }

    public Iterable<User> findAll(){
        return this.userRepo.findAll();
    }
    public void exportToExcel(){
        writeExcelFile.dataUser(findAll());
    }
}
