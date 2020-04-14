package com.netcracker.educ.printing.service;

import com.netcracker.educ.printing.model.bean.Role;
import com.netcracker.educ.printing.model.entity.Address;
import com.netcracker.educ.printing.model.entity.City;
import com.netcracker.educ.printing.model.entity.User;
import com.netcracker.educ.printing.model.repository.AddressRepo;
import com.netcracker.educ.printing.model.repository.CityRepo;
import com.netcracker.educ.printing.model.repository.UserRepo;
import com.netcracker.educ.printing.model.representationModel.AddressRepresent;
import com.netcracker.educ.printing.model.representationModel.UserRepresent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SearchService {

    private UserService userService;
    private UserRepo userRepo;
    private AddressRepo addressRepo;
    private CityRepo cityRepo;

    public SearchService(UserService userService, AddressRepo addressRepo, CityRepo cityRepo,UserRepo userRepo) {
        this.userService = userService;
        this.addressRepo = addressRepo;
        this.cityRepo = cityRepo;
        this.userRepo=userRepo;
    }


    public List<UserRepresent> searchExecutorsByAddress(String city) {

        List<User> users=userRepo.findByRoleAndAddressesCityName(cityRepo.findAllByTitle(city),Role.EXECUTOR);
        return userService.usersToUserRepresents(users);

    }






}