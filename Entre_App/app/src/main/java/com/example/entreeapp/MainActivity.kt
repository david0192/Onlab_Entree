package com.example.entreeapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apiexample.api.UserApi
import com.example.entreeapp.ui.theme.EntreeAppTheme
import com.example.entreeapp.ui.theme.Purple700
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EntreeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MainScreen() {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = Purple700,
                    title = {
                        Text(
                            text = "Simple API Request",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    val user = remember {
                        mutableStateOf(User(
                            Email = "",
                            Role = ""
                        ))
                    }

                    Text(
                        text="API Sample",
                        style= TextStyle(
                            fontSize = 40.sp,
                            fontFamily = FontFamily.Cursive
                        )
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Spacer(modifier = Modifier.height(15.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                val data = sendRequest(

                                    UserState = user
                                )

                                Log.d("Main Activity", user.toString())
                            }
                        ) {
                            Text(text = "Get Data")
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(text = user.component1().toString(), fontSize = 40.sp)
                }
            }
        )
    }

    fun sendRequest(
                UserState: MutableState<User>
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:7111/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(UserApi::class.java)

        val call: Call<User?>? = api.getUser();

        call!!.enqueue(object: Callback<User?> {
            override fun onResponse(call: Call<User?>, response: Response<User?>) {
                if(response.isSuccessful) {
                    Log.d("Main", "success!" + response.body().toString())
                    UserState.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<User?>, t: Throwable) {
                Log.e("Main", "Failed mate " + t.message.toString())
            }
        })
    }
}