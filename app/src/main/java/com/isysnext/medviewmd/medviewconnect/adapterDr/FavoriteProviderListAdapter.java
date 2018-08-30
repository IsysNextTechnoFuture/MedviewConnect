package com.isysnext.medviewmd.medviewconnect.adapterDr;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.isysnext.medviewmd.medviewconnect.modelDr.HomeProviderListDTO;
import com.isysnext.medviewmd.medviewconnect.OnItemClickListener;
import com.isysnext.medviewmd.medviewconnect.R;
import com.isysnext.medviewmd.medviewconnect.utils.AppConstants;
import com.isysnext.medviewmd.medviewconnect.utils.AppSession;
import com.isysnext.medviewmd.medviewconnect.utils.Utilities;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;


public class FavoriteProviderListAdapter extends RecyclerView.Adapter<FavoriteProviderListAdapter.ViewHolder>
        implements AppConstants {
    //Declaration of variables
    ArrayList<HomeProviderListDTO.Provider> providerList;
    private Context context;
    private Utilities utilities;
    private AppSession appSession;
    private CustomSpinnerAdapter customSpinnerAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int iFirstVisibleItem, iVisibleItemCount,
            iTotalItemCount, iLastVisibleItem, pageNo = 1, pageSize = 10, iListPosition;
    private RecyclerView rvAttendance;
    private boolean bShouldLoadMore = false;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;

    //Constructor
    public FavoriteProviderListAdapter(Context context,
                                       ArrayList<HomeProviderListDTO.Provider> providerList,
                                       OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.providerList = providerList;
        this.context = context;
        utilities = Utilities.getInstance(context);
        appSession = new AppSession(context);
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        switch (viewType) {
            case 0:
                layout = R.layout.dr_list_item_home_favorite_provider_list;
                break;
        }
        //Inflate view on adapter
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Set server data on view according to position
        if(providerList.get(position).getIsFavourite() != null || !providerList.get(position).getIsFavourite().equals("")) {
            if(providerList.get(position).getIsFavourite().equals("1"))
            {
                holder.tvProviderName.setText(providerList.get(position).getName());
                holder.tvSpeciality.setText(providerList.get(position).getSpecialty());
                holder.rbProvider.setRating(providerList.get(position).getCalculatedRating());
                try {
                    if (providerList.get(position).getAvatar() != null || !providerList.get(position).getAvatar().equals("")) {
                        Glide.with(context)
                                .load(BASE_URL+providerList.get(position).getAvatar())
                                .placeholder(R.mipmap.user_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.user_image)
                                .skipMemoryCache(false)
                                .dontAnimate()
                                .into(holder.civProvider);
                        Log.e("Image--------", BASE_URL + providerList.get(position).getAvatar());
                    } else {
                        Glide.with(context)
                                .load(R.mipmap.user_image)
                                .placeholder(R.mipmap.user_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.user_image)
                                .skipMemoryCache(false)
                                .dontAnimate()
                                .into(holder.civProvider);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {

            }
        }
        //Click Event
        holder.llProviderListItem.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
        holder.ivFavorite.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return providerList.size();
    }

    //Class for holding view
    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llProviderListItem;
        CircleImageView civProvider;
        TextView tvProviderName,tvSpeciality,tvStatus;
        ImageView ivStatus,ivFavorite,ivChat;
        RatingBar rbProvider;
        Typeface iconFont;
        public ViewHolder(View v) {
            super(v);
          //  iconFont = FontManager.getTypeface(context.getApplicationContext(), FontManager.FONTAWESOME);
            llProviderListItem = (LinearLayout) v.findViewById(R.id.ll_provider_list_item);
            civProvider = (CircleImageView) v.findViewById(R.id.civ_provider);
            tvProviderName = (TextView) v.findViewById(R.id.tv_provider_name);
            tvSpeciality = (TextView) v.findViewById(R.id.tv_speciality);
            tvStatus = (TextView) v.findViewById(R.id.tv_status);
            rbProvider = (RatingBar) v.findViewById(R.id.rb_provider);
            ivFavorite = (ImageView) v.findViewById(R.id.iv_favorite);
            ivStatus = (ImageView) v.findViewById(R.id.iv_status);
            ivChat = (ImageView) v.findViewById(R.id.iv_chat);

        }
    }

}
