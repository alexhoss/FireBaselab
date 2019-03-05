package bcit.lab.firebaselab;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvTask = listViewItem.findViewById(R.id.textViewTask);
        TextView tvWho = listViewItem.findViewById(R.id.textViewWho);
        TextView tvDate = listViewItem.findViewById(R.id.textViewDate);

        Todo todo = todoList.get(position);
        tvTask.setText(todo.getTask());
        tvWho.setText(todo.getWho());
        tvDate.setText(todo.getDate().toString());

        return listViewItem;
    }

}

