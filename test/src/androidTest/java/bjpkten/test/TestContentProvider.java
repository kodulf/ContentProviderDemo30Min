package bjpkten.test;

import android.net.Uri;

import junit.framework.TestCase;

/**
 * Created by Kodulf on 2019/5/26.
 */
public class TestContentProvider extends TestCase {

    public void testInsert(){
        Uri uri = Uri.parse("content://kodulf.contentprovider/student");
    }
}
