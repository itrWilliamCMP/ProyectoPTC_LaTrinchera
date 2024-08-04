create table clientes_PTC( 
<<<<<<< HEAD
id_cliente VARCHAR2(50) PRIMARY KEY, 
=======
id_cliente NUMBER PRIMARY KEY, 
>>>>>>> master
nombre_clie varchar2(20), 
telefono_clie varchar2(20), 
correoElectronico VARCHAR2(100) NOT NULL UNIQUE,
contrasena VARCHAR2(255),
direccion_entrega VARCHAR2(300)
);

CREATE SEQUENCE clientes
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigClientes
BEFORE INSERT ON clientes_PTC
for EACH ROW
BEGIN
SELECT clientes.NEXTVAL INTO : NEW.id_cliente from DUAL;
END; 

create table Empleados_PTC( 
id_empleado number PRIMARY KEY, 
nom_empleado varchar2(20), 
ape_empleado varchar2(20), 
usu_empleado varchar2(15), 
contrasena varchar2(128) 
); 

CREATE SEQUENCE empleados
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigEmpleados
BEFORE INSERT ON Empleados_PTC
for EACH ROW
BEGIN
SELECT empleados.NEXTVAL INTO : NEW.id_empleado from DUAL;
END; 

create table Menus_PTC( 
id_menu Number PRIMARY KEY, 
categoria varchar2(20),
imagen_categoria varchar2(250)
); 

CREATE SEQUENCE menus
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigMenus
BEFORE INSERT ON Menus_PTC
for EACH ROW
BEGIN
SELECT id_menu.NEXTVAL INTO : NEW.id_menu from DUAL;
END; 

create table Permisos_PTC( 
id_permiso number PRIMARY KEY, 
permiso number, 
motivopermiso char(15) 
); 

CREATE SEQUENCE permisos
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigPermisos
BEFORE INSERT ON Permisos_PTC
for EACH ROW
BEGIN
SELECT permisos.NEXTVAL INTO : NEW.id_permiso from DUAL;
END;

create table PEDIDOS_PTC( 
id_pedido number PRIMARY KEY, 
id_cliente number, 
id_empleado number, 
//id_carritoCompra number,
fecha_hora_pedido Date, 
//otrosdatos varchar2(200), 
total_pedido number(7,2), 
Estado VARCHAR(20) CHECK (Estado IN ('enviado', 'Entregado','Anulado','En proceso')),
Tipo_Pago VARCHAR(20) CHECK (Tipo_Pago IN ('Tarjeta', 'Efectivo')),
Tipo_Pedido VARCHAR(20) CHECK (Tipo_Pedido IN ('Comer aqui', 'Para llevar','Delivery')),

CONSTRAINT FK_id_cliente_cliente1 FOREIGN KEY (id_cliente) REFERENCES clientes_PTC(id_cliente), 
CONSTRAINT FK_id_empleado_empleado1 FOREIGN KEY (id_empleado) REFERENCES Empleados_PTC(id_empleado)
); 

CREATE SEQUENCE pedidos
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigPedidos
BEFORE INSERT ON PEDIDOS_PTC
for EACH ROW
BEGIN
SELECT pedidos.NEXTVAL INTO : NEW.id_pedido from DUAL;
END; 

create table DetallePedidos_PTC( 
id_detapedido number PRIMARY KEY, 
id_pedido number, 
id_producto number, 
cantidad number(3), 
precio number(7,2), 
CONSTRAINT FK_id_pedido_pedido1 FOREIGN KEY (id_pedido) REFERENCES PEDIDOS_PTC(id_pedido), 
CONSTRAINT FK_id_producto_producto1 FOREIGN KEY (id_producto) REFERENCES Productos_PTC(id_producto) 
); 

CREATE SEQUENCE detalle
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigDetalle
BEFORE INSERT ON DetallePedidos_PTC
for EACH ROW
BEGIN
SELECT detalle.NEXTVAL INTO : NEW.id_detapedido from DUAL;
END; 

create table PermisosEmpelado_PTC( 
id_permisoempl number PRIMARY KEY, 
id_permiso number, 
id_empleado number, 
habilitado number, 
CONSTRAINT FK_id_permiso1 FOREIGN KEY (id_permiso) REFERENCES Permisos_PTC(id_permiso), 
CONSTRAINT FK_id_empleado2 FOREIGN KEY (id_empleado) REFERENCES Empleados_PTC(id_empleado) 
); 

CREATE SEQUENCE permisosemp
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigPermisosEmp
BEFORE INSERT ON PermisosEmpelado_PTC
for EACH ROW
BEGIN
SELECT permisosemp.NEXTVAL INTO : NEW.id_permisoemp1 from DUAL;
END; 

create table Productos_PTC( 
id_producto number PRIMARY KEY, 
id_menu Number,
producto varchar2(20), 
descripcion varchar2(250),
precioventa number, 
stock int,
imagen_comida varchar2(250),
CONSTRAINT FK_categoria FOREIGN KEY (id_menu) REFERENCES Menus_PTC(id_menu)
); 

CREATE SEQUENCE productos
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigProductos
BEFORE INSERT ON Productos_PTC
for EACH ROW
BEGIN
SELECT productos.NEXTVAL INTO : NEW.id_producto from DUAL;
END; 

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
CONSTRAINT FK_id_producto_producto4 FOREIGN KEY (id_producto) REFERENCES Productos_PTC(id_producto) 
); 

 CREATE SEQUENCE detamov
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigDetaMov
BEFORE INSERT ON DetaMovInventario_PTC
for EACH ROW
BEGIN
SELECT detamov.NEXTVAL INTO : NEW.id_detamov from DUAL;
END; 

create table MovsInventario_PTC( 
id_movinv number PRIMARY KEY, 
mov_no number, 
fechahoramov date, 
concepto varchar2(200), 
entrada number(1), 
salida number(1) 
); 

CREATE SEQUENCE inventario
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigMovinv
BEFORE INSERT ON MovsInventario_PTC
for EACH ROW
BEGIN
SELECT inventario.NEXTVAL INTO : NEW.id_movinv from DUAL;
END;

select * from Menus_PTC;

select * from Productos_PTC;

INSERT INTO Menus_PTC (id_menu, categoria, imagen_categoria) VALUES (1, 'Tacos', 'antojitos.jpg');
INSERT INTO Menus_PTC (id_menu, categoria, imagen_categoria) VALUES (2, 'Tortas', 'platos_fuertes.jpg');
INSERT INTO Menus_PTC (id_menu, categoria, imagen_categoria) VALUES (3, 'Burritos', 'postres.jpg');
INSERT INTO Menus_PTC (id_menu, categoria, imagen_categoria) VALUES (4, 'Chiles', 'bebidas.jpg');

INSERT INTO Productos_PTC (id_producto, id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (1, 1, 'Tacos al Pastor', 'Tacos de carne de cerdo adobada con piña y cebolla', 3.00, 50, 'tacos_al_pastor.jpg');
INSERT INTO Productos_PTC (id_producto, id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (2, 1, 'Tacos de pollo', 'Tacos de pollo muy buenos', 2.50, 30, 'jpg');
INSERT INTO Productos_PTC (id_producto, id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (3, 2, 'Torta de milanesa', 'Torta de pan rellena de milanesa de pollo o res, con aguacate y jitomate', 4.50, 20, 'torta_milanesa.jpg');
INSERT INTO Productos_PTC (id_producto, id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (4, 2, 'Torta de pollo', 'Torta de pollo, cubierto con salsa de nuez y granada', 10.00, 10, 'Torta.jpg');
INSERT INTO Productos_PTC (id_producto, id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (5, 3, 'Buriitos', 'Burritos para el burro', 3.50, 25, 'burro.jpg');
INSERT INTO Productos_PTC (id_producto, id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (6, 3, 'burrito de carne', 'Burrito de carne para el burro', 2.00, 40, 'b.jpg');
INSERT INTO Productos_PTC (id_producto, id_menu, producto, descripcion, precioventa, stock, imagen_comida) VALUES (7, 4, 'Chile', 'Chile con jugo de limón y licor de naranja', 5.00, 15, 'chile.jpg');

DROP TABLE Menus_PTC;

DROP TABLE Productos_PTC;

DROP TABLE CarritoCompra_PTC;

DROP TABLE PEDIDOS_PTC;

DROP TABLE DetallePedidos_PTC;

drop table clientes_ptc;


