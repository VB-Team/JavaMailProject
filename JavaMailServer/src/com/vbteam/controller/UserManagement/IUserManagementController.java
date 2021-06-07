/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.controller.UserManagement;

import com.vbteam.models.User;
import java.util.List;

/**
 *
 * @author schea
 */
public interface IUserManagementController {
    public User addUser(User user);
    public boolean deletedUser(int userId);
    public User updateUser(User user);
    public List<User> listUser();
    public int IncomingMailCount(int userId);
    public int OutgoingMailCount(int userId);

}
