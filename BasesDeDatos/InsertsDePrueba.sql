INSERT INTO userstate (name) VALUES('Activo');

INSERT INTO proyectofinal.users
(name, surname, mail, username, Password, UserStateId, CreatedDate, LastUpdate)
values
('Usuario', 'Administrador', 'fabribarrios@hotmail.com', 'admin', 'pass1234', 1, current_timestamp(), current_timestamp());

INSERT INTO UserState (Name) VALUES ('Inactivo');

INSERT INTO GroupState (Name) VALUES ('Archivado');

INSERT INTO CustomerState (Name, Description) VALUES 
('Nuevo', 'Cliente que aún no realizó pedidos'),
('Frecuente', 'Cliente habitual con varias compras'),
('Inhabilitado', 'Cliente bloqueado por mal comportamiento');

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
('Pendiente', 'Pedido recibido, en espera de confirmación'),
('Preparando', 'En cocina'),
('Listo', 'Listo para entregar'),
('Completado', 'Finalizado'),
('Cancelado', 'Pedido cancelado');

INSERT INTO OrderCanal (Name, Description) VALUES
('Salón', 'Pedido realizado en mesa'),
('Delivery', 'Pedido enviado a domicilio'),
('TakeAway', 'Pedido retirado en local');

INSERT INTO ProgressSteps (Name, Description, PreparationTime) VALUES
('Recepción', 'Pedido recibido por sistema', 0),
('Preparación', 'Elaboración en cocina', 15),
('Finalizado', 'Listo para entregar', 0);

INSERT INTO ReportState (Name) VALUES ('Activo'), ('Inactivo');

INSERT INTO Users (Name, Surname, Mail, Username, Password, UserStateId)
VALUES 
('Gastón', 'Szucs', 'gaston@resto.com', 'gaston', '1234', 1),
('Fabricio', 'Barrios', 'fabricio@resto.com', 'fabricio', '1234', 1),
('Mauro', 'Castro', 'mauro@resto.com', 'mcastro', '1234', 1);

INSERT INTO Groups (Name, GroupStateId)
VALUES 
('Administradores', 1),
('Mozos', 1),
('Cocina', 1);

INSERT INTO UserInGroup (UserId, GroupId) VALUES
(1,1), (2,2), (3,3);

INSERT INTO `Table` (Name, Description, ChairsAmount)
VALUES 
('Mesa 1', 'Cerca de la ventana', 4),
('Mesa 2', 'Patio interno', 2),
('Mesa 3', 'Sector principal', 6);

INSERT INTO TableAvailability (TableId, StateId, ReservedTimestamp)
VALUES 
(1,1,NOW()),
(2,2,NOW()),
(3,3,NOW());

-- Sesiones simuladas
INSERT INTO Sessions (SessionId) VALUES (UUID()), (UUID()), (UUID());

INSERT INTO Customer (SessionId, StateId)
VALUES 
((SELECT SessionId FROM Sessions LIMIT 1), 1),
((SELECT SessionId FROM Sessions LIMIT 2,1), 2);

INSERT INTO Preference (Name, Description) VALUES
('Vegetariano', 'Prefiere comida sin carne'),
('Sin gluten', 'Evita gluten'),
('Bajo sodio', 'Evita comidas saladas');

INSERT INTO Menu (Name, Description)
VALUES ('Menú principal', 'Carta general del restaurante');

INSERT INTO MenuItem (MenuId, Name, Description, TypeId, StateId)
VALUES 
(1, 'Milanesa con papas fritas', 'Plato clásico', 2, 1),
(1, 'Ensalada César', 'Lechuga, pollo, crutones y aderezo', 1, 1),
(1, 'Flan casero', 'Con dulce de leche', 3, 1),
(1, 'Refresco 500ml', 'Coca-Cola o similar', 4, 1);

INSERT INTO MenuItemHasVariant (MenuItemId, VariantId, PriceDelta) VALUES
(1,1,150), (2,2,0), (3,3,-20), (4,2,0);

INSERT INTO MenuItemPreparationTime (MenuItemId, Time)
VALUES (1,20), (2,10), (3,5), (4,0);

INSERT INTO Restriction (Name, Description) VALUES
('Celiaco', 'No puede consumir gluten'),
('Alergia a mariscos', 'Evita frutos de mar');

INSERT INTO CustomerPreference (CustomerId, PreferenceId) VALUES (1,1), (2,2);
INSERT INTO CustomerRestriction (CustomerId, RestrictionId) VALUES (2,1);

-- Un carrito abierto de ejemplo
INSERT INTO Cart (TableId, SessionId, Amount, CartStateId)
VALUES (1, (SELECT SessionId FROM Sessions LIMIT 1), 850.00, 1);

INSERT INTO CartItem (CartId, ItemId, MenuItemId, Quantity, ItemAmount)
VALUES 
(1,1,1,1,350.00),
(1,2,4,1,150.00),
(1,3,3,2,350.00);

-- Pedido generado desde ese carrito
INSERT INTO `Order` (OrderNumber, Amount, StateId, CanalId, PaymentTypeId, Description)
VALUES ('ORD001', 850.00, 1, 1, 2, 'Pedido desde mesa 1');

INSERT INTO OrderItem (OrderId, ItemId, MenuItemId, Quantity)
VALUES (1,1,1,1), (1,2,3,2), (1,3,4,1);

INSERT INTO OrderInProgress (OrderId, StepId)
VALUES (1,1), (1,2);

INSERT INTO Reports (Name, Description, StateId, Query)
VALUES 
('Ventas Diarias', 'Informe de ventas por día', 1, 'SELECT DATE(Date), SUM(Amount) FROM Order GROUP BY DATE(Date);'),
('Pedidos Cancelados', 'Pedidos anulados por cliente', 1, 'SELECT * FROM Order WHERE StateId = 5;');