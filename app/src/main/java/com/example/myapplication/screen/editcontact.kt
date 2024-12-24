package com.example.myapplication.screen

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data_layer.contactviewmodel
import com.example.myapplication.database.contacttable
import com.example.myapplication.ui.theme.greendc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showSystemUi = true)
fun edit (
    modifier: contactviewmodel,
    navcontroll: NavHostController,
    datasingelscreen: dataseanglescreen,
    name: String,
    id: Int,
    number: String,
    email: String,
    viewmodel: contactviewmodel
) {

    var nameholder = rememberSaveable {
        mutableStateOf("")
    }
    var numberholder = rememberSaveable{
        mutableStateOf("")
    }
    var emailholder = rememberSaveable {
        mutableStateOf("")
    }
    nameholder.value=name
    numberholder.value=number
    emailholder.value=email
    Scaffold(modifier =Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit contact", color = Color.Black, modifier = Modifier.padding(start = 4.dp), fontSize = 24.sp, fontWeight = FontWeight.Normal)
                }
                , colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ), modifier = Modifier.shadow(
                    elevation = 3.dp,
                    spotColor = greendc.copy(0.4f)
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navcontroll.popBackStack()



                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(0.09f),
                    )) {
                        Icon(
                            Icons.Rounded.Close,null,
                            Modifier
                                .size(27.dp)
                                .padding(start = 1.dp))

                    }

                },
                actions = {
                    IconButton(onClick = {


                        navcontroll.navigate(
                            MainActivity.homescreen
                        ){
                            // important
                            popUpTo(MainActivity.homescreen){
                                inclusive=true
                            }
                        }
                        GlobalScope.launch (Dispatchers.Default){
                            if (nameholder.value.isNotEmpty() && numberholder.value.isNotEmpty()){
                                viewmodel.db.contactdao().update(contacttable(id= id,name = nameholder.value, number = numberholder.value, email = emailholder.value, image = datasingelscreen.image.value))
                            }


                        }




                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(0.09f),
                    )) {
                        Icon(Icons.Rounded.Save,null,
                            Modifier
                                .size(27.dp)
                                .padding(end = 0.dp), tint =
                            greendc.copy(0.8f))
                    }

                }
            )

        }) {
        Column(Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Spacer(modifier = Modifier.height(40.dp))
            var contaxt = LocalContext.current
            var imagepicker = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {uri ->
                    if (uri!=null){
                        var inputstreem = contaxt.contentResolver.openInputStream(uri)
                        var byteimage = inputstreem?.readBytes()
                        val compressedImageBytes = resizeAndCompressImage(byteimage!!, 500, 500, 80)
                        if (compressedImageBytes!=null){
                            datasingelscreen.image.value=compressedImageBytes

                        }



                    }

                }
            )

            if (datasingelscreen.image.value.isEmpty()){
                IconButton(onClick = {
                    imagepicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                   // pic image


                },modifier = Modifier.size(120.dp), colors = IconButtonDefaults.iconButtonColors(
                    containerColor = greendc.copy(0.1f),
                )){
                    Image(painter = painterResource(R.drawable.add_image),null,
                        Modifier
                            .size(40.dp)
                            .clickable {
                                imagepicker.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )

                                // pic image
                            },
                        contentScale = ContentScale.Crop)

                }


            }

            else{
            IconButton(onClick = {
                imagepicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
                // pic image


            },modifier = Modifier.size(120.dp), colors = IconButtonDefaults.iconButtonColors(
                containerColor = greendc.copy(0.1f),
            )){
                var image = BitmapFactory.decodeByteArray(datasingelscreen.image.value,0, datasingelscreen.image.value.size)
                Image(bitmap = image.asImageBitmap(),contentDescription = null,
                    Modifier
                        .size(120.dp)
                        .clip(
                            RoundedCornerShape(90.dp)
                        ), contentScale = ContentScale.Crop)
            }
            }
            if (datasingelscreen.image.value.isEmpty()){
                Text("Add photo", fontSize = 16.sp, color = greendc, modifier = Modifier
                    .padding(top = 6.dp)
                    .clickable {
                        imagepicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )

                       //
                    },
                    fontWeight = FontWeight.SemiBold
                )

            }else{
                Spacer(Modifier.height(2.dp))
                Row (
                    Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Icon(Icons.Filled.Delete,null,
                        Modifier
                            .size(23.dp)
                            .padding(top = 5.dp)
                            .clickable {
                               // remove image
                            }, tint = greendc)
                    Text("Remove", fontSize = 14.sp, color = greendc ,modifier = Modifier
                        .clickable {
                         datasingelscreen.image.value=ByteArray(0)

                        }
                        .padding(start = 1.dp, top = 4.dp), fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(modifier = Modifier.height(36.dp))
            OutlinedTextField(value = nameholder.value,
                onValueChange = {
                    nameholder.value=it
                },
                label = {
                    Text("Name")
                },

                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greendc,
                    unfocusedBorderColor = Color.Black

                ),
                shape = RoundedCornerShape(corner = CornerSize(7.dp))
            )
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(value = numberholder.value,
                onValueChange = {
                    numberholder.value=it
                },
                label = {
                    Text("Number")
                },
                placeholder = {
                    Text("Number")
                },
                prefix = {
                    Text("+91")
                },

                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greendc,
                    unfocusedBorderColor = Color.Black

                ),
                shape = RoundedCornerShape(corner = CornerSize(7.dp))
            )
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(value = emailholder.value,
                onValueChange = {
                    emailholder.value=it
                },
                label = {
                    Text("Email")
                },

                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greendc,
                    unfocusedBorderColor = Color.Black

                ),
                shape = RoundedCornerShape(corner = CornerSize(7.dp))
            )


        }

    }


}
