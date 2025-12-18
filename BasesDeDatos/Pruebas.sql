SELECT * FROM Cartstate;

select * from cart s order by s.CartId desc;

select * from cartitem s order by s.CartId desc;

select * from tableavailability t;

drop table if exists tablereservation;

select * from tablestate t;

SELECT *
FROM tableavailability
WHERE DATE(ReservedTimestamp) = '2025-11-30';

select * from productcategory p;

select * from tablereservation s;

select * from sessions m order by m.CreatedDate desc;

select * from cart c order by c.CartId desc;

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

ALTER TABLE Cart DROP FOREIGN KEY fk_Cart_Session;
ALTER TABLE Customer DROP FOREIGN KEY fk_Customer_Session;


ALTER TABLE Sessions
MODIFY SessionId VARCHAR(100)
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
NOT NULL;

ALTER TABLE Cart
MODIFY SessionId VARCHAR(100)
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
NOT NULL;

ALTER TABLE Customer
MODIFY SessionId VARCHAR(100)
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci
NULL;


ALTER TABLE Cart
ADD CONSTRAINT fk_Cart_Session
FOREIGN KEY (SessionId)
REFERENCES Sessions(SessionId);

ALTER TABLE Customer
ADD CONSTRAINT fk_Customer_Session
FOREIGN KEY (SessionId)
REFERENCES Sessions(SessionId);



SELECT VERSION();

