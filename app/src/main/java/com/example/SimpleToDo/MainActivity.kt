package com.example.SimpleToDo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter       // lateinit means its going to be initialized later on

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. Remove item from list
                listOfTasks.removeAt(position)

                // 2. Notify Adapter something has changed
                adapter.notifyDataSetChanged()

                // 3. Saving Items
                saveItems()
            }
        }

        // Populating list of files
        loadItems()


        // finds the recyclerView in layout and assigns it a value in this function
        val recyclerView =  findViewById<RecyclerView>(R.id.recyclerView)
        // Create an adapter passing user data (listOfTasks)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to recycleView to populate items onto holder
        recyclerView.adapter = adapter
        // Sets the layout
        recyclerView.layoutManager = LinearLayoutManager(this)


        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Set up the button so the user can add a task to the list
        // We need to detect the contents of addTaskField and the button will add it to the list
        findViewById<Button>(R.id.button).setOnClickListener{

            // 1. Grab text inputted in @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()
            // 2. Add the string to the list of tasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapater that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size -1)

            // 3. Reset the text field
            inputTextField.setText("")

            // 4. Saving Items
            saveItems()
        }
    }

    // 1. Save the inputted user data
    fun getDataFile(): File {

        // each lin ein the file is going to be a specific task
        return File(filesDir, "data.txt")
    }
    // 2. Save data by reading and from a file


    //3. Create a method to get the file we need

    // 4. Load the items by reading each line in the file
    fun loadItems(){
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch(ioException: IOException) {
            ioException.printStackTrace()
        }
    }
    // 5. Save items by writing them into the file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }catch(ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}
