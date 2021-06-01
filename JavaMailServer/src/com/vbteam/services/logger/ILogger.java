/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vbteam.services.logger;

import com.vbteam.models.Log;
import java.util.List;

/**
 *
 * @author schea
 */
public interface ILogger {
    /**
    * Add logs to database
     * @param log
     * @return if logs added successfully this method return true else return false
    */
    public boolean addLog(Log log);
    /**
     *
     * @return this method return all logs 
     */
    public List<Log> getLogs();
}
