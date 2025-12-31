/*==========================================================
=  DATABASE
==========================================================*/
/*CREATE DATABASE IF NOT EXISTS proyectofinal
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE ProyectoFinal;
*/
/*==========================================================
=  CONFIG
==========================================================*/
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/*==========================================================
=  1) USUARIOS, GRUPOS, SESIONES
==========================================================*/
CREATE TABLE UserState (
  UserStateId     INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  UNIQUE KEY ux_UserState_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE Users (
  UserId          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(100) NOT NULL,
  Surname         VARCHAR(100) NOT NULL,
  Mail            VARCHAR(150) NOT NULL,
  Username        VARCHAR(100) NOT NULL,
  Password        VARCHAR(255) NOT NULL,
  UserStateId     INT UNSIGNED NOT NULL,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LastUpdate      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_Users_UserState
    FOREIGN KEY (UserStateId) REFERENCES UserState(UserStateId),
  UNIQUE KEY ux_Users_Mail (Mail),
  UNIQUE KEY ux_Users_Username (Username)
) ENGINE=InnoDB;

CREATE TABLE GroupState (
  GroupStateId    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  UNIQUE KEY ux_GroupState_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE Groups (
  GroupId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(100) NOT NULL,
  GroupStateId    INT UNSIGNED NOT NULL,
  CONSTRAINT fk_Groups_GroupState
    FOREIGN KEY (GroupStateId) REFERENCES GroupState(GroupStateId),
  UNIQUE KEY ux_Groups_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE UserInGroup (
  UserId          INT UNSIGNED NOT NULL,
  GroupId         INT UNSIGNED NOT NULL,
  PRIMARY KEY (UserId, GroupId),
  CONSTRAINT fk_UserInGroup_User
    FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE,
  CONSTRAINT fk_UserInGroup_Group
    FOREIGN KEY (GroupId) REFERENCES Groups(GroupId) ON DELETE CASCADE
) ENGINE=InnoDB;

/* Sesiones: usamos CHAR(36) con UUID(). Si tu versión no soporta DEFAULT (UUID()),
   generá el UUID desde la app o usa un TRIGGER BEFORE INSERT. */
CREATE TABLE Sessions (
  SessionId       VARCHAR(50) PRIMARY KEY,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  KEY ix_Sessions_Created (CreatedDate)
) ENGINE=InnoDB;

/*==========================================================
=  2) CLIENTES, PREFERENCIAS, RESTRICCIONES
==========================================================*/
/*CREATE TABLE CustomerState (
  StateId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  Description     TEXT,
  UNIQUE KEY ux_CustomerState_Name (Name)
) ENGINE=InnoDB;*/

CREATE TABLE Customer (
  CustomerId      INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  SessionId       VARCHAR(50) NULL,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  StateId         INT UNSIGNED NOT NULL,
  LastUpdate      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_Customer_State
    FOREIGN KEY (StateId) REFERENCES CustomerState(StateId),
  CONSTRAINT fk_Customer_Session
    FOREIGN KEY (SessionId) REFERENCES Sessions(SessionId)
) ENGINE=InnoDB;

CREATE TABLE Preference (
  PreferenceId    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(100) NOT NULL,
  Description     TEXT,
  UNIQUE KEY ux_Preference_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE Restriction (
  RestrictionId   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(100) NOT NULL,
  Description     TEXT,
  UNIQUE KEY ux_Restriction_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE CustomerPreference (
  CustomerId      INT UNSIGNED NOT NULL,
  PreferenceId    INT UNSIGNED NOT NULL,
  PRIMARY KEY (CustomerId, PreferenceId),
  CONSTRAINT fk_CustomerPreference_Customer
    FOREIGN KEY (CustomerId) REFERENCES Customer(CustomerId) ON DELETE CASCADE,
  CONSTRAINT fk_CustomerPreference_Preference
    FOREIGN KEY (PreferenceId) REFERENCES Preference(PreferenceId)
) ENGINE=InnoDB;

CREATE TABLE CustomerRestriction (
  CustomerId      INT UNSIGNED NOT NULL,
  RestrictionId   INT UNSIGNED NOT NULL,
  PRIMARY KEY (CustomerId, RestrictionId),
  CONSTRAINT fk_CustomerRestriction_Customer
    FOREIGN KEY (CustomerId) REFERENCES Customer(CustomerId) ON DELETE CASCADE,
  CONSTRAINT fk_CustomerRestriction_Restriction
    FOREIGN KEY (RestrictionId) REFERENCES Restriction(RestrictionId)
) ENGINE=InnoDB;

/*==========================================================
=  3) MESAS Y DISPONIBILIDAD (histórico)
==========================================================*/
CREATE TABLE TableState (
  StateId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  Description     TEXT,
  UNIQUE KEY ux_TableState_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE `Table` (
  TableId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  Description     TEXT,
  ChairsAmount    INT UNSIGNED NOT NULL,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LastUpdate      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CHECK (ChairsAmount >= 0),
  UNIQUE KEY ux_Table_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE TableShift (
  TableId   INT UNSIGNED NOT NULL,
  ShiftId   INT UNSIGNED NOT NULL,
  StateId	INT UNSIGNED NOT NULL,
  OpenTime	VARCHAR(10) NOT NULL,
  CloseTime	VARCHAR(10) NOT null,
  PRIMARY KEY (TableId, ShiftId),
  CONSTRAINT fk_TableAvailability_Table
    FOREIGN KEY (TableId) REFERENCES `Table`(TableId) ON DELETE CASCADE,
  CONSTRAINT fk_TableAvailability_State
    FOREIGN KEY (StateId) REFERENCES TableState(StateId)
) ENGINE=InnoDB;

CREATE TABLE CustomerDirection (
  DirectionId   INT UNSIGNED NOT NULL,
  CustomerId   INT UNSIGNED NOT NULL,
  StreetName   VARCHAR(30) NOT NULL,
  DoorNumber    VARCHAR(10) NOT NULL,
  Phone     VARCHAR(15) NOT null,
  Comments  VARCHAR(50) NOT NULL,
  PRIMARY KEY (DirectionId),
  CONSTRAINT fk_CustomerDirection_Customer
    FOREIGN KEY (CustomerId) REFERENCES Customer(CustomerId) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE TableReservation (
  ReservationId     INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  TableId           INT UNSIGNED NOT NULL,
  ShiftId           INT UNSIGNED NOT NULL,
  CustomerName      VARCHAR(30) NOT NULL,
  ReservationDate   DATE NOT NULL,
  CreatedDate       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LastUpdate        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_TableReservation_TableShift FOREIGN KEY (TableId, ShiftId) REFERENCES TableShift(TableId, ShiftId) ON DELETE CASCADE,
  CONSTRAINT ux_TableReservation UNIQUE KEY (TableId, ReservationDate)
) ENGINE=InnoDB;

CREATE TABLE TableCustomer (
  TableId         INT UNSIGNED NOT NULL,
  CustomerId      INT UNSIGNED NOT NULL,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (TableId, CustomerId),
  CONSTRAINT fk_TableCustomer_Table
    FOREIGN KEY (TableId) REFERENCES `Table`(TableId) ON DELETE CASCADE,
  CONSTRAINT fk_TableCustomer_Customer
    FOREIGN KEY (CustomerId) REFERENCES Customer(CustomerId) ON DELETE CASCADE
) ENGINE=InnoDB;

/*==========================================================
=  4) MENÚ, ÍTEMS, VARIANTES, TIEMPOS
==========================================================*/
CREATE TABLE Menu (
  MenuId          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(120) NOT NULL,
  Description     TEXT,
  Date            DATE NOT NULL DEFAULT (CURRENT_DATE),
  LastUpdate      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE MenuItemType (
  TypeId          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(80) NOT NULL,
  Description     TEXT,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY ux_MenuItemType_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE MenuItemState (
  StateId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(80) NOT NULL,
  Description     TEXT,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY ux_MenuItemState_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE MenuItemVariant (
  VariantId       INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(100) NOT NULL,
  Description     TEXT,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY ux_MenuItemVariant_Name (Name)
) ENGINE=InnoDB;

/* Ítems de menú:
   Reemplazo PK compuesta por surrogate + UNIQUE(menu_id, name) para evitar duplicados por menú */
CREATE TABLE MenuItem (
  MenuItemId      INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  MenuId          INT UNSIGNED NOT NULL,
  Name            VARCHAR(150) NOT NULL,
  Description     TEXT,
  BasePrice DECIMAL(12,2) NOT NULL DEFAULT 0,
  TypeId          INT UNSIGNED NOT NULL,
  StateId         INT UNSIGNED NOT NULL,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LastUpdate      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_MenuItem_Menu
    FOREIGN KEY (MenuId) REFERENCES Menu(MenuId) ON DELETE RESTRICT,
  CONSTRAINT fk_MenuItem_Type
    FOREIGN KEY (TypeId) REFERENCES MenuItemType(TypeId),
  CONSTRAINT fk_MenuItem_State
    FOREIGN KEY (StateId) REFERENCES MenuItemState(StateId),
  UNIQUE KEY ux_MenuItem_Menu_Name (MenuId, Name)
) ENGINE=InnoDB;

/* N:M entre MenuItem y Variant, con posible delta de precio */
CREATE TABLE MenuItemHasVariant (
  MenuItemId      INT UNSIGNED NOT NULL,
  VariantId       INT UNSIGNED NOT NULL,
  PriceDelta      DECIMAL(12,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (MenuItemId, VariantId),
  CONSTRAINT fk_MIHasVar_MenuItem
    FOREIGN KEY (MenuItemId) REFERENCES MenuItem(MenuItemId) ON DELETE CASCADE,
  CONSTRAINT fk_MIHasVar_Variant
    FOREIGN KEY (VariantId) REFERENCES MenuItemVariant(VariantId)
) ENGINE=InnoDB;

/* Tiempo de preparación por ítem (si no depende de la versión de menú) */
CREATE TABLE MenuItemPreparationTime (
  MenuItemId      INT UNSIGNED PRIMARY KEY,
  Time            INT UNSIGNED NOT NULL,     -- minutos
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_MIprep_MenuItem
    FOREIGN KEY (MenuItemId) REFERENCES MenuItem(MenuItemId) ON DELETE CASCADE
) ENGINE=InnoDB;

/*==========================================================
=  5) INVENTARIO (opcional)
==========================================================*/
CREATE TABLE ProductCategory (
  CategoryId      INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(100) NOT NULL,
  UNIQUE KEY ux_ProductCategory_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE Products (
  ProductId       INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(150) NOT NULL,
  ExpirationDate  DATE NULL,
  Stock           DECIMAL(12,3) NOT NULL DEFAULT 0,
  IncomeDate      DATE NULL,
  CategoryId      INT UNSIGNED NULL,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_Products_Category
    FOREIGN KEY (CategoryId) REFERENCES ProductCategory(CategoryId),
  CHECK (Stock >= 0)
) ENGINE=InnoDB;

/*==========================================================
=  6) CARRITO
==========================================================*/
CREATE TABLE CartState (
  CartStateId     INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  Description     TEXT,
  UNIQUE KEY ux_CartState_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE Cart (
  CartId          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  TableId         INT UNSIGNED NULL,
  SessionId       VARCHAR(50) NOT NULL,
  Amount          DECIMAL(12,2) NOT NULL DEFAULT 0,
  Date            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CartStateId     INT UNSIGNED NOT NULL,
  DelayTime       INT UNSIGNED NOT NULL DEFAULT 0,
  LastUpdate      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_Cart_Table
    FOREIGN KEY (TableId) REFERENCES `Table`(TableId),
  CONSTRAINT fk_Cart_Session
    FOREIGN KEY (SessionId) REFERENCES Sessions(SessionId),
  CONSTRAINT fk_Cart_State
    FOREIGN KEY (CartStateId) REFERENCES CartState(CartStateId),
  CHECK (Amount >= 0),
  CHECK (DelayTime >= 0),
  KEY ix_Cart_Session (SessionId)
) ENGINE=InnoDB;

CREATE TABLE CartItem (
  CartId          INT UNSIGNED NOT NULL,
  ItemId          INT UNSIGNED NOT NULL,
  MenuItemId      INT UNSIGNED NOT NULL,
  Quantity        INT UNSIGNED NOT NULL,
  ItemAmount      DECIMAL(12,2) NOT NULL,
  DelayTime       INT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (CartId, ItemId),
  CONSTRAINT fk_CartItem_Cart
    FOREIGN KEY (CartId) REFERENCES Cart(CartId) ON DELETE CASCADE,
  CONSTRAINT fk_CartItem_MenuItem
    FOREIGN KEY (MenuItemId) REFERENCES MenuItem(MenuItemId),
  CHECK (Quantity > 0),
  CHECK (ItemAmount >= 0),
  CHECK (DelayTime >= 0)
) ENGINE=InnoDB;

/*==========================================================
=  7) PEDIDOS
==========================================================*/
CREATE TABLE PaymentType (
  PaymentTypeId   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  UNIQUE KEY ux_PaymentType_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE OrderState (
  StateId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  Description     TEXT,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY ux_OrderState_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE OrderCanal (
  CanalId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,  -- SALON / DELIVERY / TAKEAWAY
  Description     TEXT,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY ux_OrderCanal_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE `Order` (
  OrderId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  OrderNumber     VARCHAR(30) NOT NULL,
  Amount          DECIMAL(12,2) NOT NULL,
  Date            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  StateId         INT UNSIGNED NOT NULL,
  CanalId         INT UNSIGNED NOT NULL,
  PaymentTypeId   INT UNSIGNED NULL,
  Description     TEXT,
  LastUpdate      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  ExtraData       JSON NULL,
  CONSTRAINT fk_Order_State
    FOREIGN KEY (StateId) REFERENCES OrderState(StateId),
  CONSTRAINT fk_Order_Canal
    FOREIGN KEY (CanalId) REFERENCES OrderCanal(CanalId),
  CONSTRAINT fk_Order_Payment
    FOREIGN KEY (PaymentTypeId) REFERENCES PaymentType(PaymentTypeId),
  UNIQUE KEY ux_Order_OrderNumber (OrderNumber),
  CHECK (Amount >= 0),
  KEY ix_Order_Date (Date)
) ENGINE=InnoDB;

CREATE TABLE OrderItem (
  OrderId    INT UNSIGNED NOT NULL,
  CartId     INT UNSIGNED NOT NULL,
  ItemId     INT UNSIGNED NOT NULL,
  Quantity   INT UNSIGNED NOT NULL,
  ExtraData  JSON NULL,
  PRIMARY KEY (OrderId, ItemId),

  CONSTRAINT fk_OrderItem_Order
    FOREIGN KEY (OrderId)
    REFERENCES `Order`(OrderId)
    ON DELETE CASCADE,

  CONSTRAINT fk_OrderItem_CartItem
    FOREIGN KEY (CartId, ItemId)
    REFERENCES CartItem (CartId, ItemId),

  CHECK (Quantity > 0)
) ENGINE=InnoDB;

/*==========================================================
=  8) PROGRESO (COCINA)
==========================================================*/
CREATE TABLE ProgressSteps (
  StepId          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(80) NOT NULL,
  Description     TEXT,
  PreparationTime INT UNSIGNED NOT NULL DEFAULT 0,
  UNIQUE KEY ux_ProgressSteps_Name (Name),
  CHECK (PreparationTime >= 0)
) ENGINE=InnoDB;

/* OrderInProgress: histórico por paso alcanzado */
CREATE TABLE OrderInProgress (
  OrderId         INT UNSIGNED NOT NULL,
  StepId          INT UNSIGNED NOT NULL,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (OrderId, StepId),
  CONSTRAINT fk_OIP_Order
    FOREIGN KEY (OrderId) REFERENCES `Order`(OrderId) ON DELETE CASCADE,
  CONSTRAINT fk_OIP_Step
    FOREIGN KEY (StepId) REFERENCES ProgressSteps(StepId)
) ENGINE=InnoDB;

/*==========================================================
=  9) REPORTES
==========================================================*/
CREATE TABLE ReportState (
  StateId         INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(50) NOT NULL,
  UNIQUE KEY ux_ReportState_Name (Name)
) ENGINE=InnoDB;

CREATE TABLE Reports (
  ReportId        INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  Name            VARCHAR(120) NOT NULL,
  Description     TEXT,
  StateId         INT UNSIGNED NOT NULL,
  Query           TEXT NOT NULL,
  CreatedDate     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LastUpdate      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_Reports_State
    FOREIGN KEY (StateId) REFERENCES ReportState(StateId),
  UNIQUE KEY ux_Reports_Name (Name)
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;
