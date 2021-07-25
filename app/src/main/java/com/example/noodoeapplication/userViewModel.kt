package com.example.noodoeapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noodoeapplication.data.UserInfo
import com.example.noodoeapplication.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject


class UserViewModel: ViewModel() {


    private val _user = MutableLiveData<UserInfo>()
    val user: LiveData<UserInfo> = _user

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state

    private var headers = mutableMapOf<String,String>()

    fun getUserInfo(userName: String, passWord: String){
        //set up header
        if(headers.isNotEmpty()) headers.clear()

        headers["X-Parse-Application-Id"] = "vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD"
        headers["Content-Type"] = "application/x-www-form-urlencoded"


        viewModelScope.launch(Dispatchers.IO) {
            try{
                val response = UserApi.retrofitService.getUserInfo(headers,userName,passWord)
                //check the request is successful
                if(response.isSuccessful){
                    _user.postValue(response.body())
                    _state.postValue("Successful")
                }
            }catch (e: Exception){
                _state.postValue("wrong password/email")
            }
        }
    }

    fun updateUserInfo(timezone: Int){
        if(headers.isNotEmpty()) headers.clear()

        headers["X-Parse-Session-Token"] = _user.value?.sessionToken.toString()
        headers["X-Parse-Application-Id"] = "vqYuKPOkLQLYHhk4QTGsGKFwATT4mBIGREI2m8eD"
        headers["Content-Type"] = "application/json"


        var json = JSONObject()
        json = json.put("timezone",timezone)
        val requestBody = RequestBody.create(MediaType.parse("application/json"),json.toString())
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val response = UserApi.retrofitService.updateUserInfo(headers,_user.value!!.objectId,requestBody)
                //successfully update
                if(response.isSuccessful) _state.postValue("Updated")

            }catch (e: Exception){
                _state.postValue("Update failed")
            }
        }



    }



}