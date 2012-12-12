package org.vkedco.mobappdev.tic_tac_toe_game_cp_creator_00001;

/*
 **********************************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************************************
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TicTacToeBoardStateDbPopulator {
	static final String SEMICOLON = ";";
	
	 final static void populateTTTBoardStateDb(Context cntxt, String file_path, SQLiteDatabase writeDb) {
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = cntxt.getResources().getAssets()
					.open(file_path, Context.MODE_WORLD_READABLE);
			br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				processGameStateLine(line, writeDb);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 final static void processGameStateLine(String txt, SQLiteDatabase writeDb) {
		if (txt == null) return;
		String[] parts = txt.trim().split(SEMICOLON);
		if (parts != null && parts.length == 3) {
			String board      = parts[0];
			String player     = parts[1];
			String move_utils = parts[2];
			TicTacToeBoardStateDbAdptr.insertUniqueBoadState(board, player, move_utils, writeDb);
		}
	}
}
