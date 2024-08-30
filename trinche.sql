<<<<<<< HEAD
CREATE TABLE clientes_PTC( 
    id_cliente NUMBER PRIMARY KEY, 
    nombre_clie VARCHAR2(20), 
    telefono_clie VARCHAR2(20), 
    correoElectronico VARCHAR2(100) NOT NULL UNIQUE,
    contrasena VARCHAR2(255),
    direccion_entrega VARCHAR2(300),
    imagen_Clientes VARCHAR2(250)
=======
create table clientes_PTC( 
id_cliente NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, 
nombre_clie varchar2(20), 
telefono_clie varchar2(20), 
correoElectronico VARCHAR2(100) NOT NULL UNIQUE,
contrasena VARCHAR2(255),
direccion_entrega VARCHAR2(300),
imagen_Clientes varchar2(250)
>>>>>>> 33784e090d270aff5923600d09c95556d59d8ecb
);
 
CREATE TABLE Empleados_PTC( 
    id_empleado NUMBER PRIMARY KEY, 
    nom_empleado VARCHAR2(20), 
    ape_empleado VARCHAR2(20), 
    usu_empleado VARCHAR2(15), 
    contrasena VARCHAR2(128) 
);
 
 
CREATE TABLE Menus_PTC( 
    id_menu NUMBER PRIMARY KEY, 
    categoria VARCHAR2(20),
    imagen_categoria VARCHAR2(250)
);
 
 
CREATE TABLE Permisos_PTC( 
    id_permiso NUMBER PRIMARY KEY, 
    permiso NUMBER, 
    motivopermiso CHAR(15) 
);
 
CREATE TABLE MovsInventario_PTC( 
    id_movinv NUMBER PRIMARY KEY, 
    mov_no NUMBER, 
    fechahoramov DATE, 
    concepto VARCHAR2(200), 
    entrada NUMBER(1), 
    salida NUMBER(1) 
);
 
 
CREATE TABLE Detalle_Productos_PTC( 
    id_producto NUMBER PRIMARY KEY, 
    id_menu NUMBER,
    producto VARCHAR2(20), 
    descripcion VARCHAR2(250),
    precioventa NUMBER, 
    stock INT,
    imagen_comida VARCHAR2(250),
    CONSTRAINT FK_categoria FOREIGN KEY (id_menu) REFERENCES Menus_PTC(id_menu)
);
 
CREATE TABLE PEDIDOS_PTC( 
    id_pedido NUMBER PRIMARY KEY, 
    id_cliente NUMBER, 
    id_empleado NUMBER, 
    fecha_hora_pedido DATE, 
    total_pedido NUMBER(7,2), 
    Estado VARCHAR(20) CHECK (Estado IN ('enviado', 'Entregado','Anulado','En proceso')), 
    Tipo_Pago VARCHAR(20) CHECK (Tipo_Pago IN ('Tarjeta', 'Efectivo')), 
    Tipo_Pedido VARCHAR(20) CHECK (Tipo_Pedido IN ('Comer aqui', 'Para llevar','Delivery')), 
    CONSTRAINT FK_id_cliente_cliente1 FOREIGN KEY (id_cliente) REFERENCES clientes_PTC(id_cliente), 
    CONSTRAINT FK_id_empleado_empleado1 FOREIGN KEY (id_empleado) REFERENCES Empleados_PTC(id_empleado) 
);
 
CREATE TABLE DetallePedidos_PTC( 
    id_detapedido NUMBER PRIMARY KEY, 
    id_pedido NUMBER, 
    id_producto NUMBER, 
    cantidad NUMBER(3), 
    precio NUMBER(7,2), 
    CONSTRAINT FK_id_pedido_pedido1 FOREIGN KEY (id_pedido) REFERENCES PEDIDOS_PTC(id_pedido), 
    CONSTRAINT FK_id_producto_producto1 FOREIGN KEY (id_producto) REFERENCES Detalle_Productos_PTC(id_producto) 
);
 
 
CREATE TABLE PermisosEmpelado_PTC( 
    id_permisoempl NUMBER PRIMARY KEY, 
    id_permiso NUMBER, 
    id_empleado NUMBER, 
    habilitado NUMBER, 
    CONSTRAINT FK_id_permiso1 FOREIGN KEY (id_permiso) REFERENCES Permisos_PTC(id_permiso), 
    CONSTRAINT FK_id_empleado2 FOREIGN KEY (id_empleado) REFERENCES Empleados_PTC(id_empleado) 
);
 
 
CREATE TABLE DetaMovInventario_PTC( 
    id_detamov NUMBER PRIMARY KEY, 
    id_movinv NUMBER, 
    id_producto NUMBER, 
    cantidad NUMBER(7,2), 
    CONSTRAINT FK_id_movinv_movinv1 FOREIGN KEY (id_movinv) REFERENCES MovsInventario_PTC(id_movinv), 
    CONSTRAINT FK_id_producto_producto4 FOREIGN KEY (id_producto) REFERENCES Detalle_Productos_PTC(id_producto) 
);
 
 
----------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------TRIGGER Y SECUENCIAS-----------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------
 
-- Secuencia y trigger para clientes_PTC
CREATE SEQUENCE clientes_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigClientes
BEFORE INSERT ON clientes_PTC
FOR EACH ROW
BEGIN
    SELECT clientes_seq.NEXTVAL INTO :NEW.id_cliente FROM dual;
END;
 
 
-- Secuencia y trigger para Empleados_PTC
CREATE SEQUENCE empleados_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigEmpleados
BEFORE INSERT ON Empleados_PTC
FOR EACH ROW
BEGIN
    SELECT empleados_seq.NEXTVAL INTO :NEW.id_empleado FROM dual;
END;
 
 
-- Secuencia y trigger para Permisos_PTC
CREATE SEQUENCE permisos_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigPermisos
BEFORE INSERT ON Permisos_PTC
FOR EACH ROW
BEGIN
    SELECT permisos_seq.NEXTVAL INTO :NEW.id_permiso FROM dual;
END;
 
-- Secuencia y trigger para PEDIDOS_PTC
CREATE SEQUENCE pedidos_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigPedidos
BEFORE INSERT ON PEDIDOS_PTC
FOR EACH ROW
BEGIN
    SELECT pedidos_seq.NEXTVAL INTO :NEW.id_pedido FROM dual;
END;
 
 
-- Secuencia y trigger para DetallePedidos_PTC
CREATE SEQUENCE detalle_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigDetalle
BEFORE INSERT ON DetallePedidos_PTC
FOR EACH ROW
BEGIN
    SELECT detalle_seq.NEXTVAL INTO :NEW.id_detapedido FROM dual;
END;
 
 
-- Secuencia y trigger para PermisosEmpelado_PTC
CREATE SEQUENCE permisosemp_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigPermisosEmp
BEFORE INSERT ON PermisosEmpelado_PTC
FOR EACH ROW
BEGIN
    SELECT permisosemp_seq.NEXTVAL INTO :NEW.id_permisoempl FROM dual;
END;
/
 
-- Secuencia y trigger para Productos_PTC
CREATE SEQUENCE productos_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigProductos
BEFORE INSERT ON Detalle_Productos_PTC
FOR EACH ROW
BEGIN
    SELECT productos_seq.NEXTVAL INTO :NEW.id_producto FROM dual;
END;
/
 
-- Secuencia y trigger para DetaMovInventario_PTC
CREATE SEQUENCE detamov_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigDetaMov
BEFORE INSERT ON DetaMovInventario_PTC
FOR EACH ROW
BEGIN
    SELECT detamov_seq.NEXTVAL INTO :NEW.id_detamov FROM dual;
END;
/
 
-- Secuencia y trigger para MovsInventario_PTC
CREATE SEQUENCE inventario_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigMovinv
BEFORE INSERT ON MovsInventario_PTC
FOR EACH ROW
BEGIN
    SELECT inventario_seq.NEXTVAL INTO :NEW.id_movinv FROM dual;
END;
 
-- Secuencia y trigger para Menu_PTC
CREATE SEQUENCE Menus_seq START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER TrigMenus
BEFORE INSERT ON Menus_PTC
FOR EACH ROW
BEGIN
    SELECT Menus_seq.NEXTVAL INTO :NEW.id_menu FROM dual;
END;
 
 
select * from Menus_PTC ;
 
INSERT INTO Menus_PTC ( categoria, imagen_categoria) VALUES ( 'Tacos', 'antojitos.jpg');
INSERT INTO Menus_PTC ( categoria, imagen_categoria) VALUES ( 'Tortas', 'platos_fuertes.jpg');
INSERT INTO Menus_PTC ( categoria, imagen_categoria) VALUES ( 'Burritos', 'postres.jpg');
INSERT INTO Menus_PTC ( categoria, imagen_categoria) VALUES ( 'Chiles', 'bebidas.jpg');
 
 
INSERT INTO Detalle_Productos_PTC ( id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (  (select id_menu from Menus_PTC where Categoria='Tacos' ),'Tacos al Pastor', 'Tacos de carne de cerdo adobada con pi?a y cebolla', 3.00, 50, 'tacos_al_pastor.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu,producto, descripcion, precioventa, stock, imagen_comida) VALUES ( (select id_menu from Menus_PTC where Categoria='Tacos' ),'Tacos de pollo', 'Tacos de pollo muy buenos', 2.50, 30, 'jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu,producto, descripcion, precioventa, stock, imagen_comida) VALUES ( (select id_menu from Menus_PTC where Categoria='Tortas' ),'Torta de milanesa', 'Torta de pan rellena de milanesa de pollo o res, con aguacate y jitomate', 4.50, 20, 'torta_milanesa.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES ( (select id_menu from Menus_PTC where Categoria='Tortas' ),'Torta de pollo', 'Torta de pollo, cubierto con salsa de nuez y granada', 10.00, 10, 'Torta.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu,producto, descripcion, precioventa, stock, imagen_comida) VALUES ((select id_menu from Menus_PTC where Categoria='Burritos'), 'Burritos', 'Burritos para el burro', 3.50, 25, 'burro.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu,producto, descripcion, precioventa, stock, imagen_comida) VALUES ((select id_menu from Menus_PTC where Categoria='Burritos'), 'burrito de carne', 'Burrito de carne para el burro', 2.00, 40, 'b.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES ((select id_menu from Menus_PTC where Categoria='Chile'),'Chile', 'Chile con jugo de limon y licor de naranja', 5.00, 15, 'chile.jpg');
 

 
commit;
 
 
Select id_menu from Menus_PTC;

SELECT Menus_PTC.id_menu,
Menus_PTC.categoria,
Detalle_Productos_PTC.id_producto,
Detalle_Productos_PTC.producto, 
Detalle_Productos_PTC.descripcion, 
Detalle_Productos_PTC.precioventa,
Detalle_Productos_PTC.stock,
Menus_PTC.imagen_categoria,
Detalle_Productos_PTC.imagen_comida 
FROM Menus_PTC
INNER JOIN Detalle_Productos_PTC
ON Menus_PTC.id_menu = Detalle_Productos_PTC.id_menu
;
  