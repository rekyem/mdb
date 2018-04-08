package themoviedb.privalia.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Arturo on 08/04/2018.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    boolean doubleWidth;
    boolean onlyWidth;
    boolean doubleHeight;
    boolean onlyHeight;

    public SpacesItemDecoration(Context ctx, int space, boolean doubleWidth, boolean onlyWidth, boolean doubleHeight, boolean onlyHeight) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = ctx.getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        this.space = (int) (dpWidth * space / 800);
        this.doubleWidth = doubleWidth;
        this.onlyWidth = onlyWidth;
        this.doubleHeight = doubleHeight;
        this.onlyHeight = onlyHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {



        outRect.right = this.space;
        outRect.left = this.space;
        outRect.top = this.space;
        outRect.bottom = this.space;

        if(doubleWidth){
            outRect.right = outRect.right*2;
            outRect.left = outRect.left*2;
        }

        if(onlyWidth){
            outRect.top = 0;
            outRect.bottom = 0;
            outRect.right = outRect.right*2;
            outRect.bottom = outRect.bottom*2;
        }

        if(doubleHeight){
            outRect.top = outRect.top*2;
            outRect.bottom = outRect.bottom*2;
        }

        if(onlyHeight){
            outRect.right = 0;
            outRect.left = 0;
            outRect.top = outRect.top*2;
            outRect.bottom = outRect.bottom*2;
        }


    }
}


