package com.example.myapplication.screen.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.data_layer.contactviewmodel
import com.example.myapplication.screen.dataseanglescreen
import com.example.myapplication.ui.theme.buttombaritem
import com.example.myapplication.ui.theme.greendc

@Composable
@Preview(showBackground = true)
fun homescreen(
    navcontroll: NavHostController,
    viewmodel: contactviewmodel,
    datasingelscreen: dataseanglescreen
) {
    var currentindex = remember {
        mutableStateOf(0)
    }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    val bottomBarHeight = if (screenWidth.dp > 600.dp) {
        72.dp
    } else {
        62.dp
    }
    var buttombarItem = arrayOf(buttombaritem("Contact",
        icon = Icons.Filled.Person),
        buttombaritem("Favroits",Icons.Outlined.Star))
    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(Modifier.height(bottomBarHeight).shadow(
                elevation = 7.dp,
            ), containerColor = Color.White) {

              buttombarItem.forEachIndexed { index, buttombaritem ->
                  NavigationBarItem(
                      onClick = {
                          currentindex.value= index

                      }, selected = currentindex.value == index,

                      icon = {
                          Icon(buttombaritem.icon,null)
                      }
                      ,
                      colors = NavigationBarItemColors(
                          selectedIconColor = greendc,
                          unselectedIconColor = Color.Black,
                          selectedIndicatorColor = Color.Transparent,
                          selectedTextColor = greendc,
                          unselectedTextColor = Color.Black,
                          disabledIconColor = Color.Black,
                          disabledTextColor = Color.Black
                      ),
                      label = {
                          Text(buttombaritem.titel)
                      }

                  )
              }
            }

        }) {it->
        Box(Modifier.fillMaxSize().padding(it)){
            when(currentindex.value){
                0->{
                   contactScreen(viewmodel,navcontroll,datasingelscreen)
                }
                1->{
                    favratescreen(viewmodel,navcontroll,datasingelscreen)

                }
            }

        }

    }





}

