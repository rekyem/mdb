package themoviedb.privalia.views;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.wang.avi.AVLoadingIndicatorView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import themoviedb.privalia.ApplicationMdb;
import themoviedb.privalia.R;
import themoviedb.privalia.models.MoviePageModel;
import themoviedb.privalia.network.MdbService;
import themoviedb.privalia.utils.EndlessScrollListener;
import themoviedb.privalia.utils.SpacesItemDecoration;

/**
 * Created by Arturo on 08/04/2018.
 */

public class MovieActivity extends AppCompatActivity implements MovieActivityView {


    @Inject
    public MdbService service;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;


    MovieActivityPresenter presenter;

    SearchView mSearhView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    MovieActivityAdapter adapter;
    boolean inSearch;
    String searchQuery;

    public EndlessScrollListener endlessScrollListener = new EndlessScrollListener() {
        @Override
        public void onLoadMore(int page, int totalItemsCount) {
            if(inSearch){
                presenter.searchMovie(page, searchQuery);
            } else {
                presenter.getMovieList(page);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ApplicationMdb.app().basicComp().inject(this);
        createToolBar();

        presenter = new MovieActivityPresenter(service, this);
        presenter.getMovieList(1);
    }

    private void createToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearhView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        mSearhView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        mSearhView.setMaxWidth(Integer.MAX_VALUE);

        mSearhView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                    doSearch(query);
                return false;
            }
        });

        mSearhView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                presenter.getMovieList(1);
                return false;
            }
        });

        return true;
    }



    private void doSearch(String query){
        inSearch = true;
        adapter = null;
        searchQuery = query;
        presenter.searchMovie(1, searchQuery);
    }



    @Override
    public void showWait() {
        loading.show();
    }

    @Override
    public void removeWait() {
        loading.hide();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        Log.e("Error",appErrorMessage);
    }

    @Override
    public void getMovieListSucess(MoviePageModel moviePageModel) {

        if (null == adapter) {
            adapter = new MovieActivityAdapter(getApplicationContext(), moviePageModel.getMovies());
            mRecyclerView.setAdapter(adapter);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            endlessScrollListener.setLayoutManager(mRecyclerView.getLayoutManager());
            mRecyclerView.addOnScrollListener(endlessScrollListener);

            if (mRecyclerView.getItemDecorationCount() == 0)
                mRecyclerView.addItemDecoration(new SpacesItemDecoration(MovieActivity.this, 35, false, false, false, false));

        } else {
            adapter.refreshMovies(moviePageModel.getMovies());
            adapter.notifyDataSetChanged();
        }


    }


}
