/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.authenticate;

import com.vbteam.models.User;

/**
 *
 * @author schea
 */
public interface IAuthService {
   public User Login(String UserName,String Password);
   public User Register(User user);
}