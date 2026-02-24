# 🎮 BossesOriginal

![LibGDX](https://img.shields.io/badge/LibGDX-1.13.1-red.svg)
![Gradle](https://img.shields.io/badge/Gradle-8.13-blue.svg)
![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)
![Platform](https://img.shields.io/badge/Platform-Android%20%7C%20Desktop-green.svg)

Una reliquia de hace una década traída al presente. **BossesOriginal** es un juego de acción/aventura desarrollado originalmente hace 8 años utilizando LibGDX, ahora migrado y optimizado para ejecutarse en dispositivos modernos.

## ✨ Características de la Migración

- **Versión Moderna**: Actualizado de LibGDX 1.9.3 a **1.13.1**.
- **Build System**: Migrado a **Gradle 8.13** con soporte para Java 17+.
- **Desktop**: Actualizado a **LWJGL 3** (soporte para pantallas modernas y mejor rendimiento).
- **Android**: SOPORTE COMPLETO para **Android 14 (SDK 34)**, incluyendo AndroidX y Jetifier.
- **Game Speed Fix**: Implementación de **Frame-rate Independence** (Delta Time). El juego corre a la misma velocidad en pantallas de 60Hz, 90Hz o 120Hz.

## 🚀 Cómo Empezar

### 🖥️ Escritorio (Desktop)
Asegúrate de tener Java 17 o superior instalado.
```bash
./gradlew desktop:run
```

### 📱 Android
1. Abre el proyecto en **Android Studio**.
2. Conecta tu dispositivo con **Depuración USB** activada.
3. Haz clic en **Run**.

---

## 🛠️ Arquitectura del Motor
El motor fue diseñado a medida, destacando:
- **POO Robusta**: Estructura clara de `SpriteObject` -> `Character` -> `GamePlayer`.
- **Event-Driven**: Sistema de señales (`GameSignals`) para lógica desacoplada.
- **TiledMaps**: Integración profunda con mapas `.tmx`.

## 📜 Créditos
Diseñado y desarrollado originalmente por [jlmunix](https://github.com/jlmunix).

---
*Migrado con ❤️ por Antigravity AI.*
