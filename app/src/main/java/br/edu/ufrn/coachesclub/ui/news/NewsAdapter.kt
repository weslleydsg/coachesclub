package br.edu.ufrn.coachesclub.ui.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufrn.coachesclub.HTTPRequest
import br.edu.ufrn.coachesclub.R
import br.edu.ufrn.coachesclub.TaskRunner

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    var news = ArrayList<News>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemTitle: TextView = itemView.findViewById(R.id.title_news)

        init {
            itemView.setOnClickListener() {
                val url = news[layoutPosition].url
                val intent = Intent(Intent.ACTION_VIEW)
                val context = itemView.context
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            }
        }
    }

    private fun loadData() {
        val taskRunner = TaskRunner()
        taskRunner.executeAsync(HTTPRequest()) { data ->
            if (data != null) {
                news.addAll(data)
                notifyDataSetChanged()
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        loadData()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.news_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = news[i].title
    }

    override fun getItemCount(): Int = news.size
}