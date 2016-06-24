package com.nightfarmer.recyclertree;

import java.util.ArrayList;

/**
 * 树形节点
 * Created by zhangfan on 16-6-24.
 */
public interface TreeNode {

    ArrayList<? extends TreeNode> getChildren();

    String getTitle();

    NodeType getNodeType();
}
