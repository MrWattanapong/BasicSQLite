package com.egco428.basicsqlite;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private CommentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSource = new CommentsDataSource(this);
        dataSource.open();
        List<Comment> values = dataSource.getAllComments();
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    public void onClick(View view){
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>)getListAdapter();
        Comment comment = null;
        switch (view.getId()){
            case R.id.addBtn:
                //String[] comments = new String[]{"Good","Cool","#whatever","Very Nice"};
                //int nextINT = new Random().nextInt(4);
                //comment = dataSource.createComment(comments[nextINT]);
                EditText firstName = (EditText) findViewById(R.id.firstName);
                EditText lastName = (EditText) findViewById(R.id.lastName);
                String name = firstName.getText().toString()+"  "+lastName.getText().toString();
                comment = dataSource.createComment(name);
                adapter.add(comment);
                break;
            case R.id.deleteBtn:
                if (getListAdapter().getCount()>0){
                    comment = (Comment)getListAdapter().getItem(0);
                    dataSource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }
}

