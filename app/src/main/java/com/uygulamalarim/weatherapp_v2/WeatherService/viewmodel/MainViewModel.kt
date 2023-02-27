package com.uygulamalarim.weatherapp_v2.WeatherService.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uygulamalarim.weatherapp_v2.WeatherService.model.WeatherModal
import com.uygulamalarim.weatherapp_v2.WeatherService.service.WeatherApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel:ViewModel() {

    private val weatherApiService=WeatherApiService()
    private val disposable=CompositeDisposable()
    val weather_data= MutableLiveData<WeatherModal>()
    val weather_error= MutableLiveData<Boolean>()
    val weather_load= MutableLiveData<Boolean>()

    fun refreshData(cityName: String){
        getDataFromAPI(cityName)
        //getDataFromLocal()
    }
    private fun getDataFromAPI(cityName:String) {
        weather_load.value=true
        disposable.add(
            weatherApiService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModal>(){
                    override fun onSuccess(t: WeatherModal) {
                        weather_data.value=t
                        weather_error.value=false
                        weather_error.value=false

                    }

                    override fun onError(e: Throwable) {
                        weather_error.value=true
                        weather_load.value=false
                    }

                })
        )
    }




}