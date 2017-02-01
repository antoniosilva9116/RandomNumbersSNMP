/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.client;

import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

/**
 *
 * @author anton
 */
public class SNMPManager {

    private Snmp snmp = null;
    private String address = null;
    private String community;

    public SNMPManager(String address, String community) {
        this.address = address;
        this.community = community;
    }

    /**
     * Start the Snmp session. If you forget the listen() method you will not
     * get any answers because the communication is asynchronous and the
     * listen() method listens for answers.
     *
     * @throws IOException
     */
    public void start() throws IOException {
        TransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        // Do not forget this line!
        transport.listen();
    }

    /**
     * Method which takes a single OID and returns the response from the agent
     * as a String.
     *
     * @param oid
     * @return
     * @throws IOException
     */
    public String getAsString(OID oid) throws IOException {
        ResponseEvent event = get(new OID[]{oid});
        return event.getResponse().get(0).getVariable().toString();
    }

    /**
     * Normally this would return domain objects or something else than this...
     */
    public List<List<String>> getTableAsStrings(OID[] oids) {
        TableUtils tUtils = new TableUtils(snmp, new DefaultPDUFactory());

        @SuppressWarnings("unchecked")
        List<TableEvent> events = tUtils.getTable(getTarget(), oids, null, null);

        List<List<String>> list = new ArrayList<List<String>>();
        for (TableEvent event : events) {
            if (event.isError()) {
                throw new RuntimeException(event.getErrorMessage());
            }
            List<String> strList = new ArrayList<String>();
            list.add(strList);
            for (VariableBinding vb : event.getColumns()) {
                strList.add(vb.getVariable().toString());
            }
        }
        return list;
    }

    /**
     * This method is capable of handling multiple OIDs
     *
     * @param oids
     * @return
     * @throws IOException
     */
    public ResponseEvent get(OID oids[]) throws IOException {
        PDU pdu = new PDU();
        for (OID oid : oids) {
            pdu.add(new VariableBinding(oid));
        }
        pdu.setType(PDU.GET);
        ResponseEvent event = snmp.send(pdu, getTarget(), null);
        if (event != null) {
            return event;
        }
        throw new RuntimeException("GET timed out");
    }

    /**
     * This method returns a Target, which contains information about where the
     * data should be fetched and how.
     *
     * @return
     */
    private Target getTarget() {
        Address targetAddress = GenericAddress.parse(address);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    public static String extractSingleString(ResponseEvent event) {
        return event.getResponse().get(0).getVariable().toString();
    }

}
