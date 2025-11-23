SELECT * FROM Cart;

select * from sessions s;

select * from menu m;

select * from productcategory p;

select * from Cart;

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
