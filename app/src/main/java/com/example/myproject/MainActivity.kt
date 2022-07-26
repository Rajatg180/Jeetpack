package com.example.myproject

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myproject.ui.theme.MyProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),

                    color = MaterialTheme.colors.background
                ) {
                    CreateBizCard()
                }
            }
        }
    }
}

@Composable
fun CreateBizCard() {
    val buttonClickedState = remember{
       mutableStateOf(false)
    }

   androidx.compose.material.Surface(modifier = Modifier
       .fillMaxWidth()
       .fillMaxHeight()) {
       Card(modifier = Modifier
           .width(200.dp)
           .height(400.dp)
           .padding(7.dp),
           shape = RoundedCornerShape(corner = CornerSize(10.dp)),
           backgroundColor = Color.Gray,
           elevation = 4.dp) {
           Column(modifier = Modifier.height(300.dp)
           , verticalArrangement = Arrangement.Top
           , horizontalAlignment = Alignment.CenterHorizontally) {
               ImageProfile()
               Divider(  color = Color.Black, thickness = 0.5.dp)
               PersonalInfo()
               Button(
                   onClick = {
                             buttonClickedState.value= !buttonClickedState.value


                   },
                   colors = ButtonDefaults.buttonColors(
                       backgroundColor = Color.White,
                       contentColor = Color.Black)) { Text(text = "Portfolio", style = MaterialTheme.typography.button)



               }
               if(buttonClickedState.value){
                   Project()
               }else{
                   Box(){

                   }
               }
           }


       }
       
   }
}
//@Preview
@Composable
 fun Project(){
     Box(modifier = Modifier
         .fillMaxWidth()
         .fillMaxHeight()
         .padding(5.dp)){
         Surface(modifier = Modifier
             .padding(3.dp)
             .fillMaxHeight()
             .fillMaxWidth()
             , shape = RoundedCornerShape(corner = CornerSize(6.dp))
             , border = BorderStroke(width = 2.dp, color = Color.Black)
             , color = Color.LightGray
         , elevation = 10.dp) {
             Portfolio(data= listOf("Roll Dice","Age in Minute","Tip Time","Meme Share","Easy Notes"))


         }

     }

}

@Composable
fun Portfolio(data: List<String>) {
    LazyColumn{
        items(data){
            item ->
            Card(modifier = Modifier
                .padding(13.dp)
                .fillMaxWidth()
            , shape = RectangleShape
            , elevation = 10.dp
            ) {
                Row(modifier = Modifier
                    .padding(8.dp)
                    .background(MaterialTheme.colors.surface)
                    .padding(16.dp)) {
                    ImageProfile(modifier = Modifier.size(100.dp))

                     Column(modifier = Modifier.padding(7.dp).align(Alignment.CenterVertically)) {
                         Text(text = item, fontSize =30.sp , fontWeight = FontWeight.Bold)
                         Text(text = "Android Project", fontSize =20.sp)

                     }
                }
            }
        }
    }

}

@Composable
private fun PersonalInfo() {
    Column(
        modifier = Modifier.padding(5.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rajat Gore", style = MaterialTheme.typography.h4, color = Color.Black , fontWeight = FontWeight.Bold
        )
        HyperlinkText(
            fullText = "Github",
            linkText = listOf("Github"),
            hyperlinks = listOf("https://github.com/Rajatg180"),
            fontSize = MaterialTheme.typography.h5.fontSize
        )
        HyperlinkText(
            fullText = "Linkedin",
            linkText = listOf("Linkedin"),
            hyperlinks = listOf("https://www.linkedin.com/in/rajat-gore-098088228/"),
            fontSize = MaterialTheme.typography.h5.fontSize
        )

    }
}

@Composable
fun HyperlinkText(modifier: Modifier = Modifier,
                  fullText: String,
                  linkText: List<String>,
                  linkTextColor: Color = Color.Blue,
                  linkTextFontWeight: FontWeight = FontWeight.Medium,
                  linkTextDecoration: TextDecoration = TextDecoration.Underline,
                  hyperlinks: List<String>,
                  fontSize: TextUnit=TextUnit.Unspecified) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )

}

@Composable
private fun ImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .size(150.dp)
            .padding(5.dp)
            .clip(CircleShape), elevation = 20.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "profile image",
            modifier = Modifier.size(20.dp),
            contentScale = ContentScale.Crop

        )

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyProjectTheme {
        CreateBizCard()
    }
}