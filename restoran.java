// ClASS UTAMA
public class restoran {

    public static void main(String[] args) {
        System.out.println("Menyiapkan data...");

        // migration : Method untuk membuat tabel dari semua class menu
        pelanggan.buatTabel(); // buat tabel pelanggan
        produk.buatTabel(); // buat tabel produk
        pesanan.buatTabel(); // buat tabel pesanan

        // seeder : Method untuk membuat atau menambahkan data awal class menu
        produk.buatDataAwal(); // buat data awal produk

        // mulai
        System.out.println();
        System.out.println("+--------------------------------------------+");
        System.out.println("|         Aplikasi Manajemen Restoran        |");
        System.out.println("+--------------------------------------------+");
        System.out.println("|            Tugas Pemrograman 3             |");
        System.out.println("|               Final Project                |");
        System.out.println("|                                            |");
        System.out.println("| 202243500497 - Alfarobby                   |");
        System.out.println("| 202243500500 - Ahmad Badawi                |");
        System.out.println("| 202243500501 - Abdur Rosyid Fachriansyah   |");
        System.out.println("| 202243500502 - Sangga Buana                |");
        System.out.println("| 202243500505 - Taufik Ramadhan             |");
        System.out.println("| 202243500524 - Riyan Rizaldy               |");
        System.out.println("+--------------------------------------------+");
        
        util.tahan("Silahkan Tekan Enter ⏎ Untuk Memulai Aplikasi...");
        
        // mulai navigasi
        menuUtama();
    }

    public static void menuUtama() {
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("🏠 Menu Utama");
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("1. Pesanan 💰");
        System.out.println("2. Data Pelanggan 👤");
        System.out.println("3. Data Menu 🍝");
        System.out.println();
        System.out.println("0. Keluar ⬅️");
        System.out.println();
        System.out.print("Silakan pilih menu (0-3) : ");

        switch (util.bacaInput()) {
            case "1": pesanan.menu(); break;
            case "2": pelanggan.menu(); break;
            case "3": produk.menu(); break;
            case "0": 
                System.out.println();
                System.out.println("Terima Kasih..."); 
                System.exit(0); break;
            default: 
                System.out.println();
                System.out.println("Nomor menu tidak valid! silakan pilih menu dengan angka 0 sampai 3"); 
                menuUtama(); break;
        }
    }
}