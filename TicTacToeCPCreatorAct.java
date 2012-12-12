package org.vkedco.mobappdev.tic_tac_toe_game_cp_creator_00001;

/*
 **********************************************************************************
 * Bugs to vladimir dot kulyukin at gmail dot com
 **********************************************************************************
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TicTacToeCPCreatorAct extends Activity {
	
	final static String ASSETS_GAME_STATE_TREE_FILE = "large_game_state_tree.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_cp_creator_activity_layout);
        TicTacToeBoardStateDbAdptr.createTicTacToeBoardStateDbOpenHelper(this);
        new TicTacToeBoardStateDbPopulatorTask(this, TicTacToeBoardStateDbAdptr.getWriteDb())
        			.execute(ASSETS_GAME_STATE_TREE_FILE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tic_tac_toe_cp_creator_activity_layout, menu);
        return true;
    }
}
