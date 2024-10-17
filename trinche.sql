-- ------------------------------------------------------------------------------
-- ------------------------------- CREACIÓN DE TABLAS ----------------------------
-- ------------------------------------------------------------------------------

CREATE TABLE Clientes_PTC( 
    id_cliente NUMBER PRIMARY KEY, 
    nombre_clie VARCHAR2(20), 
    telefono_clie VARCHAR2(20), 
    correoElectronico VARCHAR2(100) NOT NULL UNIQUE,
    contrasena VARCHAR2(255),
    direccion_entrega VARCHAR2(300),
    imagen_clientes VARCHAR2(250)
);
select * from Clientes_PTC;

CREATE TABLE Empleados_PTC( 
    id_empleado NUMBER PRIMARY KEY, 
    nom_empleado VARCHAR2(20), 
    ape_empleado VARCHAR2(20), 
    usu_empleado VARCHAR2(15), 
    contrasena VARCHAR2(128) 
);

SELECT * FROM clientes_PTC;

SELECT dp.id_producto,
        dp.producto,
        dp.imagen_Comida,
        dp.descripcion,
        dp.imagen_comida,
        dp.precioventa,
        dp.stock,
        c.categoria
    FROM
        Detalle_Productos_PTC dp
    INNER JOIN
        Menus_PTC c ON dp.ID_Menu = c.ID_Menu
    WHERE
        dp.id_producto = 1;

SELECT contrasena FROM Empleados_PTC WHERE nom_empleado = 'Trincherito';

UPDATE Empleados_PTC 
SET contrasena = '438c001b149c37269e78710c2c298248eb9b5b7e10f2826b2c57dd98c35e400f'
WHERE nom_empleado = 'Trincherito';

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
    Estado VARCHAR2(20) CHECK (Estado IN ('enviado', 'Entregado','Anulado','En proceso')), 
    Tipo_Pago VARCHAR2(20) CHECK (Tipo_Pago IN ('Tarjeta', 'Efectivo')), 
    Tipo_Pedido VARCHAR2(20) CHECK (Tipo_Pedido IN ('Comer aqui', 'Para llevar','Delivery')), 
    CONSTRAINT FK_id_cliente_cliente1 FOREIGN KEY (id_cliente) REFERENCES Clientes_PTC(id_cliente), 
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

CREATE TABLE PermisosEmpleado_PTC( 
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

CREATE TABLE Auditoria_Pedidos (
    id_auditoria NUMBER PRIMARY KEY,
    id_pedido NUMBER,
    usuario_modificacion VARCHAR2(50),
    fecha_modificacion DATE DEFAULT SYSDATE,
    campo_modificado VARCHAR2(50),
    valor_anterior VARCHAR2(100),
    valor_nuevo VARCHAR2(100),
    CONSTRAINT FK_id_pedido FOREIGN KEY (id_pedido) REFERENCES PEDIDOS_PTC(id_pedido)
);

-- ------------------------------------------------------------------------------
-- --------------------------- CREACIÓN DE SECUENCIAS ---------------------------
-- ------------------------------------------------------------------------------

-- Secuencia para Clientes_PTC
CREATE SEQUENCE clientes_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para Empleados_PTC
CREATE SEQUENCE empleados_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para Permisos_PTC
CREATE SEQUENCE permisos_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para PEDIDOS_PTC
CREATE SEQUENCE pedidos_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para DetallePedidos_PTC
CREATE SEQUENCE detalle_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para PermisosEmpleado_PTC
CREATE SEQUENCE permisosemp_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para Detalle_Productos_PTC
CREATE SEQUENCE productos_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para DetaMovInventario_PTC
CREATE SEQUENCE detamov_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para MovsInventario_PTC
CREATE SEQUENCE inventario_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para Menus_PTC
CREATE SEQUENCE Menus_seq START WITH 1 INCREMENT BY 1;

-- Secuencia para Auditoria_Pedidos
CREATE SEQUENCE Auditoria_Pedidos_SEQ START WITH 1 INCREMENT BY 1;

-- ------------------------------------------------------------------------------
-- --------------------------- CREACIÓN DE TRIGGERS ----------------------------
-- ------------------------------------------------------------------------------

-- Trigger para Clientes_PTC
CREATE OR REPLACE TRIGGER TrigClientes
BEFORE INSERT ON Clientes_PTC
FOR EACH ROW
BEGIN
    SELECT clientes_seq.NEXTVAL INTO :NEW.id_cliente FROM dual;
END;
/
 
-- Trigger para Empleados_PTC
CREATE OR REPLACE TRIGGER TrigEmpleados
BEFORE INSERT ON Empleados_PTC
FOR EACH ROW
BEGIN
    SELECT empleados_seq.NEXTVAL INTO :NEW.id_empleado FROM dual;
END;
/
 
-- Trigger para Permisos_PTC
CREATE OR REPLACE TRIGGER TrigPermisos
BEFORE INSERT ON Permisos_PTC
FOR EACH ROW
BEGIN
    SELECT permisos_seq.NEXTVAL INTO :NEW.id_permiso FROM dual;
END;
/
 
-- Trigger para PEDIDOS_PTC
CREATE OR REPLACE TRIGGER TrigPedidos
BEFORE INSERT ON PEDIDOS_PTC
FOR EACH ROW
BEGIN
    SELECT pedidos_seq.NEXTVAL INTO :NEW.id_pedido FROM dual;
END;
/
 
-- Trigger para DetallePedidos_PTC
CREATE OR REPLACE TRIGGER TrigDetalle
BEFORE INSERT ON DetallePedidos_PTC
FOR EACH ROW
BEGIN
    SELECT detalle_seq.NEXTVAL INTO :NEW.id_detapedido FROM dual;
END;
/
 
-- Trigger para PermisosEmpleado_PTC
CREATE OR REPLACE TRIGGER TrigPermisosEmp
BEFORE INSERT ON PermisosEmpleado_PTC
FOR EACH ROW
BEGIN
    SELECT permisosemp_seq.NEXTVAL INTO :NEW.id_permisoempl FROM dual;
END;
/
 
-- Trigger para Detalle_Productos_PTC
CREATE OR REPLACE TRIGGER TrigProductos
BEFORE INSERT ON Detalle_Productos_PTC
FOR EACH ROW
BEGIN
    SELECT productos_seq.NEXTVAL INTO :NEW.id_producto FROM dual;
END;
/
 
-- Trigger para DetaMovInventario_PTC
CREATE OR REPLACE TRIGGER TrigDetaMov
BEFORE INSERT ON DetaMovInventario_PTC
FOR EACH ROW
BEGIN
    SELECT detamov_seq.NEXTVAL INTO :NEW.id_detamov FROM dual;
END;
/
 
-- Trigger para MovsInventario_PTC
CREATE OR REPLACE TRIGGER TrigMovinv
BEFORE INSERT ON MovsInventario_PTC
FOR EACH ROW
BEGIN
    SELECT inventario_seq.NEXTVAL INTO :NEW.id_movinv FROM dual;
END;
/
 
-- Trigger para Menus_PTC
CREATE OR REPLACE TRIGGER TrigMenus
BEFORE INSERT ON Menus_PTC
FOR EACH ROW
BEGIN
    SELECT Menus_seq.NEXTVAL INTO :NEW.id_menu FROM dual;
END;
/
 
-- Trigger para Auditoria_Pedidos
CREATE OR REPLACE TRIGGER Trigger_Auditoria_Pedidos
AFTER UPDATE ON PEDIDOS_PTC
FOR EACH ROW
DECLARE
    v_usuario VARCHAR2(50);
    
    
    
BEGIN
    -- Obtener el usuario actual de la sesión
    v_usuario := SYS_CONTEXT('USERENV', 'SESSION_USER');

    -- Insertar un registro de auditoría para el cambio en el campo 'Estado'
    IF :OLD.Estado != :NEW.Estado THEN
        INSERT INTO Auditoria_Pedidos (
            id_auditoria, id_pedido, usuario_modificacion, fecha_modificacion, 
            campo_modificado, valor_anterior, valor_nuevo
        ) VALUES (
            Auditoria_Pedidos_SEQ.NEXTVAL, :NEW.id_pedido, v_usuario, SYSDATE, 
            'Estado', :OLD.Estado, :NEW.Estado
        );
    END IF;

    -- Insertar un registro de auditoría para el cambio en el campo 'Tipo_Pago'
    IF :OLD.Tipo_Pago != :NEW.Tipo_Pago THEN
        INSERT INTO Auditoria_Pedidos (
            id_auditoria, id_pedido, usuario_modificacion, fecha_modificacion, 
            campo_modificado, valor_anterior, valor_nuevo
        ) VALUES (
            Auditoria_Pedidos_SEQ.NEXTVAL, :NEW.id_pedido, v_usuario, SYSDATE, 
            'Tipo_Pago', :OLD.Tipo_Pago, :NEW.Tipo_Pago
        );
    END IF;
END;
/
 
-- ------------------------------------------------------------------------------
-- --------------------------- INSERCIÓN DE DATOS -------------------------------
-- ------------------------------------------------------------------------------

-- Insertar datos en Menus_PTC
INSERT INTO Menus_PTC (categoria, imagen_categoria) VALUES ('Tacos', 'https://png.pngtree.com/png-clipart/20230429/original/pngtree-mexican-taco-crepes-with-lemon-sauce-psd-transparent-png-image_9121918.png');
INSERT INTO Menus_PTC (categoria, imagen_categoria) VALUES ('Tortas', 'https://www.tortaslacastellana.com/imagenes/menu/milanesa.png');
INSERT INTO Menus_PTC (categoria, imagen_categoria) VALUES ('Burritos', 'https://www.pngplay.com/wp-content/uploads/14/Burrito-PNG-Photos-2.png');
INSERT INTO Menus_PTC (categoria, imagen_categoria) VALUES ('Chiles', 'https://tienda.lahuerta.com.mx/cdn/shop/files/ICEHR0865ChilePoblanoRelleno_370Gr-2Pz_CHproducto.png?v=1695911835&width=1445');
INSERT INTO Menus_PTC (categoria, imagen_categoria) VALUES ('Sopas', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.quericavida.com%2Frecetas%2Fcarne-asada-facil-de-preparar%2Ff2cadabb-3115-4158-aebf-44b7fd486723&psig=AOvVaw2b35Eq9vIR1KTtv3Aik67p&ust=1729200519746000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOCvvJLsk4kDFQAAAAAdAAAAABAE');
INSERT INTO Menus_PTC (categoria, imagen_categoria) VALUES ('Quesadillas', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Felpoderdelconsumidor.org%2F2022%2F02%2Fel-poder-de-las-quesadillas%2F&psig=AOvVaw3bhUrGQfOu9iQ1mNcyGGK_&ust=1729200814486000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPiAsZftk4kDFQAAAAAdAAAAABAE');


-- Insertar datos en Detalle_Productos_PTC



--------tacos
INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tacos'),
    'Tacos al Pastor', 
    'Los tacos al pastor son un platillo icónico de la gastronomía mexicana, 
    especialmente popular en la Ciudad de México. Este platillo consiste en 
    tortillas de maíz rellenas de carne de cerdo adobada, que se cocina en un trompo
    vertical', 
    3.00, 
    50, 
    'https://static.vecteezy.com/system/resources/previews/036/498/004/non_2x/ai-generated-delicious-tacos-al-pastor-placed-on-transparent-background-free-png.png'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tacos'),
    'Tacos de bistec', 
    'Los tacos de bistec son un platillo típico de la cocina mexicana que consiste en tortillas, generalmente de maíz, rellenas de carne de res (bistec) picada o en tiras, cocinada a la parrilla o en una plancha. Este platillo es una de las variantes más populares de los tacos debido a su sabor jugoso y su preparación sencilla.', 
    2.50, 
    30, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Finmamamaggieskitchen.com%2Ftacos-de-bistec%2F&psig=AOvVaw3sMSdKHhsiKkB0wtTdFO3o&ust=1729201149186000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKjE1rbuk4kDFQAAAAAdAAAAABAE'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tacos'),
    'Tacos de lengua', 
    'Los tacos de lengua son un tipo de taco en la cocina mexicana que utiliza lengua
    de res cocida como el ingrediente principal. Este tipo de taco es muy apreciado por su
    textura suave y su sabor único, y es una de las muchas variantes de tacos que se 
    encuentran en la rica tradición culinaria de México.', 
    4.50, 
    30, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fcomedera.com%
    2Fcomo-hacer-suaves-tacos-de-lengua-mexicanos%2F&psig=AOvVaw2u85O
    P2XvhqUlSKXJi0EFY&ust=1729201238138000&source=images&cd=vfe&opi=899
    78449&ved=0CBQQjRxqFwoTCJCGk-Luk4kDFQAAAAAdAAAAABAK'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tacos'),
    'Tacos de cochinita pibil', 
    'Los tacos de cochinita pibil son un platillo 
    tradicional de la cocina mexicana, específicamente de la región de Yucatán. Estos tacos consisten en tortillas 
    de maíz o harina rellenas de cochinita pibil, que es carne
    de cerdo marinada en achiote y otros condimentos, envuelta
    en hojas de plátano y cocida lentamente.', 
    3.50, 
    30, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.bonviveur.es%2Freceta
    s%2Fcochinita-pibil-casera&psig=AOvVaw2HoU0ikJu1OTCAHbYf0l28&ust=1729201329879
    000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPDQvI7vk4kDFQAAAAAdAAAAABAE'
);


-----tortas
INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tortas'),
    'Torta de carne', 
    'Una torta de carne de res mexicana es un platillo tradicional de la gastronomía
    mexicana que consiste en un sándwich hecho con un bolillo o telera (tipos de pan), 
    relleno principalmente de carne de res y acompañado de una variedad de ingredientes 
    y salsas.', 
    4.50, 
    20, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.unilever
    foodsolutions.us%2Frecipe%2Ftijuana-style-carne-asada-torta-R00764
    75.html&psig=AOvVaw2bF52Z2f9VjPP7O6hIfNqA&ust=1729201452673000&source
    =images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJjJpcjvk4kDFQAAAAAdAAAAABAV'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tortas'),
    'Torta de pollo', 
    'Una torta de pollo mexicana es un tipo de sándwich popular en México que 
    consiste en un pan tipo bolillo o telera, relleno de pollo cocido y desmenuzado,
    acompañado de una variedad de ingredientes y aderezos que pueden incluir aguacate,
    jitomate, cebolla, chiles, mayonesa, entre otros. Es una opción rápida y sabrosa para 
    el desayuno, almuerzo o cena.', 
    4.00, 
    10, 
    'https://store.loscorrales.com.mx/cdn/shop/products/TORTAPECHUGA150px-01_1200x1200.png?v=1590855998'
);


INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tortas'),
    'Torta de birria', 
    'La torta de birria es una combinacion de guiso de cordero y pan de la cocina
    mexicana, una deliciosa y reconfortante opción para el desayuno, el almuerzo o
    la cena. Se trata de un sándwich hecho con pan bolillo o telera, relleno de guiso 
    de carne de cordero, lechuga, cebolla y queso.', 
    5.50, 
    10, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.savorasousvide.com%2Freci
    pes%2Fbeef-birria-torta%2F&psig=AOvVaw0TQKXltarizDdX44_FC9JF&ust=1729201561628000&
    source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCNis0fvvk4kDFQAAAAAdAAAAABAE'
);


INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tortas'),
    'Torta de jamon', 
    'La torta de jamón es un clásico de la cocina mexicana, una deliciosa y
    reconfortante opción para el desayuno, el almuerzo o la cena. Se trata de 
    un sándwich hecho con pan bolillo o telera, relleno de lonchas de jamón, 
    queso, cebolla, tocino y tomate.', 
    2.00, 
    10, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.kiwilimon.com%2Freceta%2Fbotanas%2Ftorta-de-jamon-y-dos-quesos&psig=AOvVaw07T8jytTQoxIK_BUKMm_Dv&ust=1729201626853000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKiWyJ7wk4kDFQAAAAAdAAAAABAK'
);


----burritos

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Burritos'), 
    'Burrito de carne', 
    'Un burrito de carne es un platillo mexicano que consiste en una tortilla de harina rellena de carne (generalmente de res, como carne asada o guisada) y otros ingredientes como frijoles, arroz, queso, salsa, y a veces verduras. La tortilla se enrolla para envolver el relleno y suele servirse caliente. Es una comida completa, fácil de transportar y muy popular tanto en México como en Estados Unidos.', 
    4.50, 
    25, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.cocinafacil.lat%2Frecipes%2Fburritos-de-carne-molida%2F&psig=AOvVaw08zUiWut8miwcvBIu8qKl9&ust=1729201815785000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMCMl_Twk4kDFQAAAAAdAAAAABAE'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Burritos'), 
    'Cacerola de burrito', 
    'Una cacerola con cuatro burritos bañados en queso es un platillo donde se colocan cinco burritos dentro de un recipiente para horno, y luego se cubren con una generosa cantidad de queso derretido o salsa de queso. Cada burrito está relleno de diferentes ingredientes, lo que ofrece una variedad de sabores.
El queso derretido cubre los burritos, haciendo que el plato sea cremoso y sabroso, ideal para compartir.', 
    20.00, 
    40, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3Dp1Pz1fN-nVI&psig=AOvVaw29xx2qGUiF9wIJHVbatfM7&ust=1729202022041000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCKi2s9bxk4kDFQAAAAAdAAAAABAE'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Burritos'), 
    'Burrito de pollo con queso', 
    'Un burrito de pollo relleno de queso es un platillo que consiste en una tortilla de harina rellena de pollo cocido y desmenuzado, combinado con una porción generosa de queso derretido. El pollo puede estar sazonado con especias mexicanas como comino, chile en polvo, o ajo, y el queso puede ser de tipo cheddar, mozzarella, o un queso mexicano como el queso Oaxaca. El burrito se enrolla con estos ingredientes y se sirve caliente, lo que hace que el queso esté bien derretido y el pollo jugoso, creando un platillo delicioso y reconfortante.', 
    5.00, 
    40, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fekilu.com%2Fes%2Freceta%2Fburritos-de-pollo-con-pimientos-y-queso&psig=AOvVaw2TF459UN09FQesbyZj9n4p&ust=1729202150743000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJjc-ZPyk4kDFQAAAAAdAAAAABAE'
);


INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Burritos'), 
    'Burrito de Pollo al Chipotle ', 
    'Un burrito de pollo al chipotle es una tortilla de harina rellena con pollo cocido en una salsa de chipotle, que le da un sabor ahumado y ligeramente picante. El pollo al chipotle suele prepararse con una mezcla de chiles chipotle en adobo, crema o salsa de tomate, cebolla y especias. Además del pollo, el burrito puede incluir ingredientes como arroz, frijoles, queso, y aguacate o guacamole, que equilibran el picante del chipotle. La tortilla se enrolla con el relleno caliente y se sirve lista para disfrutar, ofreciendo un sabor intenso y un toque picante característico.', 
    4.00, 
    40, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fcookidoo.mx%2Frecipes%2Frecipe%2Fes-MX%2Fr104474&psig=AOvVaw2BDz89b-SUoBfj9c9QBba2&ust=1729202208330000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMDno9Lyk4kDFQAAAAAdAAAAABAE'
);


----chiles

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Chiles'), 
    'Chile en Nogada', 
    'El chile en nogada es un platillo típico de la gastronomía mexicana, especialmente popular durante las celebraciones de las fiestas patrias en septiembre. Consiste en un chile poblano relleno de una mezcla de carne molida con frutas, como manzana, pera, plátano, piña, pasas y nueces, sazonada con especias como canela y clavo. Luego se cubre con una salsa de nuez de castilla y se decora con granos de granada y perejil.', 
    6.00, 
    15, 
    'https://entodomx.com.mx/wp-content/uploads/2019/09/Chile-Nogada-min.png'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Chiles'), 
    'Chile relleno', 
' Existen diversas versiones de chiles rellenos que varían según la región. Por lo general, se rellenan chiles poblanos con una mezcla de carne molida, verduras, frutas y especias. Se suelen capear y freír, y se sirven con salsa de tomate.',
  4.00, 
    15, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwildfork.mx%2Fblogs%2Frecetas%2Fchile-relleno-de-picadillo-de-cerdo&psig=AOvVaw2witem6tJXnBZa03lgfUq2&ust=1729202493540000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOCQv7fzk4kDFQAAAAAdAAAAABAK'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Chiles'), 
    'Chile en crema', 
    'Las rajas con crema es un exquisito platillo típico de la cocina mexicana que destaca por su sabor reconfortante y su textura cremosa. Consiste en chiles poblanos asados y cortados en tiras delgadas, que luego se cocinan a fuego lento con cebolla y crema fresca. La cocción lenta permite que los sabores se mezclen y se intensifiquen, mientras que la crema aporta una suavidad y untuosidad irresistibles.', 
    4.99, 
    15, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.recetasnestle.com.mx%2Frecetas%2Fchiles-rellenos-de-carne&psig=AOvVaw3TEybLv1f746J9Bb0BEq-6&ust=1729202559747000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJDNjdvzk4kDFQAAAAAdAAAAABAE'
);


INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Chiles'), 
    'Chile rellono de picadillo', 
    'Un chile relleno de picadillo es un platillo tradicional mexicano que consiste en un
    chile poblano asado y pelado, relleno con un guiso de picadillo, que es una mezcla de 
    carne molida (generalmente de res o cerdo) cocida con ingredientes como tomate, cebolla,
    ajo, zanahorias, papas, pasas, almendras y especias como comino y laurel. El picadillo 
    aporta un sabor dulce y salado, complementando el sabor suave y ligeramente picante del
    chile poblano. Una vez relleno, el chile puede servirse tal cual o cubierto con salsa,
    crema o queso. Es un platillo sustancioso y lleno de sabor.', 
    8.99, 
    15, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fthecommunalfeast.com%2F2021%2F08%2Fchiles-rellenos-stuffed-with-picadillo%2F&psig=AOvVaw0w96-PTiMufHSZshjGcTsZ&ust=1729202705864000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMDv2p30k4kDFQAAAAAdAAAAABAE'
);


---carnes

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Sopas'), 
    'Pozole', 
    'El pozole es un platillo tradicional mexicano, especialmente popular en las regiones de Guerrero, Jalisco, Michoacán y el Distrito Federal. Se trata de una sopa espesa y sustanciosa elaborada con maíz blanco grande (llamado hominy), carne de cerdo o pollo, y una base de caldo sazonado con chiles rojos y otros ingredientes aromáticos. Es una comida festiva y reconfortante, a menudo asociada con celebraciones y reuniones familiares.', 
    6.00, 
    15, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fgran.luchito.com%2Fes%2Frecipes%2Ftraditional-mexican%2Fpozole%2F&psig=AOvVaw1Co3FxDLfzurrotW4XgzDW&ust=1729203187820000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOi28IL2k4kDFQAAAAAdAAAAABAE'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Sopas'), 
    'Sopa de frijoles', 
' La sopa de frijoles es un plato reconfortante y nutritivo que se prepara con frijoles cocidos como ingrediente principal. Esta sopa es popular en muchas culturas culinarias alrededor del mundo, con variaciones en ingredientes y técnicas de preparación. Sin embargo, en la cocina mexicana, la sopa de frijoles es una especialidad muy apreciada, especialmente durante los meses más fríos del año.',
  3.99, 
    15, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.daisybrand.com%2Fes%2Frecetas%2Fsopa-de-frijoles-negros%2F&psig=AOvVaw12W67D7F3KKrS6hKzJsY5p&ust=1729203127105000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOjCxuX1k4kDFQAAAAAdAAAAABAE'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Sopas'), 
    'Caldo de pollo', 
    'El caldo de pollo es una preparación líquida elaborada principalmente con agua y pollo, junto con una variedad de vegetales, hierbas y especias. Es un caldo reconfortante y versátil que se utiliza como base para sopas, guisos, salsas y otros platos, así como una bebida reconfortante en sí misma.', 
    4.99, 
    15, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.allrecipes.com%2Frecipe%2F231097%2Fcaldo-de-pollo%2F&psig=AOvVaw0EXDQf_r9al4pGcndd0e0K&ust=1729203030733000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOjbkrz1k4kDFQAAAAAdAAAAABAE'
);


INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Sopas'), 
    'Sopa de tortilla', 
    'La sopa de tortilla mexicana, también conocida como sopa azteca o sopa de tortilla, es un plato tradicional de la cocina mexicana. Se caracteriza por su base de caldo de pollo sazonado con tomate, cebolla, ajo y chiles, al que se añaden tiras de tortilla fritas o tostadas, así como otros ingredientes como aguacate, queso fresco, crema, cilantro y jugo de limón. Es una sopa reconfortante, sabrosa y llena de texturas.', 
    4.99, 
    15, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.recepedia.com%2Fes-mx%2Freceta%2Fsopa%2F212885-sopa-de-tortilla%2F&psig=AOvVaw1h5_fmyTWDQQ7wCEfAWiAT&ust=1729202941916000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCNC9hY31k4kDFQAAAAAdAAAAABAE'
);






---Quesadillas

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Quesadillas'),
    'Quesadilla de aguacate y queso',
    'Las quesadillas de aguacate y queso son una variante de las tradicionales quesadillas mexicanas, que consisten en tortillas de maíz o harina rellenas de aguacate y queso, y luego cocidas hasta que el queso se derrite y la tortilla esté dorada y crujiente. Esta versión particular de las quesadillas destaca por el sabor cremoso y suave del aguacate, complementado por el queso derretido, creando un platillo delicioso y reconfortante.', 
    4.50, 
    20, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.directoalpaladar.com%2Frecetas-de-aperitivos%2Fquesadillas-de-aguacate-y-queso-emmental-receta&psig=AOvVaw33nU2GCuHRgfvu6H3yAtiX&ust=1729203498006000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPjJjJn3k4kDFQAAAAAdAAAAABAE'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Quesadillas'),
    'Quesadilla de queso con champiñones', 
    'Una quesadilla mexicana de queso con champiñones es una variante de las quesadillas tradicionales mexicanas que incorpora champiñones como ingrediente adicional al relleno clásico de queso. Es un plato sabroso y reconfortante que combina la cremosidad del queso derretido con el sabor terroso de los champiñones, todo envuelto en una tortilla de maíz o harina y cocinado hasta que esté dorado y crujiente.', 
    4.00, 
    10, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Funareceta.com%2Fquesadilla-de-champinones%2F&psig=AOvVaw1l-h8AP0wBFbhOcLbJVqEI&ust=1729203534019000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCIDyoK73k4kDFQAAAAAdAAAAABAE'
);


INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Quesadillas'),
    'Quesadilla de queso con frijoles', 
    'Una quesadilla de queso con frijoles es una variante de las quesadillas tradicionales mexicanas que incorpora frijoles refritos como ingrediente adicional al relleno clásico de queso. Es un plato sabroso y reconfortante que combina la cremosidad del queso derretido con la textura y el sabor robusto de los frijoles, todo envuelto en una tortilla de maíz o harina y cocinado hasta que esté dorado y crujiente.', 
    3.50, 
    10, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.directoalpaladar.com%2Frecetas-de-aperitivos%2Fquesadillas-de-frijoles-refritos-y-queso-fundente-receta&psig=AOvVaw2HQOGR0nP-27gPkWXKY2_S&ust=1729203575853000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOiht773k4kDFQAAAAAdAAAAABAE'
);


INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Quesadillas'),
    'Quesadilla de jamon y queso', 
    'Las quesadillas de jamón y queso de cabra son una variante gourmet de las tradicionales quesadillas mexicanas, que combinan el sabor salado y ahumado del jamón con la cremosidad y el sabor distintivo del queso de cabra. Es un plato sabroso y sofisticado que combina ingredientes de alta calidad para crear una experiencia culinaria única.', 
    3.00, 
    10, 
    'https://www.google.com/url?sa=i&url=https%3A%2F%2Fekilu.com%2Fes%2Freceta%2Fquesadillas-de-jamon-y-queso-con-albahaca&psig=AOvVaw3ce0nxClgTJn6_EJGi4flF&ust=1729203599762000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCJC32cb3k4kDFQAAAAAdAAAAABAJ'
);





COMMIT;

-- ------------------------------------------------------------------------------
-- ------------------------------- CONSULTAS SELECT -----------------------------
-- ------------------------------------------------------------------------------

-- Seleccionar todos los registros de Menus_PTC
SELECT * FROM Menus_PTC;

-- Inner Join entre PEDIDOS_PTC, Clientes_PTC y Empleados_PTC
SELECT 
    PEDIDOS_PTC.id_pedido,
    Clientes_PTC.nombre_clie AS nombre_cliente,
    Empleados_PTC.nom_empleado || ' ' || Empleados_PTC.ape_empleado AS nombre_empleado,
    PEDIDOS_PTC.fecha_hora_pedido,
    PEDIDOS_PTC.total_pedido,
    PEDIDOS_PTC.Estado,
    PEDIDOS_PTC.Tipo_Pago,
    PEDIDOS_PTC.Tipo_Pedido
FROM PEDIDOS_PTC
INNER JOIN Clientes_PTC ON PEDIDOS_PTC.id_cliente = Clientes_PTC.id_cliente
INNER JOIN Empleados_PTC ON PEDIDOS_PTC.id_empleado = Empleados_PTC.id_empleado;

-- Inner Join entre Menus_PTC y Detalle_Productos_PTC
SELECT 
    Menus_PTC.id_menu,
    Menus_PTC.categoria,
    Detalle_Productos_PTC.id_producto,
    Detalle_Productos_PTC.producto, 
    Detalle_Productos_PTC.descripcion, 
    Detalle_Productos_PTC.precioventa,
    Detalle_Productos_PTC.stock,
    Menus_PTC.imagen_categoria,
    Detalle_Productos_PTC.imagen_comida 
FROM Menus_PTC
INNER JOIN Detalle_Productos_PTC ON Menus_PTC.id_menu = Detalle_Productos_PTC.id_menu;

-- Inner Join entre Menus_PTC y Clientes_PTC (Nota: Parece haber una relación incorrecta aquí)
SELECT 
    Menus_PTC.*,
    Clientes_PTC.nombre_clie,
    Clientes_PTC.imagen_clientes
FROM 
    Menus_PTC
INNER JOIN 
    Clientes_PTC ON Menus_PTC.id_menu = Clientes_PTC.id_cliente;

-- Inner Join entre Detalle_Productos_PTC y Menus_PTC
SELECT 
    Detalle_Productos_PTC.id_producto,
    Menus_PTC.categoria AS nombre_categoria,
    Detalle_Productos_PTC.producto,
    Detalle_Productos_PTC.descripcion,
    Detalle_Productos_PTC.precioventa,
    Detalle_Productos_PTC.stock,
    Detalle_Productos_PTC.imagen_comida
FROM Detalle_Productos_PTC
INNER JOIN Menus_PTC ON Detalle_Productos_PTC.id_menu = Menus_PTC.id_menu;

-- Inner Join entre DetallePedidos_PTC, PEDIDOS_PTC y Detalle_Productos_PTC
SELECT 
    DetallePedidos_PTC.id_detapedido,
    PEDIDOS_PTC.id_pedido,
    Detalle_Productos_PTC.producto AS nombre_producto,
    DetallePedidos_PTC.cantidad,
    DetallePedidos_PTC.precio
FROM DetallePedidos_PTC
INNER JOIN PEDIDOS_PTC ON DetallePedidos_PTC.id_pedido = PEDIDOS_PTC.id_pedido
INNER JOIN Detalle_Productos_PTC ON DetallePedidos_PTC.id_producto = Detalle_Productos_PTC.id_producto;

-- Inner Join entre PermisosEmpleado_PTC, Empleados_PTC y Permisos_PTC
SELECT 
    PermisosEmpleado_PTC.id_permisoempl,
    Empleados_PTC.nom_empleado || ' ' || Empleados_PTC.ape_empleado AS nombre_empleado,
    Permisos_PTC.permiso AS codigo_permiso,
    Permisos_PTC.motivopermiso,
    PermisosEmpleado_PTC.habilitado
FROM PermisosEmpleado_PTC
INNER JOIN Empleados_PTC ON PermisosEmpleado_PTC.id_empleado = Empleados_PTC.id_empleado
INNER JOIN Permisos_PTC ON PermisosEmpleado_PTC.id_permiso = Permisos_PTC.id_permiso;

-- Inner Join entre DetaMovInventario_PTC, MovsInventario_PTC y Detalle_Productos_PTC
SELECT 
    DetaMovInventario_PTC.id_detamov,
    MovsInventario_PTC.mov_no,
    MovsInventario_PTC.fechahoramov,
    MovsInventario_PTC.concepto,
    Detalle_Productos_PTC.producto AS nombre_producto,
    DetaMovInventario_PTC.cantidad
FROM DetaMovInventario_PTC
INNER JOIN MovsInventario_PTC ON DetaMovInventario_PTC.id_movinv = MovsInventario_PTC.id_movinv
INNER JOIN Detalle_Productos_PTC ON DetaMovInventario_PTC.id_producto = Detalle_Productos_PTC.id_producto;

-- ------------------------------------------------------------------------------
-- ------------------------------- CREACIÓN DE PROCEDURES ----------------------
-- ------------------------------------------------------------------------------

-- Procedimiento para Insertar Pedido
CREATE OR REPLACE PROCEDURE InsertarPedido(
    p_id_cliente NUMBER,
    p_id_empleado NUMBER,
    p_fecha_hora_pedido DATE,
    p_total_pedido NUMBER,
    p_estado VARCHAR2,
    p_tipo_pago VARCHAR2,
    p_tipo_pedido VARCHAR2
) AS
BEGIN
    INSERT INTO PEDIDOS_PTC (
        id_pedido, 
        id_cliente, 
        id_empleado, 
        fecha_hora_pedido, 
        total_pedido, 
        Estado, 
        Tipo_Pago, 
        Tipo_Pedido
    )
    VALUES (
        pedidos_seq.NEXTVAL, 
        p_id_cliente, 
        p_id_empleado, 
        p_fecha_hora_pedido, 
        p_total_pedido, 
        p_estado, 
        p_tipo_pago, 
        p_tipo_pedido
    );
    
    COMMIT;
END;
/
 
-- Procedimiento para Eliminar Pedido
CREATE OR REPLACE PROCEDURE EliminarPedido(
    p_id_pedido NUMBER
) AS
BEGIN
    DELETE FROM PEDIDOS_PTC WHERE id_pedido = p_id_pedido;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error eliminando el pedido: ' || SQLERRM);
END;
/
 
-- Procedimiento para Consultar Pedidos
CREATE OR REPLACE PROCEDURE ConsultarPedidos IS
BEGIN
    FOR r IN (
        SELECT 
            p.id_pedido,
            c.nombre_clie AS Cliente,
            e.nom_empleado || ' ' || e.ape_empleado AS Empleado,
            p.fecha_hora_pedido,
            p.total_pedido,
            p.Estado,
            p.Tipo_Pago,
            p.Tipo_Pedido
        FROM PEDIDOS_PTC p
        INNER JOIN Clientes_PTC c ON p.id_cliente = c.id_cliente
        INNER JOIN Empleados_PTC e ON p.id_empleado = e.id_empleado
    ) LOOP
        DBMS_OUTPUT.PUT_LINE('Pedido ID: ' || r.id_pedido || ', Cliente: ' || r.Cliente || ', Empleado: ' || r.Empleado);
        DBMS_OUTPUT.PUT_LINE('Fecha/Hora: ' || r.fecha_hora_pedido || ', Total: ' || r.total_pedido);
        DBMS_OUTPUT.PUT_LINE('Estado: ' || r.Estado || ', Tipo de Pago: ' || r.Tipo_Pago || ', Tipo de Pedido: ' || r.Tipo_Pedido);
        DBMS_OUTPUT.PUT_LINE('-------------------------------------------');
    END LOOP;
END;
/
 
-- Procedimiento para Registrar Cambio en Auditoría
CREATE OR REPLACE PROCEDURE RegistrarCambioAuditoria(
    p_id_pedido NUMBER,
    p_usuario_modificacion VARCHAR2,
    p_campo_modificado VARCHAR2,
    p_valor_anterior VARCHAR2,
    p_valor_nuevo VARCHAR2
) AS
BEGIN
    INSERT INTO Auditoria_Pedidos (
        id_auditoria, 
        id_pedido, 
        usuario_modificacion, 
        fecha_modificacion, 
        campo_modificado, 
        valor_anterior, 
        valor_nuevo
    ) VALUES (
        Auditoria_Pedidos_SEQ.NEXTVAL, 
        p_id_pedido, 
        p_usuario_modificacion, 
        SYSDATE, 
        p_campo_modificado, 
        p_valor_anterior, 
        p_valor_nuevo
    );
    
    COMMIT;
END;
/

-- ------------------------------------------------------------------------------
-- ------------------------------- CONSULTA FINAL -------------------------------
-- ------------------------------------------------------------------------------

-- Seleccionar todos los id_menu de Menus_PTC
SELECT id_menu FROM Menus_PTC;
