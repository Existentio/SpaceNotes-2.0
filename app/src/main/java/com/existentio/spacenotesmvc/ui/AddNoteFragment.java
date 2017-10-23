package com.existentio.spacenotesmvc.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.existentio.spacenotesmvc.R;
import com.existentio.spacenotesmvc.util.PrefHelper;

public class AddNoteFragment extends Fragment {

    EditText etText;
    LinearLayout container;

    public AddNoteFragment() {
        // Required empty public constructor
    }

    public static AddNoteFragment newInstance() {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        etText = (EditText) getActivity().findViewById(R.id.et_enter_text);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_add_note, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences spTheme = getActivity().getSharedPreferences(PrefHelper.PREF_THEME,
                Context.MODE_PRIVATE);
        container = (LinearLayout) getActivity().findViewById(R.id.ll_text_add);

        if (spTheme.contains(PrefHelper.KEY_THEME_WASTELAND)) {
            container.setBackgroundResource(R.drawable.wasteland_design_edit_text);
        } else if (spTheme.contains(PrefHelper.KEY_THEME_MINIMAL)) {
            container.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.black));
        }
    }


}
