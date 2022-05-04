package com.example.statemanagementwithvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    //in view models we move our state management to the view model

//    Note View model state management does not need the remember variable
    //This is quite different from the state management of architectural patterns of
    //LiveData, StateFLow , FLows etc, this is just to handle view state recompositions
    var viewUserNameTextStateInViewModel by mutableStateOf("")
    var viewPasswordTextStateInViewModel by mutableStateOf("")

    //we also need to handle the onValue change

    fun onUserNameChangeInViewModel(newValue : String){
        viewUserNameTextStateInViewModel = newValue
    }
    fun onPasswordChangeInViewModel(newValue : String){
        viewPasswordTextStateInViewModel = newValue
    }

    //next step initialise ViewModel in View
}