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


CREATE TABLE OrderItem (
  OrderItemId INT UNSIGNED NOT NULL AUTO_INCREMENT,
  OrderId INT UNSIGNED NOT NULL,
  MenuItemId INT UNSIGNED NOT NULL,
  Quantity INT UNSIGNED NOT NULL,
  UnitPrice DECIMAL(12,2) NOT NULL,
  Notes TEXT DEFAULT NULL,
  PRIMARY KEY (OrderItemId),
  KEY ix_OrderItem_Order (OrderId),
  CONSTRAINT fk_OrderItem_Order
    FOREIGN KEY (OrderId) REFERENCES `Order` (OrderId),
  CONSTRAINT chk_OrderItem_Qty CHECK (Quantity > 0),
  CONSTRAINT chk_OrderItem_Price CHECK (UnitPrice >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

drop table if exists OrderItem;

ALTER TABLE OrderItem
DROP  COLUMN ExtraData;

ALTER TABLE OrderItem
ADD COLUMN Notes TEXT DEFAULT NULL,

select * from `Order` o;

select * from OrderItem;

select * from Cart c where c.CartId = 168 order by c.CartId desc;

select * from CartItem c where c.CartId = 168;

select * from CartState;

select * from Bill;

update Cart set CartStateId = 3 where CartId < 180;

select * from BillItem;

CREATE TABLE `Order` (
  OrderId int(10) unsigned NOT NULL AUTO_INCREMENT,
  CartId int(10) unsigned NOT NULL,
  OrderDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  OrderStateId int(10) unsigned NOT NULL,
  Notes TEXT DEFAULT NULL,
  LastUpdate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (OrderId),
  KEY ix_Order_Cart (CartId),
  KEY ix_Order_State (OrderStateId),
  CONSTRAINT fk_Order_Cart
    FOREIGN KEY (CartId) REFERENCES Cart (CartId)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




