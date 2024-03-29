// import library java.sql yang dibutuhkan
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// import library java.util yang dibutuhkan
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

// CLASS MENU PESANAN
public class pesanan {

    // migration : Method untuk membuat tabel pesanan
    public static void buatTabel() {
        rincianPesanan.buatTabel();
        String sql = ""
            + "CREATE TABLE IF NOT EXISTS pesanan ("
            + "  id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "  id_pelanggan INTEGER,"
            + "  nomor VARCHAR(25) UNIQUE,"
            + "  status VARCHAR(255),"
            + "  nilai_pesanan INTEGER,"
            + "  FOREIGN KEY (id_pelanggan) REFERENCES pelanggan (id)"
            + ")";
        try {
            Statement stmt = util.koneksiDB().createStatement();
            stmt.execute(sql);
            System.out.println("Tabel pesanan berhasil dibuat...");
        } catch (Exception e) {
            System.out.println("Gagal membuat tabel pesanan : " + e.getMessage());
            System.exit(1);
        }
    }

    // Menu Pesanan
    public static void menu() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("💰 Pesanan");
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("1. Tampilkan Daftar Pesanan 📋");
        System.out.println("2. Lihat Rincian Pesanan 🗂️");
        System.out.println("3. Tambah Pesanan ➕");
        System.out.println("4. Ubah Pesanan 📝");
        System.out.println("5. Hapus Pesanan ❌");
        System.out.println();
        System.out.println("0. Kembali Ke Menu Utama ⬅️");
        System.out.println();
        System.out.print("Silakan pilih menu (0-5) : ");
        switch (util.bacaInput()) {
            case "1": tampilkan(true); break;
            case "2": tampilkanRincian(); break;
            case "3": tambah(); break;
            case "4": ubah(); break;
            case "5": hapus(); break;
            case "0": restoran.menuUtama(); break;
            default: 
                System.out.println();
                System.out.println("Nomor menu tidak valid! silakan pilih menu dengan angka 0 sampai 5"); 
                menu(); break;
        }
    }

    public static void tampilkan(Boolean navigasi) {
        System.out.println();
        if (navigasi) {
            System.out.println("📋 Daftar Pesanan");
        }
        ArrayList<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
        String sql = ""
            + "SELECT "
            + "  p.nomor nomor, "
            + "  pel.nama pelanggan, "
            + "  p.nilai_pesanan nilai, "
            + "  p.status status "
            + "FROM "
            + "  pesanan p "
            + "  JOIN pelanggan pel ON pel.id = p.id_pelanggan "
            + "ORDER BY "
            + "  p.nomor ASC";
        try (ResultSet rs = util.koneksiDB().createStatement().executeQuery(sql)) {
            while (rs.next()) {
                LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
                data.put("No.", rs.getString("nomor"));
                data.put("Pelanggan", rs.getString("pelanggan"));
                data.put("Nilai Pesanan", util.formatAngka(rs.getInt("nilai")));
                data.put("Status", rs.getString("status"));
                list.add(data);
            }
            
            if (list.isEmpty()) {
            // Jika data kosong, tampilkan pesan khusus
                System.out.println("Data Pesanan Kosong. Silahkan isi data pelanggan terlebih dahulu.");
            } else {
                util.tampilkanData(list);
            }
            
            if (navigasi){
                util.tahan("Silahkan Tekan Enter ⏎ Untuk Kembali Ke Menu Pesanan...");
            }
            
        } catch (Exception e) {
            System.out.println();
            System.out.println("Gagal menampilkan daftar pesanan : " + e.getMessage());
        }
        if (navigasi) {
            menu();
        }
    }

    public static void tampilkanRincian() {
        System.out.println();
        System.out.println("🗂️Lihat Rincian Pesanan");
        Integer id = pilih();
        if (id==0) {
            menu();
        }
        String sql1 = "SELECT p.nomor, pel.nama pelanggan, p.status FROM pesanan p JOIN pelanggan pel ON pel.id = p.id_pelanggan WHERE p.id = ?";
        String sql2 = "SELECT p.nama produk, r.catatan, r.jumlah, p.harga FROM rincian_pesanan r JOIN produk p ON p.id = r.id_produk WHERE r.id_pesanan = ?";
        try (
            PreparedStatement ps1 = util.koneksiDB().prepareStatement(sql1);
            PreparedStatement ps2 = util.koneksiDB().prepareStatement(sql2);
        ) {
            ps1.setInt(1, id);
            ResultSet rs1 = ps1.executeQuery();
            String nomor = "";
            String status = "";
            String namaPelanggan = "";
            while (rs1.next()) {
                nomor = rs1.getString("nomor");
                status = rs1.getString("status");
                namaPelanggan = rs1.getString("pelanggan");
            }

            ps2.setInt(1, id);
            ResultSet rs2 = ps2.executeQuery();
            ArrayList<LinkedHashMap<String, String>> rincian = new ArrayList<LinkedHashMap<String, String>>();
            while (rs2.next()) {
                LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
                data.put("Menu", rs2.getString("produk"));
                data.put("Catatan", rs2.getString("catatan"));
                data.put("Jumlah", util.formatAngka(rs2.getInt("jumlah")));
                data.put("Harga Satuan", util.formatAngka(rs2.getInt("harga")));
                data.put("Subtotal", util.formatAngka(rs2.getInt("jumlah")*rs2.getInt("harga")));
                rincian.add(data);
            }
            cetakRincian(nomor, status, namaPelanggan, rincian);
            
        } catch (Exception e) {
            System.out.println();
            System.out.println("Gagal menampilkan rincian pesanan : " + e.getMessage());
        }

        menu();
    }

    public static void tambah() {
        System.out.println();
        System.out.println("➕ Tambah Data Pesanan");
        String nomor = nomorBaru();
        Integer idPelanggan = pelanggan.pilih();
        if (idPelanggan==0) {
            menu();
        }
        ArrayList<LinkedHashMap<String, String>> rincian = new ArrayList<LinkedHashMap<String, String>>();
        rincian = tambahRincian(nomor, idPelanggan, rincian);

        String status = inputStatus();
        Integer nilaiPesanan = hitungNilai(rincian);
        String sql1 = "INSERT INTO pesanan (id_pelanggan, nomor, status, nilai_pesanan) VALUES (?, ?, ?, ?)";
        String sql2 = "INSERT INTO rincian_pesanan (id_pesanan, id_produk, jumlah, catatan) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps1 = util.koneksiDB().prepareStatement(sql1)) {
            ps1.setInt(1, idPelanggan);
            ps1.setString(2, nomor);
            ps1.setString(3, status);
            ps1.setInt(4, nilaiPesanan);
            ps1.executeUpdate();
            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                Integer idPesanan = rs.getInt(1);
                for (LinkedHashMap<String, String> data : rincian) {
                    PreparedStatement ps2 = util.koneksiDB().prepareStatement(sql2);
                    ps2.setInt(1, idPesanan);
                    ps2.setInt(2, util.toInteger(data.get("idProduk")));
                    ps2.setInt(3, util.toInteger(data.get("jumlah")));
                    ps2.setString(4, data.get("catatan"));
                    ps2.executeUpdate();
                }
            }
            System.out.println();
            System.out.println("Data pesanan berhasil ditambahkan!");
            System.out.println();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Gagal menambah data pesanan : " + e.getMessage());
        }
        menu();
    }

    public static void ubah() {
        System.out.println();
        System.out.println("📝 Ubah Data Pesanan");
        Integer id = pilih();
        if (id==0) {
            menu();
        }
        String nomor = inputNomorBaru(id);
        Integer idPelanggan = pelanggan.pilih();
        if (idPelanggan==0) {
            menu();
        }
        ArrayList<LinkedHashMap<String, String>> rincian = new ArrayList<LinkedHashMap<String, String>>();
        rincian = tambahRincian(nomor, idPelanggan, rincian);
        String status = inputStatus();
        Integer nilaiPesanan = hitungNilai(rincian);
        String sql1 = "UPDATE pesanan SET id_pelanggan = ?, nomor = ?, status = ?, nilai_pesanan = ? WHERE id = ?";
        String sql2 = "DELETE FROM rincian_pesanan WHERE id_pesanan = ?";
        String sql3 = "INSERT INTO rincian_pesanan (id_pesanan, id_produk, jumlah, catatan) VALUES (?, ?, ?, ?)";
        try (
            PreparedStatement ps1 = util.koneksiDB().prepareStatement(sql1);
            PreparedStatement ps2 = util.koneksiDB().prepareStatement(sql2);
        ) {
            ps1.setInt(1, idPelanggan);
            ps1.setString(2, nomor);
            ps1.setString(3, status);
            ps1.setInt(4, nilaiPesanan);
            ps1.setInt(5, id);
            ps1.executeUpdate();

            ps2.setInt(1, id);
            ps2.executeUpdate();

            for (LinkedHashMap<String, String> data : rincian) {
                PreparedStatement ps3 = util.koneksiDB().prepareStatement(sql3);
                ps3.setInt(1, id);
                ps3.setInt(2, util.toInteger(data.get("idProduk")));
                ps3.setInt(3, util.toInteger(data.get("jumlah")));
                ps3.setString(4, data.get("catatan"));
                ps3.executeUpdate();
            }
            System.out.println();
            System.out.println("Data pesanan berhasil diubah!");
            System.out.println();
        } catch (Exception e) {
            System.out.println();
            System.out.println("Gagal merubah data pesanan : " + e.getMessage());
        }
        menu();
    }

    public static void hapus() {
        System.out.println();
        System.out.println("❌ Hapus Data Pesanan");
        Integer id = pilih();
        if (id==0) {
            menu();
        }
        String sql1 = "DELETE FROM rincian_pesanan WHERE id_pesanan = ?";
        String sql2 = "DELETE FROM pesanan WHERE id = ?";
        try (
            PreparedStatement ps1 = util.koneksiDB().prepareStatement(sql1);
            PreparedStatement ps2 = util.koneksiDB().prepareStatement(sql2);
        ) {
            ps1.setInt(1, id);
            ps1.executeUpdate();
            ps2.setInt(1, id);
            ps2.executeUpdate();
            System.out.println();
            System.out.println("Data pesanan berhasil dihapus!");
        } catch (Exception e) {
            System.out.println();
            System.out.println("Gagal menghapus data pesanan : " + e.getMessage());
        }
        menu();
    }

    // Pilihan nomor pesanan    
    public static Integer pilih() {
        tampilkan(false);
        System.out.print("Masukan Nomor Pesanan : "); String nomor = util.bacaInput();
        if (nomor.equals("0")) {
            return 0;
        }

        Integer id = getIdByNomor(nomor);
        if (id > 0) {
            return id;
        }
        System.out.println("Nomor pesanan tidak ditemukan! silakan isi sesuai nomor pesanan yang valid atau isi 0 untuk membatalkan"); 
        return pilih();
    }

    private static ArrayList<LinkedHashMap<String, String>> tambahRincian(String nomor, Integer idPelanggan, ArrayList<LinkedHashMap<String, String>> rincian) {
        Integer idProduk = produk.pilih();
        if (idProduk==0) {
            menu();
        }
        Integer jumlah = inputJumlah();
        String catatan = inputCatatan();

        LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
        data.put("idProduk", idProduk.toString());
        data.put("jumlah", jumlah.toString());
        data.put("catatan", catatan);
        rincian.add(data);

        return menuTambahRincian(nomor, idPelanggan, rincian);
    }

    private static ArrayList<LinkedHashMap<String, String>> menuTambahRincian(String nomor, Integer idPelanggan, ArrayList<LinkedHashMap<String, String>> rincian) {
        System.out.println();
        System.out.println("1. Lanjutkan ➡️");
        System.out.println("2. Lihat Rincian 🗂️");
        System.out.println("3. Tambah Menu ➕");
        System.out.println("4. Batal ❌");
        System.out.println();
        System.out.print("Silakan pilih menu (1-4) : "); String pilihan = util.bacaInput();
        if (pilihan.equals("2")) {
            return draftRincian(nomor, idPelanggan, rincian);
        } else if (pilihan.equals("3")) {
            return tambahRincian(nomor, idPelanggan, rincian);
        } else if (pilihan.equals("4")) {
            menu();
        }
        return rincian;
    }

    private static ArrayList<LinkedHashMap<String, String>> draftRincian(String nomor, Integer idPelanggan, ArrayList<LinkedHashMap<String, String>> rincian) {
        String namaPelanggan = pelanggan.getNamaById(idPelanggan);

        ArrayList<LinkedHashMap<String, String>> rincianCetak = new ArrayList<LinkedHashMap<String, String>>();
        for (LinkedHashMap<String, String> data : rincian) {
            LinkedHashMap<String, String> dataCetak = new LinkedHashMap<String, String>();

            Integer jumlah = util.toInteger(data.get("jumlah"));
            Integer harga = produk.getHargaById(util.toInteger(data.get("idProduk")));

            dataCetak.put("Menu", produk.getNamaById(util.toInteger(data.get("idProduk"))));
            dataCetak.put("Catatan", data.get("catatan"));
            dataCetak.put("Jumlah", util.formatAngka(jumlah));
            dataCetak.put("Harga Satuan", util.formatAngka(harga));
            dataCetak.put("Subtotal", util.formatAngka(jumlah*harga));
            rincianCetak.add(dataCetak);
        }

        cetakRincian(nomor, "Draft", namaPelanggan, rincianCetak);
        return menuTambahRincian(nomor, idPelanggan, rincian);
    }

    // inputan nomor pesanan
    private static String inputNomorBaru(Integer idLama) {
        System.out.print("Masukan Nomor pesanan Baru : "); String nomor = util.bacaInput();
        if (nomor.equals("0")) {
            menu();
        }
        if (!util.isValidNumber(nomor)) {
            System.out.println("Nomor harus berupa angka! silakan isi dengan angka yang valid");
            return inputNomorBaru(idLama);
        }

        Integer id = getIdByNomor(nomor);
        if (id>0 && id!=idLama) {
            System.out.println("Nomor sudah digunakan! silakan isi dengan angka yang lain atau isi 0 untuk membatalkan");
            return inputNomorBaru(idLama);
        }
        return nomor;
    }

    // inputan status pesanan
    private static String inputStatus() {
        System.out.print("Masukan Status : "); String status = util.bacaInput();
        return status;
    }

    // inputan jumlah pesanan
    private static Integer inputJumlah() {
        System.out.print("Masukan Jumlah : "); String jumlah = util.bacaInput();
        return util.toInteger(jumlah);
    }

    // inputan catatan pesanan
    private static String inputCatatan() {
        System.out.print("Masukan Catatan : "); String catatan = util.bacaInput();
        return catatan;
    }

    private static Integer hitungNilai(ArrayList<LinkedHashMap<String, String>> rincian) {
        Integer total = 0;
        for (LinkedHashMap<String, String> data : rincian) {
            total += util.toInteger(data.get("jumlah")) * produk.getHargaById(util.toInteger(data.get("idProduk")));
        }
        return total;
    }

    private static String nomorBaru() {
        Integer nomor = 0;
        // untuk mencari nomor pesanan terbesar dari tabel pesanan
        String sql = "SELECT MAX(nomor) nomor FROM pesanan";
        try (ResultSet rs = util.koneksiDB().createStatement().executeQuery(sql)) {
            while (rs.next()) {
                nomor = rs.getInt("nomor");
            }
        } catch (Exception e) {}
        Integer baru = nomor + 1;
        return baru.toString();
    }

    private static Integer getIdByNomor(String nomor) {
        Integer id = 0;
        // Untuk mencari id pesanan dari tabel pesanan dimana nomor pesanan = inputan (hanya satu baris(limit1))
        String sql = "SELECT id FROM pesanan WHERE nomor = ? LIMIT 1";
        try (PreparedStatement ps = util.koneksiDB().prepareStatement(sql)) {
            ps.setString(1, nomor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {}

        return id;
    }

    private static void cetakRincian(String nomor, String status, String pelanggan, ArrayList<LinkedHashMap<String, String>> rincian) {
        System.out.println();
        System.out.println("Nomor     : " + nomor);
        System.out.println("Status    : " + status);
        System.out.println("Pelanggan : " + pelanggan);

        util.tampilkanData(rincian);

        Integer total = 0;
        for (LinkedHashMap<String, String> map : rincian) {
            total += util.toInteger(map.get("Subtotal").replace(".", ""));
        }

        LinkedHashMap<String, Integer> charLength = util.getTableCharLength(rincian);

        System.out.print("| Total     ");
        for (String key : charLength.keySet()) {
            if (key == "Subtotal") {
                System.out.print("|");
                util.cetakCell(charLength.get(key), util.formatAngka(total));
            } else if (key != "Jumlah") {
                for (Integer i = 0; i < (charLength.get(key) + 2); i++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println("|");

        System.out.print("+");
        for (String key : charLength.keySet()) {
            String sign = (key == "Subtotal") ? "+" : "-";
            if (key != "Menu") {
                System.out.print(sign);
            }
            for (Integer i = 0; i < (charLength.get(key) + 2); i++) {
                System.out.print("-");
            }
        }        
        System.out.println("+");
    }
}


// CLASS RINCIAN PESANAN
class rincianPesanan {

    // migration : Method untuk membuat tabel rincian pesanan 
    public static void buatTabel() {
        String sql = ""
            + "CREATE TABLE IF NOT EXISTS rincian_pesanan ("
            + "  id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "  id_pesanan INTEGER,"
            + "  id_produk INTEGER,"
            + "  jumlah INTEGER,"
            + "  catatan VARCHAR(255),"
            + "  FOREIGN KEY (id_pesanan) REFERENCES pesanan (id),"
            + "  FOREIGN KEY (id_produk) REFERENCES menu (id)"
            + ")";
        try {
            Statement stmt = util.koneksiDB().createStatement();
            stmt.execute(sql);
            System.out.println("Tabel kelompok_produk berhasil dibuat...");
        } catch (Exception e) {
            System.out.println("Gagal membuat tabel kelompok_produk : " + e.getMessage());
            System.exit(1);
        }
    }
}