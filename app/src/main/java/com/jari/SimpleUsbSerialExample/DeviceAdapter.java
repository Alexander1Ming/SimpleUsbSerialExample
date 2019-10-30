package com.jari.SimpleUsbSerialExample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author:sunchen
 * @createdtime:2019/10/29
 * @email:sunchencs@foxmail.com
 */
public class DeviceAdapter extends BaseAdapter {
    private Context context;
    private List<ListItem> datas;
    //构造函数需要传入两个必要的参数：上下文对象和数据源
    public DeviceAdapter(Context context,List<ListItem> datas) {
        this.context=context;
        this.datas=datas;
    }
    //返回子项的个数
    @Override
    public int getCount() {
        return datas.size();
    }
    //返回子项对应的对象
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }
    //返回子项的下标
    @Override
    public long getItemId(int position) {
        return position;
    }
    //返回子项视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("SerialExample", "getView called, position: " + position + ", convertView: " + convertView);
        ListItem listItem= (ListItem) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.device_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.tvDeviceName = (TextView) view.findViewById(R.id.tv_device_name);
            viewHolder.tvDeviceUndetermined = (TextView)view.findViewById(R.id.tv_device_undetermined);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        if (listItem.getDevice() != null) {
            viewHolder.tvDeviceName.setText(listItem.getDevice().getDeviceName());
        } else {
            viewHolder.tvDeviceName.setText("没有设备");
        }

        // TODO: 2019/10/29 用来显示设备的其他属性
//        viewHolder.tvDeviceUndetermined.setImageResource(ListItem.getImgId());
        return view;
    }
    //创建ViewHolder类
    class ViewHolder{
        TextView tvDeviceName;
        TextView tvDeviceUndetermined;
    }



}
