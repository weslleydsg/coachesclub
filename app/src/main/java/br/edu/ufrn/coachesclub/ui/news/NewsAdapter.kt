package br.edu.ufrn.coachesclub.ui.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ufrn.coachesclub.R


class NewsAdapter  : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    val news = mutableListOf(
            News("https://www.inverse.com/gaming/genshin-impact-version-16-release-date-trailer-kazuha-klee-inzauma", "https://imgix.bustle.com/uploads/image/2021/4/26/8dd7c863-2b07-4a5d-b7fe-5081d8a37229-spriteatlastexture-ui_sprite_activity_effigy_challenge_rock-1024x1024-fmt25_156008.jpeg?w=2000&h=640&fit=crop&crop=faces&auto=format%2Ccompress", "EVERYTHING YOU NEED TO KNOW ABOUT GENSHIN IMPACT VERSION 1.6"),
            News("https://switch-brasil.com/nintendo-estudio-monolith-soft-anuncia-expansao-de-larga-escala/", "https://switch-brasil.com/wp-content/uploads/2021/04/Monolith-Soft-Scrn28042021-750x430.jpg", "Nintendo | Estúdio Monolith Soft anuncia expansão de larga escala"),
            News("https://www.legiaodosherois.com.br/2021/boruto-manga-fraquezas-eida.html", "https://kanto.legiaodosherois.com.br/w760-h398-gnw-cfill-q80/wp-content/uploads/2021/04/legiao_ENjenzVPmbp9.jpg.jpeg", "BORUTO: MANGÁ EXPLICA FRAQUEZAS DOS PODERES DE EIDA"),
            News("https://www.legiaodosherois.com.br/2021/house-dragon-fotos-daemon-rhaenyra-targaryen-bastidores.html", "https://kanto.legiaodosherois.com.br/w760-h398-gnw-cfill-q80/wp-content/uploads/2021/04/legiao_j9hoBNgVM_i8.jpg.jpeg", "HOUSE OF THE DRAGON: FOTOS DOS BASTIDORES MOSTRAM DAEMON E RHAENYRA TARGARYEN NA SÉRIE"),
            News("https://observatoriodocinema.uol.com.br/series-e-tv/2021/04/na-netflix-ninguem-aguenta-ver-essa-serie-ate-o-fim", "https://observatoriodocinema.uol.com.br/wp-content/uploads/2021/04/Netflix-logo-and-screen.jpg", "Na Netflix? Ninguém aguenta ver ESSA série até o fim")
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item_title: TextView = itemView.findViewById(R.id.title_news)

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

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.news_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.item_title.text = news[i].title
    }

    override fun getItemCount(): Int = news.size
}