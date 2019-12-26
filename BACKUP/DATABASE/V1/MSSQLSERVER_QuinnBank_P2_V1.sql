USE quinn_inc;

CREATE TABLE customer (
  ACCOUNT_NUMBER BIGINT NOT NULL,
  NID_NUMBER BIGINT NOT NULL,
  ACC_TYPE VARCHAR(15) NOT NULL,
  FIRST_NAME VARCHAR(50) NOT NULL,
  MIDDLE_NAME VARCHAR(50) NOT NULL,
  LAST_NAME VARCHAR(50) NOT NULL,
  BALANCE FLOAT NOT NULL DEFAULT '0.00',
  EMAIL_ADDRESS VARCHAR(50) NOT NULL,
  MOBILE_NUMBER VARCHAR(10) NOT NULL,
  PRIMARY KEY (ACCOUNT_NUMBER, NID_NUMBER)
);

INSERT INTO customer (ACCOUNT_NUMBER, NID_NUMBER, ACC_TYPE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, BALANCE, EMAIL_ADDRESS, MOBILE_NUMBER) VALUES
(1002448464, 255647189432, 'PREMIER_SAVINGS', 'Charles', '', 'Winderson', 250, 'charls@icloud.com', '0774856219'),
(5984684623, 124165310564, '', 'Rick', 'Edward', 'Brooks', 0, 'Brooks@yahoo.com', '0771367842'),
(9561654133, 144165055654, '', 'Nuran', 'Fernando', 'Miller', 0, 'Nuran_3211@gmail.com', '0713589742'),
(8465320089, 145484164106, '', 'John', 'Seth', 'Neil', 0, 'neil7@gmail.com', '0761578238'),
(4169856616, 156448413104, '', 'Caitlin', 'Lane', 'Moore', 0, 'Caitlin412@gmail.com', '0713449843'),
(5261065161, 165419489416, '', 'Lucas', 'Troy', 'Matthews', 0, 'mathew@gmail.com', '0773842316'),
(6765164689, 200143748675, '', 'Rachel', 'Rebecca', 'Reynolds', 0, 'Rachel14@gmail.com', '0773687516'),
(1851561616, 200154387348, '', 'Sansa', 'Ellen', 'Palmer', 0, 'Palmer_45@gmail.com', '0767461384'),
(2306514965, 200156724342, '', 'Rob', 'Felix', 'Parker', 0, 'rob54@gmail.com', '0763485721'),
(8001243764, 200531848736, '', 'Rohan', 'Wade', 'Anderson', 0, 'Rohan4@gmail.com', '0764753147'),
(8949651335, 248416164841, '', 'Olivia', 'Jane', 'Fletcher', 0, 'Olivia_fl@gmail.com', '0713548702'),
(4988451621, 345343437887, '', 'William', 'Merle', 'Stark', 0, 'starks@gmail.com', '0713575324'),
(8465161232, 415648413103, '', 'Franklin', 'Arden', 'Bradley', 0, 'Bradley145@yahoo.com', '0762404404'),
(9546842123, 443451257841, '', 'Jace', 'Murphy', 'Adams', 0, 'Adams_j@yahoo.com', '0765791234'),
(8949845162, 489481361605, '', 'Jacob', 'Francis', 'Johnson', 0, 'jacob@yahoo.com', '0763587421'),
(9846511653, 567863786786, '', 'Harry', 'Jordan', 'Phillips', 0, 'phill@gmail.com', '0713689427'),
(8469851261, 676786378678, '', 'Monica', 'Alice', 'Rose', 0, 'Monica410@gmail.com', '0761579412'),
(1641989495, 745572237783, '', 'Thomas', 'Luke', 'Roberts', 0, 'Thomas123@gmail.com', '0714691328'),
(8016463554, 874613255486, '', 'Morgan', 'Avery', 'Armstrong', 0, 'Morgan21@gmail.com', '0779842153'),
(8495262163, 894861104640, '', 'Selena', 'Naomi', 'Kelly', 0, 'Selena_k@gmail.com', '0764897512');


CREATE TABLE login (
    USERNAME VARCHAR(20) NOT NULL,
    PASSWORD VARCHAR(50) NOT NULL,
    EMAIL_ADDRESS VARCHAR(50) NOT NULL,
    HASH_VALUE VARCHAR(50) NOT NULL,
    PRIMARY KEY (USERNAME,EMAIL_ADDRESS)
);

INSERT INTO login (USERNAME, PASSWORD, EMAIL_ADDRESS, HASH_VALUE) VALUES
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


CREATE TABLE customer_transaction (
  ACCOUNT_NUMBER INT NOT NULL,
  TRANSACTION_TYPE CHAR(1) NOT NULL,
  ACCOUNT_TYPE VARCHAR(50) NOT NULL,
  AMOUNT FLOAT NOT NULL DEFAULT '0.00',
  DATE_TIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ACCOUNT_NUMBER)
);
