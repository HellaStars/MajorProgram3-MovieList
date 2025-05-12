package com.example.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.Movie
import com.example.movielist.R

class MovieAdapter(private val movieList: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {    // This extension is crucial

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val yearTextView: TextView = itemView.findViewById(R.id.yearTextView)
        val genreTextView: TextView = itemView.findViewById(R.id.genreTextView)
        val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.titleTextView.text = movie.title
        holder.yearTextView.text = movie.year
        holder.genreTextView.text = movie.genre
        holder.ratingTextView.text = movie.rating
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
