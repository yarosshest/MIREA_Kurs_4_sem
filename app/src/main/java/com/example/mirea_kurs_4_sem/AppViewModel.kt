package com.example.mirea_kurs_4_sem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mirea_kurs_4_sem.api.Film
import com.example.mirea_kurs_4_sem.find.FilmAdapter


class AppViewModel: ViewModel()  {
    private val findList = MutableLiveData<List<Film>>()
    private val recommendList = MutableLiveData<List<Film>>()

    fun saveFindList(ad : List<Film>){
        findList.value = ad
    }
    fun getFindList() : List<Film>? {
        return findList.value
    }

    fun saveRecommendList(ad : List<Film>){
        recommendList.value = ad
    }
    fun getRecommendList() : List<Film>? {
        return recommendList.value
    }

}