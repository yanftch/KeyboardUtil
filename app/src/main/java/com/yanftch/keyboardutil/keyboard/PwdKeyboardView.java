package com.yanftch.keyboardutil.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.yanftch.keyboardutil.R;


/**
 * User : yanftch
 * Date : 2017/4/17
 */

public class PwdKeyboardView extends FrameLayout implements View.OnClickListener{

    private static final String TAG = "dah_PwdKeyboardView";
    private Listener listener;
    private PwdNoView codeView;

    public PwdKeyboardView(Context context) {
        super(context);
        init(context);
    }

    public void setPwdNoView(PwdNoView codeView) {
        this.codeView = codeView;
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pwd_keyboard, null, false);
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(view);
        view.findViewById(R.id.keyboard_0).setOnClickListener(this);
        view.findViewById(R.id.keyboard_1).setOnClickListener(this);
        view.findViewById(R.id.keyboard_2).setOnClickListener(this);
        view.findViewById(R.id.keyboard_3).setOnClickListener(this);
        view.findViewById(R.id.keyboard_4).setOnClickListener(this);
        view.findViewById(R.id.keyboard_5).setOnClickListener(this);
        view.findViewById(R.id.keyboard_6).setOnClickListener(this);
        view.findViewById(R.id.keyboard_7).setOnClickListener(this);
        view.findViewById(R.id.keyboard_8).setOnClickListener(this);
        view.findViewById(R.id.keyboard_9).setOnClickListener(this);
        view.findViewById(R.id.keyboard_delete).setOnClickListener(this);
    }

    public PwdKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PwdKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        public void onInput(String s);

        public void onDelete();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag != null) {
            switch (tag) {
                case "delete":
                    if (codeView != null) {
                        codeView.delete();
                    }
                    if (listener != null) {
                        listener.onDelete();
                    }
                    break;
                default:
                    if (codeView != null) {
                        codeView.input(tag);
                    }
                    if (listener != null) {
                        listener.onInput(tag);
                    }
                    break;
            }
        }
    }
}
