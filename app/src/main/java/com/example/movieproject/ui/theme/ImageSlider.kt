package com.example.movieproject.ui.theme

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieproject.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider() {

    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3

    )

    val pagerState = rememberPagerState(initialPage = 1)



    HorizontalPager(
        pageCount = 3,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 30.dp),
        modifier = Modifier
            .padding(top = 24.dp, bottom = 22.dp)
            .height(196.dp),

        ) { index ->
            val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if(pageOffset != 0.0f) 0.9f else 1f
            )
        Log.d("TAG", "${pageOffset} ${imageSize}")
        Log.i("TAG", "${pagerState.currentPageOffsetFraction}")

            Image(
                painter = painterResource(id = images[index]),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(
                        start =
                    if(pagerState.currentPageOffsetFraction != 0.0f) 7.5.dp else 0.dp,
                        end = if(pagerState.currentPageOffsetFraction != 0.0f) 7.5.dp else 0.dp
                    )
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    }
                    .clip(RoundedCornerShape(10.dp))

            )


    }


}


@Preview
@Composable
fun ImageSliderPreview() {
    ImageSlider()
}