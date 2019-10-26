# Simulador del Scheduler de Linux

Autores:

* José Morán (14-10714@usb.ve)
* Aurivan Castro (14-10205@usb.ve)
* Sandra Vera (14-11130@usb.ve)

## Resumen

Este proyecto consistió en realizar una aproximación a la implementación del scheduler de Linux con el fin de entender mejor su funcionamiento. Para ello se pretendió simular un conjunto de CPU que ejecutan procesos que pueden tener operaciones de E/S, que se programan de acuerdo a la política de Complete Fair Scheduling usando árboles rojo-negro, sincronizándose a través de un reloj global.

## Idea

La idea para la implementación consiste en una serie de hilos que ejecutan una simulación de las funciones de programación de tareas del CPU, sincronizados mediante un reloj global correspondiente a la clase `Clock`. Las tareas o estructuras `Task` se inicializan de un archivo en formato json, que se leen con un parser y son cargados por un hilo a la tabla de procesos, identificada por la clase `ProcessTable`, a la cual accederán los hilos CPU en el caso de que necesiten información sobre los procesos, además de acceder al árbol de cada CPU para añadir los nuevos procesos. Por su parte cada CPU lleva un árbol rojo-negro y una estructura `Runqueue` con la información de los procesos y que maneja la parte de agregar, terminar, dormir y despertar tareas, simular cambios de contexto y hacer cálculos de la carga del CPU para el balanceo de cargas entre CPUs. La clase `Runqueue` hace uso de la estructura `PrioArray`, que consiste nuestra adaptación de la lista de prioridad del kernel de Linux para scheduling.

En cuanto al balanceo de cargas, la clase `Runqueue` posee una lista enlazada destinada a este balanceo, el cual se realiza de acuerdo a la carga de los distintos CPU. La idea es que un hilo monitoree las cargas de los CPU y realice las llamadas a las funciones pertinentes, de manera que el CPU pueda desencolar los procesos que se migrarán.

La sincronización se da en la tabla de procesos y en los árboles rojo-negro, al momento de insertar, elegir procesos y eliminarlos de las tablas y del árbol de programación de tareas.

## Detalles de Implementación

### Clase `Task`

Contiene todos los atributos comunes de los procesos:

* PID
* Estado
* CPU que lo ejecuta
* Prioridad dinámica y estática
* Apuntador a la estructura `PrioArray` que lo contiene
* Time slice del proceso
* Tiempo durante el cual se ha ejecutado
* Tiempo total de ejecución del proceso

### Clase `ProcessTable`

Implementa la tabla de procesos como un HashMap de pares <PID, Task>.

### Clase `PrioArray`

Esta clase implementa la cola de prioridad que se usa para clasificar los procesos. Sus atributos son:

* La cantidad de procesos activos en la cola
* Un arreglo con 140 listas enlazadas que contienen los procesos clasificados por prioridad
* Un bitmap que indica si la lista enlazada en una posición específica del arreglo está vacía o no

### Clase `Runqueue`

Esta estructura de datos se usa para llevar el control de los procesos en ejecución del CPU. Maneja:

* Cálculo de carga del CPU de acuerdo a los procesos activos, expirados y dormidos
* Agregar y eliminar procesos a las colas `PrioArray` de procesos activos y expirados
* Cambio de contexto y terminación de procesos
* Intercambio de las colas cuando todos los procesos pasan a la cola de expirados
* Levantar y dormir procesos

### Clase `CPU`

Es la estructura principal para los hilos que simularán ser CPU. Sus atributos son:

* El árbol rojo-negro para scheduling
* El runqueue
* La referencia al reloj global
* El quantum del CPU
* Una variable booleana que indica si se necesita hacer rescheduling
* Una referencia al HashMap que representa los dispositivos de E/S, donde la clave es el id del dispositivo y el valor es una lista enlazada de `Task`

Además contiene las funciones para realizar el scheduling y levantar y dormir procesos.

### Clase `RbTree`

Implementa un árbol rojo-negro donde los nodos representan a los procesos y poseen los siguientes atributos:

* PID del proceso
* Prioridad del proceso

La clave del nodo para su inserción en el árbol es la prioridad.

### Clase `Clock`

Consiste en un contador que se aumenta de acuerdo a un parámetro de entrada `tick`, el cual indica cúanto tiempo dura cada tick.

## Pruebas

Las pruebas se pueden ejecutar escribiendo el comando `make` en la terminal desde la carpeta del proyecto. Las pruebas actuales corresponden a la funcionalidad de `Runqueue` y `PrioArray`, sin embargo al descomentar las líneas de código del archivo `Test.java` se ejecutan las pruebas de las funcionalidades desarrolladas hasta el momento.

## Sobre la Interfaz


## Conclusiones 

