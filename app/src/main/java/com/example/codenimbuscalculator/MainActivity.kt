package com.example.codenimbuscalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codenimbuscalculator.ui.theme.CodeNimbusCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeNimbusCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF17171C)
                ) {
                    val viewModel: CalculatorViewModel = viewModel()
                    CalculatorScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        // Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = viewModel.inputExpression,
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.End,
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = viewModel.liveResult,
                fontSize = 28.sp,
                color = Color.Gray,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Button Grid Layout
        val buttons = listOf(
            listOf("C", "( )", "%", "÷"),
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "⌫", "=")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { symbol ->
                    CalculatorButton(
                        symbol = symbol,
                        modifier = Modifier.weight(1f),
                        onClick = { handleAction(symbol, viewModel) }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val backgroundColor = when (symbol) {
        "C" -> Color(0xFFA5A5A5)
        "÷", "×", "-", "+", "=" -> Color(0xFFFF9F0A)
        "( )", "%", "⌫" -> Color(0xFF4E4E50)
        else -> Color(0xFF2E2E38)
    }

    val textColor = if (symbol == "C") Color.Black else Color.White

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() }
    ) {
        Text(
            text = symbol,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

fun handleAction(symbol: String, viewModel: CalculatorViewModel) {
    when (symbol) {
        "C" -> viewModel.onAction(CalculatorAction.Clear)
        "⌫" -> viewModel.onAction(CalculatorAction.Delete)
        "=" -> viewModel.onAction(CalculatorAction.Calculate)
        "( )" -> viewModel.onAction(CalculatorAction.Parentheses)
        "%" -> viewModel.onAction(CalculatorAction.Percentage)
        "+" -> viewModel.onAction(CalculatorAction.Operator("+"))
        "-" -> viewModel.onAction(CalculatorAction.Operator("-"))
        "×" -> viewModel.onAction(CalculatorAction.Operator("×"))
        "÷" -> viewModel.onAction(CalculatorAction.Operator("÷"))
        "." -> viewModel.onAction(CalculatorAction.Decimal)
        else -> symbol.toIntOrNull()?.let { viewModel.onAction(CalculatorAction.Number(it)) }
    }
}