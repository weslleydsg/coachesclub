package br.edu.ufrn.coachesclub

import android.util.Log
import br.edu.ufrn.coachesclub.ui.news.News
import com.google.gson.GsonBuilder
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable

data class ApiResult(val status: String, val totalResults: Int, val articles: ArrayList<News>)

class HTTPRequest : Callable<ArrayList<News>?> {
    var page: Int? = 1

    override fun call(): ArrayList<News>? {
        if (page == null) return null

        val url = URL("https://newsapi.org/v2/everything?apiKey=bafe25ee0bc645c383c68ffff71f7e06&language=pt&q=esporte&page=$page")

        var result: ArrayList<News>? = null

        try {
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"
            )

            val code: Int = urlConnection.responseCode
            if (code != 200) throw IOException("Invalid response from server: $code")

            urlConnection.inputStream.bufferedReader().use { reader ->
                val jsonString = reader.readText()

                val builder = GsonBuilder()
                builder.setPrettyPrinting()
                val gson = builder.create()

                val apiResult: ApiResult = gson.fromJson(jsonString, ApiResult::class.java)

                page = if (page!! >= apiResult.totalResults) {
                    null
                } else {
                    page!! + 1
                }

                result = apiResult.articles
            }

            urlConnection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }
}