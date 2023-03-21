package com.example.rickandmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val repository = SharedRepository()

    private val _characterByIdLivedata = MutableLiveData<GetCharacterByIdResponse?>()
    val _characterByIdLiveData: LiveData<GetCharacterByIdResponse?> = _characterByIdLivedata

    fun refreshCharacter(characterId: Int) {
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId)

            _characterByIdLivedata.postValue(response)
        }

    }

}