-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-03-2024 a las 23:49:24
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.2.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_abarroteria_ammc`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_clientes`
--

CREATE TABLE `tbl_clientes` (
  `id` int(10) NOT NULL,
  `nombreCliente` varchar(30) NOT NULL,
  `direccion` varchar(20) NOT NULL,
  `telefono` varchar(18) NOT NULL,
  `estado` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tbl_clientes`
--

INSERT INTO `tbl_clientes` (`id`, `nombreCliente`, `direccion`, `telefono`, `estado`) VALUES
(1, 'Arlington', 'William Hall', '(504)9808-18-46', 'Activo'),
(4, 'Eduard', 'Bendeck Col', '(504)9846-25-14', 'Inactivo'),
(5, 'Tamia', 'Ramirez Reina', '(504)    -  -  ', 'Activo'),
(6, 'Marturi', 'san Jorge', '(504)    -  -  ', 'Activo'),
(7, 'Jann', 'Inva', '(504)9456-85-21', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_inventario`
--

CREATE TABLE `tbl_inventario` (
  `idInventario` int(10) NOT NULL,
  `idProcucto` int(10) NOT NULL,
  `fechaMovimiento` date NOT NULL,
  `tipoMovimiento` varchar(8) NOT NULL,
  `cantidad` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tbl_inventario`
--

INSERT INTO `tbl_inventario` (`idInventario`, `idProcucto`, `fechaMovimiento`, `tipoMovimiento`, `cantidad`) VALUES
(1, 1, '2024-03-07', 'Entrada', 5.00),
(3, 1, '2024-03-07', 'Entrada', 10.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_productos`
--

CREATE TABLE `tbl_productos` (
  `idProcucto` int(10) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `costo` double(7,2) NOT NULL,
  `utilidad` double(7,2) NOT NULL,
  `precio_V` double(7,2) NOT NULL,
  `id_Proveedor` int(10) NOT NULL,
  `proveedor` varchar(30) NOT NULL,
  `stock_minimo` int(10) NOT NULL,
  `stock_maximo` int(10) NOT NULL,
  `estado` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tbl_productos`
--

INSERT INTO `tbl_productos` (`idProcucto`, `descripcion`, `costo`, `utilidad`, `precio_V`, `id_Proveedor`, `proveedor`, `stock_minimo`, `stock_maximo`, `estado`) VALUES
(1, 'Almohada standar CELENA', 50.00, 22.50, 72.50, 1, 'Carlos ', 5, 15, 'Activo'),
(2, 'Almohada premium SCOT', 100.00, 45.00, 145.00, 2, 'Jackeline', 5, 15, 'Activo'),
(3, 'Almohada standar GOLD', 150.00, 67.50, 217.50, 1, 'Carlos ', 6, 15, 'Activo'),
(4, 'Camere AZUL GOLD', 100.00, 45.00, 145.00, 3, 'Diego', 6, 10, 'Activo'),
(5, 'Camere CAFE  GOLD', 100.00, 45.00, 145.00, 3, 'Diego', 6, 10, 'Activo'),
(6, 'Camere NARANJA GOLD', 100.00, 45.00, 145.00, 3, 'Diego', 10, 10, 'Activo'),
(7, 'Camere NARANJA LUX', 150.00, 67.50, 217.50, 2, 'Jackeline', 5, 15, 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_proveedores`
--

CREATE TABLE `tbl_proveedores` (
  `id` int(10) NOT NULL,
  `nombreProveedor` varchar(30) NOT NULL,
  `direccionProveedor` varchar(20) NOT NULL,
  `telefonoProveedor` varchar(18) NOT NULL,
  `estadoProveedor` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tbl_proveedores`
--

INSERT INTO `tbl_proveedores` (`id`, `nombreProveedor`, `direccionProveedor`, `telefonoProveedor`, `estadoProveedor`) VALUES
(1, 'Carlos ', 'San Pedro Sula', '(504)6222-33-55', 'Activo'),
(2, 'Jackeline', 'Choluteca', '(504)9856-47-74', 'Activo'),
(3, 'Diego', 'Choloma', '(504)9965-45-12', 'Activo'),
(4, 'Asael', 'Santa Rita y', '(504)9966-58-58', 'Inactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tbl_ventas`
--

CREATE TABLE `tbl_ventas` (
  `IdVentas` int(11) NOT NULL,
  `NumeroSerie` varchar(12) NOT NULL,
  `IdCliente` int(11) NOT NULL,
  `FechaVenta` varchar(12) NOT NULL,
  `Monto` double NOT NULL,
  `Estado` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tbl_ventas`
--

INSERT INTO `tbl_ventas` (`IdVentas`, `NumeroSerie`, `IdCliente`, `FechaVenta`, `Monto`, `Estado`) VALUES
(1, '667', 1, '2024-03-13', 57.5, 'Aprobado'),
(2, '82373197', 1, '2024-03-13', 720, 'Aprobado'),
(3, '27673535', 1, '2024-03-13', 57.5, 'Aprobado'),
(4, '45416827', 4, '2024-03-13', 230, 'Aprobado'),
(5, '21822449', 4, '2024-03-13', 366.93999999999994, 'Aprobado'),
(6, '27181602', 1, '2024-03-13', 84.96, 'Aprobado');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tbl_clientes`
--
ALTER TABLE `tbl_clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tbl_inventario`
--
ALTER TABLE `tbl_inventario`
  ADD PRIMARY KEY (`idInventario`),
  ADD KEY `idProducto` (`idProcucto`);

--
-- Indices de la tabla `tbl_productos`
--
ALTER TABLE `tbl_productos`
  ADD PRIMARY KEY (`idProcucto`),
  ADD KEY `id_Proveedor` (`id_Proveedor`);

--
-- Indices de la tabla `tbl_proveedores`
--
ALTER TABLE `tbl_proveedores`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tbl_ventas`
--
ALTER TABLE `tbl_ventas`
  ADD PRIMARY KEY (`IdVentas`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tbl_clientes`
--
ALTER TABLE `tbl_clientes`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tbl_inventario`
--
ALTER TABLE `tbl_inventario`
  MODIFY `idInventario` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tbl_productos`
--
ALTER TABLE `tbl_productos`
  MODIFY `idProcucto` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tbl_proveedores`
--
ALTER TABLE `tbl_proveedores`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tbl_ventas`
--
ALTER TABLE `tbl_ventas`
  MODIFY `IdVentas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tbl_inventario`
--
ALTER TABLE `tbl_inventario`
  ADD CONSTRAINT `tbl_inventario_ibfk_1` FOREIGN KEY (`idProcucto`) REFERENCES `tbl_productos` (`idProcucto`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Filtros para la tabla `tbl_productos`
--
ALTER TABLE `tbl_productos`
  ADD CONSTRAINT `tbl_productos_ibfk_1` FOREIGN KEY (`id_Proveedor`) REFERENCES `tbl_proveedores` (`id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
