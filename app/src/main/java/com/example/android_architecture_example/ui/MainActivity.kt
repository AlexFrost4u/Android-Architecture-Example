package com.example.android_architecture_example.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.android_architecture_example.R
import com.example.android_architecture_example.domain.User
import com.example.android_architecture_example.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG: String = "AppDebug"


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetUserEvents)
    }

    private fun subscribeObservers(){
        viewModel.dataSate.observe(this, { dataSate ->
            when(dataSate){
                is DataState.Success<List<User>>->{
                    displayProgressBar(false)
                    appendUserNames(dataSate.data)
                }
                is DataState.Error->{
                    displayProgressBar(false)
                    displayError(dataSate.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }

        })
    }


    private fun displayError(message:String?){
        val text = findViewById<TextView>(R.id.request_result)
        if(message!=null){
            text.text = message
        }else{
            text.text = getString(R.string.unknown_error)
        }
        Log.i("DEBUG",message!!)
    }

    private fun displayProgressBar(isDisplayed:Boolean){
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendUserNames(users:List<User>){
        val sb = StringBuilder()
        for(user in users){
            sb.append(user.firstName + "\n")
        }
        val text = findViewById<TextView>(R.id.request_result)
        text.text = sb.toString()
    }
}