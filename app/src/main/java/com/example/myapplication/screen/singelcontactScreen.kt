package com.example.myapplication.screen

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Whatsapp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data_layer.contactviewmodel
import com.example.myapplication.database.contacttable
import com.example.myapplication.ui.theme.greendc
import com.example.myapplication.ui.theme.whatsappiconcolour
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun singel(
    viewmodel: contactviewmodel,
    navcontroll: NavHostController,
    datasingelscreen: dataseanglescreen,
    name: String,
    id: Int,
    number: String,
    email: String
) {

    var contaxt = LocalContext.current
    Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = {

                 navcontroll.navigate(
                    MainActivity.editscreen(id=id,name=name,email=email,number=number)
                 )


                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(0.06f),
                    ),
                        modifier = Modifier.padding(end = 19.dp)) {
                        Icon(
                            Icons.Outlined.Edit,null,
                            Modifier
                                .size(25.dp)
                                .padding(start = 0.dp))

                    }
                    IconButton(onClick = {



                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(0.06f),
                    ),
                        modifier = Modifier.padding(end = 4.dp)) {
                        Icon(
                            Icons.Outlined.StarOutline,null,
                            Modifier
                                .size(25.dp)
                                .padding(start = 0.dp))

                    }
                    IconButton(onClick = {
                        //
                       GlobalScope.launch (Dispatchers.Default){
                           viewmodel.db.contactdao().delete(contacttable(id = id, name = name, number = number, email = email, image = datasingelscreen.image.value))
                       }
                        navcontroll.popBackStack()
                        Toast.makeText(contaxt, "Delete success", Toast.LENGTH_SHORT).show()



                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(0.06f),
                    ),
                        modifier = Modifier.padding(end = 4.dp, start = 9.dp)) {

                        Icon(
                            Icons.Filled.Delete,null,
                            Modifier
                                .size(25.dp)
                                .padding(start = 0.dp))

                    }



                },
                navigationIcon = {
                    IconButton(onClick = {
                        navcontroll.popBackStack()




                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary.copy(0.06f),
                    ),
                        modifier = Modifier.padding(start = 4.dp)) {
                        Icon(
                            Icons.Outlined.ArrowBack,null,
                            Modifier
                                .size(25.dp)
                                .padding(start = 0.dp))

                    }

                }
            )

        }
    ){
        var contaxt = LocalContext.current
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(it),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally){
            Spacer(modifier = Modifier.height(35.dp))

            if (datasingelscreen.image.value.isEmpty()){
                Image(painterResource(R.drawable.user),null, modifier = Modifier.size(180.dp))

            }else{
                var image = BitmapFactory.decodeByteArray(datasingelscreen.image.value,0, datasingelscreen.image.value.size)
                Image(bitmap = image.asImageBitmap(),contentDescription = null,
                    Modifier
                        .size(180.dp)
                        .clip(
                            RoundedCornerShape(90.dp)
                        ), contentScale = ContentScale.Crop)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = Color.Black.copy(0.8f), fontFamily = FontFamily.Default)
            Spacer(modifier = Modifier.height(26.dp))
            Row  (Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                Column(Modifier.size(90.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {


                        val intent = Intent(Intent.ACTION_CALL).apply {
                            data = Uri.parse("tel:${number}")
                        }
                        contaxt.startActivity(intent)


                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor =  greendc.copy(0.1f)
                    ),
                        modifier = Modifier
                            .padding(end = 0.dp)
                            .size(50.dp)) {
                        Icon(
                            Icons.Outlined.Call,null,
                            Modifier
                                .size(23.dp)
                                .padding(start = 0.dp))

                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("call", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black.copy(0.9f) )
                }

                Column(Modifier.size(90.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {

                       // message text
                        val intentmessage = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("smsto:${number}")
                        }
                        contaxt.startActivity(intentmessage)


                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor =  greendc.copy(0.1f)
                    ),
                        modifier = Modifier
                            .padding(end = 0.dp)
                            .size(50.dp)) {
                        Icon(
                            Icons.Outlined.Message,null,
                            Modifier
                                .size(23.dp)
                                .padding(start = 0.dp))

                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("text", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black.copy(0.9f) )
                }
                Column(Modifier.size(90.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = {
                        Log.d("WhatsAppDebug", "sendWhatsAppMessage called")
                        Log.d("WhatsAppDebug", "Phone Number: $number")
                        Log.d("WhatsAppDebug", "Message: $name")

                        val encodedMessage = Uri.encode("hyy")
                        Log.d("WhatsAppDebug", "Encoded Message: $encodedMessage")

                        val url = "https://wa.me/$number?text=$encodedMessage"
                        Log.d("WhatsAppDebug", "URL: $url")

                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(url)
                            setPackage("com.whatsapp") // Explicitly target WhatsApp
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Add this flag
                        }
                        Log.d("WhatsAppDebug", "Intent created: $intent")

                        if (intent.resolveActivity(contaxt.packageManager) != null) {
                            Log.d("WhatsAppDebug", "WhatsApp is installed")
                            contaxt.startActivity(intent)
                            Log.d("WhatsAppDebug", "startActivity called for WhatsApp")
                        } else {
                            Log.d("WhatsAppDebug", "WhatsApp is NOT installed")
                            Toast.makeText(contaxt, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
                            // Or, direct the user to the Play Store:
                            val playStoreIntent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")
                                setPackage("com.android.vending") // Optional: Ensure Play Store handles it
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Add this flag
                            }
                            Log.d("WhatsAppDebug", "Play Store Intent created: $playStoreIntent")
                            if (playStoreIntent.resolveActivity(contaxt.packageManager) != null) {
                                Log.d("WhatsAppDebug", "Play Store is installed")
                                contaxt.startActivity(playStoreIntent)
                                Log.d("WhatsAppDebug", "startActivity called for Play Store")
                            } else {
                                Log.d("WhatsAppDebug", "Play Store is NOT installed")
                            }
                        }






                    }, colors = IconButtonDefaults.iconButtonColors(
                        containerColor =  greendc.copy(0.1f)
                    ),
                        modifier = Modifier
                            .padding(end = 0.dp)
                            .size(50.dp)) {
                        Icon(
                            Icons.Rounded.Whatsapp,null,
                            Modifier
                                .size(23.dp)
                                .padding(start = 0.dp),
                            tint = whatsappiconcolour
                        )

                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Whatsapp", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.Black.copy(0.9f) )
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(14.dp)
                    .clickable {
                        // click effect
                    }
                    .background(
                        color = MaterialTheme.colorScheme.secondary.copy(0.04f),
                        shape = RoundedCornerShape(14.dp)
                    )) {
                Text("Contact info", fontSize = 16.sp, fontWeight = FontWeight.SemiBold,
                    color = Color.Black.copy(0.8f), modifier = Modifier.padding(start = 14.dp, top = 16.dp ))
                Spacer(modifier = Modifier.height(6.dp))
                Row (
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp, top = 20.dp, end = 14.dp), verticalAlignment = Alignment.CenterVertically){
                    Icon(Icons.Outlined.Call,null,
                        Modifier
                            .size(26.dp)
                            .clickable {
                                //call
                                val intent = Intent(Intent.ACTION_CALL).apply {
                                    data = Uri.parse("tel:${number}")
                                }
                                contaxt.startActivity(intent)
                            }, tint = Color.Black.copy(0.7f))
                    Spacer(modifier = Modifier.widthIn(14.dp))
                    Text("+91 ${number}", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black.copy(0.7f))
                    Spacer(modifier = Modifier.widthIn(100.dp))
                    Icon(Icons.Outlined.Message,null,
                        Modifier
                            .size(26.dp)
                            .clickable {
                                // message
                                val intentmessage = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("smsto:${number}")

                                }
                                contaxt.startActivity(intentmessage)
                            }, tint = Color.Black.copy(0.7f))

                }


            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider()





        }

    }
}
@Composable
fun CallButton(phoneNumber: String) {
    val context = LocalContext.current
    val callPermissionGranted = remember { mutableStateOf(false) }

    // Launcher for requesting the CALL_PHONE permission
    val requestCallPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            callPermissionGranted.value = isGranted
            if (isGranted) {
                makePhoneCall(context, phoneNumber)
            } else {
                Toast.makeText(context, "Call permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    )}
fun makePhoneCall(context: Context, phoneNumber: String) {
    Log.d("CallDebug", "makePhoneCall called")
    Log.d("CallDebug", "Phone Number: $phoneNumber")

    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:$phoneNumber")
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        Log.d("CallDebug", "Call is possible")
        context.startActivity(intent)
        Log.d("CallDebug", "startActivity called for call")
    } else {
        Log.d("CallDebug", "Call is NOT possible")
        Toast.makeText(context, "Call is not possible.", Toast.LENGTH_SHORT).show()
    }
}