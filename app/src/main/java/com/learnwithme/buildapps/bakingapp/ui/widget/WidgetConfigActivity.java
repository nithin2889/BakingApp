package com.learnwithme.buildapps.bakingapp.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.learnwithme.buildapps.bakingapp.BakingApp;
import com.learnwithme.buildapps.bakingapp.R;
import com.learnwithme.buildapps.bakingapp.di.component.DaggerWidgetDataComponent;

import java.util.Set;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class WidgetConfigActivity extends AppCompatActivity {

    @Inject
    WidgetDataHelper widgetDataHelper;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindString(R.string.widget_config_no_data)
    String errorMessage;
    private CompositeDisposable disposableList;
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_config_activity);
        ButterKnife.bind(this);

        disposableList = new CompositeDisposable();

        DaggerWidgetDataComponent.builder()
                .recipeRepositoryComponent(
                        ((BakingApp) getApplication()).getRecipeRepositoryComponent())
                .build()
                .inject(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }

        Set<String> names = widgetDataHelper.getRecipeNamesFromPrefs();

        if (names.size() == 0) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Fill the radioGroup
        int currentIndex = 0;

        for (String name : names) {
            AppCompatRadioButton button = new AppCompatRadioButton(this);
            button.setText(name);
            button.setId(currentIndex++);
            setRadioButtonColor(button);
            radioGroup.addView(button);
        }

        // Check the first item when loaded
        if (radioGroup.getChildCount() > 0) {
            ((AppCompatRadioButton) radioGroup.getChildAt(0)).setChecked(true);
        }
    }

    @OnClick(R.id.button)
    public void onOkButtonClick() {

        int checkedItemId = radioGroup.getCheckedRadioButtonId();
        String recipeName = ((AppCompatRadioButton) radioGroup
                .getChildAt(checkedItemId)).getText().toString();

        widgetDataHelper.saveRecipeNameToPrefs(appWidgetId, recipeName);

        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        Disposable subscription = widgetDataHelper
                .getIngredientsList(recipeName)
                .subscribe(
                        // OnNext
                        ingredients ->
                                WidgetProvider
                                        .updateAppWidgetContent(context, appWidgetManager, appWidgetId, recipeName,
                                                ingredients),
                        // OnError
                        throwable ->
                                Timber.d("Error: unable to populate widget data."));

        disposableList.add(subscription);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposableList.clear();
    }

    private void setRadioButtonColor(AppCompatRadioButton button) {
        int color = ContextCompat.getColor(this, R.color.colorPrimary);

        ColorStateList colorStateList = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}
                },
                new int[] {
                        color, color
                }
        );
        button.setSupportButtonTintList(colorStateList);
    }
}