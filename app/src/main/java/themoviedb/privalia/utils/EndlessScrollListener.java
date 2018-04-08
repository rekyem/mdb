package themoviedb.privalia.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by Arturo on 08/04/2018.
 */

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    private final int VISIBLE_THRESHOLD = 3;
    private RecyclerView.LayoutManager mLayoutManager;
    private int currentPage = 1;
    public int posX;
    public int posY;
    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private boolean loading = true;

    public EndlessScrollListener() {

    }

    public void setLayoutManager(RecyclerView.LayoutManager _mLayoutManager){
        this.mLayoutManager = _mLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {
        posX = dx;
        posY = dy;
        totalItemCount = mLayoutManager.getItemCount();
        visibleItemCount = mLayoutManager.getChildCount();

        firstVisibleItem = ((StaggeredGridLayoutManager)mLayoutManager).findFirstVisibleItemPositions(null)[0];

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            // End has been reac


            loading = true;
            onLoadMore(currentPage, totalItemCount);
        }

    }

    // Defines the process for actually loading more data based on page
    // Returns true if more data is being loaded; returns false if there is no more data to load.
    public abstract void onLoadMore(int page, int totalItemsCount);


    public int getCurrentPage(){return currentPage;}

    public int getFirstVisibleItem(){return firstVisibleItem;}
}
