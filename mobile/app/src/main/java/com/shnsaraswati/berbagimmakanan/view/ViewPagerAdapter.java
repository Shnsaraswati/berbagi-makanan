package com.shnsaraswati.berbagimmakanan.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.shnsaraswati.berbagimmakanan.R;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    int[] images = {
            R.drawable.keranjang,
            R.drawable.foto,
            R.drawable.upload,
            R.drawable.pilih,
            R.drawable.tunggu,
            R.drawable.peta,
            R.drawable.bintang
    };

    int[] description = {

            R.string.desc1,
            R.string.desc2,
            R.string.desc3,
            R.string.desc4,
            R.string.desc5,
            R.string.desc6,
            R.string.desc7,

    };

    public ViewPagerAdapter(Context context) {

        this.context = context;

    }

    @Override
    public int getCount() {
        return description.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slidetitleimage = (ImageView) view.findViewById(R.id.gambar1);
        TextView slidedescription = (TextView) view.findViewById(R.id.textslide1);

        slidetitleimage.setImageResource(images[position]);
        slidedescription.setText(description[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
