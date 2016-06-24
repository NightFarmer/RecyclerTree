package com.nightfarmer.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nightfarmer.recyclertree.NodeType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ArrayList<Node> list = new ArrayList<>();

        ArrayList<Node> nodes1 = new ArrayList<>();
        Node node1 = new Node(nodes1, "1");
        list.add(node1);

        ArrayList<Node> nodes2 = new ArrayList<>();
        Node node2 = new Node(nodes2, "2");
        nodes1.add(node2);


        ArrayList<Node> nodes3 = new ArrayList<>();
        Node node3 = new Node(nodes3, "3");
        nodes2.add(node3);

        ArrayList<Node> nodes4 = new ArrayList<>();
        Node node4 = new Node(nodes4, "4");
        node4.type = NodeType.LEAF;
        nodes3.add(node4);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new MyAdapter(list, this));
    }
}
