package com.example.kiit.mybagdkart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kiit.mybagdkart.R;
import com.example.kiit.mybagdkart.bean.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kiit on 22-12-2016.
 */
public class LocationViewAdapter extends BaseAdapter {
    private List<Location> mSearchPhoneBook2;
    // private Context mContext;

    private ArrayList<Location> arraylist;
    private LayoutInflater inflater;

    private class ViewHolder{


        TextView tvName2;

    }

    public LocationViewAdapter(Context context, List<Location> list)
    {
        inflater= LayoutInflater.from(context);
        mSearchPhoneBook2=list;

        this.arraylist = new ArrayList<Location>();
        this.arraylist.addAll(list);
    }

    public int getCount() {
        return mSearchPhoneBook2.size();
    }

    public Object getItem(int arg0)
    {
        return mSearchPhoneBook2.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }

    public View getView(int arg0, View arg1, ViewGroup arg2)
    {
        ViewHolder holder=null;
        //PhoneBook entry = mListPhoneBook.get(arg0);
        if (arg1 == null)
        {
            holder=new ViewHolder();
            // LayoutInflater inflater = LayoutInflater.from(mContext);
            arg1 = inflater.inflate(R.layout.address_page_list, null);

            holder.tvName2=(TextView)arg1.findViewById(R.id.textView_current_location);

            arg1.setTag(holder);
        }
        else {
            holder=(ViewHolder)arg1.getTag();
        }


        holder.tvName2.setText(mSearchPhoneBook2.get(arg0).getName());
        return arg1;
    }
    public void filter(String ob){
        ob=ob.toLowerCase(Locale.getDefault());
        mSearchPhoneBook2.clear();
        if (ob.length()==0){
            mSearchPhoneBook2.addAll(arraylist);
        }
        else {
            for (Location wp:arraylist){
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(ob)){
                    mSearchPhoneBook2.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
