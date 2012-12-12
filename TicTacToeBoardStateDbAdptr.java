package org.vkedco.mobappdev.tic_tac_toe_game_cp_creator_00001;

/*
 **********************************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************************************
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class TicTacToeBoardStateDbAdptr {
	
	static final String ADPTR_LOGTAG = TicTacToeBoardStateDbAdptr.class.getSimpleName() + "_TAG";
	
	static final String DB_NAME     = "ttt_game.db";
	static final int    DB_VERSION  = 1;
	static final String BOARD_STATE_TBL = "board_state";
	
	// constants for board_state table column names
	static final String BOARD_STATE_TBL_ID_COL_NAME        = "ID";
	static final String BOARD_STATE_TBL_BOARD_COL_NAME     = "Board";
	static final String BOARD_STATE_TBL_PLAYER_COL_NAME    = "Player";
	static final String BOARD_STATE_TBL_MOVEUTILS_COL_NAME = "MoveUtils";

	static final String[] BOARD_STATE_TBL_COLUMN_NAMES = 
	{ 
		BOARD_STATE_TBL_ID_COL_NAME, 
		BOARD_STATE_TBL_BOARD_COL_NAME, 
		BOARD_STATE_TBL_PLAYER_COL_NAME, 
		BOARD_STATE_TBL_MOVEUTILS_COL_NAME,
	};
	
	// constants for board_state table column numbers
	static final int BOARD_STATE_TBL_ID_COL_NUM        = 0;
	static final int BOARD_STATE_TBL_BOARD_COL_NUM	   = 1;
	static final int BOARD_STATE_TBL_PLAYER_COL_NUM    = 2;
	static final int BOARD_STATE_TBL_MOVEUTILS_COL_NUM = 3;
	
	private static TicTacToeBoardStateDBOpenHelper mDbHelper = null;
	
	private static class TicTacToeBoardStateDBOpenHelper extends SQLiteOpenHelper {
		static final String HELPER_LOGTAG = TicTacToeBoardStateDBOpenHelper.class.getSimpleName() + "_TAG";
		
		public TicTacToeBoardStateDBOpenHelper(Context context, String name, 
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		// table creation string constant
		static final String BOARD_STATE_TBL_CREATE =
			"create table " + BOARD_STATE_TBL + 
			" (" + 
			BOARD_STATE_TBL_ID_COL_NAME        + " integer primary key autoincrement, " + 
			BOARD_STATE_TBL_BOARD_COL_NAME     + " text not null, " + 
			BOARD_STATE_TBL_PLAYER_COL_NAME    + " text not null, " +
			BOARD_STATE_TBL_MOVEUTILS_COL_NAME + " text not null " + 
			");";
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(HELPER_LOGTAG, BOARD_STATE_TBL_CREATE);
			db.execSQL(BOARD_STATE_TBL_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, 
				int newVersion) {
			Log.d(ADPTR_LOGTAG, "Upgrading from version " +
					oldVersion + " to " +
					newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + BOARD_STATE_TBL_CREATE);
			onCreate(db);
		}
	} // end of Helper
	
	// Strongly typed insertion method. Insert the board state if and only if it is 
	// not already in the database.
	static final long insertUniqueBoadState(String board, String player, String move_utils, SQLiteDatabase writeDb) {
		ContentValues newBoardState = new ContentValues();
		newBoardState.put(BOARD_STATE_TBL_BOARD_COL_NAME, board);
		newBoardState.put(BOARD_STATE_TBL_PLAYER_COL_NAME, player);
		newBoardState.put(BOARD_STATE_TBL_MOVEUTILS_COL_NAME, move_utils);
		
		if ( writeDb == null ) Log.d(ADPTR_LOGTAG, "writeDb is null");
		Cursor rslt = writeDb.query(BOARD_STATE_TBL,
				new String[] { BOARD_STATE_TBL_BOARD_COL_NAME }, 
				BOARD_STATE_TBL_BOARD_COL_NAME  + "=" + "\"" + board + "\"", 
				null, null, null, null);
		long insertedRowIndex = -1;
		if ((rslt.getCount() == 0 || !rslt.moveToFirst())) {
			insertedRowIndex = writeDb.insertWithOnConflict(
					BOARD_STATE_TBL, null, newBoardState,
					SQLiteDatabase.CONFLICT_REPLACE);
		}

		rslt.close();
		//Log.d(ADPTR_LOGTAG, "Inserted board state record " + insertedRowIndex);
		return insertedRowIndex;
	}
	
	// retrieve boards
	static final String[] QUERY_RETRIEVE_BOARD_MOVE_UTILS_COLNAMES = BOARD_STATE_TBL_COLUMN_NAMES;
	static final String   QUERY_RETRIEVE_BOARD_MOVE_UTILS_WHERE_CLAUSE = BOARD_STATE_TBL_BOARD_COL_NAME + "=?";
	static final Cursor retrieveMoveUtilsByBoard(final String board, SQLiteDatabase readDb) {
		try {
			//Log.d(ADPTR_LOGTAG, "QUERY Readable DB");
			Cursor crsr =
				readDb.query(BOARD_STATE_TBL, // table name 
						QUERY_RETRIEVE_BOARD_MOVE_UTILS_COLNAMES, // column names 
						QUERY_RETRIEVE_BOARD_MOVE_UTILS_WHERE_CLAUSE, // where clause
						new String[]{board},    // selection args
						null, 			    	// group by
						null, 					// having
						null); 					// order by
			return crsr;
		}
		catch (SQLiteException ex) {
			Log.d(ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
	
	static final void createTicTacToeBoardStateDbOpenHelper(Context cntxt) {
		mDbHelper = new TicTacToeBoardStateDBOpenHelper(cntxt, DB_NAME, null, DB_VERSION);
	}
	
	static final SQLiteDatabase getWriteDb() {
		try {
			return mDbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			Log.d(TicTacToeBoardStateDbAdptr.ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}

	static final SQLiteDatabase getReadDb() {
		try {
			return mDbHelper.getReadableDatabase();
		} catch (SQLiteException ex) {
			Log.d(TicTacToeBoardStateDbAdptr.ADPTR_LOGTAG, ex.toString());
			return null;
		}
	}
}
