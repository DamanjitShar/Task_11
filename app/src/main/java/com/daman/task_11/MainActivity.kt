package com.daman.task_11

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        // Initialize UI components
        nameTextView = findViewById(R.id.nameTextView)
        val updateButton: Button = findViewById(R.id.updateButton)
        val clearButton: Button = findViewById(R.id.clearButton)

        // Load name from SharedPreferences and display it
        loadName()

        // Update Button Click Listener
        updateButton.setOnClickListener {
            showUpdateDialog()
        }

        // Clear Button Click Listener
        clearButton.setOnClickListener {
            clearPreferences()
        }
    }

    private fun loadName() {
        val name = sharedPreferences.getString("name", "Default Name")
        nameTextView.text = "Name: $name"
    }

    private fun showUpdateDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_update_name, null)
        builder.setView(dialogView)

        val nameEditText: EditText = dialogView.findViewById(R.id.nameEditText)

        builder.setPositiveButton("Update") { _, _ ->
            val newName = nameEditText.text.toString()
            saveName(newName)
            loadName()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun saveName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()
    }

    private fun clearPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        loadName()
    }
}
