-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 24, 2020 at 01:37 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_kos_sister`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE `akun` (
  `id_akun` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL DEFAULT 'shambhala',
  `id_phn` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`id_akun`, `username`, `password`, `id_phn`) VALUES
(1, 'admin', 'admin', 0),
(2, 'rehu', 'dipo83', 10),
(3, 'yosef', 'yosef', 8);

-- --------------------------------------------------------

--
-- Table structure for table `kamar`
--

CREATE TABLE `kamar` (
  `kode_kamar` int(11) NOT NULL,
  `lantai` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kamar`
--

INSERT INTO `kamar` (`kode_kamar`, `lantai`) VALUES
(1, '1'),
(2, '1'),
(3, '1'),
(4, '1'),
(5, '1'),
(6, '1'),
(7, '1'),
(8, '1'),
(9, '1'),
(10, '1'),
(11, '2'),
(12, '2'),
(13, '2'),
(14, '2'),
(15, '2'),
(16, '2'),
(17, '2'),
(18, '2'),
(19, '2'),
(20, '2'),
(21, '3'),
(22, '3'),
(23, '3'),
(24, '3'),
(25, '3'),
(26, '3'),
(27, '3'),
(28, '3'),
(29, '3'),
(30, '3');

-- --------------------------------------------------------

--
-- Table structure for table `penghuni`
--

CREATE TABLE `penghuni` (
  `id` int(11) NOT NULL,
  `nik` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kontak` varchar(20) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kode_kamar` int(11) NOT NULL,
  `tgl_masuk` date NOT NULL DEFAULT current_timestamp(),
  `tgl_keluar` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `penghuni`
--

INSERT INTO `penghuni` (`id`, `nik`, `nama`, `kontak`, `alamat`, `kode_kamar`, `tgl_masuk`, `tgl_keluar`) VALUES
(2, '2058433887640201', 'Erico', '+62-815-5554-33', 'Cirebon', 2, '2020-03-22', NULL),
(7, '2957952872480999', 'Choaz', '+62-859-5551-34', 'Makassar', 30, '2020-03-22', NULL),
(8, '2350769245608444', 'Yosef Febrianes', '+62-838-5555-14', 'Lobunta', 13, '2020-03-22', NULL),
(9, '5419378336204261', 'Henry', '+62-898-5552-07', 'Brebes', 10, '2020-03-22', NULL),
(10, '2387650912784587', 'Rehuel Paskahrio Handono', '081267904538', 'Kroya - Cilacap', 17, '2020-03-22', NULL),
(11, '6666', 'Adrian', '14069', 'Midgard', 7, '2020-03-22', NULL),
(12, '923894273987', 'Dwicky', '14045', 'Tanjung', 11, '2020-03-22', NULL),
(13, '93276428', 'Yeremia', '81738761123', 'Salatiga', 3, '2020-03-22', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_trans` int(11) NOT NULL,
  `bulan` varchar(30) DEFAULT NULL,
  `tahun` year(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_trans`, `bulan`, `tahun`) VALUES
(9, 'JANUARY', 2020),
(10, 'FEBRUARY', 2020);

--
-- Triggers `transaksi`
--
DELIMITER $$
CREATE TRIGGER `defaultMonth` BEFORE INSERT ON `transaksi` FOR EACH ROW SET NEW.bulan = IFNULL(NEW.bulan, UPPER(MONTHNAME(NOW())))
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `ins_month` BEFORE INSERT ON `transaksi` FOR EACH ROW SET NEW.bulan = UPPER(MONTHNAME(NOW()))
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `ins_year` BEFORE INSERT ON `transaksi` FOR EACH ROW SET NEW.tahun = YEAR(NOW())
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_det`
--

CREATE TABLE `transaksi_det` (
  `id_det` int(11) NOT NULL,
  `id_trans` int(11) NOT NULL,
  `id_phn` int(11) NOT NULL,
  `tgl_bayar` date DEFAULT NULL,
  `tagihan` int(11) NOT NULL,
  `status` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi_det`
--

INSERT INTO `transaksi_det` (`id_det`, `id_trans`, `id_phn`, `tgl_bayar`, `tagihan`, `status`) VALUES
(7, 9, 2, '2020-01-01', 600000, 1),
(8, 10, 2, '2020-02-26', 600000, 1),
(9, 9, 3, '2020-02-27', 600000, 1),
(10, 10, 3, '2020-02-27', 600000, 1),
(13, 9, 9, '2020-02-27', 600000, 1),
(14, 10, 9, '2020-02-27', 600000, 1),
(15, 9, 7, '2020-03-18', 600000, 1),
(16, 10, 7, '2020-03-01', 600000, 1),
(17, 9, 8, '2020-01-01', 0, 0),
(18, 10, 8, '2020-02-27', 600000, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`id_akun`);

--
-- Indexes for table `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`kode_kamar`);

--
-- Indexes for table `penghuni`
--
ALTER TABLE `penghuni`
  ADD PRIMARY KEY (`id`),
  ADD KEY `kode_kamar` (`kode_kamar`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_trans`);

--
-- Indexes for table `transaksi_det`
--
ALTER TABLE `transaksi_det`
  ADD PRIMARY KEY (`id_det`),
  ADD KEY `id_trans` (`id_trans`),
  ADD KEY `id_phn` (`id_phn`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `akun`
--
ALTER TABLE `akun`
  MODIFY `id_akun` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `kamar`
--
ALTER TABLE `kamar`
  MODIFY `kode_kamar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `penghuni`
--
ALTER TABLE `penghuni`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_trans` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `transaksi_det`
--
ALTER TABLE `transaksi_det`
  MODIFY `id_det` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `penghuni`
--
ALTER TABLE `penghuni`
  ADD CONSTRAINT `penghuni_ibfk_1` FOREIGN KEY (`kode_kamar`) REFERENCES `kamar` (`kode_kamar`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaksi_det`
--
ALTER TABLE `transaksi_det`
  ADD CONSTRAINT `transaksi_det_ibfk_1` FOREIGN KEY (`id_trans`) REFERENCES `transaksi` (`id_trans`) ON DELETE CASCADE ON UPDATE CASCADE;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `data_trans` ON SCHEDULE EVERY 1 MONTH STARTS '2020-01-01 00:00:01' ON COMPLETION NOT PRESERVE ENABLE DO INSERT INTO transaksi(bulan, tahun)
  VALUES('', '')$$

CREATE DEFINER=`root`@`localhost` EVENT `data_trans_det` ON SCHEDULE EVERY 1 MONTH STARTS '2020-01-01 00:30:00' ON COMPLETION NOT PRESERVE ENABLE DO INSERT INTO transaksi_det(id_trans, id_phn, tgl_bayar, tagihan, status)
SELECT d.id_trans, c.id, CURDATE(), '0', '0'
FROM penghuni c, transaksi d
WHERE d.bulan = (SELECT UPPER(MONTHNAME(CURDATE())))$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
