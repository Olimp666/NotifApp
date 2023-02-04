/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.notifapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ListHeader
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import com.example.notifapp.presentation.theme.NotifAppTheme
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.internal.wait
import java.io.IOException


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp("fsasf")
        }
    }

    fun getColor(text: String): Color {
        if (text == "approved")
            return Color.Green
        if (text == "declined")
            return Color.Red
        if (text == "pending")
            return Color.Yellow
        return Color.White
    }

    @Composable
    fun WearApp(greetingName: String) {
        NotifAppTheme {

//            val phoneNumber = "79142924823"
//            val host = "/api/v1/user/getAllNotification"

            val client = OkHttpClient()

            val request = Request.Builder()
                .url("http://fefu-project.site/api/v1/user/getAllNotification?phone=81231231212")
                .build()

            var text = "[]"
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {

                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")

                        for ((name, value) in response.headers) {
                            println("$name: $value")
                        }
                        text = response.body!!.string()
                        println(text)

                    }
                }
            })
            Thread.sleep(1000)
            val obj = Json.decodeFromString<MutableList<Notification>>(text) //obj пустой


            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    ListHeader {
                        Text(text = "Уведомления")
                    }
                }
                items(obj.size) {
                    Column(
                        modifier = Modifier
                            .border(Dp.Hairline, Color.White, CircleShape)
                            .fillParentMaxWidth()
                    ) {

                        Text(
                            modifier = Modifier.fillParentMaxWidth(),
                            text = obj[it].status,
                            color = getColor(obj[it].status),
                            textAlign = TextAlign.Center,
                            fontSize = 11.sp,
                        )
                        Text(
                            modifier = Modifier.fillParentMaxWidth(),
                            text = obj[it].description,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }
//                    Chip(
//                        onClick = { },
//                        label = { Text(obj[it].status) },
//                        secondaryLabel = { Text(obj[it].description) },
//                        colors = ChipDefaults.secondaryChipColors()
//                    )
                }
            }
        }
    }
}

//@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
//@Composable
//fun DefaultPreview() {
//    WearApp("Preview Android")
//}
