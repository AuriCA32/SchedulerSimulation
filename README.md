# Simulador del Scheduler de Linux

Autores:

* José Morán (@usb.ve)
* Aurivan Castro (14-10205@usb.ve)
* Sandra Vera (14-11130@usb.ve)

## Resumen
Este proyecto consistió en realizar una aproximación a la implementación del scheduler de Linux con el fin de entender mejor su funcionamiento. Para ello se pretendió simular un conjunto de CPU que ejecutan procesos que pueden tener operaciones de E/S, que se programan de acuerdo a la política de Complete Fair Scheduling usando Árboles Rojo-Negro, sincronizándose a través de un reloj global.

## Idea
La idea para la implementación consiste en una serie de hilos que ejecutan una simulación de las funciones de programación de tareas del CPU, sincronizados mediante un reloj global correspondiente a la clase `Clock`. Las tareas o tasks se inicializan de un archivo en formato json, que se leen con un parser y se cargan por un hilo a la tabla de procesos identificada por la clase `ProcessTable`, a la cual accederán los hilos CPU en el caso de que necesiten información sobre los procesos

## Detalles de Implementación

### Clase `Clock`
Consiste en un contador que se aumenta de acuerdo a un parámetro de entrada `tick`, el cual indica cúanto tiempo dura cada tick.

## Sobre la Interfaz


## Conclusiones 

