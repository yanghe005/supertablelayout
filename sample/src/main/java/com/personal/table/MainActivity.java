package com.personal.table;

import android.app.Activity;
import android.os.Bundle;

import com.hepeng.view.SuperTableLayout;

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
        rowlist1.add("4432");
        rowlist1.add("ytrr");
        rowlist1.add("ecc");
        list.add(rowlist1);

        List<String> rowlist2 = new ArrayList<>();
        rowlist2.add("323");
        rowlist2.add("11");
        rowlist2.add("nvb");
        list.add(rowlist2);

        List<String> rowlist3 = new ArrayList<>();
        rowlist3.add("ret5");
        rowlist3.add("fdsd");
        rowlist3.add("gjg");
        list.add(rowlist3);

        List<String> rowlist4 = new ArrayList<>();
        rowlist4.add("222");
        rowlist4.add("5678");
        rowlist4.add("gjg");
        list.add(rowlist4);

        List<String> rowlist5 = new ArrayList<>();
        rowlist5.add("y676");
        rowlist5.add("fdsd");
        rowlist5.add("223");
        list.add(rowlist5);

        List<String> rowlist6 = new ArrayList<>();
        rowlist6.add("ret5");
        rowlist6.add("tree");
        rowlist6.add("bnvbv");
        list.add(rowlist6);


        tableLayout.addData(list);
    }
}