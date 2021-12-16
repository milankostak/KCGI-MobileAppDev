package edu.kcg.mobile.ads

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

// Remove the line below after defining your own ad unit ID.
private const val TOAST_TEXT = "Test ads are being shown. " +
        "To show live ads, replace the ad unit ID in res/values/strings.xml " +
        "with your own ad unit ID."

class MainActivity : AppCompatActivity() {

    private var currentLevel: Int = 0
    private var interstitialAd: InterstitialAd? = null
    private lateinit var nextLevelButton: Button
    private lateinit var levelTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the next level button, which tries to show an interstitial when clicked.
        nextLevelButton = findViewById(R.id.next_level_button)
        nextLevelButton.isEnabled = false
        nextLevelButton.setOnClickListener { showInterstitial() }

        levelTextView = findViewById(R.id.level)
        // Create the text view to show the level number.
        currentLevel = 1

        // Create the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        loadInterstitial()

        loadBannerAd()

        // Toasts the test ad message on the screen. Remove this after defining your own ad unit ID.
        Toast.makeText(this, TOAST_TEXT, Toast.LENGTH_LONG).show()
    }

    private fun loadBannerAd() {
        MobileAds.initialize(this) {}
        val mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        mAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(error: LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem) =
//            when (item.itemId) {
//                R.id.action_settings -> true
//                else -> super.onOptionsItemSelected(item)
//            }

    private fun showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (interstitialAd != null) {
            setFullScreenCallback()
            interstitialAd?.show(this)
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
            goToNextLevel()
        }
    }

    private fun setFullScreenCallback() {
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                goToNextLevel()
            }

//            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
//            }
//
//            override fun onAdShowedFullScreenContent() {
//            }
        }
    }

    private fun loadInterstitial() {
        // Disable the next level button and load the ad.
        nextLevelButton.isEnabled = false
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            getString(R.string.interstitial_ad_unit_id),
//            getString(R.string.interstitial_ad_unit_id_video),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    nextLevelButton.isEnabled = true
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                    nextLevelButton.isEnabled = true
                }
            }
        )
    }

    private fun goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        levelTextView.text = getString(R.string.level_x, ++currentLevel)
        loadInterstitial()
    }
}