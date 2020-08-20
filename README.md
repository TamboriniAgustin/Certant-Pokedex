# Pokedex

## Entorno utilizado

* IntelliJ (y Maven)

## Base de datos utilizada

* SQL Server (conexión local)

## ¿Como configurar la base de datos para adaptarla a tu PC?

* Se deberá ejecutar la querie "database.sql" en SQL server

* Se ha seguido el siguiente tutorial para conectarla a java: https://la.mathworks.com/help/database/ug/microsoft-sql-server-jdbc-windows.html

* Habrá que modificar el constructor de la clase 'BaseDeDatos': connectionUrl = "jdbc:sqlserver://DIRECCION_LOCAL_DE_LA_BASE_DE_DATOS;database=Pokedex;user=USUARIO_LOCAL;password=PASSWORD_ESTABLECIDA"; 
