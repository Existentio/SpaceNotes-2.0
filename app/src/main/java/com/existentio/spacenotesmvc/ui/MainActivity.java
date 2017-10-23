package com.existentio.spacenotesmvc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.existentio.spacenotesmvc.R;
import com.existentio.spacenotesmvc.data.DBHelper;
import com.existentio.spacenotesmvc.util.PrefHelper;
import com.existentio.spacenotesmvc.model.Notes;
import com.existentio.spacenotesmvc.util.FragmentConditions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.existentio.spacenotesmvc.util.FragmentConditions.CONDITION_ADD;
import static com.existentio.spacenotesmvc.util.FragmentConditions.CONDITION_SAVE;
import static com.existentio.spacenotesmvc.util.FragmentConditions.CONDITION_SETTINGS;

public class MainActivity extends AppCompatActivity {
    public static List<Notes> notes = new ArrayList<>();

    private Toolbar toolbar;
    private DBHelper db;
    private RelativeLayout container;
    private NoteItemAdapter adapter = new NoteItemAdapter(this);
    private String theme;
    private BaseFragment baseFragment;
    private SettingsFragment settingsFragment;

    private static final int ADD_NOTE = 100;
    private static final int EDIT_NOTE = 101;

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
        baseFragment = BaseFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, baseFragment)
                .commit();
        baseFragment.getFragmentManager().beginTransaction()
                .detach(baseFragment)
                .attach(baseFragment)
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
        initSettings();
    }

    private void initSettings() {
        SharedPreferences spTheme = getSharedPreferences(PrefHelper.PREF_THEME,
                Context.MODE_PRIVATE);
        SharedPreferences spDate = getSharedPreferences(PrefHelper.PREF_DATE,
                Context.MODE_PRIVATE);

        if (spTheme.contains(PrefHelper.KEY_THEME)) {
            container.setBackgroundResource(R.drawable.spacetheme);
        } else if (spTheme.contains(PrefHelper.KEY_THEME_WASTELAND)) {
            container.setBackgroundResource(R.drawable.mars_theme);
        } else if (spTheme.contains(PrefHelper.KEY_THEME_MINIMAL)) {
            container.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black));
        }

        if (spDate.contains(PrefHelper.KEY_DATE)) {
//            container.setBackgroundResource(R.drawable.menu_background);
//            container.setBackgroundResource(R.drawable.spacetheme);
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
                CONDITION_ADD.replace(baseFragment, AddNoteFragment.newInstance(), null);
                break;

            case R.id.action_settings:
                CONDITION_SETTINGS.replace(baseFragment, SettingsFragment.newInstance(), null);
                break;

            case R.id.action_save:
                Toast.makeText(this, "Запись сохранена", Toast.LENGTH_LONG).show();
                saveToDb(fetchData(ADD_NOTE));
                CONDITION_SAVE.replace(baseFragment, null, null);
                break;

            case R.id.action_back:
                onBackPressed();
                break;

            case R.id.action_settings_internal:
                FragmentManager fm = getSupportFragmentManager();
                FragmentConditions.CONDITION_FOUR.replace(baseFragment, settingsFragment, fm);
                break;

//
            case R.id.action_back_sv:
                updToDb(fetchData(EDIT_NOTE));
                CONDITION_SAVE.replace(baseFragment, null, null);

                if (!baseFragment.isVisible()) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;


//            case R.id.action_share:
//                String contents = EditNoteFragment.newInstance().text.getText().toString();
//                if (contents.equals("")) {
//
//                } else {
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_SEND);
//                    intent.putExtra(Intent.EXTRA_TEXT, contents);
//                    intent.setType("text/plain");
//                    if (intent.resolveActivity(this.getPackageManager()) != null)
//                        startActivity(Intent.createChooser(intent, getResources().getText(R.string.send_to)));
//                }
//                break;
        }
        return true;
    }

    private String[] fetchData(int action) {
        EditText etText = (EditText)
                (action == ADD_NOTE ? findViewById(R.id.et_enter_text) : findViewById(R.id.edit_tv));
        String text = etText.getText().toString();
        String date = DateFormat.getDateInstance
                (DateFormat.SHORT).format(Calendar.getInstance().getTime());
        String id = String.valueOf(adapter.getListId());

        String[] data = {text, date, id};
        return data;
    }

    public void saveToDb(String... data) {
        if (data[0].isEmpty()) {
            Toast.makeText(this, "Нет записей", Toast.LENGTH_SHORT).show();
        } else {
            db.addNote(data[0], data[1]);
            Toast.makeText(this, "Запись добавлена", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    public void updToDb(String... data) {
        long _id = Long.parseLong(data[2]);

        if (data[0].isEmpty()) {
            Toast.makeText(this, "Нет записей", Toast.LENGTH_SHORT).show();
        } else {
            db.updNote(data[0], data[1], _id);
            Log.d("id", String.valueOf(adapter.getListId()));
            Toast.makeText(this, "Запись обновлена", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (AddNoteFragment.newInstance() != null) {
            super.onBackPressed();
        }
    }

}
