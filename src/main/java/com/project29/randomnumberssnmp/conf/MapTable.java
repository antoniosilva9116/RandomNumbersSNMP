/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project29.randomnumberssnmp.conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author anton
 */
public class MapTable {

    String[][] table;
    private int columns;
    private int lines;

    public MapTable() {
    }

    public MapTable(int lines, int columns) {
        table = new String[lines][columns];
    }

    public MapTable(String[][] table) {
        this.table = table;
    }

    public String[][] getTable() {
        return table;
    }

    public void setTable(String[][] table) {
        this.table = table;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public void parseConfFile(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lines = 0;
            table = new String[this.lines][this.columns];
            while ((line = br.readLine()) != null) {
                String[] values = line.split("");

                for (int c = 0; c < values.length; c++) {
                    table[lines][c] = values[c];
                }

                lines++;
            }
        }
    }

    @Override
    public String toString() {
        return "MapTable{" + "table=" + tableToString() + '}';
    }

    public String tableToString() {
        String result = new String();
        String newline = System.getProperty("line.separator");
        
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                result += ' ' + table[i][j];
            }

            result += newline;
        }

        return result;
    }

}
