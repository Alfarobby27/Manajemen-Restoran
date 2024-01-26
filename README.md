# **Aplikasi Manajemen Restoran**
Ini adalah aplikasi CRUD sederhana yang dibuat menggunakan Java dan SQLite3.

## Anggota
| NPM          | Nama                                                        |
| ------------ | ----------------------------------------------------------- |
| 202243500497 | [Alfarobby](https://github.com/Alfarobby27)                 |
| 202243500500 | [Ahmad Badawi](https://github.com/Ahmadbadawi123)           |
| 202243500501 | [Abdur Rosyid Fachriansyah](https://github.com/dellwatch21) |
| 202243500502 | [Sangga Buana](https://github.com/sanggabuana453)           |
| 202243500505 | [Taufik Ramadhan](https://github.com/Alfarobby27)           |  
| 202243500524 | [Riyan Rizaldy](https://github.com/riyanzaldy03)            |


## Memulai
1. Pastikan komputer kamu sudah terinstall [Git](https://git-scm.com/) dan [Java Development Kit](http://jdk.java.net/).
2. Kloning repositori ini ke komputer kamu dan masuk ke folder restoran dari terminal atau cmd
	```bash
	git clone https://github.com/Alfarobby27/Manajemen-Restoran.git && cd Manajemen-Restoran
	```
3. Lakukan kompilasi aplikasi
	```bash
	javac restoran.java
	```
4. Jalankan aplikasi
  * Windows
	```bash
	java -cp ".;sqlite-jdbc-3.40.0.0.jar" restoran
	```
  * Linux atau Mac
	```bash
	java -cp .:sqlite-jdbc-3.40.0.0.jar restoran
	```
	
## Schema / RM ACCESS	
![Schema](./image/rmaccess.jpg)

## Flowchart	
![Flowchart](./image/flowchart.jpg)

## **Penjelasan**	
## APLIKASI MANAJEMEN RESTORAN

Sebuah aplikasi CRUD sederhana yang dibuat menggunakan Bahasa Java dan SQLite3.

- Aplikasi ini berfungsi untuk memanajemen restoran mulai dari data pelanggan, menu, kategori menu, dan pesanan.

- Fitur yang di dapatkan di dalam aplikasi ini adalah :

1. Menu Data Pelanggan
      - Melihat Data Pelanggan
      - Menambah Data Pelanggan
      - Mengubah Data Pelanggan
      - Menghapus Data Pelanggan

2. Menu Data Menu Pesanan
      - Melihat Data Menu
      - Menambah Data Menu
      - Mengubah Data Menu
      - Menghapus Data Menu

3. Menu Data Kategori Menu
      - Melihat Data Kategori Menu
      - Menambah Data Kategori Menu
      - Mengubah Data Kategori Menu
      - Menghapus Data Kategori Menu

4. Menu Pesanan
      - Melihat Daftar Pesanan
      - Melihat Rincian Pesanan
      - Menambah Pesanan
      - Mengubah Pesanan
      - Menghapus Pesanan


## PENJELASAN CODINGAN

- Terdapat 5 class pada aplikasi kami :

1. Class restoran : yaitu class utama / main untuk menjalankan judul aplikasi, menu utama, dan class lainnya.

2. Class pelanggan : yaitu class untuk memanajemen menu data pelanggan

3. Class produk : yaitu class untuk memanajemen menu data menu pesanan

4. Class pesanan : yaitu class untuk memanajemen menu pesanan

5. Class util : yaitu class untuk fitur-fitur pendukung class yang lain. Seperti fitur untuk membuat tabel, fitur inputan, fitur tahan layar, dll.


- Disini kami menggunakan Scanner sebagai inputan
- Menggunakan Switch case dan if sebagai statement atau pilihan.
- Try catch : untuk menangkap error jika terjadi error maka program menampilkan pesan error
- Menggunakan for looping untuk perulangan
- Menggunakan Getter untuk mendapatkan nilai.

- Menggunakan beberapa library java seperti :
1. java.sql.PreparedStatement: Buat bikin dan eksekusi query SQL dengan parameter, jaga-jaga dari serangan SQL Injection.

2. java.sql.ResultSet: Menangkap data yang balik dari database setelah menjalankan query SELECT.

3. java.sql.Statement: Menjalankan query SQL dasar.

4. java.util.ArrayList: Koleksi yang ukurannya bisa naik turun sesuai kebutuhan, enak buat simpan data.

5. java.util.Arrays: Memberikan banyak operasi siap pakai buat mengatur array, seperti sorting atau konversi.

6. java.util.LinkedHashMap : Untuk menyimpan data dengan menjaga urutan data. Ini sangat berguna ketika menghapus data atau mengubah data

7. java.util.Scanner : Untuk inputan scanner

8. java.util.Locale: untuk membuat format tanggal, angka, atau harga sesuai lokasi kita.

9. java.sql.Connection: untuk koneksi ke database pake JDBC.

10. java.sql.DriverManager: untuk mendapatkan koneksi ke database. Menyediakan cara menyambungkan ke lokasi database berdasarkan URL database.

11. java.text.NumberFormat: untuk format angka ke format lokal.

## Fitur
- [x] Data Pelanggan
  - [x] Melihat Data Pelanggan
  - [x] Menambah Data Pelanggan
  - [x] Merubah Data Pelanggan
  - [x] Menghapus Data Pelanggan
- [x] Data Menu
  - [x] Melihat Data Menu
  - [x] Menambah Data Menu
  - [x] Merubah Data Menu
  - [x] Menghapus Data Menu
- [x] Data Kelompok Menu
  - [x] Melihat Data Kelompok Menu
  - [x] Menambah Data Kelompok Menu
  - [x] Merubah Data Kelompok Menu
  - [x] Menghapus Data Kelompok Menu
- [x] Pesanan
  - [x] Melihat Daftar Pesanan
  - [x] Melihat Rincian Pesanan
  - [x] Menambah Pesanan
  - [x] Merubah Pesanan
  - [x] Menghapus Pesanan

## Catatan
Aplikasi ini menggunakan [Driver SQLite JDBC](https://github.com/xerial/sqlite-jdbc), seharusnya driver tersebut diunduh langsung dari sana, namun untuk memudahkan proses belajar terutama bagi pemula driver tersebut juga disertakan pada repositori ini.

## Thanks to
 [Jeffry Luqman](https://github.com/jeffry-luqman)
 
Contributed to help make this application
