package com.dineshprabha.batteryinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dineshprabha.batteryinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        registerReceiver()

    }

    private fun registerReceiver() {
        registerReceiver(batteryInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private var batteryInfoReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {

            val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            val batteryIsCharging = intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
            val batteryTemperature = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)?.div(10)
            val batteryVoltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
            val batteryTechnology = intent?.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, 0)


            binding.apply {

                batteryLevel?.let {
                    batteryProgress.setProgress(100-it)
                    tvBatteryPercent.text = "$batteryLevel"

                }

                batteryIsCharging.let {

                    if (batteryIsCharging == 0) tvPlugStateValue.text = "plug out"
                    else tvPlugStateValue.text = "plug in"
                }

                batteryVoltage.let {

                    tvVoltageValue.text = batteryVoltage.toString() + " V"


                }

                batteryTemperature.let {

                    tvTempValue.text = "$batteryTemperature"
                }

                batteryTechnology.let {

                    tvTechValue.text = batteryTechnology.toString()
                }
            }

        }
    }
}