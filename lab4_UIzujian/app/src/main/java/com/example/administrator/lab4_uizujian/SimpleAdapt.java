package com.example.administrator.lab4_uizujian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAdapt extends AppCompatActivity {
    private String[] name=new String[]{"Lion","Tiger","Monkey","Dog","Cat","Elephant"};
    private int[] picID=new int[]{R.drawable.lion,R.drawable.tiger,R.drawable.monkey,R.drawable.dog,R.drawable.cat,R.drawable.elephant};
    private int[] status=new int[]{0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<name.length;i++)
        {
            Map<String,Object> showitem=new HashMap<String,Object>();
            showitem.put("name",name[i]);
            showitem.put("picID",picID[i]);
            list.add(showitem);
        }
        SimpleAdapter myadapter=new SimpleAdapter(this,list,R.layout.listview_item,
                new String[]{"name","picID"},new int[]{R.id.textview1,R.id.imageview1});
        ListView listview=findViewById(R.id.listview1);
        listview.setAdapter(myadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(getApplicationContext(), name[i], Toast.LENGTH_SHORT).show();
                if(status[i]==0) {
                    view.setBackgroundResource(R.color.red);
                    status[i]=1;
                }
                else
                {    view.setBackgroundResource(R.color.white);
                    status[i]=0;
                }
            }
        });

    }

}
