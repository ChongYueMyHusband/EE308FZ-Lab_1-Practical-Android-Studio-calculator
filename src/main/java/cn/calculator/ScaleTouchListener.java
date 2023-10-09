package cn.calculator;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.ScaleAnimation;

// 实现 View.OnTouchListener 接口 -- 附加到 Android 视图上，以处理触摸事件。
// 有时候触摸事件处理需要捕获不同的触摸事件，如按下、移动、释放等，这可以通过实现View.OnTouchListener接口来实现，
// 然后在onTouch方法中根据不同的事件类型来执行不同的操作。这种方式更灵活，适用于需要自定义触摸逻辑的情况。
public class ScaleTouchListener implements View.OnTouchListener {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                view.setScaleX(0.9f);
                view.setScaleY(0.9f);
                break;
            case MotionEvent.ACTION_UP:
                view.setScaleX(1);
                view.setScaleY(1);
                break;
            default:
        }
        return false;
    }
}
