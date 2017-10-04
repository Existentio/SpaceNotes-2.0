package com.existentio.spacenotesmvc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.existentio.spacenotesmvc.R;
import com.existentio.spacenotesmvc.controller.DBHelper;
import com.existentio.spacenotesmvc.data.PrefHelper;
import com.existentio.spacenotesmvc.model.Notes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;

public class MainActivity extends AppCompatActivity {
    public static List<Notes> notes = new ArrayList<>();

    Toolbar toolbar;
    private DBHelper db;
    private RelativeLayout container;
    private NoteItemAdapter adapter = new NoteItemAdapter(this);

    private String theme;

    BaseFragment fragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (RelativeLayout) findViewById(R.id.main_container);
        db = new DBHelper(this);

        initToolbar();

        initBaseView();
    }

    private void initBaseView() {
        fragment = BaseFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, fragment)
                .commit();
        fragment.getFragmentManager().beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences pref = this.getSharedPreferences(this.getPackageName() + "_preferences", Context.MODE_PRIVATE);
//        theme = pref.getString("theme", "light-sans");

        initSettings();
    }

    private void initSettings() {
        SharedPreferences spTheme = getSharedPreferences(PrefHelper.PREF_THEME,
                Context.MODE_PRIVATE);

        if (spTheme.contains(PrefHelper.KEY_THEME)) {
            container.setBackgroundResource(R.drawable.menu_background);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, AddNoteFragment.newInstance())
                        .setTransition(TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack("frags")
//                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.action_settings:
                settingsFragment = SettingsFragment.newInstance();
                if (!settingsFragment.isAdded()) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_container, settingsFragment)
                            .setTransition(TRANSIT_FRAGMENT_OPEN)
//                            .addToBackStack("frags")
                            .addToBackStack(null)
                            .commit();
                }
                break;

            case R.id.action_save:
                Toast.makeText(this, "Note have saved", Toast.LENGTH_LONG).show();
                saveToDb();
                fragment.getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();
                if (!fragment.isVisible()) {
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;

            case R.id.action_back:
            case R.id.action_settings_internal:
                onBackPressed();
                break;

            case R.id.action_share:
                String contents = EditNoteFragment.newInstance().textView.getText().toString();
                if (contents.equals("")) {

                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, contents);
                    intent.setType("text/plain");
                    if (intent.resolveActivity(this.getPackageManager()) != null)
                        startActivity(Intent.createChooser(intent, getResources().getText(R.string.send_to)));
                }
                break;

            case R.id.action_back_sv:
                updToDb();
                fragment.getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();
                if (!fragment.isVisible()) {
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
        }
        return true;
    }

    public void saveToDb() {
        EditText etText = (EditText) findViewById(R.id.et_enter_text);
        String text = etText.getText().toString();
        String date = DateFormat.getDateInstance
                (DateFormat.SHORT).format(Calendar.getInstance().getTime());
        if (text.isEmpty()) {
            Toast.makeText(this, "empty note", Toast.LENGTH_SHORT).show();
        } else {
            db.addNote(text, date);
            Toast.makeText(this, "note added", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    public void updToDb() {
        EditText etText = (EditText) findViewById(R.id.edit_tv);
        String text = etText.getText().toString();
        String date = DateFormat.getDateInstance
                (DateFormat.SHORT).format(Calendar.getInstance().getTime());
        if (text.isEmpty()) {
            Toast.makeText(this, "empty note", Toast.LENGTH_SHORT).show();
        } else {
            db.updRec(text, date, adapter.getListId());
            Log.d("id", String.valueOf(adapter.getListId()));
            Toast.makeText(this, "rarara!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (AddNoteFragment.newInstance() != null) {
//            toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            super.onBackPressed();

        }

    }

}
