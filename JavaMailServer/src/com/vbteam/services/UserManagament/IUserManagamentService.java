/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.UserManagament;

import com.vbteam.models.User;
import java.util.List;

/**
 *
 * @author schea
 */
public interface IUserManagamentService {
    public User AddUser(User user);
    public boolean DeletedUser(int userId);
    public User UpdateUser(User user);
    public List<User> ListUser();

}
