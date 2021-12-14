package com.lichenxidian.blog.service;

import com.lichenxidian.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
