package test.zt.com.rvtheaderandfooter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class RVAdapter3 extends RecyclerView.Adapter<RVAdapter3.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private LayoutInflater inflater;
    private List<String> datas;
    private LoaderListener loaderListener;

    public void setLoaderListener(LoaderListener loaderListener) {
        this.loaderListener = loaderListener;
    }

    //头布局
    private View headerView;
    //尾部
    private View footerView;

    public RVAdapter3(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.datas = new ArrayList<>();
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    public interface LoaderListener{
        public void loadMore();
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0) //头
        {
            return TYPE_HEADER;
        }
        else if(position>0 && position<getItemCount()-1) //listview每个真正的item
        {
            return TYPE_ITEM;
        }else{ //尾
            return TYPE_FOOTER;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType) {
            case TYPE_HEADER:
                view = headerView;
                break;
            case TYPE_ITEM:
                view = inflater.inflate(R.layout.item_test,parent,false);
                break;
            case TYPE_FOOTER:
                view = footerView;
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_ITEM) //item
        {
            ViewGroup.LayoutParams layoutParams = holder.textView.getLayoutParams();

            layoutParams.height = (int)(Math.random()* 600 + 200);

            holder.itemView.setLayoutParams(layoutParams);

            holder.itemView.requestLayout();

            holder.textView.setText(datas.get(position-1));

        }else if(getItemViewType(position) == TYPE_FOOTER)
        {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        holder.itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                loaderListener.loadMore();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 要展示的itemView的条目数
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size()+2;
    }

    public void addAll(List<String> dd) {
        datas.addAll(dd);
        notifyDataSetChanged();
    }

    public void addHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public void addHFooterView(View footerView) {
        this.footerView = footerView;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.item_rv_textViewId));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //点击真正的item
            if(getLayoutPosition()>0 && getLayoutPosition() < getItemCount()-1)//当前点击的真正的item
            {
                Log.i("info","position:"+getLayoutPosition());
            }
        }
    }

    /**
     * 将view的布局参数调整为填充父布局
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if(layoutParams!=null&& layoutParams instanceof StaggeredGridLayoutManager.LayoutParams ){
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            int position = holder.getLayoutPosition();
            if(position == 0 || position ==  getItemCount()-1)
            {
                lp.setFullSpan(true);
            }
        }
    }
}
