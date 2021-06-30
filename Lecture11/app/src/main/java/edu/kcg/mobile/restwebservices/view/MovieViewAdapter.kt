package edu.kcg.mobile.restwebservices.view

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.kcg.mobile.restwebservices.R
import edu.kcg.mobile.restwebservices.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL


class MovieViewAdapter(
    private val context: Context,
    var data: List<Movie>
) : RecyclerView.Adapter<MovieViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvYear: TextView = view.findViewById(R.id.tvYear)
        val imagePoster: ImageView = view.findViewById(R.id.image_poster)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val url = "https://www.imdb.com/title/" + data[layoutPosition].imdbID
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(browserIntent)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.text_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvTitle.text = data[position].Title
        viewHolder.tvYear.text = data[position].Year

        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                val url = data[position].Poster
                try {
                    val stream = URL(url).openStream()
                    val bitmap = BitmapFactory.decodeStream(stream)
                    if (bitmap != null) {
                        viewHolder.imagePoster.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

}