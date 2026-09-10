package com.example.codenimbuscalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

class CalculatorViewModel : ViewModel() {

    var inputExpression by mutableStateOf("")
        private set

    var liveResult by mutableStateOf("")
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> appendInput(action.number.toString())
            is CalculatorAction.Operator -> appendOperator(action.symbol)
            is CalculatorAction.Decimal -> appendDecimal()
            is CalculatorAction.Clear -> {
                inputExpression = ""
                liveResult = ""
            }
            is CalculatorAction.Delete -> {
                if (inputExpression.isNotEmpty()) {
                    inputExpression = inputExpression.dropLast(1)
                    calculateLiveResult()
                }
            }
            is CalculatorAction.Calculate -> {
                if (liveResult.isNotEmpty() && liveResult != "Cannot divide by zero") {
                    inputExpression = liveResult
                    liveResult = ""
                }
            }
            is CalculatorAction.Parentheses -> appendParentheses()
            is CalculatorAction.Percentage -> appendPercentage()
        }
    }

    private fun appendInput(value: String) {
        inputExpression += value
        calculateLiveResult()
    }

    private fun appendOperator(operator: String) {
        if (inputExpression.isEmpty()) return

        val lastChar = inputExpression.last()
        if (lastChar in listOf('+', '-', '×', '÷')) {
            inputExpression = inputExpression.dropLast(1) + operator
        } else {
            inputExpression += operator
        }
        calculateLiveResult()
    }

    private fun appendPercentage() {
        if (inputExpression.isEmpty()) return
        val lastChar = inputExpression.last()
        if (lastChar.isDigit() || lastChar == ')') {
            inputExpression += "%"
            calculateLiveResult()
        }
    }

    private fun appendDecimal() {
        val lastToken = inputExpression.split('+', '-', '×', '÷', '%').lastOrNull() ?: ""
        if (!lastToken.contains('.')) {
            inputExpression += if (inputExpression.isEmpty() || !inputExpression.last().isDigit()) "0." else "."
            calculateLiveResult()
        }
    }

    private fun appendParentheses() {
        val openCount = inputExpression.count { it == '(' }
        val closeCount = inputExpression.count { it == ')' }

        inputExpression += if (openCount > closeCount && inputExpression.isNotEmpty() && inputExpression.last().isDigit()) {
            ")"
        } else {
            "("
        }
        calculateLiveResult()
    }

    private fun calculateLiveResult() {
        if (inputExpression.isBlank()) {
            liveResult = ""
            return
        }

        try {
            var parsedExpression = inputExpression
                .replace("×", "*")
                .replace("÷", "/")

            // Convert numbers with % (e.g., 5% -> (5/100))
            parsedExpression = parsedExpression.replace(Regex("(\\d+(\\.\\d+)?)%")) { matchResult ->
                "(${matchResult.groupValues[1]}/100)"
            }

            val expression = ExpressionBuilder(parsedExpression).build()
            val eval = expression.evaluate()

            liveResult = if (eval.isInfinite() || eval.isNaN()) {
                "Cannot divide by zero"
            } else if (eval == eval.toLong().toDouble()) {
                eval.toLong().toString()
            } else {
                val formatter = DecimalFormat("#.########")
                formatter.format(eval)
            }
        } catch (e: Exception) {
            liveResult = ""
        }
    }
}

sealed class CalculatorAction {
    data class Number(val number: Int) : CalculatorAction()
    data class Operator(val symbol: String) : CalculatorAction()
    object Decimal : CalculatorAction()
    object Clear : CalculatorAction()
    object Delete : CalculatorAction()
    object Calculate : CalculatorAction()
    object Parentheses : CalculatorAction()
    object Percentage : CalculatorAction()
}