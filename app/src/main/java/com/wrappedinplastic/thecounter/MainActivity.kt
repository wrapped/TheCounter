package com.wrappedinplastic.thecounter

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var countTotal : Int = 0
    private var countIncrease : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val increase = pref.getString("increase", "")
        txtIncrement.text = "Increment amount: " + increase.toString()

        val on = pref.getBoolean("theme_switch", false)

        if (on) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("count", txtCountTotal.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val count = savedInstanceState.getString("count")
        txtCountTotal.text = count
        countTotal = count.toString().toInt()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val on = pref.getBoolean("volumeButtons", false)

        if (on) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                val increase = pref.getString("increase", "")
                countIncrease = increase.toString().toInt()
                countTotal += countIncrease
                txtCountTotal.text = countTotal.toString()
                return true
            }
            if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                val increase = pref.getString("increase", "")
                countIncrease = increase.toString().toInt()
                countTotal -= countIncrease
                txtCountTotal.text = countTotal.toString()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun add(view: View) {
        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val increase = pref.getString("increase", "")
        countIncrease = increase.toString().toInt()
        countTotal += countIncrease
        txtCountTotal.text = countTotal.toString()
    }

    fun subtract(view: View) {
        val pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val increase = pref.getString("increase", "")
        countIncrease = increase.toString().toInt()
        countTotal -= countIncrease
        txtCountTotal.text = countTotal.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return when (item.itemId){
            R.id.menuReset -> {
                txtCountTotal.text = "0"
                countTotal  = 0
                true
            }
            R.id.menuSettings -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                true
            }
            R.id.menuAbout -> {
                Toast.makeText(this, "By Wrapped in Plastic Software", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}