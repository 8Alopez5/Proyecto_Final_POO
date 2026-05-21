# Proyecto Final — Programacion Orientada a Objetos

## Threads - Red Social

Simulacion del backend de una red social inspirada en Threads.
Implementado en Java sin interfaz grafica. La interaccion es por consola.

## Integrantes

- Jacobo Ochoa — Modulo 1: Usuarios y Relaciones
- Matias Battistolo — Modulo 2: Contenido e Interacciones
- Sebastian Rios — Modulo 3: Notificaciones, Recomendaciones y Main

## Como ejecutar

1. Clonar el repositorio
2. Abrir en IntelliJ IDEA
3. Marcar la carpeta src como Sources Root
4. Ejecutar Main.java

## Estructura del proyecto

src/
- usuarios: Tipos de usuario del sistema
- relaciones: Relaciones entre usuarios
- contenido: Tipos de publicaciones
- interacciones: Likes, reposts, guardados
- notificaciones: Sistema de notificaciones
- recomendaciones: Algoritmos de recomendacion
- gestores: Logica principal del sistema
- fabrica: Creacion de contenido
- excepciones: Manejo de errores
- utilidades: Reportes y herramientas

## Patrones de diseno utilizados

- Singleton: GestorUsuarios e HistorialOperaciones
- Factory Method: FabricaContenido y ProveedorFabricas
- Observer: GestorNotificaciones
- Strategy: MotorRecomendaciones

## Ramas

- jacobo: Modulo de usuarios
- matias: Modulo de contenido
- sebastian: Modulo de notificaciones y main
- main: Integracion final
