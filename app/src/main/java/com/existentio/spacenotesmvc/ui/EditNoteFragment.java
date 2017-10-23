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
import android.widget.FrameLayout;

import com.existentio.spacenotesmvc.R;
import com.existentio.spacenotesmvc.util.PrefHelper;


public class EditNoteFragment extends Fragment {

    EditText text;
    String args;
    FrameLayout container;

    public EditNoteFragment() {
    }

    public static EditNoteFragment newInstance() {
        EditNoteFragment fragment = new EditNoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            args = bundle.getString("appearance");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
//        container = (FrameLayout)view.findViewById(R.id.frame_edit);
        text = (EditText) view.findViewById(R.id.edit_tv);
        text.setText(args);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_edit_note, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences spTheme = getActivity().getSharedPreferences(PrefHelper.PREF_THEME,
                Context.MODE_PRIVATE);
        container = (FrameLayout)getActivity().findViewById(R.id.frame_edit);

        if (spTheme.contains(PrefHelper.KEY_THEME_WASTELAND)) {
            container.setBackgroundResource(R.drawable.wasteland_design_edit_text);
            text.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        } else if (spTheme.contains(PrefHelper.KEY_THEME_MINIMAL)) {
            container.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.black));
            text.setTextColor(ContextCompat.getColor(getContext(), R.color.light_green));
        }
    }


}
