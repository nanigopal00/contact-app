package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.data_layer.contactviewmodel
import com.example.myapplication.screen.addcontact
import com.example.myapplication.screen.dataseanglescreen
import com.example.myapplication.screen.edit
import com.example.myapplication.screen.homescreen.homescreen
import com.example.myapplication.screen.singel
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var datasingelscreen:dataseanglescreen


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var viewmodel = hiltViewModel<contactviewmodel>()
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box (modifier = Modifier.fillMaxSize()){
                        var navcontroll = rememberNavController()
                        NavHost(navController = navcontroll, startDestination = homescreen) {
                            composable<homescreen> {backstack->

                             homescreen(navcontroll,viewmodel,datasingelscreen)

                            }
                            composable<addcontact> {
                                addcontact(navcontroll,viewmodel)


                            }
                            composable<singelescreenT> {
                                var data = it.toRoute<singelescreenT>()
                                singel(viewmodel,navcontroll,datasingelscreen,data.name,data.id,data.number,data.email)

                            }
                            composable<editscreen> {
                                var data = it.toRoute<singelescreenT>()
                                edit(viewmodel,navcontroll,datasingelscreen,data.name,data.id,data.number,data.email,viewmodel)
                            }


                        }

                    }


                }
            }
        }
    }


    @Serializable
    object homescreen
   @Serializable
    object addcontact
    @Serializable
    data class singelescreenT(
        val id:Int,
        val name:String,
        val number:String,
        val email:String,

    )
    @Serializable
   data class editscreen(
        val id:Int,
        val name:String,
        val number:String,
        val email:String,

   )

}


