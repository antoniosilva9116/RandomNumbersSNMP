/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.server;

import com.project29.randomnumberssnmp.conf.UnpredictableConf;
import org.snmp4j.agent.mo.DefaultMOFactory;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOFactory;
import org.snmp4j.agent.mo.MOScalar;
import org.snmp4j.agent.mo.MOTable;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.Variable;

/**
 *
 * @author anton
 */
public class ManagedObjectCreator {

    private MOFactory moFactory
            = DefaultMOFactory.getInstance();
    //OIDs
    private static final OID unpredictableParam
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1});
    private static final OID unpredictableTable
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 2});
    private UnpredictableConf conf;

    // Scalars
    private static final OID colR
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1, 1, 0});
    private static final OID colN
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1, 2, 0});
    private static final OID colD
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1, 3, 0});
    private static final OID colReset
            = new OID(new int[]{1, 3, 6, 1, 2, 1, 200, 1, 4, 0});

    public ManagedObjectCreator() {
    }

    public ManagedObjectCreator(UnpredictableConf conf) {
        this.conf = conf;
    }

    private static Variable getVariable(Object value) {
        if (value instanceof String) {
            return new OctetString((String) value);
        }
        throw new IllegalArgumentException("Unmanaged Type: " + value.getClass());
    }

    public static MOScalar[] createUnpredictableParamMIB(int r, int n, int d, String reset) {

        MOScalar[] objScalars = new MOScalar[4];

        objScalars[0] = ManagedObjectScalarFactory.create(
                colR, r, MOAccessImpl.ACCESS_READ_ONLY
        );
        objScalars[1] = ManagedObjectScalarFactory.create(
                colN, n, MOAccessImpl.ACCESS_READ_ONLY
        );
        objScalars[2] = ManagedObjectScalarFactory.create(
                colD, d, MOAccessImpl.ACCESS_READ_ONLY
        );
        objScalars[3] = ManagedObjectScalarFactory.create(
                colReset, reset, MOAccessImpl.ACCESS_READ_WRITE
        );

        return objScalars;
    }

    public static MOTable createUnpredictableTableMIB(String[][] matrix) {

        ManagedObjectTableBuilder builder = new ManagedObjectTableBuilder(unpredictableTable)
                .addColumnType(SMIConstants.SYNTAX_INTEGER, MOAccessImpl.ACCESS_READ_CREATE)
                .addColumnType(SMIConstants.SYNTAX_OCTET_STRING, MOAccessImpl.ACCESS_READ_CREATE);
        // Normally you would begin loop over you two domain objects here

        for (int i = 0; i < matrix.length; i++) {
            String digit = new String();
            for (int j = 0; j < matrix[0].length; j++) {
                digit += matrix[i][j];
            }

            builder
                    .addRowValue(new Integer32(i))
                    .addRowValue(new OctetString(digit));
        }

        return builder.build();
    }

}
