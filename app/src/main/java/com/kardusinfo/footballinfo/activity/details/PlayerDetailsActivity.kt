package com.kardusinfo.footballinfo.activity.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.kardusinfo.footballinfo.R
import com.kardusinfo.footballinfo.model.PlayerDataItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_player.*

class PlayerDetailsActivity : AppCompatActivity() {

    private lateinit var playerData : PlayerDataItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_player)

        playerData = intent.getParcelableExtra("player_data")


        if(playerData.mFanArt1.isNullOrEmpty()){
            // Reference : https://android--code.blogspot.com/2018/03/android-kotlin-imageview-set-image.html
            img_player_banner_detail.setImageResource(R.drawable.bg)
        } else {
            Picasso.get().load(playerData.mFanArt1).into(img_player_banner_detail)
        }

        if(playerData.mCutOut.isNullOrEmpty()) {
            img_player_potrait_detail.setImageResource(R.drawable.player)
        } else {
            Picasso.get().load(playerData.mCutOut).into(img_player_potrait_detail)
        }

        tv_player_name_detail.text = playerData.mNamePlayer

        if(playerData.mWeight.isNullOrEmpty()) {
            tv_player_weight_detail.setText(" - ")
        } else {
            tv_player_weight_detail.text = playerData.mWeight
        }

        if(playerData.mHeight.isNullOrEmpty()){
            tv_player_height_detail.setText(" - ")
        } else {
            tv_player_height_detail.text = playerData.mHeight
        }

        tv_player_position_detail.text = playerData.mPosition
        tv_player_description_detail.text = playerData.mDescriptionEN

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = playerData.mNamePlayer +" Profile"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}
