package com.example.mirea_kurs_4_sem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mirea_kurs_4_sem.api.Api
import com.example.mirea_kurs_4_sem.api.Film
import com.example.mirea_kurs_4_sem.api.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val api = RetrofitHelper.getInstance().create(Api::class.java)
        if (arguments != null) {
            val photo : ImageView = view.findViewById(R.id.imageViewProduct)
            val description : TextView = view.findViewById(R.id.textViewDescription)
            val call = api.get_film(
                id = requireArguments().getInt("id_film"),
            )

            call.enqueue(object : Callback<Film> {
                override fun onResponse(
                    call: Call<Film>,
                    response: Response<Film>
                ) {
                    if (response.isSuccessful) {
                        val film = response.body()
                        if (film != null) {
                            description.text = film.description
                            val currentUrl: String = film.photo
                            Glide.with(view)
                                .load(currentUrl)
                                .into(photo)
                        } else {
                            if (response.code() == 404)
                                description.text = "Film not found"
                        }
                    }
                }

                override fun onFailure(call: Call<Film>, t: Throwable) {
                    println("Network Error :: " + t.localizedMessage);
                }

            })
        }
    }

}
