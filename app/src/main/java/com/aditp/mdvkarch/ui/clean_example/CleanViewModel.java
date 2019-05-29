package com.aditp.mdvkarch.ui.clean_example;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

/**
 * @implSpec BusinessLogic Here ...
 */
public class CleanViewModel extends ViewModel {

    // ------------------------------------------------------------------------
    // logicName
    // ------------------------------------------------------------------------
    public void logicName(Context context){
        Toast.makeText(context, "Hi Am Adit ~", Toast.LENGTH_SHORT).show();
    }

}
