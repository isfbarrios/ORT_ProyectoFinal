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


