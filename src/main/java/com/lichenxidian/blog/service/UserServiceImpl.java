package com.lichenxidian.blog.service;

import com.lichenxidian.blog.dao.UserRepository;
import com.lichenxidian.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 服务层，而且还是面向接口编程
@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndAndPassword(username, password);
        return user;
    }
}
