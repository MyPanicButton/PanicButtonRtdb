package com.example.panicbuttonrtdb.prensentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.panicbuttonrtdb.R
import com.example.panicbuttonrtdb.data.OnBoardingData
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoarding(navController: NavController) {
    val items = listOf(
        OnBoardingData(
            R.raw.problem,
            "Masalah",
            "Jika anda mendapat masalah di rumah anda seperti kemalingan atau apapun masalahnya yang membutuhkan bantuan sesegera mungkin"
        ),
        OnBoardingData(
            R.raw.confused,
            "Bingung",
            "Tapi anda bingung, siapa yang harus dihubungi untuk mengatasi masalah tersebut"
        ),
        OnBoardingData(
            R.raw.clicked,
            "Aktifkan Panic Button",
            "Tenang saja! anda bisa memanggil security hanya dengan sekali klik"
        ),
        OnBoardingData(
            R.raw.problem_solved,
            "Masalah Diatasi",
            "Setelah mendapat pesan darurat, security akan sesegera mungkin ke rumah anda untuk mengatasi masalah"
        )
    )

    val pagerState = rememberPagerState( pageCount = { items.size })

    OnBoardingPager(
        items = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        navController = navController
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPager(
    modifier: Modifier = Modifier,
    items: List<OnBoardingData>,
    pagerState: PagerState,
    navController: NavController
) {

    val scope = rememberCoroutineScope()

    Column(
        modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier
                .padding(top = 100.dp),
            state = pagerState,
            pageSize = PageSize.Fill,
            pageSpacing = 8.dp,
            contentPadding = PaddingValues(16.dp),
            pageContent = { page ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LoaderIntro(
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterHorizontally),
                        image = items[page].image
                    )
                    Text(
                        text = items[page]. title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = items[page].desc,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 40.dp, start = 8.dp, end = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
        DotsIndicator(
            totalDots = items.size,
            selectedIndex = pagerState.currentPage
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 70.dp, start = 24.dp, end = 24.dp)
                .background(Color.White),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }) {
                        Text("Kembali")
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                if (pagerState.currentPage < items.size - 1) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }) {
                        Text("Lanjut")
                    }
                }else {
                    TextButton(
                        onClick = {
                            navController.navigate("login") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }) {
                        Text("Start Now")

                    }
                }
            }
        }
    }
}

@Composable
fun LoaderIntro(
    modifier: Modifier = Modifier,
    image: Int
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(image))

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp, alignment = Alignment.CenterHorizontally),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        for (i in 0 until totalDots) {
            val color = if (i == selectedIndex) Color.Black else Color.Gray
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color = color, shape = CircleShape),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun View() {
    val items = listOf(
        OnBoardingData(
            R.raw.problem,
            "Masalah",
            "Jika anda mendapat masalah di rumah anda seperti kemalingan atau apapun masalahnya yang membutuhkan bantuan sesegera mungkin"
        ),
        OnBoardingData(
            R.raw.confused,
            "Bingung",
            "Tapi anda bingung, siapa yang harus dihubungi untuk mengatasi masalah tersebut"
        ),
        OnBoardingData(
            R.raw.clicked,
            "Aktifkan Panic Button",
            "Tenang saja! anda bisa memanggil security hanya dengan sekali klik"
        ),
        OnBoardingData(
            R.raw.problem_solved,
            "Masalah Diatasi",
            "Setelah mendapat pesan darurat, security akan sesegera mungkin ke rumah anda untuk mengatasi masalah"
        )
    )

    val pagerState = rememberPagerState(pageCount = {items.size})

    OnBoardingPager(
        items = items,
        pagerState = pagerState,
        navController = rememberNavController()
    )
}