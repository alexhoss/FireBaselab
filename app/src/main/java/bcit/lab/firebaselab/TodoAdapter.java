package bcit.lab.firebaselab;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<Todo> {
    private Activity context;
    private List<Todo> todoList;

    public TodoAdapter(Activity context, List<Todo> todoList) {
        super(context, R.layout.list_layout, todoList);
        this.context = context;
        this.todoList = todoList;
    }

    public TodoAdapter(Context context, int resource, List<Todo> objects, Activity context1, List<Todo> todoList) {
        super(context, resource, objects);
        this.context = context1;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        final View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        final CheckedTextView tvTask = listViewItem.findViewById(R.id.textViewTask);
        TextView tvWho = listViewItem.findViewById(R.id.textViewWho);
        TextView tvDate = listViewItem.findViewById(R.id.textViewDate);


        final Todo todo = todoList.get(position);
        tvTask.setText(todo.getTask());
        tvTask.setChecked(todo.isDone());

        tvWho.setText(todo.getWho());
        tvDate.setText(todo.getDate().toString());



        tvTask.setOnClickListener(new View.OnClickListener() {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("todos");;
            DatabaseReference dbRef = myRef.child(todo.getID());


            @Override
            public void onClick(View v) {
                if (tvTask.isChecked()) {

                    todo.setDone(false);
                    dbRef.setValue(todo);;
                    tvTask.setCheckMarkDrawable(R.drawable.white);
                    tvTask.setChecked(false);

                } else {
                    todo.setDone(true);
                    dbRef.setValue(todo);
                    tvTask.setCheckMarkDrawable(R.drawable.checked);
                    tvTask.setChecked(true);
                }
            }
        });

        return listViewItem;
    }

}

