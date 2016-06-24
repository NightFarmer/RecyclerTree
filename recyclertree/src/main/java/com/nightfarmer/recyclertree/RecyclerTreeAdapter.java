package com.nightfarmer.recyclertree;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 树形adapter
 * Created by zhangfan on 16-6-24.
 */
public abstract class RecyclerTreeAdapter<N extends TreeNode, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    ArrayList<N> dataList = new ArrayList<>();

    HashMap<N, Boolean> nodeChildrenStatusMap = new HashMap<>();

    HashMap<N, Integer> nodeLevelMap = new HashMap<>();

    HashSet<N> checkedNodes = new HashSet<>();

    /**
     * @return 每级缩进，单位px
     */
    protected abstract int indentPerLevel();

    /**
     * @param dataList 已传入的数据参数在控件展开和折叠后会产生变化，不可在其他地方引用或使用
     */
    public RecyclerTreeAdapter(List<N> dataList) {
        this.dataList.addAll(dataList);
    }

    /**
     * 折叠
     *
     * @param treeNode
     */
    public void collapse(TreeNode treeNode) {

    }

    /**
     * 展开
     *
     * @param treeNode
     */
    public void expand(TreeNode treeNode) {

    }

    @Override
    public final T onCreateViewHolder(ViewGroup parent, int viewType) {
        NodeType nodeType;
        if (viewType == 0) {
            nodeType = NodeType.LEAF;
        } else {
            nodeType = NodeType.BRANCH;
        }
        return onCreateViewHolder(parent, nodeType);
    }

    protected abstract T onCreateViewHolder(ViewGroup parent, NodeType nodeType);

    @Override
    public final void onBindViewHolder(final T holder, int position) {
        N treeNode = dataList.get(position);
        onBindViewHolder(holder, treeNode);
        final View itemView = holder.itemView;
        Integer integerLevel = nodeLevelMap.get(treeNode);
        int level = integerLevel == null ? 0 : integerLevel;
        itemView.setPadding(level * indentPerLevel(), itemView.getPaddingTop(),
                itemView.getPaddingRight(), itemView.getPaddingBottom());
        itemView.setTag(treeNode);
        itemView.setClickable(true);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                N treeNode = (N) itemView.getTag();
                switch (treeNode.getNodeType()) {
                    case BRANCH:

                        int location = dataList.indexOf(treeNode);
                        ArrayList<? extends TreeNode> children = treeNode.getChildren();
                        if (children.size() > 0) {
                            Boolean aBoolean = nodeChildrenStatusMap.get(treeNode);
                            boolean isShowChildren = aBoolean != null && aBoolean;
                            if (isShowChildren) {
                                List<N> allShowedChildren = getAllShowedChildren(treeNode);
                                dataList.removeAll(allShowedChildren);
                                notifyItemRangeRemoved(location + 1, allShowedChildren.size());
                                updateChildrenStatusMap(treeNode, false);
                            } else {
                                Integer integer = nodeLevelMap.get(treeNode);
                                int thisLevel = integer == null ? 0 : integer;
                                for (TreeNode n :
                                        children) {
                                    nodeLevelMap.put((N) n, thisLevel + 1);
                                }
                                dataList.addAll(location + 1, (Collection<? extends N>) children);
                                notifyItemRangeInserted(location + 1, children.size());
                                nodeChildrenStatusMap.put(treeNode, true);
                            }
                        }
                        break;

                    case LEAF:
                        boolean checked = checkedNodes.contains(treeNode);
                        setNodeChecked(treeNode, !checked);
                        onLeafTouch(treeNode);
                        break;
                }
            }
        });
    }

    private void updateChildrenStatusMap(N treeNode, boolean isShowChildren) {
        nodeChildrenStatusMap.put(treeNode, isShowChildren);
        ArrayList<? extends TreeNode> children = treeNode.getChildren();
        if (children == null) return;
        for (TreeNode n :
                children) {
            Boolean aBoolean = nodeChildrenStatusMap.get(n);
            if ((aBoolean != null && aBoolean) == isShowChildren) continue;
            updateChildrenStatusMap((N) n, isShowChildren);
        }
    }

    private List<N> getAllShowedChildren(N treeNode) {
        List<N> result = new ArrayList<>();
        Boolean aBoolean = nodeChildrenStatusMap.get(treeNode);
        boolean showed = aBoolean != null && aBoolean;
        if (showed) {
            ArrayList<? extends TreeNode> children = treeNode.getChildren();
            if (children == null) return result;
            result.addAll((Collection<? extends N>) children);
            for (TreeNode n : children) {
                result.addAll(getAllShowedChildren((N) n));
            }
        }
        return result;
    }


    protected void onLeafTouch(N treeNode) {
    }

    public void setNodeChecked(N treeNode, boolean checked) {
        if (checked) {
            checkedNodes.add(treeNode);
        } else {
            checkedNodes.remove(treeNode);
        }
        ArrayList<? extends TreeNode> children = treeNode.getChildren();
        if (children == null) return;
        for (TreeNode n : children) {
            setNodeChecked((N) n, checked);
        }
    }


    /**
     * 数据和UI绑定
     *
     * @param holder   holder内itemView的tag方法已被使用，不建议重新设置该值
     * @param treeNode 节点数据
     */
    public abstract void onBindViewHolder(T holder, N treeNode);


    /**
     * 节点类型，1分支，0叶子
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return NodeType.BRANCH.equals(dataList.get(position).getNodeType()) ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
