package com.example.anax.animationtesting

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.anax.animationtesting.fragments.*
import com.example.anax.animationtesting.fragments.FlingListFragment.FlingType.*
import com.example.anax.animationtesting.fragments.SnapbackFragment.AnimationType.TYPE_PHYSICS_BASED
import com.example.anax.animationtesting.fragments.SnapbackFragment.AnimationType.TYPE_STANDARD
import com.example.anax.animationtesting.fragments.TargetValueChangeFragment.TargetValueAnimationType.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        onNavigationItemSelected(nav_view.menu.getItem(0))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.standard_snapback_showcase -> {
                val fragment = SnapbackFragment.newInstance(TYPE_STANDARD)
                showFragment(fragment)
            }
            R.id.spring_snapback_showcase -> {
                val fragment = SnapbackFragment.newInstance(TYPE_PHYSICS_BASED)
                showFragment(fragment)
            }
            R.id.translation_with_rotation_showcase -> {
                showFragment(TranslateRotateListFragment())
            }
            R.id.tracking_showcase -> {
                showFragment(TrackingFragment())
            }
            R.id.fling_showcase -> {
                val fragment = FlingListFragment.newInstance(WITHOUT_BOUNCE_BACK)
                showFragment(fragment)
            }
            R.id.fling_bounceback_showcase -> {
                val fragment = FlingListFragment.newInstance(WITH_BOUNCE_BACK)
                showFragment(fragment)
            }
            R.id.scale_showcase -> {
                showFragment(ScalePopFragment())
            }
            R.id.target_value_change_showcase -> {
                val fragment = TargetValueChangeFragment.newInstance(STANDARD)
                showFragment(fragment)
            }
            R.id.target_value_change_physics_showcase -> {
                val fragment = TargetValueChangeFragment.newInstance(PHYSICS_BASED)
                showFragment(fragment)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showFragment(fragment: Fragment) {
        replaceFragment(fragment, R.id.content_layout)
    }
}
