package com.icodelt.appgiroscopio

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var gyroSensor: Sensor
    private lateinit var textView: TextView

    private var currentRotationX = 0f
    private var currentRotationY = 0f
    private var currentRotationZ = 0f

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!!
        textView = findViewById(R.id.textView)

        if (gyroSensor == null) {
            textView.text = "Giroscópio não disponível no dispositivo"
        }

        val gyroListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                // Ajustar a rotação atual com base nas mudanças nos valores do giroscópio
                currentRotationX -= event.values[0]
                currentRotationY -= event.values[1]
                currentRotationZ -= event.values[2]

                // Atualizar a rotação do TextView
                textView.rotationX = currentRotationX
                textView.rotationY = currentRotationY
                textView.rotation = currentRotationZ
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Não é necessário fazer nada aqui
            }
        }

        sensorManager.registerListener(gyroListener, gyroSensor, SensorManager.SENSOR_DELAY_GAME)
    }
}
