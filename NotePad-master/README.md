Android期中实验完成度说明
====
> *[添加时间戳](#1)  
> *[添加搜索功能](#2)   
> *[界面美化](#3)    
使用谷歌原版notepad进行修改，最低SKD版本，修改为21，以避免使用过时函数   

<h3 id='1'> 1.时间戳</h3>
实现时间戳功能，需要：
+ #### 在布局文件中修改布局，使得文件具备有时间戳的显示位置   
+ #### 在查询数据库时，返回定义好的修改时间**NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE**字段   
+ #### 将存储使用的long类型的系统时间，转化为“年-月-日”格式    
首先修改布局文件noteslist_item.xml，使用一个线性布局，将时间戳显示的TextView添加进去。  
```
		<LinearLayout  xmlns:android="http://schemas.android.com/apk/res/android"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:orientation="vertical"
			>
			<TextView xmlns:android="http://schemas.android.com/apk/res/android"
				android:id="@+id/text1"
				android:layout_width="match_parent"
				android:layout_height="40dp"
				android:textAppearance="?android:attr/textAppearanceLarge"
				android:gravity="center_vertical"
				android:paddingLeft="5dip"
				android:singleLine="true"
				android:textSize="30dp"
				/>

			<TextView
				android:id="@+id/timestamp1"
				android:layout_width="match_parent"
				android:layout_height="25dp"
				android:textAlignment="center"
				android:textSize="18dp"
				android:gravity="center_vertical"
				/>
		</LinearLayout>  
```	
接着，在修改NoteList.java中的PROJECTION，将日期字段读出来  
```
		private static final String[] PROJECTION = new String[] {
				NotePad.Notes._ID, // 0
				NotePad.Notes.COLUMN_NAME_TITLE, // 1
				//添加修改日期字段
				NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,
		};   
```
使用**SimpleDateFormat**就可以将以毫秒为单位的系统时间，转化为想要的格式。转化之后就要想办法应用到**SimpleCursorAdapter**中，有两种方式，一种是重写**myadapt**，继承**SimpleCursorAdapter**，一种是直接使用**SimpleCursorAdapter.ViewBinder**，由于添加时间戳功能较为简单，所以采用第二种方式。  
```
		//将读出的数据转化为年月日类型
        SimpleCursorAdapter.ViewBinder viewBinder=new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
				//修改条目的背景颜色
                view.setBackgroundColor(Color.rgb(255,218,185));
                if(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE)==i)
                {
                    TextView textView1=(TextView)view;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss",Locale.CHINA);
                    Long nowtime=cursor.getLong(i);
                    Date date=new Date(nowtime);
                    String time=format.format(date);
                    Log.d("TIME", "onCreate1:"+time+"  cursor.getinti= "+nowtime);
                    textView1.setText(time);
                    return true;
                }
                return false;
            }
        };
        //应用viewBinder
        adapter.setViewBinder(viewBinder);
```
应用后的效果如图：   
![截图1](https://github.com/mozhilei/android/blob/master/NotePad-master/screenshot/1.gif)

<h3 id='2'>2.搜索功能 </h3>
搜索功能的实现相对简单，在一个布局中，上部有一个搜索框，下部，是同**NoteList.java**中的显示页面。  
+ #### 新建一个Activity，以及对应的布局（SearchActivity,activity_search.xml）
+ #### 使用和**NoteList.java**中相同的**SimpleCursorAdapter**实现  
在onCreate中加载布局，为搜索框设置监听器，重载**onQueryTextSubmit()**和**onQueryTextChange()**
```
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }
        SearchView searchview = (SearchView)findViewById(R.id.search_view);
        //为搜索框设置监听器
        searchview.setOnQueryTextListener(SearchActivity.this);
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
```
**onQueryTextChange()**的实现和**NoteList.java**中关于**SimpleCursorAdapter**实现方法相同。
```   
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

        SimpleCursorAdapter.ViewBinder viewBinder=new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {

                view.setBackgroundColor(Color.rgb(176,226,255));
                if(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE)==i)
                {
                    TextView textView1=(TextView)view;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.CHINA);
                    Long nowtime=cursor.getLong(i);
                    Date date=new Date(nowtime);
                    String time=format.format(date);
                    Log.d("TIME", "onCreate1:"+time+"  cursor.getinti= "+nowtime);
                    textView1.setText(time);
                    return true;
                }
                return false;
            }
        };
        //应用viewBinder
        adapter.setViewBinder(viewBinder);
        setListAdapter(adapter);
        return true;
    }
```	
实现效果如图：  
![截图2](https://github.com/mozhilei/android/blob/master/NotePad-master/screenshot/2.gif)  

<h3 id='3'>3.界面美化 </h3>

#### 3.1.修改字体大小，原本的字体太小，使用不便  
![]() 


#### 3.2.修改了应用的主题，黑色过于压抑，使用更为明亮的色彩搭配  
![]() 

#### 3.3.条目的背景颜色经过精心搭配，主页面用淡粉色，搭配白色页面较为美观，搜索页面用蓝色，跳出搜索条目比较醒目  
![]()    

最后修改日期：2018年6月2日




