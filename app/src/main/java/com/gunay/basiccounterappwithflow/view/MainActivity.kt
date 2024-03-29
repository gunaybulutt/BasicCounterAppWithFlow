package com.gunay.basiccounterappwithflow.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gunay.basiccounterappwithflow.FirstScreenVM
import com.gunay.basiccounterappwithflow.ui.theme.BasicCounterAppWithFlowTheme
import com.gunay.basiccounterappwithflow.view.FirstScreen
import com.gunay.basiccounterappwithflow.view.SecondScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicCounterAppWithFlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "firstScreen"){
                        composable(route = "firstScreen"){

                            val viewModel = viewModel<FirstScreenVM>()

                            //bu şekilde yazıldığında uygulama arka plana atıldığında 5sn sonra çalışmayı durdurur devam etmez
                            //5 sn sonra durdurulmasının nedeni flowun sonuna SharingStarted.WhileSubscribed(5000),0) eklenmesi (viewModele bak)
                            //yani .collectAsStateWithLifecycle() yaşam dögüsü farkındalığı olur fakat
                            //.collectAsState() şekliden yazıldığında yaşam döngüsü farkındalığı olmaz ve devam eder
                            val time by viewModel.counter.collectAsStateWithLifecycle() //lifecycle awareness yes
                            //val time by viewModel.counter.collectAsState() //lifecycle awareness no

                            FirstScreen(time = time, onButtonClicked =  {
                                navController.navigate("secondScreen")
                            })
                        }

                        composable(route = "secondScreen"){
                            SecondScreen()
                        }
                    }
                }
            }
        }
    }
}

