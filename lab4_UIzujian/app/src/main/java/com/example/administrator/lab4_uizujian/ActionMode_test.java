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

        SimpleAdapter myadapter=new SimpleAdapter(this,list,R.layout.list_item,
                new String[]{"name","picID"},new int[]{R.id.textview2,R.id.image2});
        ListView listview=findViewById(R.id.listview2);
        listview.setAdapter(myadapter);

        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater= actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu1,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.item_delete:
                        actionMode.finish();
                        return true;
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_check:
                Toast.makeText(this, "click check", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_delete:
                Toast.makeText(this, "click delete", Toast.LENGTH_SHORT).show();
                break;
        }


        return super.onContextItemSelected(item);
    }
}
