package bcit.lab.firebaselab;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText taskField;
    EditText whoField;
    EditText dateField;
    Button addToDb;

    ListView lvTodo;
    List<Todo> todoList;



    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        myRef = FirebaseDatabase.getInstance().getReference("todos");

        taskField = findViewById(R.id.taskET);
        whoField = findViewById(R.id.whoET);
        addToDb = findViewById(R.id.addToDbButt);
        dateField = findViewById(R.id.dateET);

        lvTodo = findViewById(R.id.lvStudents);
        todoList = new ArrayList<Todo>();


        addToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                todoList.clear();
                for (DataSnapshot todoSnapshot : dataSnapshot.getChildren()) {
                    Todo todo = todoSnapshot.getValue(Todo.class);
                    todoList.add(todo);
                }

                TodoAdapter adapter = new TodoAdapter(MainActivity.this, todoList);
                lvTodo.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


    private void addTodo() {
        String task = taskField.getText().toString().trim();
        String who = whoField.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateStr = dateField.getText().toString();
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (TextUtils.isEmpty(task)) {
            Toast.makeText(this, "You must enter a task .", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(who)) {
            Toast.makeText(this, "You must enter a person type.", Toast.LENGTH_LONG).show();
            return;
        }

        String id = myRef.push().getKey();

        Todo todo = new Todo(task, who, date);

        Task setValueTask = myRef.child(id).setValue(todo);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Todo added.",Toast.LENGTH_LONG).show();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

}
