package com.yanftch.keyboardutil.keyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanftch.keyboardutil.R;


/**
 * User : yanftch
 * Date : 2017/4/17
 * Desc : 暂行实现功能呢：点击左上角的x关闭弹框；输入完6位的密码之后自动关闭弹框，并将密码回调
 */

public class KeyBoardFragment extends DialogFragment {
    private static final String TAG = "dah_KeyBoardFragment";
    private double outputMonery;//可以提现的金额
    private TextView tv_out_money;
    private Dialog mDialog;
    private DialogClickListener eventListener;// 按钮点击回调
    private ImageView dialog_iv_cancle;
    private OnKeyListener onKeyListener;
    private PwdNoView dialog_pwd_noview;//密码显示框
    private PwdKeyboardView dialog_pwd_keyboardview;//密码键盘

    public KeyBoardFragment() {

    }

    @SuppressLint("ValidFragment")
    public KeyBoardFragment(DialogClickListener dialogClickListener) {
        eventListener = dialogClickListener;
    }

    /**
     * 传递可以提现的金额
     *
     * @param outputMonery 可提现金额
     */
    @SuppressLint("ValidFragment")
    public KeyBoardFragment(double outputMonery, DialogClickListener dialogClickListener) {
        this.outputMonery = outputMonery;
        eventListener = dialogClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_fragment, null);
        mDialog = new Dialog(getActivity(), R.style.dialog_background_dimEnabled);
        mDialog.setContentView(view);//添加view
        dialogSettings();
        initView(view);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (onKeyListener != null) {
                    return onKeyListener.onkeyDown(keyCode, event);
                } else
                    return false;
            }
        });
        return mDialog;

    }

    //初始化
    private void initView(final View view) {
        tv_out_money = (TextView) view.findViewById(R.id.tv_out_money);//提现金额
        dialog_iv_cancle = ((ImageView) view.findViewById(R.id.dialog_iv_cancle));
        //x点击事件回调
        dialog_iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != eventListener) {
                    eventListener.onCancleClick();
                }
                dismiss();//不管回调，必须关闭？
            }
        });
        if (outputMonery > 0.0) {
            tv_out_money.setText(String.format("%s", outputMonery));
        } else {
            tv_out_money.setText(String.format("%s", "0.00"));
        }
        /**----------密码显示框以及密码键盘的处理逻辑-----------BEGIN**/
        dialog_pwd_noview = (PwdNoView) view.findViewById(R.id.dialog_pwd_noview);
        dialog_pwd_keyboardview = (PwdKeyboardView) view.findViewById(R.id.dialog_pwd_keyboardview);
        dialog_pwd_keyboardview.setPwdNoView(dialog_pwd_noview);//绑定PwdNoView
//        dialog_pwd_noview.setShowType(PwdNoView.SHOW_TYPE_WORD);//密码显示类型，隐藏型
        dialog_pwd_noview.setListener(new PwdNoView.Listener() {
            @Override
            public void onValueChanged(String value) {

            }

            @Override
            public void onComplete(String value) {
                Context applicationContext = view.getContext().getApplicationContext();
                if (null != eventListener) {
                    eventListener.onPwdCompleteListener(value);
                    dismiss();
                }
//                Toast.makeText(applicationContext, "输入结果 == " + value, Toast.LENGTH_SHORT).show();
            }
        });
        /**----------密码显示框以及密码键盘的处理逻辑-------------END**/
    }

    /**
     * Dialog相关设置
     */
    private void dialogSettings() {
        Window dialogWindow = mDialog.getWindow();
        if (null != dialogWindow) {
            // TODO: 2017/4/17  添加出现动画效果
            dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager m = dialogWindow.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            p.width = d.getWidth(); // 宽度设置为与屏幕同宽
            dialogWindow.setAttributes(p);
            mDialog.setCanceledOnTouchOutside(false);//外部禁止点击
        }
        // TODO: 2017/4/17 外部是否可以点击取消？看需求吧
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mDialog != null) {
            dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mDialog != null) {
            dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public interface DialogClickListener {

        void onCancleClick();//取消按钮点击事件

        void onPwdCompleteListener(String pwd);//输入完成回调，将密码传递

    }

    public interface OnKeyListener {
        boolean onkeyDown(int keyCode, KeyEvent event);
    }

    public void setOnKeyListener(OnKeyListener listener) {
        this.onKeyListener = listener;
    }

}
