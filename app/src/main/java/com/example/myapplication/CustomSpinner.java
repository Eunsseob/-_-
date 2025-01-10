package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

public class CustomSpinner extends androidx.appcompat.widget.AppCompatSpinner {

    private OnItemSelected onItemSelected;
    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, int mode) {
        super(context, mode);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }



    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (onItemSelected != null){
            onItemSelected.onitemSelected(this, getSelectedView(), position, getAdapter().getItemId(position));
        }
    }

    public void setOnSelected(OnItemSelected onItemSelected){
        this.onItemSelected = onItemSelected;
    }


    public interface OnItemSelected {
        public void onitemSelected(AdapterView p0, View p1, int p2, long p3);

    }
}
