package gerber.apress.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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