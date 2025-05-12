package com.example.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class newMovie : Fragment() {
    private var movieList: ArrayList<Movie>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.saveMovieButton).setOnClickListener {
            saveMovie()
        }
    }

    private fun saveMovie() {
        val title = view?.findViewById<EditText>(R.id.titleInput)?.text.toString()
        val year = view?.findViewById<EditText>(R.id.yearInput)?.text.toString()
        val genre = view?.findViewById<EditText>(R.id.genreInput)?.text.toString()
        val rating = view?.findViewById<EditText>(R.id.ratingInput)?.text.toString()

        if (title.isNotEmpty() && year.isNotEmpty() && genre.isNotEmpty() && rating.isNotEmpty()) {
            (activity as? MainActivity)?.addMovie(Movie(title, year, genre, rating))
            parentFragmentManager.popBackStack()
        } else {
            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = newMovie()
    }
}
