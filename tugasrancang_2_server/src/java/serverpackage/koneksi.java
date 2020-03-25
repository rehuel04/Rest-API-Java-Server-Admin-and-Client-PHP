package serverpackage;

import java.sql.DriverManager;

public class koneksi {
    private java.sql.Connection con;

    String koneksi  = "jdbc:mysql://127.0.0.1/db_kos_sister";
    String user     = "root";
    String pass     = "";
    
    public java.sql.Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(koneksi, user, pass);
            return con;
        } catch (Exception err) {
            System.out.println("Terjadi Kesalahan! "+err);
            return null;
        }
    }
    
    public static void main(String[] args) {
        koneksi konek = new koneksi();
        konek.getCon();
        
        if(konek.getCon() == null){
            System.out.println("Gagal Terhubung!");
        }
    }
}
