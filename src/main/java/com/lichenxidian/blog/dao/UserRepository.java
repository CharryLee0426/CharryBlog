package com.lichenxidian.blog.dao;

import com.lichenxidian.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

// 数据持久层
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndAndPassword(String username, String password);
}
