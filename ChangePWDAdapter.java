package com.tra.loginscreen;

import static android.content.ContentValues.TAG;

import static org.apache.commons.beanutils.ConvertUtils.convert;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChangePWDAdapter extends  BaseAdapter  implements View.OnClickListener {

    private Context context;
    //数据项
    private List<String> data;
    public ChangePWDAdapter(List<String> data){
        this.data = data;
    }
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;

        if(context == null)
            context = viewGroup.getContext();

        if(view == null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_itempwdchange,null);
            viewHolder = new ViewHolder();
            viewHolder.mTv = (TextView)view.findViewById(R.id.mTv);
            viewHolder.mPwdModification = (ImageView)view.findViewById(R.id.mpwdimg);
            view.setTag(viewHolder);

        }
        //获取viewHolder实例
        viewHolder = (ViewHolder)view.getTag();
        //设置数据
        viewHolder.mTv.setText(data.get(i));
        //设置监听事件
        viewHolder.mTv.setOnClickListener(this);
        //设置数据
        // viewHolder.mBtn.setText("点我点我"+ i);
        viewHolder.mPwdModification.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        /*
        switch (view.getId()){
            case R.id.mBtn:
               //  Log.d(TAG, "onClick: Btn_onClick: " + "view = " + view);
                Toast.makeText(context,"我是按钮",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mTv:
               //  Log.d("tag", "Tv_onClick: " + "view = " + view);
                Toast.makeText(context,"我是文本", Toast.LENGTH_SHORT).show();
                break;
        }   */

        if (view.getId() == R.id.mpwdimg) {
            Toast.makeText(context,"我是按钮",Toast.LENGTH_SHORT).show();



        }
        else if (view.getId() == R.id.mTv)  {
            Toast.makeText(context,"我是文本", Toast.LENGTH_SHORT).show();
        }
    }

    static class ViewHolder{
        TextView mTv;
        ImageView mPwdModification;
    }


}
