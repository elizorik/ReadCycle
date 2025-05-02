package com.example.readcycle.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.readcycle.Greeting
import com.example.readcycle.navigation.Timer
//import com.example.readcycle.App
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen() {
    val auth = Firebase.auth
    val emailState = remember{
        mutableStateOf("")
    }
    val passwordState = remember{
        mutableStateOf("")
    }
    if (auth.currentUser?.email == null){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = emailState.value, onValueChange = {
            emailState.value = it
        })
        Spacer(modifier = Modifier.height(15.dp))
        TextField(value = passwordState.value, onValueChange = {
            passwordState.value = it
        })
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Уже есть аккаунт? Тогда скорее входи и продолжай свой путь в мире книг!", textAlign = TextAlign.Center)
        Button(onClick = {
            signIn(auth, emailState.value,passwordState.value)
        }) {
            Text(text = "SignIn")
        }
        Text(text = "Ещё нет аккаунта? Тогда скорее регистрируйся и читай с нами!", textAlign = TextAlign.Center)
        Button(onClick = {
            signUp(auth, emailState.value,passwordState.value)
        }) {
            Text(text = "SignUp")
        }
    }
    } else {
        Timer()
    }
}
private fun signUp(auth:FirebaseAuth, email:String, password:String){
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
        if (it.isSuccessful){
            Log.d("Login", "Sign Up succeed")
        }
        else{
            //Toast.makeText(App.APP, "Вы уже зарегистрированы в приложении!", Toast.LENGTH_SHORT).show()
            Log.d("Login","Sign Up failed")
        }
    }
}
private fun signIn(auth:FirebaseAuth, email:String, password:String){
    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
        if (it.isSuccessful){
            Log.d("Login", "Sign In succeed")
        }
        else{
            Log.d("Login","Sign In failed")
            //Toast.makeText(App.APP, "Неправильный адрес почты или пароль!", Toast.LENGTH_SHORT).show()
        }
    }
}
public fun signOut(auth:FirebaseAuth){
    auth.signOut()
    //Toast.makeText(App.APP, "До новых встреч!", Toast.LENGTH_SHORT).show()
}
public fun deleteAccount(auth:FirebaseAuth, email:String, password:String){
    val credential = EmailAuthProvider.getCredential(email,password)
    auth.currentUser?.reauthenticate(credential)?.addOnCompleteListener{
        if (it.isSuccessful){
            auth.currentUser?.delete()?.addOnCompleteListener{
                if (it.isSuccessful){
                    Log.d("Login", "Account was deleted")
                }
                else{
                    Log.d("Login","Failure to delete an account")
            }
            }

        }else{
            Log.d("Login","Failure to reauthenticate")
        }
    }
}
