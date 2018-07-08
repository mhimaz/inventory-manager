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
-- Definition for table Item_Price
--
DROP TABLE IF EXISTS Item_Price;
CREATE TABLE Item_Price (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  SellingPrice DECIMAL(10,2) NOT NULL,
  BuyingPrice DECIMAL(10,2) DEFAULT NULL,
  CreatedDate DATETIME NOT NULL,
  PriceType VARCHAR(10),
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID)
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Alter table Item with Price
--
ALTER TABLE Item ADD PriceID INT(10) NULL AFTER Name;
ALTER TABLE Item ADD CONSTRAINT FK_Item_PriceID FOREIGN KEY (PriceID) REFERENCES Item_Price(ID) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Alter table Good_Receive_Note_Line
--
ALTER TABLE Good_Receive_Note_Line ADD DiscountMode VARCHAR(12) NULL AFTER Quantity;
ALTER TABLE Good_Receive_Note_Line ADD DiscountValue DECIMAL(10,2) NULL AFTER DiscountMode;
ALTER TABLE Good_Receive_Note_Line ADD GrandTotal DECIMAL(10,2) NOT NULL AFTER DiscountValue;
ALTER TABLE Good_Receive_Note_Line ADD DiscountTotal DECIMAL(10,2) NULL AFTER GrandTotal;
ALTER TABLE Good_Receive_Note_Line ADD NetTotal DECIMAL(10,2) NOT NULL AFTER DiscountTotal;

--
-- Alter table Good_Receive_Note
--
ALTER TABLE Good_Receive_Note ADD GrandTotal DECIMAL(10,2) NOT NULL AFTER Location;
ALTER TABLE Good_Receive_Note ADD DiscountTotal DECIMAL(10,2) NULL AFTER GrandTotal;
ALTER TABLE Good_Receive_Note ADD NetTotal DECIMAL(10,2) NOT NULL AFTER DiscountTotal;
ALTER TABLE Good_Receive_Note ADD IsReturn BINARY(1) NOT NULL default FALSE AFTER Remarks;

--
-- Alter table Sales_Invoice_Line
--
ALTER TABLE Sales_Invoice_Line ADD DiscountMode VARCHAR(12) NULL AFTER Quantity;
ALTER TABLE Sales_Invoice_Line ADD DiscountValue DECIMAL(10,2) NULL AFTER DiscountMode;
ALTER TABLE Sales_Invoice_Line ADD GrandTotal DECIMAL(10,2) NOT NULL AFTER DiscountValue;
ALTER TABLE Sales_Invoice_Line ADD DiscountTotal DECIMAL(10,2) NULL AFTER GrandTotal;
ALTER TABLE Sales_Invoice_Line ADD NetTotal DECIMAL(10,2) NOT NULL AFTER DiscountTotal;

--
-- Alter table Sales_Invoice
--
ALTER TABLE Sales_Invoice ADD GrandTotal DECIMAL(10,2) NOT NULL AFTER Location;
ALTER TABLE Sales_Invoice ADD DiscountTotal DECIMAL(10,2) NULL AFTER GrandTotal;
ALTER TABLE Sales_Invoice ADD NetTotal DECIMAL(10,2) NOT NULL AFTER DiscountTotal;
ALTER TABLE Sales_Invoice ADD IsReturn BINARY(1) NOT NULL default FALSE AFTER Remarks;


--
-- Definition for table Stock_Adjustment
--
DROP TABLE IF EXISTS Stock_Adjustment;
CREATE TABLE Stock_Adjustment (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  AdjustedDate DATETIME NOT NULL,
  Location VARCHAR(20) NOT NULL,
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
-- Definition for table Stock_Adjustment_Line
--
DROP TABLE IF EXISTS Stock_Adjustment_Line;
CREATE TABLE Stock_Adjustment_Line (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  StockAdjustmentID INT(10) NOT NULL,
  ItemID INT(10) NOT NULL,
  Quantity BIGINT NOT NULL,
  CreatedDate DATETIME NOT NULL,
  LastModifiedDate DATETIME NOT NULL,
  IsDeleted BINARY(1) NOT NULL,
  PRIMARY KEY (ID),
  CONSTRAINT FK_Stock_Adjustment_Line_StockAdjustmentID FOREIGN KEY (StockAdjustmentID)
    REFERENCES Stock_Adjustment(ID) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;


--
-- Definition for table Location
--
DROP TABLE IF EXISTS Location;
CREATE TABLE Location (
  ID INT(10) NOT NULL AUTO_INCREMENT,
  Name VARCHAR(255) NOT NULL,
  ContactNo VARCHAR(10) DEFAULT NULL,
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
-- Adding existing locations
--
INSERT INTO Location
(`ID`, `Name`, `ContactNo`, `Address`, `CreatedDate`, `LastModifiedDate`, `IsDeleted`) VALUES
(1, 'Warehouse', null, null, NOW(), NOW(), false),
(2, 'Transport Truck', null, null, NOW(), NOW(), false);


ALTER TABLE Item_Stock ADD LocationID INT(10) NOT NULL AFTER Location;
ALTER TABLE Sales_Invoice ADD LocationID INT(10) NOT NULL AFTER Location;
ALTER TABLE Good_Receive_Note ADD LocationID INT(10) NOT NULL AFTER Location;
ALTER TABLE Stock_Adjustment ADD LocationID INT(10) NOT NULL AFTER Location;
ALTER TABLE Transfer_Note ADD FromLocationID INT(10) NOT NULL AFTER FromLocation;
ALTER TABLE Transfer_Note ADD ToLocationID INT(10) NOT NULL AFTER ToLocation;


SET SQL_SAFE_UPDATES = 0;
UPDATE Item_Stock i 
SET i.LocationID = 1 
WHERE i.Location = 'WAREHOUSE';

UPDATE Item_Stock i 
SET i.LocationID = 2 
WHERE i.Location = 'TRANSPORT_TRUCK';

UPDATE Sales_Invoice i 
SET i.LocationID = 1 
WHERE i.Location = 'WAREHOUSE';

UPDATE Sales_Invoice i 
SET i.LocationID = 2 
WHERE i.Location = 'TRANSPORT_TRUCK';

UPDATE Good_Receive_Note i 
SET i.LocationID = 1 
WHERE i.Location = 'WAREHOUSE';

UPDATE Good_Receive_Note i 
SET i.LocationID = 2 
WHERE i.Location = 'TRANSPORT_TRUCK';

UPDATE Stock_Adjustment i 
SET i.LocationID = 1 
WHERE i.Location = 'WAREHOUSE';

UPDATE Stock_Adjustment i 
SET i.LocationID = 2 
WHERE i.Location = 'TRANSPORT_TRUCK';

UPDATE Transfer_Note i 
SET i.FromLocationID = 1 
WHERE i.FromLocation = 'WAREHOUSE';

UPDATE Transfer_Note i 
SET i.FromLocationID = 2 
WHERE i.FromLocation = 'TRANSPORT_TRUCK';

UPDATE Transfer_Note i 
SET i.ToLocationID = 1 
WHERE i.ToLocation = 'WAREHOUSE';

UPDATE Transfer_Note i 
SET i.ToLocationID = 2 
WHERE i.ToLocation = 'TRANSPORT_TRUCK';
SET SQL_SAFE_UPDATES = 1;


ALTER TABLE Item_Stock ADD CONSTRAINT FK_Item_Stock_LocationID FOREIGN KEY (LocationID) REFERENCES Location(ID) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE Sales_Invoice ADD CONSTRAINT FK_Sales_Invoice_LocationID FOREIGN KEY (LocationID) REFERENCES Location(ID) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE Good_Receive_Note ADD CONSTRAINT FK_Good_Receive_Note_LocationID FOREIGN KEY (LocationID) REFERENCES Location(ID) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE Stock_Adjustment ADD CONSTRAINT FK_Stock_Adjustment_LocationID FOREIGN KEY (LocationID) REFERENCES Location(ID) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE Transfer_Note ADD CONSTRAINT FK_Transfer_Note_FromLocationID FOREIGN KEY (FromLocationID) REFERENCES Location(ID) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE Transfer_Note ADD CONSTRAINT FK_Transfer_Note_ToLocationID FOREIGN KEY (ToLocationID) REFERENCES Location(ID) ON DELETE RESTRICT ON UPDATE RESTRICT;


ALTER TABLE Item_Stock DROP Location;
ALTER TABLE Sales_Invoice DROP Location;
ALTER TABLE Good_Receive_Note DROP Location;
ALTER TABLE Stock_Adjustment DROP Location;
ALTER TABLE Transfer_Note DROP FromLocation;
ALTER TABLE Transfer_Note DROP ToLocation;