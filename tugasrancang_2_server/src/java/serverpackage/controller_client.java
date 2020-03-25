package serverpackage;

import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ClientController")
public class controller_client {

    //KONEKSI
    public java.sql.Connection con;
    PreparedStatement ps;

    public controller_client() throws RemoteException {
        try {
            this.con = new koneksi().getCon();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //LOGIN
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(gs_login data) throws ClassNotFoundException {
        String result = "-1";
        try {
            Statement st = con.createStatement();
            String a = "SELECT * FROM akun WHERE username = '" + data.getUser() + "' AND password = '" + data.getPass() + "'";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                result = rs.getString("id_phn");
            }
        } catch (Exception e) {
            result = "Error!";
        }

        return result;
    }

    //PROFILE
    @POST
    @Path("/tampilProfile")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_penghuni2> getPhn(gs_penghuni2 data) throws ClassNotFoundException {
        List<gs_penghuni2> list = new ArrayList<gs_penghuni2>();

        try {
            Statement st = con.createStatement();
            String a = "SELECT id, nik, nama, kontak, alamat, kode_kamar, tgl_masuk, datediff(now(), tgl_masuk) AS 'lama' FROM penghuni WHERE id = '" + data.getId_phn() + "'";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nik = rs.getString("nik");
                String nama = rs.getString("nama");
                String kontak = rs.getString("kontak");
                String alamat = rs.getString("alamat");
                String tgl = rs.getString("tgl_masuk");
                int kode_kamar = rs.getInt("kode_kamar");
                int lama = rs.getInt("lama");

                gs_penghuni2 phn = new gs_penghuni2();
                phn.setId_phn(id);
                phn.setNik(nik);
                phn.setNama(nama);
                phn.setKontak(kontak);
                phn.setAlamat(alamat);
                phn.setKd_kamar(kode_kamar);
                phn.setTgl(tgl);
                phn.setLama(lama);
                list.add(phn);
            }
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }

        return list;
    }

    @POST
    @Path("/editProfile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPhn(gs_penghuni data) throws ClassNotFoundException {
        String result = "Gagal!";
        try {
            ps = con.prepareStatement("UPDATE penghuni SET nama = '" + data.getNama() + "', kontak = '" + data.getKontak() + "', alamat = '" + data.getAlamat() + "' WHERE id = '" + data.getId_phn() + "'");
            ps.executeUpdate();

            result = "Perubahan berhasil!";
        } catch (Exception e) {
            result = "Terjadi kesalahan pada server!\n" + e;
        }

        return Response.status(201).entity(result).build();
    }

    //PEMBAYARAN
    @POST
    @Path("/tampilRiwayatTagihan")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_riwayat_transaksi> getRTgh(gs_penghuni data) throws ClassNotFoundException {
        List<gs_riwayat_transaksi> list = new ArrayList<gs_riwayat_transaksi>();
        
        try {
            Statement st = con.createStatement();
            String a = "SELECT a.bulan, a.tahun, IF(b.status = 1, DATE_FORMAT(b.tgl_bayar, \"%d %M %Y\"), '-') as 'tgl_bayar', IF(b.status = 1, 'Lunas', 'Belum Lunas') AS 'status' FROM transaksi a, transaksi_det b WHERE a.id_trans = b.id_trans AND b.id_phn = '" + data.getId_phn() + "'";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                String bulan = rs.getString("bulan");
                int tahun = rs.getInt("tahun");
                String tanggal = rs.getString("tgl_bayar");
                String status = rs.getString("status");

                gs_riwayat_transaksi trs = new gs_riwayat_transaksi();
                trs.setBulan(bulan);
                trs.setTahun(tahun);
                trs.setTgl_bayar(tanggal);
                trs.setStatus(status);

                list.add(trs);
            }

            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return list;
    }

    @POST
    @Path("/tampilBulanTagihan")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_bulan> getBTgh(gs_penghuni data) throws ClassNotFoundException {
        List<gs_bulan> list = new ArrayList<gs_bulan>();

        try {
            Statement st = con.createStatement();
            String a = "SELECT b.id_det, CONCAT(a.bulan, ' ', a.tahun) AS 'tagihan' FROM transaksi a, transaksi_det b WHERE b.id_trans = a.id_trans AND b.status = '0' AND b.id_phn = '" + data.getId_phn() + "'";
            ResultSet rs = st.executeQuery(a);
            while (rs.next()) {
                int id = rs.getInt("id_det");
                String bulan = rs.getString("tagihan");

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

    //PINDAH KAMAR
    @GET
    @Path("/tampilKamarKosong")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_kamar> getKmr() throws ClassNotFoundException {
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
    
    @GET
    @Path("/tampilKamarBerisi")
    @Produces(MediaType.APPLICATION_JSON)
    public List<gs_kamar> getKmr2() throws ClassNotFoundException {
        List<gs_kamar> list = new ArrayList<gs_kamar>();

        try {
            Statement st = con.createStatement();
            String a = "SELECT * FROM kamar WHERE kode_kamar IN(SELECT kode_kamar FROM penghuni)";
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
    
}
