/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.client;

import com.project29.randomnumberssnmp.conf.UnpredictableConf;
import com.project29.randomnumberssnmp.server.ManagedObjectFactory;
import com.project29.randomnumberssnmp.server.SNMPAgent;
import com.project29.randomnumberssnmp.ui.Interface;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.snmp4j.smi.OID;

/**
 *
 * @author anton
 */
public class Main {

    static final OID unpredictableParam = new OID(".1.3.6.1.2.1.200.1.4.0");
    static final OID unpredictableTable = new OID(".1.3.6.1.2.1.200.2");

    static String confFilePath = "C:\\Users\\anton\\OneDrive\\Documentos\\NetBeansProjects\\RandomNumbersSNMP\\resource\\unpredictable-conf.txt";

    static final UnpredictableConf UNPREDICTABLE_CONF = new UnpredictableConf();
    static ManagedObjectFactory factory;

    static SNMPAgent agent;
    static Interface client;

//    static String ipAddress = "193.137.92.72/";
    static String ipAddress = "0.0.0.0/";

    public static void main(String[] args) throws IOException {

        UNPREDICTABLE_CONF.parseConfFile(confFilePath);

        String command = (String) JOptionPane.showInputDialog(null, "Insert the comand to init the agent:", "Command", JOptionPane.PLAIN_MESSAGE, null, null, "localhost");

        UNPREDICTABLE_CONF.setCommandKey(command);

        agent = new SNMPAgent(ipAddress + UNPREDICTABLE_CONF.getUdpPort(), UNPREDICTABLE_CONF);

        factory = ManagedObjectFactory.getInstance(agent);

        try {
            agent.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Thread(() -> {
            new Interface(confFilePath).setVisible(true);
        }).start();
    }
}
