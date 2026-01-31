INSERT INTO UserState (name) VALUES('Activo');

INSERT INTO `Users` VALUES
(1,'Gastón','Szucs','gaston@resto.com',NULL,'gaston','1234',1,'2025-12-16 00:23:54','2025-12-16 00:23:54'),
(2,'Fabricio','Barrios','fabricio@resto.com',NULL,'fabricio','1234',1,'2025-12-16 00:23:54','2025-12-16 00:23:54'),
(3,'Mauro','Castro','mauro@resto.com',NULL,'mcastro','1234',1,'2025-12-16 00:23:54','2025-12-16 00:23:54'),
(5,'Fabricio','Barrios','fbarrios@hotmail.com','5E4D6C5524293A706FFDF07D0D582084','fbarrios','$2a$10$2QLXESSRkLo/RwWzVnFCpukxWF1uJXpuQVHYgU.9A1WunUWRMaeJG',1,'2025-12-04 21:20:54','2026-01-19 21:25:39'),
(6,'Gaston','Szucs','gszucs@hotmail.com',NULL,'gszucs','$2a$10$2QLXESSRkLo/RwWzVnFCpukxWF1uJXpuQVHYgU.9A1WunUWRMaeJG',1,'2026-01-17 05:36:16','2026-01-17 05:39:51');

INSERT INTO UserState (Name) VALUES ('Inactivo');

INSERT INTO GroupState (Name) VALUES ('Archivado');

INSERT INTO TableState (Name, Description) VALUES
('Libre', 'Mesa disponible'),
('Ocupada', 'Actualmente en uso'),
('Reservada', 'Mesa reservada por cliente');

INSERT INTO MenuItemType (Name, Description) VALUES
('Entrada', 'Platos iniciales'),
('Principal', 'Platos principales'),
('Postre', 'Postres dulces'),
('Bebida', 'Bebidas frías o calientes');

INSERT INTO MenuItemState (Name, Description) VALUES
('Disponible', 'Visible en el menú'),
('No disponible', 'Fuera de stock temporalmente');

INSERT INTO MenuItemVariant (Name, Description) VALUES
('Grande', 'Porción grande'),
('Mediano', 'Porción estándar'),
('Chico', 'Porción pequeña');

INSERT INTO CartState (Name, Description) VALUES
('Abierto', 'Carrito activo del cliente'),
('Cerrado', 'Carrito confirmado'),
('Cancelado', 'Carrito anulado');

INSERT INTO PaymentType (Name) VALUES
('Efectivo'), ('Tarjeta'), ('MercadoPago');

INSERT INTO OrderState (Name, Description) VALUES
('Pendiente'),
('Preparando'),
('Listo'),
('Completado'),
('Cancelado');

INSERT INTO ProgressSteps (Name, Description, PreparationTime) VALUES
('Recepción', 'Pedido recibido por sistema', 0),
('Preparación', 'Elaboración en cocina', 15),
('Finalizado', 'Listo para entregar', 0);

INSERT INTO ReportState (Name) VALUES ('Activo'), ('Inactivo');

INSERT INTO Groups (Name, GroupStateId)
VALUES 
('Administradores', 1),
('Mozos', 1),
('Cocina', 1);

select * from `Table` t;

INSERT INTO `Table` (Name, Description, ChairsAmount)
VALUES 
('Mesa 1', 'Cerca de la ventana', 4),
('Mesa 2', 'Patio interno', 2),
('Mesa 3', 'Sector principal', 6);

INSERT INTO `TableState` VALUES
(1,'Libre','Mesa disponible'),
(2,'Ocupada','Actualmente en uso'),
(3,'Reservada','Mesa reservada por cliente');

INSERT INTO `TableShift` VALUES
(1,1,1,'19','20'),
(1,2,2,'20','21'),
(1,3,1,'21','22'),
(1,4,1,'22','23'),
(1,5,1,'23','24'),
(2,1,1,'19','20'),
(2,2,2,'20','21'),
(2,3,2,'21','22'),
(2,4,1,'22','23'),
(2,5,1,'23','24'),
(3,1,1,'19','20'),
(3,2,2,'20','21'),
(3,3,1,'21','22'),
(3,4,1,'22','23'),
(3,5,1,'23','24');

INSERT INTO Preference (Name, Description) VALUES
('Vegetariano', 'Prefiere comida sin carne'),
('Sin gluten', 'Evita gluten'),
('Bajo sodio', 'Evita comidas saladas');

select * from Menu;

INSERT INTO Menu (Name, Description)
VALUES ('Menú principal', 'Carta general del restaurante');

select * from MenuItemType;

INSERT INTO `MenuItemType` VALUES
(1,'Entrada','Platos iniciales','2025-12-16 00:23:54'),
(2,'Principal','Platos principales','2025-12-16 00:23:54'),
(3,'Postre','Postres dulces','2025-12-16 00:23:54'),
(4,'Bebida','Bebidas frías o calientes','2025-12-16 00:23:54');

INSERT INTO MenuItem (MenuId, Name, Description,BasePrice, TypeId, StateId)
VALUES 
(1, 'Milanesa con papas fritas', 'Plato clásico', 450.00, 2, 1),
(1, 'Ensalada César', 'Lechuga, pollo, crutones y aderezo', 380.00, 1, 1),
(1, 'Flan casero', 'Con dulce de leche', 220.00, 3, 1),
(1, 'Refresco 500ml', 'Coca-Cola o similar', 120.00, 4, 1);

INSERT INTO MenuItemHasVariant (MenuItemId, VariantId, PriceDelta) VALUES
(1,1,150), (2,2,0), (3,3,-20), (4,2,0);

INSERT INTO MenuItemPreparationTime (MenuItemId, Time)
VALUES (1,20), (2,10), (3,5), (4,0);

INSERT INTO Restriction (Name, Description) VALUES
('Celiaco', 'No puede consumir gluten'),
('Alergia a mariscos', 'Evita frutos de mar');

INSERT INTO CustomerPreference (CustomerId, PreferenceId) VALUES (1,1), (2,2);
INSERT INTO CustomerRestriction (CustomerId, RestrictionId) VALUES (2,1);

ALTER TABLE MenuItem
ADD COLUMN BasePrice DECIMAL(12,2) NOT NULL DEFAULT 0
AFTER Description;




