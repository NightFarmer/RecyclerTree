package com.nightfarmer.sample;

import android.content.Context;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nightfarmer.recyclertree.NodeType;
import com.nightfarmer.recyclertree.RecyclerTreeAdapter;

import java.util.List;

/**
 * Created by zhangfan on 16-6-24.
 */
public class MyAdapter extends RecyclerTreeAdapter<Node, MyAdapter.MyHolder> {

    private final Context context;

    /**
     * @param dataList 已传入的数据参数在控件展开和折叠后会产生变化，不可在其他地方引用或使用
     */
    public MyAdapter(List<Node> dataList, Context context) {
        super(dataList);
        this.context = context;
    }

    @Override
    protected int indentPerLevel() {
        return 50;
    }

    @Override
    protected MyHolder onCreateViewHolder(ViewGroup parent, NodeType nodeType) {
        View inflate = null;
        switch (nodeType) {
            case LEAF:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.treeitem2, parent, false);
                break;
            case BRANCH:
                inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.treeitem, parent, false);
                break;
        }
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, Node treeNode) {

        holder.tv.setText("" + treeNode.getTitle());

    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.title);
        }
    }

    @Override
    protected void onLeafTouch(Node treeNode) {
        super.onLeafTouch(treeNode);
        Toast.makeText(context, "点到叶子了:" + treeNode.getTitle(), Toast.LENGTH_SHORT).show();
    }
}
