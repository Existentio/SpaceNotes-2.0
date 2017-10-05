package com.existentio.spacenotesmvc.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.existentio.spacenotesmvc.R;
import com.existentio.spacenotesmvc.data.PrefHelper;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private ImageButton btnTheme;
    private ImageButton btnDate;
    private ImageButton btnFont;
    private ImageButton btnView;
    private LinearLayout llTop;
    private TextView themeSpace;
    private TextView themeWasteland;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);

        llTop = (LinearLayout) view.findViewById(R.id.top_ll);

        btnTheme = (ImageButton) view.findViewById(R.id.btn_theme);
        btnDate = (ImageButton) view.findViewById(R.id.btn_display_date);
        btnFont = (ImageButton) view.findViewById(R.id.btn_font);
        btnView = (ImageButton) view.findViewById(R.id.btn_view);

        themeSpace = (TextView) view.findViewById(R.id.theme_space);
        themeWasteland = (TextView) view.findViewById(R.id.theme_wasteland);

        attachListeners(btnTheme, btnView, btnDate, btnFont,
                themeSpace, themeWasteland);
        return view;
    }

    private void attachListeners(View... view) {
        for (View v : view) {
            v.setOnClickListener(this);
        }
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_settings, menu);
    }

    private void animButtons(View... view) {
        for (final View v : view) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.appearance);
                    v.startAnimation(animation);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_theme:
                if (!llTop.isShown()) {
                    llTop.setVisibility(View.VISIBLE);
                    animButtons(llTop);
                } else llTop.setVisibility(View.INVISIBLE);
                break;

            case R.id.theme_space:
                PrefHelper prefHelper = new PrefHelper();
                prefHelper.setPref("pref_theme");
                PrefHelper.putInSharedPrefs(prefHelper.getPref(), getContext(), "theme", "first");
                startActivity(new Intent(getContext(), MainActivity.class));
                break;

            case R.id.theme_wasteland:
                Toast.makeText(getContext(), "working", Toast.LENGTH_SHORT).show();
                break;

            case R.id.theme_minimal:

                break;

            case R.id.btn_display_date:
//                btnDate.setBackgroundResource(R.drawable.hexagon_pressed);
                PrefHelper prefDate = new PrefHelper();
                prefDate.setPref("pref_date");
                PrefHelper.putInSharedPrefs(prefDate.getPref(), getContext(), "theme", "first");
                break;

            case R.id.btn_font:

                break;

            case R.id.btn_view:

                break;


        }

    }

}
