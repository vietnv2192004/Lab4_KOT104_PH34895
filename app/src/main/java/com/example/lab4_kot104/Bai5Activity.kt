package com.example.lab4_kot104

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.MaterialTheme.colors
//import androidx.compose.material.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab4_kot104.R
import kotlinx.coroutines.launch

private data class ItemThanhToan (var color: Color, var idRes: Int, var title: String)

class Bai5Activity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var selectedMethod by remember { mutableStateOf<ItemThanhToan?>(null) }
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color("#242121".toColorInt()),
                            titleContentColor = Color("#ffffff".toColorInt()),
                        ),
                        title = {
                            GetTextTitle("Thanh Toan")
                        }
                    )
                },
                // thành phần BottomNavigationBar
                bottomBar = {
//                    BottomNavigationBar(navController = navController)
                },
                snackbarHost = {
                    //SnackbarHost(hostState = snackbarHostState)
                }
            ) {innerPadding ->

                //NavigationGraph(navController = navController)


                GetLayout(selectedMethod = selectedMethod,
                    innerPadding = innerPadding,
                    onMethodSelected = { method ->
                        selectedMethod = method

                    })
            }
        }
    }

    @Composable
    private fun GetLayout (innerPadding : PaddingValues = PaddingValues(), selectedMethod : ItemThanhToan? = null, onMethodSelected: (ItemThanhToan) -> Unit) {

        val listItemThanhToan : MutableList<ItemThanhToan> = mutableListOf()

        //region tao list model
        listItemThanhToan.add(ItemThanhToan(color = Color("#EB8B33".toColorInt()), R.drawable.ic_logo_paypal, "Paypal"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#D93485".toColorInt()), R.drawable.ic_logo_momo, "Momo"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#57BFF7".toColorInt()), R.drawable.ic_logo_zalopay, "Zalo Pay"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#EB8B33".toColorInt()), R.drawable.ic_logo_paypal, "Paypal"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#D93485".toColorInt()), R.drawable.ic_logo_momo, "Momo"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#57BFF7".toColorInt()), R.drawable.ic_logo_zalopay, "Zalo Pay"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#EB8B33".toColorInt()), R.drawable.ic_logo_paypal, "Paypal"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#D93485".toColorInt()), R.drawable.ic_logo_momo, "Momo"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#57BFF7".toColorInt()), R.drawable.ic_logo_zalopay, "Zalo Pay"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#EB8B33".toColorInt()), R.drawable.ic_logo_paypal, "Paypal"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#D93485".toColorInt()), R.drawable.ic_logo_momo, "Momo"))

        listItemThanhToan.add(ItemThanhToan(color = Color("#57BFF7".toColorInt()), R.drawable.ic_logo_zalopay, "Zalo Pay"))
        //endregion

        val context = LocalContext.current

        Box (Modifier.fillMaxSize()) {
            Column (
                Modifier
                    .background(color = Color("#2A2727".toColorInt()))
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding() + 10.dp,
                        start = 24.dp,
                        bottom = 24.dp,
                        end = 24.dp
                    )){
                GetLayoutDiaChiNhanHang()

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
//            GetTextTitle("Trang chu")

                    items (listItemThanhToan) {
                            itemThanhToan ->
                        GetRowItem(itemThanhToan.color, itemThanhToan.idRes, itemThanhToan.title, selectedMethod == itemThanhToan, onSelected = {onMethodSelected(itemThanhToan)})

                    }

                }
            }

            Button(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(40.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color("#ED7B57".toColorInt())),
                onClick = {
                    Toast.makeText(context, selectedMethod?.title ?: "Chua chon phuong thuc thanh toan", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "Thanh Toan", color = Color.White)
            }
        }


    }



    @Composable
    private fun GetRowItem(color: Color, idRes: Int = R.drawable.ic_logo_momo, title: String = "Paypal", selected : Boolean, onSelected: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = color, shape = RoundedCornerShape(12.dp))
                .height(70.dp)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(id = idRes), contentDescription = "",
                modifier = Modifier.size(60.dp)
            )

            Text(
                text = title,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                color = Color.White,
                fontSize = 20.sp
            )

            RadioButton(selected = selected, onClick = {
                onSelected()
            })
        }
    }

    @Composable
    fun GetLayoutDiaChiNhanHang () {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            GetTextContent(text = "Địa chỉ nhận hàng")
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)){
                Image(
                    Icons.Default.LocationOn,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Magenta),)
                Column {
                    GetTextContent(text = "Tri | 222222")
                    GetTextContent(text = "22/153 Tan Thoi Tay")
                    GetTextContent(text = "quan 12")
                    GetTextContent(text = "TP HCM")
                }
            }

            GetTextContent(text = "Chọn phương thức thanh toán")
        }
    }

    @Composable
    fun GetTextContent (text: String) {
        Text(
            text = text,
            color = Color.White
        )
    }

    @Composable
    private fun GetTextTitle(title: String = "Trang chu") {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(0.dp, 16.dp)
                .fillMaxWidth()
        )
    }
}