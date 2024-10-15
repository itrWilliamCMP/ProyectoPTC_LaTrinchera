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


CREATE TABLE Empleados_PTC( 
    id_empleado NUMBER PRIMARY KEY, 
    nom_empleado VARCHAR2(20), 
    ape_empleado VARCHAR2(20), 
    usu_empleado VARCHAR2(15), 
    contrasena VARCHAR2(128) 
);
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

-- Insertar datos en Detalle_Productos_PTC
INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tacos'),
    'Tacos al Pastor', 
    'Tacos de carne de cerdo adobada con piña y cebolla', 
    3.00, 
    50, 
    'https://static.vecteezy.com/system/resources/previews/036/498/004/non_2x/ai-generated-delicious-tacos-al-pastor-placed-on-transparent-background-free-png.png'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tacos'),
    'Tacos de pollo', 
    'Tacos de pollo muy buenos', 
    2.50, 
    30, 
    'https://png.pngtree.com/png-clipart/20230423/original/pngtree-chicken-tacos-png-image_9088370.png'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tortas'),
    'Torta de milanesa', 
    'Torta de pan rellena de milanesa de pollo o res, con aguacate y jitomate', 
    4.50, 
    20, 
    'https://www.tortaslacastellana.com/imagenes/menu/la_castellana.png'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Tortas'),
    'Torta de pollo', 
    'Torta de pollo, cubierto con salsa de nuez y granada', 
    10.00, 
    10, 
    'https://store.loscorrales.com.mx/cdn/shop/products/TORTAPECHUGA150px-01_1200x1200.png?v=1590855998'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Burritos'), 
    'Burritos', 
    'Burritos para el burro', 
    3.50, 
    25, 
    'https://png.pngtree.com/png-clipart/20211010/original/pngtree-taco-food-still-life-vegetable-diet-png-image_6850789.png'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Burritos'), 
    'burrito de carne', 
    'Burrito de carne para el burro', 
    2.00, 
    40, 
    'https://static.vecteezy.com/system/resources/previews/042/404/108/non_2x/ai-generated-a-large-burrito-filled-with-meat-and-vegetables-free-png.png'
);

INSERT INTO Detalle_Productos_PTC (id_menu, producto, descripcion, precioventa, stock, imagen_comida) 
VALUES (
    (SELECT id_menu FROM Menus_PTC WHERE categoria = 'Chiles'), 
    'Chile en Nogada', 
    'Chile con jugo de limon y licor de naranja', 
    5.00, 
    15, 
    'https://entodomx.com.mx/wp-content/uploads/2019/09/Chile-Nogada-min.png'
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
