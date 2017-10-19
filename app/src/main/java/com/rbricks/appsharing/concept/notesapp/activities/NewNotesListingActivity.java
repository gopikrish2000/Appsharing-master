package com.rbricks.appsharing.concept.notesapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.rbricks.appsharing.R;
import com.rbricks.appsharing.concept.notesapp.adapters.NotesListingAdapter;
import com.rbricks.appsharing.concept.notesapp.domains.NotesItem;
import com.rbricks.appsharing.concept.notesapp.fragments.NotesSearchDialogFragment;
import com.rbricks.appsharing.concept.notesapp.intdefs.NotesActionType;
import com.rbricks.appsharing.concept.notesapp.interfaces.MVPNotesInterface;
import com.rbricks.appsharing.concept.notesapp.presenters.NewNotesListingPresenter;
import com.rbricks.appsharing.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.rbricks.appsharing.concept.notesapp.utils.ViewUtils.setGone;
import static com.rbricks.appsharing.concept.notesapp.utils.ViewUtils.setVisibleView;
import static com.rbricks.appsharing.utils.CommonUtils.doUnsubscribe;
import static com.rbricks.appsharing.utils.CommonUtils.getIntegerIntentValue;
import static com.rbricks.appsharing.utils.CommonUtils.getIntentValue;
import static com.rbricks.appsharing.utils.CommonUtils.isNullOrEmpty;


public class NewNotesListingActivity extends AppCompatActivity implements MVPNotesInterface {

    private RecyclerView notesListingRv;
    private NewNotesListingPresenter notesListingPresenter;
    private ArrayList<NotesItem> notesItemList;
    private NotesListingAdapter notesListingAdapter;
    private FloatingActionButton fabButton;
    private CompositeDisposable lifeCycle;
    private static final String NOTES_LIST = "NOTES_LIST";
    private ProgressDialog progressDialog;
    private View noNotesFoundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_listing);
        initViews();
        initActions();
        fetchDataFromSavedInstanceIfPresent(savedInstanceState);
    }

    private void initViews() {
        notesListingRv = ((RecyclerView) findViewById(R.id.notes_listing_rv));
        fabButton = ((FloatingActionButton) findViewById(R.id.notes_fab));
        noNotesFoundView = findViewById(R.id.no_notes_found_view);
    }

    private void initActions() {
        lifeCycle = new CompositeDisposable();
        initializeToolbar(getString(R.string.notes_listing_title));
        notesListingPresenter = new NewNotesListingPresenter(this, this);
        notesItemList = new ArrayList<>();
        notesListingAdapter = new NotesListingAdapter(notesItemList);
        notesListingRv.setLayoutManager(new LinearLayoutManager(this));
        notesListingRv.setAdapter(notesListingAdapter);

        lifeCycle.add(notesListingAdapter.getItemClickSubject().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(pair -> {
            notesListingPresenter.onItemClicked(pair);
        }));
        lifeCycle.add(notesListingAdapter.getItemLongClickSubject().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(pair -> {
            notesListingPresenter.onItemLongClicked(pair);
        }));
        lifeCycle.add(RxView.clicks(fabButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(s -> {
            notesListingPresenter.onAddClick();
        }));

        progressDialog = new ProgressDialog(NewNotesListingActivity.this, R.style.NotesDialogTheme);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.setMessage(getString(R.string.loading));
    }

    // Doing this for Optimizing the Database calls in mobile flip case.
    private void fetchDataFromSavedInstanceIfPresent(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            notesListingPresenter.fetchNotesList();
        } else {
            ArrayList<NotesItem> parcelledNotesList = savedInstanceState.getParcelableArrayList(NOTES_LIST);
            if (isNullOrEmpty(parcelledNotesList)) {
                notesListingPresenter.fetchNotesList();
            } else {
                showAllNotes(parcelledNotesList);
            }
        }
    }

    @Override
    public void removeNotesOfPosition(int index) {
        notesItemList.remove(index);
        notesListingAdapter.notifyItemRemoved(index);
        performNoNotesFoundLogic();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                performActionsBasedOnType(data);
            }
        }
    }

    private void performActionsBasedOnType(Intent data) {
        if (data == null || data.getExtras() == null) {
            return;
        }
        // Get the data from Notes Details Page & update in only the item added/changed/deleted .
        // So that no need to fetch all data from database again .
        String actionType = getIntentValue(data, NotesDetailsActivity.ACTION_TYPE);
        int itemPostion = getIntegerIntentValue(data, NotesDetailsActivity.ITEM_POSITION);
        NotesItem notesItem = (NotesItem) data.getParcelableExtra(NotesDetailsActivity.NOTES_ITEM);
        switch (actionType) {
            case NotesActionType.SEARCH:
                notesListingPresenter.fetchAllNotesListAfterSearch();  // In Search Flow any thing could have been updated/deleted.
                break;                                                 // So refreshing full Adapter
            case NotesActionType.DELETE:
                if (itemPostion >= notesItemList.size()) {
                    break;
                }
                removeNotesOfPosition(itemPostion);   // Optimizing Only one item is removed . not refreshing whole page.
                break;
            case NotesActionType.ADD:
                if (notesItem != null) {
                    notesItemList.add(0, notesItem);  // Adding item at first position
                    notesListingAdapter.notifyItemInserted(0);  // Optimizing Only one item is inserted . not refreshing whole page.
                    notesListingRv.smoothScrollToPosition(0);
                }
                break;
            case NotesActionType.UPDATE:
                NotesItem notesItemExisting = notesItemList.remove(itemPostion);
                notesItemExisting.setTitle(notesItem.getTitle());
                notesItemExisting.setDescription(notesItem.getDescription());
                notesItemList.add(0, notesItemExisting);
                notesListingAdapter.notifyItemMoved(itemPostion, 0);
                notesListingAdapter.notifyItemChanged(0);    // Optimizing Only one item is moved and changed . not refreshing whole page.
                notesListingRv.smoothScrollToPosition(0);    // making Recyclerview go to the top.
                break;
        }
        performNoNotesFoundLogic();
    }

    private void performNoNotesFoundLogic() {
        if (isNullOrEmpty(notesItemList)) {
            setVisibleView(noNotesFoundView);
        } else {
            setGone(noNotesFoundView);
        }
    }

    @Override
    public void showProgress() {
        if (!notesListingPresenter.isSubscribed()) {
            return;
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (!notesListingPresenter.isSubscribed()) {
            return;
        }
        CommonUtils.dismissDialog(progressDialog);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!isNullOrEmpty(notesItemList)) {
            outState.putParcelableArrayList(NOTES_LIST, notesItemList);
        }
    }

    @Override
    public void showAllNotes(List<NotesItem> notesItems, boolean isFromSearchFlow) {
        if (!notesListingPresenter.isSubscribed() || CommonUtils.isNullOrEmpty(notesItems)) {
            setVisibleView(noNotesFoundView);
            return;
        }
        setGone(noNotesFoundView);
        notesItemList.clear();
        notesItemList.addAll(notesItems);
        if (isFromSearchFlow) {
            // Any item can be modified/deleted from search flow. So doing notifyDataSetChanged for complete refresh.
            notesListingAdapter.notifyDataSetChanged();
        } else {  // On First time Page load of Listing. fetching complete list from DB and inserting.
            notesListingAdapter.notifyItemRangeInserted(0, notesItemList.size());
        }
    }

    @Override
    public void showAllNotes(List<NotesItem> notesItems) {
        showAllNotes(notesItems, false);
    }

    protected void initializeToolbar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            getSupportActionBar().setElevation(10);
        }
        toolbar.setNavigationIcon(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_listing_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(NewNotesListingActivity.this, R.string.about_text, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                NotesSearchDialogFragment dialogFragment = new NotesSearchDialogFragment();
                dialogFragment.show(getFragmentManager(), getString(R.string.search_string));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notesListingPresenter.unSubscribe();
        doUnsubscribe(lifeCycle);
    }
}
