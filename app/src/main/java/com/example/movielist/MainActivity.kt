package com.example.movielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movieList = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(movieList)
        recyclerView.adapter = movieAdapter

        // Set click listeners
        val addButton = findViewById<TextView>(R.id.addButton)
        val saveButton = findViewById<TextView>(R.id.saveButton)

        addButton.setOnClickListener {
            Log.d("MainActivity", "Add button clicked")
            showNewMovieFragment()
        }

        saveButton.setOnClickListener {
            Log.d("MainActivity", "Save button clicked")
            saveMovieList()
        }

        // Try to read saved file first, if it doesn't exist, read from assets
        val file = File(filesDir, "MOVIELIST.csv")
        if (file.exists()) {
            readFile(file.absolutePath)
        } else {
            readFile("MOVIELIST.csv")
        }
        movieAdapter.notifyDataSetChanged()
    }

    private fun readFile(filePath: String) {
        try {
            val bufferedReader = if (File(filePath).exists()) {
                BufferedReader(FileReader(filePath))
            } else {
                BufferedReader(InputStreamReader(assets.open(filePath)))
            }

            var rawLine: String? = bufferedReader.readLine()
            movieList.clear()

            while (rawLine != null) {
                val holder = rawLine.split(',') as ArrayList<String>
                if (holder.size == 4) {
                    movieList.add(Movie(holder[0], holder[1], holder[2], holder[3]))
                }
                rawLine = bufferedReader.readLine()
            }
            bufferedReader.close()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error reading file: ${e.message}")
        }
    }

    private fun saveMovieList() {
        try {
            val file = File(filesDir, "MOVIELIST.csv")
            val bufferedWriter = BufferedWriter(FileWriter(file))

            for (movie in movieList) {
                val line = "${movie.title},${movie.year},${movie.genre},${movie.rating}\n"
                bufferedWriter.write(line)
                Log.d("MainActivity", "Saved: $line")
            }

            bufferedWriter.close()
            Toast.makeText(this, "Movie list saved", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error saving file: ${e.message}")
            Toast.makeText(this, "Error saving movie list", Toast.LENGTH_SHORT).show()
        }
    }

    fun addMovie(movie: Movie) {
        movieList.add(movie)
        movieAdapter.notifyItemInserted(movieList.size - 1)
        saveMovieList() // Save immediately when adding a new movie
    }

    private fun showNewMovieFragment() {
        val fragment = newMovie()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
            .addToBackStack(null)
            .commit()
    }
}
