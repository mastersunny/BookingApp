package mastersunny.rooms.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import mastersunny.rooms.listeners.DateSelectionListener;
import mastersunny.rooms.utils.Constants;

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String TAG = "SelectDateFragment";
    private DateSelectionListener dateSelectionListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        populateSetDate(yy, mm + 1, dd);
    }

    public void populateSetDate(int year, int month, int day) {
        try {
            String dateInString = String.format("%02d", day) + "-" + String.format("%02d", month) + "-" + year;
            Date date = Constants.sdf.parse(dateInString);
            Constants.debugLog(TAG, date.toString());
            String[] strings = date.toString().split(" ");
            if (dateSelectionListener != null) {
                dateSelectionListener.startDate(strings[0] + ", " + strings[1] + " " + strings[2]);
            }

        } catch (Exception e) {
            Constants.debugLog(TAG, e.getMessage());
        }
    }

    public void setListener(DateSelectionListener dateSelectionListener) {
        this.dateSelectionListener = dateSelectionListener;
    }
}