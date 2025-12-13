-- V3: Convertir TableReservation de CustomerId a CustomerName
-- ADVERTENCIA: Ajusta los nombres de constraints si tu esquema difiere.

-- 1) Si existe foreign key hacia Customer, elimínala (reemplaza el nombre si es distinto)
ALTER TABLE TableReservation
  DROP FOREIGN KEY fk_TableReservation_Customer;

-- 2) Eliminar la columna CustomerId
ALTER TABLE TableReservation
  DROP COLUMN CustomerId;

-- 3) Añadir la nueva columna CustomerName (no nula)
ALTER TABLE TableReservation
  ADD COLUMN CustomerName VARCHAR(30) NOT NULL AFTER ShiftId;

-- 4) Añadir constraint único para evitar más de una reserva por mesa+fecha
ALTER TABLE TableReservation
  ADD CONSTRAINT uq_table_reservation_tableid_date UNIQUE (TableId, ReservationDate);

-- 5) Índice para optimizar conteos por fecha
CREATE INDEX idx_table_reservation_date_table ON TableReservation (ReservationDate, TableId);

-- NOTA: Si la tabla ya tenía datos y necesitas migrar nombres desde otra tabla, agrega las sentencias UPDATE necesarias
--       o realiza la migración en dos pasos (añadir columna nullable, poblarla, luego set NOT NULL).
