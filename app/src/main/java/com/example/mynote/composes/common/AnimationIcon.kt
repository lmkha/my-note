package com.example.mynote.composes.common

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mynote.R

@Composable
fun AnimationIcon(
    modifier: Modifier = Modifier,
    sourceId: Int = R.raw.app_icon_animation,
    size: Int
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(sourceId)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        modifier = modifier.size(size.dp),
        composition = composition,
        progress = { progress }
    )
}