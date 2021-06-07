
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.utils;

import java.sql.Connection;

/**
 *
 * @author ubuntu
 */
public interface IConnectionPool {

    Connection getConnection();

    boolean releaseConnection(Connection connection);

    String getUrl();

    String getUser();

    String getPassword();
}
