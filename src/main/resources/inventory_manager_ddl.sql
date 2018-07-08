-- 
-- Disable foreign keys
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

-- 
-- Set default database
--
USE inventory_manager;

--
-- Definition for table Supplier
--
DROP TABLE IF EXISTS Supplier;
CREATE TABLE Supplier (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  Name VARCHAR(255) NOT NULL,
  ContactNo VARCHAR(10) DEFAULT NULL,
  Email VARCHAR(50) DEFAULT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID)
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;


--
-- Definition for table Customer
--
DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  Name VARCHAR(255) NOT NULL,
  ContactNo VARCHAR(10) DEFAULT NULL,
  Email VARCHAR(50) DEFAULT NULL,
  Address VARCHAR(255) DEFAULT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID)
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;


--
-- Definition for table Item
--
DROP TABLE IF EXISTS Item;
CREATE TABLE Item (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  SupplierID INT(10) NOT NULL,
  Code VARCHAR(100) DEFAULT NULL,
  Name VARCHAR(255) NOT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Item_SupplierID FOREIGN KEY (SupplierID)
    REFERENCES Supplier(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;


--
-- Definition for table Item_Stock
--
DROP TABLE IF EXISTS Item_Stock;
CREATE TABLE Item_Stock (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  ItemID INT(10) NOT NULL,
  Location VARCHAR(20) NOT NULL,
  Quantity BIGINT NOT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Item_Stock_ItemID FOREIGN KEY (ItemID)
    REFERENCES Item(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;


--
-- Definition for table Good_Receive_Note
--
DROP TABLE IF EXISTS Good_Receive_Note;
CREATE TABLE Good_Receive_Note (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  SupplierID INT(10) NOT NULL,
  ReceiptNo VARCHAR(50) NOT NULL,
  PurchasedDate DATETIME NOT NULL,
  Location VARCHAR(20) NOT NULL,
  Remarks VARCHAR(255) DEFAULT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Good_Receive_Note_SupplierID FOREIGN KEY (SupplierID)
    REFERENCES Supplier(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table Good_Receive_Note_Line
--
DROP TABLE IF EXISTS Good_Receive_Note_Line;
CREATE TABLE Good_Receive_Note_Line (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  GoodReceiveNoteID INT(10) NOT NULL,
  ItemID INT(10) NOT NULL,
  Quantity BIGINT NOT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Good_Receive_Note_Line_GoodReceiveNoteID FOREIGN KEY (GoodReceiveNoteID)
    REFERENCES Good_Receive_Note(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table Sales_Invoice
--
DROP TABLE IF EXISTS Sales_Invoice;
CREATE TABLE Sales_Invoice (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  CustomerID INT(10) NOT NULL,
  ReceiptNo VARCHAR(50) NOT NULL,
  SoldDate DATETIME NOT NULL,
  Location VARCHAR(20) NOT NULL,
  Remarks VARCHAR(255) DEFAULT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Sales_Invoice_CustomerID FOREIGN KEY (CustomerID)
    REFERENCES Customer(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table Sales_Invoice_Line
--
DROP TABLE IF EXISTS Sales_Invoice_Line;
CREATE TABLE Sales_Invoice_Line (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  SalesInvoiceID INT(10) NOT NULL,
  ItemID INT(10) NOT NULL,
  Quantity BIGINT NOT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Sales_Invoice_Line_SalesInvoiceID FOREIGN KEY (SalesInvoiceID)
    REFERENCES Sales_Invoice(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table Transfer_Note
--
DROP TABLE IF EXISTS Transfer_Note;
CREATE TABLE Transfer_Note (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  TransferredDate DATETIME NOT NULL,
  FromLocation VARCHAR(20) NOT NULL,
  ToLocation VARCHAR(20) NOT NULL,
  Remarks VARCHAR(255) DEFAULT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID)
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table Transfer_Note_Line
--
DROP TABLE IF EXISTS Transfer_Note_Line;
CREATE TABLE Transfer_Note_Line (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  TransferNoteID INT(10) NOT NULL,
  ItemID INT(10) NOT NULL,
  Quantity BIGINT NOT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Transfer_Note_Line_TransferNoteID FOREIGN KEY (TransferNoteID)
    REFERENCES Transfer_Note(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;