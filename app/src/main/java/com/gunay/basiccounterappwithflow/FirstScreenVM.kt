package com.gunay.basiccounterappwithflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn


class FirstScreenVM : ViewModel(){

    private var count = 0

    val counter = flow {
        while (true){
            delay(1000L)
            println("running flow + ${count}")
            emit(count++)
        }
        //SharingStarted.Lazily gerekmedikçe oluşturma layz değişken gibi 0 ise start value
        //SharingStarted.WhileSubscribed -> içindeki 5000 kullanıcı başka bir ekrana geçerse
        // 5 sn sonra çalışmayı durdur fakat uygulama alta atılırsa çalışmaya devam eder
        // eğer SharingStarted.Lazily kullanılırsa başka ekrana
        //geçilse bile arka planda flow çalışmaya devam eder
        //.stateIn(viewModelScope, SharingStarted.Lazily,0)
    } .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),0)



}