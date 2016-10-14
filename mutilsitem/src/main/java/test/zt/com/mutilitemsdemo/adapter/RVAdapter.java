package test.zt.com.mutilitemsdemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import test.zt.com.mutilitemsdemo.R;
import test.zt.com.mutilitemsdemo.bean.QsLatesdEntity;
import test.zt.com.mutilitemsdemo.uri.AppInterface;

/**
 * RecyclerView的多布局
 *  1.将泛型设置为RecyclerView.ViewHOlder
 *  2.创建多布局的所有的Item所对应的ViewHolder，并且每一个ViewHolder都要继承RecyclerView的ViewHolder
 *  3.重写抽象方法
 */
public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private List<QsLatesdEntity.ItemsBean> datas;
    private LayoutInflater inflater;
    //注意每一个的类型一定要是从0开始排序的
    private static final int TYPE_WORD=0;
    private static final int TYPE_IMAGE=1;

    public RVAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        datas = new ArrayList<>();
        this.context = context;
    }

    /**
     *
     * @param parent
     * @param viewType:item的类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_IMAGE:
                return new ImageVH(inflater.inflate(R.layout.item_qslatesd_typeimage,parent,false));
            case TYPE_WORD:
                return new WordVH(inflater.inflate(R.layout.item_qslatesd_typeword,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QsLatesdEntity.ItemsBean item = datas.get(position);
        //设置内容
        switch (getItemViewType(position))
        {
            case TYPE_WORD:
                WordVH wordVH = (WordVH)holder;
                //设置内容
                if (item.getUser()!=null && item.getUser().getLogin()!=null) {
                    wordVH.login.setText(item.getUser().getLogin());
                }
                if (item.getContent()!=null) {
                    wordVH.content.setText(item.getContent());
                }
                if(item.getUser()!=null && item.getUser().getIcon()!=null)
                {
//                    int userId = item.getUser().getId();
//                    String iconUrl = String.format(AppInterface.URL_USER_ICON,userId/10000,userId,item.getUser().getIcon());
//                    Picasso.with(context).load(Uri.parse(iconUrl)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(wordVH.icon);
                }
                break;
            case TYPE_IMAGE:
                ImageVH imageVH = (ImageVH)holder;
                //设置图片内容
                //设置内容
                if (item.getUser()!=null && item.getUser().getLogin()!=null) {
                    imageVH.login.setText(item.getUser().getLogin());
                }
                if (item.getContent()!=null) {
                    imageVH.content.setText(item.getContent());
                }
                if(item.getUser()!=null && item.getUser().getIcon()!=null)
                {
                    int userId = item.getUser().getId();
                    String iconUrl = String.format(AppInterface.URL_USER_ICON,userId/10000,userId,item.getUser().getIcon());
                    Picasso.with(context).load(Uri.parse(iconUrl)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageVH.icon);
                }

                ViewGroup.LayoutParams layoutParams = imageVH.image.getLayoutParams();
                layoutParams.width = item.getImage_size().getS().get(0);
                layoutParams.height = item.getImage_size().getS().get(1);
                imageVH.image.setLayoutParams(layoutParams);
                imageVH.image.requestLayout();
                //设置image
                if(item.getImage()!=null)
                {
                    int id = item.getId();
                    String iconUrl = String.format(AppInterface.URL_IMAGE,id/10000,id,item.getImage());
                    Picasso.with(context).load(Uri.parse(iconUrl)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageVH.image);
                }
                break;
        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 获取对应位置的item类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if("image".equals(datas.get(position).getFormat()))
        {
            type = TYPE_IMAGE;
        }
        else if("word".equals(datas.get(position).getFormat()))
        {
            type = TYPE_WORD;
        }
        return type;
    }

    public void addAll(List<QsLatesdEntity.ItemsBean> dd) {
        datas.addAll(dd);
        notifyDataSetChanged();
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    /**
     * 几个item布局就有几个ViewHolder
     */

    //展示文字的ViewHolder
    class WordVH extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView login,content;

        public WordVH(View convertView) {
            super(convertView);
            icon = ((ImageView) convertView.findViewById(R.id.item_qslatesd_typeword_iconId));
            login = ((TextView) convertView.findViewById(R.id.item_qslatesd_typeword_loginId));
            content = ((TextView) convertView.findViewById(R.id.item_qslatesd_typeword_contentId));
        }
    }

    //展示图片的ViewHolder
    class ImageVH extends RecyclerView.ViewHolder{
        private ImageView icon,image;
        private TextView login,content;

        public ImageVH(View convertView) {
            super(convertView);
            icon = ((ImageView) convertView.findViewById(R.id.item_qslatesd_typeimage_iconId));
            login = ((TextView) convertView.findViewById(R.id.item_qslatesd_typeimage_loginId));
            content = ((TextView) convertView.findViewById(R.id.item_qslatesd_typeimage_contentId));
            image = ((ImageView) convertView.findViewById(R.id.item_qslatesd_typeimage_imageId));
        }
    }


}
