package com.zapposdemo.zapposdemo.lib;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.zapposdemo.zapposdemo.ActivityCompare;
import com.zapposdemo.zapposdemo.R;

public class RecyclerViewAdapter extends
		RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder> {

	///variables////
	private List<Item> ItemList;
	private Context context;
	private int activityFlag = 0;

	//constructor
	public RecyclerViewAdapter(Context context, List<Item> ItemList,
			int activityFlag) {
		this.ItemList = ItemList;
		this.context = context;
		this.activityFlag = activityFlag;
	}

	@Override
	public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.cardview_item, null);
		CustomViewHolder viewHolder = new CustomViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
		Item Item = ItemList.get(i);

		// Download image using picasso library
		Picasso.with(context).load(Item.getThumbnail())
				.error(R.drawable.noimage).placeholder(R.drawable.noimage)
				.into(customViewHolder.imageView);

		// Setting textview text
		customViewHolder.textViewBrandName.setText(Item.getBrandName());
		customViewHolder.textViewProductName.setText(Item.getProductName());
		customViewHolder.textViewOriginalPrice.setText(Item.getOriginalPrice());
		customViewHolder.textViewOriginalPrice
				.setPaintFlags(customViewHolder.textViewOriginalPrice
						.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		customViewHolder.textViewFinalPrice.setText(Item.getFinalPrice());
		customViewHolder.textViewPriceOff.setText(Item.getPriceOff() + " Off");

	}

	@Override
	public int getItemCount() {
		return (null != ItemList ? ItemList.size() : 0);
	}

	class CustomViewHolder extends RecyclerView.ViewHolder {
		protected ImageView imageView;
		protected TextView textViewBrandName, textViewProductName,
				textViewOriginalPrice, textViewFinalPrice, textViewPriceOff;

		public CustomViewHolder(View view) {
			super(view);
			this.imageView = (ImageView) view.findViewById(R.id.product_image);
			this.textViewBrandName = (TextView) view
					.findViewById(R.id.brand_name);
			this.textViewProductName = (TextView) view
					.findViewById(R.id.product_name);
			this.textViewOriginalPrice = (TextView) view
					.findViewById(R.id.product_original_price);
			this.textViewFinalPrice = (TextView) view
					.findViewById(R.id.product_final_price);
			this.textViewPriceOff = (TextView) view
					.findViewById(R.id.product_price_off);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					int pos = getAdapterPosition();
					Item feedItem = ItemList.get(pos);
					// Toast.makeText(context, feedItem.getProductName(),
					// Toast.LENGTH_SHORT).show();

					
					//open new activity only if called from zappos recyclerview
					if (activityFlag == 0) {
						Intent intent = new Intent(context,
								ActivityCompare.class);
						intent.putExtra("productName",
								feedItem.getProductName());
						intent.putExtra("productFinalPrice",
								feedItem.getFinalPrice());
						context.startActivity(intent);
					}
				}
			});

		}

	}

}