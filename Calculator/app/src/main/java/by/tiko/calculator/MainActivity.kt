package by.tiko.calculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.tiko.calculator.rpn.RPNCalc
import java.math.RoundingMode

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var buttonNumber0: TextView? = null
    private var buttonNumber1: TextView? = null
    private var buttonNumber2: TextView? = null
    private var buttonNumber3: TextView? = null
    private var buttonNumber4: TextView? = null
    private var buttonNumber5: TextView? = null
    private var buttonNumber6: TextView? = null
    private var buttonNumber7: TextView? = null
    private var buttonNumber8: TextView? = null
    private var buttonNumber9: TextView? = null
    private var buttonClear: TextView? = null
    private var buttonDivision: TextView? = null
    private var buttonMultiplication: TextView? = null
    private var buttonSubtraction: TextView? = null
    private var buttonAddition: TextView? = null
    private var buttonPercent: TextView? = null
    private var buttonEqual: TextView? = null
    private var buttonDot: TextView? = null
    private var textViewInputNumbers: TextView? = null
    private var openParenthesis = 0
    private var dotUsed = false
    private var equalClicked = false
    private var lastExpression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViewVariables()
        setOnClickListeners()
    }

    private fun initializeViewVariables() {
        buttonNumber0 = findViewById(R.id.textViewZero)
        buttonNumber1 = findViewById(R.id.textViewOne)
        buttonNumber2 = findViewById(R.id.textViewTwo)
        buttonNumber3 = findViewById(R.id.textViewThree)
        buttonNumber4 = findViewById(R.id.textViewFour)
        buttonNumber5 = findViewById(R.id.textViewFive)
        buttonNumber6 = findViewById(R.id.textViewSix)
        buttonNumber7 = findViewById(R.id.textViewSeven)
        buttonNumber8 = findViewById(R.id.textViewEight)
        buttonNumber9 = findViewById(R.id.textViewNine)
        buttonClear = findViewById(R.id.textViewClear)
        buttonDivision = findViewById(R.id.textViewDivide)
        buttonMultiplication = findViewById(R.id.textViewMultiply)
        buttonSubtraction = findViewById(R.id.textViewMinus)
        buttonAddition = findViewById(R.id.textViewPlus)
        buttonEqual = findViewById(R.id.textViewEquals)
        textViewInputNumbers = findViewById(R.id.textViewResult)
        buttonDot = findViewById(R.id.textViewDot)
        buttonPercent = findViewById(R.id.textViewPercent)
    }

    private fun setOnClickListeners() {
        buttonNumber0!!.setOnClickListener(this)
        buttonNumber1!!.setOnClickListener(this)
        buttonNumber2!!.setOnClickListener(this)
        buttonNumber3!!.setOnClickListener(this)
        buttonNumber4!!.setOnClickListener(this)
        buttonNumber5!!.setOnClickListener(this)
        buttonNumber6!!.setOnClickListener(this)
        buttonNumber7!!.setOnClickListener(this)
        buttonNumber8!!.setOnClickListener(this)
        buttonNumber9!!.setOnClickListener(this)
        buttonClear!!.setOnClickListener(this)
        buttonDivision!!.setOnClickListener(this)
        buttonMultiplication!!.setOnClickListener(this)
        buttonSubtraction!!.setOnClickListener(this)
        buttonAddition!!.setOnClickListener(this)
        buttonEqual!!.setOnClickListener(this)
        buttonDot!!.setOnClickListener(this)
        buttonPercent!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.textViewZero -> if (addNumber(R.string.ZERO)) equalClicked = false
            R.id.textViewOne -> if (addNumber(R.string.ONE)) equalClicked = false
            R.id.textViewTwo -> if (addNumber(R.string.TWO)) equalClicked = false
            R.id.textViewThree -> if (addNumber(R.string.THREE)) equalClicked = false
            R.id.textViewFour -> if (addNumber(R.string.FOUR)) equalClicked = false
            R.id.textViewFive -> if (addNumber(R.string.FIVE)) equalClicked = false
            R.id.textViewSix -> if (addNumber(R.string.SIX)) equalClicked = false
            R.id.textViewSeven -> if (addNumber(R.string.SEVEN)) equalClicked = false
            R.id.textViewEight -> if (addNumber(R.string.EIGHT)) equalClicked = false
            R.id.textViewNine -> if (addNumber(R.string.NINE)) equalClicked = false
            R.id.textViewPlus -> if (addOperand(R.string.PLUS)) equalClicked = false
            R.id.textViewMinus -> if (addOperand(R.string.MINUS)) equalClicked = false
            R.id.textViewMultiply -> if (addOperand(R.string.MULTIPLY)) equalClicked = false
            R.id.textViewDivide -> if (addOperand(R.string.DIVIDE)) equalClicked = false
            R.id.textViewDot -> if (addDot()) equalClicked = false
            R.id.textViewPercent -> if (addOperand(R.string.PERCENT)) equalClicked = false
            R.id.textViewClear -> {
                textViewInputNumbers!!.text = ""
                openParenthesis = 0
                dotUsed = false
                equalClicked = false
            }
            R.id.textViewEquals -> if (textViewInputNumbers!!.text.toString() != "") calculate(
                textViewInputNumbers!!.text.toString()
            )
        }
    }

    private fun addDot(): Boolean {
        var done = false
        if (textViewInputNumbers!!.text.isEmpty()) {
            textViewInputNumbers!!.text = "0."
            dotUsed = true
            done = true
        } else if (!dotUsed) {
            if (defineLastCharacter(textViewInputNumbers!!.text[textViewInputNumbers!!.text.length - 1].toString() + "") == IS_OPERAND) {
                textViewInputNumbers!!.append(resources.getString(R.string.ZERODOT))
                done = true
                dotUsed = true
            } else if (defineLastCharacter(textViewInputNumbers!!.text[textViewInputNumbers!!.text.length - 1].toString() + "") == IS_NUMBER) {
                textViewInputNumbers!!.append(resources.getString(R.string.DOT))
                done = true
                dotUsed = true
            }
        }
        return done
    }

    private fun addParenthesis(): Boolean {
        var done = false
        if (textViewInputNumbers!!.text.length == 0) {
            textViewInputNumbers!!.text = textViewInputNumbers!!.text.toString() + "("
            dotUsed = false
            openParenthesis++
            done = true
        } else if (openParenthesis > 0 && textViewInputNumbers!!.text.isNotEmpty()) {
            val lastInput =
                textViewInputNumbers!!.text[textViewInputNumbers!!.text.length - 1].toString() + ""
            when (defineLastCharacter(lastInput)) {
                IS_NUMBER -> {
                    textViewInputNumbers!!.text = textViewInputNumbers!!.text.toString() + ")"
                    done = true
                    openParenthesis--
                    dotUsed = false
                }
                IS_OPERAND -> {
                    textViewInputNumbers!!.text = textViewInputNumbers!!.text.toString() + "("
                    done = true
                    openParenthesis++
                    dotUsed = false
                }
                IS_OPEN_PARENTHESIS -> {
                    textViewInputNumbers!!.text = textViewInputNumbers!!.text.toString() + "("
                    done = true
                    openParenthesis++
                    dotUsed = false
                }
                IS_CLOSE_PARENTHESIS -> {
                    textViewInputNumbers!!.text = textViewInputNumbers!!.text.toString() + ")"
                    done = true
                    openParenthesis--
                    dotUsed = false
                }
            }
        } else if (openParenthesis == 0 && textViewInputNumbers!!.text.length > 0) {
            val lastInput =
                textViewInputNumbers!!.text[textViewInputNumbers!!.text.length - 1].toString() + ""
            if (defineLastCharacter(lastInput) == IS_OPERAND) {
                textViewInputNumbers!!.text = textViewInputNumbers!!.text.toString() + "("
                done = true
                dotUsed = false
                openParenthesis++
            } else {
                textViewInputNumbers!!.text = textViewInputNumbers!!.text.toString() + "x("
                done = true
                dotUsed = false
                openParenthesis++
            }
        }
        return done
    }

    private fun addOperand(operand: Int): Boolean {
        var done = false
        if (textViewInputNumbers!!.text.isNotEmpty()) {
            val lastInput =
                textViewInputNumbers!!.text[textViewInputNumbers!!.text.length - 1].toString() + ""
            if (lastInput == resources.getString(R.string.PLUS)
                || lastInput == resources.getString(R.string.MINUS)
                || lastInput == resources.getString(R.string.MULTIPLY)
                || lastInput == resources.getString(R.string.DIVIDE)
                || lastInput == resources.getString(R.string.PERCENT)) {
                Toast.makeText(applicationContext, "Wrong format", Toast.LENGTH_LONG).show()
            } else if (defineLastCharacter(lastInput) == IS_NUMBER) {
                textViewInputNumbers!!.append(resources.getString(operand))
                dotUsed = false
                equalClicked = false
                lastExpression = ""
                done = true
            }
/*            } else if (operand != "%") {
                textViewInputNumbers!!.text = textViewInputNumbers!!.text.toString() + operand
                dotUsed = false
                equalClicked = false
                lastExpression = ""
                done = true
            }*/
        } else {
            Toast.makeText(
                applicationContext,
                "Wrong Format. Operand Without any numbers?",
                Toast.LENGTH_LONG
            ).show()
        }
        return done
    }

    private fun addNumber(number: Int): Boolean {
        var done = false
        val operationLength = textViewInputNumbers!!.text.length
        if (operationLength > 0) {
            val lastCharacter =
                textViewInputNumbers!!.text[operationLength - 1].toString() + ""
            val lastCharacterState = defineLastCharacter(lastCharacter)
            if (operationLength == 1 && lastCharacterState == IS_NUMBER && lastCharacter == resources.getString(R.string.ZERO)) {
                textViewInputNumbers!!.setText(number)
                done = true
            } else if (lastCharacterState == IS_OPEN_PARENTHESIS
                || lastCharacterState == IS_NUMBER
                || lastCharacterState == IS_OPERAND
                || lastCharacterState == IS_DOT) {
                textViewInputNumbers!!.append(resources.getString(number))
                done = true
            } else if (lastCharacterState == IS_CLOSE_PARENTHESIS || lastCharacter == resources.getString(R.string.PERCENT)) {
                textViewInputNumbers!!.append(resources.getString(R.string.MULTIPLY))
                textViewInputNumbers!!.append(resources.getString(number))
                done = true
            }
        } else {
            textViewInputNumbers!!.append(resources.getString(number))
            done = true
        }
        return done
    }

    private fun calculate(input: String) {
        val expressionCalculator = RPNCalc()
        var result = ""
        try {
            var temp = input
            if (equalClicked) {
                temp = input + lastExpression
            } else {
                saveLastExpression(input)
            }
            result = temp
            val doubleResult = expressionCalculator.calculate(result)
            if (doubleResult != null) {
                result = doubleResult.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toString()
            }
            equalClicked = true
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Wrong Format", Toast.LENGTH_LONG).show()
            return
        }
        if (result == "Infinity") {
            Toast.makeText(
                applicationContext,
                "Division by zero is not allowed",
                Toast.LENGTH_SHORT
            ).show()
            textViewInputNumbers!!.text = input
        } else if (result.contains(".")) {
            result = result.replace("\\.?0*$".toRegex(), "")
            textViewInputNumbers!!.text = result
        }
    }

    private fun saveLastExpression(input: String) {
        val lastOfExpression = input[input.length - 1].toString() + ""
        if (input.length > 1) {
            if (lastOfExpression == ")") {
                lastExpression = ")"
                var numberOfCloseParenthesis = 1
                for (i in input.length - 2 downTo 0) {
                    if (numberOfCloseParenthesis > 0) {
                        val last = input[i].toString() + ""
                        if (last == ")") {
                            numberOfCloseParenthesis++
                        } else if (last == "(") {
                            numberOfCloseParenthesis--
                        }
                        lastExpression = last + lastExpression
                    } else if (defineLastCharacter(input[i].toString() + "") == IS_OPERAND) {
                        lastExpression = input[i].toString() + lastExpression
                        break
                    } else {
                        lastExpression = ""
                    }
                }
            } else if (defineLastCharacter(lastOfExpression + "") == IS_NUMBER) {
                lastExpression = lastOfExpression
                for (i in input.length - 2 downTo 0) {
                    val last = input[i].toString() + ""
                    if (defineLastCharacter(last) == IS_NUMBER || defineLastCharacter(last) == IS_DOT) {
                        lastExpression = last + lastExpression
                    } else if (defineLastCharacter(last) == IS_OPERAND) {
                        lastExpression = last + lastExpression
                        break
                    }
                    if (i == 0) {
                        lastExpression = ""
                    }
                }
            }
        }
    }

    private fun defineLastCharacter(lastCharacter: String): Int {
        try {
            lastCharacter.toInt()
            return IS_NUMBER
        } catch (e: NumberFormatException) {
            if (lastCharacter == resources.getString(R.string.PLUS)
                || lastCharacter == resources.getString(R.string.MINUS)
                || lastCharacter == resources.getString(R.string.MULTIPLY)
                || lastCharacter == resources.getString(R.string.DIVIDE)
                || lastCharacter == resources.getString(R.string.PERCENT)) return IS_OPERAND
            return if (lastCharacter == resources.getString(R.string.DOT)) IS_DOT else EXCEPTION
        }
    }

    companion object {
        private const val EXCEPTION = -1
        private const val IS_NUMBER = 0
        private const val IS_OPERAND = 1
        private const val IS_OPEN_PARENTHESIS = 2
        private const val IS_CLOSE_PARENTHESIS = 3
        private const val IS_DOT = 4
    }
}
