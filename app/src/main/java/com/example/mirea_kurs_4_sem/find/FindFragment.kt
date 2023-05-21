package com.example.mirea_kurs_4_sem.find

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mirea_kurs_4_sem.R
import com.example.mirea_kurs_4_sem.api.Api
import com.example.mirea_kurs_4_sem.api.Film
import com.example.mirea_kurs_4_sem.api.RetrofitHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val search = view.findViewById<SearchView>(R.id.searchName)

        val listFilm = view.findViewById<RecyclerView>(R.id.listFilm)
        listFilm.layoutManager = LinearLayoutManager(context)

        val api = RetrofitHelper.getInstance().create(Api::class.java)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { api.find(line = it) }
                    ?.enqueue(object : Callback<List<Film>> {
                        override fun onResponse(
                            call: Call<List<Film>>,
                            response: Response<List<Film>>
                        ) {
                            if (response.isSuccessful) {
                                val films = response.body()
                                if (films != null) {
                                    val adapter = FilmAdapter(films)
                                    listFilm.adapter = adapter
                                    adapter.setOnClickListener(object :
                                        FilmAdapter.OnClickListener {
                                        override fun onClick(position: Int, model: Film) {
                                            val bundle = bundleOf("id_film" to model.id)
                                            view.findNavController().navigate(
                                                R.id.action_findFragment_to_productFragment,
                                                bundle
                                            )
                                        }
                                    })
                                    listFilm.adapter = adapter
                                }
                            }
                        }

                        override fun onFailure(call: Call<List<Film>>, t: Throwable) {
                            println("Network Error :: " + t.localizedMessage);
                        }

                    })
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    fun onClick(view: View) {
        val film: Film = view.tag as Film
        val bundle = bundleOf("id_film" to film.id)
        view.findNavController().navigate(R.id.action_findFragment_to_productFragment, bundle)
    }

}