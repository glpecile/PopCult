# Proyectos de Aplicaciones Web

![logo](frontend/src/images/PopCultCompleteLogo.webp)

Repositorio del TPE **72.38 - Proyecto de Aplicaciones Web** realizado a lo largo de 2021 segundo cuatrimestre.

## Autores

- [Julián Arce](https://github.com/juarce), 60509
- [Roberto Catalán](https://github.com/rcatalan98), 59174
- [Paula Domingues](https://github.com/pdomins), 60148
- [Gian Luca Pecile](https://github.com/glpecile), 59235

## Prerequisitos
* La versión de **Java** utilizada es `1.8.0_77`.
* La versión usada de **Tomcat** es `7.0.76`.

## Compilación

La compilación se realiza con el siguiente comando:

```bash
mvn clean package
```

Las tablas de la base de datos serán creadas automáticamente al inicar la aplicación.
Además, se deben ejecutar los siguientes scripts de sql, en el orden especificado, para popular la base de datos por única vez:

```bash
genre.sql
media.sql
users.sql
lists.sql
sequence.sql
```

Los scripts se encuentran disponibles en [`/persistence/src/main/resources`](persistence/src/main/resources).

## Credenciales de acceso
### Emails
- Admin:
    - popcult.paw@gmail.com
    - 18bf48221da84df80dbe0580a68cf064
- Mod:
    - popcult.mod@gmail.com
    - 5f6ccd90e2b0932d1fc83c3b1638dbe2
- User:
    - popcult.user@gmail.com
    - b1ca0f521dcdfdefc4581207b5581565

### Usuarios registrados
- Admin:
    - PopCult
    - 123456789
- Mod:
    - PopMod
    - 123456789
- User:
    - PopUser
    - 123456789

## Documentación
En el siguiente [link](https://docs.google.com/spreadsheets/d/12-d4w7wpwGuRHetUvtA7HINCAAQFAsUD5CVlg7ucaQ8/edit?usp=sharing) 
se puede encontrar la documentación de los endpoints de la API.