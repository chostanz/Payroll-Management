SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `pegawai` (
  `id_pegawai` int(11) NOT NULL,
  `nik` varchar(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jabatan` varchar(100) DEFAULT NULL,
  `jenis_pegawai` enum('Tetap','Kontrak','PartTime') NOT NULL,
  `status` enum('Aktif','Nonaktif') DEFAULT 'Aktif',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pegawai_kontrak`
--

CREATE TABLE `pegawai_kontrak` (
  `id_kontrak` int(11) NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  `upah_per_bulan` decimal(15,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pegawai_parttime`
--

CREATE TABLE `pegawai_parttime` (
  `id_parttime` int(11) NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  `upah_per_jam` decimal(15,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pegawai_tetap`
--

CREATE TABLE `pegawai_tetap` (
  `id_tetap` int(11) NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  `gaji_pokok` decimal(15,2) DEFAULT 0.00,
  `tunjangan` decimal(15,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `penggajian`
--

CREATE TABLE `penggajian` (
  `id_penggajian` int(11) NOT NULL,
  `id_pegawai` int(11) NOT NULL,
  `bulan` tinyint(4) NOT NULL,
  `tahun` year(4) NOT NULL,
  `bonus` decimal(15,2) DEFAULT 0.00,
  `jumlah_absen` int(11) DEFAULT 0,
  `potongan_bpjs` decimal(15,2) DEFAULT 100000.00,
  `total_potongan` decimal(15,2) DEFAULT 0.00,
  `gaji_kotor` decimal(15,2) DEFAULT 0.00,
  `gaji_bersih` decimal(15,2) DEFAULT 0.00,
  `tanggal_proses` date DEFAULT NULL,
  `keterangan` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(100) NOT NULL,
  `role` enum('admin','operator') DEFAULT 'operator',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `nama_lengkap`, `role`, `created_at`) VALUES
(1, 'admin', 'admin', 'Administrator', 'admin', '2026-05-23 12:43:26');

-- --------------------------------------------------------
--
-- Indexes for table `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`id_pegawai`),
  ADD UNIQUE KEY `nik` (`nik`);

--
-- Indexes for table `pegawai_kontrak`
--
ALTER TABLE `pegawai_kontrak`
  ADD PRIMARY KEY (`id_kontrak`),
  ADD UNIQUE KEY `id_pegawai` (`id_pegawai`);

--
-- Indexes for table `pegawai_parttime`
--
ALTER TABLE `pegawai_parttime`
  ADD PRIMARY KEY (`id_parttime`),
  ADD UNIQUE KEY `id_pegawai` (`id_pegawai`);

--
-- Indexes for table `pegawai_tetap`
--
ALTER TABLE `pegawai_tetap`
  ADD PRIMARY KEY (`id_tetap`),
  ADD UNIQUE KEY `id_pegawai` (`id_pegawai`);

--
-- Indexes for table `penggajian`
--
ALTER TABLE `penggajian`
  ADD PRIMARY KEY (`id_penggajian`),
  ADD UNIQUE KEY `uq_penggajian` (`id_pegawai`,`bulan`,`tahun`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);


--
-- AUTO_INCREMENT for table `pegawai`
--
ALTER TABLE `pegawai`
  MODIFY `id_pegawai` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pegawai_kontrak`
--
ALTER TABLE `pegawai_kontrak`
  MODIFY `id_kontrak` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pegawai_parttime`
--
ALTER TABLE `pegawai_parttime`
  MODIFY `id_parttime` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pegawai_tetap`
--
ALTER TABLE `pegawai_tetap`
  MODIFY `id_tetap` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `penggajian`
--
ALTER TABLE `penggajian`
  MODIFY `id_penggajian` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `pegawai_kontrak`
--
ALTER TABLE `pegawai_kontrak`
  ADD CONSTRAINT `fk_kontrak_pegawai` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id_pegawai`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pegawai_parttime`
--
ALTER TABLE `pegawai_parttime`
  ADD CONSTRAINT `fk_parttime_pegawai` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id_pegawai`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pegawai_tetap`
--
ALTER TABLE `pegawai_tetap`
  ADD CONSTRAINT `fk_tetap_pegawai` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id_pegawai`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `penggajian`
--
ALTER TABLE `penggajian`
  ADD CONSTRAINT `fk_penggajian_pegawai` FOREIGN KEY (`id_pegawai`) REFERENCES `pegawai` (`id_pegawai`) ON UPDATE CASCADE;
COMMIT;
