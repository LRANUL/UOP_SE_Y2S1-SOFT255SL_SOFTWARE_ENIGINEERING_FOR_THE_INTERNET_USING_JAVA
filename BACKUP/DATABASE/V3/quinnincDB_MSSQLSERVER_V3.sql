
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


-- Creating Table 1 - Customer
CREATE TABLE Customer(
  PassportNumber CHAR(9) NOT NULL PRIMARY KEY,
  FirstName VARCHAR(20) NOT NULL,
  MiddleName VARCHAR(25),
  LastName VARCHAR(30) NOT NULL,
  LaneAddress VARCHAR(40) NOT NULL,
  City VARCHAR(20) NOT NULL,
  CONSTRAINT CHK_CT_PassportNumber_Length CHECK (LEN(PassportNumber) = 9)
)

GO

-- Inserting records to Table 1 - Customer
INSERT INTO Customer (PassportNumber, FirstName, MiddleName, LastName, LaneAddress, City) VALUES
('255647183', 'Charles', '', 'Winderson', '53 Karstens Avenue', 'Birmingham'),
('124165310', 'Rick', 'Edward', 'Brooks', '198 Warner Road', 'Liverpool'),
('144165055', 'Nuran', 'Fernando', 'Miller', '232 Northfield Pass', 'Liverpool'),
('145484164', 'John', 'Seth', 'Neil', '673 Alpine Plaza', 'Birmingham'),
('156448413', 'Caitlin', 'Lane', 'Moore', '402 Commercial Way', 'Birmingham'),
('165419489', 'Lucas', 'Troy', 'Matthews', '58 Hoard Place', 'Liverpool'),
('200143748', 'Rachel', 'Rebecca', 'Reynolds', '4215 Vernon Place', 'Liverpool'),
('200154387', 'Sansa', 'Ellen', 'Palmer', '59 South Circle', 'Birmingham'),
('200156724', 'Rob', 'Felix', 'Parker', '6 Namekagon Crossing', 'Birmingham'),
('200531848', 'Rohan', 'Wade', 'Anderson', '47 Coleman Crossing', 'Liverpool'),
('248416164', 'Olivia', 'Jane', 'Fletcher', '25 Pearson Pass', 'Birmingham'),
('345343437', 'William', 'Merle', 'Stark', '32 Carberry Lane', 'Liverpool'),
('415648413', 'Franklin', 'Arden', 'Bradley', '648 Trailsway Trail', 'Birmingham'),
('443451257', 'Jace', 'Murphy', 'Adams', '18 Oneill Road', 'Birmingham'),
('489481361', 'Jacob', 'Francis', 'Johnson', '23 Tennessee Center', 'Birmingham'),
('567863786', 'Harry', 'Jordan', 'Phillips', '168 Moland Alley', 'Birmingham'),
('676786378', 'Monica', 'Alice', 'Rose', '9 Charing Cross Parkway', 'Liverpool'),
('745572237', 'Thomas', 'Luke', 'Roberts', '5395 Nova Center', 'Birmingham'),
('874613255', 'Morgan', 'Avery', 'Armstrong', '29 Bultman Way', 'Birmingham'),
('894861104', 'Selena', 'Naomi', 'Kelly', '224 Old Gate Road', 'Liverpool')

GO

-- -- Creating Table 2 - CustomerEmailAddress
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

-- Inserting records to Table 2 - CustomerEmailAddress
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

-- Creating Table 3 - CustomerPhoneNumber
CREATE TABLE CustomerPhoneNumber(
  cPassportNumber CHAR(9) NOT NULL,
  PhoneNumber CHAR(11) NOT NULL,
  PRIMARY KEY (cPassportNumber, PhoneNumber),
  CONSTRAINT FK_C_PassportNumber_Length FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_CPN_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_CPN_PhoneNumber_Length CHECK (LEN(PhoneNumber) = 11)
)

GO

-- Inserting rcords to Table 3 - CustomerPhoneNumber
INSERT INTO CustomerPhoneNumber VALUES
('255647183', '7748-562193'),
('255647183', '7738-583927'),
('124165310', '7713-678424'),
('144165055', '7135-897426'),
('145484164', '7615-782382'),
('145484164', '7742-582945'),
('156448413', '7134-498438'),
('165419489', '7738-423163'),
('165419489', '7726-582967'),
('200143748', '7736-875167'),
('200143748', '7726-589275'),
('200154387', '7674-613842'),
('200156724', '7634-857218'),
('200531848', '7647-531473'),
('200531848', '7537-950274'),
('248416164', '7135-487021'),
('345343437', '7135-753247'),
('415648413', '7624-044045'),
('415648413', '7629-857945'),
('443451257', '7657-912340'),
('489481361', '7635-874213'),
('567863786', '7136-894276'),
('567863786', '7583-673856'),
('676786378', '7615-794122'),
('745572237', '7146-913286'),
('745572237', '7159-683954'),
('874613255', '7798-421531'),
('874613255', '7739-583739'),
('894861104', '7648-975127'),
('894861104', '7639-859279')

GO

-- Creating Table 4 - SystemAdministrator
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
  RegistrationDateTime DATETIME CONSTRAINT DFT_SA_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  slSystemLoginID CHAR(8),
  saAdminID_RegisteredBy CHAR(8) NOT NULL,
  PRIMARY KEY (AdminID),
  CONSTRAINT FK_SA_AdminID FOREIGN KEY (saAdminID_RegisteredBy)
  REFERENCES SystemAdministrator(AdminID),
  CONSTRAINT CHK_SA_EmailAddress_@ CHECK (EmailAddress LIKE '%@%'),
  CONSTRAINT CHK_SA_AdminIDRegisteredBy_Length CHECK (LEN(saAdminID_RegisteredBy) = 8),
  CONSTRAINT CHK_SL_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8)
)

GO

-- Inserting records to Table 4 - SystemAdministrator
INSERT INTO SystemAdministrator
(Branch, FirstName, MiddleName, LastName, LaneAddress, City, EmailAddress, RegistrationDateTime, saAdminID_RegisteredBy) VALUES
('Liverpool','Peter','Adam','Fransis','43 Hill Road','Liverpool','Peter_Ad332@gmail.com', '2019-11-15 09:34:56.583','SA000001'),
('Birmingham', 'Gipsy', 'Tommy', 'Rase', '86 Mallory Way', 'Birmingham', 'trase0@youku.com', '2019-11-16 12:54:12.573', 'SA000001'),
('Birmingham','Cthrine', 'Cacilia', 'Baines', '98977 Fuller Trail', 'Birmingham', 'cbaines1@jigsy.com', '2019-11-16 11:33:34.482', 'SA000001'),
('Birmingham', 'Neille', 'Singh', 'Arthan', '7367 Carberry Point', 'Birmingham', 'nsingh2@cloudflare.com', '2019-11-17 13:23:50.382', 'SA000002'),
('Birmingham', 'Goran', 'Francis', 'Carriage', '57 Oxford Junction', 'Birmingham', 'ginstock3@typepad.com', '2019-11-17 10:34:34.492', 'SA000003'),
('Liverpool', 'Ellwood', 'Guidotti', 'Eixenberger', '1226 Crownhardt Trail', 'Liverpool', 'eguidotti4@uol.com.br', '2019-11-17 09:52:12.482', 'SA000001')

GO

-- Creating Table 5 - SystemAdministratorPhoneNumber
CREATE TABLE SystemAdministratorPhoneNumber(
  saAdminID CHAR(8) NOT NULL,
  PhoneNumber CHAR(11) NOT NULL,
  PRIMARY KEY (saAdminID, PhoneNumber),
  CONSTRAINT FK_SAPN_AdminID FOREIGN KEY (saAdminID)
  REFERENCES SystemAdministrator(AdminID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_SAPN_AdminID_Length CHECK (LEN(saAdminID) = 8),
  CONSTRAINT CHK_SAPN_PhoneNumber_Length CHECK (LEN(PhoneNumber) = 11)
)

GO

-- Inserting records to Table 5 - SystemAdministratorPhoneNumber
INSERT INTO SystemAdministratorPhoneNumber VALUES
('SA000001', '7798-035930'),
('SA000001', '7637-255059'),
('SA000002', '7909-302358'),
('SA000002', '7669-968641'),
('SA000003', '7747-582749'),
('SA000004', '7727-478891'),
('SA000005', '7728-492749')

GO

-- Creating Table 6 - SystemLoginPosition
CREATE TABLE SystemLoginPosition(
  SLPRecordID INT IDENTITY(1,1),
  PositionID AS CAST('PT' + RIGHT('000000' + CAST(SLPRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  Position VARCHAR(30) NOT NULL,
  PRIMARY KEY(PositionID)
)

GO

-- Inserting records to Talbe 6 - SystemLoginPosition
INSERT INTO SystemLoginPosition(Position) VALUES
('Bank Teller'),
('Bank Manager'),
('Bank System Administrator')

GO

-- Creating Table 7 - SystemLogin
CREATE TABLE SystemLogin(
  SLRecordID INT IDENTITY(1,1),
  SystemLoginID AS CAST('SL' + RIGHT('000000' + CAST(SLRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  Username VARCHAR(30) NOT NULL UNIQUE,
  Password CHAR(64) NOT NULL UNIQUE,
  slpPositionID CHAR(8) NOT NULL,
  saAdminID CHAR(8) NOT NULL,
  PRIMARY KEY (SystemLoginID),
  CONSTRAINT FK_SL_PositionID FOREIGN KEY (slpPositionID)
  REFERENCES SystemLoginPosition(PositionID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_SL_AdminID FOREIGN KEY (saAdminID)
  REFERENCES SystemAdministrator(AdminID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_SL_PositionID_Length CHECK (LEN(slpPositionID) = 8),
  CONSTRAINT CHK_SL_AdminID_Length CHECK (LEN(saAdminID) = 8)
)

GO

-- Inserting records to Table 7 - SystemLogin
INSERT INTO SystemLogin (Username, Password, slpPositionID, saAdminID) VALUES
('BankT', '4381dc2ab14285160c808659aee005d51255add7264b318d07c7417292c7442c', 'PT000003', 'SA000001'),   --password String : bank    Role : Bank Teller
('ToRasesy@66431', 'c78b8bf63d17fc397dfc10c3aaf66e0a95bc93b19e83a9d483af91f5d2b47c44', 'PT000003', 'SA000002'),  --password String :  Rasesy@66         Role : Bank Teller
('BasiCthClia@45762', '344c5228ba17f8111c285c28951d03e73f832ebcc612c29a3a74d880ea31b318', 'PT000003', 'SA000003'),  --password String : BasiCthClia@45       Role : Bank Teller
('ArthaNeliagh@34733', '01fb0707fa0cddaa60c9d890cab5fec68af44797d19fd2d0b72023a83985c149', 'PT000003', 'SA000004'), --password String :  ArthaNeliagh@34       Role : Bank Teller
('RiaFransRan@56846', '620bba49c6fe20ad43e69afc236de9cd26092b6b6c8132c4dea1f1ab3f74164c', 'PT000003', 'SA000005'), --password String :  RiaFransRan@56        Role : Bank Teller  
('EixenGuittiEllod@39482', '811edaff4ab0f1134b19bcf23278edb6d189517a96140fb487b733eba08d07b0', 'PT000003', 'SA000006'), --password String :  EixenGuittiEllod@39     Role : Bank Teller
('OdenAdrasWiln@38593', '48b93387577718cc813672b06b7f8556ee0c64270c715cfbb2b89205f107954c', 'PT000002', 'SA000001'), --password String :  OdenAdrasWiln@38     Role : Bank Manager
('HcketonZaCary@28549', '956853b66e5da3711d92c755daee02db1f2a90c9b4fc18f65fe07aa592fd718f', 'PT000002', 'SA000002'), --password String :  HcketonZaCary@28	    Role : Bank Manager		
('MatiRuFeli@58375', '8c435c62a4a2f6dfb9c0695dbd9d8e04dc7d2a5e36271a6459a7f336fcf6dec4', 'PT000001', 'SA000002'), --password String :  MatiRuFeli@58    Role : Bank Data Administrator
('BersJOdy@79205', 'b6e4676d4d0f8f0d7192ad5a450a401e7ea2f51892bb0671a19550eff6d1f154', 'PT000001', 'SA000002'), --password String :   BersJOdy@79	      Role : Bank Data Administrator
('JanskiRalhGar@58105', '8995796d950e15f219d6acc39c0fb439458c3f0c4a982a673c708e33f653f9e6', 'PT000001', 'SA000001'), --password String :  JanskiRalhGar@58     Role : Bank Data Administrator
('TreAoBel@68205', '32f41454a4557e3b0d73e1469dad6916bd0b2365712051b6a55ce106ef699e24', 'PT000001', 'SA000004'), --password String :  TreAoBel@68     Role : Bank Data Administrator
('SeanRodDlon@58503', '29d5167464bf98d7c2a0fbf325026417a6094ee54d43eb428df302e6af937e52', 'PT000001', 'SA000001'), --password String :  SeanRodDlon@58     Role : Bank Data Administrator
('AbreyHarDil@48205', '9df5a66ddc929603225663e3295902e70fef668c5a4f1646db89b333131b623c', 'PT000001', 'SA000001'), --password String :  AbreyHarDil@48     Role : Bank Data Administrator
('BlaxrYananElo@69205', '24c7ad78d95a2ada9da282b7b8c0960800bba44647de9669c5799988451de450', 'PT000001', 'SA000004'), --password String :  BlaxrYananElo@692     Role : Bank Data Administrator
('GernFeliaMay@19503', 'f9ed2601857f9c3804ed96362302e0effdf509e3f09bb03adcb2eee0d7157985', 'PT000001', 'SA000006'), --password String :  GernFeliaMay@19     Role : Bank Data Administrator
('BaitAryBlain@50284', '19566e09fc2af820ab322c835c358a1e3aabc287982240dc9d779de559b85247', 'PT000001', 'SA000006'),  --password String :  BaitAryBlain@502     Role : Bank Data Administrator
('MedsKayMartca@60482', 'fc040371fc88998da38b4b9703a35778ef84e94bd3a89d91fe6c372327bd08fb', 'PT000001', 'SA000004') --password String :  MedsKayMartca@604     Role : Bank Data Administrator

GO

-- Altering Table 4 - SystemAdministrator to add foerign key
ALTER TABLE SystemAdministrator
ADD CONSTRAINT FK_SL_SystemLoginID FOREIGN KEY (slSystemLoginID)
REFERENCES SystemLogin(SystemLoginID)

GO

-- Updating the records after altering Table 4 - SystemAdministrator with a new foreign key
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000001' WHERE AdminID = 'SA000001'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000002' WHERE AdminID = 'SA000002'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000003' WHERE AdminID = 'SA000003'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000004' WHERE AdminID = 'SA000004'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000005' WHERE AdminID = 'SA000005'
UPDATE SystemAdministrator SET slSystemLoginID = 'SL000006' WHERE AdminID = 'SA000006'

GO

-- Altering Table 4 - SystemAdministrator to alter comlumn to not null
ALTER TABLE SystemAdministrator
ALTER COLUMN slSystemLoginID CHAR(8) NOT NULL

GO

-- Creating Table 8 - SystemLoginDateTime
CREATE TABLE SystemLoginDateTime(
  slSystemLoginID CHAR(8) NOT NULL,
  LoginDateTime DATETIME CONSTRAINT DFT_SLDT_LoginDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(slSystemLoginID, LoginDateTime),
  CONSTRAINT FK_SLDT_SystemLoginID FOREIGN KEY (slSystemLoginID)
  REFERENCES SystemLogin(SystemLoginID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_SLDT_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8)
)

GO

-- Inserting records to Table 8 - SystemLoginDateTime
INSERT INTO SystemLoginDateTime VALUES
('SL000001', '2019-11-15 09:34:56.583'),
('SL000002', '2019-11-15 09:34:56.583'),
('SL000003', '2019-11-15 09:34:56.583'),
('SL000004', '2019-11-15 09:34:56.583'),
('SL000005', '2019-11-15 09:34:56.583'),
('SL000006', '2019-11-15 09:34:56.583')

GO

-- Creating Table 9 - Teller
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
  RegistrationDateTime DATETIME CONSTRAINT DFT_T_RegirstrationDateTime DEFAULT CURRENT_TIMESTAMP,
  slSystemLoginID CHAR(8) NOT NULL,
  PRIMARY KEY (TellerID),
  CONSTRAINT FK_T_SystemLoginID FOREIGN KEY (slSystemLoginID)
  REFERENCES SystemLogin(SystemLoginID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_T_EmailAddress_@ CHECK (EmailAddress LIKE '%@%'),
  CONSTRAINT CHK_T_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8)
)

GO

-- Inserting records to Table 9 - Teller
INSERT INTO Teller
(Branch, FirstName, MiddleName, LastName, LaneAddress, City, EmailAddress, RegistrationDateTime, slSystemLoginID)
VALUES
('Birmingham', 'Felisha', 'Ruperta', 'Martijn', '155 Algoma Trail', 'Birmingham', 'fmartijn0@blogtalkradio.com', '2019-11-20 09:31:59.283', 'SL000009'),
('Birmingham', 'Odey', 'Jerri', 'Berzins', '382 Badeau Terrace', 'Birmingham', 'oberzins1@wordpress.org', '2019-11-20 11:44:23.523', 'SL000010'),
('Liverpool', 'Garnham', 'Raleigh', 'Janikowski', '4 Bobwhite Center', 'Liverpool', 'ggarnham2@biglobe.ne.jp', '2019-11-21 10:32:36.383', 'SL000011'),
('Birmingham', 'Belva', 'Aron', 'Treweek', '7 Valley Edge Alley', 'Birmingham', 'btreweek3@dailymail.co.uk', '2019-11-21 12:23:14.343', 'SL000012'),
('Liverpool',  'Donlon', 'Rodger', 'Seaman', '898 Fair Oaks Way', 'Liverpool', 'jdonlon4@deviantart.com', '2019-11-21 12:54:16.683', 'SL000013'),
('Liverpool',  'Dikels', 'Harwell', 'Aubrey', '	273 Larry Park', 'Liverpool', 'mdikels5@goo.gl', '2019-11-21 13:44:26.423', 'SL000014'),
('Birmingham', 'Elmo', 'Yanaton', 'Blaxter', '74 Springs Court', 'Birmingham', 'eblaxter6@gizmodo.com', '2019-11-22 09:12:34.542', 'SL000015'),
('Liverpool',  'Maddy', 'Fenelia', 'Germaine', '732 Bobwhite Plaza', 'Liverpool', 'mmabbutt7@sciencedirect.com', '2019-11-22 09:23:46.465', 'SL000016'),
('Liverpool',  'Blannin', 'Arney', 'Baitey', '311 Jay Drive', 'Liverpool', 'tblannin8@zdnet.com', '2019-11-22 10:04:34.683', 'SL000017'),
('Birmingham', 'Martica', 'Kathy', 'Meddows', '708 Delladonna Way', 'Birmingham', 'mmeddows9@newsvine.com', '2019-11-22 10:38:26.475', 'SL000018')

GO

-- Creating Table 10 - TellerShiftStartDateTime
CREATE TABLE TellerShiftStartDateTime(
  tTellerID CHAR(8) NOT NULL,
  ShiftStartDateTime DATETIME CONSTRAINT DFT_TSSDT_ShidtStartDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (tTellerID, ShiftStartDateTime),
  CONSTRAINT FK_TSSDT_TellerID FOREIGN KEY (tTellerID)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_TSSDT_TellerID_Length CHECK (LEN(tTellerID) = 8)
)

GO

-- Inserting records to Table 10 - TellerShiftStartDateTime
INSERT INTO TellerShiftStartDateTime (tTellerID, ShiftStartDateTime) VALUES
('TE000001', '2019-11-27 09:00:00.000'),
('TE000002', '2019-11-27 09:00:00.000'),
('TE000003', '2019-11-27 09:00:00.000'),
('TE000004', '2019-11-27 09:00:00.000'),
('TE000005', '2019-11-27 09:00:00.000')

GO

-- Creating Table 11 - TellerShiftEndDateTime
CREATE TABLE TellerShiftEndDateTime(
  tTellerID CHAR(8) NOT NULL,
  TellerShiftEndDateTime DATETIME CONSTRAINT DFT_TSEDT_ShiftEndDateTime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (tTellerID, TellerShiftEndDateTime),
  CONSTRAINT FK_Teller_ShiftEndDateTime FOREIGN KEY (tTellerID)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_TSEDT_TellerID_Length CHECK (LEN(tTellerID) = 8)
)

GO

-- Inserting records to Table 11 - TellerShiftEndDateTime
INSERT INTO TellerShiftEndDateTime (tTellerID, TellerShiftEndDateTime) VALUES
('TE000001', '2019-11-27 15:00:00.000'),
('TE000002', '2019-11-27 15:00:00.000'),
('TE000003', '2019-11-27 15:00:00.000'),
('TE000004', '2019-11-27 15:00:00.000'),
('TE000005', '2019-11-27 15:00:00.000')

GO

-- Creating Table 12 - TellerPhoneNumber
CREATE TABLE TellerPhoneNumber(
  tTellerID CHAR(8) NOT NULL,
  PhoneNumber CHAR(11) NOT NULL,
  PRIMARY KEY(tTellerID, PhoneNumber),
  CONSTRAINT FK_TPN_PhoneNumber FOREIGN KEY (tTellerID)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_TPN_TellerID_Length CHECK (LEN(tTellerID) = 8),
  CONSTRAINT CHK_TPN_PhoneNumber_Length CHECK (LEN(PhoneNumber) = 11)
)

GO

-- Inserting records to Table 12 - TellerPhoneNumber
INSERT INTO TellerPhoneNumber VALUES
('TE000001', '765-5834968'),
('TE000001', '742-4492534'),
('TE000002', '742-4495832'),
('TE000003', '793-2340824'),
('TE000004', '723-3439485'),
('TE000005', '749-5839502'),
('TE000005', '729-3849248'),
('TE000006', '738-8359282'),
('TE000007', '724-3948298'),
('TE000008', '732-3849219'),
('TE000009', '723-1498593'),
('TE000010', '732-4829104')

GO

-- Creating Table 13 - AccountStatus
CREATE TABLE AccountStatus(
  ASRecordID INT IDENTITY(1,1),
  AccountStatusID AS CAST('AS' + RIGHT('000000' + CAST(ASRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  AccountStatus VARCHAR(10) NOT NULL,
  PRIMARY KEY (AccountStatusID)
)

GO

-- Inserting records to Table 13 - AccountStatus
INSERT INTO AccountStatus (AccountStatus) VALUES
('ACTIVE'),
('ONHOLD'),
('DISABLED')

GO

-- Creating Table 14 - AccountNormalSavings
CREATE TABLE AccountNormalSavings(
  ANSRecordID INT IDENTITY(1,1),
  NSAccountNumber AS CAST('25' + RIGHT('000000' + CAST(ANSRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  NSAccountBalance MONEY NOT NULL,
  NSRegistrationDateTime DATETIME CONSTRAINT DFT_ANS_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  cPassportNumber CHAR(9) NOT NULL,
  tTellerID_RegisteredBy CHAR(8) NOT NULL,
  asAccountStatusID CHAR(8) NOT NULL,
  PRIMARY KEY (NSAccountNumber),
  CONSTRAINT FK_ANS_PassportNumber FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ANS_TellerID FOREIGN KEY (tTellerID_RegisteredBy)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ANS_AccountStatusID FOREIGN KEY (asAccountStatusID)
  REFERENCES AccountStatus(AccountStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_ANS_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_ANS_TellerID_Length CHECK (LEN(tTellerID_RegisteredBy) = 8),
  CONSTRAINT CHK_ANS_AccountStatusID CHECK (LEN(asAccountStatusID) = 8)
)

GO

-- Inserting records to Table 14 - AccountNormalSavings
INSERT INTO AccountNormalSavings (NSAccountBalance, NSRegistrationDateTime, cPassportNumber, tTellerID_RegisteredBy, asAccountStatusID) VALUES
('140.23', '2019-11-19 13:54:43.583', '144165055', 'TE000001', 'AS000001'),
('150.39', '2019-11-20 09:23:23.647', '156448413','TE000003', 'AS000001'),
('534.49', '2019-11-21 11:54:23.153', '200143748', 'TE000005', 'AS000002'),
('589.25', '2019-11-25 13:56:12.357', '200156724', 'TE000010', 'AS000001'),
('522.25', '2019-11-26 10:53:54.693', '415648413', 'TE000010', 'AS000001'),
('2200.58', '2019-11-28 12:42:33.602', '489481361', 'TE000004', 'AS000001'),
('398.89', '2019-11-29 11:24:29.593', '745572237', 'TE000004', 'AS000003'),
('700.43', '2019-11-29 11:19:53.583', '874613255', 'TE000002', 'AS000001')

GO

-- Creating Table 15 - AccountBonusSavings
CREATE TABLE AccountBonusSavings(
  ABSRecordID INT IDENTITY(1,1),
  BSAccountNumber AS CAST('45' + RIGHT('000000' + CAST(ABSRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  BSAccountBalance MONEY NOT NULL,
  BSRegistrationDateTime DATETIME CONSTRAINT DFT_ABS_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  cPassportNumber CHAR(9) NOT NULL,
  tTellerID_RegisteredBy CHAR(8) NOT NULL,
  asAccountStatusID CHAR(8) NOT NULL,
  PRIMARY KEY (BSAccountNumber),
  CONSTRAINT FK_ABS_PassportNumber FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ABS_TellerID FOREIGN KEY (tTellerID_RegisteredBy)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_ABS_AccountStatusID FOREIGN KEY (asAccountStatusID)
  REFERENCES AccountStatus(AccountStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_ABS_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_ABS_TellerID_Length CHECK (LEN(tTellerID_RegisteredBy) = 8),
  CONSTRAINT CHK_ABS_AccountStatusID CHECK (LEN(asAccountStatusID) = 8)
)

GO

-- Inserting records to Table 15 - AccountBonusSavings
INSERT INTO AccountBonusSavings (BSAccountBalance, BSRegistrationDateTime, cPassportNumber, tTellerID_RegisteredBy, asAccountStatusID) VALUES
('541.25', '2019-11-18 08:23:56.654', '124165310', 'TE000002', 'AS000001'),
('358.24', '2019-11-19 13:43:43.302', '145484164', 'TE000001', 'AS000002'),
('653.29', '2019-11-25 14:53:56.543', '248416164', 'TE000006', 'AS000001'),
('649.78', '2019-11-28 14:56:35.593', '676786378', 'TE000003', 'AS000001'),
('655.98', '2019-11-29 13:12:09.937', '894861104', 'TE000005', 'AS000003')

GO

-- Creating Table 16 - AccountPremierSavings
CREATE TABLE AccountPremierSavings(
  APSRecordID INT IDENTITY(1,1),
  PSAccountNumber AS CAST('75' + RIGHT('000000' + CAST(APSRecordID AS VARCHAR(6)), 6) AS CHAR(8)) PERSISTED,
  PSAccountBalance MONEY NOT NULL,
  PSRegistrationDateTime DATETIME CONSTRAINT DFT_APS_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  cPassportNumber CHAR(9) NOT NULL,
  tTellerID_RegisteredBy CHAR(8) NOT NULL,
  asAccountStatusID CHAR(8) NOT NULL,
  PRIMARY KEY (PSAccountNumber),
  CONSTRAINT FK_APS_PassportNumber FOREIGN KEY (cPassportNumber)
  REFERENCES Customer(PassportNumber) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_APS_TellerID FOREIGN KEY (tTellerID_RegisteredBy)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_APS_AccountStatusID FOREIGN KEY (asAccountStatusID)
  REFERENCES AccountStatus(AccountStatusID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_APS_PassportNumber_Length CHECK (LEN(cPassportNumber) = 9),
  CONSTRAINT CHK_APS_TellerID_Length CHECK (LEN(tTellerID_RegisteredBy) = 8),
  CONSTRAINT CHK_APS_AccountStatusID CHECK (LEN(asAccountStatusID) = 8)
)

GO

-- Inserting records to Table 16 - AccountPremierSavings
INSERT INTO AccountPremierSavings (PSAccountBalance, PSRegistrationDateTime, cPassportNumber, tTellerID_RegisteredBy, asAccountStatusID) VALUES
('1486.25', '2019-11-18 10:53:23.623', '255647183', 'TE000001', 'AS000002'),
('2586.21', '2019-11-20 11:34:43.464', '165419489', 'TE000004', 'AS000001'),
('2925.25', '2019-11-22 10:12:54.435', '200154387', 'TE000007', 'AS000001'),
('1945.25', '2019-11-25 14:43:04.963', '200531848', 'TE000009', 'AS000001'),
('1100.36', '2019-11-26 09:24:43.854', '345343437', 'TE000007', 'AS000003'),
('5289.25', '2019-11-27 09:14:46.492', '443451257', 'TE000008', 'AS000001'),
('1020.09', '2019-11-28 13:32:59.593', '567863786', 'TE000008', 'AS000001')

GO

-- Creating Table 17 - CustomerTransactionDeposit
CREATE TABLE CustomerTransactionDeposit(
  DTransactionNumber INT IDENTITY(22000000,1) PRIMARY KEY,
  TransactionAmount MONEY NOT NULL,
  TransactionDateTime DATETIME CONSTRAINT DFT_CTD_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  ansNSAccountNumber CHAR(8),
  absBSAccountNumber CHAR(8),
  apsPSAccountNumber CHAR(8),
  tTellerID CHAR(8) NOT NULL,
  CONSTRAINT FK_CTD_NSAccountNumber FOREIGN KEY (ansNSAccountNumber)
  REFERENCES AccountNormalSavings(NSAccountNumber),
  CONSTRAINT FK_CTD_BSAccountNumber FOREIGN KEY (absBSAccountNumber)
  REFERENCES AccountBonusSavings(BSAccountNumber),
  CONSTRAINT FK_CTD_PSAccountNumber FOREIGN KEY (apsPSAccountNumber)
  REFERENCES AccountPremierSavings(PSAccountNumber),
  CONSTRAINT FK_CTD_TellerID FOREIGN KEY (tTellerID)
  REFERENCES Teller(TellerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_CTD_TransactionAmount_Amount CHECK (TransactionAmount > 0.00 ),
  CONSTRAINT CHK_CTD_NSAccountNumber_BSAccountNumber_PSAccountNumber_Length CHECK ( (LEN(ansNSAccountNumber) = 8) OR (LEN(absBSAccountNumber) = 8) OR (LEN(apsPSAccountNumber) = 8) ),
  CONSTRAINT CHK_CTD_NSAccountNumber_BSAccountNumber_PSAccountNumber_NotNullVerification CHECK ((ansNSAccountNumber = ' ') OR (absBSAccountNumber = ' ') OR (apsPSAccountNumber = ' ')),
  CONSTRAINT CHK_CTD_TellerID_Length CHECK (LEN(ansNSAccountNumber) = 8)
)

GO

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

-- Inserting records to Table 17 - CustomerTransactionDeposit, to Normal Savings Account Type
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, ansNSAccountNumber, tTellerID)
VALUES
('100.50', '2019-11-25 10:45:23.258', '25000001', 'TE000001'),
('200.50', '2019-11-26 10:45:45.846', '25000002', 'TE000003'),
('130.50', '2019-11-26 12:43:25.458', '25000003', 'TE000005'),
('500.50', '2019-11-26 12:43:25.458', '25000004', 'TE000005'),
('300.50', '2019-11-26 12:43:25.458', '25000005', 'TE000008'),
('350.50', '2019-11-26 12:43:25.458', '25000006', 'TE000007'),
('150.50', '2019-11-26 12:43:25.458', '25000008', 'TE000004')

GO

-- Inserting records to Table 17 - CustomerTransactionDeposit, to Bonus Savings Account Type
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, absBSAccountNumber, tTellerID)
VALUES
('350.10', '2019-11-25 11:36:48.369', '45000001', 'TE000002'),
('600.50', '2019-11-26 12:43:25.458', '45000002', 'TE000006'),
('600.50', '2019-11-26 12:43:25.458', '45000003', 'TE000003'),
('350.50', '2019-11-26 12:43:25.458', '45000004', 'TE000006'),
('440.50', '2019-11-26 12:43:25.458', '45000005', 'TE000007')

GO

-- Inserting records to Table 17 - CustomerTransactionDeposit, to Premier Savings Account Type
INSERT INTO CustomerTransactionDeposit
(TransactionAmount, TransactionDateTime, apsPSAccountNumber, tTellerID)
VALUES
('1500.50', '2019-11-26 12:43:25.458', '75000001', 'TE000004'),
('1140.50', '2019-11-26 12:43:25.458', '75000002', 'TE000001'),
('1260.50', '2019-11-26 12:43:25.458', '75000003', 'TE000010'),
('1470.50', '2019-11-26 12:43:25.458', '75000005', 'TE000005'),
('1900.50', '2019-11-26 12:43:25.458', '75000006', 'TE000010'),
('1560.50', '2019-11-26 12:43:25.458', '75000007', 'TE000001')

GO

-- Crating Table 18 - CustomerTransactionWithdrawal
CREATE TABLE CustomerTransactionWithdrawal(
  WTransactionNumber INT IDENTITY(44000000,1) PRIMARY KEY,
  TransactionAmount MONEY NOT NULL,
  TransactionDateTime DATETIME CONSTRAINT DFT_CTW_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  ansNSAccountNumber CHAR(8),
  absBSAccountNumber CHAR(8),
  apsPSAccountNumber CHAR(8),
  tTellerID CHAR(8) NOT NULL,
  CONSTRAINT FK_CTW_NSAccountNumber FOREIGN KEY (ansNSAccountNumber)
  REFERENCES AccountNormalSavings(NSAccountNumber),
  CONSTRAINT FK_CTW_BSAccountNumber FOREIGN KEY (absBSAccountNumber)
  REFERENCES AccountBonusSavings(BSAccountNumber),
  CONSTRAINT FK_CTW_PSAccountNumber FOREIGN KEY (apsPSAccountNumber)
  REFERENCES AccountPremierSavings(PSAccountNumber),
  CONSTRAINT FK_CTW_TellerID FOREIGN KEY (tTellerID)
  REFERENCES Teller(TellerID),
  CONSTRAINT CHK_CTW_TransactionAmount_Amount CHECK (TransactionAmount > 0.00 ),
  CONSTRAINT CHK_CTW_NSAccountNumber_BSAccountNumber_PSAccountNumber_Length CHECK ( (LEN(ansNSAccountNumber) = 8) OR (LEN(absBSAccountNumber) = 8) OR (LEN(apsPSAccountNumber) = 8) ),
  CONSTRAINT CHK_CTW_NSAccountNumber_BSAccountNumber_PSAccountNumber_NotNullVerification CHECK ((ansNSAccountNumber = ' ') OR (absBSAccountNumber = ' ') OR (apsPSAccountNumber = ' ')),
  CONSTRAINT CHK_CTW_TellerID_Length CHECK (LEN(ansNSAccountNumber) = 8)
)

GO

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

-- Inserting records to Table 18 - CustomerTransactionWithdrawal, to Normal Savings Account Type
INSERT INTO CustomerTransactionWithdrawal
(TransactionAmount, TransactionDateTime, ansNSAccountNumber, tTellerID)
VALUES
('50.50', '2019-11-27 09:47:41.259', '25000001', 'TE000001'),
('40.50', '2019-11-27 12:45:42.356', '25000002', 'TE000003'),
('60.50', '2019-11-28 13:26:36.412', '25000003', 'TE000005')

GO

-- Inserting records to Table 18 - CustomerTransactionWithdrawal, to Bonus Savings Account Type
INSERT INTO CustomerTransactionWithdrawal
(TransactionAmount, TransactionDateTime, absBSAccountNumber, tTellerID)
VALUES
('50.10', '2019-11-27 10:36:32.954', '45000001', 'TE000002')

GO

-- Inserting records to Table 18 - CustomerTransactionWithdrawal, to Premier Savings Account Type
INSERT INTO CustomerTransactionWithdrawal
(TransactionAmount, TransactionDateTime, apsPSAccountNumber, tTellerID)
VALUES
('240.50', '2019-11-28 13:12:34.432', '75000003', 'TE000005'),
('160.50', '2019-11-28 13:22:53.244', '75000002', 'TE000006')

GO

-- Creating Table 19 - Manager
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
  RegistrationDateTime DATETIME CONSTRAINT DFT_M_RegistrationDateTime DEFAULT CURRENT_TIMESTAMP,
  slSystemLoginID CHAR(8) NOT NULL,
  PRIMARY KEY(ManagerID),
  CONSTRAINT FK_M_SystemLogin FOREIGN KEY (slSystemLoginID)
  REFERENCES SystemLogin(SystemLoginID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_M_EmailAddress_@ CHECK (EmailAddress LIKE '%@%'),
  CONSTRAINT CHK_M_SystemLoginID_Length CHECK (LEN(slSystemLoginID) = 8)
)

GO

-- Inserting records to Table 19 - Manager
INSERT INTO Manager (Branch, FirstName, MiddleName, LastName, LaneAddress, City, EmailAddress, RegistrationDateTime, slSystemLoginID)
VALUES
('Liverpool', 'Wildon', 'Andras', 'Odeson', '	690 Hauk Park', 'Liverpool', 'wodeson0@nationalgeographic.com', '2019-11-19 12:12:34.323', 'SL000007'),
('Birmingham', 'Carlye', 'Zabrina', 'Hackleton', '267 Waxwing Trail', 'Birmingham', 'chackletong@washington.edu', '2019-11-19 13:45:24.542', 'SL000008')

GO

-- Creating Table 20 - ManagerPhoneNumber
CREATE TABLE ManagerPhoneNumber(
  mManagerID CHAR(8) NOT NULL,
  PhoneNumber CHAR(11) NOT NULL,
  PRIMARY KEY(mManagerID, PhoneNumber),
  CONSTRAINT FK_M_ManagerID FOREIGN KEY (mManagerID)
  REFERENCES Manager(ManagerID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT CHK_M_ManagerID_Length CHECK (LEN(mManagerID) = 8),
  CONSTRAINT CHK_M_PhoneNumber_Length CHECK (LEN(PhoneNumber) = 11)
)

GO

-- Inserting records to Table 20 - ManagerPhoneNumber
INSERT INTO ManagerPhoneNumber VALUES
('MA000001', '739-3392492'),
('MA000001', '784-3829891'),
('MA000002', '794-2940148'),
('MA000002', '738-3893482')
