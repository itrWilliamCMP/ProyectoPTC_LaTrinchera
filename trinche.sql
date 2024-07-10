create table PEDIDOS_PTC( 
id_pedido number PRIMARY KEY, 
id_cliente number, 
id_empleado number, 
//id_carritoCompra number,
fecha_hora_pedido Date, 
//otrosdatos varchar2(200), 
total_pedido number(7,2), 
Estado VARCHAR(20) CHECK (Estado IN ('enviado', 'Entregado','Anulado','En proceso')),
Tipo_Pago VARCHAR(20) CHECK (Estado IN ('Tarjeta', 'Efectivo')),
Tipo_Pedido VARCHAR(20) CHECK (Estado IN ('Comer aqui', 'Para llevar','Delivery')),

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
CONSTRAINT FK_id_producto_producto1 FOREIGN KEY (id_producto) REFERENCES Productos_PTC(id_producto) 
); 
 

create table clientes_PTC( 
UUID_cliente VARCHAR2(50) PRIMARY KEY, 
//cod_clie char(10), 
nombre_clie varchar2(20), 
apellido_clie varchar2(20), 
telefono_clie varchar2(20), 
dir_entrega varchar2(200), 
usu_cliente varchar2(15), 
correoElectronico VARCHAR2(100) NOT NULL UNIQUE,
contrasena VARCHAR2(255) NOT NULL 
);

create table Empleados_PTC( 
id_empleado number PRIMARY KEY, 
nom_empleado varchar2(20), 
ape_empleado varchar2(20), 
usu_empleado varchar2(15), 
contrasena varchar2(128) 
); 

 
create table PermisosEmpelado_PTC( 
id_permisoempl number PRIMARY KEY, 
id_permiso number, 
id_empleado number, 
habilitado number, 
CONSTRAINT FK_id_permiso1 FOREIGN KEY (id_permiso) REFERENCES Permisos_PTC(id_permiso), 
CONSTRAINT FK_id_empleado2 FOREIGN KEY (id_empleado) REFERENCES Empleados_PTC(id_empleado) 
); 

 
create table Permisos_PTC( 
id_permiso number PRIMARY KEY, 
permiso number, 
motivopermiso char(15) 
); 

create table Menus_PTC( 
id_menu Number PRIMARY KEY, 
categoria varchar2(20)
); 

create table Productos_PTC( 
id_producto number PRIMARY KEY, 
id_menu Number,
producto varchar2(20), 
descripcion varchar2(250),
precioventa number, 
stock int,
//cod_prodcuto char(20)
CONSTRAINT FK_categoria FOREIGN KEY (id_menu) REFERENCES Menus_PTC(id_menu)); 



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

 

create table MovsInventario_PTC( 
id_movinv number PRIMARY KEY, 
mov_no number, 
fechahoramov date, 
concepto varchar2(200), 
entrada number(1), 
salida number(1) 
); 

CREATE SEQUENCE CLIENTE
START WITH 1
INCREMENT BY 1;

CREATE TRIGGER TrigClientes12
BEFORE INSERT ON clientes_PTC
for EACH ROW
BEGIN
SELECT CLIENTE.NEXTVAL INTO : NEW.id_cliente from DUAL;
END;

select * from clientes_PTC;


DROP TABLE CarritoCompra_PTC;

DROP TABLE PEDIDOS_PTC;

DROP TABLE DetallePedidos_PTC;

drop table clientes_ptc;


