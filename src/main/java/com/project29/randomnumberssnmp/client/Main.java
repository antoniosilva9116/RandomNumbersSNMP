/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.client;

import com.project29.randomnumberssnmp.server.ManagedObjectCreator;
import com.project29.randomnumberssnmp.server.SNMPAgent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.agent.CommandProcessor;
import org.snmp4j.mp.MPv3;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;

/**
 *
 * @author anton
 */
public class Main {

    static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");

    public static void main(String[] args) throws IOException {

        SNMPAgent agent = new SNMPAgent("0.0.0.0/2001");

        try {
            agent.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        agent.unregisterManagedObject(agent.getSnmpv2MIB());

        agent.registerManagedObject(ManagedObjectCreator.createReadOnly(sysDescr,
                "This Description is set By ShivaSoft"));

        // Setup the client to use our newly started agent
        SNMPManager client = new SNMPManager("udp:127.0.0.1/2001");
        client.start();
        // Get back Value which is set
        System.out.println(client.getAsString(sysDescr));

    }
}
