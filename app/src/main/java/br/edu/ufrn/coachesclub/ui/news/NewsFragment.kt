package br.edu.ufrn.coachesclub.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ufrn.coachesclub.R
import br.edu.ufrn.coachesclub.HTTPRequest
import kotlinx.android.synthetic.main.fragment_news.*

class NewsFragment : Fragment() {
    val request: HTTPRequest? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        request?.execute()
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = NewsAdapter()
        }
    }
}