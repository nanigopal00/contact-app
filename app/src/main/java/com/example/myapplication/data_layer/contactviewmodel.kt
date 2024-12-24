package com.example.myapplication.data_layer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.contacttable
import com.example.myapplication.database.database
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class contactviewmodel @Inject constructor(var db:database):ViewModel() {
    var contact = db.contactdao().shortByName()
    fun add(name:String,number:String,email:String,image:ByteArray){
        viewModelScope.launch {
            db.contactdao().insert(contacttable(name = name, number = number, email = email, image = image))

        }

    }


    }

