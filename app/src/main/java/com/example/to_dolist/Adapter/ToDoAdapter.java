package com.example.to_dolist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.AddNewTask;
import com.example.to_dolist.MainActivity;
import com.example.to_dolist.Model.ToDoModel;
import com.example.to_dolist.R;
import com.example.to_dolist.TaskDetailsActivity;
import com.example.to_dolist.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder>{
    private List<ToDoModel> mList;
    private MainActivity activity;
    private DataBaseHelper myDB;

    public ToDoAdapter(DataBaseHelper myDB,MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;

    }

    private Context context;

    public ToDoAdapter(List<ToDoModel> taskList, Context context) {
        this.mList = taskList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         final ToDoModel item = mList.get(position);
         holder.checkBox.setText(item.getTitle());
         holder.checkBox.setChecked(toBoolean(item.getStatus()));
         holder.textView.setText(item.getTargetDate());
         holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b){
               if(compoundButton.isChecked()){
                   myDB.updateStatus(item.getId(), 1);
               }else{
                   myDB.updateStatus(item.getId(),0);
               }
           }
        });

        // Set up a click listener on the entire item view to open TaskDetailActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TaskDetailsActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("category", item.getCategory());
                intent.putExtra("location", item.getLocation());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("status", item.getStatus() == 0 ? "Pending" : "Completed");
                intent.putExtra("targetDate", item.getTargetDate());
                activity.startActivity(intent);
            }
        });
    }


    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setTasks(List<ToDoModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        ToDoModel item = mList.get(position);
        myDB.deleteTask(item.getId());

        mList.remove(position);
        notifyItemRemoved(position);

    }

    public void editItems(int position){
        ToDoModel item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("Id", item.getId());
        bundle.putString("title", item.getTitle());
        bundle.putString("category", item.getCategory());
        bundle.putString("desc",item.getDescription());
        bundle.putString("location",item.getLocation());
        bundle.putInt("status",item.getStatus());
        bundle.putString("target",item.getTargetDate());
        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(),task.getTag());
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    CheckBox checkBox;
    TextView textView;
    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkbox);
        textView = itemView.findViewById(R.id.textView1);
    }
}
}
