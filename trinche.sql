create table PEDIDOS_PTC( 
id_pedido number PRIMARY KEY, 
id_cliente number, 
id_empleado number, 
id_carritoCompra number,
pedido_no number, 
fechahora_pedido Date, 
otrosdatos varchar2(200), 
total_pedido number(7,2), 
recibido_rest number(1), 
enproceso number(1), 
enviado number(1), 
entregado number(1), 
anulado number(1), 
pagotarjeta number(1), 
pagoefectivo number(1), 
comeraqui number(1), 
llevar number(1), 
paraenvio number(1), 
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



create table Productos_PTC( 
id_producto number PRIMARY KEY, 
producto char(20), 
precioventa number(7,2), 
existencia number(6), 
cod_prodcuto char(20) 
); 

 

create table clientes_PTC( 
id_cliente number PRIMARY KEY,
UUID_cliente VARCHAR2(50), 
cod_clie char(10), 
nom_clie char(20), 
ape_clie char(20), 
tel_clie char(20), 
dir_entrega varchar2(200), 
usu_cliente varchar2(15), 
correoElectronico VARCHAR2(100) NOT NULL UNIQUE,
contrasena VARCHAR2(255) NOT NULL 
);

create table Empleados_PTC( 
id_empleado number PRIMARY KEY, 
cod_empleado char(10), 
nom_empleado char(20), 
ape_empleado char(20), 
user_empleado varchar2(15), 
clave_empleado varchar2(128) 
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
cod_menu char(10), 
descripcionmenu varchar2(100) 
); 

create table DetalleMenus_PTC( 
id_detamenu number PRIMARY KEY, 
id_menu number, 
id_producto number, 
precio number(7,2), 
CONSTRAINT FK_id_menu_menu1 FOREIGN KEY (id_menu) REFERENCES Menus_PTC(id_menu), 
CONSTRAINT FK_id_producto_producto3 FOREIGN KEY (id_producto) REFERENCES Productos_PTC(id_producto) 
); 

 

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


