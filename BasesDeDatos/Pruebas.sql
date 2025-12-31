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

ALTER TABLE MenuItem
ADD COLUMN BasePrice DECIMAL(12,2) NOT NULL DEFAULT 0
AFTER Description;

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


CREATE USER 'backend'@'%' IDENTIFIED BY 'ortedu2025';
GRANT ALL PRIVILEGES ON proyectofinal.* TO 'backend'@'%';
FLUSH PRIVILEGES;

SELECT VERSION();

