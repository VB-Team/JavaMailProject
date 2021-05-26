/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.UserManagement;

import com.vbteam.models.User;
import java.util.List;

/**
 *
 * @author schea
 */
public interface IUserManagementService {
    public User addUser(User user);
    public boolean deletedUser(int userId);
    public User updateUser(User user);
    public List<User> listUser();
    public int FromUserMailCount(int userId);
    public int SendUserMailCount(int userId);

}