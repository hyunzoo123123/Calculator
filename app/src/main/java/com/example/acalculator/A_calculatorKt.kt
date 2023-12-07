package com.example.acalculator

import java.util.*

abstract class AbstractOperation {
    abstract fun operate(x: Double, y: Double): Double
}

class AddOperation : AbstractOperation() {
    override fun operate(x: Double, y: Double): Double {
        return x + y
    }
}

class SubtractOperation : AbstractOperation() {
    override fun operate(x: Double, y: Double): Double {
        return x - y
    }
}

class MultiplyOperation : AbstractOperation() {
    override fun operate(x: Double, y: Double): Double {
        return x * y
    }
}

class DivideOperation : AbstractOperation() {
    override fun operate(x: Double, y: Double): Double {
        if (y != 0.0) {
            return x / y
        } else {
            throw IllegalArgumentException("0으로는 나누기 못 해요...")
        }
    }
}

class Calculator(private val operation: AbstractOperation) {
    fun performOperation(x: Double, y: Double): Double {
        return operation.operate(x, y)
    }
}

fun main() {
    val scanner = Scanner(System.`in`)

    println("단순 계산기입니다. 'X+Y' or 'X-Y' or 'X*Y' or 'X/Y' 형태의 계산이 가능합니다.")
    val expression = readLine()

    if (expression == null || expression.isBlank()) {
        println("엔터만 치면 안돼요...")
        return
    }

    val regex = """(\d+)([+\-*/])(\d+)""".toRegex()
    val matchResult = regex.matchEntire(expression)

    if (matchResult != null) {
        val (xStr, operator, yStr) = matchResult.destructured

        val x = xStr.toDouble()
        val y = yStr.toDouble()

        val calculatorAdd = Calculator(AddOperation())
        val calculatorSubtract = Calculator(SubtractOperation())
        val calculatorMultiply = Calculator(MultiplyOperation())
        val calculatorDivide = Calculator(DivideOperation())

        val result = when (operator) {
            "+" -> calculatorAdd.performOperation(x, y)
            "-" -> calculatorSubtract.performOperation(x, y)
            "*" -> calculatorMultiply.performOperation(x, y)
            "/" -> calculatorDivide.performOperation(x, y)
            else -> {
                println("'X+Y' or 'X-Y' or 'X*Y' or 'X/Y' 형태의 계산만 가능합니다.")
                return
            }
        }

        // 결과값 소수점 이하 3자리 까지 출력
        println("%.3f".format(result))
    } else {
        println("'X+Y' or 'X-Y' or 'X*Y' or 'X/Y' 형태의 계산만 가능합니다.")
    }
}