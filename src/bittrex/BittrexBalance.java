/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bittrex;

import org.json.JSONObject;
import global.FileSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mysql.Mysqlconnector;
import org.json.JSONArray;
import org.json.JSONException;

public class BittrexBalance {

    //maak objecten aan
    FileSystem filesystem = new FileSystem();
    BittrexProtocall bittrexprotocall = new BittrexProtocall();
    Mysqlconnector mysqlconnector = new Mysqlconnector();

    //private
    private final String USERNAME = mysqlconnector.getUsername();
    private final String PASSWORD = mysqlconnector.getPassword();
    private final String CONN_STRING = mysqlconnector.getUrlmysql();

    /**
     * UpdateBalance object
     */
    public void UpdateBalance() {
        System.out.println(bittrexprotocall.getBalances());
        Connection conn;
        try {
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            Statement stmt = (Statement) conn.createStatement();

            JSONObject obj = new JSONObject(bittrexprotocall.getBalances());

            JSONArray array = obj.getJSONArray("result");
            for (int i = 0; i < array.length(); i++) {
                
                //list
                String currency = array.getJSONObject(i).getString("Currency");
                double balance = array.getJSONObject(i).getDouble("Balance");
                double available = array.getJSONObject(i).getDouble("Available");
                double pending = array.getJSONObject(i).getDouble("Pending");
                
                //query
                String query = "SELECT COUNT(*) AS total FROM balance WHERE Handelsplaats = 'bittrex' AND Coin='" + currency + "'";
                ResultSet rs1 = stmt.executeQuery(query);
                int count = 0;
                while (rs1.next()) {
                    count = rs1.getInt("total");
                }

                //count loop
                if (count == 0) {
                    String sql = "INSERT INTO balance (Handelsplaats, Coin, balance, pending, available)"
                            + "VALUES('bittrex', '" + currency + "', '" + balance + "', '" + available + "', '" + pending + "')";

                    //voer sql uit
                    stmt.execute(sql);
                } else {
                    String sql = "UPDATE balance SET Balance='"+balance+"', Pending='"+pending+"', Available='"+available+"'"
                            + "WHERE Handelsplaats='bittrex' AND Coin='" + currency + "';";
                    stmt.execute(sql);
                }
            }

        } catch (SQLException | JSONException ex) {
            System.out.println("Probleem bij bittrex balance.");
            ex.printStackTrace();
        }
    }
;
}
