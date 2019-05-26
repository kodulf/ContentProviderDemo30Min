package bjpkten.contentproviderdemo30min.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import bjpkten.contentproviderdemo30min.MySQLiteOpenHelper;

public class MyContentProvider extends ContentProvider {

    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private SQLiteDatabase mDb;

    public static final String AUTHRITY = "kodulf.contentprovider";
    public static final String URI_STUDENT = "content://" + AUTHRITY + "/" +MySQLiteOpenHelper.TABLE_STUDENT;
    public static final String URI_TEACHER = "content://" + AUTHRITY + "/" +MySQLiteOpenHelper.TABLE_TEACHER;

    static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int STUDENT_CODE = 1;
    public static final int STUDENT_NUMBER_CODE = 2;
    public static final int TEACHER_CODE = 3;
    public static final int TEACHER_NUMBER_CODE = 4;

    static {
        mUriMatcher.addURI(AUTHRITY,MySQLiteOpenHelper.TABLE_STUDENT,STUDENT_CODE);
        mUriMatcher.addURI(AUTHRITY,MySQLiteOpenHelper.TABLE_STUDENT+"/#",STUDENT_NUMBER_CODE);

        mUriMatcher.addURI(AUTHRITY,MySQLiteOpenHelper.TABLE_TEACHER,TEACHER_CODE);
        mUriMatcher.addURI(AUTHRITY,MySQLiteOpenHelper.TABLE_TEACHER+"/#",TEACHER_NUMBER_CODE);
    }

    public MyContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int ret = -1;

        switch (mUriMatcher.match(uri)){
            case STUDENT_CODE:
                    ret  = mDb.delete(uri.getLastPathSegment(),selection,selectionArgs);
                    getContext().getContentResolver().notifyChange(uri,null);
                break;

            case STUDENT_NUMBER_CODE:
                long l = ContentUris.parseId(uri);
                String where = "age="+ l;

                if(selection!=null&&(!"".equals(selection.trim()))){
                    where = " and " + selection;
                }
                ret = mDb.delete(MySQLiteOpenHelper.TABLE_STUDENT,where,selectionArgs);
                getContext().getContentResolver().notifyChange(uri,null);

                break;

                default:
                    throw new IllegalArgumentException("");

        }

        return ret;

    }


    /**
     * getType 返回的是我们的数据类型是什么，有的时候数据类型是text，有的时候是数据库。
     *
     * Implement this to handle requests for the MIME type of the data at the
     *      * given URI.  The returned MIME type should start with
     *      * <code>vnd.android.cursor.item</code> for a single record,
     *      * or <code>vnd.android.cursor.dir/</code> for multiple items.
     *      * This method can be called from multiple threads, as described in
     *      * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     *      * and Threads</a>.
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        //我们也分类型进行返回

        switch (mUriMatcher.match(uri)) {

            //如果是多条数据的
            case STUDENT_CODE:
                return "vnd.android.cursor.dir/student";
            //如果是单挑数据的
            case STUDENT_NUMBER_CODE:
                return "vnd.android.cursor.item/student";

                default:
                    throw new IllegalArgumentException("");
        }



    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //可以直接通过getLastPathSegment 来获取最后一个，但是最好通过URIMatch 来做
        String lastPathSegment = uri.getLastPathSegment();

        switch (mUriMatcher.match(uri)) {
            case STUDENT_CODE:
                //第二个参数为字段名称
                long rowId = mDb.insert(MySQLiteOpenHelper.TABLE_STUDENT, null, values);
                //content://kodulf.contentprovider/student/10
                //可以有两种方法：
                Uri parse = Uri.parse(URI_STUDENT + "/" + rowId);
                Uri uri1 = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(uri,null);

                return parse;

            case STUDENT_NUMBER_CODE:
                break;

            default:
                throw new IllegalArgumentException("Wrong parameter");
        }

            return uri;
    }

    @Override
    public boolean onCreate() {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext());
        mDb = mySQLiteOpenHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String lastPathSegment = uri.getLastPathSegment();
        Cursor query = mDb.query(lastPathSegment, null, null, null, null, null, null);

        return query;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
