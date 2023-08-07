package com.artemissoftware.orpheusplaylist.headphone.presentation.playlist.composables

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.artemissoftware.orpheusplaylist.headphone.util.audio.VisualizerData
import com.artemissoftware.orpheusplaylist.headphone.util.audio.VisualizerHelper
import java.lang.Math.PI
import java.lang.Math.sin
import kotlin.math.roundToInt

@JvmInline
private value class Point(private val p: Pair<Float, Float>) {

    fun x() = p.first
    fun y() = p.second

    operator fun plus(other: Point) = Point(x() + other.x() to y() + other.y())
    operator fun minus(other: Point) = Point(x() - other.x() to y() - other.y())
    operator fun times(factor: Float) = Point(x() * factor to y() * factor)
    operator fun div(factor: Float) = times(1f / factor)
}

@Composable
fun StackedBarVisualizer(
    data: VisualizerData,
    barCount: Int,
    modifier: Modifier = Modifier,
    maxStackCount: Int = 32,
    shape: Shape = RoundedCornerShape(size = 8.dp),
    barColors: List<Color> = listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
    ),
    stackBarBackgroundColor: Color = Color.Gray,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    Row(modifier = modifier.onSizeChanged { size = it }) {
        val viewportWidth = size.width.toFloat()
        val viewportHeight = size.height.toFloat()
        val padding = LocalDensity.current.run { 1.dp.toPx() }

        val nodes = calculateStackedBarPoints(
            resampled = data.resample(barCount),
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            barCount = barCount,
            maxStackCount = maxStackCount,
            horizontalPadding = padding,
            verticalPadding = padding,
        ).mapIndexed { index, point ->
            if (index % 4 == 0) {
                PathNode.MoveTo(point.x(), point.y())
            } else {
                PathNode.LineTo(point.x(), point.y())
            }
        }

        val vectorPainter = rememberVectorPainter(
            defaultHeight = viewportHeight.dp,
            defaultWidth = viewportWidth.dp,
            viewportHeight = viewportHeight,
            viewportWidth = viewportWidth,
            autoMirror = false,
        ) { _, _ ->
            Path(
                fill = Brush.linearGradient(
                    colors = barColors,
                    start = Offset.Zero,
                    end = Offset(0f, Float.POSITIVE_INFINITY),
                ),
                pathData = nodes,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = shape),
        ) {
            StackedBarVisualizerBackground(
                barCount = barCount,
                viewportWidth = viewportWidth,
                viewportHeight = viewportHeight,
                horizontalPadding = padding,
                verticalPadding = padding,
                stackBarBackgroundColor = stackBarBackgroundColor,
            )
            Image(
                painter = vectorPainter,
                contentDescription = "",
            )
        }
    }
}

@Composable
private fun calculateStackedBarPoints(
    resampled: IntArray,
    viewportWidth: Float,
    viewportHeight: Float,
    barCount: Int,
    maxStackCount: Int,
    horizontalPadding: Float,
    verticalPadding: Float,
): List<Point> {
    val barWidth = (viewportWidth / barCount) - horizontalPadding
    val stackHeightWithPadding = viewportHeight / maxStackCount
    val stackHeight = stackHeightWithPadding - verticalPadding

    val nodes = mutableListOf<Point>()

    resampled.forEachIndexed { index, d ->

        val stackCount = animateIntAsState(
            targetValue = (maxStackCount * (d / 128f)).roundToInt(),
            animationSpec = tween(durationMillis = VisualizerHelper.SAMPLING_INTERVAL),
        )

        for (stackIndex in 0..stackCount.value) {
            nodes += Point(
                barWidth * index + horizontalPadding * index to
                    viewportHeight - stackIndex * stackHeight - verticalPadding * stackIndex,
            )
            nodes += Point(
                barWidth * (index + 1) + horizontalPadding * index to
                    viewportHeight - stackIndex * stackHeight - verticalPadding * stackIndex,
            )
            nodes += Point(
                barWidth * (index + 1) + horizontalPadding * index to
                    viewportHeight - (stackIndex + 1) * stackHeight - verticalPadding * stackIndex,
            )
            nodes += Point(
                barWidth * index + horizontalPadding * index to
                    viewportHeight - (stackIndex + 1) * stackHeight - verticalPadding * stackIndex,
            )
        }
    }
    return nodes
}


@Composable
private fun StackedBarVisualizerBackground(
    barCount: Int,
    maxStackCount: Int = 32,
    stackBarBackgroundColor: Color = Color.Gray,
    viewportWidth: Float,
    viewportHeight: Float,
    horizontalPadding: Float,
    verticalPadding: Float,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        val nodes = calculateStackedBarPoints(
            resampled = VisualizerData.getMaxProcessed(resolution = barCount),
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            barCount = barCount,
            maxStackCount = maxStackCount,
            horizontalPadding = horizontalPadding,
            verticalPadding = verticalPadding,
        ).mapIndexed { index, point ->
            if (index % 4 == 0) {
                PathNode.MoveTo(point.x(), point.y())
            } else {
                PathNode.LineTo(point.x(), point.y())
            }
        }

        val vectorPainter = rememberVectorPainter(
            defaultHeight = viewportHeight.dp,
            defaultWidth = viewportWidth.dp,
            viewportHeight = viewportHeight,
            viewportWidth = viewportWidth,
            autoMirror = false,
        ) { _, _ ->
            Path(
                fill = SolidColor(stackBarBackgroundColor),
                pathData = nodes,
            )
        }

        Image(
            painter = vectorPainter,
            contentDescription = "",
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun StackedBarVisualizerPreview() {
    val sampleRate = 44100 // Sample rate in Hz
    val duration = 2.0 // Duration of the waveform in seconds
    val frequency = 440.0 // Frequency of the sine wave in Hz
    val amplitude = 127.0 // Amplitude of the waveform (from -128 to 127)

    val rawWaveform = generateSineWaveSample(frequency, amplitude, sampleRate, duration)

    StackedBarVisualizer(
        stackBarBackgroundColor = Color.Green,
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 100.dp),
        data = VisualizerData(
            rawWaveform = rawWaveform,
            captureSize = rawWaveform.size,
        ),
        barCount = 32,

    )
}

fun generateSineWaveSample(frequency: Double, amplitude: Double, sampleRate: Int, duration: Double): ByteArray {
    val numSamples = (duration * sampleRate).toInt()
    val samples = ByteArray(numSamples)

    val angularFrequency = 2.0 * PI * frequency / sampleRate
    for (i in 0 until numSamples) {
        val sampleValue = (amplitude * sin(i * angularFrequency)).toInt().toByte()
        samples[i] = sampleValue
    }

    return samples
}
















@Composable
private fun StackedBarVisualizerBackground_(
//    barCount: Int,
//    maxStackCount: Int = 32,
    stackBarBackgroundColor: Color = Color.Gray,
    viewportWidth: Float,
    viewportHeight: Float,
//    horizontalPadding: Float,
//    verticalPadding: Float,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        val nodes = listOf(Point(p = Pair(1F, 1F)), Point(p = Pair(2F, 2F)), Point(p = Pair(1F, 1F)), Point(p = Pair(4F, 4F))).mapIndexed { index, point ->
            if (index % 4 == 0) {
                PathNode.MoveTo(point.x(), point.y())
            } else {
                PathNode.LineTo(point.x(), point.y())
            }
        }

//        val nodes = calculateStackedBarPoints(
//            resampled = VisualizerData.getMaxProcessed(resolution = barCount),
//            viewportWidth = viewportWidth,
//            viewportHeight = viewportHeight,
//            barCount = barCount,
//            maxStackCount = maxStackCount,
//            horizontalPadding = horizontalPadding,
//            verticalPadding = verticalPadding,
//        ).mapIndexed { index, point ->
//            if (index % 4 == 0) {
//                PathNode.MoveTo(point.x(), point.y())
//            } else {
//                PathNode.LineTo(point.x(), point.y())
//            }
//        }

        val vectorPainter = rememberVectorPainter(
            defaultHeight = viewportHeight.dp,
            defaultWidth = viewportWidth.dp,
            viewportHeight = viewportHeight,
            viewportWidth = viewportWidth,
            autoMirror = false,
        ) { _, _ ->
            Path(
                fill = SolidColor(stackBarBackgroundColor),
                pathData = nodes,
            )
        }

        Image(
            painter = vectorPainter,
            contentDescription = "",
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StackedBarVisualizerBackgroundPreview() {
    StackedBarVisualizerBackground_(
        stackBarBackgroundColor = Color.Green,
        viewportWidth = 200F,
        viewportHeight = 200F,
    )
}
