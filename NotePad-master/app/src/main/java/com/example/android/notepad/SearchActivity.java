package com.example.android.notepad;
/**
 * 用于显示搜索框，点击搜索图标，跳转到这个页面
 * 显示内容部分，和NotesList几乎一样
 * 通过自己实现的myCursorAdapter来显示搜索结果
 */

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

public class SearchActivity extends ListActivity implements SearchView.OnQueryTextListener {
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            //添加修改日期字段
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
            //添加背景颜色字段
//            NotePad.Notes.COLUMN_NAME_BACK_COLOR,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        SearchView searchview = (SearchView)findViewById(R.id.search_view);
        //为查询文本框注册监听器
        searchview.setOnQueryTextListener(SearchActivity.this);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        String selection = NotePad.Notes.COLUMN_NAME_TITLE + " Like ? ";
        String[] selectionArgs = { "%"+newText+"%" };
        Cursor cursor = managedQuery(
                getIntent().getData(),            // Use the default content URI for the provider.
                PROJECTION,                       // Return the note ID and title for each note. and modifcation date
                selection,                        // 条件左边
                selectionArgs,                    // 条件右边
                NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
        );
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE, NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;
        int[] viewIDs = { R.id.text1,R.id.timestamp1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.noteslist_item,
                cursor,
                dataColumns,
                viewIDs,
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        setListAdapter(adapter);
        return true;

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Constructs a new URI from the incoming URI and the row ID
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
        // Gets the action from the incoming Intent
        String action = getIntent().getAction();
        // Handles requests for note data
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            // Sets the result to return to the component that called this Activity. The
            // result contains the new URI
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {
            // Sends out an Intent to start an Activity that can handle ACTION_EDIT. The
            // Intent's data is the note ID URI. The effect is to call NoteEdit.
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }


//    public class MyCursorAdapter extends SimpleCursorAdapter {
//        public MyCursorAdapter(Context context, int layout, Cursor c,
//                               String[] from, int[] to) {
//
//            super(context,layout,c,from,to,SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
//
//        }
//        @Override
//        public void bindView(View view, Context context, Cursor cursor){
//            super.bindView(view, context, cursor);
//            //从数据库中读取的cursor中获取笔记列表对应的颜色数据，并设置笔记颜色
//            int x = cursor.getInt(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_BACK_COLOR));
//            /**
//             * 白 255 255 255
//             * 黄 247 216 133
//             * 蓝 165 202 237
//             * 绿 161 214 174
//             * 红 244 149 133
//             */
//            switch (x){
//                case NotePad.Notes.DEFAULT_COLOR:
//                    view.setBackgroundColor(Color.rgb(255, 255, 255));
//                    break;
//                case NotePad.Notes.YELLOW_COLOR:
//                    view.setBackgroundColor(Color.rgb(247, 216, 133));
//                    break;
//                case NotePad.Notes.BLUE_COLOR:
//                    view.setBackgroundColor(Color.rgb(165, 202, 237));
//                    break;
//                case NotePad.Notes.GREEN_COLOR:
//                    view.setBackgroundColor(Color.rgb(161, 214, 174));
//                    break;
//                case NotePad.Notes.RED_COLOR:
//                    view.setBackgroundColor(Color.rgb(244, 149, 133));
//                    break;
//                default:
//                    view.setBackgroundColor(Color.rgb(255, 255, 255));
//                    break;
//            }
//        }
//    }


}

