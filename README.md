# Simulador del Scheduler de Linux

Autores:

* José Morán (@usb.ve)
* Aurivan Castro (14-10205@usb.ve)
* Sandra Vera (14-11130@usb.ve)

## Resumen

Este proyecto consistió en realizar una aproximación a la implementación del scheduler de Linux con el fin de entender mejor su funcionamiento. Para ello se pretendió simular un conjunto de CPU que ejecutan procesos que pueden tener operaciones de E/S, que se programan de acuerdo a la política de Complete Fair Scheduling usando árboles rojo-negro, sincronizándose a través de un reloj global.

## Idea

La idea para la implementación consiste en una serie de hilos que ejecutan una simulación de las funciones de programación de tareas del CPU, sincronizados mediante un reloj global correspondiente a la clase `Clock`. Las tareas o tasks se inicializan de un archivo en formato json, que se leen con un parser y son cargados por un hilo a la tabla de procesos, identificada por la clase `ProcessTable`, a la cual accederán los hilos CPU en el caso de que necesiten información sobre los procesos, además de acceder al árbol de cada CPU para añadir los nuevos procesos. Por su parte cada CPU lleva un árbol rojo-negro y una estructura `Runqueue` con la información de los procesos y que maneja la parte de agregar, terminar, dormir y despertar tareas, simular cambios de contexto y hacer cálculos de la carga del CPU para el balanceo de cargas entre CPU. La clase `Runqueue` hace uso de la estructura `PrioArray`, que consiste nuestra adaptación de la lista de prioridad del kernel de Linux para scheduling. Ésta contiene un arreglo de 140 listas enlazadas, una por cada prioridad, así como un bitmap que indica si la lista está vacía o no.

En cuanto al balanceo de cargas, la clase `Runqueue` posee una lista enlazada destinada a este balanceo, el cual se realiza de acuerdo a la carga de los distintos CPU. La idea es que un hilo monitoree las cargas de los CPU y realice las llamadas a las funciones pertinentes, de manera que el CPU pueda desencolar los procesos que se migrarán.

La sincronización se da en la tabla de procesos y en los árboles rojo-negro, al momento de insertar, elegir procesos y eliminarlos de las tablas y del árbol de programación de tareas.

## Detalles de Implementación

### Clase `Clock`

Consiste en un contador que se aumenta de acuerdo a un parámetro de entrada `tick`, el cual indica cúanto tiempo dura cada tick.

### Clase `CPU`



## Pruebas

Las pruebas se pueden ejecutar escribiendo el comando `make` en la terminal desde la carpeta del proyecto. Las pruebas actuales corresponden a la funcionalidad de `Runqueue` y `PrioArray`, sin embargo al descomentar las líneas de código del archivo `Test.java` se ejecutan las pruebas de las funcionalidades desarrolladas hasta el momento.

## Sobre la Interfaz


## Conclusiones 

