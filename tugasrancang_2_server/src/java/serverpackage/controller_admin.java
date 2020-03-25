package serverpackage;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("AdminController")
public class controller_admin {

    public java.sql.Connection con;
    PreparedStatement ps;

    public controller_admin() throws RemoteException {
        try {
            this.con = new koneksi().getCon();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(gs_login data) throws ClassNotFoundException {
        String result = "0";
        try {
            Statement st = con.createStatement();
            String a = "SELECT * FROM akun WHERE username = '" + data.getUser() + "' AND password = '" + data.getPass() + "'";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {            
                result = "1";
            }
        } catch (Exception e) {
            result = "Error! \n" + e.toString();
        }
        
        return Response.status(201).entity(result).build();
    }

    @POST
    @Path("/tambahPenghuni")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tambah_phn(gs_penghuni data) throws ClassNotFoundException {
        String result;
        try {
            ps = con.prepareStatement("INSERT INTO penghuni(nik, nama, kontak, alamat, kode_kamar) values (?, ?, ?, ?, ?)");
            ps.setString(1, data.getNik());
            ps.setString(2, data.getNama());
            ps.setString(3, data.getKontak());
            ps.setString(4, data.getAlamat());
            ps.setString(5, Integer.toString(data.getKd_kamar()));
            ps.executeUpdate();
            result = "Data ditambahkan!";
        } catch (Exception e) {
            result = "Terjadi kesalahan!\n" + e;
        }

        return Response.status(201).entity(result).build();
    }

    @POST
    @Path("/cariPenghuni")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_penghuni> getphn(gs_penghuni data) throws ClassNotFoundException {
        List<gs_penghuni> list = new ArrayList<gs_penghuni>();
        try {
            Statement st = con.createStatement();
            String a = "SELECT * FROM penghuni WHERE kode_kamar = " + data.getKd_kamar();
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nik = rs.getString("nik");
                String nama = rs.getString("nama");
                String kontak = rs.getString("kontak");

                String alamat = rs.getString("alamat");
                int kode_kamar = rs.getInt("kode_kamar");

                gs_penghuni phn = new gs_penghuni();
                phn.setId_phn(id);
                phn.setNik(nik);
                phn.setNama(nama);
                phn.setKontak(kontak);
                phn.setAlamat(alamat);
                phn.setKd_kamar(kode_kamar);

                list.add(phn);
            }
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @POST
    @Path("/tambahTagihan")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tambah_tgn(gs_tagihan data) throws ClassNotFoundException {
        String result;
        try {
            ps = con.prepareStatement("UPDATE transaksi_det SET tgl_bayar = '" + data.getTgl_bayar() + "', tagihan = '" + data.getTagihan() + "', status = '" + data.getStatus() + "' WHERE id_trans = (SELECT id_trans FROM transaksi WHERE bulan = '" + data.getBulan() + "' && tahun = '" + data.getTahun() + "') AND id_phn = '" + data.getId_phn() + "'");
            ps.executeUpdate();
            result = "Transaksi diterima!";
        } catch (Exception e) {
            result = "Terjadi kesalahan!\n" + e;
        }

        return Response.status(201).entity(result).build();
    }

    @POST
    @Path("/tampilTransaksi2")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_bayar> getbyr(gs_bulan2 data) throws ClassNotFoundException {
        List<gs_bayar> list = new ArrayList<gs_bayar>();
        try {
            Statement st = con.createStatement();

            if (data.getStatus() == 0) {
                String a = "SELECT b.kode_kamar, b.nama, a.tgl_bayar, a.tagihan FROM transaksi_det a, penghuni b WHERE a.status = '0' AND a.id_phn = b.id AND a.id_trans = (SELECT id_trans FROM transaksi WHERE bulan = '" + data.getBulan() + "' && tahun = '" + data.getTahun() + "')";
                ResultSet rs = st.executeQuery(a);
                while (rs.next()) {
                    int kd = rs.getInt("kode_kamar");
                    String nm = rs.getString("nama");
                    String tglbyr = rs.getString("tgl_bayar");
                    int tagihan = rs.getInt("tagihan");

                    gs_bayar byr = new gs_bayar();
                    byr.setKamar(kd);
                    byr.setNama(nm);
                    byr.setTgl_bayar(tglbyr);
                    byr.setTagihan(tagihan);

                    list.add(byr);
                }

                rs.close();
            } else if (data.getStatus() == 1) {
                String a = "SELECT b.kode_kamar, b.nama, a.tgl_bayar, a.tagihan FROM transaksi_det a, penghuni b WHERE a.status = '1' AND a.id_phn = b.id AND a.id_trans = (SELECT id_trans FROM transaksi WHERE bulan = '" + data.getBulan() + "' && tahun = '" + data.getTahun() + "')";
                ResultSet rs = st.executeQuery(a);
                while (rs.next()) {

                    int kd = rs.getInt("kode_kamar");
                    String nm = rs.getString("nama");
                    String tglbyr = rs.getString("tgl_bayar");
                    int tagihan = rs.getInt("tagihan");

                    gs_bayar byr = new gs_bayar();
                    byr.setKamar(kd);
                    byr.setNama(nm);
                    byr.setTgl_bayar(tglbyr);
                    byr.setTagihan(tagihan);

                    list.add(byr);
                }

                rs.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @POST
    @Path("/tampilKamar")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_kamar> getkmr3(gs_kamar_2 data) throws ClassNotFoundException {
        List<gs_kamar> list = new ArrayList<gs_kamar>();

        try {
            Statement st = con.createStatement();
            if (data.getStatus() == 0) {
                String a = "SELECT * FROM kamar WHERE lantai = '" + data.getLantai() + "' AND kode_kamar NOT IN(SELECT kode_kamar FROM penghuni)";
                ResultSet rs = st.executeQuery(a);
                while (rs.next()) {
                    int kode = rs.getInt("kode_kamar");
                    int lantai = rs.getInt("lantai");

                    gs_kamar kr = new gs_kamar();
                    kr.setKode_kamar(kode);
                    kr.setLantai(lantai);

                    list.add(kr);
                }
                rs.close();
            } else if (data.getStatus() == 1) {
                String a = "SELECT * FROM kamar WHERE lantai = '" + data.getLantai() + "' AND kode_kamar IN(SELECT kode_kamar FROM penghuni)";
                ResultSet rs = st.executeQuery(a);
                while (rs.next()) {
                    int kode = rs.getInt("kode_kamar");
                    int lantai = rs.getInt("lantai");

                    gs_kamar kr = new gs_kamar();
                    kr.setKode_kamar(kode);
                    kr.setLantai(lantai);

                    list.add(kr);
                }
                rs.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    @GET
    @Path("/tampilPenghuni")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_penghuni> getphn2() throws ClassNotFoundException {
        List<gs_penghuni> list = new ArrayList<gs_penghuni>();

        try {
            Statement st = con.createStatement();
            String a = "SELECT * FROM penghuni";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {

                int id = rs.getInt("id");
                String nik = rs.getString("nik");
                String nama = rs.getString("nama");
                String kontak = rs.getString("kontak");

                String alamat = rs.getString("alamat");
                int kode_kamar = rs.getInt("kode_kamar");

                gs_penghuni phn = new gs_penghuni();
                phn.setId_phn(id);
                phn.setNik(nik);
                phn.setNama(nama);
                phn.setKontak(kontak);
                phn.setAlamat(alamat);
                phn.setKd_kamar(kode_kamar);

                list.add(phn);
            }
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    @GET
    @Path("/tampilKamarKosong")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_kamar> getkmr1() throws ClassNotFoundException {
        List<gs_kamar> list = new ArrayList<gs_kamar>();

        try {
            Statement st = con.createStatement();
            String a = "SELECT * FROM kamar WHERE kode_kamar NOT IN(SELECT kode_kamar FROM penghuni)";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                int kode = rs.getInt("kode_kamar");
                int lantai = rs.getInt("lantai");

                gs_kamar kr = new gs_kamar();
                kr.setKode_kamar(kode);
                kr.setLantai(lantai);

                list.add(kr);
            }
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    @POST
    @Path("/pindahKamar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pindah_phn(gs_penghuni data) throws ClassNotFoundException {
        String result;
        try {
            ps = con.prepareStatement("UPDATE penghuni SET kode_kamar = "+ data.getKd_kamar() +" WHERE id = " + data.getId_phn());
            ps.executeUpdate();
            
            result = "Perubahan berhasil";
        } catch (Exception e) {
            result = "Terjadi kesalahan pada server!\n" + e;
        }
        
        return Response.status(201).entity(result).build();
    }

    @POST
    @Path("/akhiri")
    @Produces(MediaType.APPLICATION_JSON)
    public Response akhiri(gs_akhiri data) throws ClassNotFoundException {
        String result;
        try {
            ps = con.prepareStatement("DELETE FROM penghuni WHERE id = '" + data.getKode() + "'");
            ps.executeUpdate();
            result = "Data dihapus!";
        } catch (Exception e) {
            result = "Terjadi kesalahan!\n" + e;
        }

        return Response.status(201).entity(result).build();
    }

    @POST
    @Path("/cariBulan1")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_bulan> getbln1(gs_penghuni data) throws ClassNotFoundException {
        List<gs_bulan> list = new ArrayList<gs_bulan>();

        try {
            Statement st = con.createStatement();
            String a = "SELECT a.id_trans, a.bulan, a.tahun FROM transaksi a, transaksi_det b WHERE a.id_trans = b.id_trans AND b.status = 0 AND b.id_phn = '" + data.getId_phn() + "'";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                int id = rs.getInt("id_trans");
                String bulan = rs.getString("bulan") + " " + rs.getString("tahun").substring(0, 4);

                gs_bulan bl = new gs_bulan();
                bl.setId_bln(id);
                bl.setNama_bln(bulan);

                list.add(bl);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    @GET
    @Path("/tampilBulan2")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_bulan> getbln2() throws ClassNotFoundException {
        List<gs_bulan> list = new ArrayList<gs_bulan>();

        try {
            Statement st = con.createStatement();
            String a = "SELECT * FROM transaksi";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                int id = rs.getInt("id_trans");
                String bulan = rs.getString("bulan") + " " + rs.getString("tahun").substring(0, 4);

                gs_bulan bl = new gs_bulan();
                bl.setId_bln(id);
                bl.setNama_bln(bulan);

                list.add(bl);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    @GET
    @Path("/tampilLantai")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_lantai> getkmr2() throws ClassNotFoundException {
        List<gs_lantai> list = new ArrayList<gs_lantai>();

        try {
            Statement st = con.createStatement();
            String a = "SELECT DISTINCT lantai FROM kamar";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                int lantai = rs.getInt("lantai");

                gs_lantai kr = new gs_lantai();
                kr.setLantai(lantai);

                list.add(kr);
            }
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

}
