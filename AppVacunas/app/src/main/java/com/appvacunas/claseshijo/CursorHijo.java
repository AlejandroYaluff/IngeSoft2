package com.appvacunas.claseshijo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import com.appvacunas.R;
import com.appvacunas.clases.DatosHijo.HijoTable;

/**
 * Clase Cursor Hijo
 */

public class CursorHijo extends CursorAdapter {
    public CursorHijo(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_hijo, viewGroup, false);
    }

    // Construccion de la vista con la lista de hijos
    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView date = (TextView) view.findViewById(R.id.tv_fecha);
        final ImageView imagenavatar = (ImageView) view.findViewById(R.id.iv_avatar);

        String nombre = cursor.getString(cursor.getColumnIndex(HijoTable.NOMBRE));
        String apellido = cursor.getString(cursor.getColumnIndex(HijoTable.APELLIDO));
        String nacimiento = cursor.getString(cursor.getColumnIndex(HijoTable.FECHA_NACIMIENTO));
        String avatar;
        avatar = "vacuna.png";

        name.setText(nombre+" "+apellido);
        date.setText(nacimiento);
        Glide
                .with(context)
                .load(Uri.parse("file:///android_asset/" + avatar))
                .asBitmap()
                .error(R.drawable.avatar)
                .centerCrop()
                .into(new BitmapImageViewTarget(imagenavatar) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        imagenavatar.setImageDrawable(drawable);
                    }
                });
    }
}

