package AdminPackage;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class admin extends javax.swing.JFrame {

    private int tmp = 0, tmp2 = 0, tmp3 = 0, id_bln1 = 0;

    //METHOD
    void m1(String d1, String d2, String d3, String d4, String d5) {
        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tambahPenghuni");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"nik\":\"" + d1 + "\",\"nama\":\"" + d2 + "\",\"kontak\":\"" + d3 + "\",\"alamat\":\"" + d4 + "\",\"kd_kamar\":\"" + d5 + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                JOptionPane.showMessageDialog(null, opt);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void m2(int d2, String d3, int d4, int d5, String d6, int d7) {
        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tambahTagihan");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"id_phn\":\"" + d2 + "\",\"tgl_bayar\":\"" + d3 + "\",\"tagihan\":\"" + d4 + "\",\"status\":\"" + d5 + "\",\"bulan\":\"" + d6 + "\",\"tahun\":\"" + d7 + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                JOptionPane.showMessageDialog(null, opt);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void m4() {
        DefaultTableModel dt;
        Object[] baris = {"NIK", "Nama", "Kontak", "Alamat", "No. Kamar"};
        dt = new DefaultTableModel(null, baris);
        tb_phn.setModel(dt);

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tampilPenghuni");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String NIK = (String) s.get("nik");
                        String NAMA = (String) s.get("nama");
                        String KONTAK = (String) s.get("kontak");
                        String ALAMAT = (String) s.get("alamat");
                        String KD = (String) s.get("kd_kamar").toString();

                        String[] data = {NIK, NAMA, KONTAK, ALAMAT, KD};
                        dt.addRow(data);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void m5(int d, String g, int f) {
        DefaultTableModel dt;
        Object[] baris = {"Kamar", "Nama", "Tanggal", "Tagihan"};
        dt = new DefaultTableModel(null, baris);
        tb_byr.setModel(dt);
        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tampilTransaksi2");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"status\":\"" + d + "\",\"bulan\":\"" + g + "\",\"tahun\":\"" + f + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String KAMAR = (String) s.get("kamar").toString();
                        String NAMA = (String) s.get("nama");
                        String TANGGAL = (String) s.get("tgl_bayar");
                        String TAGIHAN = (String) s.get("tagihan").toString();

                        String[] data = {KAMAR, NAMA, TANGGAL, TAGIHAN};
                        dt.addRow(data);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void m6(int id, int baru) {
        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/pindahKamar");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"id_phn\":\"" + id + "\",\"kd_kamar\":\"" + baru + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                JOptionPane.showMessageDialog(null, opt);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    void m7(int kode) {
        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/akhiri");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"kode\":\"" + kode + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                JOptionPane.showMessageDialog(null, opt);
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    //SERVICES
    void get_kamar_tb(int a, int b) {
        DefaultTableModel dt;
        Object[] baris = {"Nomor kamar", "Lantai"};
        dt = new DefaultTableModel(null, baris);
        tb_kamar.setModel(dt);

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tampilKamar");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"lantai\":" + a + ",\"status\":\"" + b + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String NO = (String) s.get("kode_kamar").toString();
                        String LT = (String) s.get("lantai").toString();

                        String[] data = {NO, LT};
                        dt.addRow(data);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void get_bln_cmb1(int d) {
        cmb_bulan.removeAllItems();

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/cariBulan1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"id_phn\":\"" + d + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        Long tmpl = (Long) s.get("id_bln");
                        id_bln1 = tmpl.intValue();

                        String bulan = (String) s.get("nama_bln");
                        cmb_bulan.addItem(bulan);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void get_bln_cmb2() {
        cmb_bulan2.removeAllItems();
        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tampilBulan2");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String bulan = (String) s.get("nama_bln");;
                        cmb_bulan2.addItem(bulan);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void get_lantai_cmb() {
        cmb_lantai.removeAllItems();

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tampilLantai");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);
                        String LT = (String) s.get("lantai").toString();
                        cmb_lantai.addItem(LT);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void get_kamar_cmb() {
        cmb_no_kamar.removeAllItems();

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tampilKamarKosong");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    cmb_no_kamar.addItem("Lantai 1");
                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String NO = (String) s.get("kode_kamar").toString();
                        String LT = (String) s.get("lantai").toString();

                        if (LT.equals("1")) {
                            cmb_no_kamar.addItem(NO);
                        }
                    }

                    cmb_no_kamar.addItem("");
                    cmb_no_kamar.addItem("Lantai 2");
                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String NO = (String) s.get("kode_kamar").toString();
                        String LT = (String) s.get("lantai").toString();

                        if (LT.equals("2")) {
                            cmb_no_kamar.addItem(NO);
                        }
                    }

                    cmb_no_kamar.addItem("");
                    cmb_no_kamar.addItem("Lantai 3");
                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String NO = (String) s.get("kode_kamar").toString();
                        String LT = (String) s.get("lantai").toString();

                        if (LT.equals("3")) {
                            cmb_no_kamar.addItem(NO);
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void get_kamarpdh_cmb() {
        cmb_pdh.removeAllItems();

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/tampilKamarKosong");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    cmb_pdh.addItem("Lantai 1");
                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String NO = (String) s.get("kode_kamar").toString();
                        String LT = (String) s.get("lantai").toString();

                        if (LT.equals("1")) {
                            cmb_pdh.addItem(NO);
                        }
                    }

                    cmb_pdh.addItem("");
                    cmb_pdh.addItem("Lantai 2");
                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String NO = (String) s.get("kode_kamar").toString();
                        String LT = (String) s.get("lantai").toString();

                        if (LT.equals("2")) {
                            cmb_pdh.addItem(NO);
                        }
                    }

                    cmb_pdh.addItem("");
                    cmb_pdh.addItem("Lantai 3");
                    for (int i = 0; i < s_data.size(); i++) {
                        JSONObject s = (JSONObject) s_data.get(i);

                        String NO = (String) s.get("kode_kamar").toString();
                        String LT = (String) s.get("lantai").toString();

                        if (LT.equals("3")) {
                            cmb_pdh.addItem(NO);
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void get_trans_tb(int kd_kmr) {
        int i = 0;
        DefaultTableModel dt;
        Object[] baris = {"NIK", "Nama", "Kontak", "Alamat", "No. Kamar"};
        dt = new DefaultTableModel(null, baris);
        tb_dttrans.setModel(dt);

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/cariPenghuni");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"kd_kamar\":\"" + kd_kmr + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int j = 0; j < s_data.size(); j++) {
                        JSONObject s = (JSONObject) s_data.get(j);

                        Long tmpl = (Long) s.get("id_phn");
                        tmp = tmpl.intValue();

                        String NIK = (String) s.get("nik");
                        String NAMA = (String) s.get("nama");
                        String KONTAK = (String) s.get("kontak");
                        String ALAMAT = (String) s.get("alamat");
                        String KD = (String) s.get("kd_kamar").toString();

                        String[] data = {NIK, NAMA, KONTAK, ALAMAT, KD};
                        dt.addRow(data);
                        i++;
                    }

                    if (i != 0) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        txt_tglbyr.setText(dateFormat.format(date));
                        txt_tgh.setText("600000");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void get_pdh_tb(int kd_kmr) {
        DefaultTableModel dt;
        Object[] baris = {"NIK", "Nama", "Kontak", "Alamat", "No. Kamar"};
        dt = new DefaultTableModel(null, baris);
        tb_pk.setModel(dt);
        int i = 0;

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/cariPenghuni");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"kd_kamar\":\"" + kd_kmr + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int j = 0; j < s_data.size(); j++) {
                        JSONObject s = (JSONObject) s_data.get(j);

                        Long tmpl = (Long) s.get("id_phn");
                        tmp2 = tmpl.intValue();

                        String NIK = (String) s.get("nik");
                        String NAMA = (String) s.get("nama");
                        String KONTAK = (String) s.get("kontak");
                        String ALAMAT = (String) s.get("alamat");
                        String KD = (String) s.get("kd_kamar").toString();

                        String[] data = {NIK, NAMA, KONTAK, ALAMAT, KD};
                        dt.addRow(data);
                        i++;
                    }

                    if (i != 0) {
                        get_kamarpdh_cmb();
                    } else {
                        cmb_pdh.removeAllItems();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void get_keluar(int kd_kamar) {
        DefaultTableModel dt;
        Object[] baris = {"NIK", "Nama", "Kontak", "Alamat", "No. Kamar"};
        dt = new DefaultTableModel(null, baris);
        tb_keluar.setModel(dt);

        try {
            URL url = new URL("http://192.168.1.104:26791/tugasrancang_2_server/wtfservices/AdminController/cariPenghuni");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            String input = "{\"kd_kamar\":\"" + kd_kamar + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Gagal => HTTP error code: " + conn.getResponseCode());
            }

            JSONParser parser = new JSONParser();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String opt;

            while ((opt = br.readLine()) != null) {
                try {
                    Object obj = parser.parse(opt);
                    JSONArray s_data = (JSONArray) obj;

                    for (int j = 0; j < s_data.size(); j++) {
                        JSONObject s = (JSONObject) s_data.get(j);

                        Long tmpl = (Long) s.get("id_phn");
                        tmp2 = tmpl.intValue();

                        String NIK = (String) s.get("nik");
                        String NAMA = (String) s.get("nama");
                        String KONTAK = (String) s.get("kontak");
                        String ALAMAT = (String) s.get("alamat");
                        String KD = (String) s.get("kd_kamar").toString();

                        String[] data = {NIK, NAMA, KONTAK, ALAMAT, KD};
                        dt.addRow(data);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan!\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }

    void acceptNUMBER(JTextField tf) {
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                char ch = ke.getKeyChar();
                if (Character.isDigit(ch) || ke.getExtendedKeyCode() == KeyEvent.VK_SPACE || ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                    tf.setEditable(true);
                } else {
                    tf.setEditable(false);
                }
            }
        });
    }

    void acceptLETTER(JTextField tf) {
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                char ch = ke.getKeyChar();
                if (Character.isLetter(ch) || ke.getExtendedKeyCode() == KeyEvent.VK_SPACE || ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE || ke.getExtendedKeyCode() == KeyEvent.VK_DELETE) {
                    tf.setEditable(true);
                } else {
                    tf.setEditable(false);
                }
            }
        });
    }

    //MENU
    private void dashboard() {
        dashboard.setVisible(true);
        tambah_penghuni.setVisible(false);
        transaksi_pembayaran.setVisible(false);
        status_kamar.setVisible(false);
        pindah_kamar.setVisible(false);
        daftar_penghuni.setVisible(false);
        daftar_pembayaran.setVisible(false);
        keluar_kost.setVisible(false);

        jLabel2.setText("<html>Kelola bisnis anda lebih baik dengan memilih bantuan yang<br>anda perlukan di bawah ini.<html>");
        jLabel17.setText("<html>Pastikan syarat dan ketentuan sudah terpenuhi</html>");
        jLabel18.setText("<html>Jangan sampai telat bayar</html>");
        jLabel19.setText("<html>Masih ada kamar kosong gak ya?</html>");
        jLabel20.setText("<html>Siapa aja sih yang tinggal disini?</html>");
        jLabel21.setText("<html>Bulan ini yang belum bayar siapa aja nih?</html>");
        jLabel22.setText("<html>Bosen disitu terus, mau ganti pemandangan</html>");
        jLabel23.setText("<html>Jangan lupa ucapkan selamat tinggal</html>");
    }

    private void tambah_penghuni() {
        dashboard.setVisible(false);
        tambah_penghuni.setVisible(true);
        transaksi_pembayaran.setVisible(false);
        status_kamar.setVisible(false);
        pindah_kamar.setVisible(false);
        daftar_penghuni.setVisible(false);
        daftar_pembayaran.setVisible(false);
        keluar_kost.setVisible(false);

        get_kamar_cmb();
    }

    private void transaksi_pembayaran() {
        dashboard.setVisible(false);
        tambah_penghuni.setVisible(false);
        transaksi_pembayaran.setVisible(true);
        status_kamar.setVisible(false);
        pindah_kamar.setVisible(false);
        daftar_penghuni.setVisible(false);
        daftar_pembayaran.setVisible(false);
        keluar_kost.setVisible(false);
    }

    private void status_kamar() {
        dashboard.setVisible(false);
        tambah_penghuni.setVisible(false);
        transaksi_pembayaran.setVisible(false);
        status_kamar.setVisible(true);
        pindah_kamar.setVisible(false);
        daftar_penghuni.setVisible(false);
        daftar_pembayaran.setVisible(false);
        keluar_kost.setVisible(false);

        get_lantai_cmb();
    }

    private void pindah_kamar() {
        dashboard.setVisible(false);
        tambah_penghuni.setVisible(false);
        transaksi_pembayaran.setVisible(false);
        status_kamar.setVisible(false);
        pindah_kamar.setVisible(true);
        daftar_penghuni.setVisible(false);
        daftar_pembayaran.setVisible(false);
        keluar_kost.setVisible(false);
    }

    private void daftar_penghuni() {
        dashboard.setVisible(false);
        tambah_penghuni.setVisible(false);
        transaksi_pembayaran.setVisible(false);
        status_kamar.setVisible(false);
        pindah_kamar.setVisible(false);
        daftar_penghuni.setVisible(true);
        daftar_pembayaran.setVisible(false);
        keluar_kost.setVisible(false);
    }

    private void daftar_pembayaran() {
        dashboard.setVisible(false);
        tambah_penghuni.setVisible(false);
        transaksi_pembayaran.setVisible(false);
        status_kamar.setVisible(false);
        pindah_kamar.setVisible(false);
        daftar_penghuni.setVisible(false);
        daftar_pembayaran.setVisible(true);
        keluar_kost.setVisible(false);

        get_bln_cmb2();
    }

    private void keluar_kost() {
        dashboard.setVisible(false);
        tambah_penghuni.setVisible(false);
        transaksi_pembayaran.setVisible(false);
        status_kamar.setVisible(false);
        pindah_kamar.setVisible(false);
        daftar_penghuni.setVisible(false);
        daftar_pembayaran.setVisible(false);
        keluar_kost.setVisible(true);
    }

    //DASHBOARD MENU
    private void frameEnter(JPanel p) {
        p.setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 2));
    }

    private void frameExit(JPanel p) {
        p.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }

    //LEFTSIDE MENU
    private void lpEnter(JPanel p) {
        p.setBackground(new Color(226, 226, 226));
    }

    private void lpExit(JPanel p) {
        p.setBackground(new Color(245, 245, 245));
    }

    private void Active(JPanel p) {
        p.setBackground(new Color(0, 120, 215));
    }

    private void btnNear(JPanel p) {
        p.setBorder(BorderFactory.createLineBorder(new Color(122, 122, 122), 2));
    }

    private void btnFar(JPanel p) {
        p.setBorder(BorderFactory.createLineBorder(new Color(192, 192, 192), 2));
    }

    /**
     * Creates new form NewJFrame
     */
    public admin() {
        initComponents();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.setMaximizedBounds(env.getMaximumWindowBounds());
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);

        dashboard();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        leftside_panel = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        lp1 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        lp2 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        lp3 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        lp4 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        lp5 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        lp6 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        lp8 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        lp9 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        lp7 = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        dashboard = new javax.swing.JPanel();
        d_tambah_penghuni = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        d_transasksi_pembayaran = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        d_status_kamar = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        d_data_transaksi = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        d_akhiri = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        d_pindah_kamar = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        d_data_penghuni = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tambah_penghuni = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_kontak = new javax.swing.JTextField();
        txt_nik = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        cmb_no_kamar = new javax.swing.JComboBox<>();
        btn_save1 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        transaksi_pembayaran = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txt_kdkr = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_dttrans = new javax.swing.JTable();
        txt_tglbyr = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txt_tgh = new javax.swing.JTextField();
        btn_cari3 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        btn_bayar = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        cmb_bulan = new javax.swing.JComboBox<>();
        jLabel68 = new javax.swing.JLabel();
        status_kamar = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tb_kamar = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        btn_load2 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        cmb_lantai = new javax.swing.JComboBox<>();
        cmb_kondisi = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        pindah_kamar = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txt_mkdkmr = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_pk = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        cmb_pdh = new javax.swing.JComboBox<>();
        btn_cari4 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        btn_setuju = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        daftar_penghuni = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_phn = new javax.swing.JTable();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        btn_load1 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        daftar_pembayaran = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tb_byr = new javax.swing.JTable();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        btn_load3 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        cmb_status = new javax.swing.JComboBox<>();
        jLabel69 = new javax.swing.JLabel();
        cmb_bulan2 = new javax.swing.JComboBox<>();
        jLabel72 = new javax.swing.JLabel();
        keluar_kost = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tb_keluar = new javax.swing.JTable();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        txt_kdkr1 = new javax.swing.JTextField();
        btn_cari5 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        btn_keluar = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistem Terdistribusi 1");
        setBackground(new java.awt.Color(255, 255, 255));
        setSize(new java.awt.Dimension(800, 600));

        leftside_panel.setBackground(new java.awt.Color(245, 245, 245));
        leftside_panel.setMinimumSize(new java.awt.Dimension(40, 32767));
        leftside_panel.setPreferredSize(new java.awt.Dimension(40, 32767));

        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_menu.png"))); // NOI18N

        lp1.setBackground(new java.awt.Color(245, 245, 245));
        lp1.setMinimumSize(new java.awt.Dimension(40, 40));
        lp1.setPreferredSize(new java.awt.Dimension(40, 40));
        lp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lp1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lp1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lp1MouseExited(evt);
            }
        });

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_home.png"))); // NOI18N

        javax.swing.GroupLayout lp1Layout = new javax.swing.GroupLayout(lp1);
        lp1.setLayout(lp1Layout);
        lp1Layout.setHorizontalGroup(
            lp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel49)
                .addGap(10, 10, 10))
        );
        lp1Layout.setVerticalGroup(
            lp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel49)
                .addGap(10, 10, 10))
        );

        lp2.setBackground(new java.awt.Color(245, 245, 245));
        lp2.setPreferredSize(new java.awt.Dimension(40, 40));
        lp2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lp2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lp2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lp2MouseExited(evt);
            }
        });

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_tambah.png"))); // NOI18N

        javax.swing.GroupLayout lp2Layout = new javax.swing.GroupLayout(lp2);
        lp2.setLayout(lp2Layout);
        lp2Layout.setHorizontalGroup(
            lp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel50)
                .addGap(10, 10, 10))
        );
        lp2Layout.setVerticalGroup(
            lp2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel50)
                .addGap(10, 10, 10))
        );

        lp3.setBackground(new java.awt.Color(245, 245, 245));
        lp3.setPreferredSize(new java.awt.Dimension(40, 40));
        lp3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lp3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lp3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lp3MouseExited(evt);
            }
        });

        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_bayar.png"))); // NOI18N

        javax.swing.GroupLayout lp3Layout = new javax.swing.GroupLayout(lp3);
        lp3.setLayout(lp3Layout);
        lp3Layout.setHorizontalGroup(
            lp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel51)
                .addGap(10, 10, 10))
        );
        lp3Layout.setVerticalGroup(
            lp3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel51)
                .addGap(10, 10, 10))
        );

        lp4.setBackground(new java.awt.Color(245, 245, 245));
        lp4.setPreferredSize(new java.awt.Dimension(40, 40));
        lp4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lp4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lp4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lp4MouseExited(evt);
            }
        });

        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_kamar.png"))); // NOI18N

        javax.swing.GroupLayout lp4Layout = new javax.swing.GroupLayout(lp4);
        lp4.setLayout(lp4Layout);
        lp4Layout.setHorizontalGroup(
            lp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel52)
                .addGap(10, 10, 10))
        );
        lp4Layout.setVerticalGroup(
            lp4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel52)
                .addGap(10, 10, 10))
        );

        lp5.setBackground(new java.awt.Color(245, 245, 245));
        lp5.setPreferredSize(new java.awt.Dimension(40, 40));
        lp5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lp5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lp5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lp5MouseExited(evt);
            }
        });

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_penghuni.png"))); // NOI18N

        javax.swing.GroupLayout lp5Layout = new javax.swing.GroupLayout(lp5);
        lp5.setLayout(lp5Layout);
        lp5Layout.setHorizontalGroup(
            lp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel53)
                .addGap(10, 10, 10))
        );
        lp5Layout.setVerticalGroup(
            lp5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel53)
                .addGap(10, 10, 10))
        );

        lp6.setBackground(new java.awt.Color(245, 245, 245));
        lp6.setPreferredSize(new java.awt.Dimension(40, 40));
        lp6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lp6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lp6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lp6MouseExited(evt);
            }
        });

        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_daftar.png"))); // NOI18N

        javax.swing.GroupLayout lp6Layout = new javax.swing.GroupLayout(lp6);
        lp6.setLayout(lp6Layout);
        lp6Layout.setHorizontalGroup(
            lp6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel54)
                .addGap(10, 10, 10))
        );
        lp6Layout.setVerticalGroup(
            lp6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel54)
                .addGap(10, 10, 10))
        );

        lp8.setBackground(new java.awt.Color(245, 245, 245));
        lp8.setPreferredSize(new java.awt.Dimension(40, 40));
        lp8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lp8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lp8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lp8MouseExited(evt);
            }
        });

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_pindah.png"))); // NOI18N

        javax.swing.GroupLayout lp8Layout = new javax.swing.GroupLayout(lp8);
        lp8.setLayout(lp8Layout);
        lp8Layout.setHorizontalGroup(
            lp8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel55)
                .addGap(10, 10, 10))
        );
        lp8Layout.setVerticalGroup(
            lp8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel55)
                .addGap(10, 10, 10))
        );

        lp9.setBackground(new java.awt.Color(245, 245, 245));
        lp9.setPreferredSize(new java.awt.Dimension(40, 40));
        lp9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lp9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lp9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lp9MouseExited(evt);
            }
        });

        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini_keluar.png"))); // NOI18N

        javax.swing.GroupLayout lp9Layout = new javax.swing.GroupLayout(lp9);
        lp9.setLayout(lp9Layout);
        lp9Layout.setHorizontalGroup(
            lp9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel56)
                .addGap(10, 10, 10))
        );
        lp9Layout.setVerticalGroup(
            lp9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lp9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel56)
                .addGap(10, 10, 10))
        );

        lp7.setBackground(new java.awt.Color(245, 245, 245));
        lp7.setPreferredSize(new java.awt.Dimension(40, 40));

        javax.swing.GroupLayout lp7Layout = new javax.swing.GroupLayout(lp7);
        lp7.setLayout(lp7Layout);
        lp7Layout.setHorizontalGroup(
            lp7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        lp7Layout.setVerticalGroup(
            lp7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout leftside_panelLayout = new javax.swing.GroupLayout(leftside_panel);
        leftside_panel.setLayout(leftside_panelLayout);
        leftside_panelLayout.setHorizontalGroup(
            leftside_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftside_panelLayout.createSequentialGroup()
                .addGroup(leftside_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(leftside_panelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel48))
                    .addComponent(lp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lp3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lp5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lp6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lp8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lp9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftside_panelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lp7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        leftside_panelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel48, lp1, lp2, lp3, lp4, lp5, lp6, lp7, lp8, lp9});

        leftside_panelLayout.setVerticalGroup(
            leftside_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftside_panelLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel48)
                .addGap(25, 25, 25)
                .addComponent(lp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lp2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lp3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lp4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lp5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lp6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lp8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lp9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lp7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32250, Short.MAX_VALUE))
        );

        leftside_panelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel48, lp1, lp2, lp3, lp4, lp5, lp6, lp7, lp8, lp9});

        jLayeredPane2.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane2.setPreferredSize(new java.awt.Dimension(800, 600));

        dashboard.setBackground(new java.awt.Color(253, 253, 253));
        dashboard.setAutoscrolls(true);
        dashboard.setMinimumSize(new java.awt.Dimension(800, 600));

        d_tambah_penghuni.setBackground(new java.awt.Color(255, 255, 255));
        d_tambah_penghuni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        d_tambah_penghuni.setPreferredSize(new java.awt.Dimension(230, 200));
        d_tambah_penghuni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_tambah_penghuniMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_tambah_penghuniMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_tambah_penghuniMouseExited(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tambah.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel10.setText("Tambah penghuni");

        jLabel17.setBackground(new java.awt.Color(230, 230, 230));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout d_tambah_penghuniLayout = new javax.swing.GroupLayout(d_tambah_penghuni);
        d_tambah_penghuni.setLayout(d_tambah_penghuniLayout);
        d_tambah_penghuniLayout.setHorizontalGroup(
            d_tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_tambah_penghuniLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(d_tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel10)
                    .addComponent(jLabel3))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        d_tambah_penghuniLayout.setVerticalGroup(
            d_tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_tambah_penghuniLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel3)
                .addGap(15, 15, 15)
                .addComponent(jLabel10)
                .addGap(0, 0, 0)
                .addComponent(jLabel17)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Dashboard panel");

        jLabel2.setBackground(new java.awt.Color(230, 230, 230));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));

        d_transasksi_pembayaran.setBackground(new java.awt.Color(255, 255, 255));
        d_transasksi_pembayaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        d_transasksi_pembayaran.setPreferredSize(new java.awt.Dimension(230, 200));
        d_transasksi_pembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_transasksi_pembayaranMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_transasksi_pembayaranMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_transasksi_pembayaranMouseExited(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bayar.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel11.setText("Transaksi pembayaran");

        jLabel18.setBackground(new java.awt.Color(230, 230, 230));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout d_transasksi_pembayaranLayout = new javax.swing.GroupLayout(d_transasksi_pembayaran);
        d_transasksi_pembayaran.setLayout(d_transasksi_pembayaranLayout);
        d_transasksi_pembayaranLayout.setHorizontalGroup(
            d_transasksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_transasksi_pembayaranLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(d_transasksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel18)
                    .addComponent(jLabel11))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        d_transasksi_pembayaranLayout.setVerticalGroup(
            d_transasksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_transasksi_pembayaranLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4)
                .addGap(15, 15, 15)
                .addComponent(jLabel11)
                .addGap(0, 0, 0)
                .addComponent(jLabel18)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        d_status_kamar.setBackground(new java.awt.Color(255, 255, 255));
        d_status_kamar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        d_status_kamar.setPreferredSize(new java.awt.Dimension(230, 200));
        d_status_kamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_status_kamarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_status_kamarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_status_kamarMouseExited(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/kamar.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel12.setText("Status kamar");

        jLabel19.setBackground(new java.awt.Color(230, 230, 230));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout d_status_kamarLayout = new javax.swing.GroupLayout(d_status_kamar);
        d_status_kamar.setLayout(d_status_kamarLayout);
        d_status_kamarLayout.setHorizontalGroup(
            d_status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_status_kamarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(d_status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel12)
                    .addComponent(jLabel5))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        d_status_kamarLayout.setVerticalGroup(
            d_status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_status_kamarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addGap(15, 15, 15)
                .addComponent(jLabel12)
                .addGap(0, 0, 0)
                .addComponent(jLabel19)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        d_data_transaksi.setBackground(new java.awt.Color(255, 255, 255));
        d_data_transaksi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        d_data_transaksi.setPreferredSize(new java.awt.Dimension(230, 200));
        d_data_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_data_transaksiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_data_transaksiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_data_transaksiMouseExited(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/daftar.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel14.setText("Data transaksi");

        jLabel21.setBackground(new java.awt.Color(230, 230, 230));
        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout d_data_transaksiLayout = new javax.swing.GroupLayout(d_data_transaksi);
        d_data_transaksi.setLayout(d_data_transaksiLayout);
        d_data_transaksiLayout.setHorizontalGroup(
            d_data_transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_data_transaksiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(d_data_transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel14)
                    .addComponent(jLabel7))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        d_data_transaksiLayout.setVerticalGroup(
            d_data_transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_data_transaksiLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel7)
                .addGap(15, 15, 15)
                .addComponent(jLabel14)
                .addGap(0, 0, 0)
                .addComponent(jLabel21)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        d_akhiri.setBackground(new java.awt.Color(255, 255, 255));
        d_akhiri.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        d_akhiri.setPreferredSize(new java.awt.Dimension(230, 200));
        d_akhiri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_akhiriMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_akhiriMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_akhiriMouseExited(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/keluar.png"))); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel16.setText("Akhiri masa tinggal");

        jLabel23.setBackground(new java.awt.Color(230, 230, 230));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout d_akhiriLayout = new javax.swing.GroupLayout(d_akhiri);
        d_akhiri.setLayout(d_akhiriLayout);
        d_akhiriLayout.setHorizontalGroup(
            d_akhiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_akhiriLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(d_akhiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel16)
                    .addComponent(jLabel9))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        d_akhiriLayout.setVerticalGroup(
            d_akhiriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_akhiriLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9)
                .addGap(15, 15, 15)
                .addComponent(jLabel16)
                .addGap(0, 0, 0)
                .addComponent(jLabel23)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        d_pindah_kamar.setBackground(new java.awt.Color(255, 255, 255));
        d_pindah_kamar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        d_pindah_kamar.setPreferredSize(new java.awt.Dimension(230, 200));
        d_pindah_kamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_pindah_kamarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_pindah_kamarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_pindah_kamarMouseExited(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pindah.png"))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel15.setText("Pindah kamar");

        jLabel22.setBackground(new java.awt.Color(230, 230, 230));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout d_pindah_kamarLayout = new javax.swing.GroupLayout(d_pindah_kamar);
        d_pindah_kamar.setLayout(d_pindah_kamarLayout);
        d_pindah_kamarLayout.setHorizontalGroup(
            d_pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_pindah_kamarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(d_pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel15)
                    .addComponent(jLabel8))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        d_pindah_kamarLayout.setVerticalGroup(
            d_pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_pindah_kamarLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addGap(15, 15, 15)
                .addComponent(jLabel15)
                .addGap(0, 0, 0)
                .addComponent(jLabel22)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        d_data_penghuni.setBackground(new java.awt.Color(255, 255, 255));
        d_data_penghuni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        d_data_penghuni.setPreferredSize(new java.awt.Dimension(230, 200));
        d_data_penghuni.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                d_data_penghuniMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d_data_penghuniMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d_data_penghuniMouseExited(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/penghuni.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel13.setText("Data penghuni");

        jLabel20.setBackground(new java.awt.Color(230, 230, 230));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout d_data_penghuniLayout = new javax.swing.GroupLayout(d_data_penghuni);
        d_data_penghuni.setLayout(d_data_penghuniLayout);
        d_data_penghuniLayout.setHorizontalGroup(
            d_data_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_data_penghuniLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(d_data_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel20)
                    .addComponent(jLabel13))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        d_data_penghuniLayout.setVerticalGroup(
            d_data_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(d_data_penghuniLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel6)
                .addGap(15, 15, 15)
                .addComponent(jLabel13)
                .addGap(0, 0, 0)
                .addComponent(jLabel20)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dashboardLayout.createSequentialGroup()
                        .addComponent(d_pindah_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(d_akhiri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dashboardLayout.createSequentialGroup()
                            .addComponent(d_tambah_penghuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(d_transasksi_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(d_status_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel1)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addComponent(d_data_penghuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(d_data_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(242, Short.MAX_VALUE))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addGap(45, 45, 45)
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(d_data_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(d_tambah_penghuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(d_transasksi_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(d_status_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(d_data_penghuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(d_pindah_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(d_akhiri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(220, Short.MAX_VALUE))
        );

        tambah_penghuni.setBackground(new java.awt.Color(255, 255, 255));
        tambah_penghuni.setMinimumSize(new java.awt.Dimension(800, 600));
        tambah_penghuni.setPreferredSize(new java.awt.Dimension(1464, 768));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel24.setText("Tambah penghuni");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("Pastikan syarat dan ketentuan sudah terpenuhi");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setText("NIK");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("Nama");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Kontak");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setText("Alamat asal");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setText("Nomor kamar terpilih");

        txt_kontak.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_kontak.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));
        txt_kontak.setMargin(new java.awt.Insets(2, 5, 2, 5));
        txt_kontak.setPreferredSize(new java.awt.Dimension(4, 25));
        txt_kontak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_kontakKeyTyped(evt);
            }
        });

        txt_nik.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_nik.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));
        txt_nik.setMargin(new java.awt.Insets(2, 5, 2, 5));
        txt_nik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nikKeyTyped(evt);
            }
        });

        txt_nama.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_nama.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));
        txt_nama.setMargin(new java.awt.Insets(2, 5, 2, 5));
        txt_nama.setPreferredSize(new java.awt.Dimension(4, 25));
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        txt_nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_namaKeyTyped(evt);
            }
        });

        txt_alamat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_alamat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));
        txt_alamat.setPreferredSize(new java.awt.Dimension(4, 25));

        cmb_no_kamar.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cmb_no_kamar.setBorder(null);
        cmb_no_kamar.setMinimumSize(new java.awt.Dimension(56, 22));
        cmb_no_kamar.setPreferredSize(new java.awt.Dimension(56, 22));
        cmb_no_kamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_no_kamarActionPerformed(evt);
            }
        });

        btn_save1.setBackground(new java.awt.Color(192, 192, 192));
        btn_save1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_save1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_save1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_save1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_save1MouseExited(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel57.setText("Simpan");

        javax.swing.GroupLayout btn_save1Layout = new javax.swing.GroupLayout(btn_save1);
        btn_save1.setLayout(btn_save1Layout);
        btn_save1Layout.setHorizontalGroup(
            btn_save1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_save1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel57)
                .addGap(20, 20, 20))
        );
        btn_save1Layout.setVerticalGroup(
            btn_save1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_save1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel57)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout tambah_penghuniLayout = new javax.swing.GroupLayout(tambah_penghuni);
        tambah_penghuni.setLayout(tambah_penghuniLayout);
        tambah_penghuniLayout.setHorizontalGroup(
            tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambah_penghuniLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tambah_penghuniLayout.createSequentialGroup()
                        .addComponent(txt_nik, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 145, Short.MAX_VALUE))
                    .addGroup(tambah_penghuniLayout.createSequentialGroup()
                        .addGroup(tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_alamat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tambah_penghuniLayout.createSequentialGroup()
                                    .addGroup(tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel28)
                                        .addComponent(txt_kontak, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel30)
                                        .addComponent(cmb_no_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txt_nama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel24)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26)
                            .addComponent(btn_save1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(144, 144, 144)))
                .addContainerGap(819, Short.MAX_VALUE))
        );

        tambah_penghuniLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txt_alamat, txt_nama, txt_nik});

        tambah_penghuniLayout.setVerticalGroup(
            tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tambah_penghuniLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel24)
                .addGap(13, 13, 13)
                .addComponent(jLabel25)
                .addGap(45, 45, 45)
                .addComponent(jLabel26)
                .addGap(8, 8, 8)
                .addComponent(txt_nik, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel27)
                .addGap(8, 8, 8)
                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel29)
                .addGap(8, 8, 8)
                .addComponent(txt_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel30))
                .addGap(8, 8, 8)
                .addGroup(tambah_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kontak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_no_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(btn_save1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(286, Short.MAX_VALUE))
        );

        tambah_penghuniLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_save1, cmb_no_kamar, txt_alamat, txt_kontak, txt_nama, txt_nik});

        transaksi_pembayaran.setBackground(new java.awt.Color(255, 255, 255));
        transaksi_pembayaran.setMinimumSize(new java.awt.Dimension(800, 600));
        transaksi_pembayaran.setPreferredSize(new java.awt.Dimension(1464, 768));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel31.setText("Transaksi pembayaran");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setText("Pastikan penghuni tidak terlambat membayar.");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel33.setText("Masukkan nomor kamar");

        txt_kdkr.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_kdkr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));
        txt_kdkr.setPreferredSize(new java.awt.Dimension(4, 25));
        txt_kdkr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_kdkrKeyTyped(evt);
            }
        });

        tb_dttrans.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_dttrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tb_dttrans);

        txt_tglbyr.setEditable(false);
        txt_tglbyr.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_tglbyr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setText("Tanggal bayar");

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel35.setText("Masukkan tagihan");

        txt_tgh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_tgh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));
        txt_tgh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_tghKeyTyped(evt);
            }
        });

        btn_cari3.setBackground(new java.awt.Color(192, 192, 192));
        btn_cari3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_cari3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cari3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cari3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_cari3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_cari3MouseExited(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel62.setText("Cari");

        javax.swing.GroupLayout btn_cari3Layout = new javax.swing.GroupLayout(btn_cari3);
        btn_cari3.setLayout(btn_cari3Layout);
        btn_cari3Layout.setHorizontalGroup(
            btn_cari3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_cari3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel62)
                .addGap(20, 20, 20))
        );
        btn_cari3Layout.setVerticalGroup(
            btn_cari3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_cari3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel62)
                .addGap(4, 4, 4))
        );

        btn_bayar.setBackground(new java.awt.Color(192, 192, 192));
        btn_bayar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_bayar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_bayar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_bayarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_bayarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_bayarMouseExited(evt);
            }
        });

        jLabel63.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel63.setText("Bayar");

        javax.swing.GroupLayout btn_bayarLayout = new javax.swing.GroupLayout(btn_bayar);
        btn_bayar.setLayout(btn_bayarLayout);
        btn_bayarLayout.setHorizontalGroup(
            btn_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_bayarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel63)
                .addGap(20, 20, 20))
        );
        btn_bayarLayout.setVerticalGroup(
            btn_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_bayarLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel63)
                .addGap(4, 4, 4))
        );

        cmb_bulan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel68.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel68.setText("Untuk membayar tagihan");

        javax.swing.GroupLayout transaksi_pembayaranLayout = new javax.swing.GroupLayout(transaksi_pembayaran);
        transaksi_pembayaran.setLayout(transaksi_pembayaranLayout);
        transaksi_pembayaranLayout.setHorizontalGroup(
            transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksi_pembayaranLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transaksi_pembayaranLayout.createSequentialGroup()
                        .addGroup(transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tgh, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(transaksi_pembayaranLayout.createSequentialGroup()
                                .addComponent(txt_tglbyr, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addGroup(transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel68)
                                    .addComponent(cmb_bulan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(964, Short.MAX_VALUE))
                    .addGroup(transaksi_pembayaranLayout.createSequentialGroup()
                        .addGroup(transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(transaksi_pembayaranLayout.createSequentialGroup()
                                .addComponent(txt_kdkr, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btn_cari3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel35)
                            .addComponent(jLabel34)
                            .addComponent(jLabel33)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(transaksi_pembayaranLayout.createSequentialGroup()
                        .addGroup(transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(transaksi_pembayaranLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(151, 151, 151)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        transaksi_pembayaranLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane1, txt_tgh});

        transaksi_pembayaranLayout.setVerticalGroup(
            transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksi_pembayaranLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel31)
                .addGap(13, 13, 13)
                .addComponent(jLabel32)
                .addGap(45, 45, 45)
                .addComponent(jLabel33)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_kdkr, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel68, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(8, 8, 8)
                .addGroup(transaksi_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_tglbyr, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_bulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel35)
                .addGap(8, 8, 8)
                .addComponent(txt_tgh, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(280, Short.MAX_VALUE))
        );

        transaksi_pembayaranLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_bayar, btn_cari3, cmb_bulan, txt_kdkr, txt_tgh, txt_tglbyr});

        status_kamar.setBackground(new java.awt.Color(255, 255, 255));
        status_kamar.setMinimumSize(new java.awt.Dimension(800, 600));
        status_kamar.setPreferredSize(new java.awt.Dimension(1464, 768));

        tb_kamar.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_kamar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tb_kamar);

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel40.setText("Status kamar");

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(51, 51, 51));
        jLabel41.setText("Cari tahu apakah kamar kosong masih tersedia.");

        btn_load2.setBackground(new java.awt.Color(192, 192, 192));
        btn_load2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_load2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_load2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_load2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_load2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_load2MouseExited(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel60.setText("Tampilkan data");

        javax.swing.GroupLayout btn_load2Layout = new javax.swing.GroupLayout(btn_load2);
        btn_load2.setLayout(btn_load2Layout);
        btn_load2Layout.setHorizontalGroup(
            btn_load2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_load2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel60)
                .addGap(20, 20, 20))
        );
        btn_load2Layout.setVerticalGroup(
            btn_load2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_load2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel60)
                .addGap(4, 4, 4))
        );

        cmb_lantai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        cmb_kondisi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cmb_kondisi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kosong", "Terisi" }));

        jLabel70.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel70.setText("Lantai");

        jLabel71.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel71.setText("Kondisi");

        javax.swing.GroupLayout status_kamarLayout = new javax.swing.GroupLayout(status_kamar);
        status_kamar.setLayout(status_kamarLayout);
        status_kamarLayout.setHorizontalGroup(
            status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(status_kamarLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(status_kamarLayout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(status_kamarLayout.createSequentialGroup()
                        .addGroup(status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel40))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, status_kamarLayout.createSequentialGroup()
                                .addGroup(status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmb_lantai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel70))
                                .addGap(15, 15, 15)
                                .addGroup(status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(status_kamarLayout.createSequentialGroup()
                                        .addComponent(jLabel71)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(status_kamarLayout.createSequentialGroup()
                                        .addComponent(cmb_kondisi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_load2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 964, Short.MAX_VALUE))))
        );

        status_kamarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmb_kondisi, cmb_lantai});

        status_kamarLayout.setVerticalGroup(
            status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(status_kamarLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel40)
                .addGap(13, 13, 13)
                .addComponent(jLabel41)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70)
                    .addComponent(jLabel71))
                .addGap(8, 8, 8)
                .addGroup(status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_load2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(status_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmb_lantai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmb_kondisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(133, 133, 133))
        );

        status_kamarLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_load2, cmb_kondisi, cmb_lantai});

        pindah_kamar.setBackground(new java.awt.Color(255, 255, 255));
        pindah_kamar.setMinimumSize(new java.awt.Dimension(800, 600));
        pindah_kamar.setPreferredSize(new java.awt.Dimension(1464, 768));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel42.setText("Pindah kamar");

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 51, 51));
        jLabel43.setText("Ingin ganti suasana kamar dengan yang baru? Pastikan kamar tujuan tidak terisi.");

        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel44.setText("Masukkan Kode kamar");

        txt_mkdkmr.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txt_mkdkmr.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));

        tb_pk.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_pk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tb_pk);

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel45.setText("Nomor kamar terpilih");

        cmb_pdh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_pdhActionPerformed(evt);
            }
        });

        btn_cari4.setBackground(new java.awt.Color(192, 192, 192));
        btn_cari4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_cari4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cari4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cari4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_cari4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_cari4MouseExited(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel64.setText("Cari");

        javax.swing.GroupLayout btn_cari4Layout = new javax.swing.GroupLayout(btn_cari4);
        btn_cari4.setLayout(btn_cari4Layout);
        btn_cari4Layout.setHorizontalGroup(
            btn_cari4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_cari4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel64)
                .addGap(20, 20, 20))
        );
        btn_cari4Layout.setVerticalGroup(
            btn_cari4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_cari4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel64)
                .addGap(4, 4, 4))
        );

        btn_setuju.setBackground(new java.awt.Color(192, 192, 192));
        btn_setuju.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_setuju.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_setuju.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_setujuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_setujuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_setujuMouseExited(evt);
            }
        });

        jLabel65.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel65.setText("Simpan");

        javax.swing.GroupLayout btn_setujuLayout = new javax.swing.GroupLayout(btn_setuju);
        btn_setuju.setLayout(btn_setujuLayout);
        btn_setujuLayout.setHorizontalGroup(
            btn_setujuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_setujuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel65)
                .addGap(20, 20, 20))
        );
        btn_setujuLayout.setVerticalGroup(
            btn_setujuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_setujuLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel65)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout pindah_kamarLayout = new javax.swing.GroupLayout(pindah_kamar);
        pindah_kamar.setLayout(pindah_kamarLayout);
        pindah_kamarLayout.setHorizontalGroup(
            pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pindah_kamarLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pindah_kamarLayout.createSequentialGroup()
                        .addComponent(txt_mkdkmr, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_setuju, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_cari4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(1205, Short.MAX_VALUE))
                    .addGroup(pindah_kamarLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(964, Short.MAX_VALUE))
                    .addGroup(pindah_kamarLayout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addContainerGap(1284, Short.MAX_VALUE))
                    .addGroup(pindah_kamarLayout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addContainerGap(1293, Short.MAX_VALUE))
                    .addGroup(pindah_kamarLayout.createSequentialGroup()
                        .addComponent(cmb_pdh, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pindah_kamarLayout.createSequentialGroup()
                        .addGroup(pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pindah_kamarLayout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(151, 151, 151)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pindah_kamarLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmb_pdh, txt_mkdkmr});

        pindah_kamarLayout.setVerticalGroup(
            pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pindah_kamarLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel42)
                .addGap(13, 13, 13)
                .addComponent(jLabel43)
                .addGap(45, 45, 45)
                .addComponent(jLabel44)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_cari4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_mkdkmr, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel45)
                .addGap(8, 8, 8)
                .addGroup(pindah_kamarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_setuju, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_pdh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(406, Short.MAX_VALUE))
        );

        pindah_kamarLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_cari4, btn_setuju, cmb_pdh, txt_mkdkmr});

        daftar_penghuni.setBackground(new java.awt.Color(255, 255, 255));
        daftar_penghuni.setMinimumSize(new java.awt.Dimension(800, 600));
        daftar_penghuni.setPreferredSize(new java.awt.Dimension(1464, 768));

        tb_phn.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_phn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tb_phn);

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel36.setText("Daftar penghuni");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(51, 51, 51));
        jLabel37.setText("Mari lihat siapa saja yang tinggal disini. Mungkin salah satunya orang terkenal.");

        btn_load1.setBackground(new java.awt.Color(192, 192, 192));
        btn_load1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_load1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_load1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_load1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_load1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_load1MouseExited(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel59.setText("Tampilkan data");

        javax.swing.GroupLayout btn_load1Layout = new javax.swing.GroupLayout(btn_load1);
        btn_load1.setLayout(btn_load1Layout);
        btn_load1Layout.setHorizontalGroup(
            btn_load1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_load1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel59)
                .addGap(20, 20, 20))
        );
        btn_load1Layout.setVerticalGroup(
            btn_load1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_load1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel59)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout daftar_penghuniLayout = new javax.swing.GroupLayout(daftar_penghuni);
        daftar_penghuni.setLayout(daftar_penghuniLayout);
        daftar_penghuniLayout.setHorizontalGroup(
            daftar_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(daftar_penghuniLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(daftar_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(daftar_penghuniLayout.createSequentialGroup()
                        .addComponent(btn_load1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(daftar_penghuniLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(daftar_penghuniLayout.createSequentialGroup()
                        .addGroup(daftar_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(daftar_penghuniLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        daftar_penghuniLayout.setVerticalGroup(
            daftar_penghuniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(daftar_penghuniLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel36)
                .addGap(13, 13, 13)
                .addComponent(jLabel37)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btn_load1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        daftar_pembayaran.setBackground(new java.awt.Color(255, 255, 255));
        daftar_pembayaran.setMinimumSize(new java.awt.Dimension(800, 600));
        daftar_pembayaran.setPreferredSize(new java.awt.Dimension(1464, 768));

        tb_byr.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_byr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tb_byr);

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel38.setText("Daftar pembayaran");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(51, 51, 51));
        jLabel39.setText("Periksa siapa saja yang sudah dan belum membayar biaya sewa.");

        btn_load3.setBackground(new java.awt.Color(192, 192, 192));
        btn_load3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_load3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_load3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_load3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_load3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_load3MouseExited(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setText("Tampilkan data");

        javax.swing.GroupLayout btn_load3Layout = new javax.swing.GroupLayout(btn_load3);
        btn_load3.setLayout(btn_load3Layout);
        btn_load3Layout.setHorizontalGroup(
            btn_load3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_load3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel61)
                .addGap(20, 20, 20))
        );
        btn_load3Layout.setVerticalGroup(
            btn_load3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_load3Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel61)
                .addGap(4, 4, 4))
        );

        cmb_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Belum lunas", "Lunas" }));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel69.setText("Status pembayaran");

        jLabel72.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel72.setText("Bulan");

        javax.swing.GroupLayout daftar_pembayaranLayout = new javax.swing.GroupLayout(daftar_pembayaran);
        daftar_pembayaran.setLayout(daftar_pembayaranLayout);
        daftar_pembayaranLayout.setHorizontalGroup(
            daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(daftar_pembayaranLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(daftar_pembayaranLayout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(daftar_pembayaranLayout.createSequentialGroup()
                        .addGroup(daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38)
                            .addGroup(daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, daftar_pembayaranLayout.createSequentialGroup()
                                    .addGroup(daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel69)
                                        .addComponent(cmb_status, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(15, 15, 15)
                                    .addGroup(daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel72)
                                        .addGroup(daftar_pembayaranLayout.createSequentialGroup()
                                            .addComponent(cmb_bulan2, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn_load3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(964, Short.MAX_VALUE))))
        );
        daftar_pembayaranLayout.setVerticalGroup(
            daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(daftar_pembayaranLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel38)
                .addGap(13, 13, 13)
                .addComponent(jLabel39)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(daftar_pembayaranLayout.createSequentialGroup()
                        .addGroup(daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel69)
                            .addComponent(jLabel72))
                        .addGap(10, 10, 10)
                        .addGroup(daftar_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_load3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_status, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cmb_bulan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        daftar_pembayaranLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_load3, cmb_bulan2, cmb_status});

        keluar_kost.setBackground(new java.awt.Color(255, 255, 255));
        keluar_kost.setMinimumSize(new java.awt.Dimension(800, 600));
        keluar_kost.setPreferredSize(new java.awt.Dimension(1464, 768));

        tb_keluar.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        tb_keluar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tb_keluar);

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel46.setText("Akhiri masa tinggal");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(51, 51, 51));
        jLabel47.setText("Sudah saatnya keluar? Semoga kelak bisa bertemu kembali.");

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setText("Masukkan nomor kamar");

        txt_kdkr1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txt_kdkr1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(92, 92, 92), 2));
        txt_kdkr1.setPreferredSize(new java.awt.Dimension(4, 25));

        btn_cari5.setBackground(new java.awt.Color(192, 192, 192));
        btn_cari5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_cari5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cari5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cari5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_cari5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_cari5MouseExited(evt);
            }
        });

        jLabel66.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel66.setText("Cari");

        javax.swing.GroupLayout btn_cari5Layout = new javax.swing.GroupLayout(btn_cari5);
        btn_cari5.setLayout(btn_cari5Layout);
        btn_cari5Layout.setHorizontalGroup(
            btn_cari5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_cari5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel66)
                .addGap(20, 20, 20))
        );
        btn_cari5Layout.setVerticalGroup(
            btn_cari5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_cari5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        btn_keluar.setBackground(new java.awt.Color(192, 192, 192));
        btn_keluar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(192, 192, 192), 2));
        btn_keluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_keluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_keluarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_keluarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_keluarMouseExited(evt);
            }
        });

        jLabel67.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel67.setText("Keluar");

        javax.swing.GroupLayout btn_keluarLayout = new javax.swing.GroupLayout(btn_keluar);
        btn_keluar.setLayout(btn_keluarLayout);
        btn_keluarLayout.setHorizontalGroup(
            btn_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_keluarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel67)
                .addGap(20, 20, 20))
        );
        btn_keluarLayout.setVerticalGroup(
            btn_keluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_keluarLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE)
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout keluar_kostLayout = new javax.swing.GroupLayout(keluar_kost);
        keluar_kost.setLayout(keluar_kostLayout);
        keluar_kostLayout.setHorizontalGroup(
            keluar_kostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(keluar_kostLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(keluar_kostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(keluar_kostLayout.createSequentialGroup()
                        .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(keluar_kostLayout.createSequentialGroup()
                        .addGroup(keluar_kostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel58)
                            .addComponent(jLabel46)
                            .addGroup(keluar_kostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(keluar_kostLayout.createSequentialGroup()
                                    .addComponent(txt_kdkr1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(btn_cari5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        keluar_kostLayout.setVerticalGroup(
            keluar_kostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(keluar_kostLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel46)
                .addGap(13, 13, 13)
                .addComponent(jLabel47)
                .addGap(45, 45, 45)
                .addComponent(jLabel58)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(keluar_kostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_keluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_kdkr1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(488, Short.MAX_VALUE))
        );

        keluar_kostLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btn_cari5, btn_keluar, txt_kdkr1});

        jLayeredPane2.setLayer(dashboard, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(tambah_penghuni, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(transaksi_pembayaran, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(status_kamar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(pindah_kamar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(daftar_penghuni, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(daftar_pembayaran, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(keluar_kost, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(tambah_penghuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(transaksi_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(daftar_penghuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(daftar_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(status_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(pindah_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(40, 40, 40)
                    .addComponent(keluar_kost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(tambah_penghuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(transaksi_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(daftar_penghuni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(daftar_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(status_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(pindah_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(keluar_kost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1494, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(leftside_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(leftside_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void cmb_pdhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_pdhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_pdhActionPerformed

    private void d_tambah_penghuniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_tambah_penghuniMouseEntered
        frameEnter(d_tambah_penghuni);
    }//GEN-LAST:event_d_tambah_penghuniMouseEntered

    private void d_tambah_penghuniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_tambah_penghuniMouseExited
        frameExit(d_tambah_penghuni);
    }//GEN-LAST:event_d_tambah_penghuniMouseExited

    private void d_transasksi_pembayaranMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_transasksi_pembayaranMouseEntered
        frameEnter(d_transasksi_pembayaran);
    }//GEN-LAST:event_d_transasksi_pembayaranMouseEntered

    private void d_transasksi_pembayaranMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_transasksi_pembayaranMouseExited
        frameExit(d_transasksi_pembayaran);
    }//GEN-LAST:event_d_transasksi_pembayaranMouseExited

    private void d_status_kamarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_status_kamarMouseEntered
        frameEnter(d_status_kamar);
    }//GEN-LAST:event_d_status_kamarMouseEntered

    private void d_status_kamarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_status_kamarMouseExited
        frameExit(d_status_kamar);
    }//GEN-LAST:event_d_status_kamarMouseExited

    private void d_data_penghuniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_data_penghuniMouseEntered
        frameEnter(d_data_penghuni);
    }//GEN-LAST:event_d_data_penghuniMouseEntered

    private void d_data_penghuniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_data_penghuniMouseExited
        frameExit(d_data_penghuni);
    }//GEN-LAST:event_d_data_penghuniMouseExited

    private void d_data_transaksiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_data_transaksiMouseEntered
        frameEnter(d_data_transaksi);
    }//GEN-LAST:event_d_data_transaksiMouseEntered

    private void d_data_transaksiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_data_transaksiMouseExited
        frameExit(d_data_transaksi);
    }//GEN-LAST:event_d_data_transaksiMouseExited

    private void d_pindah_kamarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pindah_kamarMouseEntered
        frameEnter(d_pindah_kamar);
    }//GEN-LAST:event_d_pindah_kamarMouseEntered

    private void d_pindah_kamarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pindah_kamarMouseExited
        frameExit(d_pindah_kamar);
    }//GEN-LAST:event_d_pindah_kamarMouseExited

    private void d_akhiriMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_akhiriMouseEntered
        frameEnter(d_akhiri);
    }//GEN-LAST:event_d_akhiriMouseEntered

    private void d_akhiriMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_akhiriMouseExited
        frameExit(d_akhiri);
    }//GEN-LAST:event_d_akhiriMouseExited

    //LEFTSIDE PANEL
    private void lp1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp1MouseEntered
        lpEnter(lp1);
    }//GEN-LAST:event_lp1MouseEntered

    private void lp2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp2MouseEntered
        lpEnter(lp2);
    }//GEN-LAST:event_lp2MouseEntered

    private void lp3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp3MouseEntered
        lpEnter(lp3);
    }//GEN-LAST:event_lp3MouseEntered

    private void lp4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp4MouseEntered
        lpEnter(lp4);
    }//GEN-LAST:event_lp4MouseEntered

    private void lp5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp5MouseEntered
        lpEnter(lp5);
    }//GEN-LAST:event_lp5MouseEntered

    private void lp6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp6MouseEntered
        lpEnter(lp6);
    }//GEN-LAST:event_lp6MouseEntered

    private void lp8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp8MouseEntered
        lpEnter(lp8);
    }//GEN-LAST:event_lp8MouseEntered

    private void lp9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp9MouseEntered
        lpEnter(lp9);
    }//GEN-LAST:event_lp9MouseEntered

    private void lp1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp1MouseExited
        lpExit(lp1);
    }//GEN-LAST:event_lp1MouseExited

    private void lp2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp2MouseExited
        lpExit(lp2);
    }//GEN-LAST:event_lp2MouseExited

    private void lp3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp3MouseExited
        lpExit(lp3);
    }//GEN-LAST:event_lp3MouseExited

    private void lp4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp4MouseExited
        lpExit(lp4);
    }//GEN-LAST:event_lp4MouseExited

    private void lp5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp5MouseExited
        lpExit(lp5);
    }//GEN-LAST:event_lp5MouseExited

    private void lp6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp6MouseExited
        lpExit(lp6);
    }//GEN-LAST:event_lp6MouseExited

    private void lp8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp8MouseExited
        lpExit(lp8);
    }//GEN-LAST:event_lp8MouseExited

    private void lp9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp9MouseExited
        lpExit(lp9);
    }//GEN-LAST:event_lp9MouseExited

    private void d_tambah_penghuniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_tambah_penghuniMouseClicked
        tambah_penghuni();
    }//GEN-LAST:event_d_tambah_penghuniMouseClicked

    private void d_transasksi_pembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_transasksi_pembayaranMouseClicked
        transaksi_pembayaran();
    }//GEN-LAST:event_d_transasksi_pembayaranMouseClicked

    private void d_status_kamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_status_kamarMouseClicked
        status_kamar();
    }//GEN-LAST:event_d_status_kamarMouseClicked

    private void d_data_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_data_transaksiMouseClicked
        daftar_pembayaran();
    }//GEN-LAST:event_d_data_transaksiMouseClicked

    private void d_akhiriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_akhiriMouseClicked
        keluar_kost();
    }//GEN-LAST:event_d_akhiriMouseClicked

    private void d_pindah_kamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_pindah_kamarMouseClicked
        pindah_kamar();
    }//GEN-LAST:event_d_pindah_kamarMouseClicked

    private void d_data_penghuniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d_data_penghuniMouseClicked
        daftar_penghuni();
    }//GEN-LAST:event_d_data_penghuniMouseClicked

    private void lp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp1MouseClicked
        dashboard();
    }//GEN-LAST:event_lp1MouseClicked

    private void lp2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp2MouseClicked
        tambah_penghuni();
    }//GEN-LAST:event_lp2MouseClicked

    private void lp3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp3MouseClicked
        transaksi_pembayaran();
    }//GEN-LAST:event_lp3MouseClicked

    private void lp4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp4MouseClicked
        status_kamar();
    }//GEN-LAST:event_lp4MouseClicked

    private void lp5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp5MouseClicked
        daftar_penghuni();
    }//GEN-LAST:event_lp5MouseClicked

    private void lp6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp6MouseClicked
        daftar_pembayaran();
    }//GEN-LAST:event_lp6MouseClicked

    private void lp8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp8MouseClicked
        pindah_kamar();
    }//GEN-LAST:event_lp8MouseClicked

    private void lp9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lp9MouseClicked
        keluar_kost();
    }//GEN-LAST:event_lp9MouseClicked

    private void cmb_no_kamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_no_kamarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_no_kamarActionPerformed

    private void btn_save1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_save1MouseClicked
        String nik = txt_nik.getText(), nama = txt_nama.getText(), alamat = txt_alamat.getText(), kontak = txt_kontak.getText(), kamar = String.valueOf(cmb_no_kamar.getSelectedItem());

        if (nik.isEmpty() || nama.isEmpty() || alamat.isEmpty() || kontak.isEmpty() || kamar.isEmpty() || kamar.equals("Lantai 1") || kamar.equals("Lantai 2") || kamar.equals("Lantai 3")) {
            if (nik.isEmpty()) {
                txt_nik.requestFocus();
                txt_nik.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else if (nama.isEmpty()) {
                txt_nama.requestFocus();
                txt_nama.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else if (alamat.isEmpty()) {
                txt_alamat.requestFocus();
                txt_alamat.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else if (kontak.isEmpty()) {
                txt_kontak.requestFocus();
                txt_kontak.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else if (kamar.isEmpty() || kamar.equals("Lantai 1") || kamar.equals("Lantai 2") || kamar.equals("Lantai 3")) {
                JOptionPane.showMessageDialog(null, "Mohon pilih nomor kamar dengan benar!", "PERHATIAN!", JOptionPane.ERROR_MESSAGE);
                cmb_no_kamar.requestFocus();
            }
        } else {
            m1(nik, nama, kontak, alamat, kamar);

            txt_nik.setText("");
            txt_nama.setText("");
            txt_alamat.setText("");
            txt_kontak.setText("");
            cmb_no_kamar.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btn_save1MouseClicked

    private void txt_kontakKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kontakKeyTyped
        acceptNUMBER(txt_kontak);
    }//GEN-LAST:event_txt_kontakKeyTyped

    private void txt_nikKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nikKeyTyped
        acceptNUMBER(txt_nik);
    }//GEN-LAST:event_txt_nikKeyTyped

    private void txt_namaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namaKeyTyped
        acceptLETTER(txt_nama);
    }//GEN-LAST:event_txt_namaKeyTyped

    private void txt_kdkrKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kdkrKeyTyped
        acceptNUMBER(txt_kdkr);
    }//GEN-LAST:event_txt_kdkrKeyTyped

    private void txt_tghKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_tghKeyTyped
        acceptNUMBER(txt_tgh);
    }//GEN-LAST:event_txt_tghKeyTyped

    private void btn_load1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load1MouseClicked
        m4();
    }//GEN-LAST:event_btn_load1MouseClicked

    private void btn_load2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load2MouseClicked
        get_kamar_tb(Integer.parseInt(String.valueOf(cmb_lantai.getSelectedItem())), cmb_kondisi.getSelectedIndex());
    }//GEN-LAST:event_btn_load2MouseClicked

    private void btn_load3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load3MouseClicked
        String xx = String.valueOf(cmb_bulan2.getSelectedItem());
        String bln = xx.substring(0, xx.indexOf(" "));
        int thn = Integer.parseInt(xx.substring(xx.indexOf(" ") + 1));
        if (cmb_status.getSelectedItem().equals("Belum lunas")) {
            m5(0, bln, thn);
        } else if (cmb_status.getSelectedItem().equals("Lunas")) {
            m5(1, bln, thn);
        }
    }//GEN-LAST:event_btn_load3MouseClicked

    private void btn_cari3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari3MouseClicked
        if (txt_kdkr.getText().isEmpty()) {
            txt_kdkr.requestFocus();
            txt_kdkr.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else {
            int kdkr = Integer.parseInt(txt_kdkr.getText());
            get_trans_tb(kdkr);
            get_bln_cmb1(tmp);
        }
    }//GEN-LAST:event_btn_cari3MouseClicked

    private void btn_bayarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bayarMouseClicked
        int id_trans = id_bln1, id_phn = tmp, status = 1;
        String tgl_bayar = txt_tglbyr.getText();

        if (txt_tgh.getText().isEmpty() || txt_kdkr.getText().isEmpty() || id_trans == 0 && tgl_bayar.isEmpty()) {
            if (txt_tgh.getText().isEmpty()) {
                txt_tgh.requestFocus();
                txt_tgh.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else if (txt_kdkr.getText().isEmpty() || id_trans == 0) {
                txt_kdkr.requestFocus();
                txt_kdkr.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
        } else {
            if (id_bln1 == 0) {
                JOptionPane.showMessageDialog(null, "Anda tidak memiliki tagihan!");
            } else {
                String xx = String.valueOf(cmb_bulan.getSelectedItem());
                String bln = xx.substring(0, xx.indexOf(" "));
                int thn = Integer.parseInt(xx.substring(xx.indexOf(" ") + 1));

                int tagihan = Integer.parseInt(txt_tgh.getText());
                m2(id_phn, tgl_bayar, tagihan, status, bln, thn);
            }

            txt_kdkr.setText("");
            txt_tgh.setText("");
            txt_tglbyr.setText("");
            cmb_bulan.removeAllItems();
            tb_dttrans.setModel(new DefaultTableModel());
        }
    }//GEN-LAST:event_btn_bayarMouseClicked

    private void btn_cari4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari4MouseClicked
        if (txt_mkdkmr.getText().isEmpty()) {
            txt_mkdkmr.requestFocus();
            txt_mkdkmr.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else {
            int kdkr = Integer.parseInt(txt_mkdkmr.getText());
            get_pdh_tb(kdkr);
        }
    }//GEN-LAST:event_btn_cari4MouseClicked

    private void btn_setujuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_setujuMouseClicked
        String id = txt_mkdkmr.getText(), kamar = String.valueOf(cmb_pdh.getSelectedItem());

        if (id.isEmpty() || kamar.isEmpty() || kamar.equals("Lantai 1") || kamar.equals("Lantai 2") || kamar.equals("Lantai 3")) {
            if (id.isEmpty()) {
                txt_mkdkmr.requestFocus();
                txt_mkdkmr.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            } else if (kamar.isEmpty() || kamar.equals("Lantai 1") || kamar.equals("Lantai 2") || kamar.equals("Lantai 3")) {
                JOptionPane.showMessageDialog(null, "Mohon pilih nomor kamar dengan benar!", "PERHATIAN!", JOptionPane.ERROR_MESSAGE);
                cmb_pdh.requestFocus();
            }
        } else {
            m6(tmp2, Integer.parseInt(kamar));

            tb_pk.setModel(new DefaultTableModel());
            txt_mkdkmr.setText("");
            cmb_pdh.removeAllItems();
        }
    }//GEN-LAST:event_btn_setujuMouseClicked

    private void btn_cari5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari5MouseClicked
        if (txt_kdkr1.getText().isEmpty()) {
            txt_kdkr1.requestFocus();
            txt_kdkr1.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else {
            int kode = Integer.parseInt(txt_kdkr1.getText());
            get_keluar(kode);
        }
    }//GEN-LAST:event_btn_cari5MouseClicked

    private void btn_keluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_keluarMouseClicked
        if (txt_kdkr1.getText().isEmpty() || tmp2 == 0) {
            txt_kdkr1.requestFocus();
            txt_kdkr1.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        } else {
            m7(tmp2);
            txt_kdkr1.setText("");
            tb_keluar.setModel(new DefaultTableModel());
        }
    }//GEN-LAST:event_btn_keluarMouseClicked

    private void btn_cari5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari5MouseEntered
        btnNear(btn_cari5);
    }//GEN-LAST:event_btn_cari5MouseEntered

    private void btn_keluarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_keluarMouseEntered
        btnNear(btn_keluar);
    }//GEN-LAST:event_btn_keluarMouseEntered

    private void btn_keluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_keluarMouseExited
        btnFar(btn_keluar);
    }//GEN-LAST:event_btn_keluarMouseExited

    private void btn_cari5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari5MouseExited
        btnFar(btn_cari5);
    }//GEN-LAST:event_btn_cari5MouseExited

    private void btn_load3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load3MouseEntered
        btnNear(btn_load3);
    }//GEN-LAST:event_btn_load3MouseEntered

    private void btn_load3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load3MouseExited
        btnFar(btn_load3);
    }//GEN-LAST:event_btn_load3MouseExited

    private void btn_load1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load1MouseEntered
        btnNear(btn_load1);
    }//GEN-LAST:event_btn_load1MouseEntered

    private void btn_load1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load1MouseExited
        btnFar(btn_load1);
    }//GEN-LAST:event_btn_load1MouseExited

    private void btn_cari4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari4MouseEntered
        btnNear(btn_cari4);
    }//GEN-LAST:event_btn_cari4MouseEntered

    private void btn_cari4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari4MouseExited
        btnFar(btn_cari4);
    }//GEN-LAST:event_btn_cari4MouseExited

    private void btn_setujuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_setujuMouseEntered
        btnNear(btn_setuju);
    }//GEN-LAST:event_btn_setujuMouseEntered

    private void btn_setujuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_setujuMouseExited
        btnFar(btn_setuju);
    }//GEN-LAST:event_btn_setujuMouseExited

    private void btn_load2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load2MouseEntered
        btnNear(btn_load2);
    }//GEN-LAST:event_btn_load2MouseEntered

    private void btn_load2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_load2MouseExited
        btnFar(btn_load2);
    }//GEN-LAST:event_btn_load2MouseExited

    private void btn_cari3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari3MouseEntered
        btnNear(btn_cari3);
    }//GEN-LAST:event_btn_cari3MouseEntered

    private void btn_cari3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cari3MouseExited
        btnFar(btn_cari3);
    }//GEN-LAST:event_btn_cari3MouseExited

    private void btn_bayarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bayarMouseEntered
        btnNear(btn_bayar);
    }//GEN-LAST:event_btn_bayarMouseEntered

    private void btn_bayarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_bayarMouseExited
        btnFar(btn_bayar);
    }//GEN-LAST:event_btn_bayarMouseExited

    private void btn_save1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_save1MouseEntered
        btnNear(btn_save1);
    }//GEN-LAST:event_btn_save1MouseEntered

    private void btn_save1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_save1MouseExited
        btnFar(btn_save1);
    }//GEN-LAST:event_btn_save1MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws RemoteException, NotBoundException, MalformedURLException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn_bayar;
    private javax.swing.JPanel btn_cari3;
    private javax.swing.JPanel btn_cari4;
    private javax.swing.JPanel btn_cari5;
    private javax.swing.JPanel btn_keluar;
    private javax.swing.JPanel btn_load1;
    private javax.swing.JPanel btn_load2;
    private javax.swing.JPanel btn_load3;
    private javax.swing.JPanel btn_save1;
    private javax.swing.JPanel btn_setuju;
    private javax.swing.JComboBox<String> cmb_bulan;
    private javax.swing.JComboBox<String> cmb_bulan2;
    private javax.swing.JComboBox<String> cmb_kondisi;
    private javax.swing.JComboBox<String> cmb_lantai;
    private javax.swing.JComboBox<String> cmb_no_kamar;
    private javax.swing.JComboBox<String> cmb_pdh;
    private javax.swing.JComboBox<String> cmb_status;
    private javax.swing.JPanel d_akhiri;
    private javax.swing.JPanel d_data_penghuni;
    private javax.swing.JPanel d_data_transaksi;
    private javax.swing.JPanel d_pindah_kamar;
    private javax.swing.JPanel d_status_kamar;
    private javax.swing.JPanel d_tambah_penghuni;
    private javax.swing.JPanel d_transasksi_pembayaran;
    private javax.swing.JPanel daftar_pembayaran;
    private javax.swing.JPanel daftar_penghuni;
    private javax.swing.JPanel dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    public javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel keluar_kost;
    private javax.swing.JPanel leftside_panel;
    private javax.swing.JPanel lp1;
    private javax.swing.JPanel lp2;
    private javax.swing.JPanel lp3;
    private javax.swing.JPanel lp4;
    private javax.swing.JPanel lp5;
    private javax.swing.JPanel lp6;
    private javax.swing.JPanel lp7;
    private javax.swing.JPanel lp8;
    private javax.swing.JPanel lp9;
    private javax.swing.JPanel pindah_kamar;
    private javax.swing.JPanel status_kamar;
    private javax.swing.JPanel tambah_penghuni;
    private javax.swing.JTable tb_byr;
    private javax.swing.JTable tb_dttrans;
    private javax.swing.JTable tb_kamar;
    private javax.swing.JTable tb_keluar;
    private javax.swing.JTable tb_phn;
    private javax.swing.JTable tb_pk;
    private javax.swing.JPanel transaksi_pembayaran;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_kdkr;
    private javax.swing.JTextField txt_kdkr1;
    private javax.swing.JTextField txt_kontak;
    private javax.swing.JTextField txt_mkdkmr;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nik;
    private javax.swing.JTextField txt_tgh;
    private javax.swing.JTextField txt_tglbyr;
    // End of variables declaration//GEN-END:variables
}
