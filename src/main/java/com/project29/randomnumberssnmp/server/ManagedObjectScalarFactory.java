package com.project29.randomnumberssnmp.server;

import org.snmp4j.agent.MOAccess;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

public class ManagedObjectScalarFactory {

    public static MOScalar create(OID oid, Object value, MOAccess accessType) {
        return new MOScalar(oid,
                accessType,
                getVariable(value));
    }

    private static Variable getVariable(Object value) {
        if (value instanceof String) {
            return new OctetString((String) value);
        }
        
        if(value instanceof Integer){
            return new Integer32((int)value);
        }
        throw new IllegalArgumentException("Unmanaged Type: " + value.getClass());
    }
}
