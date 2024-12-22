package com.DAO;

import java.util.List;

import com.entity.User;

public interface UserDAO {
    public boolean userRegister(User us);
    public User login(String username, String password);
    public boolean updateUserInfo(User us);
    public boolean deleteUser(int userId);
    public boolean updatePassword(int userId, String newPassword);
    public List<User> getAllUsers();

    
    
}