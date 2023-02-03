package com.example.fundo.view

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemServiceName
import com.example.fundo.R
import com.example.fundo.databinding.FragmentAlarmBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar


class AlarmFragment : Fragment() {

    lateinit var binding : FragmentAlarmBinding
    private lateinit var picker : MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        calendar = Calendar.getInstance()

        createNotificationChannel()

        binding.selectTimebtn.setOnClickListener {

            showTimePicker()

        }
        binding.setAlarmbtn.setOnClickListener {

            alarmManager = requireActivity().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
            val intent = Intent(requireContext(),AlarmReceiver::class.java)

            pendingIntent = PendingIntent.getBroadcast(requireContext(),0,intent,0)

            alarmManager.setRepeating(

                AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,pendingIntent

            )
            Toast.makeText(requireContext(),"Alarm set Successfully",Toast.LENGTH_SHORT).show()

        }
        binding.cancelbtn.setOnClickListener {

            cancelAlarm()

        }

        return binding.root
    }

    private fun cancelAlarm() {

        alarmManager = requireActivity().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(),AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(requireContext(),0,intent,0)

        alarmManager.cancel(pendingIntent)
        Toast.makeText(requireContext(),"Alarm Cancelled",Toast.LENGTH_SHORT).show()

    }

    private fun setAlarm() {
//        alarmManager = requireActivity().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(requireContext(),AlarmReceiver::class.java)
//
//        pendingIntent = PendingIntent.getBroadcast(requireContext(),0,intent,0)
//
//        alarmManager.setRepeating(
//
//            AlarmManager.RTC_WAKEUP,calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,pendingIntent
//
//        )
//        Toast.makeText(requireContext(),"Alarm set Successfully",Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun showTimePicker() {

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Alarm Time")
            .build()

        picker.show( (context as AppCompatActivity).supportFragmentManager,"FunDo")
        picker.addOnNegativeButtonClickListener {

            if(picker.hour > 12){
                binding.selectedTime.text =
                    String.format("%02d",picker.hour - 12) +" : " + String.format("%02d",picker.minute) + "PM"
            }else{
                binding.selectedTime.text =
                String.format("%02d",picker.hour) +" : " + String.format("%02d",picker.minute) + "AM"
            }

            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

        }

    }

    private fun createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name : CharSequence = "fundoReminder"
            val description = "Fun Do Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("FunDo",name,importance)
            channel.description = description
            val notificationManager = getSystemService(requireContext(),NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}