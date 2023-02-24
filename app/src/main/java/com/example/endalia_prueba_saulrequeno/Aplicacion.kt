package com.example.endalia_prueba_saulrequeno
import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Aplicacion
 *
 * @constructor Create empty Aplicacion
 */
@HiltAndroidApp
class Aplicacion : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}