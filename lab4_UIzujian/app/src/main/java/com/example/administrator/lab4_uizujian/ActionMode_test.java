package com.example.administrator.lab4_uizujian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.net.Uri;


public class ActionMode_test extends AppCompatActivity {

    private String[] number=new String[]{"one","two","three","four","five","six","seven"};
    private Myadapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_mode_test);
        myadapter=new Myadapter(this,R.layout.list_item,R.id.textview2,number);
        final ListView listview=findViewById(R.id.listview2);
        listview.setAdapter(myadapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            private int nr=0;
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if (b)
                {
                    nr++;
                    myadapter.setNewSelection(i,b);
                }
                else
                {
                    nr--;
                    myadapter.removeSelection(i);
                }
                actionMode.setTitle(nr+" selected");

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater=getMenuInflater();
                inflater.inflate(R.menu.menu2,menu);
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
                        nr = 0;
                        myadapter.clearSelection();
                        actionMode.finish();
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                myadapter.clearSelection();

            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               listview.setItemChecked(i,!myadapter.isPositionChecked(i));
                return false;
            }
        });

    }

    public class Myadapter extends ArrayAdapter<String>
    {
        private HashMap<Integer,Boolean> mSelection=new HashMap<Integer,Boolean>();
        public Myadapter(Context context, int resource,int textViewResourceId, String[] objects)
        {
            super(context, resource, textViewResourceId, objects);
        }
        public void setNewSelection(int position, boolean value) {
            mSelection.put(position, value);
            notifyDataSetChanged();
        }
        public boolean isPositionChecked(int position) {
            Boolean result = mSelection.get(position);
            return result == null ? false : result;
        }

        public void removeSelection(int position) {
            mSelection.remove(position);
            notifyDataSetChanged();
        }

        public void clearSelection() {
            mSelection = new HashMap<Integer, Boolean>();
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v=super.getView(position,convertView,parent);
            v.setBackgroundColor(getResources().getColor(R.color.white));

            if(mSelection.get(position)!=null)
            {
                v.setBackgroundColor(getResources().getColor(R.color.red));
            }
            return super.getView(position, convertView, parent);
        }
    }

}
