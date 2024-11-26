package c14220080.example.taskapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private Context context;
    private ArrayList task_id, task_name, task_description, task_date;
    private ArrayList<Boolean> isTaskStarted = new ArrayList<>();

    private int position;

    public TaskAdapter(Context context,
                       ArrayList task_id,
                       ArrayList task_name,
                       ArrayList task_description,
                       ArrayList task_date) {
        this.context = context;
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_description = task_description;
        this.task_date = task_date;
    }

    @NonNull
    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_row, parent, false);
        return new TaskAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        this.position = position;
        holder.task_name_txt.setText(String.valueOf(task_name.get(position)));
        holder.task_description_txt.setText(String.valueOf(task_description.get(position)));
        holder.task_date_txt.setText(String.valueOf(task_date.get(position)));


        // State management for the "Start/End" button
//        holder.start_end_button.setOnClickListener(new View.OnClickListener() {
//            private boolean isStarted = false; // Initial state for "Start/End"
//
//            @Override
//            public void onClick(View v) {
//                if (!isStarted) {
//                    // If "Start", change button text to "End" and disable "Update" button
//                    holder.start_end_button.setText("End");
//                    holder.update_button.setEnabled(false);
//                    holder.update_button.setAlpha(0.5f); // Make the button visually look disabled
//                    isStarted = true;
//                } else {
//                    // If "End", hide "Start/End" and "Update" buttons
//                    holder.start_end_button.setVisibility(View.GONE);
//                    holder.update_button.setVisibility(View.GONE);
//
//                    // Show "Task ended" text
//                    holder.task_ended_txt.setVisibility(View.VISIBLE);
//
//                    // Move "Delete" button to the far right
//                    ConstraintSet constraintSet = new ConstraintSet();
//                    constraintSet.clone(holder.task_column);
//                    constraintSet.clear(holder.delete_button.getId(), ConstraintSet.START);
//                    constraintSet.connect(holder.delete_button.getId(), ConstraintSet.END,
//                            ConstraintSet.PARENT_ID, ConstraintSet.END, 16);
//                    constraintSet.applyTo(holder.task_column);
//                }
//            }
//        });




        holder.update_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, UpdateActivity.class);
                        intent.putExtra("id", String.valueOf(task_id.get(position)));
                        intent.putExtra("name", String.valueOf(task_name.get(position)));
                        intent.putExtra("date", String.valueOf(task_date.get(position)));
                        intent.putExtra("description", String.valueOf(task_description.get(position)));
                        context.startActivity(intent);
                    }
                }
        );

        holder.delete_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Buat AlertDialog untuk konfirmasi penghapusan
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Delete task ?");
                        builder.setMessage("Are you sure to delete this task?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Hapus data dari database
                                MyDatabase myDB = new MyDatabase(context);
                                myDB.deleteOneRow(String.valueOf(task_id.get(position)));

                                // Refresh aktivitas
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss(); // Tutup dialog jika pengguna membatalkan
                            }
                        });

                        // Tampilkan AlertDialog
                        builder.create().show();
                    }
                }
        );


    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mainLayout;
        ConstraintLayout task_column;
        Button update_button,delete_button, start_end_button;

        TextView task_name_txt, task_description_txt, task_date_txt, task_ended_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_name_txt = itemView.findViewById(R.id.task_name_txt);
            task_description_txt = itemView.findViewById(R.id.task_description_txt);
            task_date_txt = itemView.findViewById(R.id.task_date_txt);
            task_ended_txt = itemView.findViewById(R.id.task_ended_txt);
//            mainLayout = itemView.findViewById(R.id.mainLayout);
            update_button = itemView.findViewById(R.id.update_button);
            delete_button = itemView.findViewById(R.id.delete_button);
            start_end_button = itemView.findViewById(R.id.start_end_button);
            task_column = itemView.findViewById(R.id.task_column);
        }
    }


}
