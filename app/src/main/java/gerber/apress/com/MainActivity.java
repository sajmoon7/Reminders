package gerber.apress.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
private ListView mListView;
private RemindersDbAdapter mDbAdapter;
private RemindersSimpleCursorAdapter mCursorAdapter;
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.reminders_list_view);
        mListView.setDivider(null);
        mDbAdapter = new RemindersDbAdapter(this);
        mDbAdapter.open();
        if(savedInstanceState == null){
            mDbAdapter.deleteAllReminders();
            mDbAdapter.createReminder("Kupić książkę", true);
            mDbAdapter.createReminder("Wysłać prezent ojcu", false);
            mDbAdapter.createReminder("Piątkowy obiad ze znajomymi", false);
            mDbAdapter.createReminder("Gra w squasha", false);
            mDbAdapter.createReminder("Odgarnąć i posolić podjazd", false);
            mDbAdapter.createReminder("Przygotować program zajęć z Androida", true);
            mDbAdapter.createReminder("Kupić nowe krzesło do biura", false);
            mDbAdapter.createReminder("Zadzwonić do mechanika", false);
            mDbAdapter.createReminder("Odnowić członkostwo w klubie", false);
            mDbAdapter.createReminder("Kupić nowy telefon Android Galaxy", true);
            mDbAdapter.createReminder("Sprzedać stary telefon Android - aukcja", false);
            mDbAdapter.createReminder("Kupić nowe wiosła do kajaka", false);
            mDbAdapter.createReminder("Zadzwonić do księgowego", false);
            mDbAdapter.createReminder("Kupić 300 000 akcji Google", false);
            mDbAdapter.createReminder("Oddzwonić do Dalajlamy", true);
        }
        Cursor cursor = mDbAdapter.fetchAllReminders();
        String[] from = new String[]{RemindersDbAdapter.COL_CONTENT};
        int[] to = new int[]{R.id.row_text};
        mCursorAdapter = new RemindersSimpleCursorAdapter(
                MainActivity.this,
                R.layout.reminders_row,
                cursor,
                from,
                to,
                0
        );
        mListView.setAdapter(mCursorAdapter);
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            ListView modeListView = new ListView(MainActivity.this);
            String[] modes = new String[]{"Edycja przypomnienia", "Usunięcie przypomnienia"};
            ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, modes);
            modeListView.setAdapter(modeAdapter);
            builder.setView(modeListView);
            final Dialog dialog = builder.create();
            dialog.show();
            modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 0){
                        Toast.makeText(MainActivity.this, "edycja pozycji "+masterListPosition, Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "usunięcie pozycji "+masterListPosition, Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
        }
    });
    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.HONEYCOMB){
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.cam_menu,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_item_delete_reminder:
                        for(int nC = mCursorAdapter.getCount() - 1; nC>=0; nC--){
                            if(mListView.isItemChecked(nC)){
                                mDbAdapter.deleteReminderById(getIdfromPosition(nC));
                            }

                        }
                        mode.finish();
                        mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }
    }

    private int getIdfromPosition(int nC) {
        return (int) mCursorAdapter.getItemId(nC);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_reminders, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_new:
                Log.d(getLocalClassName(), "utworzenie nowego przypomnienia");
                return true;
            case R.id.action_exit:
                finish();
                return true;
            default:
                return false;

        }
    }
}