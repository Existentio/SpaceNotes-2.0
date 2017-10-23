package com.existentio.spacenotesmvc.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.existentio.spacenotesmvc.R;
import com.existentio.spacenotesmvc.data.DBHelper;
import com.existentio.spacenotesmvc.util.PrefHelper;
import com.existentio.spacenotesmvc.model.Notes;
import com.existentio.spacenotesmvc.util.FragmentConditions;
import com.existentio.spacenotesmvc.util.RecyclerItemSwiper;

import java.util.List;

import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;
import static com.existentio.spacenotesmvc.ui.MainActivity.notes;


public class BaseFragment extends Fragment implements NoteItemAdapter.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGridLayoutManager;

    private NoteItemAdapter adapter;
    private DBHelper db;
    private RecyclerView mContainer;

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mContainer = (RecyclerView) view.findViewById(R.id.rv_base);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mGridLayoutManager = new GridLayoutManager(getContext(), 4);
        adapter = new NoteItemAdapter(getActivity());
        adapter.setListener(this);
        mContainer.setLayoutManager(mLayoutManager);
        mContainer.setAdapter(adapter);

        initApps(notes);

        new RecyclerItemSwiper(0, LEFT | RIGHT, getContext(),
                adapter, notes, db, mContainer);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences spView = getActivity().getSharedPreferences(PrefHelper.PREF_VIEW,
                Context.MODE_PRIVATE);

        if (spView.contains("view_grid")) {
            mContainer.setLayoutManager(mGridLayoutManager);
        }

        adapter.notifyDataSetChanged();

    }

    private void initApps(List<Notes> list) {
        adapter.setNotes(list);
        db.getNotes(list);

        mContainer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(Notes noteItem) {
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentConditions.CONDITION_EDIT.replace(null, editNoteFragment, fm);
        Bundle bundle = new Bundle();
        bundle.putString("appearance", noteItem.getDescription());
        editNoteFragment.setArguments(bundle);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
