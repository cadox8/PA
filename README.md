# PA - ProjectAlpha [![Build Status](https://travis-ci.com/cadox8/PA.svg?token=my8wXPsnzgtSyTetfaip&branch=master)](https://travis-ci.com/cadox8/PA)

ProjectAlpha fue un servidor de Minecraft con temática de Minijuegos y Survival que cerró por falta de usuarios al empezar las clases.

## Requisitos

  - Java 8.
  - Maven 3.
  - Una base de datos MySQL para almacenar los datos de los usuarios. Nosotros usábamos [MySQL](https://www.mysql.com).
  
## Compilación

Instala dependencias, inicia módulos y compila.

```sh
$ mvn clean install
```

Y carga el esquema de la base de datos en tu servidor.

Para su ejecución
* Cambia los datos de acceso a tu DB en PA-Core
* Pon tantos servidores cómo juegos quieras
* Arranca el servidor y reza...

## Funcionamiento
  - Funciona con la API de Spigot, usando [SpigotMC](https://spigotmc.org)
  - Está preparado y testeado para usarse con [BungeeCoord](https://github.com/SpigotMC/BungeeCord).
  - Compartiamos versiones de 1.8 y de 1.12, por lo que era necesario [ViaVersion](https://github.com/MylesIsCool/ViaVersion).*
  - Con la ayuda de Bungee, cada plugin ejecuta UNA partida del juego. Necesitarás N servidores para N arenas.
  - Usa third-party para versiones antiguas de Minecraft tales como las de [InventiveTalent](https://github.com/InventivetalentDev).
  - La estructura del código, así algunas clases (Sistema de rangos, registro de comandos, etc.) es propiedad de @Cadiducho y están sacadas de su proyecto [FEM](https://github.com/cadiducho/FEM).

'*' No me gusta usar versiones de Minecraft tan desactualizadas y en un principio todo el servidor estaba en la 1.8, pero la gente se quejó y lo tuvimos que cambiar.

## Características
  - Sistema de rangos integrado. Es básico, si un comando es de rango 3 y tú eres 3 o más te dejará.
  - Un plugin que se tiene que ejecutar en todos los servidores para el manejo de su API interna y control de usuarios (PA-Core)
  - *Diferentes modos de juego*
    - Survival: Un survival modificado ligeramente por nosotros para incluir todas las funcionalidades que más gustaban a la gente, manteniendo la esencia del puro survival vanilla. Tambien incluye un PvPManager para controlar el PvP y un plugin que venía a simular el mod [SafariNet](https://ftbwiki.org/Safari_Net).
    - Creativo: Modo de juego para construir sin límites. Para administrar la mayor parte del juego, usabamos [PlotSquared](https://www.spigotmc.org/resources/plotsquared.1177/).
    - RageGames: Modo de juego de todos contra todos donde el objetivo era obtener la mayor cantidad de puntos matando a los otros jugadores con diferentes armas. Si te mataban, perdias algunos puntos.
    - NexusSiege: Modo de juego que estaba en construcción (versión muy temprana) cuyo objetivo era destruir el nexo del equipo contrario.
    - TOA: El modo de juego que nunca salía. Su objetivo era descender por una torre matando mobs y enfrentándose a bosses. Debías jugar en equipo para pasarse ciertas zonas y cada jugador tenía una clase distinta de las cuatro disponibles.
  - Sistema de contraseñas propio que guarda los datos en una DB.
  - *NO* tiene sistema de baneos. Recomiendo usar [AdvancedBan](https://www.spigotmc.org/resources/advancedban.8695/).
  - *NO* tiene el protocol-hack integrado. Por favor, actualizad a la última versión de Minecraft... o en su defecto usad [ViaVersion](https://github.com/MylesIsCool/ViaVersion).
  - *NO* contiene los mapas de los juegos. No fueron nuestro trabajo. Derechos reservados a nuestros MapMakers, SrJohn y Rahu8.

## Bugs
* **RageGames**: En ciertos mapas puede crashear en el 2 en la cuenta atrás pero el juego sigue funcionando.
* **RageGames**: Puedes llegar a morir(?)
* **SafariNet**: Duplicado de huevo al ponerlo con plugins como McMMO.
* **Survival**: Al usar el comando /loteria y poner un número grande (E.j. 99) puede llegar a congelar el servidor mientras todos los números son generados.
* **Survival**: Los bocatas puedes llegar a no darte los nuevos efectos.
* Alguno olvidado o que surge en de cosas no testeadas.

## ToDo
 - Mejorar el código internamente... la idea era hacerlo todo objetos, incluso las fases de los juegos
 - Terminar NexusSiege y TOA.
 - Unificar la estructura del código.
 - Añadir los Logros al juego.
 - Terminar los cosméticos.
 - Añadir soporte de caballos y aldeanos.
 - Añadir Casino.
 - Muchos más cambios que se han ido olvidando.

## Licencias

FEM fue desarrollado con la ayuda y uso de:
* [BungeeCoord](https://github.com/SpigotMC/BungeeCord) - [BSD 3-clause](https://github.com/SpigotMC/BungeeCord/blob/master/LICENSE) - The Spigot Team
* [Bukkit/Spigot](https://hub.spigotmc.org/stash/projects/SPIGOT) - [GPL](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/browse/LICENCE.txt) - The Bukkit and Spigot Team
* [ParticleAPI](https://github.com/InventivetalentDev/ParticleAPI) - ¿MIT? - [InventiveTalent](https://github.com/InventivetalentDev)
* [BossBarAPI](https://github.com/InventivetalentDev/BossBarAPI) - ¿MIT? - [InventiveTalent](https://github.com/InventivetalentDev)
* [ReflectionHelper](https://github.com/InventivetalentDev/ReflectionHelper) - [MIT](https://github.com/InventivetalentDev/ReflectionHelper/blob/master/LICENSE) - [InventiveTalent](https://github.com/InventivetalentDev)
* [Lombok](https://projectlombok.org/) - [MIT](https://opensource.org/licenses/mit-license.php) - The Project Lombok Authors
* [FEM](https://github.com/cadiducho/FEM) - [MIT](https://github.com/Cadiducho/FEM/blob/develop/LICENSE) - [Cadiducho](https://twitter.com/Cadiducho) y [Cadox8](https://twitter.com/cadox8)

*ProjectAlpha (PA)* es liberado bajo una [Licencia Apache](https://github.com/cadox8/PA/blob/master/LICENSE.md)

## Comentarios de los desarrolladores

> **Cadox8**:<br>
> Para mi, trabajar en ProjectAlpha tanto tiempo como el que ya llevo aquí ha sido genial, basicamente por toda la gente nueva a la que he conocido. Espero que a alguien le sirva este código, bien para uso privado o para continuar con el proyecto y estaríamos encantados de saberlo.<br>
> Muchas partes del código pueden estar mejor optimizadas y pueden ser muy mejorables. Lo primero, perdón por tenerlo así, pero nos centramos primero en que todo funcionara.

# Contacto
* [Cadox](https://github.com/cadox8) - Desarrollador principal - cadox8@gmail.com - [Web](http://cadox8.me) - [@cadox8](https://twitter.com/cadox8)
* [Wikijito7](https://twitter.com/Wiky3567) - Desarrollador principal
