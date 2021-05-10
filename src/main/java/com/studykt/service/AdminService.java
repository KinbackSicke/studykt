package com.studykt.service;

import com.studykt.entity.Admin;
import com.studykt.entity.User;

import java.util.List;

public interface AdminService {

    Admin selectById(String username);

    List<User> selectAllUsers();

}
