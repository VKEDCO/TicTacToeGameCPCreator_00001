1) Eclipse Project (TicTacToeGameCPCreator_00001.rar) with an Android 
application that implements a ContentProvider. The application creates
a content provder and populates an SQLite database (ttt_game.db). The 
provider can be used by Tic-Tac-Toe playing applications to retrieve
optimal min/max moves for given board states.

2) The ContentProvider is implemented in TicTacToeGameContentProvider.java.

3) Targets: Android 2.3+, Eclipse Helios 3.6

4) To start: 1) unzip into a folder; 2) open in Eclipse; 3) Run
as Android application to install and register the content provider.
The installation runs as an AsyncTask. When completed, the task
toasts a message that the database has been created.




