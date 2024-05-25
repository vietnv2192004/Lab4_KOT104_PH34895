package com.example.lab4_kot104

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
//import androidx.compose.material.BottomNavigation
//import androidx.compose.material.BottomNavigationItem
//import androidx.compose.material.SnackbarHost
//import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.lab4_kot104.R
import kotlinx.coroutines.launch


sealed class Screen(val route: String, val icon: Int, val title: String) {
    object Home : Screen("home", android.R.drawable.ic_menu_view, "Trang chủ")
    object History : Screen("history", android.R.drawable.ic_menu_recent_history, "Lịch sử")
    object Cart : Screen("cart", android.R.drawable.ic_menu_compass, "Giỏ hàng")
    object Profile : Screen("profile", android.R.drawable.ic_menu_save, "Hồ sơ")
}

private class ItemThanhToanModel (var color: Color, var idRes: Int, var title: String)

class Lab3Activity : ComponentActivity() {



    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()

            var selectedMethod by remember { mutableStateOf<ItemThanhToanModel?>(null) }
//
//            // Tạo 1 list gồm các PaymentMethod component là các item row chứa: Ảnh, tên phương thức, radiobutton tích chọn
//            val paymentMethods = listOf(
//                ItemThanhToan(title = "PayPal", idRes = R.drawable.ic_logo_paypal, color = Color(0xFFFFA726)),
//                ItemThanhToan(title = "Momo", idRes = R.drawable.ic_logo_momo, color = Color(0xFFF48FB1)),
//                ItemThanhToan(title = "Zalo Pay", idRes = R.drawable.ic_logo_zalopay, color = Color(0xFF81D4FA)),
//                ItemThanhToan(title = "Thanh toán trực tiếp", idRes = R.drawable.ic_logo_zalopay, color = Color(0xFF80CBC4))
//            )

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
                    BottomNavigationBar(navController = navController)
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) {innerPadding ->

                NavigationGraph(navController = navController)


                GetLayout(selectedMethod = selectedMethod,
                    innerPadding = innerPadding,
                    onMethodSelected = { method ->
                    selectedMethod = method
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Selected Payment Method: ${method.title}")
                    }
                })
            }
        }
    }

    //    ----------------

    //    các màn để chuyến hướng trong bottom nav bar
    @Composable
    fun HomeScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        }
    }

    @Composable
    fun HistoryScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Lịch sử", style = MaterialTheme.typography.titleSmall)
        }
    }

    @Composable
    fun CartScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Giỏ hàng", style = MaterialTheme.typography.titleSmall)
        }
    }

    @Composable
    fun ProfileScreen() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Hồ sơ", style = MaterialTheme.typography.titleSmall)
        }
    }


    //     hàm tạo thành phần bottom nav bar. Trong đó kết hợp sử dụng NavigationBar và NavigationBarItem
    @Composable
    private fun BottomNavigationBar(navController: NavHostController) {

        // Tạo list dựa vào các object đã khai báo ở main
        val items = listOf(
            Screen.Home,
            Screen.History,
            Screen.Cart,
            Screen.Profile
        )

        NavigationBar(
            containerColor = Color("#302C2C".toColorInt())
        ) {
            // trả về thông tin của màn hình hiện tại( đường dẫn ,trạng thái màn hình,Trạng thái vòng đời của màn hình,..)
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val currentRoute = navBackStackEntry?.destination?.route
            // kiểm tra xem các mục trong điều hướng có trùng với đường dẫn màn hình đc lưu trong biến currentRoute hay ko

            items.forEach { screen ->
                NavigationBarItem(

                    icon = {
                        Icon(
                            painterResource(id = screen.icon),
                            contentDescription = screen.title
                        )
                    },
                    label = { Text(screen.title) },
                    selected = currentRoute == screen.route, // nếu danh mục được chọn, trả về currentRoute= link đường dẫn đến danh mục được chọn
                    onClick = {
                        navController.navigate(screen.route) {
                            // Điều hướng đến một màn hình duy nhất, không tạo thêm bản sao
                            launchSingleTop = true
                            // Khôi phục trạng thái đã lưu
                            restoreState = true
                            // Xóa tất cả các trang trước trang đích để tránh chồng chất trang
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = colorResource(id = android.R.color.holo_orange_dark),
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = colorResource(id = android.R.color.holo_orange_dark),
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController) {

//         nhận nhiệm vụ xử lí chính cho việc chuyển màn khi ấn vào icon của thanh điều hướng
        // truyền và o 3 tham số : navController là công cụ thuộc thư việ có sẵn của jetpack compose giúp xử lí về bottom nav bar
        NavHost(navController, startDestination = Screen.Home.route) {

            // tùy vào route object nào sẽ navigate đến fun component đó
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.History.route) { HistoryScreen() }
            composable(Screen.Cart.route) { CartScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }

        }

    }


    //@Preview
    @Composable
    private fun GetLayout (innerPadding : PaddingValues = PaddingValues(), selectedMethod : ItemThanhToanModel? = null, onMethodSelected: (ItemThanhToanModel) -> Unit) {

        val listItemThanhToan : MutableList<ItemThanhToanModel> = mutableListOf()


        listItemThanhToan.add(ItemThanhToanModel(color = Color("#EB8B33".toColorInt()), R.drawable.ic_logo_paypal, "Paypal"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#D93485".toColorInt()), R.drawable.ic_logo_momo, "Momo"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#57BFF7".toColorInt()), R.drawable.ic_logo_zalopay, "Zalo Pay"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#EB8B33".toColorInt()), R.drawable.ic_logo_paypal, "Paypal"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#D93485".toColorInt()), R.drawable.ic_logo_momo, "Momo"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#57BFF7".toColorInt()), R.drawable.ic_logo_zalopay, "Zalo Pay"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#EB8B33".toColorInt()), R.drawable.ic_logo_paypal, "Paypal"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#D93485".toColorInt()), R.drawable.ic_logo_momo, "Momo"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#57BFF7".toColorInt()), R.drawable.ic_logo_zalopay, "Zalo Pay"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#EB8B33".toColorInt()), R.drawable.ic_logo_paypal, "Paypal"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#D93485".toColorInt()), R.drawable.ic_logo_momo, "Momo"))

        listItemThanhToan.add(ItemThanhToanModel(color = Color("#57BFF7".toColorInt()), R.drawable.ic_logo_zalopay, "Zalo Pay"))

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
    private fun GetLayoutDiaChiNhanHang () {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            GetTextContent(text = "Địa chỉ nhận hàng")
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)){
                Image(Icons.Default.LocationOn,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Red),)
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
    private fun GetTextContent (text: String) {
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