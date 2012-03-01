package com.meamka.pine;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by IntelliJ IDEA.
 * User: Amka
 * Date: 17.02.12
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public class BFragmentTab extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_b, container, false);
    }
}
