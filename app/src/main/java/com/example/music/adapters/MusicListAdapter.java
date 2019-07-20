package com.example.music.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.music.R;
import com.example.music.activities.PlayMusicActivity;

public class MusicListAdapter extends RecyclerView.Adapter <MusicListAdapter.ViewHolder>{

    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalcaulation;

    public MusicListAdapter(Context context,RecyclerView recyclerView){
        mContext = context;
        mRv = recyclerView;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_music,parent,false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setRecyclerViewHeight();

        Glide.with(mContext)
                .load("http://res.lgdsunday.club/poster-1.png")
                .into(holder.ivIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    /**
     * 获取itemview高度
     * itemview数量
     * 高度*数量 = 高度
     */
    private void setRecyclerViewHeight(){
        if (isCalcaulation || mRv == null)return;

        isCalcaulation = true;

        //获取itemView的高度
        RecyclerView.LayoutParams itemViewLp = (RecyclerView.LayoutParams) mItemView.getLayoutParams();
        //itemview的数量
        int itemCount = getItemCount();
        //高度*数量 = 高度
        int recyclerViewHeight = itemViewLp.height * itemCount;
        //设置高度
        LinearLayout.LayoutParams rvLp = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        rvLp.height = recyclerViewHeight;
        mRv.setLayoutParams(rvLp);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
