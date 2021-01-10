package com.fast.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fast.todoapp.Adapters.TaskAdapter;
import com.fast.todoapp.Model.Task;
import com.fast.todoapp.ViewModel.TaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {

    private TaskViewModel viewModel;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private FloatingActionButton fab;
    private TaskAdapter.setChecked setChecked;
    private LinearLayout enterLay;
    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        fab = findViewById(R.id.fab);
        enterLay = findViewById(R.id.enterLay);
        editText = findViewById(R.id.editTask);
        imageView = findViewById(R.id.imgAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this
        .getApplication())).get(TaskViewModel.class);

        viewModel.getAllTasks().observe(this, tasks -> adapter.setTasks(tasks));

        setChecked = new TaskAdapter.setChecked() {
            @Override
            public void check(Task task) {
                viewModel.update(task);
            }

            @Override
            public void remove(final Task task) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Are You Sure You Want To Delete This Task?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            if (viewModel != null){
                                viewModel.delete(task);
                            }
                        })
                        .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                        .show();
            }
        };

        adapter.setInterface(setChecked);

        fab.setOnClickListener(view -> {
            if (enterLay.getVisibility() == View.VISIBLE){
                enterLay.setVisibility(View.GONE);
            }
            else {
                enterLay.setVisibility(View.VISIBLE);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }

        });

        fab.setOnLongClickListener(view -> {
            if (viewModel != null){
                viewModel.deleteAllNotes();
            }
            return false;
        });


        editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if(i== EditorInfo.IME_ACTION_DONE){
                if (viewModel != null){
                    setTask();
                }
            }
            return false;
        });


        imageView.setOnClickListener(view -> setTask());

    }

    void setTask(){
        if (!editText.getText().toString().isEmpty()){
            if (viewModel != null){
                viewModel.insert(new Task(editText.getText().toString(), false));
                editText.setText("");
                enterLay.setVisibility(View.GONE);
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Enter Text", Toast.LENGTH_SHORT).show();
        }
    }
}
