/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.conf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author anton
 */
public class UnpredictableConf {

    private String udpPort;
    private String comunityString;
    private int refreshRate;
    private int tableSize;
    private int numberSize;
    private String firstSeedPath;
    private String commandKey = "unpredictable-agent dfh8ty3t-4rq8549";

    public UnpredictableConf() {
    }

    public UnpredictableConf(String udpPort, String comunityString, int refreshRate, int tableSize, int numberSize, String firstSeedPath) {
        this.udpPort = udpPort;
        this.comunityString = comunityString;
        this.refreshRate = refreshRate;
        this.tableSize = tableSize;
        this.numberSize = numberSize;
        this.firstSeedPath = firstSeedPath;
    }

    public String getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(String udpPort) {
        this.udpPort = udpPort;
    }

    public String getComunityString() {
        return comunityString;
    }

    public void setComunityString(String comunityString) {
        this.comunityString = comunityString;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public int getNumberSize() {
        return numberSize;
    }

    public void setNumberSize(int numberSize) {
        this.numberSize = numberSize;
    }

    public String getFirstSeedPath() {
        return firstSeedPath;
    }

    public void setFirstSeedPath(String firstSeedPath) {
        this.firstSeedPath = firstSeedPath;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = commandKey;
    }
    
    public void parseConfFile(String filePath) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] value = line.split(" ");
                
                if(value[0].equals("UDP-PORT")){
                    udpPort = value[1];
                }
                if(value[0].equals("COMMUNITY-STRING")){
                    comunityString = value[1];
                }
                if(value[0].equals("REFRESH-RATE")){
                    refreshRate = Integer.parseInt(value[1]);
                }  
                if(value[0].equals("TABLE-SIZE")){
                    tableSize = Integer.parseInt(value[1]);
                } 
                if(value[0].equals("NUMBER-SIZE")){
                    numberSize = Integer.parseInt(value[1]);
                }
                if(value[0].equals("FIRST-SEED")){
                    firstSeedPath = value[1];
                }                
            }
        }
    }

    @Override
    public String toString() {
        return "UnpredictableConf{" + "udpPort=" + udpPort + ", comunityString=" + comunityString + ", refreshRate=" + refreshRate + ", tableSize=" + tableSize + ", numberSize=" + numberSize + ", firstSeedPath=" + firstSeedPath + '}';
    }

}
