package com.export.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.export.domains.entities.User;
import com.export.domains.repositories.UserRepositories;
import com.export.dtos.UserDetailDto;
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
    }

    /**
     * method ini digunakan untuk mengenerate user palsu beserta role nya
     * menggunkan bantukan liberaly dari Faker (com.github.javafaker.Faker)
     */
    public void generateFakeUser(int size) {
        List<User> users = this.dataFaker.getUsers(size);
        for (User user : users) {
            System.out.println("secure id : "+user.getSecureId());
            System.out.println("name :"+user.getName());
            System.out.println("email :"+user.getEmail());
            System.out.println("password :"+user.getPassword());
            System.out.println("role : "+user.getRole().get(0).getName());
            System.out.println("description role :"+user.getRole().get(0).getDescription());
            System.out.println("\n");
        }
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
    public List<UserDetailDto> exportToExcelDetailUser() {
        return this.userRepo.findAllUserDetail();
        // this.writeExcelFile.userDetails(this.userRepo.findAllUserDetail());
    }

}
