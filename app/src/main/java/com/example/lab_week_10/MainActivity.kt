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
import androidx.room.Room
import com.example.lab_week_10.database.Total
import com.example.lab_week_10.database.TotalDatabase
import com.example.lab_week_10.viewmodels.TotalViewModel

class MainActivity : AppCompatActivity() {
    private var total: Int = 0

    // Create an instance of the TotalDatabase
    // by lazy is used to create the database only when it's needed
    private val db by lazy { prepareDatabase() }

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
//        total = 0
//        updateText(viewModel.total)
//        findViewById<Button>(R.id.button_increment).setOnClickListener{
//            val newTotal = viewModel.incrementTotal()
//            updateText(newTotal)
//        }

        // Initialize the value of the total from the database
        initializeValueFromDatabase()
        //prepare the viewmodel
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

//    private fun prepareViewModel(){
//        findViewById<Button>(R.id.button_increment).setOnClickListener{
//            val newTotal = viewModel.incrementTotal()
//            updateText(newTotal)
//        }
//    }
    private fun prepareViewModel(){
        viewModel.total.observe(this, {
            updateText(it)
        })
    findViewById<Button>(R.id.button_increment).setOnClickListener{
        viewModel.incrementTotal()
    }
    }

    // Create and build the TotalDatabase with the name 'total-database'
// allowMainThreadQueries() is used to allow queries to be run on the main thread
    // This is not recommended, but for simplicity it's used here
    private fun prepareDatabase(): TotalDatabase {
        return Room.databaseBuilder(
            applicationContext,
            TotalDatabase::class.java, "total-database"
        ).allowMainThreadQueries().build()
    }
// Initialize the value of the total from the database
// If the database is empty, insert a new Total object with the value of 0
// If the database is not empty, get the value of the total from the database
    private fun initializeValueFromDatabase() {
        val total = db.totalDao().getTotal(ID)
        if (total.isEmpty()) {
            db.totalDao().insert(Total(id = 1, total = 0))
        } else {
            viewModel.setTotal(total.first().total)
        }
    }

    // Update the value of the total in the database
// whenever the activity is paused
// This is done to ensure that the value of the total is always up to date
// even if the app is closed
    override fun onPause() {
        super.onPause()
        db.totalDao().update(Total(ID, viewModel.total.value!!))
    }

    // The ID of the Total object in the database
// For simplicity, we only have one Total object in the database
// So the ID is always 1
    companion object {
        const val ID: Long = 1
    }
}