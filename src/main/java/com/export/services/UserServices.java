package com.export.services;


import java.io.IOException;
import java.util.List;

import org.hibernate.QueryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> generateFakeUser(int size) {
        List<User> users = this.dataFaker.getUsers(size);
        try {
            this.userRepo.saveAll(users);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (QueryException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /*
     * method ini digunkan untuk mengquery atau menampilkan semua user
     */
    public ResponseEntity<List<UserDetailDto>> findAllUserDetail(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(this.userRepo.findAllUserDetail());
        }catch(QueryException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /*
     * method ini digunkan untuk mengekspor data yang berada didalam database menajadi file excel
     */
    public void exportToExcel(){
        writeExcelFile.dataUser(this.userRepo.findAll());
    }

    /*
     * methoid ini untuk mengeksport detail user
     */
    public ResponseEntity<?> exportToExcelDetailUser() {
        this.writeExcelFile.userDetails(this.userRepo.findAllUserDetail());
        try {
            this.writeExcelFile.execute();
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException IOX) {
            IOX.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
