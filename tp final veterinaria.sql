create database if not exists veterinaria_2026 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
use veterinaria_2026;

CREATE TABLE cliente (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    domicilio VARCHAR(150) NOT NULL
);

CREATE TABLE veterinario (
    matricula INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100),
    especialidad VARCHAR(100)
);


CREATE TABLE mascota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(50),
    edad INT,
    cliente_dni VARCHAR(20) NOT NULL,
    CONSTRAINT fk_mascota_cliente 
        FOREIGN KEY (cliente_dni) 
        REFERENCES cliente(dni) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);


CREATE TABLE turno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    costo DECIMAL(10, 2) NOT NULL,
    cliente_dni VARCHAR(20) NOT NULL,
    matricula_veterinario INT NOT NULL,
    CONSTRAINT fk_turno_cliente 
        FOREIGN KEY (cliente_dni) 
        REFERENCES cliente(dni) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT fk_turno_veterinario 
        FOREIGN KEY (matricula_veterinario) 
        REFERENCES veterinario(matricula) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);


CREATE TABLE consulta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT NOT NULL,
    matricula_veterinario INT NOT NULL,
    fecha DATETIME NOT NULL,
    diagnostico TEXT,
    tratamiento TEXT,
    receta TEXT,
    CONSTRAINT fk_consulta_mascota 
        FOREIGN KEY (mascota_id) 
        REFERENCES mascota(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT fk_consulta_veterinario 
        FOREIGN KEY (matricula_veterinario) 
        REFERENCES veterinario(matricula) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    cliente_dni VARCHAR(20) NOT NULL,
    monto_total DECIMAL(10, 2) NOT NULL,
    forma_pago VARCHAR(50) NOT NULL, 
    activo BOOLEAN NOT NULL DEFAULT 1, 
    CONSTRAINT fk_factura_cliente 
        FOREIGN KEY (cliente_dni) 
        REFERENCES cliente(dni)
        ON DELETE CASCADE 
        ON UPDATE CASCADE
) ENGINE=InnoDB ;

CREATE TABLE IF NOT EXISTS detalle_facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    factura_id INT NOT NULL,
    concepto VARCHAR(150) NOT NULL, 
    subtotal DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_detalle_factura FOREIGN KEY (factura_id) REFERENCES facturas(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, 
    rol VARCHAR(30) NOT NULL
);

INSERT INTO cliente (dni, nombre, apellido, telefono, email, domicilio) VALUES
('1', 'Carlos', 'Gómez', '1145678901', 'carlos.gomez@email.com', 'Av. Corrientes 1234, CABA'),
('2', 'María', 'Pérez', '1156789012', 'maria.perez@email.com', 'Calle Falsa 456, Buenos Aires'),
('3', 'Juan', 'Rodríguez', '1167890123', 'juan.rodriguez@email.com', 'Mitre 789, Avellaneda'),
('4', 'Ana', 'López', '1178901234', 'ana.lopez@email.com', 'Belgrano 321, Lanús'),
('5', 'Luis', 'Martínez', '1189012345', 'luis.martinez@email.com', 'San Martín 654, Lomas de Zamora'),
('6', 'Sofía', 'Fernández', '1190123456', 'sofia.fernandez@email.com', 'Rivadavia 987, Morón'),
('7', 'Diego', 'González', '1123456789', 'diego.gonzalez@email.com', 'Alberdi 159, Quilmes'),
('8', 'Lucía', 'García', '1134567890', 'lucia.garcia@email.com', 'Sarmiento 753, Vicente López'),
('9', 'Esteban', 'Díaz', '1145123456', 'esteban.diaz@email.com', 'Urquiza 357, San Isidro'),
('10', 'Valeria', 'Benítez', '1156234567', 'valeria.benitez@email.com', 'Pueyrredón 951, La Plata'),
('11', 'Gabriel', 'Romero', '1167345678', 'gabriel.romero@email.com', 'Garay 246, Mar del Plata'),
('12', 'Florencia', 'Sosa', '1178456789', 'florencia.sosa@email.com', 'Córdoba 852, Rosario'),
('13', 'Matías', 'Torres', '1189567890', 'matias.torres@email.com', 'Salta 147, Córdoba'),
('14', 'Camila', 'Álvarez', '1190678901', 'camila.alvarez@email.com', 'Jujuy 369, Mendoza'),
('15', 'Nicolás', 'Ruiz', '1121789012', 'nicolas.ruiz@email.com', 'Mendoza 258, Tucumán');


INSERT INTO veterinario (matricula, nombre, apellido, dni, telefono, email, especialidad) VALUES
(1001, 'Alberto', 'Iglesias', '20123456', '1111223344', 'alberto.iglesias@vet.com', 'Cirugía General'),
(1002, 'Beatriz', 'Méndez', '21234567', '1122334455', 'beatriz.mendez@vet.com', 'Medicina Felina'),
(1003, 'Claudio', 'Navarro', '22345678', '1133445566', 'claudio.navarro@vet.com', 'Traumatología'),
(1004, 'Daniela', 'Paz', '23456789', '1144556677', 'daniela.paz@vet.com', 'Dermatología'),
(1005, 'Eduardo', 'Ríos', '24567890', '1155667788', 'eduardo.rios@vet.com', 'Cardiología'),
(1006, 'Fabiana', 'Blanco', '25678901', '1166778899', 'fabiana.blanco@vet.com', 'Odontología'),
(1007, 'Gonzalo', 'Castro', '26789012', '1177889900', 'gonzalo.castro@vet.com', 'Oftalmología'),
(1008, 'Helena', 'Vargas', '27890123', '1188990011', 'helena.vargas@vet.com', 'Animales Exóticos'),
(1009, 'Ignacio', 'Medina', '28901234', '1199001122', 'ignacio.medina@vet.com', 'Oncología'),
(1010, 'Julieta', 'Acosta', '29012345', '1100112233', 'julieta.acosta@vet.com', 'Neurología'),
(1011, 'Kevin', 'Silva', '30123456', '1112233445', 'kevin.silva@vet.com', 'Etología'),
(1012, 'Lorena', 'Flores', '31234567', '1123344556', 'lorena.flores@vet.com', 'Medicina General'),
(1013, 'Mariano', 'Juárez', '32345678', '1134455667', 'mariano.juarez@vet.com', 'Cuidados Intensivos'),
(1014, 'Natalia', 'Peralta', '33456789', '1145566778', 'natalia.peralta@vet.com', 'Nutrición Animal'),
(1015, 'Oscar', 'Delgado', '34567890', '1156677889', 'oscar.delgado@vet.com', 'Diagnóstico por Imágenes');


INSERT INTO mascota (nombre, especie, raza, edad, cliente_dni) VALUES
('Firulais', 'Canino', 'Mestizo', 3, '1'),
('Michi', 'Felino', 'Siamés', 2, '2'),
('Bobby', 'Canino', 'Golden Retriever', 5, '3'),
('Luna', 'Felino', 'Persa', 4, '4'),
('Thor', 'Canino', 'Ovejero Alemán', 6, '5'),
('Simba', 'Felino', 'Común Europeo', 1, '6'),
('Coco', 'Canino', 'Caniche', 8, '7'),
('Lola', 'Canino', 'Beagle', 3, '8'),
('Nala', 'Felino', 'Angora', 2, '9'),
('Rocky', 'Canino', 'Pitbull', 4, '10'),
('Milo', 'Canino', 'Bulldog Francés', 2, '11'),
('Kira', 'Felino', 'Maine Coon', 5, '12'),
('Zeus', 'Canino', 'Husky Siberiano', 3, '13'),
('Cleo', 'Felino', 'Ragdoll', 1, '14'),
('Pancho', 'Canino', 'Schnauzer', 7, '15');


INSERT INTO turno (fecha, hora, costo, cliente_dni, matricula_veterinario) VALUES
('2026-06-01', '09:00:00', 15000.00, '1', 1001),
('2026-06-01', '10:00:00', 12000.00, '2', 1002),
('2026-06-01', '11:00:00', 18000.00, '3', 1003),
('2026-06-02', '09:30:00', 15000.00, '4', 1004),
('2026-06-02', '10:30:00', 20000.00, '5', 1005),
('2026-06-02', '11:30:00', 12000.00, '6', 1006),
('2026-06-03', '14:00:00', 15000.00, '7', 1007),
('2026-06-03', '15:00:00', 22000.00, '8', 1008),
('2026-06-03', '16:00:00', 25000.00, '9', 1009),
('2026-06-04', '09:00:00', 19000.00, '10', 1010),
('2026-06-04', '10:00:00', 15000.00, '11', 1011),
('2026-06-04', '11:00:00', 15000.00, '12', 1012),
('2026-06-05', '15:00:00', 30000.00, '13', 1013),
('2026-06-05', '16:00:00', 14000.00, '14', 1014),
('2026-06-05', '17:00:00', 16000.00, '15', 1015);


INSERT INTO consulta (mascota_id, matricula_veterinario, fecha, diagnostico, tratamiento, receta) VALUES
(1, 1001, '2026-05-10 09:15:00', 'Infección leve en la piel', 'Aplicar crema tópica cada 12 horas', 'Crema dermoprotectora x 50g'),
(2, 1002, '2026-05-10 10:20:00', 'Otitis leve', 'Limpieza de oídos y gotas óticas', 'Gotas Otosec x 10ml'),
(3, 1003, '2026-05-11 11:10:00', 'Leve renguera en pata trasera', 'Reposo y antiinflamatorios', 'Meloxicam 0.2mg - 5 comprimidos'),
(4, 1004, '2026-05-11 14:00:00', 'Alergia estacional', 'Cambio de dieta y antihistamínico', 'Cetirizina jarabe - 1 frasco'),
(5, 1005, '2026-05-12 09:45:00', 'Control general cardiológico', 'Sin anomalías aparentes, continuar actividad normal', 'Ninguna'),
(6, 1006, '2026-05-12 10:30:00', 'Sarro dental moderado', 'Programar limpieza deultrasonido', 'Enjuague bucal veterinario'),
(7, 1007, '2026-05-13 15:00:00', 'Conjuntivitis leve', 'Aplicación de colirio oftálmico', 'Tobramicina en gotas - 1 unidad'),
(8, 1008, '2026-05-13 16:15:00', 'Parásitos intestinales', 'Desparasitación interna completa', 'Pastilla Drontal Plus x 1'),
(9, 1009, '2026-05-14 11:00:00', 'Control post-operatorio', 'Evolución favorable, retirar puntos en 4 días', 'Curabichera en spray'),
(10, 1010, '2026-05-14 15:30:00', 'Episodio de estrés leve', 'Ambiente tranquilo y suplemento natural', 'Adaptil en comprimidos'),
(11, 1011, '2026-05-15 09:20:00', 'Vacunación anual al día', 'Aplicación de vacuna séxtuple', 'Certificado de vacunación'),
(12, 1012, '2026-05-15 10:40:00', 'Infección estomacal', 'Dieta blanda por 48 horas y probióticos', 'Proplan Fortiflora x 5 sobres'),
(13, 1013, '2026-05-16 12:00:00', 'Trauma leve por caída', 'Reposo absoluto por una semana', 'Tramadol gotas (según peso)'),
(14, 1014, '2026-05-16 16:20:00', 'Sobrepeso detectado', 'Ajuste de porciones en alimento balanceado', 'Plan nutricional estricto'),
(15, 1015, '2026-05-17 11:00:00', 'Control ecográfico de rutina', 'Órganos en condiciones normales', 'Ninguna');

INSERT INTO facturas (id, fecha_hora, cliente_dni, monto_total, forma_pago, activo) VALUES
(1, '2026-06-01 09:30:00', '1', 15000.00, 'Efectivo', 1),
(2, '2026-06-01 10:30:00', '2', 14000.00, 'Tarjeta de Crédito', 1),
(3, '2026-06-01 11:45:00', '3', 18500.00, 'Transferencia', 1),
(4, '2026-06-02 10:00:00', '4', 15000.00, 'Efectivo', 1),
(5, '2026-06-02 11:00:00', '5', 22000.00, 'Débito', 1);


INSERT INTO detalle_facturas (factura_id, concepto, subtotal) VALUES
(1, 'Consulta veterinaria - Turno N° 1', 15000.00),
(2, 'Consulta veterinaria - Turno N° 2', 12000.00),
(2, 'Medicamento Gotas Otosec x 10ml', 2000.00),
(3, 'Consulta veterinaria - Turno N° 3', 18000.00),
(3, 'Venda elástica y gasas', 500.00),
(4, 'Consulta veterinaria - Turno N° 4', 15000.00),
(5, 'Consulta veterinaria - Turno N° 5', 20000.00),
(5, 'Pipeta antipulgas', 2000.00);

INSERT INTO usuarios (nombre_usuario, password, rol) VALUES 
('admin', '1234', 'ADMINISTRADOR'),
('recepcion1', '1234', 'RECEPCIONISTA');
INSERT INTO usuarios (nombre_usuario, password, rol) VALUES ('veterinario1','9012','VETERINARIO');
update usuarios set password= 5678 where nombre_usuario ='recepcion1';