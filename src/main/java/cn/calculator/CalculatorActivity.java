package cn.calculator;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.widget.Toast;
// 程序主活动类
public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    // 按钮控件id
    public static final int[] btnIds = {
            R.id.btn_clear,
            R.id.btn_reverse,
            R.id.btn_percent,
            R.id.btn_division,
            R.id.btn_one,
            R.id.btn_two,
            R.id.btn_three,
            R.id.btn_multiplication,
            R.id.btn_four,
            R.id.btn_five,
            R.id.btn_six,
            R.id.btn_subtraction,
            R.id.btn_seven,
            R.id.btn_eight,
            R.id.btn_nine,
            R.id.btn_addition,
            R.id.btn_zero,
            R.id.btn_dot,
            R.id.btn_equals,
            R.id.btn_copy,
            R.id.btn_sin,
            R.id.btn_cos,
            R.id.btn_tan,
            R.id.btn_mi

    };
    private TextView txContent;     // tv控件 - 显示计算器输入和结果
    private TextView txPrecision;   // tv控件 - 显示精确度
    private Calculator calculator;  // 自定义类-执行计算器的计算逻辑
    private ScaleTouchListener touchListener;

    public int precision = 6;      // 保留的小数位数


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calculator);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        touchListener = new ScaleTouchListener();
        calculator = new Calculator();
        txContent = (TextView) findViewById(R.id.tv_content);
        txPrecision = (TextView) findViewById(R.id.tv_precision);
        Button btn;
        for (int btnId : btnIds) {
            // 遍历按钮ID数组，为每个按钮设置点击事件监听器和触摸事件监听器。
            btn = (Button) findViewById(btnId);
            // 点击事件监听器：处理的是用户点击（或触摸）屏幕上的特定 UI 元素（例如按钮、文本框等）的事件。点击事件通常包括单击、双击、长按等。
            btn.setOnClickListener(this);
            // 触摸事件监听器：处理的是用户在屏幕上的触摸操作，不仅包括点击，还包括滑动、拖拽、缩放等各种触摸手势。
            // 作用：点击/释放时产生缩小/恢复效果
            btn.setOnTouchListener(touchListener);
            // Q：为什么点击事件可以直接写在onClick函数中，而触摸事件写在另一个类中
            // Ans: 触摸事件处理的方式有多种，因为触摸事件可能涉及更复杂的场景，如拖拽、滑动、缩放等。
            // 为了更灵活地处理这些复杂的交互，通常会创建一个单独的触摸事件监听器类（如你提供的ScaleTouchListener），并将其附加到需要处理触摸事件的视图上。
            // 这种方式允许你以更细粒度的方式处理触摸事件，并且可以在不同的视图元素上重复使用同一个触摸事件监听器。
        }

        txPrecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(precision > 8)
                    precision = 0;
                precision++;
                calculator.setDecimalPlaces(precision);
//                txPrecision.setText(precision);
                txPrecision.setText(String.valueOf(precision));
            }
        });
    }

    // 告诉编译器或静态代码分析工具，应该忽略与非常量资源ID相关的警告或错误
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        // 获取当前 TextView 的文本内容
        String numberText = txContent.getText().toString();
        switch (view.getId()) {
            case R.id.btn_clear:
                numberText = calculator.clickOperator(CalculatorOperator.CLEAR, numberText);
                break;
            case R.id.btn_reverse:
                numberText = calculator.clickOperator(CalculatorOperator.REVERSE, numberText);
                break;
            case R.id.btn_percent:
                numberText = calculator.clickOperator(CalculatorOperator.PERCENT, numberText);
                break;
            case R.id.btn_division:
                numberText = calculator.clickOperator(CalculatorOperator.DIVISION, numberText);
                break;
            case R.id.btn_one:
                numberText = calculator.clickCode(CalculatorCode.ONE);
                break;
            case R.id.btn_two:
                numberText = calculator.clickCode(CalculatorCode.TWO);
                break;
            case R.id.btn_three:
                numberText = calculator.clickCode(CalculatorCode.THREE);
                break;
            case R.id.btn_multiplication:
                numberText = calculator.clickOperator(CalculatorOperator.MULTIPLICATION, numberText);
                break;
            case R.id.btn_four:
                numberText = calculator.clickCode(CalculatorCode.FOUR);
                break;
            case R.id.btn_five:
                numberText = calculator.clickCode(CalculatorCode.FIVE);
                break;
            case R.id.btn_six:
                numberText = calculator.clickCode(CalculatorCode.SIX);
                break;
            case R.id.btn_subtraction:
                numberText = calculator.clickOperator(CalculatorOperator.SUBTRACTION, numberText);
                break;
            case R.id.btn_seven:
                numberText = calculator.clickCode(CalculatorCode.SEVEN);
                break;
            case R.id.btn_eight:
                numberText = calculator.clickCode(CalculatorCode.EIGHT);
                break;
            case R.id.btn_nine:
                numberText = calculator.clickCode(CalculatorCode.NINE);
                break;
            case R.id.btn_addition:
                numberText = calculator.clickOperator(CalculatorOperator.ADDITION, numberText);
                break;
            case R.id.btn_zero:
                numberText = calculator.clickCode(CalculatorCode.ZERO);
                break;
            case R.id.btn_dot:
                numberText = calculator.clickCode(CalculatorCode.DOT);
                break;
            case R.id.btn_equals:
                numberText = calculator.clickOperator(CalculatorOperator.EQUAlS, numberText);
                break;
            case R.id.btn_copy:
                // 获取剪贴板管理器
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建一个ClipData对象，包含要复制的文本
                ClipData clipData = ClipData.newPlainText("text", numberText);
                // 将ClipData对象复制到剪贴板
                clipboardManager.setPrimaryClip(clipData);
                // 提示用户文本已复制到剪贴板
                Toast.makeText(this, "文本已复制到剪贴板", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_sin:
                numberText = calculator.clickOperator(CalculatorOperator.SIN, numberText);
                break;
            case R.id.btn_cos:
                numberText = calculator.clickOperator(CalculatorOperator.COS, numberText);
                break;
            case R.id.btn_tan:
                numberText = calculator.clickOperator(CalculatorOperator.TAN, numberText);
                break;
            case R.id.btn_mi:
                numberText = calculator.clickOperator(CalculatorOperator.POWER, numberText);
                break;
        }
        txContent.setText(numberText);
    }
}
