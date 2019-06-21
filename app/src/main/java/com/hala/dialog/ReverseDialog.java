/*
package com.hala.dialog;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.hala.R;

public class ReverseDialog {

    public void show(){
        if (signDialog == null) {
            AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
            dialogView = View.inflate(getActivity(), R.layout.video_fragment_dialog, null);

            View ll = dialogView.findViewById(R.id.ll);
            TextView tvSignIn
                    = dialogView.findViewById(R.id.tv_sign_in);

            TextView tvContent
                    = dialogView.findViewById(R.id.tv_content);

            if (baseBean.getResult().getIsSign() == 1) {
                if(autoSignIn){
                    return;
                }
                tvSignIn.setText(getString(R.string.already_received) + "" + baseBean.getResult().getTodayMoney() + "HiCoins");
                ll.setBackground(getResources().getDrawable(R.drawable.dialog_video_signed));
            } else {
                tvSignIn.setText(getString(R.string.received_coins) + "" + baseBean.getResult().getTodayMoney() + "HiCoins");
                tvSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!hasClickSign){
                            hasClickSign =true;
                            signIn();
                            MobclickAgent.onEvent(getActivity(), "home_16");
                        }
                    }
                });
            }

            String string = getResources().getString(R.string.sign_in_1);
            String userInfo = String.format(string, baseBean.getResult().getSignDays()+"", baseBean.getResult().getTodayMoney()+"",baseBean.getResult().getTomorrowMoney()+"");
            tvContent.setText(userInfo);


            bld.setView(dialogView);
            signDialog = bld.create();

            signDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            signDialog.show();
            android.view.WindowManager.LayoutParams p = signDialog.getWindow().getAttributes();//获取对话框当前的参数值     
            p.width = SizeUtils.dp2px(getActivity(), 265);
            signDialog.getWindow().setAttributes(p);
        } else {
            View ll = dialogView.findViewById(R.id.ll);
            TextView tvSignIn
                    = dialogView.findViewById(R.id.tv_sign_in);
            TextView tvContent
                    = dialogView.findViewById(R.id.tv_content);
            if (baseBean.getResult().getIsSign() == 1) {
                tvSignIn.setText(getString(R.string.already_received) + "" + baseBean.getResult().getTodayMoney() + "HiCoins");
                ll.setBackground(getResources().getDrawable(R.drawable.dialog_video_signed));
                tvSignIn.setOnClickListener(null);

            }
            String string = getResources().getString(R.string.sign_in_1);
            String userInfo = String.format(string, baseBean.getResult().getSignDays()+"", baseBean.getResult().getTodayMoney()+"",baseBean.getResult().getTomorrowMoney()+"");
            tvContent.setText(userInfo);

            signDialog.show();
        }
    }
    }

}
*/
