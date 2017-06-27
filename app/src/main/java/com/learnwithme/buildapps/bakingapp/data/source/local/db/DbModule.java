package com.learnwithme.buildapps.bakingapp.data.source.local.db;

import android.content.Context;
import android.support.annotation.NonNull;

import com.learnwithme.buildapps.bakingapp.data.source.local.db.helper.DbHelper;
import com.learnwithme.buildapps.bakingapp.di.ApplicationContext;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by Nithin on 26/06/2017.
 */
@Module
public class DbModule {
    @Singleton
    @Provides
    @NonNull
    BriteDatabase provideBriteDatabase(SqlBrite sqlBrite, DbHelper dbHelper, Scheduler scheduler) {
        return sqlBrite.wrapDatabaseHelper(dbHelper, scheduler);
    }

    @Singleton
    @Provides
    @NonNull
    SqlBrite provideSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Singleton
    @Provides
    @NonNull
    DbHelper provideDbHelper(@ApplicationContext Context context) {
        return new DbHelper(context);
    }

    @Provides
    @NonNull
    public Scheduler provideScheduler() {
        return Schedulers.io();
    }
}