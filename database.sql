-- Elimino las tablas por si ya existen --
GO
	IF EXISTS (SELECT name FROM sysobjects WHERE name='pokemon')
			DROP TABLE dbo.pokemon
	IF EXISTS (SELECT name FROM sysobjects WHERE name='usuario')
			DROP TABLE dbo.usuario
	IF EXISTS (SELECT name FROM sysobjects WHERE name='habilidad_pokemon')
			DROP TABLE dbo.habilidad_pokemon
	IF EXISTS (SELECT name FROM sysobjects WHERE name='tipo_pokemon')
			DROP TABLE dbo.tipo_pokemon
	IF EXISTS (SELECT name FROM sysobjects WHERE name='evolucion_pokemon')
			DROP TABLE dbo.evolucion_pokemon

GO
-- Creación de Tablas --
CREATE TABLE usuario(
	usuario_nombre NVARCHAR(50) NOT NULL,
	usuario_password NVARCHAR(60) NOT NULL

	PRIMARY KEY(usuario_nombre)
);
CREATE TABLE pokemon(
    pokemon_nombre NVARCHAR(50) NOT NULL,
    pokemon_nivel INT NOT NULL,
	pokemon_duenio NVARCHAR(50)

    PRIMARY KEY(pokemon_nombre),
	FOREIGN KEY(pokemon_duenio) REFERENCES usuario(usuario_nombre)
);
CREATE TABLE habilidad_pokemon(
	habilidad_nombre NVARCHAR(255) NOT NULL,
	nombre_pokemon NVARCHAR(50) NOT NULL,

	PRIMARY KEY(habilidad_nombre, nombre_pokemon),
	FOREIGN KEY(nombre_pokemon) REFERENCES pokemon(pokemon_nombre) 
);
CREATE TABLE tipo_pokemon(
	tipo_pokemon NVARCHAR(255) NOT NULL,
	nombre_pokemon NVARCHAR(50) NOT NULL,

	PRIMARY KEY(tipo_pokemon, nombre_pokemon),
	FOREIGN KEY(nombre_pokemon) REFERENCES pokemon(pokemon_nombre) 
);
CREATE TABLE evolucion_pokemon(
	nombre_evolucion NVARCHAR(50) NOT NULL,
	nombre_pokemon NVARCHAR(50) NOT NULL,

	PRIMARY KEY(nombre_evolucion),
	FOREIGN KEY(nombre_evolucion) REFERENCES pokemon(pokemon_nombre) 
);