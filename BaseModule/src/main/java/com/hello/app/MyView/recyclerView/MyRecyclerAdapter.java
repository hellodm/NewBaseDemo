package com.hello.app.MyView.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hello.app.R;
import com.hello.app.Util.ViewUtil;

import java.util.Collections;
import java.util.List;

/**
 * Copyright 2012-2014  CST.All Rights Reserved
 *
 * Comments：功能描述
 *
 * @author dong
 * @Time: 2017/3/8
 *
 * Modified By: ***
 * Modified Date: ***
 * Why & What is modified:
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的

    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的

    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private List<String> mDatas;

    private View mHeaderView;

    private View mFooterView;

    private OnViewDelete mDelete;

    public MyRecyclerAdapter(List<String> list) {
        this.mDatas = list;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    public interface OnViewDelete {

        void deleteView(int view);
    }

    public void setView(OnViewDelete viewDelete) {
        mDelete = viewDelete;
    }


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new MapHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ListHolder(mFooterView);
        }
        View layout = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_report_tips, parent, false);
        return new ListHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (mHeaderView == null) {
                ((ListHolder) holder).tv.setText(mDatas.get(position));
                return;
            }
            if (holder instanceof ListHolder) {
                ((ListHolder) holder).tv.setText(mDatas.get(position - 1));
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }


    class ListHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ListHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            tv = (TextView) itemView.findViewById(R.id.report_text_name);
        }
    }

    class MapHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MapHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mDatas.size();
        } else if (mHeaderView == null && mFooterView != null) {
            return mDatas.size() + 1;
        } else if (mHeaderView != null && mFooterView == null) {
            return mDatas.size() + 1;
        } else {
            return mDatas.size() + 2;
        }
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);


    }

    @Override
    public void onItemDismiss(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        mDelete.deleteView(100);

    }

}
