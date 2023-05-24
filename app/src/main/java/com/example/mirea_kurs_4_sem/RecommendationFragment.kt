package com.example.mirea_kurs_4_sem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mirea_kurs_4_sem.api.Api
import com.example.mirea_kurs_4_sem.api.Film
import com.example.mirea_kurs_4_sem.api.RetrofitHelper
import com.example.mirea_kurs_4_sem.find.FilmAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommendationFragment : Fragment() {
    private lateinit var saveAdapter : FilmAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getButton : Button = view.findViewById(R.id.buttonRecommindations)

        val bar : ProgressBar = view.findViewById(R.id.progressBar)

        val textError : TextView = view.findViewById(R.id.textError)

        val listFilm = view.findViewById<RecyclerView>(R.id.listFilm)
        listFilm.layoutManager = LinearLayoutManager(context)

        if (this::saveAdapter.isInitialized){
            saveAdapter.setOnClickListener(object :
                FilmAdapter.OnClickListener {
                override fun onClick(position: Int, model: Film) {
                    val bundle = bundleOf("id_film" to model.id)
                    view.findNavController().navigate(
                        R.id.action_recommendationFragment_to_productFragment,
                        bundle
                    )
                }
            })
            listFilm.adapter = saveAdapter
        }


        getButton.setOnClickListener {
            bar.visibility = View.VISIBLE
            getButton.visibility = View.GONE

            val api = RetrofitHelper.getInstance().create(Api::class.java)
            val call = api.get_recommendations()

            call.enqueue(object : Callback<List<Film>>{
                override fun onResponse(call: Call<List<Film>>, response: Response<List<Film>>) {
                    bar.visibility = View.GONE
                    getButton.visibility = View.VISIBLE
                    if (response.isSuccessful) {
                        val films = response.body()
                        if (films != null) {
                            val adapter = FilmAdapter(films)
                            adapter.setOnClickListener(object :
                                FilmAdapter.OnClickListener {
                                override fun onClick(position: Int, model: Film) {
                                    val bundle = bundleOf("id_film" to model.id)
                                    view.findNavController().navigate(
                                        R.id.action_recommendationFragment_to_productFragment,
                                        bundle
                                    )
                                }
                            })
                            listFilm.adapter = adapter
                            saveAdapter = adapter
                        }
                    } else if (response.code() == 405) {
                        textError.text = "Less 2 positive rates"
                        textError.visibility = View.VISIBLE
                    }else if (response.code() == 406) {
                        textError.text = "Less 2 negative rates"
                        textError.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<List<Film>>, t: Throwable) {
                    textError.text = "Connection lost"
                    textError.visibility = View.VISIBLE
                    println("Network Error :: " + t.localizedMessage);
                }
            })
        }
    }

}