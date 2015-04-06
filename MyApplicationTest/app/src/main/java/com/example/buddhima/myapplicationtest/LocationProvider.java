package com.example.buddhima.myapplicationtest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class LocationProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.example.provider.AndroidApp";
    static final String URL = "content://" + PROVIDER_NAME + "/locations";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String ADDRESS = "address";
    static final String LONGITUDE = "longitude";
    static final String LATITUDE = "latitude";
    static final String COUNT = "count";

    private static HashMap<String, String> LOCATIONS_PROJECTION_MAP;

    static final int LOCATIONS = 1;
    static final int LOCATIONS_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "locations", LOCATIONS);
        uriMatcher.addURI(PROVIDER_NAME, "locations/#", LOCATIONS_ID);
    }


    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "AndroidApp";
    static final String LOCATIONS_TABLE_NAME = "locations";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + LOCATIONS_TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " address TEXT NOT NULL, " +
                    " longitude TEXT NOT NULL, " +
                    " latitude TEXT NOT NULL, " +
                    " count INTEGER NOT NULL);";
// address, longitude, latitude, count

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
            onCreate(sqLiteDatabase);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long row_ID = db.insert(LOCATIONS_TABLE_NAME, "", values);
        if(row_ID > 0){
            Uri _uri = ContentUris.withAppendedId(uri, row_ID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(LOCATIONS_TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case LOCATIONS:
                qb.setProjectionMap(LOCATIONS_PROJECTION_MAP);
                break;
            case LOCATIONS_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if(sortOrder == null || sortOrder == ""){
            /**
             * By default sort on location address
             */
            sortOrder = ADDRESS;
        }

        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case LOCATIONS:
                count = db.delete(LOCATIONS_TABLE_NAME, selection, selectionArgs);
                break;
            case LOCATIONS_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(LOCATIONS_TABLE_NAME, _ID + "=" + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case LOCATIONS:
                count = db.update(LOCATIONS_TABLE_NAME, values, selection, selectionArgs);
                break;
            case LOCATIONS_ID:
                count = db.update(LOCATIONS_TABLE_NAME, values, _ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            /**
             * Get all location records
             */
            case LOCATIONS:
                return "vnd.android.cursor.dir/vnd.example.locations";
            /**
             * Get a particular location
             */
            case LOCATIONS_ID:
                return "vnd.android.cursor.item/vnd.example.locations";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    public LocationProvider() {
    }
}
