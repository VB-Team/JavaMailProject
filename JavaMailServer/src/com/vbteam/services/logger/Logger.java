/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.logger;

import com.vbteam.models.Log;
import com.vbteam.socket.Server;
import com.vbteam.utils.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author schea
 */
public class Logger implements ILogger {

    DbContext context;
    Connection connection;
    private static Logger instance = null;

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    @Override
    public boolean addLog(Log log) {
        try {
            int affectedRow = 0;
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
            String insertQuery = "Insert into Logs(Type,ExceptionMessage,CreateDate)values(?,?,?)";
            statement = connection.prepareStatement(insertQuery);
            statement.setString(1, log.getType());
            statement.setString(2, log.getExceptionMessage());
            statement.setTimestamp(3, log.getCreateDate());
            affectedRow += statement.executeUpdate();
            statement.close();
            //connection.close();
            System.out.println("Etkilenen satır sayısı : " + affectedRow);
            if (affectedRow > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Logger Service Exception : " + e.getMessage());
            return false;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<Log> getLogs() {
        try {
            PreparedStatement statement;
            context = new DbContext();

            //connection = context.getConnection();
            connection = Server.connectionPool.getConnection();
            String selectQuery = "Select * From Logs";
            statement = connection.prepareStatement(selectQuery);
            ResultSet rs = statement.executeQuery();
            List<Log> logs = new ArrayList<Log>();
            while (rs.next()) {
                Log log = new Log();
                log.setExceptionMessage(rs.getString(4));
                log.setType(rs.getString(3));
                logs.add(log);
            }
            //connection.close();

            return logs;
        } catch (Exception e) {
            System.out.println("Logger Service Exception : " + e.getMessage());
            return null;
        } finally {
            try {
                //connection.close();
                Server.connectionPool.releaseConnection(connection);
            } catch (Exception e) {
            }
        }
    }

}
