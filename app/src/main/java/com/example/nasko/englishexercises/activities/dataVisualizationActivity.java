package com.example.nasko.englishexercises.activities;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nasko.englishexercises.databases.FeedReaderContract;
import com.example.nasko.englishexercises.databases.FeedReaderDbHelper;
import com.example.nasko.englishexercises.entries.EntryList;
import com.example.nasko.englishexercises.entries.MyEntity;
import com.example.nasko.englishexercises.R;

import java.util.ArrayList;

/**
 * Created by nasko on 27.3.2017 г..
 */

public class dataVisualizationActivity extends Activity {
    int selectedID;
    EditText IDEditText;
    Button selectBtn;
    Button deletFromDbBtn;
    TextView allDbToText;
    TextView showEntityTextView;
    LinearLayout layout;
    MyEntity  entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_visualization);

        allDbToText = (TextView) findViewById(R.id.textViewAllText);
        IDEditText = (EditText) findViewById(R.id.IDEditText);
        selectBtn = (Button) findViewById(R.id.selectBtn);
        showEntityTextView = (TextView) findViewById(R.id.showEntityTextView);
        deletFromDbBtn = (Button) findViewById(R.id.deletFromDbBtn);
        layout = (LinearLayout) findViewById(R.id.SelectLinearLayout);
        layout.setVisibility(View.GONE);
        String s = "";
        final ArrayList<MyEntity> list = EntryList.getAllDB(getApplication());


        for (MyEntity entity : list) {
            s+= entity.toString();
            s+= "\n";
        }

        allDbToText.setText(s);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int ir = 10;
                String input = IDEditText.getText().toString();
                if (!input.matches("")){
                    selectedID = Integer.valueOf(input);
                    for (MyEntity myEntity : list) {
                        if (myEntity.getId() == selectedID){
                            entity = myEntity;
                            String str = entity.toString();
                            showEntityTextView.setText(str);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }

                    if (entity == null){
                        Context context = getApplicationContext();
                        CharSequence text = "Nonexistent ID";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        finish();
                        startActivity(getIntent());
                    }

                }else {
                    Context context = getApplicationContext();
                    CharSequence text = "Please input ID";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            }
        });

        deletFromDbBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getApplication());
                SQLiteDatabase db1 = mDbHelper.getReadableDatabase();

                //String delete = String.format("DELETE FROM %s WHERE _ID = %d", FeedReaderContract.FeedEntry.TABLE_NAME, selectedID + 1);
                //Cursor cursor = db1.rawQuery(delete, null);
                //cursor.close();
                db1.delete(FeedReaderContract.FeedEntry.TABLE_NAME, FeedReaderContract.FeedEntry._ID + "=" + entity.getId(), null);


                finish();
                startActivity(getIntent());
            }
        });

    }


}
