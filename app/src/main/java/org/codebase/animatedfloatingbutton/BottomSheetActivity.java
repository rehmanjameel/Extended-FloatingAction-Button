package org.codebase.animatedfloatingbutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomSheetActivity extends AppCompatActivity {

    BottomSheetDialog sheetDialog;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        sheetDialog = new BottomSheetDialog(this, R.style.BottomSheetStyle);
        linearLayout = findViewById(R.id.bottom_sheet_id);
        View view = LayoutInflater.from(this).inflate(R.layout.bottomsheet_layout, linearLayout);

        sheetDialog.setContentView(view);
        sheetDialog.show();
    }
}