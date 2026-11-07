# OCTANE
### Sistem Informasi Persediaan dan Distribusi Bahan Bakar Minyak

**Project Capstone PBO dan Sistem Basis Data - 2026**  
Program Studi Sistem Informasi · Fakultas Teknik · Universitas Mulawarman

---

## Gambaran Proyek

OCTANE adalah aplikasi desktop untuk membantu pencatatan persediaan dan proses distribusi bahan bakar minyak (BBM). Aplikasi ini menghubungkan aktivitas administrator, petugas penyimpanan, dan petugas SPBU melalui data yang tersimpan pada database.

Melalui OCTANE, proses penerimaan BBM, pengiriman ke SPBU, konfirmasi jumlah yang diterima, pemeriksaan selisih, serta pemantauan stok dapat dicatat dalam satu sistem.

## Ruang Lingkup Aplikasi

| Bagian | Aktivitas yang didukung |
|---|---|
| Data pengguna | Pengelolaan akun dan hak akses |
| Data master | Pengelolaan SPBU, jenis BBM, dan lokasi penyimpanan |
| Penerimaan | Pencatatan BBM yang masuk ke lokasi penyimpanan |
| Distribusi | Pencatatan pengiriman BBM dari lokasi penyimpanan ke SPBU |
| Konfirmasi | Pencatatan jumlah BBM yang diterima oleh SPBU |
| Pemeriksaan | Penanganan distribusi yang memiliki selisih atau membutuhkan tindak lanjut |
| Persediaan | Pemantauan stok BBM di lokasi penyimpanan dan SPBU |
| Riwayat dan laporan | Penelusuran transaksi penerimaan serta distribusi |

### Pembagian Pengguna

| Pengguna | Tanggung jawab utama |
|---|---|
| Administrator | Mengelola data master, akun, dan melihat informasi sistem |
| Petugas Penyimpanan | Mencatat penerimaan dan distribusi serta memantau stok penyimpanan |
| Petugas SPBU | Mengonfirmasi penerimaan BBM dan memantau stok SPBU |

*Rincian hak akses mengikuti implementasi akhir aplikasi.*

## Alur Proses Distribusi

Secara umum, proses distribusi BBM pada OCTANE berjalan melalui tahapan berikut:

1. Petugas penyimpanan mencatat pengiriman BBM beserta SPBU tujuan dan jumlah yang dikirim.
2. Data distribusi tersimpan dan menunggu konfirmasi dari SPBU.
3. Petugas SPBU memeriksa pengiriman dan memasukkan jumlah BBM yang diterima.
4. Sistem membandingkan jumlah yang dikirim dengan jumlah yang diterima.
5. Jika terdapat perbedaan, distribusi dapat ditandai untuk pemeriksaan.
6. Setelah proses konfirmasi atau tindak lanjut selesai, status distribusi diperbarui.

### Arti Status Distribusi

| Status | Makna dalam proses |
|---|---|
| `MENUNGGU_KONFIRMASI` | Menunggu konfirmasi penerimaan dari SPBU |
| `DIPROSES` | Sedang dalam proses |
| `PERLU_PEMERIKSAAN` | Membutuhkan pemeriksaan lebih lanjut |
| `SELESAI` | Distribusi telah selesai |
| `SELESAI_DITANGANI` | Tindak lanjut pemeriksaan telah diselesaikan |

## Teknologi yang Digunakan

| Komponen | Teknologi |
|---|---|
| Bahasa pemrograman | Java |
| Antarmuka desktop | Java Swing |
| Perancangan form | NetBeans GUI Builder |
| Lingkungan pengembangan | Apache NetBeans IDE |
| Build dan dependency | Apache Maven |
| Database | MySQL |
| Akses database | JDBC |
| Pola arsitektur | MVC |
| Pengelolaan akses data | DAO Pattern |
| Version control | Git dan GitHub |

## Rancangan Sistem

Dokumen rancangan digunakan sebagai acuan dalam pengembangan fitur dan struktur database.

| Dokumen | Keterangan | Lokasi berkas |
|---|---|---|
| Flowchart | Urutan proses dan alur kerja aplikasi | `docs/Flowchart.pdf` |
| Use Case Diagram | Interaksi aktor dengan fitur sistem | `docs/Use-Case-Diagram.pdf` |
| ERD Logical | Gambaran entitas, atribut, dan hubungan | `docs/ERD-Logical.png` |
| ERD Relational | Rancangan relasi tabel database | `docs/ERD-Relational.png` |

### Flowchart
![Flowchart OCTANE](docs/Flowchart.png)

### Use Case Diagram
![Use Case OCTANE](docs/Use-Case-Diagram.png)

### ERD Logical
![ERD Logical OCTANE](docs/ERD-Logical.png)

### ERD Relational
![ERD Relational OCTANE](docs/ERD-Relational.png)

> Nama berkas pada tabel dan tautan gambar perlu disamakan dengan nama dokumen yang benar-benar disimpan di repository.

## Susunan Source Code

OCTANE menggunakan susunan proyek Maven. Kode dipisahkan ke dalam package agar tanggung jawab antarkomponen lebih jelas.

```text
OCTANE/
├── database/
│   └── octane_db.sql
├── docs/
├── screenshots/
└── src/
    └── main/
        └── java/
            ├── config/
            ├── controller/
            ├── dao/
            ├── main/
            ├── model/
            ├── util/
            └── view/
                ├── Form.java
                └── Form.form
```

| Package | Peran |
|---|---|
| `config` | Pengaturan koneksi database |
| `controller` | Penghubung proses antara tampilan dan akses data |
| `dao` | Operasi baca dan perubahan data pada database |
| `main` | Titik awal aplikasi |
| `model` | Representasi data dan objek sistem |
| `util` | Fungsi pendukung aplikasi |
| `view` | Form antarmuka berbasis Java Swing |

Form pada package `view` dirancang menggunakan NetBeans GUI Builder. File `.java` berpasangan dengan file `.form` agar tampilan dapat dibuka dan disunting melalui tab Design di NetBeans.

## Penerapan Konsep OOP

Bagian ini menjelaskan penerapan konsep berorientasi objek pada source code OCTANE. Nama kelas dan contoh kode di bawah perlu disesuaikan dengan kelas yang benar-benar digunakan pada versi repository final.

| Pilar OOP | Penerapan pada aplikasi |
|---|---|
| Encapsulation | Data objek dibatasi melalui access modifier dan diakses menggunakan method yang disediakan kelas. |
| Inheritance | Kelas turunan menggunakan atribut atau perilaku umum dari kelas induk, misalnya pada pengelompokan jenis pengguna apabila diterapkan pada source code. |
| Polymorphism | Method dengan nama atau kontrak yang sama dapat memiliki implementasi berbeda melalui overriding atau overloading. |
| Abstraction | Detail implementasi disembunyikan melalui kelas abstrak atau interface, termasuk kontrak akses data apabila digunakan. |

1. Encapsulation 

Enkapsulasi diterapkan pada class Pengguna.java. Atribut pada class tersebut menggunakan access modifier private, sehingga tidak dapat diakses secara langsung dari luar class. Untuk mengakses dan mengubah nilainya, digunakan method getter dan setter.

File: src/main/java/model/Pengguna.java

```
public class Pengguna {
    private int idPengguna;
    private String namaPengguna;
    private String username;
    private String password;
    private String role;

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }
}
```

Atribut seperti idPengguna, namaPengguna, username, password, dan role dibuat private. Pengaksesan atribut dilakukan melalui getter dan setter. Dengan demikian, data pengguna tidak dapat diakses atau diubah secara langsung dari luar class.

2. Inheritance 

Inheritance diterapkan melalui class PenggunaLogin sebagai parent class yang diwariskan oleh tiga class turunan, yaitu AdministratorLogin, PetugasPenyimpananLogin, dan PetugasSpbuLogin.

File: src/main/java/model/PenggunaLogin.java

```
public abstract class PenggunaLogin {
    private final int idPengguna;
    private final String namaLengkap;
    private final String username;

    protected PenggunaLogin(
        int idPengguna,
        String namaLengkap,
        String username,
        String lokasi,
        int idLokasiPenyimpanan,
        int idSpbu
    ) {
        this.idPengguna = idPengguna;
        this.namaLengkap = namaLengkap;
        this.username = username;
    }

    public abstract String getPeran();
}
```

Contoh class turunan:

```
public final class AdministratorLogin extends PenggunaLogin {
    public AdministratorLogin(
        int id,
        String nama,
        String username,
        String lokasi
    ) {
        super(id, nama, username, lokasi, 0, 0);
    }

    @Override
    public String getPeran() {
        return "ADMINISTRATOR";
    }
}
```

Class AdministratorLogin mewarisi class PenggunaLogin menggunakan keyword extends. Constructor pada class turunan menggunakan super() untuk memanggil constructor parent class. Pola yang sama juga diterapkan pada class PetugasPenyimpananLogin dan PetugasSpbuLogin.

3. Polymorphism 

Polymorphism diterapkan melalui method getPeran() yang dideklarasikan pada class PenggunaLogin dan diimplementasikan kembali oleh setiap class turunannya.

Contoh implementasi:

Pada AdministratorLogin.java:

```
@Override
public String getPeran() {
    return "ADMINISTRATOR";
}
```

Pada PetugasPenyimpananLogin.java:

```
@Override
public String getPeran() {
    return "PETUGAS PENYIMPANAN";
}
```

Pada PetugasSpbuLogin.java:

```
@Override
public String getPeran() {
    return "PETUGAS SPBU";
}
```

Ketiga class memiliki method dengan nama yang sama, yaitu getPeran(), tetapi menghasilkan nilai yang berbeda sesuai dengan jenis pengguna. Hal ini merupakan penerapan method overriding, yaitu salah satu bentuk polimorfisme dalam Java.

4. Abstraction

Abstraksi diterapkan melalui abstract class PenggunaLogin dan interface JenisBbmDao.

Contoh abstract class:

```
public abstract class PenggunaLogin {
    public abstract String getPeran();
}
```

Method getPeran() hanya dideklarasikan pada abstract class dan implementasinya diserahkan kepada class turunannya.

Contoh interface:

File: src/main/java/dao/JenisBbmDao.java

```
public interface JenisBbmDao {
    List<JenisBbmModel> getAll() throws SQLException;

    boolean insert(JenisBbmModel data) throws SQLException;

    boolean update(JenisBbmModel data) throws SQLException;

    boolean updateStatus(int id, String status) throws SQLException;

    int deleteOrDeactivate(int id) throws SQLException;
}
```

Interface JenisBbmDao mendefinisikan operasi yang dapat dilakukan terhadap data jenis BBM, seperti mengambil, menambahkan, memperbarui, dan mengubah status data. Detail proses pengolahan data diserahkan kepada class yang mengimplementasikan interface tersebut.

## Aturan Pemeriksaan Input

Input diperiksa sebelum diproses agar data yang disimpan sesuai dengan kebutuhan setiap fitur. Ringkasan berikut perlu dicocokkan dengan aturan validasi yang benar-benar aktif pada aplikasi.

| No. | Data yang diperiksa | Aturan pemeriksaan |
|---:|---|---|
| 1 | Kolom wajib | Tidak boleh kosong atau hanya berisi spasi |
| 2 | Nama dan keterangan | Mengikuti batas panjang kolom pada database |
| 3 | Username | Wajib diisi dan mengikuti ketentuan panjang field |
| 4 | Password | Wajib diisi saat membuat akun atau mengganti password |
| 5 | Jumlah penerimaan | Harus berupa angka lebih dari nol |
| 6 | Jumlah distribusi | Harus berupa angka lebih dari nol |
| 7 | Jumlah BBM diterima | Harus berupa angka dan tidak boleh negatif |
| 8 | ID data | Harus menggunakan format bilangan bulat yang valid |
| 9 | Jenis BBM | Harus dipilih dari data jenis BBM yang tersedia |
| 10 | Lokasi penyimpanan | Harus dipilih dari data lokasi yang tersedia |
| 11 | SPBU tujuan | Harus dipilih dari data SPBU yang tersedia |
| 12 | Status distribusi | Harus menggunakan status yang disediakan sistem |
| 13 | Tanggal transaksi | Harus menggunakan tanggal yang valid |
| 14 | Keterangan pemeriksaan | Diisi ketika proses pemeriksaan membutuhkan catatan |

## Database

Database aplikasi menggunakan MySQL. Berkas SQL disimpan di dalam repository agar struktur database dapat diimpor ketika aplikasi dipasang pada perangkat lain.

- **Nama database:** `octane_db`
- **Berkas SQL:** [`database/octane_db.sql`](database/octane_db.sql)

Konfigurasi koneksi yang umum digunakan pada lingkungan pengembangan lokal:

| Parameter | Nilai |
|---|---|
| Host | `localhost` |
| Port | `3306` |
| Database | `octane_db` |
| Username | `root` |
| Password | Sesuai konfigurasi MySQL lokal |


## Menjalankan Aplikasi

### Kebutuhan

- JDK 17
- Apache NetBeans IDE
- Apache Maven
- MySQL Server

### Cara penggunaan Program

Bagian ini menjelaskan demonstrasi penggunaan aplikasi OCTANE secara langsung, mulai dari proses login, pengelolaan data, pencatatan penerimaan BBM, hingga proses distribusi dan konfirmasi penerimaan BBM. Demonstrasi dilakukan berdasarkan hak akses masing-masing pengguna.

#### 1. Login ke Aplikasi

**1.1 Halaman Login**

Halaman Login merupakan tampilan awal aplikasi OCTANE. Pengguna harus memasukkan username dan password yang telah terdaftar untuk mengakses sistem.

<img width="1225" height="762" alt="image" src="https://github.com/user-attachments/assets/2f8e7af4-1ec4-40bc-83a3-185db465250f" />

**1.2 Login Administrator**

Administrator memasukkan username dan password pada form Login. Setelah autentikasi berhasil, sistem akan mengarahkan Administrator ke halaman Dashboard.

<img width="1228" height="762" alt="image" src="https://github.com/user-attachments/assets/099801aa-b0c7-46a3-b13e-98dffcfe1a24" />

#### 2. Demonstrasi Pengelolaan Data Administrator

**2.1 Dashboard Administrator**

Dashboard Administrator merupakan halaman utama yang menampilkan informasi dan menyediakan akses ke berbagai fitur pengelolaan sistem.

<img width="1682" height="947" alt="image" src="https://github.com/user-attachments/assets/7df1ac6f-2f0e-4f71-9d00-bfd4dffaf144" />

**2.2 Menampilkan Data Pengguna**

Administrator membuka menu Pengguna untuk melihat daftar pengguna yang telah terdaftar dalam sistem. Data pengguna ditampilkan dalam bentuk tabel yang berisi informasi sesuai dengan atribut pengguna.

<img width="1687" height="947" alt="image" src="https://github.com/user-attachments/assets/38e12a69-c310-4938-ab3d-a0dc800c7d13" />

**2.3 Menambahkan Data Pengguna**

Administrator memilih tombol Tambah untuk membuka form penambahan pengguna. Selanjutnya, Administrator mengisi informasi pengguna, seperti nama, username, password, dan role. Setelah seluruh data terisi, tombol Simpan digunakan untuk menyimpan data pengguna ke dalam database.

<img width="1695" height="947" alt="image" src="https://github.com/user-attachments/assets/7c0a9e58-d110-4089-81a7-1a92b0a38d75" />

<img width="1130" height="391" alt="image" src="https://github.com/user-attachments/assets/d0c5c2cf-c7a6-4e04-8a3e-49af5bab6f62" />

**2.4 Mengubah Data Pengguna**

Administrator memilih salah satu data pengguna pada tabel, kemudian menekan tombol Ubah untuk membuka form perubahan data. Setelah melakukan perubahan informasi yang diperlukan, Administrator menekan tombol Simpan untuk memperbarui data pengguna.

<img width="1681" height="943" alt="image" src="https://github.com/user-attachments/assets/10fd0561-ed20-4066-a701-e7357c3dc28c" />

<img width="1127" height="398" alt="image" src="https://github.com/user-attachments/assets/3fb2e289-4308-4af8-b502-17b0453a6d00" />

**2.5 Menghapus atau Menonaktifkan Data Pengguna**

Administrator memilih data pengguna yang akan dihapus atau dinonaktifkan, kemudian menekan tombol yang tersedia. Sistem memproses tindakan tersebut dan memperbarui informasi pengguna sesuai mekanisme pengelolaan data yang diterapkan.

<img width="1685" height="945" alt="image" src="https://github.com/user-attachments/assets/dc8da6e5-9db7-42df-8afd-715a97ef44dd" />

<img width="1678" height="946" alt="image" src="https://github.com/user-attachments/assets/75d5d975-37ae-4405-be9d-92a23f7b9dc1" />

<img width="1121" height="396" alt="image" src="https://github.com/user-attachments/assets/83fbb3c1-02e8-483e-ad88-29b350edb627" />

**2.6 Menampilkan Data Jenis BBM**

Administrator membuka menu Jenis BBM untuk melihat daftar jenis bahan bakar yang telah terdaftar dalam sistem.

<img width="1686" height="946" alt="image" src="https://github.com/user-attachments/assets/9e5436d0-7d7c-4188-b327-f63a5c58486d" />

**2.7 Menambahkan dan Mengelola Data Jenis BBM**

Administrator menggunakan form yang tersedia untuk menambahkan jenis BBM baru. Informasi yang diminta pada form diisi sesuai kebutuhan sistem, kemudian disimpan ke database. Data yang telah tersimpan dapat dipilih untuk dilakukan perubahan atau pengelolaan lebih lanjut.

Pada tahap ini dilakukan penambahan data jenis BBM

<img width="1686" height="947" alt="image" src="https://github.com/user-attachments/assets/10c0f694-16ca-4a63-8e5f-e5ccad097a79" />

Pada tahap ini ditampilkan bahwa data yang sudah ditambahkan telah berhasil muncul pada tabel

<img width="1256" height="336" alt="image" src="https://github.com/user-attachments/assets/4230dfed-27a1-43f2-9564-3460c5a9aea3" />

Pada tahap ini dilakukan perubahan nama pada jenis bbm dengan tombol ubah

<img width="1687" height="942" alt="image" src="https://github.com/user-attachments/assets/12498c27-cbd2-48c9-a03b-5ec47fa8cff4" />

Pada tahap ini dilakukan penghapusan data pada jenis bbm yang tadi sudah ditambahkan

<img width="1686" height="943" alt="image" src="https://github.com/user-attachments/assets/57b2ef8c-5e7f-487e-bf9b-569a3d5e12fb" />

**2.8 Menampilkan dan Mengelola Data Lokasi**

Administrator membuka menu Lokasi untuk melihat data lokasi yang digunakan dalam sistem, termasuk lokasi penyimpanan BBM dan SPBU. Administrator dapat menambahkan atau mengubah informasi lokasi melalui form yang tersedia.

Berikut adalah menu Kelola lokasi penyimpanan beserta fitur fiturnya

<img width="1682" height="942" alt="image" src="https://github.com/user-attachments/assets/5a5d7342-ff6f-49da-bed9-5d9a17612ec1" />

Berikut adalah menu Kelola SPBU beserta fitur fiturnya

<img width="1681" height="946" alt="image" src="https://github.com/user-attachments/assets/12e25f7d-3397-4b11-8297-69a533ab95e8" />

**2.9 Menambahkan Data Lokasi Penyimpanan atau SPBU**

Administrator mengisi informasi lokasi berdasarkan jenis lokasi yang akan didaftarkan. Setelah data dilengkapi, Administrator menyimpan data tersebut agar dapat digunakan dalam proses penerimaan dan distribusi BBM.

Pada tahap ini dilakukan penambahan lokasi penyimpanan

<img width="1678" height="947" alt="image" src="https://github.com/user-attachments/assets/39a4f652-a157-4308-9aad-09ec82092f0a" />



Pada tahap ini dilakukan penambahan SPBU

<img width="1682" height="946" alt="image" src="https://github.com/user-attachments/assets/0a8f2583-7d36-4345-8303-bd09ce8e66e5" />



#### 3. Demonstrasi Penerimaan BBM oleh Petugas Penyimpanan

**3.1 Login Petugas Penyimpanan**

Petugas Penyimpanan melakukan login menggunakan akun yang telah dibuat oleh Administrator. Setelah berhasil masuk, sistem menampilkan Dashboard Petugas Penyimpanan.

![Dashboard Petugas Penyimpanan](docs/screenshots/demo/13-dashboard-petugas-penyimpanan.png)

**3.2 Menampilkan Data Penerimaan BBM**

Petugas Penyimpanan membuka menu Penerimaan BBM untuk melihat daftar transaksi penerimaan yang telah tercatat dalam sistem.

![Data Penerimaan BBM](docs/screenshots/demo/14-data-penerimaan-bbm.png)

**3.3 Menambahkan Data Penerimaan BBM**

Petugas Penyimpanan memilih tombol Tambah untuk membuka form penerimaan BBM. Petugas kemudian mengisi informasi penerimaan sesuai data yang tersedia, seperti jenis BBM, jumlah penerimaan, lokasi penyimpanan, serta informasi lainnya yang diminta oleh sistem. Setelah selesai, data disimpan ke dalam database.

![Form Tambah Penerimaan BBM](docs/screenshots/demo/15-form-penerimaan-bbm.png)

**3.4 Melihat Hasil Penerimaan BBM**

Setelah data berhasil disimpan, transaksi penerimaan akan ditampilkan pada tabel. Petugas dapat memeriksa kembali informasi penerimaan yang telah dimasukkan.

![Hasil Penerimaan BBM](docs/screenshots/demo/16-hasil-penerimaan-bbm.png)

#### 4. Demonstrasi Distribusi BBM oleh Petugas Penyimpanan

**4.1 Menampilkan Data Distribusi BBM**

Petugas Penyimpanan membuka menu Distribusi BBM untuk melihat daftar transaksi distribusi yang telah tercatat dalam sistem.

![Data Distribusi BBM](docs/screenshots/demo/17-data-distribusi-bbm.png)

**4.2 Menambahkan Data Distribusi BBM**

Petugas memilih tombol Tambah untuk membuka form distribusi. Selanjutnya, Petugas menentukan jenis BBM, lokasi asal, SPBU tujuan, dan jumlah BBM yang akan didistribusikan sesuai informasi yang tersedia pada form.

![Form Distribusi BBM](docs/screenshots/demo/18-form-distribusi-bbm.png)

**4.3 Melihat Hasil Distribusi BBM**

Setelah transaksi disimpan, data distribusi akan ditampilkan pada tabel. Informasi tersebut digunakan untuk memantau pengiriman BBM dari lokasi penyimpanan menuju SPBU.

![Hasil Distribusi BBM](docs/screenshots/demo/19-hasil-distribusi-bbm.png)

#### 5. Demonstrasi Konfirmasi Penerimaan oleh Petugas SPBU

**5.1 Login Petugas SPBU**

Petugas SPBU melakukan login menggunakan akun yang telah terdaftar. Setelah berhasil masuk, sistem menampilkan Dashboard Petugas SPBU.

![Dashboard Petugas SPBU](docs/screenshots/demo/20-dashboard-petugas-spbu.png)

**5.2 Menampilkan Data Distribusi Masuk**

Petugas SPBU membuka menu penerimaan atau konfirmasi distribusi untuk melihat informasi pengiriman BBM yang ditujukan kepada SPBU tersebut.

![Data Distribusi Masuk](docs/screenshots/demo/21-data-distribusi-masuk.png)

**5.3 Melakukan Konfirmasi Penerimaan BBM**

Petugas SPBU memilih transaksi yang akan dikonfirmasi, memeriksa informasi pengiriman, kemudian melakukan konfirmasi penerimaan sesuai kondisi transaksi dan fitur yang tersedia pada aplikasi.

![Konfirmasi Penerimaan BBM](docs/screenshots/demo/22-konfirmasi-penerimaan-bbm.png)

**5.4 Melihat Riwayat Distribusi**

Setelah proses konfirmasi dilakukan, Petugas SPBU dapat melihat riwayat distribusi untuk memeriksa transaksi yang telah diterima dan statusnya.

![Riwayat Distribusi](docs/screenshots/demo/23-riwayat-distribusi.png)

#### 6. Demonstrasi Pemeriksaan Stok BBM

**6.1 Menampilkan Informasi Stok BBM**

Pengguna membuka menu Stok BBM untuk melihat informasi ketersediaan BBM berdasarkan data transaksi yang telah tercatat dalam sistem.

![Informasi Stok BBM](docs/screenshots/demo/24-informasi-stok-bbm.png)

**6.2 Memeriksa Perubahan Stok**

Pengguna memeriksa perubahan jumlah stok setelah proses penerimaan dan distribusi BBM. Informasi ini digunakan untuk memastikan pencatatan persediaan sesuai dengan transaksi yang dilakukan.

![Perubahan Stok BBM](docs/screenshots/demo/25-perubahan-stok-bbm.png)

#### 7. Logout

**7.1 Logout Pengguna**

Setelah selesai menggunakan aplikasi, pengguna menekan tombol Logout untuk mengakhiri sesi. Sistem kemudian mengarahkan pengguna kembali ke halaman Login.

![Logout](docs/screenshots/demo/26-logout.png)

## Tim Pengembang

| No. | Nama | NIM |
|:---:|---|---|
| 1 | [Nama anggota] | [NIM] |
| 2 | [Nama anggota] | [NIM] |
| 3 | [Nama anggota] | [NIM] |
| 4 | [Nama anggota] | [NIM] |
| 5 | [Nama anggota] | [NIM] |

---

**OCTANE**  
*Fuel for a Better Tomorrow*  
Program Studi Sistem Informasi · Fakultas Teknik · Universitas Mulawarman · 2026
