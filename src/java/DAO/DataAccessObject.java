/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import VO.customerVO;
import VO.perusahaanVO;
import java.sql.Connection;
import java.util.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Radiogroup;

/**
 *
 * @author helloWorld2
 */
public class DataAccessObject {

    private static DataAccessObject dao;
    String temp;

    public static DataAccessObject getInstance() {
        if (dao == null) {
            dao = new DataAccessObject();
        }
        return dao;
    }

    public void registerDriver() {
        Driver driver = new org.postgresql.Driver();
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Sukses koneksi ke database");
        return connection;

    }

    public void insertData(String namaDepan, String namaBelakang, String email, String alamat, String noTelpon, Date tanggalLahir, Integer id_perusahaan) {
        System.out.println("tanggalLahir =" + tanggalLahir.toString());
        System.out.println("Integer id_perusahaan =" + id_perusahaan.toString());
        String sql = "INSERT INTO customer ( namaDepan, namaBelakang, email, alamat, noTelpon,tanggalLahir, id_perusahaan)" + "VALUES ('" + namaDepan + "','" + namaBelakang + "','" + email + "','" + alamat + "','" + noTelpon + "','" + tanggalLahir + "','" + id_perusahaan + "')";
        try {
            registerDriver();
            Connect();
            statement.executeUpdate(sql);
            System.out.println("sql - " + sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }

    }

    public void updateData(int id, String namaDepan, String namaBelakang, String email, String alamat, String noTelpon, Date tanggalLahir, Integer id_perusahaan) {
        String sql = "UPDATE customer SET namaDepan = '" + namaDepan + "',namaBelakang = '" + namaBelakang + "',email = '" + email + "', alamat = '" + alamat + "', noTelpon = '" + noTelpon + "', tanggalLahir = '" + tanggalLahir + "', id_perusahaan = '" + id_perusahaan + "' WHERE id ='" + id + "'";
        try {
            registerDriver();
            Connect();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }

    }

    public String deleteData(int id) {
        String delete = "";
        String sql = "DELETE FROM customer WHERE id = '" + id + "'";
        try {
            registerDriver();
            Connect();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return delete;
    }

    public List searchDataSpecific(String comboString, String keywords) {
        List list = new ArrayList();
        if (comboString.equals("nama_perusahaan")) {
            comboString = "id_perusahaan";
        }
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
                Integer id = resultSet.getInt("id");
                Date tanggalLahir = resultSet.getDate("tanggalLahir");
                String namaDepan = resultSet.getString("namaDepan");
                String namaBelakang = resultSet.getString("namaBelakang");
                String email = resultSet.getString("email");
                String alamat = resultSet.getString("alamat");
                System.out.println("ini adalah alamat = " + alamat);
                String noTelpon = resultSet.getString("noTelpon");
                Integer id_perusahaan = resultSet.getInt("id_perusahaan");
//    System.out.println("integer id_perusahaan = "+id_perusahaan);
//    String nama_perusahaan = DataAccessObject.getInstance().selectNameCompany(id_perusahaan);
//    System.out.println("nama_perusahaan get = " + nama_perusahaan);

                customerVO vo = new customerVO();
                vo.setId(id);
                vo.setNamaDepan(namaDepan);
                vo.setNamaBelakang(namaBelakang);
                vo.setEmail(email);
                vo.setAlamat(alamat);
                vo.setNoTelpon(noTelpon);
                vo.setTanggalLahir(tanggalLahir);
                vo.setId_perusahaan(id_perusahaan);
                list.add(vo);

                System.out.println("id = " + id);
                System.out.println("namaDepan = " + namaDepan);
                System.out.println("namaBelakang = " + namaBelakang);
                System.out.println("email = " + email);
                System.out.println("noTelpon = " + noTelpon);
                System.out.println("tanggalLahir = " + tanggalLahir);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return list;
    }

    public List searchDataAll(String keywords) {
        List list = new ArrayList();
//   String sql = "SELECT * FROM customer WHERE "+radioInput+" LIKE '%"+keywords+"%'";
//   String sql = "SELECT * FROM customer WHERE LOWER (customer."+radioString+") LIKE '%"+keywords+"%' OR UPPER (customer."+radioString+")LIKE '%"+keywords+"%';";
        String sql = "SELECT * FROM customer WHERE ID ::TEXT ILIKE '%" + keywords + "%' OR namaDepan ::TEXT ILIKE '%" + keywords + "%' OR namaBelakang ::TEXT ILIKE '%" + keywords + "%' OR email ::TEXT ILIKE '%" + keywords + "%'OR alamat ::TEXT ILIKE '%" + keywords + "%'OR noTelpon ::TEXT ILIKE '%" + keywords + "%' OR tanggalLahir ::TEXT ILIKE '%" + keywords + "%' OR id_perusahaan ::TEXT ILIKE '%" + keywords + "%'";

//   sql.replace(comboString, keywords);
        System.out.println("sql - " + sql);
        try {
            registerDriver();
            Connect();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                //iterasi tiap data
                Integer id = resultSet.getInt("id");
                Date tanggalLahir = resultSet.getDate("tanggalLahir");
                String namaDepan = resultSet.getString("namaDepan");
                String namaBelakang = resultSet.getString("namaBelakang");
                String email = resultSet.getString("email");
                String alamat = resultSet.getString("alamat");
                System.out.println("ini adalah alamat = " + alamat);
                String noTelpon = resultSet.getString("noTelpon");
                Integer id_perusahaan = resultSet.getInt("id_perusahaan");
//    System.out.println("integer id_perusahaan = "+id_perusahaan);
//    String nama_perusahaan = DataAccessObject.getInstance().selectNameCompany(id_perusahaan);
//    System.out.println("nama_perusahaan get = " + nama_perusahaan);

                customerVO vo = new customerVO();
                vo.setId(id);
                vo.setNamaDepan(namaDepan);
                vo.setNamaBelakang(namaBelakang);
                vo.setEmail(email);
                vo.setAlamat(alamat);
                vo.setNoTelpon(noTelpon);
                vo.setTanggalLahir(tanggalLahir);
                vo.setId_perusahaan(id_perusahaan);
                list.add(vo);

                System.out.println("id = " + id);
                System.out.println("namaDepan = " + namaDepan);
                System.out.println("namaBelakang = " + namaBelakang);
                System.out.println("email = " + email);
                System.out.println("noTelpon = " + noTelpon);
                System.out.println("tanggalLahir = " + tanggalLahir);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return list;
    }

    public List selectData() {
        List list = new ArrayList();
        String sql = "SELECT * FROM customer";
        try {
            registerDriver();
            Connect();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                //iterasi tiap data
                Integer id = resultSet.getInt("id");
                Date tanggalLahir = resultSet.getDate("tanggalLahir");
                String namaDepan = resultSet.getString("namaDepan");
                String namaBelakang = resultSet.getString("namaBelakang");
                String email = resultSet.getString("email");
                String alamat = resultSet.getString("alamat");
                System.out.println("ini adalah alamat = " + alamat);
                String noTelpon = resultSet.getString("noTelpon");
                Integer id_perusahaan = resultSet.getInt("id_perusahaan");
//    System.out.println("integer id_perusahaan = "+id_perusahaan);
//    String nama_perusahaan = DataAccessObject.getInstance().selectNameCompany(id_perusahaan);
//    System.out.println("nama_perusahaan get = " + nama_perusahaan);

                customerVO vo = new customerVO();
                vo.setId(id);
                vo.setNamaDepan(namaDepan);
                vo.setNamaBelakang(namaBelakang);
                vo.setEmail(email);
                vo.setAlamat(alamat);
                vo.setNoTelpon(noTelpon);
                vo.setTanggalLahir(tanggalLahir);
                vo.setId_perusahaan(id_perusahaan);
                list.add(vo);

                System.out.println("id = " + id);
                System.out.println("namaDepan = " + namaDepan);
                System.out.println("namaBelakang = " + namaBelakang);
                System.out.println("email = " + email);
                System.out.println("noTelpon = " + noTelpon);
                System.out.println("tanggalLahir = " + tanggalLahir);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return list;
    }

//public perusahaanVO selectNamaPerusahaan (Integer id_perusahaan) {
////    List list = new ArrayList();
//    perusahaanVO vo = new perusahaanVO();
//    String sql = "SELECT nama_perusahaan FROM perusahaan WHERE id_perusahaan="+id_perusahaan+";";
//    System.out.println("sql - " + sql);
//    try {
//        registerDriver();
//        Connect();
//        resultSet = statement.executeQuery(sql);
//        
//     while (resultSet.next()){
//     String nama_perusahaan = resultSet.getString("nama_perusahaan");
//     
////     vo.setNama_perusahaan(nama_perusahaan);
////         list.add(vo);
//         
//         System.out.println("nama_perusahaan DAO = " + nama_perusahaan);
//     }
//    } catch (SQLException ex) {
//        Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
//    }finally{
//    disconnect();
//    }
// return vo;      
//    
//}
    public String selectNameCompany(Integer id_perusahaan) {
        String temp = "";
        String sql = "SELECT nama_perusahaan FROM perusahaan WHERE id_perusahaan=" + id_perusahaan + ";";
        System.out.println("sql - " + sql);
        try {
            registerDriver();
            Connect();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String nama_perusahaan = resultSet.getString("nama_perusahaan");
                temp = nama_perusahaan;
//     String alamat_perusahaan = resultSet.getString("alamat_perusahaan");
//     String no_telpon = resultSet.getString("no_telpon");
//     String bidang_perusahaan = resultSet.getString("bidang_perusahaan");

//         perusahaanVO vo = new perusahaanVO();
//         vo.setNama_perusahaan(nama_perusahaan);
                System.out.println("nama_perusahaan DAO = " + nama_perusahaan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return temp;

    }

    public List searchNamaPerusahaan(String keywords) {
        List list = new ArrayList();
//        String temp = "";
        String sql = "SELECT id_perusahaan FROM perusahaan WHERE nama_perusahaan ILIKE '%" + keywords + "%'";
//        System.out.println("sql - " + sql);
        System.out.println("sql search = " + sql);

        try {
            registerDriver();
            Connect();
            resultSet = statement.executeQuery(sql);
            System.out.println("resultSet search =" + resultSet);

            while (resultSet.next()) {

                Integer id_perusahaan = resultSet.getInt("id_perusahaan");
                customerVO vo = new customerVO();
                vo.setId_perusahaan(id_perusahaan);
                list.add(vo);

                System.out.println("id_perusahaan DAO = " + id_perusahaan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return list;

    }

    public List searchNamaPerusahaanNonVO(String keywords) {
        List list = new ArrayList();
//        String temp = "";
        String sql = "SELECT id_perusahaan FROM perusahaan WHERE nama_perusahaan ILIKE '%" + keywords + "%'";
//        System.out.println("sql - " + sql);
        System.out.println("sql search = " + sql);

        try {
            registerDriver();
            Connect();
            resultSet = statement.executeQuery(sql);
            System.out.println("resultSet search =" + resultSet);

            while (resultSet.next()) {

                Integer id_perusahaan = resultSet.getInt("id_perusahaan");
                customerVO vo = new customerVO();
                vo.setId_perusahaan(id_perusahaan);
                list.add(id_perusahaan);

                System.out.println("id_perusahaan DAO = " + id_perusahaan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            disconnect();
        }
        return list;

    }

//public perusahaanVO nameCompany (Integer id_perusahaan) {
//    perusahaanVO temp = new perusahaanVO();
//    String sql = "SELECT nama_perusahaan FROM perusahaan WHERE id_perusahaan="+id_perusahaan+";";
//    System.out.println("sql - " + sql);
//    try {
//        registerDriver();
//        Connect();
//        resultSet = statement.executeQuery(sql);
//        
//     while (resultSet.next()){
//     String nama_perusahaan = resultSet.getString("nama_perusahaan");
//     String alamat_perusahaan = resultSet.getString("alamat_perusahaan");
//     String no_telpon = resultSet.getString("no_telpon");
//     String bidang_perusahaan = resultSet.getString("bidang_perusahaan");
//     
//     
//         perusahaanVO vo = new perusahaanVO();
//         vo.setNama_perusahaan(nama_perusahaan);
//         vo.setAlamat_perusahaan(alamat_perusahaan);
//         vo.setNo_telpon(no_telpon);
//         vo.setBidang_perusahaan(bidang_perusahaan);
//         (vo);
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
//public void selectData (){
//    String sql = "SELECT * FROM customer"; 
//    try {
//     resultSet = statement.executeQuery(sql);
//     while (resultSet.next()){
//    //iterasi tiap data
//    String id = resultSet.getString("id");
//    String namaDepan = resultSet.getString("namaDepan");
//    String namaBelakang = resultSet.getString("namaBelakang");
//    String email = resultSet.getString("email");
//    String alamat = resultSet.getString("alamat");
//    String noTelpon = resultSet.getString("noTelpon");
//    
//         System.out.println("id = " + id);
//         System.out.println("namaDepan = " + namaDepan);
//         System.out.println("namaBelakang = " + namaBelakang);
//         System.out.println("email = " + email);
//         System.out.println("noTelpon = " + noTelpon);
//    }
//    } catch (SQLException ex) {
//        Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
//    } finally{
//    disconnect();}
//    
//}
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

    public static void main(String[] args) {
        getInstance().registerDriver();
        getInstance().Connect();
//getInstance().disconnect();
//getInstance().insertData();
//getInstance().updateData();
//getInstance().selectData();
//getInstance().selectNameCompany();
//getInstance().searchData("namaDepan", "kuc");
        getInstance().searchDataAll("ayam");

    }

    public void updateData(Integer value, String value0, String value1, String value2, String value3, String value4, Date value5, String temp) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
