/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import VO.customerVO;
import VO.perusahaanVO;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author helloWorld2
 */
public class DataAccessObjectPerusahaan {

    private static DataAccessObjectPerusahaan dao;

    public static DataAccessObjectPerusahaan getInstance() {
        if (dao == null) {
            dao = new DataAccessObjectPerusahaan();
        }
        return dao;
    }

    public void registerDriver() {
        Driver driver = new org.postgresql.Driver();
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectPerusahaan.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Connection Connect() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/belajar_postgresql";
        String user = "postgres";
        String password = "postgres";
        try {
            connection = DriverManager.getConnection(jdbcUrl, user, password);
            statement = connection.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectPerusahaan.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Sukses koneksi ke database");
        return connection;

    }

    public boolean disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            System.out.println("Sukses diskonek");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List selectData() {
        List list = new ArrayList();
        String sql = "SELECT * FROM perusahaan";
        try {
            registerDriver();
            Connect();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                //iterasi tiap data
                Integer id_perusahaan = resultSet.getInt("id_perusahaan");
                String nama_perusahaan = resultSet.getString("nama_perusahaan");
                String alamat_perusahaan = resultSet.getString("alamat_perusahaan");
                String no_telpon = resultSet.getString("no_telpon");
                String bidang_perusahaan = resultSet.getString("bidang_perusahaan");

                perusahaanVO vo = new perusahaanVO();
                vo.setId_perusahaan(id_perusahaan);
                vo.setNama_perusahaan(nama_perusahaan);
                vo.setAlamat_perusahaan(alamat_perusahaan);
                vo.setNo_telpon(no_telpon);
                vo.setBidang_perusahaan(bidang_perusahaan);
                list.add(vo);

                System.out.println("id_perusahaan = " + id_perusahaan);
                System.out.println("nama_perusahaan = " + nama_perusahaan);
                System.out.println("alamat_perusahaan = " + alamat_perusahaan);
                System.out.println("no_telpon = " + no_telpon);
                System.out.println("bidang_perusahaan = " + bidang_perusahaan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectPerusahaan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return list;
    }

    public String deleteData(int id_perusahaan) {
        String delete = "";
        String sql = "DELETE FROM perusahaan WHERE id_perusahaan = '" + id_perusahaan + "'";
        try {
            registerDriver();
            Connect();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectPerusahaan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return delete;
    }

    public void insertData(String nama_perusahaan, String alamat_perusahaan, String no_telpon, String bidang_perusahaan) {
        String sql = "INSERT INTO perusahaan ( nama_perusahaan, alamat_perusahaan, no_telpon, bidang_perusahaan)" + "VALUES ('" + nama_perusahaan + "','" + alamat_perusahaan + "','" + no_telpon + "','" + bidang_perusahaan + "')";
        try {
            registerDriver();
            Connect();
            statement.executeUpdate(sql);
            System.out.println("sql - " + sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectPerusahaan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }

    }

    public List searchDataSpecific(String comboString, String keywords) {
        List list = new ArrayList();
//   String sql = "SELECT * FROM customer WHERE "+radioInput+" LIKE '%"+keywords+"%'";
//   String sql = "SELECT * FROM customer WHERE LOWER (customer."+radioString+") LIKE '%"+keywords+"%' OR UPPER (customer."+radioString+")LIKE '%"+keywords+"%';";
        String sql = "SELECT * FROM customer WHERE " + comboString + " ::TEXT ILIKE '%" + keywords + "%'";
        sql = sql.replace(comboString, comboString);
//   sql.replace(comboString, keywords);
        System.out.println("sql - " + sql);
        try {
            registerDriver();
            Connect();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                //iterasi tiap data
                Integer id_perusahaan = resultSet.getInt("id_perusahaan");
                String nama_perusahaan = resultSet.getString("nama_perusahaan");
                String alamat_perusahaan = resultSet.getString("alamat_perusahaan");
                String no_telpon = resultSet.getString("no_telpon");
                String bidang_perusahaan = resultSet.getString("bidang_perusahaan");

                perusahaanVO vo = new perusahaanVO();
                vo.setId_perusahaan(id_perusahaan);
                vo.setNama_perusahaan(nama_perusahaan);
                vo.setAlamat_perusahaan(alamat_perusahaan);
                vo.setNo_telpon(no_telpon);
                vo.setBidang_perusahaan(bidang_perusahaan);
                list.add(vo);

                System.out.println("id_perusahaan = " + id_perusahaan);
                System.out.println("nama_perusahaan = " + nama_perusahaan);
                System.out.println("alamat_perusahaan = " + alamat_perusahaan);
                System.out.println("no_telpon = " + no_telpon);
                System.out.println("bidang_perusahaan = " + bidang_perusahaan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectPerusahaan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return list;
    }

    public List searchDataAll(String keywords) {
        List list = new ArrayList();
//   String sql = "SELECT * FROM customer WHERE "+radioInput+" LIKE '%"+keywords+"%'";
//   String sql = "SELECT * FROM customer WHERE LOWER (customer."+radioString+") LIKE '%"+keywords+"%' OR UPPER (customer."+radioString+")LIKE '%"+keywords+"%';";
        String sql = "SELECT * FROM perusahaan WHERE id_perusahaan ::TEXT ILIKE '%" + keywords + "%' OR nama_perusahaan ::TEXT ILIKE '%" + keywords + "%' OR alamat_perusahaan ::TEXT ILIKE '%" + keywords + "%' OR no_telpon ::TEXT ILIKE '%" + keywords + "%'OR bidang_perusahaan ::TEXT ILIKE '%" + keywords + "%'";

        System.out.println("sql - " + sql);
        try {
            registerDriver();
            Connect();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                //iterasi tiap data
                Integer id_perusahaan = resultSet.getInt("id_perusahaan");
                String nama_perusahaan = resultSet.getString("nama_perusahaan");
                String alamat_perusahaan = resultSet.getString("alamat_perusahaan");
                String no_telpon = resultSet.getString("no_telpon");
                String bidang_perusahaan = resultSet.getString("bidang_perusahaan");

                perusahaanVO vo = new perusahaanVO();
                vo.setId_perusahaan(id_perusahaan);
                vo.setNama_perusahaan(nama_perusahaan);
                vo.setAlamat_perusahaan(alamat_perusahaan);
                vo.setNo_telpon(no_telpon);
                vo.setBidang_perusahaan(bidang_perusahaan);
                list.add(vo);

                System.out.println("id_perusahaan = " + id_perusahaan);
                System.out.println("nama_perusahaan = " + nama_perusahaan);
                System.out.println("alamat_perusahaan = " + alamat_perusahaan);
                System.out.println("no_telpon = " + no_telpon);
                System.out.println("bidang_perusahaan = " + bidang_perusahaan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectPerusahaan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return list;
    }

//public List selectNamaPerusahaan (Integer id_perusahaan) {
//    List list = new ArrayList();
//    String sql = "SELECT nama_perusahaan FROM perusahaan WHERE id_perusahaan = '"+id_perusahaan+"'";
//    System.out.println("sql - " + sql);
//    try {
//        registerDriver();
//        Connect();
//        resultSet = statement.executeQuery(sql);
//        
//     while (resultSet.next()){
//     String nama_perusahaan = resultSet.getString("nama_perusahaan");
//     
//         perusahaanVO vo = new perusahaanVO();
//         vo.setNama_perusahaan(nama_perusahaan);
//         list.add(vo);
//         
//         System.out.println("nama_perusahaan DAO = " + nama_perusahaan);
//     }
//    } catch (SQLException ex) {
//        Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
//    }finally{
//    disconnect();
//    }
// return list;      
//    
//}
    public void updateData(Integer id_perusahaan, String nama_perusahaan, String alamat_perusahaan, String no_telpon, String bidang_perusahaan) {
        String sql = "UPDATE perusahaan SET nama_perusahaan = '" + nama_perusahaan + "',alamat_perusahaan = '" + alamat_perusahaan + "',no_telpon = '" + no_telpon + "', bidang_perusahaan = '" + bidang_perusahaan + "' WHERE id_perusahaan ='" + id_perusahaan + "'";
        try {
            registerDriver();
            Connect();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObjectPerusahaan.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
    }
}
