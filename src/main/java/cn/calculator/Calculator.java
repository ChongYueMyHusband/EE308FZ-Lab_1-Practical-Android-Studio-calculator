package cn.calculator;

import java.text.DecimalFormat;

public class Calculator {
    private Double firNum;      // 存储第一个操作数（Double类型）
    private Double secNum;      // 存储第二个操作数（Double类型）。
    private CalculatorOperator operator;    // 存储当前的操作符（加法、减法、乘法、除法等）。
    private String currentNumberText;       // 存储当前用户输入的数字文本。
    private boolean pressedDotInLast = false;   // 追踪最后一个操作是否是小数点。

    public Calculator() {
        clear();
    }   // 在构造函数中，通过调用 clear 方法来初始化计算器的状态
    // 通过在构造函数中调用 clear 方法进行初始化，可以确保对象的初始状态是一致的、可预测的，
    // 并且能够提高代码的可维护性和可测试性。这是一种良好的编程实践，有助于减少错误并提高代码质量


    // 处理数字按钮的点击事件
    public String clickCode(CalculatorCode code) {
        Double number = null;
        // 根据当前操作符确定要处理的是第一个操作数还是第二个操作数
        if (this.operator == null)
            number = firNum;
        else
            number = secNum;

        // 将用户点击的数字添加到当前数字文本中，并尝试将其转换为 Double 类型，以便进行数值计算。
        String numberText = currentNumberText + code.getNumber();
        try {
            number = Double.parseDouble(numberText);
        }catch (NumberFormatException e) {
            numberText = currentNumberText;
        }
        // 根据操作符号(+-x÷)来判断当前数字是第一个数字还是第二个数字
        if (operator == null)
            firNum = number;
        else
            secNum = number;

        StringBuilder builder = new StringBuilder();

        if (operator == null) {
            builder.append(numberText);
        }
        // 如果当前存在操作符则添加操作符及第二个数字
        else  {
            builder.append(doubleWithInt(firNum));
            builder.append(operator.getOperator());
            builder.append(numberText);
        }
        currentNumberText = numberText;
        // 返回需要显示的字符串
        return builder.toString();
    }

    // 用于清除计算器的状态，将所有变量重置为初始值。
    private void clear() {
        firNum = 0.0;
        secNum = 0.0;
        operator = null;
        currentNumberText = "";
    }

    // 处理操作符按钮的点击事件，如加法、减法等。
    // 根据点击的操作符，执行不同的操作，如清除、取反、添加小数点、进行计算等
    public String clickOperator(CalculatorOperator operator, String numberText) {
        String result = "";
        switch (operator) {
            case CLEAR:
                clear();
                result = "";
                break;
            case REVERSE:
                result = reverse();
                break;
            case PERCENT:
                result = percent();
                break;
            case SIN:
                result = calculateSin();
                break;
            case COS:
                result = calculateCos();
                break;
            case TAN:
                result = calculateTan();
                break;

            case DIVISION:
            case MULTIPLICATION:
            case SUBTRACTION:
            case ADDITION:
            case POWER:
                char inputOperator = operator.getOperator();
                if (this.operator == null) {
                    result = numberText + inputOperator;
                } else {
                    char lastOperator = numberText.charAt(numberText
                            .length() - 1);
                    result = equals(numberText);
                    if (this.operator == null) {
                        result += inputOperator;
                    }else {
                        result = result.replace(lastOperator, inputOperator);
                    }
                }
                currentNumberText = "";
                this.operator = operator;
                break;
            case EQUAlS:
                result = equals(numberText);
                break;
        }

        return result;
    }

    private String reverse() {
        firNum = -firNum;
        operator = null;
        return doubleWithInt(firNum);
    }


    private String percent() {
        firNum /= 100;
        operator = null;
        return doubleWithInt(firNum);
    }

    private String calculateSin() {
        firNum = Math.sin(Math.toRadians(firNum));
        firNum = Double.valueOf(formatResult(firNum));
        operator = null;
        return doubleWithInt(firNum);
    }
    private String calculateCos() {
        firNum = Math.cos(Math.toRadians(firNum));
        firNum = Double.valueOf(formatResult(firNum));
        operator = null;
        return doubleWithInt(firNum);
    }

    private String calculateTan() {
        firNum = Math.tan(Math.toRadians(firNum));
        firNum = Double.valueOf(formatResult(firNum));
        operator = null;
        return doubleWithInt(firNum);
    }

    private String equals(String numberText) {
        double result = 0.0;
        if(secNum.equals(0.0) && operator.getOperator() == '×'){
            result = 0.0;
            firNum = result;
            operator = null;
            secNum = 0.0;
            currentNumberText = doubleWithInt(result);
            return doubleWithInt(firNum);
        }
        // 若没有运算符直接按等号/输入的第二位树是0， 则直接返回
        // 这里有点小问题，如果是乘以0就不对了,所以先研究乘以0的情况
        if (this.operator == null || secNum.equals(0.0)) {
            return numberText;
        }

        switch (operator) {
            case DIVISION:
                result = firNum / secNum;
                break;
            case MULTIPLICATION:
                result = firNum * secNum;
                break;
            case SUBTRACTION:
                result = firNum - secNum;
                break;
            case ADDITION:
                result = firNum + secNum;
                break;
            case POWER:
                result = Math.pow(firNum, secNum);
        }

//        firNum = result;
        firNum = Double.valueOf(formatResult(result));
        operator = null;
        secNum = 0.0;
//        currentNumberText = formatResult(result); // 格式化结果
        currentNumberText = doubleWithInt(result);
        return doubleWithInt(firNum);
    }

    // 将一个 Double 类型的数转换为字符串，并根据是否可以转换为整数来决定返回的字符串形式。
    // 如果可以转换为整数，返回整数形式的字符串，否则返回原始的双精度浮点数形式的字符串。
    // 这种操作可能用于确保显示数字时不显示多余的小数位。
    private String doubleWithInt(Double number) {
        boolean flag = number.longValue() == number;
        if (flag) {
            return String.valueOf(number.longValue());
        }
        return number.toString();
    }



    // 额外功能：实现可控的小数位选择
    private int decimalPlaces = 6; // 用户选择的小数位数，默认为6位

    // 设置小数位数
    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }
    private String formatResult(double result) {
        // 构造格式化字符串，根据用户选择的小数位数来设置格式
        StringBuilder formatBuilder = new StringBuilder("#.");
        for (int i = 0; i < decimalPlaces; i++) {
            formatBuilder.append("#");
        }
        String formatPattern = formatBuilder.toString();

        // 使用 DecimalFormat 格式化结果
        DecimalFormat decimalFormat = new DecimalFormat(formatPattern);
        return decimalFormat.format(result);
    }





}
