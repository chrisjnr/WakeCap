package android.wakecap.co.wakecap;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.wakecap.co.wakecap.Adapters.ViewPagerAdapter;
import android.wakecap.co.wakecap.Adapters.WorkersAdapter;
import android.wakecap.co.wakecap.Fragments.LoadRoles;
import android.wakecap.co.wakecap.Fragments.LoadWorkers;
import android.wakecap.co.wakecap.Models.WorkersListResponse;
import android.wakecap.co.wakecap.Retrofit.ApiInterface;
import android.wakecap.co.wakecap.Retrofit.RetrofitCompatJson;
import android.wakecap.co.wakecap.ViewModels.WorkersViewModel;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public ArrayList<WorkersListResponse> workerList;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        WorkersViewModel workersViewModel = ViewModelProviders.of(this).get(WorkersViewModel.class);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoadWorkers(), "View All Workers");
        adapter.addFragment(new LoadRoles(), "Workers by Section");

        viewPager.setAdapter(adapter);
    }

}
