package br.edu.ufrn.coachesclub

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class HTTPRequest : AsyncTask<Void?, Void?, Void?>() {
    override fun doInBackground(vararg params: Void?): Void? {
        var urlConnection: HttpURLConnection? = null
        try {
            val url = URL("https://newsapi.org/v2/sources?apiKey=bafe25ee0bc645c383c68ffff71f7e06&language=en")
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11")
            val code: Int = urlConnection.responseCode
            if (code != 200) {
                throw IOException("Invalid response from server: $code")
            }
            val rd = BufferedReader(InputStreamReader(
                urlConnection.inputStream))
            var line: String?
            while (rd.readLine().also { line = it } != null) {
                line?.let { Log.i("data", it) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect()
            }
        }
        return null
    }
}