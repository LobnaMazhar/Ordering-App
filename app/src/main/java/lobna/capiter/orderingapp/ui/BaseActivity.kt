package lobna.capiter.orderingapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import lobna.capiter.orderingapp.preferences.ConfigurationFile

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ConfigurationFile.setCurrentLanguage(newBase, "ar"))
    }
}