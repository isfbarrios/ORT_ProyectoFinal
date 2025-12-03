SELECT * FROM Cartstate;

select * from cart s order by s.CartId desc;

select * from cartitem s order by s.CartId desc;

select * from tableavailability t;

delete from tableavailability where TableId = 1;

select * from tablestate t;

SELECT *
FROM tableavailability
WHERE DATE(ReservedTimestamp) = '2025-11-30';

select * from productcategory p;

select * from menu s;

select * from `order`;

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
