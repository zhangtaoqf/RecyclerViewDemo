package test.zt.com.mutilitemsdemo.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import test.zt.com.mutilitemsdemo.R;
import test.zt.com.mutilitemsdemo.bean.QsLatesdEntity;
import test.zt.com.mutilitemsdemo.uri.AppInterface;

/**
 * 1.有几个类型的item 就需要写几个类型的ViewHolder
 * 2.重写适配器的两个方法
 *       getViewTypeCount
 *       getItemViewType
 * 3.开始写getView方法
 */
public class QsLatesdAdapter extends MBaseAdapter<QsLatesdEntity.ItemsBean> {
    //注意每一个的类型一定要是从0开始排序的
    private static final int TYPE_WORD=0;
    private static final int TYPE_IMAGE=1;
    Context context;

    public QsLatesdAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WordVH wordVH = null;
        ImageVH imageVH = null;
        if(convertView == null)
        {
            switch (getItemViewType(position))
            {
                case TYPE_WORD:
                    convertView = getInflater().inflate(R.layout.item_qslatesd_typeword,parent,false);
                    wordVH = new WordVH(convertView);
                    convertView.setTag(wordVH);
                    break;
                case TYPE_IMAGE:
                    convertView = getInflater().inflate(R.layout.item_qslatesd_typeimage,parent,false);
                    imageVH = new ImageVH(convertView);
                    convertView.setTag(imageVH);
                    break;
            }
        }
        else
        {
            if(convertView.getTag() instanceof WordVH){
                switch (getItemViewType(position))
                {
                    case TYPE_WORD:
                        wordVH = ((WordVH) convertView.getTag());
                        break;
                    case TYPE_IMAGE:
                        convertView = getInflater().inflate(R.layout.item_qslatesd_typeimage,parent,false);
                        imageVH = new ImageVH(convertView);
                        convertView.setTag(imageVH);
                        break;
                }
            }else if(convertView.getTag() instanceof ImageVH)
            {
                switch (getItemViewType(position))
                {
                    case TYPE_WORD:
                        convertView = getInflater().inflate(R.layout.item_qslatesd_typeword,parent,false);
                        wordVH = new WordVH(convertView);
                        convertView.setTag(wordVH);
                        break;
                    case TYPE_IMAGE:
                        imageVH = ((ImageVH) convertView.getTag());
                        break;
                }
            }
        }

        QsLatesdEntity.ItemsBean item = getItem(position);

        //设置内容
        switch (getItemViewType(position))
        {
            case TYPE_WORD:
                //设置内容
                if (item.getUser()!=null && item.getUser().getLogin()!=null) {
                    wordVH.login.setText(item.getUser().getLogin());
                }
                if (item.getContent()!=null) {
                    wordVH.content.setText(item.getContent());
                }
                if(item.getUser()!=null && item.getUser().getIcon()!=null)
                {
                    int userId = item.getUser().getId();
                    String iconUrl = String.format(AppInterface.URL_USER_ICON,userId/10000,userId,item.getUser().getIcon());
                    Picasso.with(context).load(Uri.parse(iconUrl)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(wordVH.icon);
                }
                break;
            case TYPE_IMAGE:
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
        return convertView;
    }

    /**
     * 获取item类型的个数
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 获取对应位置的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int type = -1;
        if (getItem(position).getFormat().equals("image")) {
            type = TYPE_IMAGE;
        }else if(getItem(position).getFormat().equals("word")){
            type = TYPE_WORD;
        }
        return type;

    }

    /**
     * 几个item布局就有几个ViewHolder
     */
    //展示文字的ViewHolder
    class WordVH{
        private ImageView icon;
        private TextView login,content;

        public WordVH(View convertView) {
            icon = ((ImageView) convertView.findViewById(R.id.item_qslatesd_typeword_iconId));
            login = ((TextView) convertView.findViewById(R.id.item_qslatesd_typeword_loginId));
            content = ((TextView) convertView.findViewById(R.id.item_qslatesd_typeword_contentId));
        }
    }

    //展示图片的ViewHolder
    class ImageVH{
        private ImageView icon,image;
        private TextView login,content;

        public ImageVH(View convertView) {
            icon = ((ImageView) convertView.findViewById(R.id.item_qslatesd_typeimage_iconId));
            login = ((TextView) convertView.findViewById(R.id.item_qslatesd_typeimage_loginId));
            content = ((TextView) convertView.findViewById(R.id.item_qslatesd_typeimage_contentId));
            image = ((ImageView) convertView.findViewById(R.id.item_qslatesd_typeimage_imageId));
        }
    }
}
