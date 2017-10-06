package com.existentio.spacenotesmvc.util;

import android.support.v4.app.Fragment;


import com.existentio.spacenotesmvc.R;
import com.existentio.spacenotesmvc.ui.AddNoteFragment;
import com.existentio.spacenotesmvc.ui.BaseFragment;
import com.existentio.spacenotesmvc.ui.SettingsFragment;

import static android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;

/**
 * Created by Георгий on 06.10.2017.
 */

public enum SomeCondition {
    CONDITION_ADD {
        public void someMethod(Fragment base, Fragment fragment) {
            base.getFragmentManager().beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .setTransition(TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack("frags")
                    .commit();
        }
    },

    CONDITION_TWO {
        public void someMethod(Fragment base, Fragment fragment) {
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
        public void someMethod(Fragment base, Fragment fragment) {
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
        public void someMethod(Fragment base, Fragment fragment) {
            fragment.getFragmentManager().beginTransaction()
//                        .detach(fragment)
                        .replace(R.id.main_container, base)
                        .setTransition(TRANSIT_FRAGMENT_CLOSE)
                        .commit();
        }
    };



//    CONDITION_FIVE {
//        @Override
//        public void someMethod(Fragment base, Fragment fragment) {
//            base.getFragmentManager().beginTransaction()
//                    .detach(fragment)
//                    .attach()
//                    .setTransition(TRANSIT_FRAGMENT_CLOSE)
//                    .commit();
//        }
//    };

    public abstract void someMethod(Fragment base, Fragment fragment);


}


