# Sistema-de-notas
##  Descripción

Esta aplicación es un sistema de consola desarrollado en Java que permite:

- Registrar usuarios
- Iniciar sesión
- Gestionar notas personales
- Guardar toda la información en ficheros de texto
- Mantener persistencia de datos entre ejecuciones

El proyecto utiliza `java.nio.file` para el manejo de archivos y está organizado por paquetes para una mejor estructura y mantenimiento del código.

## Cómo Ejecutar el Proyecto

1. Abrir el proyecto en un entorno como IntelliJ, Eclipse o VS Code.
2. Ejecutar el archivo `Main.java`.
3. Usar el menú que aparece en consola.

No se necesitan configuraciones adicionales.

## Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

- Una carpeta principal llamada `src`, donde está el código fuente.
- Dentro de `src` hay varios paquetes:

### 🔹 Paquete `main`
Contiene la clase `Main`, que es el punto de entrada del programa.

### 🔹 Paquete `service`
Contiene la lógica principal del programa:
- `UsuarioService` → gestión de usuarios (registro e inicio de sesión).
- `NotaService` → gestión de notas (crear, listar, ver, eliminar y buscar).

### 🔹 Paquete `utils`
Contiene clases de utilidades reutilizables:
- `EmailUtils` → sanitización del email.
- `Validaciones` → validaciones básicas de datos.

### 🔹 Paquete `model` 
Contiene las clases de datos del sistema.

---

## 📂 Carpeta de Datos

Fuera del código fuente existe una carpeta llamada `data`, que almacena la información:

- `data/users.txt` → almacena los usuarios registrados.
- `data/usuarios/` → contiene una carpeta por cada usuario.
- Dentro de cada carpeta de usuario se guardan sus notas en archivos individuales.

---

## 💾 Persistencia

Todos los datos se guardan en archivos de texto, por lo que la información permanece almacenada incluso después de cerrar el programa.