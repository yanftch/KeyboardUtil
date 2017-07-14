package com.yanftch.keyboardutil.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yanftch.keyboardutil.R;


/**
 * User : yanftch
 * Date : 2017/4/17
 * http://www.jianshu.com/p/a1899d6e4d2d--TextViewDrawable
 */

public class PwdNoView extends View {
    //禁用枚举,提升性能
    public final static int SHOW_TYPE_WORD = 1;
    public final static int SHOW_TYPE_PASSWORD = 2;
    //
    private final static String DEFAULTCOLOR = "#333333";

    //密码长度，默认6位
    private int length;
    //描边颜色
    private int borderColor;
    //描边宽度
    private float borderWidth;
    //分割线颜色
    private int dividerColor;
    //分割线宽度
    private float dividerWidth;
    //默认文本
    private String code;
    //密码点颜色
    private int codeColor;
    //密码点半径
    private float pointRadius;
    //显示明文时的文字大小，默认unitWidth/2
    private float textSize;
    //显示类型，支持密码、明文，默认密文
    private int showType;

    private float unitWidth;//每个方格的宽度
    private Paint paint;
    private Listener listener;

    public PwdNoView(Context context) {
        super(context);
        init(null);
    }

    public PwdNoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //根据宽度来计算单元格大小（高度）
        float width = getMeasuredWidth();
        //每个格格的宽度 ==   宽度-左右边宽-中间分割线宽度
        //让宽度自适应的写法，也就是自己计算每个单元格的宽度
        unitWidth = (width - (2 * borderWidth) - ((length - 1) * dividerWidth)) / length;
        //写死的每个单元格的宽度
        if (textSize == 0) {
            textSize = unitWidth / 2;
        }
        setMeasuredDimension((int) width, (int) (unitWidth + (2 * borderWidth)));//／／／／／／／／／／
//        setMeasuredDimension((int) width, getMeasuredHeight());//为了实现自定义高度
        //适应于宽高是定值的情况
//        setMeasuredDimension((int) (unitWidth * 6), getMeasuredHeight());
    }

    private void init(AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        if (attrs == null) {
            length = 6;
            borderColor = Color.parseColor(DEFAULTCOLOR);
            borderWidth = 1;
            dividerColor = Color.parseColor(DEFAULTCOLOR);
            dividerWidth = 1;
            code = "";
            codeColor = Color.parseColor("#000000");
            pointRadius = Util.dp2px(getContext(), 8);
            showType = SHOW_TYPE_PASSWORD;
            textSize = 0;
        } else {
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.pwdNoView);
            length = typedArray.getInt(R.styleable.pwdNoView_length, 6);
            borderColor = typedArray.getColor(R.styleable.pwdNoView_borderColor, Color.parseColor(DEFAULTCOLOR));
            borderWidth = typedArray.getDimensionPixelSize(R.styleable.pwdNoView_borderWidth, 1);
            dividerColor = typedArray.getColor(R.styleable.pwdNoView_myDividerColor, Color.parseColor(DEFAULTCOLOR));
            dividerWidth = typedArray.getDimensionPixelSize(R.styleable.pwdNoView_dividerWidth, 1);
            code = typedArray.getString(R.styleable.pwdNoView_code);
            if (code == null) {
                code = "";
            }
            codeColor = typedArray.getColor(R.styleable.pwdNoView_codeColor, Color.parseColor("#000000"));
            pointRadius = typedArray.getDimensionPixelSize(R.styleable.pwdNoView_pointRadius, Util.dp2px(getContext(), 8));
            showType = typedArray.getInt(R.styleable.pwdNoView_showType, SHOW_TYPE_PASSWORD);
            textSize = typedArray.getDimensionPixelSize(R.styleable.pwdNoView_myTextSize, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDivider(canvas);
        drawBorder(canvas);
        switch (showType) {
            case SHOW_TYPE_PASSWORD:
                drawPoint(canvas);
                break;
            case SHOW_TYPE_WORD:
                drawValue(canvas);
                break;
            default:
                drawPoint(canvas);
                break;
        }
    }

    /**
     * 绘制边框
     */
    private void drawBorder(Canvas canvas) {
        if (borderWidth > 0) {
            paint.setColor(borderColor);
            canvas.drawRect(0, 0, getWidth(), borderWidth, paint);
            canvas.drawRect(0, getHeight() - borderWidth, getWidth(), getHeight(), paint);
            canvas.drawRect(0, 0, borderWidth, getHeight(), paint);
            canvas.drawRect(getWidth() - borderWidth, 0, getWidth(), getHeight(), paint);
        }
    }

    /**
     * 画垂直分割线
     */
    private void drawDivider(Canvas canvas) {
        if (dividerWidth > 0) {
            paint.setColor(dividerColor);
            for (int i = 0; i < length - 1; i++) {
                final float left = unitWidth * (i + 1) + dividerWidth * i + borderWidth;
                canvas.drawRect(left, 0, left + dividerWidth, getHeight(), paint);
            }
        }
    }

    /**
     * 画输入文字
     */
    private void drawValue(Canvas canvas) {
        if (pointRadius > 0) {
            paint.setColor(codeColor);
            paint.setTextSize(textSize);
            for (int i = 0; i < code.length(); i++) {
                final float left = unitWidth * i + dividerWidth * i + borderWidth;
                canvas.drawText(String.format("%s", code.charAt(i)), left + unitWidth / 2, Util.getTextBaseLine(0, getHeight(), paint), paint);
            }
        }
    }

    /**
     * 画密码点
     */
    private void drawPoint(Canvas canvas) {
        if (pointRadius > 0) {
            paint.setColor(codeColor);
            for (int i = 0; i < code.length(); i++) {
                final float left = unitWidth * i + dividerWidth * i + borderWidth;
                //FB检测改进代码
                canvas.drawCircle((float) (left + (float) unitWidth / 2), (float) getHeight() / 2, pointRadius, paint);
            }
        }
    }

    //输入密码值
    public void input(String number) {
        if (code.length() < length) {
            code += number;
            if (listener != null) {
                listener.onValueChanged(code);
                if (code.length() == length) {
                    listener.onComplete(code);
                }
            }
            invalidate();
        }
    }

    //删除键
    public void delete() {
        if (code.length() > 0) {
            code = code.substring(0, code.length() - 1);
            if (listener != null) {
                listener.onValueChanged(code);
            }
            invalidate();
        }
    }

    public void setLength(int length) {
        this.length = length;
        invalidate();
    }

    public void setShowType(int showType) {
        this.showType = showType;
        invalidate();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {

        void onValueChanged(String value);

        void onComplete(String value);

    }

}

