/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.server;

import com.project29.randomnumberssnmp.conf.MapTable;
import com.project29.randomnumberssnmp.conf.UnpredictableConf;

import java.io.File;
import java.io.IOException;

import org.snmp4j.TransportMapping;
import org.snmp4j.agent.BaseAgent;
import org.snmp4j.agent.CommandProcessor;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.ManagedObject;
import org.snmp4j.agent.mo.MOTableRow;
import org.snmp4j.agent.mo.snmp.RowStatus;
import org.snmp4j.agent.mo.snmp.SnmpCommunityMIB;
import org.snmp4j.agent.mo.snmp.SnmpNotificationMIB;
import org.snmp4j.agent.mo.snmp.SnmpTargetMIB;
import org.snmp4j.agent.mo.snmp.StorageType;
import org.snmp4j.agent.mo.snmp.VacmMIB;
import org.snmp4j.agent.security.MutableVACM;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.transport.TransportMappings;

/**
 *
 * @author anton
 */
public class SNMPAgent extends BaseAgent {

    private UnpredictableConf unpredictableConf;
    private MapTable mapTable;
    private String address;

    public SNMPAgent(File bootCounterFile, File configFile, CommandProcessor commandProcessor) {
        super(bootCounterFile, configFile, commandProcessor);
        unpredictableConf = new UnpredictableConf();
        mapTable = new MapTable();
    }

    public UnpredictableConf getUnpredictableConf() {
        return unpredictableConf;
    }

    public void setUnpredictableConf(UnpredictableConf unpredictableConf) {
        this.unpredictableConf = unpredictableConf;
    }

    public MapTable getMapTable() {
        return mapTable;
    }

    public void setMapTable(MapTable mapTable) {
        this.mapTable = mapTable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    
    public SNMPAgent(String address) throws IOException {

        /**
         * Creates a base agent with boot-counter, config file, and a
         * CommandProcessor for processing SNMP requests. Parameters:
         * "bootCounterFile" - a file with serialized boot-counter information
         * (read/write). If the file does not exist it is created on shutdown of
         * the agent. "configFile" - a file with serialized configuration
         * information (read/write). If the file does not exist it is created on
         * shutdown of the agent. "commandProcessor" - the CommandProcessor
         * instance that handles the SNMP requests.
         */
        super(new File("conf.agent"), new File("bootCounter.agent"),
                new CommandProcessor(
                        new OctetString(MPv3.createLocalEngineID())));

        this.address = address;
        unpredictableConf = new UnpredictableConf();
        mapTable = new MapTable();
    }

    @Override
    protected void registerManagedObjects() {
        // TODO Auto-generated method 
    }

    /**
     * Unregister the basic MIB modules from the agent's MOServer.
     */
    @Override
    protected void unregisterManagedObjects() {
        // TODO Auto-generated method 
    }

    @Override
    protected void addUsmUser(USM usm) {
        // TODO Auto-generated method        
    }

    @Override
    protected void addNotificationTargets(SnmpTargetMIB stmib, SnmpNotificationMIB snmib) {
        // TODO Auto-generated method 
    }

    /**
     * Adds initial VACM configuration.
     */
    @Override
    protected void addViews(VacmMIB vacm) {
        vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv2c, new OctetString(
                "c" + unpredictableConf.getComunityString()), new OctetString("v1v2group"),
                StorageType.nonVolatile);

        vacm.addAccess(new OctetString("v1v2group"), new OctetString("public"),
                SecurityModel.SECURITY_MODEL_ANY, SecurityLevel.NOAUTH_NOPRIV,
                MutableVACM.VACM_MATCH_EXACT, new OctetString("fullReadView"),
                new OctetString("fullWriteView"), new OctetString(
                        "fullNotifyView"), StorageType.nonVolatile);

        vacm.addViewTreeFamily(new OctetString("fullReadView"), new OID("1.3"),
                new OctetString(), VacmMIB.vacmViewIncluded,
                StorageType.nonVolatile);

    }

    /**
     * Adds community to security name mappings needed for SNMPv1 and SNMPv2c.
     */
    @Override
    protected void addCommunities(SnmpCommunityMIB communityMIB) {
        Variable[] com2sec = new Variable[]{new OctetString(unpredictableConf.getComunityString()),
            new OctetString("c" + unpredictableConf.getComunityString()), // security name
            getAgent().getContextEngineID(), // local engine ID
            new OctetString(unpredictableConf.getComunityString()), // default context name
            new OctetString(), // transport tag
            new Integer32(StorageType.nonVolatile), // storage type
            new Integer32(RowStatus.active) // row status
        };

        MOTableRow row1 = communityMIB.getSnmpCommunityEntry().createRow(
                new OctetString(unpredictableConf.getComunityString() + "2" + unpredictableConf.getComunityString()).toSubIndex(true), com2sec);

        communityMIB.getSnmpCommunityEntry().addRow((SnmpCommunityMIB.SnmpCommunityEntryRow) row1);

//        Variable[] com2sec = new Variable[]{new OctetString("public"),
//            new OctetString("cpublic"), // security name
//            getAgent().getContextEngineID(), // local engine ID
//            new OctetString("public"), // default context name
//            new OctetString(), // transport tag
//            new Integer32(StorageType.nonVolatile), // storage type
//            new Integer32(RowStatus.active) // row status
//    };
//
//        MOTableRow row1 = communityMIB.getSnmpCommunityEntry().createRow(
//                new OctetString("public2public").toSubIndex(true), com2sec);
//
//        communityMIB.getSnmpCommunityEntry().addRow((SnmpCommunityMIB.SnmpCommunityEntryRow) row1);
    }

    protected void initTransportMappings() throws IOException {
        transportMappings = new TransportMapping[1];
        Address addr = GenericAddress.parse(address);
        TransportMapping tm = TransportMappings.getInstance()
                .createTransportMapping(addr);
        transportMappings[0] = tm;
    }

    public void start() throws IOException {

        unpredictableConf.parseConfFile("C:\\Users\\anton\\OneDrive\\Documentos\\NetBeansProjects\\RandomNumbersSNMP\\resource\\unpredictable-conf.txt");

        mapTable.setLines(unpredictableConf.getTableSize());
        mapTable.setColumns(unpredictableConf.getNumberSize());

        mapTable.parseConfFile("C:\\Users\\anton\\OneDrive\\Documentos\\NetBeansProjects\\RandomNumbersSNMP\\resource\\first-map-table.txt");

        System.out.println(unpredictableConf);
        System.out.println(mapTable);

        // This method reads some old config from a file and causes
        // unexpected behavior.
        // loadConfig(ImportModes.REPLACE_CREATE);
        init();

        addShutdownHook();
        getServer().addContext(new OctetString(unpredictableConf.getComunityString()));
        finishInit();
        run();
        sendColdStartNotification();
    }

    /**
     * Clients can register the MO they need
     *
     * @param mo
     */
    public void registerManagedObject(ManagedObject mo) {
        try {
            server.register(mo, null);
        } catch (DuplicateRegistrationException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void unregisterManagedObject(MOGroup moGroup) {
        moGroup.unregisterMOs(server, getContext(moGroup));
    }
}
