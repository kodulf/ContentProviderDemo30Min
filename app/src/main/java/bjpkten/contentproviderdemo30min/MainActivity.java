package bjpkten.contentproviderdemo30min;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import bjpkten.contentproviderdemo30min.db.MyContentProvider;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getContentResolver().registerContentObserver(Uri.parse(MyContentProvider.URI_STUDENT), false, new ContentObserver(new Handler()) {
            @Override
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.d("kodulfdb","onChange selfChange");

            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.d("kodulfdb","onChange selfChange, Uri uri");
            }
        });

    }

    public void queryAndInsertContentProviderForStudent(View view) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.TABLE_STUDENT_COLUMN_NAME,"Kodulf");
        contentValues.put(MySQLiteOpenHelper.TABLE_STUDENT_COLUMN_AGE,MySQLiteOpenHelper.number++);

        ContentResolver contentResolver = getContentResolver();
        Uri parse = Uri.parse(MyContentProvider.URI_STUDENT);
        contentResolver.insert(parse,contentValues);

        Cursor query = contentResolver.query(parse, null, null, null, null, null);

        try {
            while (query.moveToNext()) {
                String name = query.getString(0);
                int age = query.getInt(1);

                String firstColumn = query.getColumnName(0);
                String secondColumn = query.getColumnName(1);

                Log.d("kodulfdb", "ContentProvider " + firstColumn + " : " + name + " " + secondColumn + ": " + age);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(query!=null)
                    query.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Using Database directly
     * @param view
     */
    public void queryAndInsertDirectForStudent(View view) {

        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(this);
        SQLiteDatabase writableDatabase = mySQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.TABLE_STUDENT_COLUMN_NAME,"Kodulf");
        contentValues.put(MySQLiteOpenHelper.TABLE_STUDENT_COLUMN_AGE,MySQLiteOpenHelper.number++);
        long insert = writableDatabase.insert(MySQLiteOpenHelper.TABLE_STUDENT, null, contentValues);
        if (insert == -1) {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }

        Cursor query = writableDatabase.query(MySQLiteOpenHelper.TABLE_STUDENT, null, null, null, null, null, null);

        try {
            while (query.moveToNext()) {
                String name = query.getString(0);
                int age = query.getInt(1);

                String firstColumn = query.getColumnName(0);
                String secondColumn = query.getColumnName(1);

                Log.d("kodulfdb", firstColumn + " : " + name + " " + secondColumn + ": " + age);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(query!=null)
                     query.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        writableDatabase.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("kodulfon","onDestroy");
    }

    public void queryAndInsertContentProviderForTeacher(View view) {

    }

    public void queryAndInsertDirectForTeacher(View view) {

    }

    public void deleteFirstUsingContentProvider(View view) {

        ContentResolver contentResolver = getContentResolver();
        Uri parse = Uri.parse(MyContentProvider.URI_STUDENT+"/1011");

        int delete = contentResolver.delete(parse, null, null);
        Log.d("kodulfdb","studentid = " +delete);


        Uri parse1 = Uri.parse(MyContentProvider.URI_STUDENT);
        Cursor query = contentResolver.query(parse1, null, null, null, null, null);

        try {
            while (query.moveToNext()) {
                String name = query.getString(0);
                int age = query.getInt(1);

                String firstColumn = query.getColumnName(0);
                String secondColumn = query.getColumnName(1);

                Log.d("kodulfdb", "ContentProvider " + firstColumn + " : " + name + " " + secondColumn + ": " + age);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(query!=null)
                    query.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
