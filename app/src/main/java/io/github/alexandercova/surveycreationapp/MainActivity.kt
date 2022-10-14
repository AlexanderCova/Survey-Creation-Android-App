package io.github.alexandercova.surveycreationapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.alexandercova.surveycreationapp.Adapters.MyAdapter



class MainActivity : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        tabLayout.addTab(tabLayout.newTab().setText("Surveys"))
        tabLayout.addTab(tabLayout.newTab().setText("Discover"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = MyAdapter(this, supportFragmentManager,
            tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        val intent = intent


        val addSurveyButton = findViewById<Button>(R.id.addSurveyButton)
        val settingsButton = findViewById<Button>(R.id.settingsButton)
        val fabMenu = findViewById<FloatingActionButton>(R.id.fabMenu)
        val tvClose = findViewById<Button>(R.id.tvClose)

        addSurveyButton.setOnClickListener {
            val intent = Intent(this, AddSurveyActivity::class.java)
            startActivity(intent)
        }

        if (intent.getBooleanExtra("created", false)) {
            popup(intent.getStringExtra("id")!!)
        }


        fabMenu.setOnClickListener {
            fabMenu.isExpanded = true
        }
        tvClose.setOnClickListener {
            fabMenu.isExpanded = false
        }
    }

    fun popup(id: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Share Survey!!")
        builder.setMessage("Use this code: $id")

        builder.setNeutralButton("Copy Code") { dialog, which ->
            val myClipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

            val myClip = ClipData.newPlainText("Survey Code", id);
            myClipboard?.setPrimaryClip(myClip);

            Toast.makeText(applicationContext,
                "Code Copied", Toast.LENGTH_SHORT).show()
        }

        builder.show()
    }
}