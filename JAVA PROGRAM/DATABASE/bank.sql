-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 29, 2019 at 06:02 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.1.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `ID` int(11) NOT NULL,
  `NID Number` bigint(12) NOT NULL,
  `First Name` varchar(20) NOT NULL,
  `Middle Name` varchar(20) NOT NULL,
  `Surname` varchar(20) NOT NULL,
  `E-mail Address` varchar(30) NOT NULL,
  `Mobile Number` int(10) NOT NULL,
  `Account Number` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`ID`, `NID Number`, `First Name`, `Middle Name`, `Surname`, `E-mail Address`, `Mobile Number`, `Account Number`) VALUES
(1, 200531848736, 'Rohan', 'Wade', 'Anderson', 'Rohan4@gmail.com', 764753147, 8001243764),
(2, 443451257841, 'Jace', 'Murphy', 'Adams', 'Adams_j@yahoo.com', 765791234, 9546842123),
(3, 874613255486, 'Morgan', 'Avery', 'Armstrong', 'Morgan21@gmail.com', 779842153, 8016463554),
(4, 124165310564, 'Rick', 'Edward', 'Brooks', 'Brooks@yahoo.com', 771367842, 5984684623),
(5, 415648413103, 'Franklin', 'Arden', 'Bradley', 'Bradley145@yahoo.com', 762404404, 8465161232),
(6, 248416164841, 'Olivia', 'Jane', 'Fletcher', 'Olivia_fl@gmail.com', 713548702, 8949651335),
(7, 894861104640, 'Selena', 'Naomi', 'Kelly', 'Selena_k@gmail.com', 764897512, 8495262163),
(8, 489481361605, 'Jacob', 'Francis', 'Johnson', 'jacob@yahoo.com', 763587421, 8949845162),
(9, 144165055654, 'Nuran', 'Fernando', 'Miller', 'Nuran_3211@gmail.com', 713589742, 9561654133),
(10, 165419489416, 'Lucas', 'Troy', 'Matthews', 'mathew@gmail.com', 773842316, 5261065161),
(11, 156448413104, 'Caitlin', 'Lane', 'Moore', 'Caitlin412@gmail.com', 713449843, 4169856616),
(12, 145484164106, 'John', 'Seth', 'Neil', 'neil7@gmail.com', 761578238, 8465320089),
(13, 200156724342, 'Rob', 'Felix', 'Parker', 'rob54@gmail.com', 763485721, 2306514965),
(14, 567863786786, 'Harry', 'Jordan', 'Phillips', 'phill@gmail.com', 713689427, 9846511653),
(15, 676786378678, 'Monica', 'Alice', 'Rose', 'Monica410@gmail.com', 761579412, 8469851261),
(16, 200143748675, 'Rachel', 'Rebecca', 'Reynolds', 'Rachel14@gmail.com', 773687516, 6765164689),
(17, 745572237783, 'Thomas', 'Luke', 'Roberts', 'Thomas123@gmail.com', 714691328, 1641989495),
(18, 200154387348, 'Sansa', 'Ellen', 'Palmer', 'Palmer_45@gmail.com', 767461384, 1851561616),
(19, 345343437887, 'William', 'Merle', 'Stark', 'starks@gmail.com', 713575324, 4988451621);

-- --------------------------------------------------------

--
-- Table structure for table `tellers`
--

CREATE TABLE `tellers` (
  `Username` varchar(20) NOT NULL,
  `Password` varbinary(15) NOT NULL,
  `E-mail` varchar(30) NOT NULL,
  `Hash Value` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tellers`
--

INSERT INTO `tellers` (`Username`, `Password`, `E-mail`, `Hash Value`) VALUES
('AMJustin', 0x6a757374696e4038373534, '', ''),
('ATNaveen', 0x41544e617665656e26343735, '', ''),
('ATSophia', 0x536f706869612f373534, '', ''),
('CBArthur', 0x43424172746875652a393531, '', ''),
('DMOliver', 0x4f6c697665722b343034, '', ''),
('KIAryn', 0x4b494172796e21363534, '', ''),
('KNTyson', 0x4b4e5479736f6e24333231, '', ''),
('LMErin', 0x4572696e40363435, '', ''),
('LTJasper', 0x4a61737065722a393837, '', ''),
('NMHaiden', 0x4d48616964656e24373435, '', ''),
('SDCharles', 0x53443936383435, '', ''),
('SFWinston', 0x534657696e73746f6e25313432, '', ''),
('WASilva', 0x53696c76617761353435, '', ''),
('WRSelena', 0x53656c656e6124353233, '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tellers`
--
ALTER TABLE `tellers`
  ADD PRIMARY KEY (`Username`,`E-mail`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clients`
--
ALTER TABLE `clients`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
