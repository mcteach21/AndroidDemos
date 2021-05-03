package mc.apps.demos.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import mc.apps.demos.R;

public class TitledInput extends ConstraintLayout {
    private static final String TAG = "tests";
    private EditText edit_component;
    private TextView title_component;

    public TitledInput(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.titled_input_layout, this);
        
        edit_component = (EditText) findViewById(R.id.edit_text_id);
        title_component = (TextView) findViewById(R.id.title_text_id);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitledInput, 0, 0);
        String titleText = a.getString(R.styleable.TitledInput_titleText);
        String hintText = a.getString(R.styleable.TitledInput_hintText);
        int editTextColor = a.getInt(R.styleable.TitledInput_textColor, Color.WHITE);

        a.recycle();

        title_component.setText(titleText);
        edit_component.setHint(hintText);
        edit_component.setTextColor(editTextColor);
    }
}
