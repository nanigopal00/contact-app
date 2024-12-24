package com.example.myapplication.screen.homescreen

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data_layer.contactviewmodel
import com.example.myapplication.screen.dataseanglescreen
import com.example.myapplication.ui.theme.greendc

@Composable
@Preview(showSystemUi = true)
fun contactScreen(
    viewmodel: contactviewmodel,
    navcontroll: NavHostController,
    singelscreendata: dataseanglescreen
) {
    var searchitem = remember {
        mutableStateOf("")
    }
    var contaxt = LocalContext.current
    var contact = viewmodel.contact.collectAsState(initial = emptyList())
   Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = {
      TextField(
          onValueChange = {searchitem.value = it
                          },
          value = searchitem.value,
          singleLine = true,
          modifier = Modifier
              .height(55.dp)
              .fillMaxWidth()
              .padding(horizontal = 14.dp),
          shape = RoundedCornerShape(40.dp),
          placeholder = {
              Text("Search contact", color = Color.Black)
          },
          leadingIcon = {
              Icon(Icons.Outlined.Search,null, modifier = Modifier.padding(start = 12.dp))
          }, colors = TextFieldDefaults.colors(
              focusedIndicatorColor = Color.Transparent,
              unfocusedLabelColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent,
              focusedContainerColor =  MaterialTheme.colorScheme.secondary.copy(0.1f),
              unfocusedContainerColor = MaterialTheme.colorScheme.secondary.copy(0.1f),
              cursorColor = greendc.copy(0.4f)

          ), trailingIcon = {
              Icon(Icons.Default.KeyboardVoice,null,Modifier.padding(end = 14.dp))
          }

      )
   },
       floatingActionButton = {
           FloatingActionButton(onClick = {
               navcontroll.navigate(MainActivity.addcontact)

           },Modifier.padding(bottom = 20.dp, end = 6.dp),
               containerColor = greendc, elevation = FloatingActionButtonDefaults.elevation(7.dp)) {
               Icon(Icons.Filled.Add,null, tint = Color.White, modifier = Modifier.size(30.dp))

           }
       }
       ) {
       Column (
           Modifier
               .fillMaxSize()
               .padding(it)){
          Text("All contact", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.padding(start = 19.dp, top = 26.dp))

           LazyColumn(
               Modifier
                   .fillMaxWidth()
                   .padding(top = 9.dp)) {
               items(contact.value.filter {
                   it.name.contains(searchitem.value)
               }){
                   Row (modifier = Modifier.fillMaxSize().clickable {

                       singelscreendata.image.value=it.image
                       navcontroll.navigate(
                           MainActivity.singelescreenT(id = it.id, name = it.name, number = it.number, email = it.email)
                       )


                   }
                       .height(75.dp)
                       .padding(horizontal = 16.dp, vertical = 4.dp),
                       verticalAlignment = Alignment.CenterVertically){
                       if (it.image!!.size==0){

                           Image( painter = painterResource(R.drawable.user),contentDescription = null,
                               modifier = Modifier
                                   .size(40.dp)
                                   .clip(
                                       RoundedCornerShape(100.dp)
                                   ),
                               contentScale = ContentScale.Crop)
                       }
                       else{
                           Log.d("check2" , "${it.image.toString()}")
                           var image = BitmapFactory.decodeByteArray(it.image,0, it.image!!.size)
                           Image(bitmap = image.asImageBitmap(),contentDescription = null,
                               modifier = Modifier
                                   .size(40.dp)
                                   .clip(
                                       RoundedCornerShape(100.dp)
                                   ),
                               contentScale = ContentScale.Crop)
                       }
                       Column(
                           Modifier
                               .fillMaxHeight()
                               .padding(horizontal = 14.dp, vertical = 5.dp),
                       ){
                           Text(it.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black.copy(0.9f), fontStyle = FontStyle.Normal)
                           Text("phone +91 ${it.number}", fontSize = 14.sp, fontWeight = FontWeight.Normal )
                       }

                       Box(
                           modifier = Modifier
                               .fillMaxSize() // Important: Make the Box take up the full space
                               .padding(16.dp),
                           contentAlignment = Alignment.BottomEnd // Align content to bottom-right
                       ) {
                           Icon(
                               Icons.Filled.Call,
                               "Call",
                               Modifier
                                   .size(24.dp)
                                   .clickable {
                                       val intent = Intent(Intent.ACTION_CALL).apply {
                                           data = Uri.parse("tel:${it.number}")
                                       }
                                       contaxt.startActivity(intent)
                                   }
                           )
                       }


                   }
               }


           }

       }

   }
   }
