package com.t3s1fy.form.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.t3s1fy.form.R

@Composable
fun FormScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableFloatStateOf(25f) }
    var gender by rememberSaveable { mutableStateOf("male") }
    var isSubscribed by rememberSaveable { mutableStateOf(false) }
    var result by remember { mutableStateOf<String?>(null) }

    val nameValid = name.trim().isNotEmpty()
    val ageValue = age.toInt()

    val maleLabel = stringResource(R.string.g_male)
    val femaleLabel = stringResource(R.string.g_female)
    val yesLabel = stringResource(R.string.yes)
    val noLabel = stringResource(R.string.no)
    val summaryHead = stringResource(R.string.result_title)
    val summaryName = stringResource(R.string.result_name)
    val summaryAge = stringResource(R.string.result_age)
    val summaryGender = stringResource(R.string.result_gender)
    val summarySubscribe = stringResource(R.string.result_sub)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFe3f2fd), Color(0xFFbbdefb))
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            painter = painterResource(R.drawable.icon),
            contentDescription = null,
            modifier = Modifier
                .size(110.dp)
                .padding(top = 20.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(stringResource(R.string.hint_name)) },
                    label = { Text(stringResource(R.string.label_name)) },
                    isError = !nameValid,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (!nameValid) {
                    Text(
                        text = stringResource(R.string.error_name_empty),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(stringResource(R.string.label_g))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = gender == "male",
                                onClick = { gender = "male" }
                            )
                            .padding(end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = gender == "male", onClick = { gender = "male" })
                        Text(maleLabel, Modifier.padding(start = 4.dp))
                    }
                    Row(
                        modifier = Modifier.selectable(
                            selected = gender == "female",
                            onClick = { gender = "female" }
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = gender == "female", onClick = { gender = "female" })
                        Text(femaleLabel, Modifier.padding(start = 4.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(stringResource(R.string.label_age))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Slider(
                        value = age,
                        onValueChange = { age = it },
                        valueRange = 1f..100f,
                        modifier = Modifier.weight(1f)
                    )
                    Text("$ageValue")
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isSubscribed, onCheckedChange = { isSubscribed = it })
                    Text(stringResource(R.string.sub_text))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val genderText = if (gender == "male") maleLabel else femaleLabel
                        val subscribeText = if (isSubscribed) yesLabel else noLabel
                        result = buildString {
                            appendLine(summaryHead)
                            appendLine("$summaryName $name")
                            appendLine("$summaryAge $ageValue")
                            appendLine("$summaryGender $genderText")
                            appendLine("$summarySubscribe $subscribeText")
                        }
                    },
                    enabled = nameValid,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                ) {
                    Text(stringResource(R.string.button_send), color = Color.White)
                }
            }
        }

        result?.let {
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = it,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFormLight() {
    FormScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewFormDark() {
    FormScreen()
}
