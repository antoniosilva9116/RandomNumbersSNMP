package com.project29.randomnumberssnmp.server;

import org.snmp4j.agent.MOAccess;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;

public class ManagedObjectScalarFactory {

    public static MOScalar create(OID oid, Object value, MOAccess accessType) {
        return new MOScalar(oid,
                accessType,
                getVariable(value));
    }

    public static MOScalar createReadOnly(OID oid, Object value) {
        return new MOScalar(oid,
                MOAccessImpl.ACCESS_READ_ONLY,
                getVariable(value));
    }

    public static MOScalar createReadWrite(OID oid, Object value) {
        return new MOScalar(oid,
                MOAccessImpl.ACCESS_READ_WRITE,
                getVariable(value));
    }

    private static Variable getVariable(Object value) {
        if (value == null) {
            return new OctetString();
        }
        if (value instanceof String) {
            return new OctetString((String) value);
        }
        if (value instanceof Integer) {
            return new Gauge32(((Integer) value).longValue());
        }
        if (value instanceof Long) {
            return new Counter64(((Long) value));
        }

        throw new IllegalArgumentException("Unmanaged Type: " + value.getClass());
    }
}
