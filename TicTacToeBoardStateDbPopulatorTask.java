package org.vkedco.mobappdev.tic_tac_toe_game_cp_creator_00001;

/*
 **********************************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************************************
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

public class TicTacToeBoardStateDbPopulatorTask extends AsyncTask<String, Void, String> {
	
	private Context mContext = null;
	final static String DB_CREATED = "TTT DB Created";
	final static String TASK_STARTED = "TTT DB Launched";
	private SQLiteDatabase mWriteDb = null;
	
	public TicTacToeBoardStateDbPopulatorTask(Context cntxt, SQLiteDatabase writeDb) {
		mContext = cntxt;
		mWriteDb = writeDb;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(mContext, TASK_STARTED, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected String doInBackground(String... params) {
		TicTacToeBoardStateDbPopulator.populateTTTBoardStateDb(mContext, params[0], mWriteDb);
		return DB_CREATED;
	}
	
	@Override
	protected void onPostExecute(String rslt) {
		Toast.makeText(mContext, rslt, Toast.LENGTH_SHORT).show();
		mWriteDb.close();
	}

}
