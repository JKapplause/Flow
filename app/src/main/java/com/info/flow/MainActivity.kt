package com.info.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.info.flow.ui.theme.FlowTheme
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlowTheme {

                val viewModel: MyViewModel by viewModels()
                SecondScreen(viewModel = viewModel)

            }
        }
    }
}

@Composable
fun FirstScreen(viewModel : MyViewModel) {
    val counter = viewModel.countDownTimerFlow.collectAsState(initial = 10)
    Surface(color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = counter.value.toString(),
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )

        }

    }
}


@Composable
fun SecondScreen(viewModel: MyViewModel) {

    val liveDataValue = viewModel.liveData.observeAsState()
    val stateFlowValue = viewModel.stateFlow.collectAsState()
    val sharedFlowState = viewModel.sharedFlow.collectAsState(initial = "")

    Surface(color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    
                    Text(text = liveDataValue.value ?: "",
                    fontSize = 26.sp
                        )
                    Button(onClick ={
                        viewModel.changeLiveDataValue()
                    } ) {
                        Text(text = "LiveData Button")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(text = stateFlowValue.value ?: "",
                    fontSize = 26.sp
                        )
                    Button(onClick = {
                        viewModel.changeStateFlow()
                    }) {
                        Text(text = "StateFlow Button")
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(text = sharedFlowState.value ?: "",
                        fontSize = 26.sp
                    )
                    Button(onClick = {
                        viewModel.changeSharedFlowValue()

                    }) {
                        Text(text = "SharedFlow Button")

                    }
                    
                }

        }

    }
}
