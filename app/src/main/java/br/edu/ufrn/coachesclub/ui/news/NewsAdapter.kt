package br.edu.ufrn.coachesclub.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufrn.coachesclub.R

class NewsAdapter  : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val title = arrayOf(
        "Notícia 1",
        "Notícia 2",
        "Notícia 3",
        "Notícia 4",
        "Notícia 5")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_title: TextView

        init {
            item_title = itemView.findViewById(R.id.title_news)

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetailNews::class.java).apply {
                    putExtra("TITULO", item_title.text)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.news_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.item_title.text = title[i]
    }

    override fun getItemCount(): Int = title.size
}