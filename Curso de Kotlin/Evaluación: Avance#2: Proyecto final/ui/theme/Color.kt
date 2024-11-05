/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.inventory.ui.theme

import androidx.compose.ui.graphics.Color

// Colores del tema claro basado en el estilo #fdd18f
val PrimaryLight = Color(0xFFFDD18F)   // Color primario claro
val SecondaryLight = Color(0xFFF8B252) // Color secundario claro
val BackgroundLight = Color(0xFFFFF4E1)    // Fondo claro
val SurfaceLight = Color(0xFFFFFFFF)       // Superficie blanca
val OnSurfaceLight = Color(0xFF3E2723)     // Texto oscuro con más contraste

// Colores del tema oscuro, ajustados para un fondo menos oscuro
val PrimaryDark = Color(0xFF2A2A2A)    // Fondo gris oscuro, pero no tan negro
val SurfaceDark = Color(0xFF424242)    // Superficie gris oscura
val OnPrimaryDark = Color(0xFFFFFFFF)  // Texto blanco puro sobre el fondo
val OnSurfaceDark = Color(0xFFE0E0E0)  // Texto gris claro para mejor contraste sobre superficies oscuras
val ButtonBackgroundDark = Color(0xFF3A3A3A)  // Fondo más claro para los botones en modo oscuro
val ButtonTextDark = Color(0xFFFFFFFF)  // Texto blanco en botones
