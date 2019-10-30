-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Oct 30, 2019 at 10:52 AM
-- Server version: 5.7.26
-- PHP Version: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quinn_inc`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `Account Number` bigint(20) NOT NULL,
  `NID Number` bigint(12) NOT NULL,
  `ACC_Type` varchar(15) NOT NULL,
  `First Name` varchar(50) NOT NULL,
  `Middle Name` varchar(20) NOT NULL,
  `Last Name` varchar(50) NOT NULL,
  `Balance` float NOT NULL DEFAULT '0',
  `E-mail Address` varchar(30) NOT NULL,
  `Mobile Number` int(14) NOT NULL,
  PRIMARY KEY (`NID Number`,`Account Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`Account Number`, `NID Number`, `ACC_Type`, `First Name`, `Middle Name`, `Last Name`, `Balance`, `E-mail Address`, `Mobile Number`) VALUES
(1002448464, 255647189, 'PREMIER_SAVINGS', 'Charles', '', 'Winderson', 250, 'charls@icloud.com', 774856219),
(5984684623, 124165310564, '', 'Rick', 'Edward', 'Brooks', 0, 'Brooks@yahoo.com', 771367842),
(9561654133, 144165055654, '', 'Nuran', 'Fernando', 'Miller', 0, 'Nuran_3211@gmail.com', 713589742),
(8465320089, 145484164106, '', 'John', 'Seth', 'Neil', 0, 'neil7@gmail.com', 761578238),
(4169856616, 156448413104, '', 'Caitlin', 'Lane', 'Moore', 0, 'Caitlin412@gmail.com', 713449843),
(5261065161, 165419489416, '', 'Lucas', 'Troy', 'Matthews', 0, 'mathew@gmail.com', 773842316),
(6765164689, 200143748675, '', 'Rachel', 'Rebecca', 'Reynolds', 0, 'Rachel14@gmail.com', 773687516),
(1851561616, 200154387348, '', 'Sansa', 'Ellen', 'Palmer', 0, 'Palmer_45@gmail.com', 767461384),
(2306514965, 200156724342, '', 'Rob', 'Felix', 'Parker', 0, 'rob54@gmail.com', 763485721),
(8001243764, 200531848736, '', 'Rohan', 'Wade', 'Anderson', 0, 'Rohan4@gmail.com', 764753147),
(8949651335, 248416164841, '', 'Olivia', 'Jane', 'Fletcher', 0, 'Olivia_fl@gmail.com', 713548702),
(4988451621, 345343437887, '', 'William', 'Merle', 'Stark', 0, 'starks@gmail.com', 713575324),
(8465161232, 415648413103, '', 'Franklin', 'Arden', 'Bradley', 0, 'Bradley145@yahoo.com', 762404404),
(9546842123, 443451257841, '', 'Jace', 'Murphy', 'Adams', 0, 'Adams_j@yahoo.com', 765791234),
(8949845162, 489481361605, '', 'Jacob', 'Francis', 'Johnson', 0, 'jacob@yahoo.com', 763587421),
(9846511653, 567863786786, '', 'Harry', 'Jordan', 'Phillips', 0, 'phill@gmail.com', 713689427),
(8469851261, 676786378678, '', 'Monica', 'Alice', 'Rose', 0, 'Monica410@gmail.com', 761579412),
(1641989495, 745572237783, '', 'Thomas', 'Luke', 'Roberts', 0, 'Thomas123@gmail.com', 714691328),
(8016463554, 874613255486, '', 'Morgan', 'Avery', 'Armstrong', 0, 'Morgan21@gmail.com', 779842153),
(8495262163, 894861104640, '', 'Selena', 'Naomi', 'Kelly', 0, 'Selena_k@gmail.com', 764897512);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS `login` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `E-mail` varchar(150) NOT NULL,
  `Hash Value` varchar(30) NOT NULL,
  PRIMARY KEY (`Username`,`E-mail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`Username`, `Password`, `E-mail`, `Hash Value`) VALUES
('admin', 'root', 'admin@quinn.co.uk', ''),
('AMJustin', 'justin@8754', '', ''),
('ATNaveen', 'ATNaveen&475', '', ''),
('ATSophia', 'Sophia/754', '', ''),
('CBArthur', 'CBArthue*951', '', ''),
('DMOliver', 'Oliver+404', '', ''),
('KIAryn', 'KIAryn!654', '', ''),
('KNTyson', 'KNTyson$321', '', ''),
('LMErin', 'Erin@645', '', ''),
('LTJasper', 'Jasper*987', '', ''),
('NMHaiden', 'MHaiden$745', '', ''),
('SDCharles', 'SD96845', '', ''),
('SFWinston', 'SFWinston%142', '', ''),
('WASilva', 'Silvawa545', '', ''),
('WRSelena', 'Selena$523', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE IF NOT EXISTS `transaction` (
  `Type` char(1) NOT NULL,
  `Account_Number` bigint(40) NOT NULL,
  `Account_Type` varchar(200) NOT NULL,
  `Amount` float NOT NULL DEFAULT '0',
  `Date_Time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Account_Number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
