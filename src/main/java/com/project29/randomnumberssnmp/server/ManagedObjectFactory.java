/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.server;

import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.agent.mo.MOTable;

/**
 *
 * @author anton
 */
public class ManagedObjectFactory {

    private static ManagedObjectFactory managedObjectFactory;
    private SNMPAgent agent;

    private ManagedObjectFactory() {
    }

    public static synchronized ManagedObjectFactory getInstance(SNMPAgent agent) {
        if (managedObjectFactory == null) {
            managedObjectFactory = new ManagedObjectFactory();
            managedObjectFactory.setAgent(agent);
        }
        return managedObjectFactory;
    }

    private ManagedObjectFactory getManagedObjectFactory() {
        return managedObjectFactory;
    }

    private void setManagedObjectFactory(ManagedObjectFactory managedObjectFactory) {
        this.managedObjectFactory = managedObjectFactory;
    }

    private SNMPAgent getAgent() {
        return agent;
    }

    private void setAgent(SNMPAgent agent) {
        this.agent = agent;
    }

    public void createManagedObjects(MOScalar[] mOScalars) {
        for (int i = 0; i < mOScalars.length; i++) {
            agent.unregisterManagedObject(agent.getSnmpv2MIB());

            agent.registerManagedObject(mOScalars[i]);
        }
    }

    public void createManagedObjects(MOTable mOTable) {

        
        
        agent.registerManagedObject(mOTable);

    }
}
