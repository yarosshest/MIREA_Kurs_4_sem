package com.example.mirea_kurs_4_sem.timer


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mirea_kurs_4_sem.R
import com.example.mirea_kurs_4_sem.databinding.FragmentTimerBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import java.util.Locale


class TimerFragment : Fragment() {

    private val TAG = "TimerFragment"

    private lateinit var binding: FragmentTimerBinding

    private lateinit var button : FloatingActionButton
    private lateinit var clock : TextView
    private var timeTimer : Long = 30*60000
    private var play : Boolean = false

    private var settings: SharedPreferences? = null

    companion object {
        const val preferences = "preferences"
        const val TIME = "time"
        const val STATUS = "play"
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "1"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentTimerBinding.inflate(layoutInflater)

        clock  = binding.textView
        button = binding.imageButton

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        clock.setOnClickListener(onTimeClicklistener)
        button.setOnClickListener(onPlayClick)

    }



    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //Update GUI
            updateGUI(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        play = requireActivity().getSharedPreferences(
            preferences, Context.MODE_PRIVATE).getBoolean(STATUS, false)
        if (play){
            button.setImageResource(R.drawable.baseline_pause_circle_outline)
        }else{
            button.setImageResource(R.drawable.baseline_play_circle_outline)
        }

        timeTimer = requireActivity().getSharedPreferences(
            preferences, Context.MODE_PRIVATE).getLong(TIME, 30000)
        clock.text = convertSecondsToHMmSs(timeTimer)
        context?.registerReceiver(broadcastReceiver, IntentFilter(TimerService.COUNTDOWN_BR))
    }

    override fun onPause() {
        super.onPause()
        requireActivity().getSharedPreferences(
            preferences, Context.MODE_PRIVATE).edit().putBoolean(STATUS, play).apply()
        requireActivity().getSharedPreferences(
            preferences, Context.MODE_PRIVATE).edit().putLong(
            TIME, timeTimer).apply()
        context?.unregisterReceiver(broadcastReceiver)
    }

    override fun onStop() {
        try {
            context?.unregisterReceiver(broadcastReceiver)
        } catch (e: Exception) {
            // Receiver was probably already
        }
        super.onStop()
    }

    override fun onDestroy() {
        context?.stopService(Intent(context, TimerService::class.java))
        super.onDestroy()
    }




    private fun updateGUI(intent: Intent) {
        if (intent.extras != null) {
            val millisUntilFinished : Long = intent.getLongExtra("countdown", 30000)
            clock.text = convertSecondsToHMmSs(millisUntilFinished)
            timeTimer = millisUntilFinished
        }
    }




    private val onPlayClick = View.OnClickListener { view ->
        when (view.id) {
            R.id.imageButton -> {
                val button : FloatingActionButton = view.findViewById(R.id.imageButton)
                play = if(!play) {
                    val intent = Intent(context, TimerService::class.java)
                    intent.putExtra("time", timeTimer)
                    context?.startService(intent)
                    Log.i(TAG,"Started Service")
                    button.setImageResource(R.drawable.baseline_pause_circle_outline)
                    true
                }else{
                    context?.stopService(Intent(context, TimerService::class.java))
                    button.setImageResource(R.drawable.baseline_play_circle_outline)
                    false
                }
            }
        }
    }


    private val onTimeClicklistener= View.OnClickListener { view ->
        when (view.id) {
            R.id.textView -> {
                play = false
                button.setImageResource(R.drawable.baseline_play_circle_outline)
                context?.stopService(Intent(context, TimerService::class.java))
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setTitleText("Select Appointment time")
                        .setInputMode(INPUT_MODE_CLOCK)
                        .build()
                fragmentManager?.let { picker.show(it, "tag") }

                picker.addOnPositiveButtonClickListener {
                    val newHour: Int = picker.hour
                    val newMinute: Int = picker.minute
                    val time: String = java.lang.String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        newHour,
                        newMinute
                    )
                    clock.text = time
                    timeTimer = (newHour*3600000).toLong()
                    timeTimer += (newMinute*60000).toLong()

                }
            }
        }
    }

    private fun convertSecondsToHMmSs(milliseconds: Long): String {
        val s = (milliseconds/1000) % 60
        val m = milliseconds / 60000  % 60
        val h = milliseconds / 3600000  % 24
        return "%d:%02d:%02d".format(h, m, s)
    }


}