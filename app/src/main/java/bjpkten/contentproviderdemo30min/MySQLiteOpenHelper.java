package bjpkten.contentproviderdemo30min;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kodulf on 2019/5/23.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "db";
    public static int number = 1008;

    public static final String TABLE_STUDENT = "student";
    public static final String TABLE_STUDENT_COLUMN_NAME = "name";
    public static final String TABLE_STUDENT_COLUMN_AGE = "age";
    public static final String TABLE_TEACHER = "teacher";
    public static final String TABLE_TEACHER_COLUMN_CLASS = "class";
    public static final String TABLE_TEACHER_COLUMN_CODE = "sex";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE "+TABLE_STUDENT + "( " +TABLE_STUDENT_COLUMN_NAME + " TEXT, " +
            " " + TABLE_TEACHER_COLUMN_CODE + " INTEGER)";

    public static final String CREATE_TABLE_SQL_TEACHER = "CREATE TABLE "+TABLE_TEACHER + "( " +TABLE_TEACHER_COLUMN_CLASS + " TEXT, " +
            " " + TABLE_TEACHER_COLUMN_CODE + " INTEGER)";

    public MySQLiteOpenHelper(Context context){
        this(context,DB_NAME,null,2);
    }

    private MySQLiteOpenHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private MySQLiteOpenHelper( Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version,  DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    private MySQLiteOpenHelper( Context context,  String name, int version, SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
        db.execSQL(CREATE_TABLE_SQL_TEACHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
