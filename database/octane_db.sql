-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 06, 2026 at 10:53 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `octane_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrator`
--

CREATE TABLE `administrator` (
  `id_pengguna` int NOT NULL,
  `tanggal_pengangkatan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `administrator`
--

INSERT INTO `administrator` (`id_pengguna`, `tanggal_pengangkatan`) VALUES
(1, '2026-10-01');

-- --------------------------------------------------------

--
-- Table structure for table `distribusi_bbm`
--

CREATE TABLE `distribusi_bbm` (
  `id_distribusi` int NOT NULL,
  `id_jenis_bbm` int NOT NULL,
  `id_lokasi_penyimpanan` int NOT NULL,
  `id_spbu` int NOT NULL,
  `id_petugas_penyimpanan` int NOT NULL,
  `tanggal_distribusi` datetime NOT NULL,
  `jumlah_dikirim` decimal(15,2) NOT NULL,
  `status_distribusi` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `distribusi_bbm`
--

INSERT INTO `distribusi_bbm` (`id_distribusi`, `id_jenis_bbm`, `id_lokasi_penyimpanan`, `id_spbu`, `id_petugas_penyimpanan`, `tanggal_distribusi`, `jumlah_dikirim`, `status_distribusi`) VALUES
(1, 1, 1, 1, 2, '2026-10-11 08:00:00', '2000.00', 'SELESAI'),
(2, 2, 1, 2, 3, '2026-10-11 10:00:00', '1500.00', 'MENUNGGU_KONFIRMASI'),
(3, 4, 1, 3, 4, '2026-10-12 08:30:00', '2500.00', 'DIPROSES'),
(4, 3, 2, 4, 5, '2026-10-12 13:00:00', '1000.00', 'PERLU_PEMERIKSAAN'),
(5, 1, 2, 5, 6, '2026-10-13 09:00:00', '1800.00', 'SELESAI_DITANGANI'),
(6, 5, 2, 6, 7, '2026-10-13 14:00:00', '1200.00', 'SELESAI'),
(7, 4, 3, 7, 8, '2026-10-14 08:15:00', '2200.00', 'MENUNGGU_KONFIRMASI'),
(8, 2, 3, 8, 9, '2026-10-14 11:30:00', '1300.00', 'DIPROSES'),
(9, 6, 3, 9, 10, '2026-10-15 09:45:00', '800.00', 'PERLU_PEMERIKSAAN'),
(10, 1, 4, 10, 11, '2026-10-16 08:00:00', '1700.00', 'SELESAI'),
(11, 4, 4, 11, 12, '2026-10-16 13:30:00', '2000.00', 'SELESAI_DITANGANI'),
(12, 5, 4, 12, 13, '2026-10-17 10:15:00', '1100.00', 'MENUNGGU_KONFIRMASI'),
(13, 2, 5, 13, 14, '2026-10-18 08:45:00', '1400.00', 'DIPROSES'),
(14, 3, 5, 14, 15, '2026-10-19 09:30:00', '900.00', 'PERLU_PEMERIKSAAN'),
(15, 4, 5, 15, 16, '2026-10-20 07:30:00', '1600.00', 'SELESAI');

-- --------------------------------------------------------

--
-- Table structure for table `jenis_bbm`
--

CREATE TABLE `jenis_bbm` (
  `id_jenis_bbm` int NOT NULL,
  `nama_bbm` varchar(50) NOT NULL,
  `satuan` varchar(10) NOT NULL,
  `status_aktif` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `jenis_bbm`
--

INSERT INTO `jenis_bbm` (`id_jenis_bbm`, `nama_bbm`, `satuan`, `status_aktif`) VALUES
(1, 'Pertalite', 'liter', 'AKTIF'),
(2, 'Pertamax', 'liter', 'AKTIF'),
(3, 'Pertamax Turbo', 'liter', 'AKTIF'),
(4, 'Solar', 'liter', 'AKTIF'),
(5, 'Dexlite', 'liter', 'AKTIF'),
(6, 'Pertamina Dex', 'liter', 'AKTIF');

-- --------------------------------------------------------

--
-- Table structure for table `konfirmasi_penerimaan`
--

CREATE TABLE `konfirmasi_penerimaan` (
  `id_konfirmasi` int NOT NULL,
  `id_distribusi` int NOT NULL,
  `id_petugas_spbu` int NOT NULL,
  `tanggal_konfirmasi` datetime NOT NULL,
  `jumlah_aktual` decimal(15,2) NOT NULL,
  `keterangan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `konfirmasi_penerimaan`
--

INSERT INTO `konfirmasi_penerimaan` (`id_konfirmasi`, `id_distribusi`, `id_petugas_spbu`, `tanggal_konfirmasi`, `jumlah_aktual`, `keterangan`) VALUES
(1, 1, 22, '2026-10-11 09:15:00', '2000.00', 'Jumlah diterima sesuai dengan jumlah yang dikirim dan telah diverifikasi petugas SPBU'),
(2, 4, 28, '2026-10-12 14:10:00', '950.00', 'Selisih 50 liter pada SPBU Loa Janan, menunggu hasil pemeriksaan'),
(3, 5, 30, '2026-10-13 10:20:00', '1800.00', 'Penerimaan telah diperiksa dan masalah selesai ditangani'),
(4, 6, 32, '2026-10-13 15:00:00', '1200.00', 'Jumlah diterima sesuai dengan jumlah yang dikirim'),
(5, 9, 38, '2026-10-15 10:30:00', '760.00', 'Selisih 40 liter pada SPBU Lempake, menunggu hasil pemeriksaan'),
(6, 10, 40, '2026-10-16 09:10:00', '1700.00', 'Jumlah diterima sesuai dengan jumlah yang dikirim'),
(7, 11, 42, '2026-10-16 14:45:00', '2000.00', 'Penerimaan telah diperiksa dan masalah selesai ditangani'),
(8, 14, 45, '2026-10-19 10:20:00', '850.00', 'Selisih 50 liter pada SPBU Sambutan, menunggu hasil pemeriksaan'),
(9, 15, 46, '2026-10-20 08:45:00', '1600.00', 'Jumlah diterima sesuai dengan jumlah yang dikirim');

-- --------------------------------------------------------

--
-- Table structure for table `lokasi_penyimpanan`
--

CREATE TABLE `lokasi_penyimpanan` (
  `id_lokasi_penyimpanan` int NOT NULL,
  `nama_lokasi` varchar(100) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `status_aktif` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `lokasi_penyimpanan`
--

INSERT INTO `lokasi_penyimpanan` (`id_lokasi_penyimpanan`, `nama_lokasi`, `alamat`, `status_aktif`) VALUES
(1, 'Gudang Utama Samarinda', 'Jl. Palaran, Samarinda', 'AKTIF'),
(2, 'Gudang Penyimpanan Sungai Kunjang', 'Jl. Untung Suropati, Samarinda', 'AKTIF'),
(3, 'Gudang Penyimpanan Loa Janan', 'Jl. Soekarno-Hatta, Loa Janan', 'AKTIF'),
(4, 'Gudang Samarinda Seberang', 'Jl. Bung Tomo, Samarinda', 'AKTIF'),
(5, 'Gudang Palaran', 'Jl. Trikora, Samarinda', 'AKTIF');

-- --------------------------------------------------------

--
-- Table structure for table `penerimaan_bbm`
--

CREATE TABLE `penerimaan_bbm` (
  `id_penerimaan` int NOT NULL,
  `id_jenis_bbm` int NOT NULL,
  `id_lokasi_penyimpanan` int NOT NULL,
  `id_petugas_penyimpanan` int NOT NULL,
  `tanggal_penerimaan` datetime NOT NULL,
  `jumlah_diterima` decimal(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `penerimaan_bbm`
--

INSERT INTO `penerimaan_bbm` (`id_penerimaan`, `id_jenis_bbm`, `id_lokasi_penyimpanan`, `id_petugas_penyimpanan`, `tanggal_penerimaan`, `jumlah_diterima`) VALUES
(16, 1, 1, 2, '2026-10-01 08:00:00', '5000.00'),
(17, 2, 1, 3, '2026-10-01 10:30:00', '4000.00'),
(18, 4, 1, 4, '2026-10-02 09:15:00', '6000.00'),
(19, 3, 2, 5, '2026-10-03 08:45:00', '2500.00'),
(20, 1, 2, 6, '2026-10-03 13:00:00', '3500.00'),
(21, 5, 2, 7, '2026-10-04 10:00:00', '2000.00'),
(22, 4, 3, 8, '2026-10-05 07:30:00', '4500.00'),
(23, 2, 3, 9, '2026-10-05 11:00:00', '3000.00'),
(24, 6, 3, 10, '2026-10-06 09:00:00', '1800.00'),
(25, 1, 4, 11, '2026-10-07 08:30:00', '3200.00'),
(26, 4, 4, 12, '2026-10-07 14:00:00', '4000.00'),
(27, 5, 4, 13, '2026-10-08 10:15:00', '2200.00'),
(28, 2, 5, 14, '2026-10-09 09:30:00', '2800.00'),
(29, 3, 5, 15, '2026-10-09 13:45:00', '1600.00'),
(30, 4, 5, 16, '2026-10-10 08:15:00', '3800.00');

-- --------------------------------------------------------

--
-- Table structure for table `pengguna`
--

CREATE TABLE `pengguna` (
  `id_pengguna` int NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status_aktif` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pengguna`
--

INSERT INTO `pengguna` (`id_pengguna`, `nama_lengkap`, `username`, `password`, `status_aktif`) VALUES
(1, 'Administrator Octane', 'admin_octane', 'Admin123!', 'AKTIF'),
(2, 'Andi Pratama', 'andi_penyimpanan', 'Simpan123!', 'NONAKTIF'),
(3, 'Budi Santoso', 'budi_penyimpanan', 'Simpan124!', 'NONAKTIF'),
(4, 'Citra Lestari', 'citra_penyimpanan', 'Simpan125!', 'AKTIF'),
(5, 'Dedi Kurniawan', 'dedi_penyimpanan', 'Simpan126!', 'AKTIF'),
(6, 'Eko Saputra', 'eko_penyimpanan', 'Simpan127!', 'AKTIF'),
(7, 'Fitri Handayani', 'fitri_penyimpanan', 'Simpan128!', 'AKTIF'),
(8, 'Gilang Ramadhan', 'gilang_penyimpanan', 'Simpan129!', 'AKTIF'),
(9, 'Hendra Wijaya', 'hendra_penyimpanan', 'Simpan130!', 'AKTIF'),
(10, 'Indah Permata', 'indah_penyimpanan', 'Simpan131!', 'AKTIF'),
(11, 'Joko Susilo', 'joko_penyimpanan', 'Simpan132!', 'AKTIF'),
(12, 'Kurniawan Putra', 'kurniawan_penyimpanan', 'Simpan133!', 'AKTIF'),
(13, 'Lina Marlina', 'lina_penyimpanan', 'Simpan134!', 'AKTIF'),
(14, 'Maya Sari', 'maya_penyimpanan', 'Simpan135!', 'AKTIF'),
(15, 'Nanda Saputra', 'nanda_penyimpanan', 'Simpan136!', 'AKTIF'),
(16, 'Oki Setiawan', 'oki_penyimpanan', 'Simpan137!', 'AKTIF'),
(17, 'Putri Amelia', 'putri_penyimpanan', 'Simpan138!', 'AKTIF'),
(18, 'Qori Ananda', 'qori_penyimpanan', 'Simpan139!', 'AKTIF'),
(19, 'Rian Firmansyah', 'rian_penyimpanan', 'Simpan140!', 'AKTIF'),
(20, 'Salsa Nuraini', 'salsa_penyimpanan', 'Simpan141!', 'AKTIF'),
(21, 'Teguh Prakoso', 'teguh_penyimpanan', 'Simpan142!', 'AKTIF'),
(22, 'Umar Faruq', 'umar_spbu', 'SPBU123!', 'AKTIF'),
(23, 'Vina Oktaviani', 'vina_spbu', 'SPBU124!', 'AKTIF'),
(24, 'Wahyu Hidayat', 'wahyu_spbu', 'SPBU125!', 'AKTIF'),
(25, 'Yuni Astuti', 'yuni_spbu', 'SPBU126!', 'AKTIF'),
(26, 'Zaki Mubarak', 'zaki_spbu', 'SPBU127!', 'AKTIF'),
(27, 'Ayu Wulandari', 'ayu_spbu', 'SPBU128!', 'AKTIF'),
(28, 'Bagas Firmansyah', 'bagas_spbu', 'SPBU129!', 'AKTIF'),
(29, 'Dina Aprilia', 'dina_spbu', 'SPBU130!', 'AKTIF'),
(30, 'Fajar Maulana', 'fajar_spbu', 'SPBU131!', 'AKTIF'),
(31, 'Gita Maharani', 'gita_spbu', 'SPBU132!', 'AKTIF'),
(32, 'Ilham Akbar', 'ilham_spbu', 'SPBU133!', 'AKTIF'),
(33, 'Kartika Dewi', 'kartika_spbu', 'SPBU134!', 'AKTIF'),
(34, 'Lukman Hakim', 'lukman_spbu', 'SPBU135!', 'AKTIF'),
(35, 'Nia Ramadhani', 'nia_spbu', 'SPBU136!', 'AKTIF'),
(36, 'Oscar Firmansyah', 'oscar_spbu', 'SPBU137!', 'AKTIF'),
(37, 'Rara Puspita', 'rara_spbu', 'SPBU138!', 'AKTIF'),
(38, 'Surya Dharma', 'surya_spbu', 'SPBU139!', 'AKTIF'),
(39, 'Tiara Anjani', 'tiara_spbu', 'SPBU140!', 'AKTIF'),
(40, 'Yusuf Maulana', 'yusuf_spbu', 'SPBU141!', 'AKTIF'),
(41, 'Zahra Khairunnisa', 'zahra_spbu', 'SPBU142!', 'AKTIF'),
(42, 'Agus Salim', 'agus_spbu', 'SPBU143!', 'AKTIF'),
(43, 'Bella Anjani', 'bella_spbu', 'SPBU144!', 'AKTIF'),
(44, 'Chandra Putra', 'chandra_spbu', 'SPBU145!', 'AKTIF'),
(45, 'Dewi Anggraini', 'dewi_spbu', 'SPBU146!', 'AKTIF'),
(46, 'Fahmi Akbar', 'fahmi_spbu', 'SPBU147!', 'AKTIF'),
(50, 'Farel', 'farel_spbu', 'SPBU897', 'AKTIF'),
(51, 'Farelawan', 'fareludi', '123', 'AKTIF');

-- --------------------------------------------------------

--
-- Table structure for table `petugas_penyimpanan`
--

CREATE TABLE `petugas_penyimpanan` (
  `id_pengguna` int NOT NULL,
  `kualifikasi_sertifikasi` varchar(100) NOT NULL,
  `id_lokasi_penyimpanan` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `petugas_penyimpanan`
--

INSERT INTO `petugas_penyimpanan` (`id_pengguna`, `kualifikasi_sertifikasi`, `id_lokasi_penyimpanan`) VALUES
(2, 'Sertifikasi Pengelolaan BBM', 1),
(3, 'Sertifikasi Keselamatan Kerja', 1),
(4, 'Sertifikasi Operasional Gudang', 1),
(5, 'Sertifikasi Pengelolaan BBM', 2),
(6, 'Sertifikasi Keselamatan Kerja', 2),
(7, 'Sertifikasi Operasional Gudang', 2),
(8, 'Sertifikasi Pengelolaan BBM', 3),
(9, 'Sertifikasi Keselamatan Kerja', 3),
(10, 'Sertifikasi Operasional Gudang', 3),
(11, 'Sertifikasi Pengelolaan BBM', 4),
(12, 'Sertifikasi Pengelolaan BBM', 4),
(13, 'Sertifikasi Pengelolaan BBM', 4),
(14, 'Sertifikasi Keselamatan Kerja', 5),
(15, 'Sertifikasi Keselamatan Kerja', 5),
(16, 'Sertifikasi Keselamatan Kerja', 5),
(17, 'Pelatihan Manajemen Penyimpanan BBM', 1),
(18, 'Sertifikasi Keselamatan dan Kesehatan Kerja (K3)', 2),
(19, 'Pelatihan Penanganan Bahan Berbahaya dan Beracun (B3)', 3),
(20, 'Pelatihan Pengendalian Mutu dan Kualitas BBM', 4),
(21, 'Pelatihan Operasional dan Pengawasan Stok BBM', 5);

-- --------------------------------------------------------

--
-- Table structure for table `petugas_spbu`
--

CREATE TABLE `petugas_spbu` (
  `id_pengguna` int NOT NULL,
  `id_spbu` int NOT NULL,
  `status_pelatihan_penerimaan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `petugas_spbu`
--

INSERT INTO `petugas_spbu` (`id_pengguna`, `id_spbu`, `status_pelatihan_penerimaan`) VALUES
(22, 1, 'LULUS'),
(23, 1, 'LULUS'),
(24, 2, 'LULUS'),
(25, 2, 'BELUM'),
(26, 3, 'LULUS'),
(27, 3, 'BELUM'),
(28, 4, 'LULUS'),
(29, 4, 'BELUM'),
(30, 5, 'LULUS'),
(31, 5, 'BELUM'),
(32, 6, 'LULUS'),
(33, 6, 'BELUM'),
(34, 7, 'LULUS'),
(35, 7, 'BELUM'),
(36, 8, 'LULUS'),
(37, 8, 'BELUM'),
(38, 9, 'LULUS'),
(39, 9, 'BELUM'),
(40, 10, 'LULUS'),
(41, 10, 'BELUM'),
(42, 11, 'LULUS'),
(43, 12, 'BELUM'),
(44, 13, 'LULUS'),
(45, 14, 'BELUM'),
(46, 15, 'LULUS'),
(50, 10, 'BELUM'),
(51, 3, 'LULUS');

-- --------------------------------------------------------

--
-- Table structure for table `spbu`
--

CREATE TABLE `spbu` (
  `id_spbu` int NOT NULL,
  `nama_spbu` varchar(100) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `status_aktif` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `spbu`
--

INSERT INTO `spbu` (`id_spbu`, `nama_spbu`, `alamat`, `status_aktif`) VALUES
(1, 'SPBU Samarinda Kota', 'Jl. Jenderal Sudirman, Samarinda', 'AKTIF'),
(2, 'SPBU Sungai Kunjang', 'Jl. Untung Suropati, Samarinda', 'AKTIF'),
(3, 'SPBU Air Hitam', 'Jl. AW Syahranie, Samarinda', 'AKTIF'),
(4, 'SPBU Loa Janan', 'Jl. Soekarno-Hatta, Loa Janan', 'AKTIF'),
(5, 'SPBU Palaran', 'Jl. Trikora, Samarinda', 'AKTIF'),
(6, 'SPBU Sempaja', 'Jl. PM Noor, Samarinda', 'AKTIF'),
(7, 'SPBU Mugirejo', 'Jl. Damanhuri, Samarinda', 'AKTIF'),
(8, 'SPBU Samarinda Seberang', 'Jl. Bung Tomo, Samarinda', 'AKTIF'),
(9, 'SPBU Lempake', 'Jl. Kebun Agung, Samarinda', 'AKTIF'),
(10, 'SPBU Juanda', 'Jl. Juanda, Samarinda', 'AKTIF'),
(11, 'SPBU Bengkuring', 'Jl. Bengkuring Raya, Samarinda', 'AKTIF'),
(12, 'SPBU Harapan Baru', 'Jl. Cipto Mangunkusumo, Samarinda', 'AKTIF'),
(13, 'SPBU Rapak Dalam', 'Jl. Sultan Hasanuddin, Samarinda', 'AKTIF'),
(14, 'SPBU Sambutan', 'Jl. Sultan Sulaiman, Samarinda', 'AKTIF'),
(15, 'SPBU Tanah Merah', 'Jl. Poros Samarinda-Bontang, Samarinda', 'AKTIF');

-- --------------------------------------------------------

--
-- Table structure for table `stok_penyimpanan`
--

CREATE TABLE `stok_penyimpanan` (
  `id_stok_penyimpanan` int NOT NULL,
  `id_lokasi_penyimpanan` int NOT NULL,
  `id_jenis_bbm` int NOT NULL,
  `jumlah_stok` decimal(15,2) NOT NULL,
  `terakhir_diperbarui` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `stok_penyimpanan`
--

INSERT INTO `stok_penyimpanan` (`id_stok_penyimpanan`, `id_lokasi_penyimpanan`, `id_jenis_bbm`, `jumlah_stok`, `terakhir_diperbarui`) VALUES
(1, 1, 1, '15000.00', '2026-10-30 05:24:19'),
(2, 1, 2, '12000.00', '2026-10-30 05:24:19'),
(3, 1, 3, '8000.00', '2026-10-30 05:24:19'),
(4, 1, 4, '18000.00', '2026-10-30 05:24:19'),
(5, 1, 5, '9000.00', '2026-10-30 05:24:19'),
(6, 1, 6, '7000.00', '2026-10-30 05:24:19'),
(7, 2, 1, '10000.00', '2026-10-30 05:24:19'),
(8, 2, 2, '8500.00', '2026-10-30 05:24:19'),
(9, 2, 3, '5000.00', '2026-10-30 05:24:19'),
(10, 2, 4, '12000.00', '2026-10-30 05:24:19'),
(11, 2, 5, '6000.00', '2026-10-30 05:24:19'),
(12, 2, 6, '4500.00', '2026-10-30 05:24:19'),
(13, 3, 1, '9000.00', '2026-10-30 05:24:19'),
(14, 3, 2, '7500.00', '2026-10-30 05:24:19'),
(15, 3, 3, '4000.00', '2026-10-30 05:24:19'),
(16, 3, 4, '11000.00', '2026-10-30 05:24:19'),
(17, 3, 5, '5500.00', '2026-10-30 05:24:19'),
(18, 3, 6, '4000.00', '2026-10-30 05:24:19'),
(19, 4, 1, '8000.00', '2026-10-30 05:24:19'),
(20, 4, 2, '6500.00', '2026-10-30 05:24:19'),
(21, 4, 3, '3500.00', '2026-10-30 05:24:19'),
(22, 4, 4, '9500.00', '2026-10-30 05:24:19'),
(23, 4, 5, '4500.00', '2026-10-30 05:24:19'),
(24, 4, 6, '3000.00', '2026-10-30 05:24:19'),
(25, 5, 1, '7000.00', '2026-10-30 05:24:19'),
(26, 5, 2, '5500.00', '2026-10-30 05:24:19'),
(27, 5, 3, '3000.00', '2026-10-30 05:24:19'),
(28, 5, 4, '8500.00', '2026-10-30 05:24:19'),
(29, 5, 5, '4000.00', '2026-10-30 05:24:19'),
(30, 5, 6, '2500.00', '2026-10-30 05:24:19');

-- --------------------------------------------------------

--
-- Table structure for table `stok_spbu`
--

CREATE TABLE `stok_spbu` (
  `id_stok_spbu` int NOT NULL,
  `id_spbu` int NOT NULL,
  `id_jenis_bbm` int NOT NULL,
  `jumlah_stok` decimal(15,2) NOT NULL,
  `terakhir_diperbarui` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `stok_spbu`
--

INSERT INTO `stok_spbu` (`id_stok_spbu`, `id_spbu`, `id_jenis_bbm`, `jumlah_stok`, `terakhir_diperbarui`) VALUES
(1, 1, 1, '2500.00', '2026-10-30 05:27:19'),
(2, 1, 2, '1800.00', '2026-10-30 05:27:19'),
(3, 1, 3, '1000.00', '2026-10-30 05:27:19'),
(4, 1, 4, '3000.00', '2026-10-30 05:27:19'),
(5, 1, 5, '1500.00', '2026-10-30 05:27:19'),
(6, 1, 6, '800.00', '2026-10-30 05:27:19'),
(7, 2, 1, '2200.00', '2026-10-30 05:27:19'),
(8, 2, 2, '1600.00', '2026-10-30 05:27:19'),
(9, 2, 3, '900.00', '2026-10-30 05:27:19'),
(10, 2, 4, '2800.00', '2026-10-30 05:27:19'),
(11, 2, 5, '1300.00', '2026-10-30 05:27:19'),
(12, 2, 6, '700.00', '2026-10-30 05:27:19'),
(13, 3, 1, '2000.00', '2026-10-30 05:27:19'),
(14, 3, 2, '1500.00', '2026-10-30 05:27:19'),
(15, 3, 3, '800.00', '2026-10-30 05:27:19'),
(16, 3, 4, '2600.00', '2026-10-30 05:27:19'),
(17, 3, 5, '1200.00', '2026-10-30 05:27:19'),
(18, 3, 6, '650.00', '2026-10-30 05:27:19'),
(19, 4, 1, '1800.00', '2026-10-30 05:27:19'),
(20, 4, 2, '1400.00', '2026-10-30 05:27:19'),
(21, 4, 3, '750.00', '2026-10-30 05:27:19'),
(22, 4, 4, '2400.00', '2026-10-30 05:27:19'),
(23, 4, 5, '1100.00', '2026-10-30 05:27:19'),
(24, 4, 6, '600.00', '2026-10-30 05:27:19'),
(25, 5, 1, '1700.00', '2026-10-30 05:27:19'),
(26, 5, 2, '1300.00', '2026-10-30 05:27:19'),
(27, 5, 3, '700.00', '2026-10-30 05:27:19'),
(28, 5, 4, '2300.00', '2026-10-30 05:27:19'),
(29, 5, 5, '1000.00', '2026-10-30 05:27:19'),
(30, 5, 6, '550.00', '2026-10-30 05:27:19'),
(31, 6, 1, '2400.00', '2026-10-30 05:27:19'),
(32, 6, 2, '1700.00', '2026-10-30 05:27:19'),
(33, 6, 3, '950.00', '2026-10-30 05:27:19'),
(34, 6, 4, '2900.00', '2026-10-30 05:27:19'),
(35, 6, 5, '1400.00', '2026-10-30 05:27:19'),
(36, 6, 6, '750.00', '2026-10-30 05:27:19'),
(37, 7, 1, '1600.00', '2026-10-30 05:27:19'),
(38, 7, 2, '1200.00', '2026-10-30 05:27:19'),
(39, 7, 3, '650.00', '2026-10-30 05:27:19'),
(40, 7, 4, '2100.00', '2026-10-30 05:27:19'),
(41, 7, 5, '950.00', '2026-10-30 05:27:19'),
(42, 7, 6, '500.00', '2026-10-30 05:27:19'),
(43, 8, 1, '1900.00', '2026-10-30 05:27:19'),
(44, 8, 2, '1450.00', '2026-10-30 05:27:19'),
(45, 8, 3, '780.00', '2026-10-30 05:27:19'),
(46, 8, 4, '2500.00', '2026-10-30 05:27:19'),
(47, 8, 5, '1150.00', '2026-10-30 05:27:19'),
(48, 8, 6, '620.00', '2026-10-30 05:27:19'),
(49, 9, 1, '1500.00', '2026-10-30 05:27:19'),
(50, 9, 2, '1100.00', '2026-10-30 05:27:19'),
(51, 9, 3, '600.00', '2026-10-30 05:27:19'),
(52, 9, 4, '2000.00', '2026-10-30 05:27:19'),
(53, 9, 5, '900.00', '2026-10-30 05:27:19'),
(54, 9, 6, '480.00', '2026-10-30 05:27:19'),
(55, 10, 1, '2300.00', '2026-10-30 05:27:19'),
(56, 10, 2, '1650.00', '2026-10-30 05:27:19'),
(57, 10, 3, '920.00', '2026-10-30 05:27:19'),
(58, 10, 4, '2750.00', '2026-10-30 05:27:19'),
(59, 10, 5, '1350.00', '2026-10-30 05:27:19'),
(60, 10, 6, '720.00', '2026-10-30 05:27:19'),
(61, 11, 1, '1400.00', '2026-10-30 05:27:19'),
(62, 11, 2, '1000.00', '2026-10-30 05:27:19'),
(63, 11, 3, '550.00', '2026-10-30 05:27:19'),
(64, 11, 4, '1900.00', '2026-10-30 05:27:19'),
(65, 11, 5, '850.00', '2026-10-30 05:27:19'),
(66, 11, 6, '450.00', '2026-10-30 05:27:19'),
(67, 12, 1, '1750.00', '2026-10-30 05:27:19'),
(68, 12, 2, '1250.00', '2026-10-30 05:27:19'),
(69, 12, 3, '680.00', '2026-10-30 05:27:19'),
(70, 12, 4, '2200.00', '2026-10-30 05:27:19'),
(71, 12, 5, '980.00', '2026-10-30 05:27:19'),
(72, 12, 6, '520.00', '2026-10-30 05:27:19'),
(73, 13, 1, '1550.00', '2026-10-30 05:27:19'),
(74, 13, 2, '1150.00', '2026-10-30 05:27:19'),
(75, 13, 3, '620.00', '2026-10-30 05:27:19'),
(76, 13, 4, '2050.00', '2026-10-30 05:27:19'),
(77, 13, 5, '920.00', '2026-10-30 05:27:19'),
(78, 13, 6, '490.00', '2026-10-30 05:27:19'),
(79, 14, 1, '1650.00', '2026-10-30 05:27:19'),
(80, 14, 2, '1220.00', '2026-10-30 05:27:19'),
(81, 14, 3, '660.00', '2026-10-30 05:27:19'),
(82, 14, 4, '2150.00', '2026-10-30 05:27:19'),
(83, 14, 5, '960.00', '2026-10-30 05:27:19'),
(84, 14, 6, '510.00', '2026-10-30 05:27:19'),
(85, 15, 1, '1450.00', '2026-10-30 05:27:19'),
(86, 15, 2, '1050.00', '2026-10-30 05:27:19'),
(87, 15, 3, '580.00', '2026-10-30 05:27:19'),
(88, 15, 4, '1950.00', '2026-10-30 05:27:19'),
(89, 15, 5, '880.00', '2026-10-30 05:27:19'),
(90, 15, 6, '460.00', '2026-10-30 05:27:19');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrator`
--
ALTER TABLE `administrator`
  ADD PRIMARY KEY (`id_pengguna`);

--
-- Indexes for table `distribusi_bbm`
--
ALTER TABLE `distribusi_bbm`
  ADD PRIMARY KEY (`id_distribusi`),
  ADD KEY `fk_distribusi_jenis` (`id_jenis_bbm`),
  ADD KEY `fk_distribusi_lokasi` (`id_lokasi_penyimpanan`),
  ADD KEY `fk_distribusi_spbu` (`id_spbu`),
  ADD KEY `fk_distribusi_petugas` (`id_petugas_penyimpanan`);

--
-- Indexes for table `jenis_bbm`
--
ALTER TABLE `jenis_bbm`
  ADD PRIMARY KEY (`id_jenis_bbm`);

--
-- Indexes for table `konfirmasi_penerimaan`
--
ALTER TABLE `konfirmasi_penerimaan`
  ADD PRIMARY KEY (`id_konfirmasi`),
  ADD KEY `fk_konfirmasi_distribusi` (`id_distribusi`),
  ADD KEY `fk_konfirmasi_petugas_spbu` (`id_petugas_spbu`);

--
-- Indexes for table `lokasi_penyimpanan`
--
ALTER TABLE `lokasi_penyimpanan`
  ADD PRIMARY KEY (`id_lokasi_penyimpanan`);

--
-- Indexes for table `penerimaan_bbm`
--
ALTER TABLE `penerimaan_bbm`
  ADD PRIMARY KEY (`id_penerimaan`),
  ADD KEY `fk_penerimaan_jenis` (`id_jenis_bbm`),
  ADD KEY `fk_penerimaan_lokasi` (`id_lokasi_penyimpanan`),
  ADD KEY `fk_penerimaan_petugas` (`id_petugas_penyimpanan`);

--
-- Indexes for table `pengguna`
--
ALTER TABLE `pengguna`
  ADD PRIMARY KEY (`id_pengguna`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `petugas_penyimpanan`
--
ALTER TABLE `petugas_penyimpanan`
  ADD PRIMARY KEY (`id_pengguna`),
  ADD KEY `fk_petugas_penyimpanan_lokasi` (`id_lokasi_penyimpanan`);

--
-- Indexes for table `petugas_spbu`
--
ALTER TABLE `petugas_spbu`
  ADD PRIMARY KEY (`id_pengguna`),
  ADD KEY `fk_petugas_spbu_spbu` (`id_spbu`);

--
-- Indexes for table `spbu`
--
ALTER TABLE `spbu`
  ADD PRIMARY KEY (`id_spbu`);

--
-- Indexes for table `stok_penyimpanan`
--
ALTER TABLE `stok_penyimpanan`
  ADD PRIMARY KEY (`id_stok_penyimpanan`),
  ADD UNIQUE KEY `uq_stok_penyimpanan_lokasi_jenis` (`id_lokasi_penyimpanan`,`id_jenis_bbm`),
  ADD KEY `fk_stok_penyimpanan_jenis` (`id_jenis_bbm`);

--
-- Indexes for table `stok_spbu`
--
ALTER TABLE `stok_spbu`
  ADD PRIMARY KEY (`id_stok_spbu`),
  ADD UNIQUE KEY `uq_stok_spbu_spbu_jenis` (`id_spbu`,`id_jenis_bbm`),
  ADD KEY `fk_stok_spbu_jenis` (`id_jenis_bbm`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `distribusi_bbm`
--
ALTER TABLE `distribusi_bbm`
  MODIFY `id_distribusi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `jenis_bbm`
--
ALTER TABLE `jenis_bbm`
  MODIFY `id_jenis_bbm` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `konfirmasi_penerimaan`
--
ALTER TABLE `konfirmasi_penerimaan`
  MODIFY `id_konfirmasi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `lokasi_penyimpanan`
--
ALTER TABLE `lokasi_penyimpanan`
  MODIFY `id_lokasi_penyimpanan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `penerimaan_bbm`
--
ALTER TABLE `penerimaan_bbm`
  MODIFY `id_penerimaan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `pengguna`
--
ALTER TABLE `pengguna`
  MODIFY `id_pengguna` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `spbu`
--
ALTER TABLE `spbu`
  MODIFY `id_spbu` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `stok_penyimpanan`
--
ALTER TABLE `stok_penyimpanan`
  MODIFY `id_stok_penyimpanan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `stok_spbu`
--
ALTER TABLE `stok_spbu`
  MODIFY `id_stok_spbu` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `administrator`
--
ALTER TABLE `administrator`
  ADD CONSTRAINT `fk_administrator_pengguna` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`);

--
-- Constraints for table `distribusi_bbm`
--
ALTER TABLE `distribusi_bbm`
  ADD CONSTRAINT `fk_distribusi_jenis` FOREIGN KEY (`id_jenis_bbm`) REFERENCES `jenis_bbm` (`id_jenis_bbm`),
  ADD CONSTRAINT `fk_distribusi_lokasi` FOREIGN KEY (`id_lokasi_penyimpanan`) REFERENCES `lokasi_penyimpanan` (`id_lokasi_penyimpanan`),
  ADD CONSTRAINT `fk_distribusi_petugas` FOREIGN KEY (`id_petugas_penyimpanan`) REFERENCES `petugas_penyimpanan` (`id_pengguna`),
  ADD CONSTRAINT `fk_distribusi_spbu` FOREIGN KEY (`id_spbu`) REFERENCES `spbu` (`id_spbu`);

--
-- Constraints for table `konfirmasi_penerimaan`
--
ALTER TABLE `konfirmasi_penerimaan`
  ADD CONSTRAINT `fk_konfirmasi_distribusi` FOREIGN KEY (`id_distribusi`) REFERENCES `distribusi_bbm` (`id_distribusi`),
  ADD CONSTRAINT `fk_konfirmasi_petugas_spbu` FOREIGN KEY (`id_petugas_spbu`) REFERENCES `petugas_spbu` (`id_pengguna`);

--
-- Constraints for table `penerimaan_bbm`
--
ALTER TABLE `penerimaan_bbm`
  ADD CONSTRAINT `fk_penerimaan_jenis` FOREIGN KEY (`id_jenis_bbm`) REFERENCES `jenis_bbm` (`id_jenis_bbm`),
  ADD CONSTRAINT `fk_penerimaan_lokasi` FOREIGN KEY (`id_lokasi_penyimpanan`) REFERENCES `lokasi_penyimpanan` (`id_lokasi_penyimpanan`),
  ADD CONSTRAINT `fk_penerimaan_petugas` FOREIGN KEY (`id_petugas_penyimpanan`) REFERENCES `petugas_penyimpanan` (`id_pengguna`);

--
-- Constraints for table `petugas_penyimpanan`
--
ALTER TABLE `petugas_penyimpanan`
  ADD CONSTRAINT `fk_petugas_penyimpanan_lokasi` FOREIGN KEY (`id_lokasi_penyimpanan`) REFERENCES `lokasi_penyimpanan` (`id_lokasi_penyimpanan`),
  ADD CONSTRAINT `fk_petugas_penyimpanan_pengguna` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`);

--
-- Constraints for table `petugas_spbu`
--
ALTER TABLE `petugas_spbu`
  ADD CONSTRAINT `fk_petugas_spbu_pengguna` FOREIGN KEY (`id_pengguna`) REFERENCES `pengguna` (`id_pengguna`),
  ADD CONSTRAINT `fk_petugas_spbu_spbu` FOREIGN KEY (`id_spbu`) REFERENCES `spbu` (`id_spbu`);

--
-- Constraints for table `stok_penyimpanan`
--
ALTER TABLE `stok_penyimpanan`
  ADD CONSTRAINT `fk_stok_penyimpanan_jenis` FOREIGN KEY (`id_jenis_bbm`) REFERENCES `jenis_bbm` (`id_jenis_bbm`),
  ADD CONSTRAINT `fk_stok_penyimpanan_lokasi` FOREIGN KEY (`id_lokasi_penyimpanan`) REFERENCES `lokasi_penyimpanan` (`id_lokasi_penyimpanan`);

--
-- Constraints for table `stok_spbu`
--
ALTER TABLE `stok_spbu`
  ADD CONSTRAINT `fk_stok_spbu_jenis` FOREIGN KEY (`id_jenis_bbm`) REFERENCES `jenis_bbm` (`id_jenis_bbm`),
  ADD CONSTRAINT `fk_stok_spbu_spbu` FOREIGN KEY (`id_spbu`) REFERENCES `spbu` (`id_spbu`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
