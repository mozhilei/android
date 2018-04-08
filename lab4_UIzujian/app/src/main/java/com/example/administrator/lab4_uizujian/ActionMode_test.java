package com.example.administrator.lab4_uizujian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionMode_test extends AppCompatActivity {

    private String[] number=new String[]{"one","two","three","four","five","six"};
    private int picId =R.drawable.cat;
    private int ifActionMode=0;
    private int[] status=new int[]{0,0,0,0,0,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_mode_test);
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=0;i<number.length;i++)
        {
            Map<String,Object> showitem=new HashMap<String,Object>();
            showitem.put("name",number[i]);
            showitem.put("picID",picId);
            list.add(showitem);
        }

        final SimpleAdapter myadapter=new SimpleAdapter(this,list,R.layout.list_item,
                new String[]{"name","picID"},new int[]{R.id.textview2,R.id.image2});
        final ListView listview=findViewById(R.id.listview2);
        listview.setAdapter(myadapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            private int n=0;
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                long itemid =myadapter.getItemId(i);
                if(b)
                {
                    n++;
                }
                else
                {
                    n--;
                }
                actionMode.setTitle(n + " selected");

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                n = 0;
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.menu2, menu);
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.item_delete:
                        n = 0;
                        actionMode.finish();
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                n=0;
                ifActionMode=0;
            }

        });



        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ifActionMode=1;
                return false;
            }
        });

    }

}
