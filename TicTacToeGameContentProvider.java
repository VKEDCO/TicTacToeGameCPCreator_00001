package org.vkedco.mobappdev.tic_tac_toe_game_cp_creator_00001;

/*
 **********************************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************************************
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TicTacToeGameContentProvider extends ContentProvider {
	
	private static SQLiteDatabase mReadDb = null;
	
	static final String AUTHORITY = "org.vkedco.mobappdev.content_providers.tic_tac_toe_boards";
	static final String LOGTAG = TicTacToeGameContentProvider.class.getSimpleName() + "_LOG";
	
	static final int BOARD_STATE_ONE = 1;
	static final String BOARD_STATE_ID_ONE_MIME = "vnd.android.cursor.item/vnd.vkedco.mobappdev.board_state_one";
	static final String BOARD_STATE_QUERY_PATH = TicTacToeBoardStateDbAdptr.BOARD_STATE_TBL + "/query";
	
	static final String EQLS = "=";
	static final String BOARD_PARAMETER = "board";
	
	static final int BOARD_STATE_URI_MATCH_CODE = 1;
	
	private static final UriMatcher mUriMatcher;
	
	static {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		// content://org.vkedco.mobappdev.content_providers.ttt_boards/board_state/query
		mUriMatcher.addURI(AUTHORITY, BOARD_STATE_QUERY_PATH, BOARD_STATE_URI_MATCH_CODE);	
	}
	
	@Override
	public String getType(Uri uri) {
		switch (mUriMatcher.match(uri)) {
		case BOARD_STATE_URI_MATCH_CODE: return BOARD_STATE_ID_ONE_MIME;
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}
	
	@Override
	public boolean onCreate() {
		TicTacToeBoardStateDbAdptr.createTicTacToeBoardStateDbOpenHelper(getContext());
		mReadDb  = TicTacToeBoardStateDbAdptr.getReadDb();
		
		if (mReadDb == null ) return false;
		if (!mReadDb.isOpen()) return false;
	
		return true;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		synchronized (this) {
			return null;
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		synchronized (this) {
			return 0;
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		synchronized (this) {
			SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
			Cursor rslt = null;
			String board = null;

			switch (mUriMatcher.match(uri)) {
			// if this is a row query, add the where clause with the
			// appropriate row number
			case BOARD_STATE_URI_MATCH_CODE:
				qb.setTables(TicTacToeBoardStateDbAdptr.BOARD_STATE_TBL);
				// uri==content://org.vkedco.mobappdev.content_providers.tic_tac_toe_boards/board_state/query?board=<board>
				board = uri.getQueryParameter(BOARD_PARAMETER);
				if (board != null) {
					qb.appendWhere(TicTacToeBoardStateDbAdptr.BOARD_STATE_TBL_BOARD_COL_NAME
							+ EQLS + "\"" + board + "\"");
					rslt = qb.query(mReadDb,
							TicTacToeBoardStateDbAdptr.BOARD_STATE_TBL_COLUMN_NAMES,
							selection, selectionArgs, null, null, sortOrder);
				}
				break;
			default:
				break;
			}

			// notify the content resolver about the change
			if (rslt != null)
				rslt.setNotificationUri(getContext().getContentResolver(), uri);

			return rslt;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		synchronized (this) {
			return 0;
		}
	}
}



	
	

