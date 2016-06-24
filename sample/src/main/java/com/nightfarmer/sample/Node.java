package com.nightfarmer.sample;

import com.nightfarmer.recyclertree.NodeType;
import com.nightfarmer.recyclertree.TreeNode;

import java.util.ArrayList;

/**
 * Created by zhangfan on 16-6-24.
 */
public class Node implements TreeNode {


    ArrayList<Node> children;
    String name;

    NodeType type = NodeType.BRANCH;

    public Node(ArrayList<Node> children, String name) {
        this.children = children;
        this.name = name;
    }


    @Override
    public ArrayList<? extends TreeNode> getChildren() {
        return children;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public NodeType getNodeType() {
        return type;
    }
}
