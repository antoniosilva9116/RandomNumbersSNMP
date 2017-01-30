/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.client;

import com.project29.randomnumberssnmp.conf.UnpredictableConf;
import com.project29.randomnumberssnmp.server.ManagedObjectCreator;
import com.project29.randomnumberssnmp.server.ManagedObjectFactory;
import com.project29.randomnumberssnmp.server.SNMPAgent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.snmp4j.agent.CommandProcessor;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.mp.MPv3;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;

/**
 *
 * @author anton
 */
public class Main {

    static final OID sysDescr = new OID(".1.3.6.1.2.1.1.3.0");
    static final UnpredictableConf UNPREDICTABLE_CONF = new UnpredictableConf();
    static ManagedObjectFactory factory;

    public static void main(String[] args) throws IOException {

        UNPREDICTABLE_CONF.parseConfFile("C:\\Users\\anton\\OneDrive\\Documentos\\NetBeansProjects\\RandomNumbersSNMP\\resource\\unpredictable-conf.txt");

        SNMPAgent agent = new SNMPAgent("0.0.0.0/" + UNPREDICTABLE_CONF.getUdpPort(), UNPREDICTABLE_CONF);

        factory = ManagedObjectFactory.getInstance(agent);
        
        try {
            agent.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        factory.createManagedObjects(
                ManagedObjectCreator.createUnpredictableParamMIB(
                        UNPREDICTABLE_CONF.getRefreshRate(),
                        UNPREDICTABLE_CONF.getTableSize(),
                        UNPREDICTABLE_CONF.getNumberSize(),
                        " "
                )
        );

        // Setup the client to use our newly started agent
        SNMPManager client = new SNMPManager("udp:127.0.0.1/" + UNPREDICTABLE_CONF.getUdpPort(), agent.getUnpredictableConf().getComunityString());

        client.start();
        // Get back Value which is set
        System.out.println(client.getAsString(sysDescr));

    }
}
