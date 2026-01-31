SELECT * FROM Cartstate;

select * from cart s order by s.CartId desc;

select * from cartitem s order by s.CartId desc;

select * from tableavailability t;

select * from TableReservation;

select * from `Order` t;

select * from Cart c;

SELECT *
FROM tableavailability
WHERE DATE(ReservedTimestamp) = '2025-11-30';

select * from CustomerDirection p;

select * from Menu s;

select * from TableShift s;

select * from sessions m order by m.CreatedDate desc;

select * from cart c order by c.CartId desc;

INSERT INTO Users
(UserId, Name, Surname, Mail, Username, Password, UserStateId, CreatedDate, LastUpdate)
VALUES(5, 'Fabricio', 'Barrios', 'fbarrios@hotmail.com', 'fbarrios', '$2a$10$2QLXESSRkLo/RwWzVnFCpukxWF1uJXpuQVHYgU.9A1WunUWRMaeJG', 1, '2025-12-04 21:20:54.000', '2025-12-04 21:20:54.000');

delete from Users where UserName = 'fbarrios@hotmail.com';

ALTER TABLE Users
ADD COLUMN SessionId VARCHAR(50)
AFTER Mail;

ALTER TABLE Users
DROP COLUMN SessionId;

UPDATE MenuItem
SET BasePrice = 450.00
WHERE MenuId = 1 AND Name = 'Milanesa con papas fritas';

UPDATE MenuItem
SET BasePrice = 380.00
WHERE MenuId = 1 AND Name = 'Ensalada César';

UPDATE MenuItem
SET BasePrice = 220.00
WHERE MenuId = 1 AND Name = 'Flan casero';

UPDATE MenuItem
SET BasePrice = 120.00
WHERE MenuId = 1 AND Name = 'Refresco 500ml';

select * from `Order` u;

select * from Users u;

select * from UserDirection oi;

select count(*) from Cart oi;

select * from Cart c order by c.CartId desc;


ALTER TABLE Cart
DROP FOREIGN KEY fk_Cart_Session;

ALTER TABLE Cart
DROP COLUMN SessionId;


ALTER TABLE Cart
ADD COLUMN UserName VARCHAR(50) NOT NULL AFTER TableId;


ALTER TABLE Cart
ADD INDEX ix_Cart_UserName (UserName);


ALTER TABLE `Order`
DROP FOREIGN KEY `fk_Order_Canal`;

ALTER TABLE `Order`
DROP INDEX `fk_Order_Canal`;


ALTER TABLE `Order`
DROP COLUMN `CanalId`;

ALTER TABLE `Order`
ADD COLUMN `Rol` VARCHAR(30) NOT NULL AFTER `StateId`;



select m.Name, c.*
from CartItem c 
join MenuItem m on (m.MenuItemId = c.MenuItemId)
where c.CartId = 152;

select * from CartState c;

select * from `Order` o;

select * from Sessions s;

delete from Sessions where UserId = 0;

select * from UserDirection u;

drop table if exists OrderItem;

INSERT INTO OrderItem (OrderId,CartId,ItemId,Quantity,ExtraData) VALUES
	 (2,1,1,1,NULL),
	 (3,3,3,1,NULL);

Carrito

CREATE TABLE `Order` (
  KitchenOrderId INT UNSIGNED NOT NULL AUTO_INCREMENT,
  CartId INT UNSIGNED NOT NULL,
  OrderDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KitchenOrderStateId INT UNSIGNED NOT NULL,
  Notes TEXT DEFAULT NULL,
  LastUpdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (KitchenOrderId),
  KEY ix_KitchenOrder_Cart (CartId),
  KEY ix_KitchenOrder_State (KitchenOrderStateId),
  CONSTRAINT fk_KitchenOrder_Cart
    FOREIGN KEY (CartId) REFERENCES Cart (CartId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



Bill

CREATE TABLE Bill (
  `BillId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `BillNumber` varchar(30) NOT NULL,
  `Amount` decimal(12,2) NOT NULL,
  `Date` datetime NOT NULL DEFAULT current_timestamp(),
  `PaymentTypeId` int(10) unsigned DEFAULT NULL,
  `ExtraData` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`ExtraData`)),
  PRIMARY KEY (`BillId`),
  UNIQUE KEY `ux_Bill_BillNumber` (`BillNumber`),
  KEY `fk_Bill_Payment` (`PaymentTypeId`),
  KEY `ix_Bill_Date` (`Date`),
  CONSTRAINT `fk_Bill_Payment` FOREIGN KEY (`PaymentTypeId`) REFERENCES `PaymentType` (`PaymentTypeId`),
  CONSTRAINT `CONSTRAINT_1` CHECK (`Amount` >= 0)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;


CREATE TABLE `BillItem` (
  `BillId` int(10) unsigned NOT NULL,
  `CartId` int(10) unsigned NOT NULL,
  `ItemId` int(10) unsigned NOT NULL,
  `Quantity` int(10) unsigned NOT NULL,
  `ExtraData` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`ExtraData`)),
  PRIMARY KEY (`BillId`,`ItemId`),
  KEY `fk_BillItem_CartItem` (`CartId`,`ItemId`),
  CONSTRAINT `fk_BillItem_CartItem` FOREIGN KEY (`CartId`, `ItemId`) REFERENCES `CartItem` (`CartId`, `ItemId`),
  CONSTRAINT `fk_BillItem_Bill` FOREIGN KEY (`BillId`) REFERENCES `Bill` (`BillId`) ON DELETE CASCADE,
  CONSTRAINT `CONSTRAINT_1` CHECK (`Quantity` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


select * from OrderState;

INSERT INTO OrderState (OrderStateId, Name) VALUES
(1, 'Pendiente'),
(2, 'Preparando'),
(3, 'Listo'),
(4, 'Completado'),
(5, 'Cancelado');


CREATE TABLE OrderState (
  OrderStateId INT UNSIGNED NOT NULL,
  Name VARCHAR(30) NOT NULL,
  PRIMARY KEY (OrderStateId),
  UNIQUE KEY ux_OrderState_Name (Name)
);



CREATE TABLE OrderItem (
  OrderItemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
  OrderId INT UNSIGNED NOT NULL,
  ProductId INT UNSIGNED NOT NULL,
  Quantity INT UNSIGNED NOT NULL,
  UnitPrice DECIMAL(12,2) NOT NULL,
  Notes TEXT DEFAULT NULL,
  PRIMARY KEY (OrderItemId),
  KEY ix_OrderItem_Order (OrderId),
  CONSTRAINT fk_OrderItem_Order
    FOREIGN KEY (OrderId) REFERENCES Order (OrderId),
  CONSTRAINT chk_OrderItem_Qty CHECK (Quantity > 0),
  CONSTRAINT chk_OrderItem_Price CHECK (UnitPrice >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



select * from  `Order`;

ALTER TABLE `Order`
DROP FOREIGN KEY `fk_Order_State`;

ALTER TABLE `Order`
DROP INDEX `fk_Order_State`;

ALTER TABLE `Order`
DROP COLUMN `StateId`;

DROP TABLE OrderState;

ALTER TABLE `Order`
DROP FOREIGN KEY `fk_Order_Canal`;

ALTER TABLE `Order`
DROP INDEX `fk_Order_Canal`;


ALTER TABLE `Order`
DROP COLUMN `CanalId`;

drop table if exists OrderInProgress;




