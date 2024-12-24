package com.example.myapplication.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data_layer.contactviewmodel
import com.example.myapplication.ui.theme.greendc
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun addcontact(navcontroll: NavHostController, viewmodel: contactviewmodel) {
    var nameholder = rememberSaveable {
        mutableStateOf("")
    }
    var numberholder = rememberSaveable{
        mutableStateOf("")
    }
    var emailholder = rememberSaveable {
        mutableStateOf("")
    }
    var imageholder= rememberSaveable {
        mutableStateOf<ByteArray>(ByteArray(0))
    }
    var bitmapimage = rememberSaveable{
        mutableStateOf<Bitmap?>(null)
    }
    var contaxt = LocalContext.current
   Scaffold(modifier = Modifier.fillMaxSize(),
       topBar = {
          TopAppBar(title = {
              Text("Create contact", color = Color.Black, modifier = Modifier.padding(start = 4.dp), fontSize = 24.sp, fontWeight = FontWeight.Normal)
          }, colors = TopAppBarDefaults.topAppBarColors(
              containerColor = Color.White
          ), modifier = Modifier.shadow(
              elevation = 3.dp,
              spotColor = greendc.copy(0.4f)
          )
              , navigationIcon = {
            IconButton(onClick = {
                navcontroll.popBackStack()


            }, colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary.copy(0.09f),
            )) {
                Icon(Icons.Rounded.Close,null,
                    Modifier
                        .size(27.dp)
                        .padding(start = 1.dp))

            }


          },
              actions = {
                 IconButton(onClick = {
                    if (nameholder.value.isEmpty() || numberholder.value.isEmpty()|| emailholder.value.isEmpty()){
                        Toast.makeText(contaxt, " Field Required", Toast.LENGTH_SHORT).show()

                    }
                    else{
                        viewmodel.add(nameholder.value,numberholder.value,emailholder.value,imageholder.value)
                        navcontroll.popBackStack()
                        Toast.makeText(contaxt, "Saved", Toast.LENGTH_SHORT).show()

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
              })

       }
     ) {

       var picimage = rememberLauncherForActivityResult(
           contract = ActivityResultContracts.PickVisualMedia(),
           onResult = {uri->
               if (uri!=null){
                   var imputstreem:InputStream? = contaxt.contentResolver.openInputStream(uri)
                   val bytearray = imputstreem?.readBytes()
                   if (bytearray!==null){

                       val compressedImageBytes = resizeAndCompressImage(bytearray, 500, 500, 80)
                       imageholder.value=compressedImageBytes
                       bitmapimage.value=BitmapFactory.decodeByteArray(compressedImageBytes,0,compressedImageBytes.size)


                       // imageholder.value= bytearray
                     //  bitmapimage.value=BitmapFactory.decodeByteArray(imageholder.value,0,imageholder.value.size)

                   }


               }

           }
       )

      Column (
          Modifier
              .fillMaxSize()
              .padding(it),
          horizontalAlignment = Alignment.CenterHorizontally){
          Spacer(Modifier.height(60.dp))
        IconButton(onClick = {
            picimage.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )


        },modifier = Modifier.size(120.dp), colors = IconButtonDefaults.iconButtonColors(
            containerColor = greendc.copy(0.1f),
        )) {
            if (bitmapimage.value != null){
                Image(bitmap = bitmapimage.value!!.asImageBitmap(),null,
                    Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(30.dp)
                        )
                        .clickable {
                            picimage.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }, contentScale = ContentScale.Crop)

            }
            else{
                Image(painter = painterResource(R.drawable.add_image),null,
                    Modifier
                        .size(40.dp)
                        .clickable {
                            picimage.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentScale = ContentScale.Crop)
            }



        }
          if (bitmapimage.value==null){
              Text("Add photo", fontSize = 16.sp, color = greendc, modifier = Modifier
                  .padding(top = 6.dp)
                  .clickable {
                      picimage.launch(
                          PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                      )
                  },
                  fontWeight = FontWeight.SemiBold
              )

          }
          if (bitmapimage.value!=null){
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
                             imageholder.value = ByteArray(0)
                             bitmapimage.value = null
                         }, tint = greendc)
                 Text("Remove", fontSize = 14.sp, color = greendc ,modifier = Modifier
                     .clickable {
                         imageholder.value = ByteArray(0)
                         bitmapimage.value = null

                     }
                     .padding(start = 1.dp, top = 4.dp), fontWeight = FontWeight.SemiBold)
             }
          }

          Spacer(Modifier.height(30.dp))
          OutlinedTextField(value = nameholder.value,
              onValueChange = {
                  nameholder.value = it
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
              prefix = {
                  Text("+91")
              },
              onValueChange = {
                  numberholder.value = it
              },
              label = {
                  Text("Number")
              },

              maxLines = 1,
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 20.dp),
              colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = greendc,
                  unfocusedBorderColor = Color.Black

              ),
              isError = numberholder.value.length>10,
              shape = RoundedCornerShape(
                  corner = CornerSize(7.dp),
              )
          )
          Spacer(Modifier.height(6.dp))
          OutlinedTextField(value = emailholder.value,
              onValueChange = {
                  emailholder.value = it
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

fun resizeAndCompressImage(imageBytes: ByteArray, maxWidth: Int, maxHeight: Int, quality: Int = 80): ByteArray {
    // Decode the ByteArray into a Bitmap
    val originalBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    // Calculate the new dimensions while maintaining aspect ratio
    val (newWidth, newHeight) = calculateResizedDimensions(originalBitmap.width, originalBitmap.height, maxWidth, maxHeight)

    // Resize the Bitmap
    val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)

    // Recycle the original Bitmap
    originalBitmap.recycle()

    // Create a ByteArrayOutputStream to hold the compressed data
    val byteArrayOutputStream =
        com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream()

    // Compress the resized Bitmap to JPEG format with the specified quality
    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)

    // Recycle the resized Bitmap
    resizedBitmap.recycle()

    // Return the compressed data as a ByteArray
    return byteArrayOutputStream.toByteArray()
}
fun calculateResizedDimensions(originalWidth: Int, originalHeight: Int, maxWidth: Int, maxHeight: Int): Pair<Int, Int> {
    val ratio = originalWidth.toFloat() / originalHeight.toFloat()

    return if (originalWidth > originalHeight) {
        val newWidth = maxWidth
        val newHeight = (newWidth / ratio).toInt()
        Pair(newWidth, newHeight)
    } else {
        val newHeight = maxHeight
        val newWidth = (newHeight * ratio).toInt()
        Pair(newWidth, newHeight)
    }
}
