# OCTANE --- Sistem Informasi Persediaan dan Distribusi BBM

> **Fuel for a Better Tomorrow**

OCTANE merupakan aplikasi desktop berbasis Java untuk membantu
pengelolaan data persediaan dan distribusi bahan bakar minyak (BBM),
mulai dari pengelolaan data master, pencatatan penerimaan dan
distribusi, konfirmasi penerimaan oleh SPBU, pemantauan stok, hingga
riwayat dan laporan operasional.

> **Catatan pengisian:** Lengkapi bagian bertanda `[ISI ...]` sesuai
> data kelompok dan hasil implementasi final sebelum repository
> dikumpulkan. Pastikan seluruh gambar/dokumen yang dirujuk di bawah
> benar-benar diunggah ke repository.

------------------------------------------------------------------------

## 1. Nama Program

**OCTANE --- Sistem Informasi Persediaan dan Distribusi BBM**

## 2. Deskripsi Singkat

OCTANE dirancang untuk mendukung proses pencatatan dan pemantauan
persediaan serta distribusi BBM dari lokasi penyimpanan menuju SPBU.
Sistem menyediakan hak akses berdasarkan peran pengguna sehingga setiap
pengguna mengakses menu dan data sesuai tanggung jawabnya.

Aplikasi mengelola data melalui database MySQL. Data operasional seperti
penerimaan BBM, distribusi BBM, konfirmasi penerimaan, stok penyimpanan,
dan stok SPBU disimpan serta diproses melalui lapisan akses data (DAO),
bukan ditentukan secara permanen (*hard-coded*) pada tampilan.

## 3. Fitur Utama Program

### 3.1 Administrator

-   Dashboard administrator yang menampilkan ringkasan data operasional.
-   Mengelola akun pengguna.
-   Mengelola data SPBU.
-   Mengelola jenis BBM.
-   Mengelola lokasi penyimpanan.
-   Melihat stok seluruh lokasi penyimpanan.
-   Melihat stok seluruh SPBU.
-   Melihat riwayat penerimaan dan distribusi.
-   Melihat laporan persediaan dan distribusi serta menggunakan filter
    laporan.

### 3.2 Petugas Penyimpanan

-   Melihat dashboard petugas penyimpanan.
-   Mencatat penerimaan BBM ke lokasi penyimpanan.
-   Mencatat distribusi BBM dari lokasi penyimpanan ke SPBU.
-   Memilih jenis BBM dari data jenis BBM yang tersedia.
-   Melihat stok pada lokasi penyimpanan.
-   Melihat riwayat penerimaan dan distribusi.
-   Melakukan pemeriksaan distribusi ketika diperlukan, sesuai status
    dan alur pemeriksaan.

### 3.3 Petugas SPBU

-   Melihat dashboard sesuai SPBU yang menjadi penempatannya.
-   Melihat pengiriman BBM yang perlu dikonfirmasi.
-   Mengonfirmasi jumlah BBM yang benar-benar diterima.
-   Mengisi keterangan konfirmasi.
-   Melihat riwayat distribusi SPBU.
-   Melihat stok SPBU.

### 3.4 Pengelolaan Status Distribusi

Alur distribusi mencatat status pengiriman. Status yang ditampilkan pada
aplikasi mencakup:

  -----------------------------------------------------------------------
  Status                              Keterangan
  ----------------------------------- -----------------------------------
  `MENUNGGU_KONFIRMASI`               Distribusi telah dicatat dan
                                      menunggu konfirmasi jumlah aktual
                                      dari petugas SPBU.

  `DIPROSES`                          Distribusi sedang
                                      ditindaklanjuti/diproses.

  `PERLU_PEMERIKSAAN`                 Terdapat selisih atau kondisi yang
                                      memerlukan pemeriksaan petugas
                                      penyimpanan.

  `SELESAI`                           Proses distribusi telah selesai
                                      sesuai hasil konfirmasi.

  `SELESAI_DITANGANI`                 Pemeriksaan atau tindak lanjut
                                      telah diselesaikan.
  -----------------------------------------------------------------------

Status aktual mengikuti aturan bisnis dan data yang tersimpan pada
database.

## 4. Tech Stack

  Komponen                          Teknologi
  --------------------------------- ----------------------------------
  Bahasa pemrograman                Java
  Versi Java                        JDK 17
  Tampilan aplikasi                 Java Swing
  GUI Builder                       Apache NetBeans GUI Builder
  IDE                               Apache NetBeans IDE
  Build dan dependency management   Apache Maven
  Database                          MySQL
  Koneksi database                  JDBC
  Driver database                   MySQL Connector/J 8.4.0
  Arsitektur aplikasi               Model--View--Controller (MVC)
  Pola akses data                   Data Access Object (DAO Pattern)

## 5. Perancangan Sistem

Dokumen perancangan sistem disimpan pada folder `docs/`. Letakkan file
final kelompok pada lokasi dan nama file berikut agar tautan pada README
dapat dibuka.

### 5.1 Flowchart

Flowchart menggambarkan urutan proses utama aplikasi, mulai dari
autentikasi hingga proses pengelolaan penerimaan, distribusi,
konfirmasi, dan pemeriksaan.

**Dokumen:** [`docs/Flowchart.pdf`](docs/Flowchart.pdf)

### 5.2 Use Case Diagram

Use Case Diagram menggambarkan interaksi aktor dengan fitur sistem
berdasarkan hak aksesnya.

**Dokumen:** [`docs/Use-Case-Diagram.pdf`](docs/Use-Case-Diagram.pdf)

### 5.3 Entity Relationship Diagram (ERD)

ERD digunakan untuk menjelaskan entitas, atribut, serta hubungan
antarentitas dalam database OCTANE.

-   **ERD Logical:** [`docs/ERD-Logical.png`](docs/ERD-Logical.png)
-   **ERD Relational:**
    [`docs/ERD-Relational.png`](docs/ERD-Relational.png)

> Gunakan ERD final yang telah disetujui. Jangan mengganti rancangan
> dengan gambar sementara atau rancangan dari versi lama.

## 6. Struktur Folder/Package

Proyek menggunakan Maven dengan pemisahan package berdasarkan tanggung
jawab MVC dan komponen pendukung.

``` text
OCTANE/
├── database/
│   └── octane_db.sql
├── src/
│   └── main/
│       └── java/
│           ├── config/
│           │   ├── Koneksi.java
│           │   └── TestKoneksi.java
│           ├── controller/
│           ├── dao/
│           ├── main/
│           │   └── Main.java
│           ├── model/
│           ├── util/
│           │   └── ValidasiInput.java
│           └── view/
│               ├── *.java
│               └── *.form
├── docs/
│   ├── Flowchart.pdf
│   ├── Use-Case-Diagram.pdf
│   ├── ERD-Logical.png
│   └── ERD-Relational.png
├── screenshots/
│   ├── login.png
│   ├── dashboard-administrator.png
│   ├── dashboard-penyimpanan.png
│   ├── dashboard-spbu.png
│   ├── penerimaan-bbm.png
│   ├── distribusi-bbm.png
│   ├── konfirmasi-penerimaan.png
│   └── laporan.png
├── pom.xml
└── README.md
```

### Penjelasan package

  -----------------------------------------------------------------------
  Package/Folder                      Tanggung jawab
  ----------------------------------- -----------------------------------
  `config`                            Menyediakan konfigurasi dan koneksi
                                      database.

  `controller`                        Menangani alur aplikasi, validasi
                                      proses, dan penghubung View dengan
                                      DAO.

  `dao`                               Menangani operasi database
                                      menggunakan JDBC, termasuk
                                      pengambilan, penambahan, perubahan,
                                      dan penghapusan data sesuai
                                      kebutuhan fitur.

  `main`                              Berisi titik awal aplikasi
                                      (`Main.java`).

  `model`                             Menyimpan representasi data dan
                                      objek domain aplikasi.

  `util`                              Menyediakan fungsi pendukung,
                                      termasuk validasi input.

  `view`                              Menyediakan tampilan Java Swing.
                                      File `.form` digunakan oleh
                                      NetBeans GUI Builder untuk
                                      menyimpan rancangan form dan
                                      dipasangkan dengan file `.java`.

  `database`                          Berisi file SQL untuk membuat dan
                                      mengisi database.

  `docs`                              Berisi dokumen perancangan sistem.

  `screenshots`                       Berisi bukti tampilan aplikasi yang
                                      digunakan pada README dan
                                      presentasi.
  -----------------------------------------------------------------------

## 7. Penerapan 4 Pilar OOP

### 7.1 Encapsulation (Enkapsulasi)

Enkapsulasi diterapkan dengan membatasi akses langsung terhadap atribut
objek dan menyediakan method untuk membaca atau mengelola data.
Contohnya terdapat pada model pengguna dan model data lain yang
menggunakan atribut dengan tingkat akses terbatas serta method
getter/setter sesuai kebutuhan.

**Contoh file:** `model/PenggunaLogin.java`, `model/Pengguna.java`, dan
model lainnya.

### 7.2 Inheritance (Pewarisan)

Inheritance diterapkan melalui kelas abstrak `PenggunaLogin` yang
menjadi induk bagi beberapa jenis pengguna. Kelas turunan mewarisi
atribut dan perilaku umum pengguna, kemudian merepresentasikan peran
yang lebih spesifik.

**Contoh:**

-   `AdministratorLogin extends PenggunaLogin`
-   `PetugasPenyimpananLogin extends PenggunaLogin`
-   `PetugasSpbuLogin extends PenggunaLogin`

### 7.3 Polymorphism (Polimorfisme)

Polimorfisme diterapkan melalui method abstrak `getPeran()` pada
`PenggunaLogin` yang diimplementasikan berbeda oleh setiap kelas
turunannya. Pemanggilan method yang sama dapat menghasilkan informasi
peran yang berbeda sesuai objek sebenarnya.

**Contoh:** `getPeran()` menghasilkan `ADMINISTRATOR`,
`PETUGAS PENYIMPANAN`, atau `PETUGAS SPBU`.

### 7.4 Abstraction (Abstraksi)

Abstraksi diterapkan melalui kelas abstrak `PenggunaLogin` dan interface
DAO. Kelas abstrak menetapkan struktur umum pengguna, sedangkan
interface DAO mendefinisikan operasi akses data tanpa mengharuskan
pemanggil mengetahui detail query SQL di dalam implementasinya.

**Contoh:**

-   Kelas abstrak: `model/PenggunaLogin.java`
-   Interface DAO: `AutentikasiDao`, `OperasionalDao`, `StokDao`,
    `SpbuDao`, dan interface DAO lainnya.
-   Implementasi DAO: kelas dengan akhiran `DaoImpl`.

## 8. Validasi Input

Validasi dilakukan pada lapisan controller dan utilitas sebelum data
diteruskan ke DAO/database. Berikut ringkasan aturan validasi yang
terdapat pada implementasi `ValidasiInput` dan pemanggilannya di
controller.

  -------------------------------------------------------------------------------
                    No. Input/Proses     Aturan validasi         Respons jika
                                                                 tidak valid
  --------------------- ---------------- ----------------------- ----------------
                      1 Teks wajib diisi Nilai tidak boleh       Proses
                                         `null`, kosong, atau    dihentikan dan
                                         hanya spasi. Nilai      pesan bahwa
                                         dibersihkan dengan      kolom wajib
                                         `trim()`.               diisi
                                                                 ditampilkan.

                      2 Panjang teks     Panjang tidak boleh     Proses
                                         melebihi batas field    dihentikan dan
                                         yang ditentukan.        pesan batas
                                                                 karakter
                                                                 ditampilkan.

                      3 Teks opsional    Jika kosong, nilai      Proses
                                         dikembalikan sebagai    dihentikan jika
                                         `null`; jika diisi,     melebihi batas
                                         panjang tetap           karakter.
                                         diperiksa.              

                      4 Jumlah           Harus berupa nilai      Data tidak
                        penerimaan BBM   numerik `BigDecimal`    diteruskan jika
                                         yang lebih besar dari   jumlah tidak
                                         0.                      positif.

                      5 Jumlah           Harus lebih besar dari  Proses
                        distribusi BBM   0, maksimal 2 angka di  dihentikan jika
                                         belakang koma, dan      nilai tidak
                                         sesuai kapasitas        memenuhi
                                         `DECIMAL(15,2)`.        ketentuan.

                      6 Jumlah aktual    Harus lebih besar dari  Konfirmasi tidak
                        diterima SPBU    0 dan memenuhi batas    diproses jika
                                         presisi/kapasitas       nilai tidak
                                         desimal.                valid.

                      7 ID data          ID pengguna, lokasi,    Proses
                                         SPBU, atau transaksi    dihentikan dan
                                         harus lebih besar dari  pesan ID tidak
                                         0.                      valid
                                                                 ditampilkan.

                      8 Nama jenis BBM   Wajib diisi dan         Data jenis BBM
                                         maksimal 50 karakter.   tidak disimpan
                                                                 jika tidak
                                                                 memenuhi aturan.

                      9 Nama SPBU        Wajib diisi dan         Data SPBU tidak
                                         maksimal 100 karakter.  disimpan jika
                                                                 tidak memenuhi
                                                                 aturan.

                     10 Alamat SPBU      Wajib diisi dan         Data SPBU tidak
                                         maksimal 255 karakter.  disimpan jika
                                                                 tidak memenuhi
                                                                 aturan.

                     11 Nama lokasi      Wajib diisi dan         Data lokasi
                        penyimpanan      maksimal 100 karakter.  tidak disimpan
                                                                 jika tidak
                                                                 memenuhi aturan.

                     12 Alamat lokasi    Wajib diisi dan         Data lokasi
                        penyimpanan      maksimal 255 karakter.  tidak disimpan
                                                                 jika tidak
                                                                 memenuhi aturan.

                     13 Keterangan       Wajib diisi dan         Konfirmasi tidak
                        konfirmasi       maksimal 255 karakter.  diproses jika
                        penerimaan                               keterangan
                                                                 kosong atau
                                                                 terlalu panjang.

                     14 Catatan          Wajib diisi dan         Pemeriksaan
                        pemeriksaan      maksimal 500 karakter.  tidak
                        distribusi                               diselesaikan
                                                                 jika catatan
                                                                 tidak memenuhi
                                                                 aturan.

                     15 Nama lengkap     Wajib diisi dan         Data pengguna
                        pengguna         maksimal 100 karakter   tidak disimpan
                                         pada proses pengelolaan jika tidak
                                         pengguna.               memenuhi aturan.

                     16 Username         Wajib diisi dan         Data pengguna
                                         maksimal 50 karakter    tidak disimpan
                                         pada proses pengelolaan jika tidak
                                         pengguna.               memenuhi aturan.

                     17 Password         Pada proses             Proses
                                         pembuatan/penggantian   pengelolaan
                                         password, nilai wajib   pengguna
                                         diisi dan dibatasi      dihentikan jika
                                         maksimal 255 karakter.  tidak memenuhi
                                                                 aturan.

                     18 Status data      Status wajib diisi      Perubahan status
                        master           sebelum perubahan       tidak diproses
                                         status data jenis BBM,  jika status
                                         SPBU, atau lokasi.      kosong.
  -------------------------------------------------------------------------------

> Batas panjang dan aturan di atas merujuk pada pemanggilan validasi
> yang ditemukan di source code. Untuk laporan akhir, sesuaikan kembali
> tabel jika ada perubahan implementasi setelah versi source code ini.

## 9. Database

Nama database: **`octane_db`**

File database disertakan pada:

[`database/octane_db.sql`](database/octane_db.sql)

Database pada source code mencakup tabel-tabel berikut:

  ----------------------------------------------------------------------------
                           No. Tabel                     Kegunaan umum
  ---------------------------- ------------------------- ---------------------
                             1 `administrator`           Data khusus
                                                         administrator.

                             2 `distribusi_bbm`          Pencatatan distribusi
                                                         BBM dari lokasi
                                                         penyimpanan ke SPBU.

                             3 `jenis_bbm`               Data master jenis
                                                         BBM.

                             4 `konfirmasi_penerimaan`   Data konfirmasi
                                                         jumlah BBM yang
                                                         diterima SPBU.

                             5 `lokasi_penyimpanan`      Data lokasi/gudang
                                                         penyimpanan BBM.

                             6 `penerimaan_bbm`          Pencatatan penerimaan
                                                         BBM pada lokasi
                                                         penyimpanan.

                             7 `pengguna`                Data akun pengguna
                                                         aplikasi.

                             8 `petugas_penyimpanan`     Data khusus petugas
                                                         penyimpanan.

                             9 `petugas_spbu`            Data khusus petugas
                                                         SPBU.

                            10 `spbu`                    Data master SPBU.

                            11 `stok_penyimpanan`        Data stok BBM pada
                                                         lokasi penyimpanan.

                            12 `stok_spbu`               Data stok BBM pada
                                                         SPBU.
  ----------------------------------------------------------------------------

### Konfigurasi koneksi database

Secara default, aplikasi menggunakan konfigurasi berikut:

-   Host: `localhost`
-   Port: `3306`
-   Database: `octane_db`
-   Username default: `root`
-   Password default: kosong

Konfigurasi dapat disesuaikan melalui environment variable:

  Environment variable   Fungsi
  ---------------------- -----------------------------
  `OCTANE_DB_URL`        Mengatur JDBC URL database.
  `OCTANE_DB_USER`       Mengatur username database.
  `OCTANE_DB_PASSWORD`   Mengatur password database.

Jangan mengunggah password database pribadi atau kredensial yang
bersifat rahasia ke repository publik.

## 10. Cara Instalasi dan Menjalankan Program

### 10.1 Persiapan

Pastikan perangkat telah memiliki:

1.  JDK 17.
2.  Apache NetBeans dengan dukungan proyek Maven.
3.  MySQL Server.
4.  phpMyAdmin atau aplikasi pengelola database lain (opsional, untuk
    mengimpor SQL).

### 10.2 Membuat database

1.  Jalankan MySQL Server.
2.  Buka phpMyAdmin.
3.  Impor file `database/octane_db.sql`.
4.  Pastikan database `octane_db` dan tabel-tabelnya berhasil dibuat.

### 10.3 Membuka proyek

1.  Clone repository OCTANE dari GitHub.
2.  Buka Apache NetBeans.
3.  Pilih **File → Open Project**.
4.  Pilih folder proyek `OCTANE`.
5.  Pastikan Maven berhasil memuat dependency.

### 10.4 Menyesuaikan koneksi

Jika konfigurasi MySQL berbeda dari pengaturan default, atur
`OCTANE_DB_URL`, `OCTANE_DB_USER`, dan `OCTANE_DB_PASSWORD` pada
environment perangkat sebelum menjalankan aplikasi.

### 10.5 Menjalankan aplikasi

1.  Buka project di NetBeans.
2.  Pastikan konfigurasi database sudah benar.
3.  Jalankan file utama `main/Main.java` atau gunakan perintah Maven:

``` bash
mvn clean install
mvn exec:java
```

4.  Login menggunakan akun yang tersedia pada database lokal. Gunakan
    akun uji yang disiapkan kelompok; jangan menuliskan password akun
    nyata pada README.

## 11. Screenshot GUI

Tambahkan screenshot dari aplikasi yang benar-benar dijalankan. Gunakan
gambar yang jelas dan tidak menampilkan password atau informasi
sensitif.

  ----------------------------------------------------------------------------------------------
                           No. Screenshot yang perlu Nama file
                               dimasukkan            
  ---------------------------- --------------------- -------------------------------------------
                             1 Halaman Login         `screenshots/login.png`

                             2 Dashboard             `screenshots/dashboard-administrator.png`
                               Administrator         

                             3 Dashboard Petugas     `screenshots/dashboard-penyimpanan.png`
                               Penyimpanan           

                             4 Dashboard Petugas     `screenshots/dashboard-spbu.png`
                               SPBU                  

                             5 Form Catat Penerimaan `screenshots/penerimaan-bbm.png`
                               BBM                   

                             6 Form Catat Distribusi `screenshots/distribusi-bbm.png`
                               BBM                   

                             7 Halaman Konfirmasi    `screenshots/konfirmasi-penerimaan.png`
                               Penerimaan            

                             8 Halaman Laporan       `screenshots/laporan.png`
  ----------------------------------------------------------------------------------------------

Contoh penyisipan gambar setelah file diunggah:

``` markdown
![Halaman Login](screenshots/login.png)
```

## 12. Kontributor

Isi sesuai anggota kelompok yang benar-benar mengerjakan proyek.

    No. Nama           NIM           Peran/Kontribusi
  ----- -------------- ------------- --------------------
      1 \[ISI NAMA\]   \[ISI NIM\]   \[ISI KONTRIBUSI\]
      2 \[ISI NAMA\]   \[ISI NIM\]   \[ISI KONTRIBUSI\]
      3 \[ISI NAMA\]   \[ISI NIM\]   \[ISI KONTRIBUSI\]
      4 \[ISI NAMA\]   \[ISI NIM\]   \[ISI KONTRIBUSI\]
      5 \[ISI NAMA\]   \[ISI NIM\]   \[ISI KONTRIBUSI\]

## 13. Catatan Pengembangan

-   Aplikasi menggunakan Java Swing untuk antarmuka desktop.
-   Tampilan pada package `view` dibuat menggunakan NetBeans GUI Builder
    dan file `.form` pasangannya.
-   Pemrosesan alur aplikasi dipisahkan ke package `controller`.
-   Operasi database dipisahkan ke package `dao`.
-   Representasi data ditempatkan pada package `model`.
-   Validasi input terpusat yang digunakan controller tersedia pada
    `util/ValidasiInput.java`.

------------------------------------------------------------------------

**Nama proyek:** OCTANE\
**Database:** `octane_db`\
**Repository:** \[ISI LINK REPOSITORY GITHUB\]
