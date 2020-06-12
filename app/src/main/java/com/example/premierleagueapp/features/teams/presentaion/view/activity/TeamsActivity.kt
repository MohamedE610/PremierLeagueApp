package com.example.premierleagueapp.features.teams.presentaion.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.premierleagueapp.R
import com.example.premierleagueapp.features.teams.allteams.presentation.view.fragment.AllTeamsFragment
import com.example.premierleagueapp.features.teams.favouriteteams.presentation.view.FavouriteTeamsFragment
import com.example.premierleagueapp.features.teams.presentaion.view.adapter.TeamsViewPagerAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_teams.*
import javax.inject.Inject

class TeamsActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_teams)
        initViews()
    }

    private fun initViews() {
        setUpTeamsViewPager()
        tlTeamsCategory.setupWithViewPager(vpTeams)
        setTeamsTabLayoutIcons()
    }

    private fun setUpTeamsViewPager() {
        val viewPagerAdapter = TeamsViewPagerAdapter(supportFragmentManager)
        val allTeamsFragment = AllTeamsFragment.newInstance()
        val favouriteTeamsFragment = FavouriteTeamsFragment.newInstance()
        viewPagerAdapter.fragments.apply {
            add(allTeamsFragment)
            add(favouriteTeamsFragment)
        }
        val fragmentTitles = resources.getStringArray(R.array.teams_categories)
        viewPagerAdapter.titles.addAll(fragmentTitles)
        vpTeams.adapter = viewPagerAdapter
    }

    private fun setTeamsTabLayoutIcons() {
        val allTeamsTab = tlTeamsCategory.getTabAt(0)
        allTeamsTab?.setIcon(R.drawable.ic_all_teams)

        val favouriteTeamsTab = tlTeamsCategory.getTabAt(1)
        favouriteTeamsTab?.setIcon(R.drawable.ic_favourite)
    }
}