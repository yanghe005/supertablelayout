package com.personal.table;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hepeng.view.SuperTableLayout;
import com.hepeng.view.TableBuild;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private SuperTableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tableLayout = findViewById(R.id.table_layout);

        List<List<String>> list = new ArrayList<>();
        List<String> rowlist1 = new ArrayList<>();
        rowlist1.add("第一行1");
        rowlist1.add("第一行2");
        rowlist1.add("第一行3");
        list.add(rowlist1);

        List<String> rowlist2 = new ArrayList<>();
        rowlist2.add("第二行1");
        rowlist2.add("第二行2");
        rowlist2.add("第二行3");
        list.add(rowlist2);

        List<String> rowlist3 = new ArrayList<>();
        rowlist3.add("第三行1");
        rowlist3.add("第三行2");
        rowlist3.add("第三行3");
        list.add(rowlist3);

        List<String> rowlist4 = new ArrayList<>();
        rowlist4.add("第四行1");
        rowlist4.add("第四行2");
        rowlist4.add("第四行3");
        list.add(rowlist4);

        List<String> rowlist5 = new ArrayList<>();
        rowlist5.add("第五行1");
        rowlist5.add("第五行2");
        rowlist5.add("第五行3");
        list.add(rowlist5);

        List<String> rowlist6 = new ArrayList<>();
        rowlist6.add("第六行1");
        rowlist6.add("第六行2");
        rowlist6.add("第六行3");
        list.add(rowlist6);


        tableLayout.addData(list);


//        Button button = findViewById(R.id.table_test);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                new TableBuild().setHeight(100).excute(tableLayout);
//            }
//        });
    }
}