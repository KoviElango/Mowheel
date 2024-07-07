package com.example.mowheel

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.star
import androidx.graphics.shapes.toPath


class MorphPolygonShape(
    private val morph: Morph,
    private val percentage: Float
) : androidx.compose.ui.graphics.Shape {

    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val matrix = androidx.compose.ui.graphics.Matrix()
        matrix.scale(size.width / 2f, size.height / 2f)
        matrix.translate(1f, 1f)

        val path = morph.toPath(progress = percentage).asComposePath()
        path.transform(matrix)
        return Outline.Generic(path)
    }
}

@Composable
fun MovieButton(isLoading: Boolean, onClick: () -> Unit) {
    val shapeA = remember {
        RoundedPolygon(
            6,
            rounding = CornerRounding(0.2f)
        )
    }
    val shapeB = remember {
        RoundedPolygon.star(
            6,
            rounding = CornerRounding(0.1f)
        )
    }
    val morph = remember {
        Morph(shapeA, shapeB)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed by interactionSource.collectIsPressedAsState()
    val animatedProgress = animateFloatAsState(
        targetValue = if (isPressed) 1f else 0f,
        label = "progress",
        animationSpec = spring(dampingRatio = 0.4f, stiffness = Spring.StiffnessMedium)
    )
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
            .clip(MorphPolygonShape(morph, animatedProgress.value))
            .graphicsLayer {
                shape = androidx.compose.foundation.shape.CircleShape
                clip = true
                alpha = 0.8f // Adjusted for semi-transparency
                shadowElevation = 8.dp.toPx()
            }
            .background(Color.White.copy(alpha = 0.5f)) // Adjusted for semi-transparency
            .clickable(interactionSource = interactionSource, indication = null) {
                onClick()
            }
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            EmbossedText("Movie!", Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun EmbossedText(text: String, modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            text = text,
            color = Color.Black.copy(alpha = 0.75f),
            modifier = Modifier.graphicsLayer(
                translationX = 2f,
                translationY = 2f
            )
        )
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.graphicsLayer(
                translationX = -2f,
                translationY = -2f
            )
        )
        Text(
            text = text,
            color = Color.Black
        )
    }
}


