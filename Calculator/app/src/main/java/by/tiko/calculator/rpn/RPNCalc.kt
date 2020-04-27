package by.tiko.calculator.rpn

import java.lang.Exception
import java.util.*

/**
 * RPNRunner class is responsible to calculate the
 * expression.
 **/
class RPNCalc() {

    private val expressionConverter = Exp2RPN()

    /**
     * Calculate expression
     *
     *  @param expression Normal expression, which will be transformed to RPN
     * and will be calculated. Default value is
     *
     *
     * @return Double In case the expression is an empty string, then the method will
     * return with null
     */
    fun calculate(expression: String): Double? {
        if (expression.trim().isEmpty())
            return null
        val rpnArray = expressionConverter.convert(expression)
        val stack = Stack<Double>()

        for (element in rpnArray) {
            print("$element ")
        }

        println()

        for (element in rpnArray) {

            when (element) {
                "+" -> {
                    val res = this.getElementValue(stack)
                    stack.push(res!!.first + res.second)
                }
                "-" -> {
                    val res = this.getElementValue(stack)
                    stack.push(res!!.second - res.first)
                }
                "*" -> {
                    val res = this.getElementValue(stack)
                    stack.push(res!!.first * res.second)
                }
                "/" -> {
                    val res = this.getElementValue(stack)
                    stack.push(res!!.second / res.first)
                }
                "%" -> {
                    val res = this.getElementValue(stack)
                    val secondValue = res!!.second
                    stack.push((res!!.first * res.second) / 100)
                    stack.push(secondValue)
                }
                else -> {
                    stack.push(element.toDouble())
                }
            }
        }

        if (stack.size > 1) {
            throw Exception("Invalid expression: '$expression'")
        }
        return stack.pop()
    }

    private fun getElementValue(stack: Stack<Double>): Pair<Double, Double>? {
        if (stack.isEmpty())
            return null

        val first: Double? = stack.pop()
        val second: Double? = stack.pop()

        return Pair(first!!, second!!)
    }
}