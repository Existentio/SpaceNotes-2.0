package com.existentio.spacenotes2.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.existentio.spacenotes2.R;
import com.existentio.spacenotes2.ui.SettingsFragment;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;

/**
 * Created by Георгий on 06.10.2017.
 */

public enum FragmentConditions {
    CONDITION_ADD {
        public void replace(Fragment base, Fragment fragment, FragmentManager fm) {
            base.getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .setTransition(TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack("frags")
                    .commit();
        }
    },

    CONDITION_SETTINGS {
        public void replace(Fragment base, Fragment fragment, FragmentManager fm) {
            fragment = SettingsFragment.newInstance();
            if (!fragment.isAdded()) {
                base.getFragmentManager().beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .setTransition(TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        }
    },

    CONDITION_SAVE {
        @Override
        public void replace(Fragment base, Fragment fragment, FragmentManager fm) {
            if (base.isAdded()) {
                base.getFragmentManager().beginTransaction()
                        .detach(base)
                        .attach(base)
                        .replace(R.id.main_container, base)
                        .commit();
            }
        }
    },

    CONDITION_FOUR {
        @Override
        public void replace(Fragment base, Fragment fragment, FragmentManager fm) {
            fm.beginTransaction()
                    .replace(R.id.main_container, base)
                    .setTransition(TRANSIT_FRAGMENT_CLOSE)
                    .commit();
        }
    },

    CONDITION_EDIT {
        @Override
        public void replace(Fragment base, Fragment fragment, FragmentManager fm) {
            fm.beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .setTransition(TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack("frags")
                    .commit();
        }
    };


    public abstract void replace(Fragment base, Fragment fragment, FragmentManager fm);


}


