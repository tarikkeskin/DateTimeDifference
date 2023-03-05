package com.example.tarihfarkapp

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.tarihfarkapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.eTKameraDate.transformIntoDatePicker(this, "dd/MM/yyyy")
        binding.eTKameraSaat.transformIntoTimePicker(this)

        binding.eTGuncelDate.transformIntoDatePicker(this, "dd/MM/yyyy")
        binding.eTGuncelSaat.transformIntoTimePicker(this)
        binding.eTGuncelDate.setText(getCurrentDate())
        binding.eTGuncelSaat.setText(getCurrentTimeStr())

        binding.eTOlayDate.transformIntoDatePicker(this, "dd/MM/yyyy")
        binding.eTOlaySaat.transformIntoTimePicker(this)

        binding.button.setOnClickListener {
            if( binding.eTKameraDate.text.isNotEmpty() && binding.eTKameraSaat.text.isNotEmpty() &&
                binding.eTGuncelDate.text.isNotEmpty() && binding.eTGuncelSaat.text.isNotEmpty() &&
                binding.eTOlayDate.text.isNotEmpty() && binding.eTOlaySaat.text.isNotEmpty())
            {
                val differenceDay = printDaysBetweenTwoDates(binding.eTKameraDate.text.toString(),binding.eTGuncelDate.text.toString())
                val diffSaat = calculateTotalMinute(binding.eTKameraSaat.text.toString(),binding.eTGuncelSaat.text.toString())
                binding.tVSonuc.text = addMinuteToHourString(diffSaat,binding.eTOlaySaat.text.toString(),addDay(binding.eTOlayDate.text.toString(),differenceDay)!!)
            }
            else{
                Snackbar.make(it,"Lütfen bütün Tarih-Saat Bilgilerini Giriniz.",Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}