package com.example.premierleagueapp.features.teamdetails.presentation.view.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.premierleagueapp.R
import com.example.premierleagueapp.core.presentation.extensions.addFragment
import com.example.premierleagueapp.features.teamdetails.presentation.view.fragment.TeamDetailsFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class TeamDetailsActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        AndroidInjection.inject(this)
        addTeamDetailsFragment()
    }

    private fun addTeamDetailsFragment() {
        val teamId = intent.getIntExtra(TEAM_ID, -1)
        val teamName = intent.getStringExtra(TEAM_NAME) ?: ""
        val fragment = TeamDetailsFragment.newInstance(teamId = teamId, teamName = teamName)
        val containerId = android.R.id.content
        addFragment(fragment = fragment, frameId = containerId)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val TEAM_ID = "teamId"
        private const val TEAM_NAME = "teamName"
        fun getIntent(context: Context, teamId: Int, teamName: String): Intent {
            val intent = Intent(context, TeamDetailsActivity::class.java)
            intent.putExtra(TEAM_ID, teamId)
            intent.putExtra(TEAM_NAME, teamName)
            return intent
        }
    }
}