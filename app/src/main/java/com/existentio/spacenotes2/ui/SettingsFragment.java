package com.existentio.spacenotes2.ui;


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

import com.existentio.spacenotes2.R;
import com.existentio.spacenotes2.util.PrefHelper;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private ImageButton btnTheme;
    private ImageButton btnDate;
    private ImageButton btnFont;
    private ImageButton btnView;

    private LinearLayout llTop;
    private LinearLayout llView;

    private TextView themeSpace, themeWasteland, themeMinimal;
    private TextView viewLines, viewGrid;

    private PrefHelper prefHelper;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);

        llTop = (LinearLayout) view.findViewById(R.id.top_ll);
        llView = (LinearLayout)view.findViewById(R.id.ll_bottom);

        btnTheme = (ImageButton) view.findViewById(R.id.btn_theme);
        btnDate = (ImageButton) view.findViewById(R.id.btn_display_date);
        btnFont = (ImageButton) view.findViewById(R.id.btn_font);
        btnView = (ImageButton) view.findViewById(R.id.btn_view);

        themeSpace = (TextView) view.findViewById(R.id.theme_space);
        themeWasteland = (TextView) view.findViewById(R.id.theme_wasteland);
        themeMinimal = (TextView) view.findViewById(R.id.theme_minimal);

        viewLines = (TextView)view.findViewById(R.id.view_lines);
        viewGrid = (TextView)view.findViewById(R.id.view_grid);


        attachListeners(btnTheme, btnView, btnDate, btnFont,
                themeSpace, themeWasteland, themeMinimal, viewLines, viewGrid);

        prefHelper = new PrefHelper();
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
                prefHelper.setPref("pref_theme");
                PrefHelper.putInSharedPrefs(prefHelper.getPref(), getContext(), "theme", "");
                startActivity(new Intent(getContext(), MainActivity.class));
                break;

            case R.id.theme_wasteland:
                prefHelper.setPref("pref_theme");
                PrefHelper.putInSharedPrefs(prefHelper.getPref(), getContext(), "theme_wasteland", "");
                startActivity(new Intent(getContext(), MainActivity.class));
                break;

            case R.id.theme_minimal:
                prefHelper.setPref("pref_theme");
                PrefHelper.putInSharedPrefs(prefHelper.getPref(), getContext(), "theme_minimal", "");
                startActivity(new Intent(getContext(), MainActivity.class));
                break;

            case R.id.btn_display_date:
//                btnDate.setBackgroundResource(R.drawable.hexagon_pressed);
                PrefHelper prefDate = new PrefHelper();
                prefDate.setPref("pref_date");
                PrefHelper.putInSharedPrefs(prefDate.getPref(), getContext(), "date", "");
                break;

            case R.id.btn_font:

                break;

            case R.id.btn_view:
                if (!llView.isShown()) {
                    llView.setVisibility(View.VISIBLE);
                    animButtons(llView);
                } else llView.setVisibility(View.INVISIBLE);
                break;

            case R.id.view_lines:
                prefHelper.setPref("pref_view");
                PrefHelper.putInSharedPrefs(prefHelper.getPref(), getContext(), "view_lines", "");
                startActivity(new Intent(getContext(), MainActivity.class));
                break;

            case R.id.view_grid:
                prefHelper.setPref("pref_view");
                PrefHelper.putInSharedPrefs(prefHelper.getPref(), getContext(), "view_grid", "");
                startActivity(new Intent(getContext(), MainActivity.class));
                break;


        }

    }

}
