# Torneos Deportivos

## Tareas
1)	Crea un proyecto llamado sesion05torneos en la organización del equipo de prácticas, basado en el template `ualhmis/torneosdeportivos`.

2)	Diseñar e implementar los tests en JUnit 5 para:
- Usar **test parametrizados** cuando sea posible
- Obtener el **100% de cobertura**. 
- Asegúrar de que los test incluyan los **valores límite**. 

3) Implementa los nuevos requisitos descritos abajo

4)	Realizar un pequeño informe con captura de pantalla del resultado de la cobertura (en Eclipse o cualquier otro IDE) y entregarlo en la tarea correspondiente.

## Gestión de Torneos Deportivos 🏆⚽🏀
Desarrollar un sistema para gestionar torneos deportivos, asegurando su correcto funcionamiento mediante pruebas unitarias con JUnit 5.


### Requisitos
- Deporte: Cada torneo pertenece a un deporte específico (fútbol, baloncesto, voleibol, etc.).
- Jugador: Tiene nombre, género (masculino o femenino-), fecha de nacimiento y su categoría se determina según la edad.
- Entrenador: Persona responsable de dirigir un equipo; puede haber un entrenador principal y opcionalmente un segundo entrenador.
- Club: Entidad deportiva con varios equipos en diferentes categorías y modalidades.
- Equipo: 
  - Pertenece a un club.
  - Está asociado a una categoría (infantil, cadete, juvenil, junior, absoluta) y una modalidad (masculina o femenina).
  - Tiene una lista de jugadores y al menos un entrenador.
- Torneo: 
  - Asociado a un deporte y una categoría específica.
  - Solo puede incluir equipos de la misma categoría y modalidad.
  - Puede ser de dos tipos: 
	Liga (todos contra todos).
	Copa (torneo clasificatorio).
- Partido: Dos equipos compiten y se registra el resultado.
- GestorTorneos: Administra la creación de torneos, registro de equipos y gestión de partidos.


Además: 

✅ Un equipo solo puede inscribirse en torneos de su misma categoría y modalidad.

✅ Un torneo no puede aceptar equipos de distintas categorías o modalidades.

✅ No se puede declarar un campeón sin que se hayan jugado partidos.

✅ Un equipo no puede estar registrado en más de un torneo simultáneamente.

✅ Un partido solo puede jugarse entre equipos inscritos en el torneo.

✅ Evitar la inscripción duplicada de equipos en un torneo.

✅ Determinar la categoría de un jugador en base a su edad.

✅ Un equipo debe tener un entrenador principal y opcionalmente un segundo entrenador.

### Código fuente

El código fuente base para comenzar a trabajar está en el repositorio indicado, en  `src/main/java`

Incluye:

✅ Jugadores con género, fecha de nacimiento y categoría según la edad.

✅ Equipos con un entrenador principal y opcionalmente un segundo entrenador.

✅ Torneos con categoría, modalidad y tipo (Liga o Copa).

✅ Reglas de validación para evitar equipos fuera de categoría/modalidad.

✅ Registro de partidos con resultados.

### Tests unitarios

También tienes unos primeros tests unitarios en `src/test/java`. Estas pruebas validan:

1️ Creación de jugadores y cálculo automático de categoría.

2️ Restricciones en los equipos (jugadores de la misma categoría y modalidad).

3️ Registro de equipos en torneos con validación de categoría y modalidad.

4️ Registro de partidos y validación de resultados.

### Nuevos requisitos

#### Gestión de sedes de los torneos:
- Cada torneo debe estar asociado a una sede específica.
- Una sede puede tener múltiples instalaciones deportivas, clasificadas en subtipos como:
    - Campo.
    - Pabellón.
    - Pista.
- Validar que las instalaciones deportivas sean adecuadas para el deporte del torneo.
- Permitir la asignación de partidos a instalaciones específicas dentro de la sede.
- Evitar la asignación de múltiples partidos a la misma instalación en horarios solapados.
- Incluir pruebas unitarias para validar la correcta gestión de sedes e instalaciones.
