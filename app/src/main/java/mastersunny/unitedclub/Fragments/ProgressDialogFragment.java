package mastersunny.unitedclub.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

@SuppressLint("ValidFragment")
public class ProgressDialogFragment extends DialogFragment {

    public String message;

    public ProgressDialogFragment(String message) {
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        // Disable the back button
        OnKeyListener keyListener = new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }

        };
        dialog.setOnKeyListener(keyListener);
        return dialog;
    }
}