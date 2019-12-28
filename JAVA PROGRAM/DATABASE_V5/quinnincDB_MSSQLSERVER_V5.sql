
-- Creating new database named 'quinnincDB'
CREATE DATABASE quinnincDB

GO

-- Accessing quinnincDB database
USE quinnincDB

GO

-- Creating a new SQL authentication login for the database to be accessed
CREATE LOGIN quinnincDB_Admin WITH PASSWORD = 'soft255sl'

GO

ALTER SERVER ROLE [sysadmin] ADD MEMBER [quinnincDB_Admin]

GO

ALTER SERVER ROLE [securityadmin] ADD MEMBER [quinnincDB_Admin]

GO

ALTER SERVER ROLE [serveradmin] ADD MEMBER [quinnincDB_Admin]

GO

ALTER SERVER ROLE [diskadmin] ADD MEMBER [quinnincDB_Admin]

GO

ALTER SERVER ROLE [dbcreator] ADD MEMBER [quinnincDB_Admin]

GO

-- Creating Table 1 - SystemAdministrator
CREATE TABLE SystemAdministrator(
  SARecordID INT IDENTITY(1,1),
  AdminID AS CAST('SA' + RIGHT('000000' + CAST(SARecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  Branch VARCHAR(30) NOT NULL,
  FirstName VARCHAR(20) NOT NULL,
  MiddleName VARCHAR(25),
  LastName VARCHAR(30) NOT NULL,
  LaneAddress VARCHAR(40) NOT NULL,
  City VARCHAR(20) NOT NULL,
  EmailAddress VARCHAR(50),
  slSystemLoginID CHAR(8),
  saAdminID_RegisteredBy CHAR(8) NOT NULL,
  RegistrationDateTime DATETIME CONSTRAINT DFT_SA_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (AdminID),
  CONSTRAINT FK_SA_AdminID FOREIGN KEY (saAdminID_RegisteredBy)
  REFERENCES SystemAdministrator(AdminID),
  CONSTRAINT CHK_SA_EmailAddress_@ CHECK (EmailAddress LIKE '%@%'),
  CONSTRAINT CHK_SA_AdminIDRegisteredBy_Length CHECK (LEN(saAdminID_RegisteredBy) = 8),
  CONSTRAINT CHK_SL_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8)
)

GO

-- Inserting records to Table 1 - SystemAdministrator
INSERT INTO SystemAdministrator
(Branch, FirstName, MiddleName, LastName, LaneAddress, City, EmailAddress, saAdminID_RegisteredBy, RegistrationDateTime) VALUES
('Liverpool','Peter','Adam','Fransis','43 Hill Road','Liverpool','Peter_Ad332@gmail.com', 'SA000001', '2019-11-15 09:34:56.583'),                         -- AdminID: SA000001
('Birmingham', 'Gipsy', 'Tommy', 'Rase', '86 Mallory Way', 'Birmingham', 'trase0@youku.com', 'SA000001', '2019-11-16 12:54:12.573'),                      -- AdminID: SA000002
('Birmingham','Cthrine', 'Cacilia', 'Baines', '98977 Fuller Trail', 'Birmingham', 'cbaines1@jigsy.com', 'SA000001', '2019-11-16 11:33:34.482'),           -- AdminID: SA000003
('Birmingham', 'Neille', 'Singh', 'Arthan', '7367 Carberry Point', 'Birmingham', 'nsingh2@cloudflare.com', 'SA000002', '2019-11-17 13:23:50.382'),        -- AdminID: SA000004
('Birmingham', 'Goran', 'Francis', 'Carriage', '57 Oxford Junction', 'Birmingham', 'ginstock3@typepad.com', 'SA000003', '2019-11-17 10:34:34.492'),       -- AdminID: SA000005
('Liverpool', 'Ellwood', 'Guidotti', 'Eixenberger', '1226 Crownhardt Trail', 'Liverpool', 'eguidotti4@uol.com.br', 'SA000001', '2019-11-17 09:52:12.482') -- AdminID: SA000006

GO

-- Creating Table 2 - SystemAdministratorPhoneNumber
CREATE TABLE SystemAdministratorPhoneNumber(
  saAdminID CHAR(8) NOT NULL,
  PhoneNumber CHAR(10) NOT NULL,
  PRIMARY KEY (saAdminID, PhoneNumber),
  CONSTRAINT FK_SAPN_AdminID FOREIGN KEY (saAdminID)
  REFERENCES SystemAdministrator(AdminID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_SAPN_AdminID_Length CHECK (LEN(saAdminID) = 8),
  CONSTRAINT CHK_SAPN_PhoneNumber_Length CHECK (LEN(PhoneNumber) = 10)
)

GO

-- Inserting records to Table 2 - SystemAdministratorPhoneNumber
INSERT INTO SystemAdministratorPhoneNumber VALUES
('SA000001', '7798035930'),
('SA000001', '7637255059'),
('SA000002', '7909302358'),
('SA000002', '7669968641'),
('SA000003', '7747582749'),
('SA000004', '7727478891'),
('SA000005', '7728492749')

GO

-- Creating Table 3 - SystemLoginPosition
CREATE TABLE SystemLoginPosition(
  SLPRecordID INT IDENTITY(1,1),
  PositionID AS CAST('PT' + RIGHT('000000' + CAST(SLPRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  Position VARCHAR(30) NOT NULL,
  PRIMARY KEY(PositionID)
)

GO

-- Inserting records to Talbe 3 - SystemLoginPosition
INSERT INTO SystemLoginPosition(Position) VALUES
('Bank Teller'),              -- PositionID: PT000001
('Bank Manager'),             -- PositionID: PT000002
('Bank System Administrator') -- PositionID: PT000003

GO

-- Creating Table 4 - SystemLogin
CREATE TABLE SystemLogin(
  SLRecordID INT IDENTITY(1,1),
  SystemLoginID AS CAST('SL' + RIGHT('000000' + CAST(SLRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  Username VARCHAR(30) NOT NULL UNIQUE,
  Password CHAR(64) NOT NULL UNIQUE,
  slpPositionID CHAR(8) NOT NULL,
  saAdminID_CreatedBy CHAR(8) NOT NULL,
  CreationDateTime DATETIME CONSTRAINT DFT_SL_CreationDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (SystemLoginID),
  CONSTRAINT FK_SL_PositionID FOREIGN KEY (slpPositionID)
  REFERENCES SystemLoginPosition(PositionID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_SL_AdminID FOREIGN KEY (saAdminID_CreatedBy)
  REFERENCES SystemAdministrator(AdminID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_SL_PositionID_Length CHECK (LEN(slpPositionID) = 8),
  CONSTRAINT CHK_SL_AdminID_Length CHECK (LEN(saAdminID_CreatedBy) = 8)
)

GO

-- Inserting records to Table 4 - SystemLogin
INSERT INTO SystemLogin (Username, Password, slpPositionID, saAdminID_CreatedBy, CreationDateTime) VALUES
('FrancisPeter@33442', '16b33235b695e5853211c76ad55ff2f8ba1ddf5886fc3c5634677583d86f13b7', 'PT000003', 'SA000001', '2019-11-15 08:13:58.707'),      -- Password String: FranPter@645         |  Role: Bank System Administrator  |  SystemLoginID: SL000001
('RaseGipsy@66431', 'c78b8bf63d17fc397dfc10c3aaf66e0a95bc93b19e83a9d483af91f5d2b47c44', 'PT000003', 'SA000002', '2019-11-16 08:13:58.707'),         -- Password String: Rasesy@66            |  Role: Bank System Administrator  |  SystemLoginID: SL000002
('BainesCthrine@45762', '344c5228ba17f8111c285c28951d03e73f832ebcc612c29a3a74d880ea31b318', 'PT000003', 'SA000003', '2019-11-17 08:13:58.707'),     -- Password String: BasiCthClia@45       |  Role: Bank System Administrator  |  SystemLoginID: SL000003
('ArthanNeilla@34733', '01fb0707fa0cddaa60c9d890cab5fec68af44797d19fd2d0b72023a83985c149', 'PT000003', 'SA000004', '2019-11-17 08:13:58.707'),      -- Password String: ArthaNeliagh@34      |  Role: Bank System Administrator  |  SystemLoginID: SL000004
('CarriageGoran@56846', '620bba49c6fe20ad43e69afc236de9cd26092b6b6c8132c4dea1f1ab3f74164c', 'PT000003', 'SA000005', '2019-11-18 08:13:58.707'),     -- Password String: RiaFransRan@56       |  Role: Bank System Administrator  |  SystemLoginID: SL000005
('EixenbergerEllwood@39482', '811edaff4ab0f1134b19bcf23278edb6d189517a96140fb487b733eba08d07b0', 'PT000003', 'SA000006', '2019-11-18 08:13:58.707'),-- Password String: EixenGuittiEllod@39  |  Role: Bank System Administrator  |  SystemLoginID: SL000006
('OdesonWildon@38593', '48b93387577718cc813672b06b7f8556ee0c64270c715cfbb2b89205f107954c', 'PT000002', 'SA000001', '2019-11-18 08:13:58.707'),      -- Password String: OdenAdrasWiln@38     |  Role: Bank Manager               |  SystemLoginID: SL000007
('HackletonCarlye@28549', '956853b66e5da3711d92c755daee02db1f2a90c9b4fc18f65fe07aa592fd718f', 'PT000002', 'SA000002', '2019-11-19 08:13:58.707'),   -- Password String: HcketonZaCary@28	 |  Role: Bank Manager               |  SystemLoginID: SL000008
('MartijnFelisha@58375', '8c435c62a4a2f6dfb9c0695dbd9d8e04dc7d2a5e36271a6459a7f336fcf6dec4', 'PT000001', 'SA000002', '2019-11-20 08:13:58.707'),    -- Password String: MatiRuFeli@58        |  Role: Bank Teller                |  SystemLoginID: SL000009
('BerzinsOdey@79205', 'b6e4676d4d0f8f0d7192ad5a450a401e7ea2f51892bb0671a19550eff6d1f154', 'PT000001', 'SA000002', '2019-11-20 08:13:58.707'),       -- Password String: BersJOdy@79	         |  Role: Bank Teller                |  SystemLoginID: SL000010
('JanikowskiGarnham@58105', '8995796d950e15f219d6acc39c0fb439458c3f0c4a982a673c708e33f653f9e6', 'PT000001', 'SA000001', '2019-11-20 08:13:58.707'), -- Password String: JanskiRalhGar@58     |  Role: Bank Teller                |  SystemLoginID: SL000011
('TreweekBelva@68205', '32f41454a4557e3b0d73e1469dad6916bd0b2365712051b6a55ce106ef699e24', 'PT000001', 'SA000004', '2019-11-21 08:13:58.707'),      -- Password String: TreAoBel@68          |  Role: Bank Teller                |  SystemLoginID: SL000012
('SeamanDonlon@58503', '29d5167464bf98d7c2a0fbf325026417a6094ee54d43eb428df302e6af937e52', 'PT000001', 'SA000001', '2019-11-21 08:13:58.707'),      -- Password String: SeanRodDlon@58       |  Role: Bank Teller                |  SystemLoginID: SL000013
('AubreyDikels@48205', '9df5a66ddc929603225663e3295902e70fef668c5a4f1646db89b333131b623c', 'PT000001', 'SA000001', '2019-11-21 08:13:58.707'),      -- Password String: AbreyHarDil@48       |  Role: Bank Teller                |  SystemLoginID: SL000014
('BlaxterElmo@69205', '24c7ad78d95a2ada9da282b7b8c0960800bba44647de9669c5799988451de450', 'PT000001', 'SA000004', '2019-11-21 08:13:58.707'),       -- Password String: BlaxrYananElo@692    |  Role: Bank Teller                |  SystemLoginID: SL000015
('GermaineMaddy@19503', 'f9ed2601857f9c3804ed96362302e0effdf509e3f09bb03adcb2eee0d7157985', 'PT000001', 'SA000006', '2019-11-22 08:13:58.707'),     -- Password String: GernFeliaMay@19      |  Role: Bank Teller                |  SystemLoginID: SL000016
('BaiteyBlannin@50284', '19566e09fc2af820ab322c835c358a1e3aabc287982240dc9d779de559b85247', 'PT000001', 'SA000006', '2019-11-22 08:13:58.707'),     -- Password String: BaitAryBlain@502     |  Role: Bank Teller                |  SystemLoginID: SL000017
('MeddowsMartica@60482', 'fc040371fc88998da38b4b9703a35778ef84e94bd3a89d91fe6c372327bd08fb', 'PT000001', 'SA000004', '2019-11-22 08:13:58.707')     -- Password String: MedsKayMartca@604    |  Role: Bank Teller                |  SystemLoginID: SL000018

GO

-- Altering Table 1 - SystemAdministrator to add foerign key
ALTER TABLE SystemAdministrator
ADD CONSTRAINT FK_SL_SystemLoginID FOREIGN KEY (slSystemLoginID)
REFERENCES SystemLogin(SystemLoginID)

GO

-- Updating the records after altering Table 1 - SystemAdministrator with a new foreign key
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000001' WHERE saAdminID_RegisteredBy = 'SA000001'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000002' WHERE saAdminID_RegisteredBy = 'SA000002'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000003' WHERE saAdminID_RegisteredBy = 'SA000003'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000004' WHERE saAdminID_RegisteredBy = 'SA000004'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000005' WHERE saAdminID_RegisteredBy = 'SA000005'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000006' WHERE saAdminID_RegisteredBy = 'SA000006'

GO

-- Altering Table 1 - SystemAdministrator to alter comlumn to not null
ALTER TABLE SystemAdministrator
ALTER COLUMN slSystemLoginID CHAR(8) NOT NULL

GO

-- Creating Table 5 - SystemLoginLoginDateTime
CREATE TABLE SystemLoginLoginDateTime(
  slSystemLoginID CHAR(8) NOT NULL,
  LoginDateTime DATETIME CONSTRAINT DFT_SLIDT_LoginDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(slSystemLoginID, LoginDateTime),
  CONSTRAINT FK_SLIDT_SystemLoginID FOREIGN KEY (slSystemLoginID)
  REFERENCES SystemLogin(SystemLoginID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_SLIDT_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8)
)

GO

-- Inserting records to Table 5 - SystemLoginLoginDateTime
INSERT INTO SystemLoginLoginDateTime VALUES
('SL000001', '2019-11-15 09:34:56.583'),
('SL000002', '2019-11-15 10:34:56.583'),
('SL000003', '2019-11-15 11:34:56.583'),
('SL000004', '2019-11-15 12:34:56.583'),
('SL000005', '2019-11-15 13:34:56.583'),
('SL000006', '2019-11-15 14:34:56.583')

GO

-- Creating Table 6 - SystemLoginLogoutDateTime
CREATE TABLE SystemLoginLogoutDateTime(
  slSystemLoginID CHAR(8) NOT NULL,
  LogoutDateTime DATETIME CONSTRAINT DFT_SLODT_LogoutDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(slSystemLoginID, LogoutDateTime),
  CONSTRAINT FK_SLODT_SystemLoginID FOREIGN KEY (slSystemLoginID)
  REFERENCES SystemLogin(SystemLoginID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_SLODT_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8)
)

GO

-- Inserting records to Table 6 - SystemLoginLogoutDateTime
INSERT INTO SystemLoginLogoutDateTime VALUES
('SL000001', '2019-11-15 09:50:56.583'),
('SL000002', '2019-11-15 10:49:56.583'),
('SL000003', '2019-11-15 11:55:56.583'),
('SL000004', '2019-11-15 12:40:56.583'),
('SL000005', '2019-11-15 13:46:56.583'),
('SL000006', '2019-11-15 14:39:56.583')

GO

-- Creating Table 7 - Teller
CREATE TABLE Teller(
  TRecordID INT IDENTITY(1,1) NOT NULL,
  TellerID AS CAST('TE' + RIGHT('000000' + CAST(TRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  Branch VARCHAR(30) NOT NULL,
  FirstName VARCHAR(20) NOT NULL,
  MiddleName VARCHAR(25),
  LastName VARCHAR(30) NOT NULL,
  LaneAddress VARCHAR(40) NOT NULL,
  City VARCHAR(20) NOT NULL,
  EmailAddress VARCHAR(50),
  slSystemLoginID CHAR(8) NOT NULL,
  saAdminID_RegisteredBy CHAR(8) NOT NULL,
  RegistrationDateTime DATETIME CONSTRAINT DFT_T_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (TellerID),
  CONSTRAINT FK_T_SystemLoginID FOREIGN KEY (slSystemLoginID)
  REFERENCES SystemLogin(SystemLoginID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_T_AdminID FOREIGN KEY (saAdminID_RegisteredBy)
  REFERENCES SystemAdministrator(AdminID),
  CONSTRAINT CHK_T_EmailAddress_@ CHECK (EmailAddress LIKE '%@%'),
  CONSTRAINT CHK_T_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8),
  CONSTRAINT CHK_T_AdminID_Length CHECK (LEN(saAdminID_RegisteredBy) = 8)
)

GO

-- Inserting records to Table 7 - Teller
INSERT INTO Teller
(Branch, FirstName, MiddleName, LastName, LaneAddress, City, EmailAddress, slSystemLoginID, saAdminID_RegisteredBy, RegistrationDateTime)
VALUES
('Birmingham', 'Felisha', 'Ruperta', 'Martijn', '155 Algoma Trail', 'Birmingham', 'fmartijn0@blogtalkradio.com', 'SL000009', 'SA000002', '2019-11-20 09:31:59.283'), -- TellerID: TE000001
('Birmingham', 'Odey', 'Jerri', 'Berzins', '382 Badeau Terrace', 'Birmingham', 'oberzins1@wordpress.org', 'SL000010', 'SA000003', '2019-11-20 11:44:23.523'),        -- TellerID: TE000002
('Liverpool', 'Garnham', 'Raleigh', 'Janikowski', '4 Bobwhite Center', 'Liverpool', 'ggarnham2@biglobe.ne.jp', 'SL000011', 'SA000001', '2019-11-21 10:32:36.383'),   -- TellerID: TE000003
('Birmingham', 'Belva', 'Aron', 'Treweek', '7 Valley Edge Alley', 'Birmingham', 'btreweek3@dailymail.co.uk', 'SL000012', 'SA000004', '2019-11-21 12:23:14.343'),     -- TellerID: TE000004
('Liverpool',  'Donlon', 'Rodger', 'Seaman', '898 Fair Oaks Way', 'Liverpool', 'jdonlon4@deviantart.com', 'SL000013', 'SA000006','2019-11-21 12:54:16.683'),         -- TellerID: TE000005
('Liverpool',  'Dikels', 'Harwell', 'Aubrey', '	273 Larry Park', 'Liverpool', 'mdikels5@goo.gl', 'SL000014', 'SA000006', '2019-11-21 13:44:26.423'),                 -- TellerID: TE000006
('Birmingham', 'Elmo', 'Yanaton', 'Blaxter', '74 Springs Court', 'Birmingham', 'eblaxter6@gizmodo.com', 'SL000015', 'SA000005', '2019-11-22 09:12:34.542'),          -- TellerID: TE000007
('Liverpool',  'Maddy', 'Fenelia', 'Germaine', '732 Bobwhite Plaza', 'Liverpool', 'mmabbutt7@sciencedirect.com', 'SL000016', 'SA000001', '2019-11-22 09:23:46.465'), -- TellerID: TE000008
('Liverpool',  'Blannin', 'Arney', 'Baitey', '311 Jay Drive', 'Liverpool', 'tblannin8@zdnet.com', 'SL000017', 'SA000006', '2019-11-22 10:04:34.683'),                -- TellerID: TE000009
('Birmingham', 'Martica', 'Kathy', 'Meddows', '708 Delladonna Way', 'Birmingham', 'mmeddows9@newsvine.com', 'SL000018', 'SA000003', '2019-11-22 10:38:26.475')       -- TellerID: TE000010

GO

-- Creating Table 8 - TellerShiftStartDateTime
CREATE TABLE TellerShiftStartDateTime(
  tTellerID CHAR(8) NOT NULL,
  ShiftStartDateTime DATETIME CONSTRAINT DFT_TSSDT_ShidtStartDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (tTellerID, ShiftStartDateTime),
  CONSTRAINT FK_TSSDT_TellerID FOREIGN KEY (tTellerID)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_TSSDT_TellerID_Length CHECK (LEN(tTellerID) = 8)
)

GO

-- Inserting records to Table 8 - TellerShiftStartDateTime
INSERT INTO TellerShiftStartDateTime (tTellerID, ShiftStartDateTime) VALUES
('TE000001', '2019-11-27 09:00:00.000'),
('TE000002', '2019-11-27 09:00:00.000'),
('TE000003', '2019-11-27 09:00:00.000'),
('TE000004', '2019-11-27 09:00:00.000'),
('TE000005', '2019-11-27 09:00:00.000')

GO

-- Creating Table 9 - TellerShiftEndDateTime
CREATE TABLE TellerShiftEndDateTime(
  tTellerID CHAR(8) NOT NULL,
  TellerShiftEndDateTime DATETIME CONSTRAINT DFT_TSEDT_ShiftEndDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (tTellerID, TellerShiftEndDateTime),
  CONSTRAINT FK_Teller_ShiftEndDateTime FOREIGN KEY (tTellerID)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_TSEDT_TellerID_Length CHECK (LEN(tTellerID) = 8)
)

GO

-- Inserting records to Table 9 - TellerShiftEndDateTime
INSERT INTO TellerShiftEndDateTime (tTellerID, TellerShiftEndDateTime) VALUES
('TE000001', '2019-11-27 15:00:00.000'),
('TE000002', '2019-11-27 15:00:00.000'),
('TE000003', '2019-11-27 15:00:00.000'),
('TE000004', '2019-11-27 15:00:00.000'),
('TE000005', '2019-11-27 15:00:00.000')

GO

-- Creating Table 10 - TellerPhoneNumber
CREATE TABLE TellerPhoneNumber(
  tTellerID CHAR(8) NOT NULL,
  PhoneNumber CHAR(10) NOT NULL,
  PRIMARY KEY(tTellerID, PhoneNumber),
  CONSTRAINT FK_TPN_PhoneNumber FOREIGN KEY (tTellerID)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_TPN_TellerID_Length CHECK (LEN(tTellerID) = 8),
  CONSTRAINT CHK_TPN_PhoneNumber_Length CHECK (LEN(PhoneNumber) = 10)
)

GO

-- Inserting records to Table 10 - TellerPhoneNumber
INSERT INTO TellerPhoneNumber VALUES
('TE000001', '7655834968'),
('TE000001', '7424492534'),
('TE000002', '7424495832'),
('TE000003', '7932340824'),
('TE000004', '7233439485'),
('TE000005', '7495839502'),
('TE000005', '7293849248'),
('TE000006', '7388359282'),
('TE000007', '7243948298'),
('TE000008', '7323849219'),
('TE000009', '7231498593'),
('TE000010', '7324829104')

GO

-- Creating Table 11 - Customer
CREATE TABLE Customer(
  PassportNumber CHAR(9) NOT NULL PRIMARY KEY,
  FirstName VARCHAR(20) NOT NULL,
  MiddleName VARCHAR(25),
  LastName VARCHAR(30) NOT NULL,
  LaneAddress VARCHAR(40) NOT NULL,
  City VARCHAR(20) NOT NULL,
  MonthlyIncomeRate MONEY NOT NULL,
  tTellerID_RegisteredBy CHAR(8) NOT NULL,
  RegistrationDateTime DATETIME CONSTRAINT DFT_C_RegisteredDateTime DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT FK_C_TellerID FOREIGN KEY (tTellerID_RegisteredBy)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_CT_PassportNumber_Length CHECK (LEN(PassportNumber) = 9),
  CONSTRAINT CHK_CT_TellerID_Length CHECK (LEN(tTellerID_RegisteredBy) = 8)
)

GO

-- Inserting records to Table 11 - Customer
INSERT INTO Customer (PassportNumber, FirstName, MiddleName, LastName, LaneAddress, City, MonthlyIncomeRate, tTellerID_RegisteredBy, RegistrationDateTime) VALUES
('255647183', 'Charles', '', 'Winderson', '53 Karstens Avenue', 'Birmingham', '6500.00', 'TE000001', '2019-11-24 10:38:26.475'),
('124165310', 'Rick', 'Edward', 'Brooks', '198 Warner Road', 'Liverpool', '8100.00', 'TE000003', '2019-11-24 10:38:26.475'),
('144165055', 'Nuran', 'Fernando', 'Miller', '232 Northfield Pass', 'Liverpool', '5000.00', 'TE000005', '2019-11-24 10:38:26.475'),
('145484164', 'John', 'Seth', 'Neil', '673 Alpine Plaza', 'Birmingham', '8000.00', 'TE000002', '2019-11-25 10:38:26.475'),
('156448413', 'Caitlin', 'Lane', 'Moore', '402 Commercial Way', 'Birmingham', '9300.00', 'TE000004', '2019-11-26 10:38:26.475'),
('165419489', 'Lucas', 'Troy', 'Matthews', '58 Hoard Place', 'Liverpool', '4500.00', 'TE000006', '2019-11-26 10:38:26.475'),
('200143748', 'Rachel', 'Rebecca', 'Reynolds', '4215 Vernon Place', 'Liverpool', '6300.00', 'TE000008', '2019-11-26 10:38:26.475'),
('200154387', 'Sansa', 'Ellen', 'Palmer', '59 South Circle', 'Birmingham', '6900.00', 'TE000007', '2019-11-26 10:38:26.475'),
('200156724', 'Rob', 'Felix', 'Parker', '6 Namekagon Crossing', 'Birmingham', '10000.00', 'TE000010', '2019-11-26 10:38:26.475'),
('200531848', 'Rohan', 'Wade', 'Anderson', '47 Coleman Crossing', 'Liverpool', '5100.00', 'TE000005', '2019-11-27 10:38:26.475'),
('248416164', 'Olivia', 'Jane', 'Fletcher', '25 Pearson Pass', 'Birmingham', '9500.00', 'TE000002', '2019-11-27 10:38:26.475'),
('345343437', 'William', 'Merle', 'Stark', '32 Carberry Lane', 'Liverpool', '21500.00', 'TE000009', '2019-11-27 10:38:26.475'),
('415648413', 'Franklin', 'Arden', 'Bradley', '648 Trailsway Trail', 'Birmingham', '24500.00', 'TE000004', '2019-11-28 10:38:26.475'),
('443451257', 'Jace', 'Murphy', 'Adams', '18 Oneill Road', 'Birmingham', '6300.00', 'TE000007', '2019-11-29 10:38:26.475'),
('489481361', 'Jacob', 'Francis', 'Johnson', '23 Tennessee Center', 'Birmingham', '9200.00', 'TE000010', '2019-11-29 10:38:26.475'),
('567863786', 'Harry', 'Jordan', 'Phillips', '168 Moland Alley', 'Birmingham', '3700.00', 'TE000001', '2019-11-29 10:38:26.475'),
('676786378', 'Monica', 'Alice', 'Rose', '9 Charing Cross Parkway', 'Liverpool', '4700.00', 'TE000005', '2019-11-29 10:38:26.475'),
('745572237', 'Thomas', 'Luke', 'Roberts', '5395 Nova Center', 'Birmingham', '9200.00', 'TE000007', '2019-11-30 10:38:26.475'),
('874613255', 'Morgan', 'Avery', 'Armstrong', '29 Bultman Way', 'Birmingham', '5500.00', 'TE000002', '2019-12-01 10:38:26.475'),
('894861104', 'Selena', 'Naomi', 'Kelly', '224 Old Gate Road', 'Liverpool', '22500.00', 'TE000003', '2019-12-02 10:38:26.475')

GO

-- -- Creating Table 12 - CustomerEmailAddress
CREATE TABLE CustomerEmailAddress(
  cPassportNumber CHAR(9) NOT NULL,
  EmailAddress VARCHAR(50) NOT NULL,
  PRIMARY KEY (cPassportNumber, EmailAddress),
  CONSTRAINT FK_CEN_PassportNumber FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_CEM_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_EmailAdress_@ CHECK (EmailAddress LIKE '%@%')
)

GO

-- Inserting records to Table 12 - CustomerEmailAddress
INSERT INTO CustomerEmailAddress VALUES
('255647183', 'charls@icloud.com'),
('255647183', 'charls_42@gmail.com'),
('124165310', 'Brooks@yahoo.com'),
('144165055', 'Nuran_3211@gmail.com'),
('145484164', 'neil7@gmail.com'),
('145484164', 'neil18@gmail.com'),
('156448413', 'Caitlin412@gmail.com'),
('165419489', 'mathew@gmail.com'),
('200143748', 'Rachel14@gmail.com'),
('200143748', 'Rebecca21@gmail.com'),
('200154387', 'Palmer_45@gmail.com'),
('200156724', 'rob54@gmail.com'),
('200156724', 'rob94@gmail.com'),
('200531848', 'Rohan4@gmail.com'),
('248416164', 'Olivia_fl@gmail.com'),
('345343437', 'starks@gmail.com'),
('415648413', 'Bradley145@yahoo.com'),
('415648413', 'Bradley45@gmail.com'),
('443451257', 'Adams_j@yahoo.com'),
('489481361', 'jacob@yahoo.com'),
('489481361', 'jacob23@gmail.com'),
('567863786', 'phill@gmail.com'),
('676786378', 'Monica410@gmail.com'),
('745572237', 'Thomas123@gmail.com'),
('745572237', 'Thomas543@gmail.com'),
('874613255', 'Morgan21@gmail.com'),
('894861104', 'Selena_k@gmail.com'),
('894861104', 'Selena22_k@gmail.com')

GO

-- Creating Table 13 - CustomerPhoneNumber
CREATE TABLE CustomerPhoneNumber(
  cPassportNumber CHAR(9) NOT NULL,
  PhoneNumber CHAR(10) NOT NULL,
  PRIMARY KEY (cPassportNumber, PhoneNumber),
  CONSTRAINT FK_C_PassportNumber_Length FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_CPN_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_CPN_PhoneNumber_Length CHECK (LEN(PhoneNumber) = 10)
)

GO

-- Inserting rcords to Table 13 - CustomerPhoneNumber
INSERT INTO CustomerPhoneNumber VALUES
('255647183', '7748562193'),
('255647183', '7738583927'),
('124165310', '7713678424'),
('144165055', '7135897426'),
('145484164', '7615782382'),
('145484164', '7742582945'),
('156448413', '7134498438'),
('165419489', '7738423163'),
('165419489', '7726582967'),
('200143748', '7736875167'),
('200143748', '7726589275'),
('200154387', '7674613842'),
('200156724', '7634857218'),
('200531848', '7647531473'),
('200531848', '7537950274'),
('248416164', '7135487021'),
('345343437', '7135753247'),
('415648413', '7624044045'),
('415648413', '7629857945'),
('443451257', '7657912340'),
('489481361', '7635874213'),
('567863786', '7136894276'),
('567863786', '7583673856'),
('676786378', '7615794122'),
('745572237', '7146913286'),
('745572237', '7159683954'),
('874613255', '7798421531'),
('874613255', '7739583739'),
('894861104', '7648975127'),
('894861104', '7639859279')

GO

-- Creating Table 14 - AccountStatus
CREATE TABLE AccountStatus(
  ASRecordID INT IDENTITY(1,1),
  AccountStatusID AS CAST('AS' + RIGHT('000000' + CAST(ASRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  AccountStatus VARCHAR(10) NOT NULL,
  PRIMARY KEY (AccountStatusID)
)

GO

-- Inserting records to Table 14 - AccountStatus
INSERT INTO AccountStatus(AccountStatus) VALUES
('ACTIVE'),  -- AccountStatusID: AS000001
('ONHOLD'),  -- AccountStatusID: AS000002
('DISABLED') -- AccountStatusID: AS000003

GO

-- Creating Table 15 - AccountNormalSavings
CREATE TABLE AccountNormalSavings(
  NSAccountNumber INT IDENTITY(25000001,1),
  NSAccountBalance MONEY NOT NULL,
  asAccountStatusID CHAR(8) NOT NULL,
  cPassportNumber CHAR(9) NOT NULL,
  tTellerID_RegisteredBy CHAR(8) NOT NULL,
  RegistrationDateTime DATETIME CONSTRAINT DFT_ANS_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (NSAccountNumber),
  CONSTRAINT FK_ANS_AccountStatusID FOREIGN KEY (asAccountStatusID)
  REFERENCES AccountStatus(AccountStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ANS_PassportNumber FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ANS_TellerID FOREIGN KEY (tTellerID_RegisteredBy)
  REFERENCES Teller(TellerID),
  CONSTRAINT CHK_ANS_AccountStatusID CHECK (LEN(asAccountStatusID) = 8),
  CONSTRAINT CHK_ANS_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_ANS_TellerID_Length CHECK (LEN(tTellerID_RegisteredBy) = 8)
)

GO

-- Inserting records to Table 15 - AccountNormalSavings
INSERT INTO AccountNormalSavings (NSAccountBalance, asAccountStatusID, cPassportNumber, tTellerID_RegisteredBy, RegistrationDateTime) VALUES
('140.23', 'AS000001', '144165055', 'TE000001', '2019-11-19 13:54:43.583'),   -- NSAccountNumber: 25000001
('150.39', 'AS000001', '156448413','TE000003', '2019-11-20 09:23:23.647'),    -- NSAccountNumber: 25000002
('534.49', 'AS000002', '200143748', 'TE000005', '2019-11-21 11:54:23.153'),   -- NSAccountNumber: 25000003
('589.25', 'AS000001', '200156724', 'TE000010', '2019-11-25 13:56:12.357'),   -- NSAccountNumber: 25000004
('522.25', 'AS000001', '415648413', 'TE000010', '2019-11-26 10:53:54.693'),   -- NSAccountNumber: 25000005
('2200.58', 'AS000001', '489481361', 'TE000004', '2019-11-28 12:42:33.602'),  -- NSAccountNumber: 25000006
('398.89', 'AS000003', '745572237', 'TE000004', '2019-11-29 11:24:29.593'),   -- NSAccountNumber: 25000007
('700.43', 'AS000001', '874613255', 'TE000002', '2019-11-29 11:19:53.583')    -- NSAccountNumber: 25000008

GO

-- Creating Table 16 - AccountBonusSavings
CREATE TABLE AccountBonusSavings(
  BSAccountNumber INT IDENTITY(45000001,1),
  BSAccountBalance MONEY NOT NULL,
  asAccountStatusID CHAR(8) NOT NULL,
  cPassportNumber CHAR(9) NOT NULL,
  tTellerID_RegisteredBy CHAR(8) NOT NULL,
  RegistrationDateTime DATETIME CONSTRAINT DFT_ABS_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (BSAccountNumber),
  CONSTRAINT FK_ABS_AccountStatusID FOREIGN KEY (asAccountStatusID)
  REFERENCES AccountStatus(AccountStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ABS_PassportNumber FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ABS_TellerID FOREIGN KEY (tTellerID_RegisteredBy)
  REFERENCES Teller(TellerID),
  CONSTRAINT CHK_ABS_AccountStatusID CHECK (LEN(asAccountStatusID) = 8),
  CONSTRAINT CHK_ABS_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_ABS_TellerID_Length CHECK (LEN(tTellerID_RegisteredBy) = 8)
)

GO

-- Inserting records to Table 16 - AccountBonusSavings
INSERT INTO AccountBonusSavings (BSAccountBalance, asAccountStatusID, cPassportNumber, tTellerID_RegisteredBy, RegistrationDateTime) VALUES
('541.25', 'AS000001', '124165310', 'TE000002', '2019-11-18 08:23:56.654'), -- BSAccountNumber: 45000001
('358.24', 'AS000002', '145484164', 'TE000001', '2019-11-19 13:43:43.302'), -- BSAccountNumber: 45000002
('653.29', 'AS000001', '248416164', 'TE000006', '2019-11-25 14:53:56.543'), -- BSAccountNumber: 45000003
('649.78', 'AS000001', '676786378', 'TE000003', '2019-11-28 14:56:35.593'), -- BSAccountNumber: 45000004
('655.98', 'AS000003', '894861104', 'TE000005', '2019-11-29 13:12:09.937')  -- BSAccountNumber: 45000005

GO

-- Creating Table 17 - AccountPremierSavings
CREATE TABLE AccountPremierSavings(
  PSAccountNumber INT IDENTITY(75000001,1),
  PSAccountBalance MONEY NOT NULL,
  asAccountStatusID CHAR(8) NOT NULL,
  cPassportNumber CHAR(9) NOT NULL,
  tTellerID_RegisteredBy CHAR(8) NOT NULL,
  RegistrationDateTime DATETIME CONSTRAINT DFT_APS_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (PSAccountNumber),
  CONSTRAINT FK_APS_AccountStatusID FOREIGN KEY (asAccountStatusID)
  REFERENCES AccountStatus(AccountStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_APS_PassportNumber FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_APS_TellerID FOREIGN KEY (tTellerID_RegisteredBy)
  REFERENCES Teller(TellerID),
  CONSTRAINT CHK_APS_AccountStatusID CHECK (LEN(asAccountStatusID) = 8),
  CONSTRAINT CHK_APS_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_APS_TellerID_Length CHECK (LEN(tTellerID_RegisteredBy) = 8)
)

GO

-- Inserting records to Table 17 - AccountPremierSavings
INSERT INTO AccountPremierSavings (PSAccountBalance, asAccountStatusID, cPassportNumber, tTellerID_RegisteredBy, RegistrationDateTime) VALUES
('1486.25', 'AS000002', '255647183', 'TE000001', '2019-11-18 10:53:23.623'), -- PSAccountNumber: 75000001
('2586.21', 'AS000001', '165419489', 'TE000004', '2019-11-20 11:34:43.464'), -- PSAccountNumber: 75000002
('2925.25', 'AS000001', '200154387', 'TE000007', '2019-11-22 10:12:54.435'), -- PSAccountNumber: 75000003
('1945.25', 'AS000001', '200531848', 'TE000009', '2019-11-25 14:43:04.963'), -- PSAccountNumber: 75000004
('1100.36', 'AS000003', '345343437', 'TE000007', '2019-11-26 09:24:43.854'), -- PSAccountNumber: 75000005
('5289.25', 'AS000001', '443451257', 'TE000008', '2019-11-27 09:14:46.492'), -- PSAccountNumber: 75000006
('1020.09', 'AS000001', '567863786', 'TE000008', '2019-11-28 13:32:59.593')  -- PSAccountNumber: 75000007

GO

-- Creating Table 18 - TransactionStatus
CREATE TABLE TransactionStatus(
  TSRecordID INT IDENTITY(1,1),
  TransactionStatusID AS CAST('TS' + RIGHT('000000' + CAST(TSRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  TransactionStatus VARCHAR(10) NOT NULL,
  PRIMARY KEY (TransactionStatusID)
)

GO

-- Inserting records to Table 18 - TransactionStatus
INSERT INTO TransactionStatus(TransactionStatus) VALUES
('Successful'), -- TransactionStatusID: TS000001
('Failed'),     -- TransactionStatusID: TS000002
('Denied'),     -- TransactionStatusID: TS000003
('Timed-Out'),  -- TransactionStatusID: TS000004
('Reverted')    -- TransactionStatusID: TS000005

GO

-- Creating Table 19 - TransactionDescription
CREATE TABLE TransactionDescription(
  TDRecordID INT IDENTITY(1,1),
  TransactionDescriptionID AS CAST('TD' + RIGHT('000000' + CAST(TDRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  TransactionDescription VARCHAR(40) NOT NULL,
  PRIMARY KEY (TransactionDescriptionID)
)

-- Inserting records to Table 19 - TransactionDescription
INSERT INTO TransactionDescription(TransactionDescription) VALUES
('TAX-ANNUALLY'),                   -- TransactionDescriptionID: TD000001
('INTEREST-MONTHLY'),               -- TransactionDescriptionID: TD000002
('SERVICE-CHARGE'),                 -- TransactionDescriptionID: TD000003
('ONLINE-DEPOSIT'),                 -- TransactionDescriptionID: TD000004
('ONLINE-WITHDRAWAL'),              -- TransactionDescriptionID: TD000005
('CASH-DEPOSIT'),                   -- TransactionDescriptionID: TD000006
('CASH-WITHDRAWAL'),				-- TransactionDescriptionID: TD000007
('CARD-WITHDRAWAL'),			    -- TransactionDescriptionID: TD000008
('INTERNET-CHARGE'),                -- TransactionDescriptionID: TD000009
('CARD-CHARGE'),                    -- TransactionDescriptionID: TD000010
('CASH-DEPOSIT-MACHINE_LIVERPOOL')  -- TransactionDescriptionID: TD000011

GO

-- Creating Table 20 - CustomerTransactionDeposit
CREATE TABLE CustomerTransactionDeposit(
  DTransactionNumber INT IDENTITY(22000001,1) PRIMARY KEY,
  TransactionAmount MONEY NOT NULL,
  TransactionDateTime DATETIME CONSTRAINT DFT_CTD_TransactionDateTime DEFAULT CURRENT_TIMESTAMP,
  tsTransactionStatusID CHAR(8) NOT NULL,
  tdTransactionDescriptionID CHAR(8) NOT NULL,
  ansNSAccountNumber INT,
  absBSAccountNumber INT,
  apsPSAccountNumber INT,
  tTellerID_ProcessedBy CHAR(8),  -- Only available for CASH-DEPOSIT, CASH-WITHDRAWAL, CARD-CHARGE transactions only
  CONSTRAINT FK_CTD_TransactionStatusID FOREIGN KEY (tsTransactionStatusID)
  REFERENCES TransactionStatus(TransactionStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_CTD_TransactionDescriptionID FOREIGN KEY (tdTransactionDescriptionID)
  REFERENCES TransactionDescription(TransactionDescriptionID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_CTD_NSAccountNumber FOREIGN KEY (ansNSAccountNumber)
  REFERENCES AccountNormalSavings(NSAccountNumber),
  CONSTRAINT FK_CTD_BSAccountNumber FOREIGN KEY (absBSAccountNumber)
  REFERENCES AccountBonusSavings(BSAccountNumber),
  CONSTRAINT FK_CTD_PSAccountNumber FOREIGN KEY (apsPSAccountNumber)
  REFERENCES AccountPremierSavings(PSAccountNumber),
  CONSTRAINT FK_CTD_TellerID FOREIGN KEY (tTellerID_ProcessedBy)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_CTD_TransactionStatusID_Length CHECK (LEN(tsTransactionStatusID) = 8),
  CONSTRAINT CHK_CTD_TransactionDescriptionID_Length CHECK (LEN(tdTransactionDescriptionID) = 8),
  CONSTRAINT CHK_CTD_TransactionAmount_Amount CHECK (TransactionAmount > 0.00 ),
  CONSTRAINT CHK_CTD_NSAccountNumber_BSAccountNumber_PSAccountNumber_Length CHECK
  ( (LEN(ansNSAccountNumber) = 8) OR (LEN(absBSAccountNumber) = 8) OR (LEN(apsPSAccountNumber) = 8) ),
  CONSTRAINT CHK_CTD_NSAccountNumber_BSAccountNumber_PSAccountNumber_NotNullVerification CHECK
  ((ansNSAccountNumber = ' ') OR (absBSAccountNumber = ' ') OR (apsPSAccountNumber = ' '))
)

GO

-- Trigger 1 - Table 20 - CustomerTransactionDeposit
-- Creating Trigger 1 - TR_UpdateAccountBalanceAfterDeposit
-- Trigger to increase customer account balance after deposit transaction has be processed
-- Trigger declaration begin
CREATE TRIGGER DBO.TR_UpdateAccountBalanceAfterDeposit
ON CustomerTransactionDeposit
AFTER INSERT
AS
  -- Retrieving transaction amount from the inserted tables (newly inserted record)
  DECLARE @depositAmount MONEY;
  SELECT @depositAmount = TransactionAmount FROM INSERTED;

  -- NORMAL SAVINGS ACCOUNT TYPE
  -- Retrieving normal savings account number from the inserted tables (newly inserted record)
  DECLARE @transactionNSAccountNumber CHAR(8);
  SELECT @transactionNSAccountNumber = ansNSAccountNumber FROM INSERTED;

  -- Checking if the customer's account type is normal savings
  IF (@transactionNSAccountNumber != 'NULL')
    BEGIN
      UPDATE AccountNormalSavings
      SET NSAccountBalance = ( NSAccountBalance + @depositAmount )
      WHERE NSAccountNumber = @transactionNSAccountNumber;
      PRINT 'Deposit Amount Successfully Updated to Account Balance in Customer Account';
    END

  -- BONUS SAVINGS ACCOUNT TYPE
  -- Retrieving bonus savings account number from the inserted tables (newly inserted record)
  DECLARE @transactionBSAccountNumber CHAR(8);
  SELECT @transactionBSAccountNumber = absBSAccountNumber FROM INSERTED;

  -- Checking if the customer's account type is bonus savings
  IF (@transactionBSAccountNumber != 'NULL')
    BEGIN
      UPDATE AccountBonusSavings
      SET BSAccountBalance = ( BSAccountBalance + @depositAmount )
      WHERE BSAccountNumber = @transactionBSAccountNumber;
      PRINT 'Deposit Amount Successfully Updated to Account Balance in Customer Account';
    END

  -- PREMIER SAVINGS ACCOUNT TYPE
  -- Retrieving premier savings account number from the inserted tables (newly inserted record)
  DECLARE @transactionPSAccountNumber CHAR(8);
  SELECT @transactionPSAccountNumber = apsPSAccountNumber FROM INSERTED;

  -- Checking if the customer's account type is premier savings
  IF (@transactionPSAccountNumber != 'NULL')
    BEGIN
      UPDATE AccountPremierSavings
      SET PSAccountBalance = ( PSAccountBalance + @depositAmount )
      WHERE PSAccountNumber = @transactionPSAccountNumber;
      PRINT 'Deposit Amount Successfully Updated to Account Balance in Customer Account';
    END
-- Trigger declaration end

GO

-- Inserting records to Table 20 - CustomerTransactionDeposit, for Normal Savings Account Type
-- Donesn't have a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, tsTransactionStatusID, tdTransactionDescriptionID, ansNSAccountNumber)
VALUES
('200.50', '2019-11-26 10:45:45.846', 'TS000002', 'TD000002', '25000002'),         -- DTransactionNumber: 22000001
('130.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000004', '25000003'),         -- DTransactionNumber: 22000002
('300.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000011', '25000005'),         -- DTransactionNumber: 22000003
('350.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000004', '25000006'),         -- DTransactionNumber: 22000004
('150.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000002', '25000008')          -- DTransactionNumber: 22000005

GO

-- Inserting records to Table 20 - CustomerTransactionDeposit, for Normal Savings Account Type
-- Has a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, tsTransactionStatusID, tdTransactionDescriptionID, ansNSAccountNumber, tTellerID_ProcessedBy)
VALUES
('100.50', '2019-11-25 10:45:23.258', 'TS000001', 'TD000006', '25000001', 'TE000001'), -- DTransactionNumber: 22000006
('500.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000006', '25000004', 'TE000005')  -- DTransactionNumber: 22000007

GO

-- Inserting records to Table 20 - CustomerTransactionDeposit, for Bonus Savings Account Type
-- Doesn't have a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, tsTransactionStatusID, tdTransactionDescriptionID, absBSAccountNumber)
VALUES
('350.10', '2019-11-25 11:36:48.369', 'TS000001', 'TD000002', '45000001'),         -- DTransactionNumber: 22000008
('600.50', '2019-11-26 12:43:25.458', 'TS000005', 'TD000004', '45000002'),         -- DTransactionNumber: 22000009
('600.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000011', '45000003'),         -- DTransactionNumber: 22000010
('440.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000004', '45000005')          -- DTransactionNumber: 22000011

GO

-- Inserting records to Table 20 - CustomerTransactionDeposit, for Bonus Savings Account Type
-- Has a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, tsTransactionStatusID, tdTransactionDescriptionID, absBSAccountNumber, tTellerID_ProcessedBy)
VALUES
('350.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000006', '45000004', 'TE000006') -- DTransactionNumber: 22000012


GO

-- Inserting records to Table 20 - CustomerTransactionDeposit, for Premier Savings Account Type
-- Doesn't have a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, tsTransactionStatusID, tdTransactionDescriptionID, apsPSAccountNumber)
VALUES
('1500.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000004', '75000001'),         -- DTransactionNumber: 22000013
('1260.50', '2019-11-26 12:43:25.458', 'TS000003', 'TD000002', '75000003'),         -- DTransactionNumber: 22000014
('1470.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000011', '75000005')          -- DTransactionNumber: 22000015

GO

-- Inserting records to Table 20 - CustomerTransactionDeposit, for Premier Savings Account Type
-- Has a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, tsTransactionStatusID, tdTransactionDescriptionID, apsPSAccountNumber, tTellerID_ProcessedBy)
VALUES
('1140.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000006', '75000002', 'TE000001'), -- DTransactionNumber: 22000016
('1900.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000006', '75000006', 'TE000010'), -- DTransactionNumber: 22000017
('1560.50', '2019-11-26 12:43:25.458', 'TS000001', 'TD000006', '75000007', 'TE000001')  -- DTransactionNumber: 22000018

GO

-- Crating Table 21 - CustomerTransactionWithdrawal
CREATE TABLE CustomerTransactionWithdrawal(
  WTransactionNumber INT IDENTITY(44000001,1) PRIMARY KEY,
  TransactionAmount MONEY NOT NULL,
  TransactionDateTime DATETIME CONSTRAINT DFT_CTW_TransactionDateTime DEFAULT CURRENT_TIMESTAMP,
  ctTransactionStatusID CHAR(8) NOT NULL,
  tdTransactionDescriptionID CHAR(8) NOT NULL,
  ansNSAccountNumber INT,
  absBSAccountNumber INT,
  apsPSAccountNumber INT,
  tTellerID_ProcessedBy CHAR(8),  -- Only available for CASH-DEPOSIT, CASH-WITHDRAWAL, CARD-CHARGE transactions only
  CONSTRAINT FK_CTW_TransactionStatusID FOREIGN KEY (ctTransactionStatusID)
  REFERENCES TransactionStatus(TransactionStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_CTW_TransactionDescriptionID FOREIGN KEY (tdTransactionDescriptionID)
  REFERENCES TransactionDescription(TransactionDescriptionID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_CTW_NSAccountNumber FOREIGN KEY (ansNSAccountNumber)
  REFERENCES AccountNormalSavings(NSAccountNumber),
  CONSTRAINT FK_CTW_BSAccountNumber FOREIGN KEY (absBSAccountNumber)
  REFERENCES AccountBonusSavings(BSAccountNumber),
  CONSTRAINT FK_CTW_PSAccountNumber FOREIGN KEY (apsPSAccountNumber)
  REFERENCES AccountPremierSavings(PSAccountNumber),
  CONSTRAINT FK_CTW_TellerID FOREIGN KEY (tTellerID_ProcessedBy)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_CTW_TransactionStatusID_Length CHECK (LEN(ctTransactionStatusID) = 8),
  CONSTRAINT CHK_CTW_TransactionDescriptionID_Length CHECK (LEN(tdTransactionDescriptionID) = 8),
  CONSTRAINT CHK_CTW_TransactionAmount_Amount CHECK (TransactionAmount > 0.00 ),
  CONSTRAINT CHK_CTW_NSAccountNumber_BSAccountNumber_PSAccountNumber_Length CHECK
  ( (LEN(ansNSAccountNumber) = 8) OR (LEN(absBSAccountNumber) = 8) OR (LEN(apsPSAccountNumber) = 8) ),
  CONSTRAINT CHK_CTW_NSAccountNumber_BSAccountNumber_PSAccountNumber_NotNullVerification CHECK
  ((ansNSAccountNumber = ' ') OR (absBSAccountNumber = ' ') OR (apsPSAccountNumber = ' '))
)

GO

-- Trigger 2 - Table 21 - CustomerTransactionWithdrawal
-- Creating Trigger 2 - TR_UpdateAccountBalanceAfterWithdrawal
-- Trigger to deduct customer account balance after withdrawal transaction has be processed
-- Trigger declaration begin
CREATE TRIGGER DBO.TR_UpdateAccountBalanceAfterWithdrawal
ON CustomerTransactionWithdrawal
AFTER INSERT
AS
  -- NORMAL SAVINGS ACCOUNT TYPE
  -- Retrieving transaction withdrawing amount from the inserted table (newly inserted record)
  DECLARE @withdrawalAmount MONEY;
  SELECT @withdrawalAmount = TransactionAmount FROM INSERTED;

  -- Retrieving entered normal savings account number
  DECLARE @transactionNSAccountNumber CHAR(8);
  SELECT @transactionNSAccountNumber = ansNSAccountNumber FROM INSERTED;

  -- Retrieving account balance from the customer account
  DECLARE @NSAccountBalance MONEY;
  SELECT @NSAccountBalance = NSAccountBalance
  FROM AccountNormalSavings
  WHERE NSAccountNumber = @transactionNSAccountNumber;

  -- Checking if the customer account type is normal savings
  IF (@transactionNSAccountNumber != 'NULL')
    BEGIN
      -- Checking if available funds are sufficient engough for the withdrawal transaction
      IF ( (@NSAccountBalance - 100) >= @withdrawalAmount )
        BEGIN
          UPDATE AccountNormalSavings
          SET NSAccountBalance = ( NSAccountBalance - @withdrawalAmount )
          WHERE NSAccountNumber = @transactionNSAccountNumber;
          PRINT 'Withdrawal Amount Successfully Updated to Account Balance in Customer Account';
        END
      ELSE
        PRINT 'Insufficient Funds, Unable to Process a Withdrawal Transaction';
    END

  -- BONUS SAVINGS ACCOUNT TYPE
  -- Retrieving entered bonus savings account number
  DECLARE @transactionBSAccountNumber CHAR(8);
  SELECT @transactionBSAccountNumber = absBSAccountNumber FROM INSERTED;

  -- Retrieving account balance from the customer account
  DECLARE @BSAccountBalance MONEY;
  SELECT @BSAccountBalance = BSAccountBalance
  FROM AccountBonusSavings
  WHERE BSAccountNumber = @transactionBSAccountNumber;


  -- Checking if the customer account type is bonus savings
  IF (@transactionBSAccountNumber != 'NULL')
    BEGIN
      -- Checking if available funds are sufficient engough for the withdrawal transaction
      IF ( (@BSAccountBalance - 100) >= @withdrawalAmount )
        BEGIN
          UPDATE AccountBonusSavings
          SET BSAccountBalance = ( BSAccountBalance - @withdrawalAmount )
          WHERE BSAccountNumber = @transactionBSAccountNumber;
          PRINT 'Withdrawal Ammount Successfully Updated to Account Balance in Customer Account';
        END
      ELSE
        PRINT 'Insufficient Funds, Unable to Process a Withdrawal Transaction';
    END

  -- PREMIER SAVINGS ACCOUNT TYPE
  -- Retrieving entered bonus savings account number
  DECLARE @transactionPSAccountNumber CHAR(8);
  SELECT @transactionPSAccountNumber = apsPSAccountNumber FROM INSERTED;

  -- Retrieving account balance from the customer account
  DECLARE @PSAccountBalance MONEY;
  SELECT @PSAccountBalance = PSAccountBalance
  FROM AccountPremierSavings
  WHERE PSAccountNumber = @transactionPSAccountNumber;

  -- Checking if the customer account type is premier savings
  IF (@transactionPSAccountNumber != 'NULL')
    BEGIN
      -- Checking if available funds are sufficient engough for the withdrawal transaction
      IF ( (@PSAccountBalance - 100) >= @withdrawalAmount )
        BEGIN
          UPDATE AccountPremierSavings
          SET PSAccountBalance = ( PSAccountBalance - @withdrawalAmount )
          WHERE PSAccountNumber = @transactionPSAccountNumber;
          PRINT 'Withdrawal Ammount Successfully Updated to Account Balance in Customer Account';
        END
      ELSE
        PRINT 'Insufficient Funds, Unable to Process a Withdrawal Transaction';
    END
-- Trigger declaration end

GO

-- Inserting records to Table 21 - CustomerTransactionWithdrawal, for Normal Savings Account Type
-- Doesn't have a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionWithdrawal
(TransactionAmount, TransactionDateTime, ctTransactionStatusID, tdTransactionDescriptionID, ansNSAccountNumber)
VALUES
('50.50', '2019-11-27 09:47:41.259', 'TS000001', 'TD000001', '25000001'),  -- WTransactionNumber: 44000001
('40.50', '2019-11-27 12:45:42.356', 'TS000004', 'TD000003', '25000002'),  -- WTransactionNumber: 44000002
('60.50', '2019-11-28 13:26:36.412', 'TS000001', 'TD000005', '25000003')   -- WTransactionNumber: 44000003

GO

-- Inserting records to Table 21 - CustomerTransactionWithdrawal, for Bonus Savings Account Type
-- Has a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionWithdrawal
(TransactionAmount, TransactionDateTime, ctTransactionStatusID, tdTransactionDescriptionID, absBSAccountNumber, tTellerID_ProcessedBy)
VALUES
('50.10', '2019-11-27 10:36:32.954', 'TS000001', 'TD000010', '45000001', 'TE000002')  -- WTransactionNumber: 44000004

GO

-- Inserting records to Table 21 - CustomerTransactionWithdrawal, for Premier Savings Account Type
-- Doesn't have a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionWithdrawal
(TransactionAmount, TransactionDateTime, ctTransactionStatusID, tdTransactionDescriptionID, apsPSAccountNumber)
VALUES
('240.50', '2019-11-28 13:12:34.432', 'TS000001', 'TD000008', '75000003'),           -- WTransactionNumber: 44000005
('160.50', '2019-11-28 10:22:53.244', 'TS000001', 'TD000009', '75000002')            -- WTransactionNumber: 44000006


GO

-- Inserting records to Table 21 - CustomerTransactionWithdrawal, for Premier Savings Account Type
-- Has a tTellerID_ProcessedBy
INSERT INTO CustomerTransactionWithdrawal
(TransactionAmount, TransactionDateTime, ctTransactionStatusID, tdTransactionDescriptionID, apsPSAccountNumber, tTellerID_ProcessedBy)
VALUES
('340.50', '2019-11-29 11:22:53.244', 'TS000001', 'TD000007', '75000002', 'TE000006')    -- WTransactionNumber: 44000007

GO

-- Creating Table 22 - Manager
CREATE TABLE Manager(
  MRecordID INT IDENTITY(1,1),
  ManagerID AS CAST('MA' + RIGHT('000000' + CAST(MRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  Branch VARCHAR(30) NOT NULL,
  FirstName VARCHAR(20) NOT NULL,
  MiddleName VARCHAR(25),
  LastName VARCHAR(30) NOT NULL,
  LaneAddress VARCHAR(40) NOT NULL,
  City VARCHAR(20) NOT NULL,
  EmailAddress VARCHAR(50),
  slSystemLoginID CHAR(8) NOT NULL,
  saAdminID_RegisteredBy CHAR(8) NOT NULL,
  RegistrationDateTime DATETIME CONSTRAINT DFT_M_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(ManagerID),
  CONSTRAINT FK_M_SystemLogin FOREIGN KEY (slSystemLoginID)
  REFERENCES SystemLogin(SystemLoginID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_M_AdminID FOREIGN KEY (saAdminID_RegisteredBy)
  REFERENCES SystemAdministrator(AdminID),
  CONSTRAINT CHK_M_EmailAddress_@ CHECK (EmailAddress LIKE '%@%'),
  CONSTRAINT CHK_M_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8),
  CONSTRAINT CHK_M_AdminID_Length CHECK (LEN(saAdminID_RegisteredBy) = 8)
)

GO

-- Inserting records to Table 22 - Manager
INSERT INTO Manager (Branch, FirstName, MiddleName, LastName, LaneAddress, City, EmailAddress, slSystemLoginID, saAdminID_RegisteredBy, RegistrationDateTime)
VALUES
('Liverpool', 'Wildon', 'Andras', 'Odeson', '	690 Hauk Park', 'Liverpool', 'wodeson0@nationalgeographic.com', 'SL000007', 'SA000001', '2019-11-19 12:12:34.323'),    -- ManagerID: MA000001
('Birmingham', 'Carlye', 'Zabrina', 'Hackleton', '267 Waxwing Trail', 'Birmingham', 'chackletong@washington.edu', 'SL000008', 'SA000002', '2019-11-19 13:45:24.542') -- ManagerID: MA000002

GO

-- Creating Table 23 - ManagerPhoneNumber
CREATE TABLE ManagerPhoneNumber(
  mManagerID CHAR(8) NOT NULL,
  PhoneNumber CHAR(10) NOT NULL,
  PRIMARY KEY(mManagerID, PhoneNumber),
  CONSTRAINT FK_M_ManagerID FOREIGN KEY (mManagerID)
  REFERENCES Manager(ManagerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_M_ManagerID_Length CHECK (LEN(mManagerID) = 8),
  CONSTRAINT CHK_M_PhoneNumber_Length CHECK (LEN(PhoneNumber) = 10)
)

GO

-- Inserting records to Table 23 - ManagerPhoneNumber
INSERT INTO ManagerPhoneNumber VALUES
('MA000001', '7393392492'),
('MA000001', '7843829891'),
('MA000002', '7942940148'),
('MA000002', '7383893482')

GO

-- Creating Table 24 - ModificationStatus
CREATE TABLE ModificationStatus(
  ModificationStatusID INT IDENTITY(1,1),
  ModificationStatus VARCHAR(50) NOT NULL,
  PRIMARY KEY (ModificationStatusID)
)

GO

-- Inserting records to Table 24 - ModificationStatus
INSERT INTO ModificationStatus(ModificationStatus) VALUES
('Updated Lane Address'),       -- ModificationStatusID: 1
('Updated Account Status'),     -- ModificationStatusID: 2
('Updated Transaction Status'), -- ModificationStatusID: 3
('Reverted Transaction'),       -- ModificationStatusID: 4
('Updating Password')           -- ModificationStatusID: 5

GO

-- Creating Table 25 - TellerManageCustomer
CREATE TABLE TellerManageCustomer(
  tTellerID_EditedBy CHAR(8) NOT NULL,
  cPassportNumber CHAR(9) NOT NULL,
  EditDateTime DATETIME CONSTRAINT DFT_TMC_EditDateTime DEFAULT CURRENT_TIMESTAMP,
  msModificationStatusID INT NOT NULL,
  PRIMARY KEY (tTellerID_EditedBy, cPassportNumber, EditDateTime),
  CONSTRAINT FK_TMC_ModificationStatusID FOREIGN KEY (msModificationStatusID)
  REFERENCES ModificationStatus(ModificationStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_TMC_TellerID FOREIGN KEY (tTellerID_EditedBy)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_TMC_PassportNumber FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber),
  CONSTRAINT CHK_TMC_TellerID_Length CHECK (LEN(tTellerID_EditedBy) = 8),
  CONSTRAINT CHK_TMC_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9)
)

GO

-- Inserting records to Table 25 - TellerManageCustomer
INSERT INTO TellerManageCustomer VALUES
('TE000003', '144165055', '2019-12-18 09:23:34.323', 1),
('TE000004', '165419489', '2019-12-20 13:42:34.323', 1)

GO

-- Creating Table 26 -	ManagerManageAccountNormalSavings
CREATE TABLE ManagerManageAccountNormalSavings(
  mManagerID_EditedBy CHAR(8) NOT NULL,
  ansNSAccountNumber INT NOT NULL,
  EditDateTime DATETIME CONSTRAINT DFT_MMANS_EditDateTime DEFAULT CURRENT_TIMESTAMP,
  msModificationStatusID INT NOT NULL,
  PRIMARY KEY (mManagerID_EditedBy, ansNSAccountNumber, EditDateTime),
  CONSTRAINT FK_MMANS_ModificationStatusID FOREIGN KEY (msModificationStatusID)
  REFERENCES ModificationStatus(ModificationStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMANS_ManagerID FOREIGN KEY (mManagerID_EditedBy)
  REFERENCES Manager(ManagerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMANS_NSAccountNumber FOREIGN KEY (ansNSAccountNumber)
  REFERENCES AccountNormalSavings(NSAccountNumber),
  CONSTRAINT CHK_MMANS_ManagerID_Length CHECK (LEN(mManagerID_EditedBy) = 8),
  CONSTRAINT CHK_MMANS_NSAccountNumber_Length CHECK (LEN(ansNSAccountNumber) = 8)
)

GO

-- Inserting records to Table 26 - ManagerManageAccountNormalSavings
INSERT INTO ManagerManageAccountNormalSavings VALUES
('MA000001', '25000002', '2019-12-10 10:42:34.323', 2),
('MA000002', '25000003', '2019-12-12 11:39:34.323', 2)

GO

-- Creating Table 27 - ManagerManageAccountBonusSavings
CREATE TABLE ManagerManageAccountBonusSavings(
  mManagerID_EditedBy CHAR(8) NOT NULL,
  absBSAccountNumber INT NOT NULL,
  EditDateTime DATETIME CONSTRAINT DFT_MMABS_EditDateTime DEFAULT CURRENT_TIMESTAMP,
  msModificationStatusID INT NOT NULL,
  PRIMARY KEY (mManagerID_EditedBy, absBSAccountNumber, EditDateTime),
  CONSTRAINT FK_MMABS_ModificationStatusID FOREIGN KEY (msModificationStatusID)
  REFERENCES ModificationStatus(ModificationStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMABS_ManagerID FOREIGN KEY (mManagerID_EditedBy)
  REFERENCES Manager(ManagerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMABS_NSAccountNumber FOREIGN KEY (absBSAccountNumber)
  REFERENCES AccountBonusSavings(BSAccountNumber),
  CONSTRAINT CHK_MMABS_ManagerID_Length CHECK (LEN(mManagerID_EditedBy) = 8),
  CONSTRAINT CHK_MMABS_NSAccountNumber_Length CHECK (LEN(absBSAccountNumber) = 8)
)

GO

-- Inserting records to Table 27 - ManagerManageAccountBonusSavings
INSERT INTO ManagerManageAccountBonusSavings VALUES
('MA000001', '45000005', '2019-12-12 12:32:34.323', 2),
('MA000002', '45000002', '2019-12-13 11:56:34.323', 2)

GO

-- Creating Table 28 - ManagerManageAccountPremierSavings
CREATE TABLE ManagerManageAccountPremierSavings(
  mManagerID_EditedBy CHAR(8) NOT NULL,
  apsPSAccountNumber INT NOT NULL,
  EditDateTime DATETIME CONSTRAINT DFT_MMAPS_EditDateTime DEFAULT CURRENT_TIMESTAMP,
  msModificationStatusID INT NOT NULL,
  PRIMARY KEY (mManagerID_EditedBy, apsPSAccountNumber, EditDateTime),
  CONSTRAINT FK_MMAPS_ModificationStatusID FOREIGN KEY (msModificationStatusID)
  REFERENCES ModificationStatus(ModificationStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMAPS_ManagerID FOREIGN KEY (mManagerID_EditedBy)
  REFERENCES Manager(ManagerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMAPS_NSAccountNumber FOREIGN KEY (apsPSAccountNumber)
  REFERENCES AccountPremierSavings(PSAccountNumber),
  CONSTRAINT CHK_MMAPS_ManagerID_Length CHECK (LEN(mManagerID_EditedBy) = 8),
  CONSTRAINT CHK_MMAPS_NSAccountNumber_Length CHECK (LEN(apsPSAccountNumber) = 8)
)

GO

-- Inserting records to Table 28 - ManagerManageAccountPremierSavings
INSERT INTO ManagerManageAccountPremierSavings VALUES
('MA000001', '75000002', '2019-12-18 11:01:34.323', 2),
('MA000002', '75000006', '2019-12-20 12:32:34.323', 2)

GO

-- Creating Table 29 -	ManagerManageCustomerTransactionDeposit
CREATE TABLE ManagerManageCustomerTransactionDeposit(
  mManagerID_EditedBy CHAR(8) NOT NULL,
  ctdDTransactionNumber INT NOT NULL,
  EditDateTime DATETIME CONSTRAINT DFT_MMCTD_EditDateTime DEFAULT CURRENT_TIMESTAMP,
  msModificationStatusID INT NOT NULL,
  PRIMARY KEY (mManagerID_EditedBy, ctdDTransactionNumber, EditDateTime),
  CONSTRAINT FK_MMCTD_ModificationStatusID FOREIGN KEY (msModificationStatusID)
  REFERENCES ModificationStatus(ModificationStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMCTD_ManagerID FOREIGN KEY (mManagerID_EditedBy)
  REFERENCES Manager(ManagerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMCTD_DTransactionNumber FOREIGN KEY (ctdDTransactionNumber)
  REFERENCES CustomerTransactionDeposit(DTransactionNumber),
  CONSTRAINT CHK_MMCTD_ManagerID_Length CHECK (LEN(mManagerID_EditedBy) = 8),
  CONSTRAINT CHK_MMCTD_BSAccountNumber_Length CHECK (LEN(ctdDTransactionNumber) = 8)
)

GO

-- Inserting records to Table 29 - ManagerManageCustomerTransactionDeposit
INSERT INTO ManagerManageCustomerTransactionDeposit VALUES
('MA000001', '22000003', '2019-12-17 09:32:45.323', 3)

GO

-- Creating Table 30 - 	ManagerManageCustomerTransactionWithdrawal
CREATE TABLE ManagerManageCustomerTransactionWithdrawal(
  mManagerID_EditedBy CHAR(8) NOT NULL,
  ctwWTransactionNumber INT NOT NULL,
  EditDateTime DATETIME CONSTRAINT DFT_MMCTW_EditDateTime DEFAULT CURRENT_TIMESTAMP,
  msModificationStatusID INT NOT NULL,
  PRIMARY KEY (mManagerID_EditedBy, ctwWTransactionNumber, EditDateTime),
  CONSTRAINT FK_MMCTW_ModificationStatusID FOREIGN KEY (msModificationStatusID)
  REFERENCES ModificationStatus(ModificationStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMCTW_ManagerID FOREIGN KEY (mManagerID_EditedBy)
  REFERENCES Manager(ManagerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_MMCTW_WTransactionNumber FOREIGN KEY (ctwWTransactionNumber)
  REFERENCES CustomerTransactionWithdrawal(WTransactionNumber),
  CONSTRAINT CHK_MMCTW_ManagerID_Length CHECK (LEN(mManagerID_EditedBy) = 8),
  CONSTRAINT CHK_MMCTW_WTransactionNumber_Length CHECK (LEN(ctwWTransactionNumber) = 8)
)

GO

-- Inserting records to Table 30 - ManagerManageCustomerTransactionWithdrawal
INSERT INTO ManagerManageCustomerTransactionWithdrawal VALUES
('MA000002', '44000003', '2019-12-13 13:04:24.323', 4)

GO

-- Creating Table 31 -	SystemAdministratorManageSystemLogin
CREATE TABLE SystemAdministratorManageSystemLogin(
  saAdminID_EditedBy CHAR(8) NOT NULL,
  slSystemLoginID CHAR(8) NOT NULL,
  EditDateTime DATETIME CONSTRAINT DFT_SAMSL_EditDateTime DEFAULT CURRENT_TIMESTAMP,
  msModificationStatusID INT NOT NULL,
  PRIMARY KEY (saAdminID_EditedBy, slSystemLoginID, EditDateTime),
  CONSTRAINT FK_SAMSL_ModificationStatusID FOREIGN KEY (msModificationStatusID)
  REFERENCES ModificationStatus(ModificationStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_SAMSL_AdminID FOREIGN KEY (saAdminID_EditedBy)
  REFERENCES SystemAdministrator(AdminID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_SAMSL_SystemLoginID FOREIGN KEY (slSystemLoginID)
  REFERENCES SystemLogin(SystemLoginID),
  CONSTRAINT CHK_SAMSL_AdminID_Lengt CHECK (LEN(saAdminID_EditedBy) = 8),
  CONSTRAINT CHK_SAMSL_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8)
)

GO

-- Inserting records to Table 31 - SystemAdministratorManageSystemLogin
INSERT INTO SystemAdministratorManageSystemLogin VALUES
('SA000001', 'SL000006', '2019-12-10 10:32:35.323', 5),
('SA000002', 'SL000010', '2019-12-15 12:43:23.323', 5)
