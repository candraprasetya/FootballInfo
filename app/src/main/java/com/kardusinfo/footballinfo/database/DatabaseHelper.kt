package com.kardusinfo.footballinfo.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) :
    ManagedSQLiteOpenHelper(ctx, "Candra.db",null,1){

    companion object {
        private  var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper{
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavMatchDB.TABLE_FAV_MATCH,true,
            FavMatchDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavMatchDB.ID_EVENT to TEXT + UNIQUE,
            FavMatchDB.DATE_EVENT to TEXT,
            FavMatchDB.HOME_TEAM to TEXT,
            FavMatchDB.AWAY_TEAM to TEXT,
            FavMatchDB.HOME_SCORE to TEXT,
            FavMatchDB.AWAY_SCORE to TEXT,
            FavMatchDB.HOME_GOAL_DETAIL to TEXT,
            FavMatchDB.AWAY_GOAL_DETAIL to TEXT,
            FavMatchDB.TIME_EVENT to TEXT
        )

        db.createTable(FavTeamDB.TABLE_FAV_TEAM, true,
            FavTeamDB.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavTeamDB.ID_TEAM to TEXT + UNIQUE,
            FavTeamDB.NAME_TEAM to TEXT,
            FavTeamDB.IMAGE_TEAM to TEXT,
            FavTeamDB.YEAR_TEAM to TEXT,
            FavTeamDB.STADIUM_TEAM to TEXT,
            FavTeamDB.STADIUM_THUMB to TEXT,
            FavTeamDB.FANART1_TEAM to TEXT,
            FavTeamDB.FANART2_TEAM to TEXT,
            FavTeamDB.DESC_TEAM to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavMatchDB.TABLE_FAV_MATCH,true)
        db.dropTable(FavTeamDB.TABLE_FAV_TEAM, true)
    }

}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)