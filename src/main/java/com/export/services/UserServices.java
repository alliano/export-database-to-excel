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
        // exportToExcel();
        generateFakeUser();
    }



    public void generateFakeUser() {
        List<User> users = this.dataFaker.getUsers(500);
        for (int i = 0; i < users.size(); i++) {
            users.get(i).getRole().forEach( r -> System.out.println(r.getDescription()+"\n"));
        }
        this.userRepo.saveAll(users);
    }



    public List<User> findAll(){
        return this.userRepo.findAll();
    }
    public void exportToExcel(){
        writeExcelFile.dataUser(findAll());
    }
}
