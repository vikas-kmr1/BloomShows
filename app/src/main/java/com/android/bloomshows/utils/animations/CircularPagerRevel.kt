import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.android.bloomshows.presentation.on_boarding.DataOnBoarding
import com.android.bloomshows.presentation.on_boarding.LabelGroup
import com.android.bloomshows.presentation.on_boarding.TopContent
import com.android.bloomshows.ui.components.ButtonWithIndicator
import com.android.bloomshows.ui.components.WormPageIndicator
import com.android.bloomshows.ui.theme.MediumPadding
import com.android.bloomshows.ui.theme.SmallPadding
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CircleRevealPager(
    slides: List<DataOnBoarding>,
    navigate_to_login: () -> Unit = {}
) {
    val pagerState = rememberPagerState(0)
    var offsetY by remember { mutableStateOf(0f) }
    val pageCount = slides.size
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            pageCount = slides.size,
            modifier = Modifier
                .pointerInteropFilter {
                    offsetY = it.y
                    false
                }
                .clip(
                    RoundedCornerShape(25.dp)
                )
                .background(Color.Black),
            state = pagerState,
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val pageOffset = pagerState.offsetForPage(page)
                        translationX = size.width * pageOffset

                        val endOffset = pagerState.endOffsetForPage(page)

                        shadowElevation = 20f
                        shape = CirclePath(
                            progress = 1f - endOffset.absoluteValue,
                            origin = Offset(
                                // set the intial position, from where the reveal shouls start
                                size.width,
                                size.height,//set this to offsetY, to start the revel,at the point the user touched
                            )
                        )
                        clip = true

                        val absoluteOffset = pagerState.offsetForPage(page).absoluteValue
                        val scale = 1f + (absoluteOffset.absoluteValue * .4f)

                        scaleX = scale
                        scaleY = scale

                        val startOffset = pagerState.startOffsetForPage(page)
                        alpha = (2f - startOffset) / 2f

                    },
                contentAlignment = Alignment.Center,
            ) {
                Box(modifier = Modifier.fillMaxSize().background(slides[page].backgroundColor))
                Box(modifier = Modifier.fillMaxSize()) {
                    TopContent(
                        modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
                            .offset(y = 30.dp)
                            .windowInsetsPadding(WindowInsets.statusBars),
                        navigate_to_login = { navigate_to_login() },
                        pagerState.currentPage
                    )


                    Column(
                        modifier = Modifier.align(Alignment.Center).fillMaxWidth()
                            .offset(y = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = SmallPadding),
                            contentScale = ContentScale.FillWidth,
                            painter = painterResource(slides[pagerState.currentPage].illustration),
                            contentDescription = "slide ${pagerState.currentPage} illustrations"
                        )
                        LabelGroup(
                            slides[pagerState.currentPage],
                            Modifier
                        )

                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(MediumPadding)
        ) {
            WormPageIndicator(
                modifier = Modifier.align(Alignment.BottomCenter),
                totalPages = pageCount,
                currentPage = pagerState.currentPage
            )
            ButtonWithIndicator(
                modifier = Modifier.align(Alignment.BottomEnd),
                pagerState = pagerState,
                nav_to_login = navigate_to_login
            )
        }
    }
}

class CirclePath(private val progress: Float, private val origin: Offset = Offset(0f, 0f)) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {

        val center = Offset(
            x = size.center.x - ((size.center.x - origin.x) * (1f - progress)),
            y = size.center.y - ((size.center.y - origin.y) * (1f - progress)),
        )
        val radius = (sqrt(
            size.height * size.height + size.width * size.width
        ) * .5f) * progress

        return Outline.Generic(Path().apply {
            addOval(
                Rect(
                    center = center,
                    radius = radius,
                )
            )
        })
    }
}

// ACTUAL OFFSET
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

// OFFSET ONLY FROM THE LEFT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.startOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtLeast(0f)
}

// OFFSET ONLY FROM THE RIGHT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.endOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtMost(0f)
}
