# Construcción 2

## Integrantes
- Jarrison Cano [GitHub](https://github.jarrison.dev)


## Tecnologías
- Java
- SpringFramework

---
## Resumen
La materia se baso en 2 módulos:

- El primer módulo consistió en el desarrollo del enunciado por medio de java (**club**). En esta parte definimos las entidades y las funcionalidades de cada entidad (Administrador, Socio, etc...). Se simuló un login y las respectivas validaciones.
- El segundo módulo se basó en la migración de lo desarrollado con java hacia SpringFramework. Tomamos los mismos modelos y funcionalidades del proyecto **club** y lo adaptamos a la estructura que requiere SpringFramework (Creación de modelos, servicios, respositorios y controladores para el consumo de los datos). Adicional hicimos conexión a la DB para persistir la información.

A continuación está el paso a paso de como ejecutar cada uno de los proyectos.

---
## Cómo ejecutar el proyecto Club

1. Clonar el proyecto
```bash
git clone https://github.com/jarrisondev/construccion-2.git
```

2. Acceder a la carpeta **club**
```bash
cd ./club/
```

3. Ejecutar el archivo **App.java**

> [!IMPORTANT]
> Es necesario tener un **java JDK** instalado. [Descargalo aquí](https://www.oracle.com/java/technologies/downloads/)
---

## Cómo ejecutar el proyecto clubApi

1. Acceder a la carpeta **clubApi**
```bash
cd ./clubApi/
```

2. Ejecutar el archivo **ClubapiApplication.java**

> [!TIP]
> Tengo un postman con ejemplos de los distintos endpoints que tiene la app. [Miralo aquí](https://www.postman.com/fdc-dash/pulbic-workspace/collection/owpkjdv/construccion-2?action=share&source=copy-link&creator=0)
