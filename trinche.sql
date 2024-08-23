create table clientes_PTC( 
id_cliente NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, 
nombre_clie varchar2(20), 
telefono_clie varchar2(20), 
correoElectronico VARCHAR2(100) NOT NULL UNIQUE,
contrasena VARCHAR2(255),
direccion_entrega VARCHAR2(300),
imagen_Clientes varchar2(250)
);



create table Empleados_PTC( 
id_empleado number PRIMARY KEY, 
nom_empleado varchar2(20), 
ape_empleado varchar2(20), 
usu_empleado varchar2(15), 
contrasena varchar2(128) 
); 


create table Menus_PTC( 
id_menu Number PRIMARY KEY, 
categoria varchar2(20),
imagen_categoria varchar2(250)
); 



create table Permisos_PTC( 
id_permiso number PRIMARY KEY, 
permiso number, 
motivopermiso char(15) 
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

create table DetallePedidos_PTC( 
id_detapedido number PRIMARY KEY, 
id_pedido number, 
id_producto number, 
cantidad number(3), 
precio number(7,2), 
CONSTRAINT FK_id_pedido_pedido1 FOREIGN KEY (id_pedido) REFERENCES PEDIDOS_PTC(id_pedido), 
CONSTRAINT FK_id_producto_producto1 FOREIGN KEY (id_producto) REFERENCES Detalle_Productos_PTC(id_producto) 
); 



create table PermisosEmpelado_PTC( 
id_permisoempl number PRIMARY KEY, 
id_permiso number, 
id_empleado number, 
habilitado number, 
CONSTRAINT FK_id_permiso1 FOREIGN KEY (id_permiso) REFERENCES Permisos_PTC(id_permiso), 
CONSTRAINT FK_id_empleado2 FOREIGN KEY (id_empleado) REFERENCES Empleados_PTC(id_empleado) 
); 



create table Detalle_Productos_PTC( 
id_producto number PRIMARY KEY, 
id_menu Number,
producto varchar2(20), 
descripcion varchar2(250),
precioventa number, 
stock int,
imagen_comida varchar2(250),
--cod_prodcuto char(20)
CONSTRAINT FK_categoria FOREIGN KEY (id_menu) REFERENCES Menus_PTC(id_menu)
); 


//Tabla de mas
--create table DetalleMenus_PTC( 
--id_detamenu number PRIMARY KEY, 
--id_menu number, 
--id_producto number,
--CONSTRAINT FK_categoria FOREIGN KEY (categoria) REFERENCES Menus_PTC(categoria), 
--CONSTRAINT FK_id_producto FOREIGN KEY (id_producto) REFERENCES Productos_PTC(id_producto) 
--); 


create table DetaMovInventario_PTC( 
id_detamov number PRIMARY KEY, 
id_movinv number, 
id_producto number, 
cantidad number(7,2), 
CONSTRAINT FK_id_movinv_movinv1 FOREIGN KEY (id_movinv) REFERENCES MovsInventario_PTC(id_movinv), 
CONSTRAINT FK_id_producto_producto4 FOREIGN KEY (id_producto) REFERENCES Detalle_Productos_PTC(id_producto) 
); 


create table MovsInventario_PTC( 
id_movinv number PRIMARY KEY, 
mov_no number, 
fechahoramov date, 
concepto varchar2(200), 
entrada number(1), 
salida number(1) 
); 


select * from clientes_PTC;


DROP TABLE CarritoCompra_PTC;

DROP TABLE PEDIDOS_PTC;

DROP TABLE DetallePedidos_PTC;

drop table clientes_ptc;



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



select * from Detalle_Productos_PTC ;

INSERT INTO Menus_PTC ( categoria, imagen_categoria) VALUES ( 'Tacos', 'antojitos.jpg');
INSERT INTO Menus_PTC ( categoria, imagen_categoria) VALUES ( 'Tortas', 'platos_fuertes.jpg');
INSERT INTO Menus_PTC ( categoria, imagen_categoria) VALUES ( 'Burritos', 'postres.jpg');
INSERT INTO Menus_PTC ( categoria, imagen_categoria) VALUES ( 'Chiles', 'bebidas.jpg');


INSERT INTO Detalle_Productos_PTC ( id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (  1,'Tacos al Pastor', 'Tacos de carne de cerdo adobada con pi?a y cebolla', 3.00, 50, 'tacos_al_pastor.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu,producto, descripcion, precioventa, stock, imagen_comida) VALUES ( 2,'Tacos de pollo', 'Tacos de pollo muy buenos', 2.50, 30, 'jpg');
INSERT INTO Detalle_Productos_PTC (  id_menu,producto, descripcion, precioventa, stock, imagen_comida) VALUES ( 3,'Torta de milanesa', 'Torta de pan rellena de milanesa de pollo o res, con aguacate y jitomate', 4.50, 20, 'torta_milanesa.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES ( 4,'Torta de pollo', 'Torta de pollo, cubierto con salsa de nuez y granada', 10.00, 10, 'Torta.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu,producto, descripcion, precioventa, stock, imagen_comida) VALUES (5, 'Buriitos', 'Burritos para el burro', 3.50, 25, 'burro.jpg');
INSERT INTO Detalle_Productos_PTC ( id_menu,producto, descripcion, precioventa, stock, imagen_comida) VALUES (6, 'burrito de carne', 'Burrito de carne para el burro', 2.00, 40, 'b.jpg');
INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (7,'Chile', 'Chile con jugo de lim?n y licor de naranja', 5.00, 15, 'chile.jpg');


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
 
 DELETE FROM Detalle_Productos_PTC
WHERE ID_MENU IS NULL;

        