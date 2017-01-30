/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.server;

import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOAccess;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.MOServer;
import org.snmp4j.agent.mo.DefaultMOFactory;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOColumn;
import org.snmp4j.agent.mo.MOFactory;
import org.snmp4j.agent.mo.MOMutableColumn;
import org.snmp4j.agent.mo.MOMutableTableModel;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.agent.mo.MOTableIndex;
import org.snmp4j.agent.mo.MOTableIndexValidator;
import org.snmp4j.agent.mo.MOTableSubIndex;
import org.snmp4j.agent.mo.snmp.smi.Constraint;
import org.snmp4j.agent.mo.snmp.smi.ConstraintsImpl;
import org.snmp4j.agent.mo.snmp.smi.ValueConstraint;
import org.snmp4j.agent.mo.snmp.smi.ValueConstraintValidator;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.Variable;

/**
 *
 * @author anton
 */
public class ManagedObjectCreator implements MOGroup {

    private MOFactory moFactory
            = DefaultMOFactory.getInstance();
    //OIDs
    private static final OID unpredictableParam
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1});
    private static final OID unpredictableTable
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 2});
    //Scalars
    private MOScalar<OctetString> reset;

    private MOTableSubIndex[] hostsEntryIndexes;
    private MOTableIndex hostsEntryIndex;

    // Column sub-identifier definitions for hostsEntry:
    private static final OID colR
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1, 1});
    private static final OID colN
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1, 2});
    private static final OID colD
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1, 3});
    private static final OID colReset
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1, 4});

    public ManagedObjectCreator() {
    }

    public static MOScalar create(OID oid, Object value) {
        return new MOScalar(oid,
                MOAccessImpl.ACCESS_READ_WRITE,
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
        server.register(this.reset, context);
    }

    @Override
    public void unregisterMOs(MOServer server, OctetString context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static MOScalar[] createUnpredictableParamMIB(int r, int n, int d, String reset) {

        MOScalar[] objScalars = new MOScalar[4];
        
        objScalars[0] = ManagedObjectScalarFactory.create(colR, r, MOAccessImpl.ACCESS_READ_ONLY);
        objScalars[1] = ManagedObjectScalarFactory.create(colN, n, MOAccessImpl.ACCESS_READ_ONLY);
        objScalars[2] = ManagedObjectScalarFactory.create(colD, d, MOAccessImpl.ACCESS_READ_ONLY);
        objScalars[3] = ManagedObjectScalarFactory.create(colReset, reset, MOAccessImpl.ACCESS_WRITE_ONLY);

        return objScalars;
    }

}
