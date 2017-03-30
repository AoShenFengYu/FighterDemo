package fengyu.aoshen.topeakdemo.bullet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fengyu.aoshen.topeakdemo.R;
import fengyu.aoshen.topeakdemo.model.BulletInfo;

/**
 * Created by David on 2017/3/30.
 */
public class BulletListAdapter extends RecyclerView.Adapter<BulletListAdapter.ItemViewHolder> {

    public final static String TAG = "BulletListAdapter";

    private final static int ITEM_VIEW_TYPE_BULLET = 0;

    private Context context;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutInflater layoutInflater;

    private ArrayList<BulletInfo> bullets;
    private OnItemClickListener onItemClickListener;

    public BulletListAdapter(Context context, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.layoutManager = layoutManager;
        bullets = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case ITEM_VIEW_TYPE_BULLET:
                view = layoutInflater.inflate(R.layout.view_bullet_list_item, parent, false);
                break;

            default:
                view = layoutInflater.inflate(R.layout.view_null_layout, parent, false);

        }


        return new ItemViewHolder(view, viewType);
    }

    @Override
    public int getItemCount() {
        return bullets.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_VIEW_TYPE_BULLET;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        switch (holder.getViewType()) {
            case ITEM_VIEW_TYPE_BULLET:

                BulletInfo bulletInfo = bullets.get(position);
                holder.image.setImageResource(bulletInfo.getListItemIconResId());

            default:

        }

    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        int viewType;

        TextView name;
        ImageView image;
        TextView damage;

        public ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;

            switch (viewType) {
                case ITEM_VIEW_TYPE_BULLET:
                    name = (TextView) itemView.findViewById(R.id.bullet_list_item_name);
                    image = (ImageView) itemView.findViewById(R.id.bullet_list_item_image);
                    damage = (TextView) itemView.findViewById(R.id.bullet_list_item_damage);

                    setOnClickListener();

                    break;

                default:

            }
        }

        private void setOnClickListener() {

            if (onItemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(v, bullets.get(getLayoutPosition()));
                    }
                });
            } else {
                itemView.setOnClickListener(null);
            }

        }

        public int getViewType() {
            return viewType;
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, BulletInfo bulletInfo);
    }

    public void update(ArrayList<BulletInfo> bulletInfos) {
        this.bullets = bulletInfos;
        notifyItemChanged(0, getItemCount());
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getBulletTypeCount() {
        return bullets.size();
    }


    public BulletInfo getBulletInfo(int position) {
        if (bullets.size() > position)
            return bullets.get(position);
        else
            return null;
    }
}
