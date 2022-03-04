package com.example.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditToDo extends AppCompatActivity {

    private EditText title,des;
    Button edit;
    DbHandler dbHandler;
    Context context;
    Long updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);

        context=this;
        dbHandler=new DbHandler(this);

        title = findViewById(R.id.editToDoTextTitle);
        des = findViewById(R.id.editToDoTextDescription);
        edit = findViewById(R.id.buttonEdit);

        final String id=getIntent().getStringExtra("id");
        ToDo toDo=dbHandler.getSingleToDo(Integer.parseInt(id));

        title.setText(toDo.getTitle());
        des.setText(toDo.getDescription());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Titletxt=title.getText().toString();
                String desctxt=des.getText().toString();
                updateDate=System.currentTimeMillis();

                ToDo toDo=new ToDo(Integer.parseInt(id),Titletxt,desctxt,updateDate,0);
                int state=dbHandler.updateSingleToDo(toDo);
                System.out.println(state);
                startActivity(new Intent(context,NoteMain_activity.class));
            }
        });


    }
}