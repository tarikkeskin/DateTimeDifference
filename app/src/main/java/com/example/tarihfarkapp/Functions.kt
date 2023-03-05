package com.example.tarihfarkapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.widget.EditText
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null, minDate: Date? = null) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            setText("${sdf.format(myCalendar.time)}")
        }

    setOnClickListener {
        DatePickerDialog(
            context, R.style.PickerDialogTheme,datePickerOnDataSetListener,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            minDate?.time?.also { datePicker.minDate = it }
            show()
        }
    }
}

fun EditText.transformIntoTimePicker(context: Context) {
    isFocusableInTouchMode = false
    isClickable = true
    isFocusable = false

    val mcurrentTime: Calendar = Calendar.getInstance()
    val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
    val minute: Int = mcurrentTime.get(Calendar.MINUTE)

    setOnClickListener {
        TimePickerDialog(context,R.style.PickerDialogTheme,
            { _, selectedHour, selectedMinute ->
                setText("$selectedHour:$selectedMinute")
            },
            hour,
            minute,
            true
        ).run {
            show()
        }
    }
}

fun getCurrentDate(): String {
    val myCalendar = Calendar.getInstance()
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return (simpleDateFormat.format(myCalendar.time))
}

fun getCurrentTimeStr(): String {
    val currentTime: Calendar = Calendar.getInstance()
    val hour: Int = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute: Int = currentTime.get(Calendar.MINUTE)
    return "$hour : $minute"
}

fun printDaysBetweenTwoDates(cameraDate:String,currentDate:String): Int {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    val endDateInMilliSeconds = dateFormat.parse(cameraDate)?.time ?: 0
    val startDateInMilliSeconds = dateFormat.parse(currentDate)?.time ?: 0
    return getNumberOfDaysBetweenDates(startDateInMilliSeconds, endDateInMilliSeconds)
}


fun getNumberOfDaysBetweenDates(
    startDateInMilliSeconds: Long,
    endDateInMilliSeconds: Long,
): Int {
    val difference = (endDateInMilliSeconds - startDateInMilliSeconds) / (1000 * 60 * 60 * 24).toDouble()
    val noOfDays = Math.ceil(difference)
    return (noOfDays).toInt()
}


fun addDay(oldDate: String?, numberOfDays: Int): String? {
    var dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val c = Calendar.getInstance()
    try {
        c.time = dateFormat.parse(oldDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    c.add(Calendar.DAY_OF_YEAR, numberOfDays)
    dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val newDate = Date(c.timeInMillis)
    Log.e("Debug",dateFormat.format(newDate))
    return dateFormat.format(newDate)
}

fun calculateTotalMinute(t1:String,t2:String):Int{
    val hour1 = t1.split(":")[0].trim().toInt()
    val min1 = t1.split(":")[1].trim().toInt()
    val hour2 = t2.split(":")[0].trim().toInt()
    val min2 = t2.split(":")[1].trim().toInt()
    val last1 = (hour1*60)+min1
    val last2 = (hour2*60)+min2

    return last1 - last2
}

fun addMinuteToHourString(min:Int,hour:String,date:String):String{
    val hour1 = hour.split(":")[0].trim().toInt()
    val min1 = hour.split(":")[1].trim().toInt()
    val last1 = (hour1*60)+min1
    var result = last1 + (min)
    return if(result<0){
        result += 24 * 60
        "${addDay(date,-1)} - ${result/60}:${result%60}"
    }else if (result>24*60){
        result -= 24 * 60
        "${addDay(date,+1)} - ${result/60}:${result%60}"
    }else{
        "$date - ${result/60}:${result%60}"
    }


}
