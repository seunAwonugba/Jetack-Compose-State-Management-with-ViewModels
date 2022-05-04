package com.example.statemanagementwithvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.statemanagementwithvm.ui.theme.StateManagementWithVMTheme

class MainActivity : ComponentActivity() {
    //initialise VM
    private val viewModel by viewModels<MyViewModel>()
    //Next pass viewModel into your screen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateManagementWithVMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //state is any value that can change over time
                    //when you are using by remember, you must set your variable to var
                    LoginScreen(viewModel = viewModel)

                }
            }
        }
    }
}

//Here is the login screen, so the screen contains 2 input fields and one button
//it will be ideal to have a custom text field that we just keep calling instead of creating new text fields
//over and over
@Composable
fun LoginScreen(viewModel: MyViewModel){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //i can handle state here 2nd way
        //but now tha state must be handled for the different text fields
        //So goo thing is state is handled for each text fields, but state takes care of
        //only recomposition

        //however if rotation occurs it does not handle it, so to create a life cycle aware app
        //we need the view model

        //don't need these again since view model handles it
        var insideCustomTextFieldComposableStateForUserName by remember{
            mutableStateOf("")
        }
        var insideCustomTextFieldComposableStateForPassword by remember{
            mutableStateOf("")
        }
//        MyCustomTextField(
//            label = "UserName",
//            userInputValueToHandleSTate = insideCustomTextFieldComposableStateForUserName,
//            onValueChange = {insideCustomTextFieldComposableStateForUserName = it}
//        )
//        MyCustomTextField(
//            label = "Password",
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            userInputValueToHandleSTate = insideCustomTextFieldComposableStateForPassword,
//            onValueChange = {insideCustomTextFieldComposableStateForPassword = it}
//        )
        MyCustomTextField(
            label = "UserName",
            userInputValueToHandleSTate = viewModel.viewUserNameTextStateInViewModel,
            onValueChange = {viewModel.onUserNameChangeInViewModel(it)}
        )
        MyCustomTextField(
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            userInputValueToHandleSTate = viewModel.viewPasswordTextStateInViewModel,
            onValueChange = {viewModel.onPasswordChangeInViewModel(it)}
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            enabled = viewModel.viewUserNameTextStateInViewModel.isNotBlank() &&
                    viewModel.viewPasswordTextStateInViewModel.isNotBlank()
        ) {
            Text(text = "Submit")
            
        }

    }
}

//custom text field
@Composable
fun MyCustomTextField(
    label : String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    userInputValueToHandleSTate : String,
    onValueChange : (String) -> Unit
){
    //i can handle state here 1st way
//    var insideCustomTextState by remember{
//        mutableStateOf("")
//    }
    OutlinedTextField(
        value = userInputValueToHandleSTate,
        onValueChange = onValueChange,
        label = { Text(text = label)},
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )

}
