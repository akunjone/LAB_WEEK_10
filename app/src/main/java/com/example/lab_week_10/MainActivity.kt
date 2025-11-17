package com.example.lab_week_10

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.LiveData
import com.example.lab_week_10.viewmodels.TotalViewModel

class MainActivity : AppCompatActivity() {
    private var total: Int = 0
    private val viewModel by lazy{
        ViewModelProvider(this)[TotalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        total = 0
        updateText(viewModel.total)
        findViewById<Button>(R.id.button_increment).setOnClickListener{
            val newTotal = viewModel.incrementTotal()
            updateText(newTotal)
        }
        prepareViewModel()
    }

//    private fun incrementTotal() {
//        total++
//        updateText(total)
//    }

    private fun updateText(total: Int){
        findViewById<TextView>(R.id.text_total).text =
            getString(R.string.text_total, total)
    }

    private fun prepareViewModel(){
        findViewById<Button>(R.id.button_increment).setOnClickListener{
            val newTotal = viewModel.incrementTotal()
            updateText(newTotal)
        }
    }
}