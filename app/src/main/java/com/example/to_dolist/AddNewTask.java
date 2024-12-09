package com.example.to_dolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.graphics.Color;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_dolist.Model.ToDoModel;
import com.example.to_dolist.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";
    private EditText mEditText;
    private EditText mEditText1;
    private EditText mEditText2;
    private EditText mEditText3;
    private EditText mEditText4;
    private EditText mEditText5;
    private ImageView calendarIcon;

    private Button mSaveButton;

    public DataBaseHelper myDB;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.add_new_task , container, false);
       return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mEditText = view.findViewById(R.id.editText);
        mEditText1 = view.findViewById(R.id.editText1);
        mEditText2= view.findViewById(R.id.editText2);
        mEditText3 = view.findViewById(R.id.editText3);
        mEditText4 = view.findViewById(R.id.editText4);
        mEditText5 = view.findViewById(R.id.editText5);
        calendarIcon = view.findViewById(R.id.calendarIcon);



        mSaveButton = view.findViewById(R.id.addButton);

        myDB = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String title = bundle.getString("title");
            String category = bundle.getString("category");
            String description = bundle.getString("desc");
            String location = bundle.getString("location");
            String status = bundle.getString("status");
            String targetDate = bundle.getString("target");
            mEditText.setText(title);
            mEditText1.setText(category);
            mEditText2.setText(description);
            mEditText3.setText(location);
            mEditText4.setText(status);
            mEditText5.setText(targetDate);
            if(title.length() > 0){
                mSaveButton.setEnabled(true);
            }
        }


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    mSaveButton.setEnabled(false);
                    mSaveButton.setBackgroundColor(Color.GRAY);
                }else{
                    mSaveButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            boolean finalIsUpdate = false;
            @Override
            public void onClick(View view) {
                String text = mEditText.getText().toString();
                String text2 = mEditText1.getText().toString();
                String text3 = mEditText2.getText().toString();
                String text4 = mEditText3.getText().toString();
                String text5 = mEditText5.getText().toString();
                if(finalIsUpdate){
                    myDB.updateTitle(bundle.getInt("ID"),text);
                    myDB.updateCategory(bundle.getInt("category"),text2);
                    myDB.updateDescription(bundle.getInt("desc"),text3);
                    myDB.updateLocation(bundle.getInt("location"),text4);
                    myDB.updateTargetDate(bundle.getInt("target"),text5);
                }else{
                    ToDoModel item = new ToDoModel();
                    item.setTitle(text);
                    item.setCategory(text2);
                    item.setDescription(text3);
                    item.setLocation(text4);
                    item.setTargetDate(text5);
                    item.setStatus(0);
                    myDB.insertTask(item);
                }
                dismiss();
            }
        });

        calendarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Initialize DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Format and display selected date in EditText
                                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                mEditText5.setText(date);
                            }
                        }, year, month, day);

                // Show DatePickerDialog
                datePickerDialog.show();
            }
        });

    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog){
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
   }

}
