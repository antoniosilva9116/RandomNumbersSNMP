/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.server;

import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.mo.DefaultMOFactory;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOFactory;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

/**
 *
 * @author anton
 */
public class ManagedObjectCreator implements MOGroup {

    private MOFactory moFactory
            = DefaultMOFactory.getInstance();
    private static final OID unpredictableParam
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200,1});
    private static final OID unpredictableTable
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 2});
    /**
     * OID of this MIB module for usage which can be used for its
     * identification.
     */
    public static final OID oidtable
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200});

    public static MOScalar createReadOnly(OID oid, Object value) {
        return new MOScalar(oid,
                MOAccessImpl.ACCESS_READ_ONLY,
                getVariable(value));
    }

    private static Variable getVariable(Object value) {
        if (value instanceof String) {
            return new OctetString((String) value);
        }
        throw new IllegalArgumentException("Unmanaged Type: " + value.getClass());
    }

    @Override
    public void registerMOs(MOServer server, OctetString context) throws DuplicateRegistrationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unregisterMOs(MOServer server, OctetString context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void createUnpredictableParamMIB(int refreshRate,int tableSize, int numberSize){
        
    }

}
