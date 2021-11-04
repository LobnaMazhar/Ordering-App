package lobna.capiter.orderingapp.preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import java.util.*

object ConfigurationFile {
    private val LANGUAGE_KEY = "languageKey"

    private var configFile: SharedPreferences? = null

    fun getCurrentLanguage(context: Context): String {
        initConfigSharedPreference(context)
        return configFile!!.getString(LANGUAGE_KEY, Locale.getDefault().language)
            ?: Locale.getDefault().language
    }

    fun setCurrentLanguage(mContext: Context, mLanguage: String): Context {
        var context = mContext
        initConfigSharedPreference(context)
        var language = mLanguage
        if (language.isNullOrBlank()) language = Locale.getDefault().language
        try {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = context.resources.configuration

            config.setLocale(locale)
            config.setLayoutDirection(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                context = context.createConfigurationContext(config)
            else
                context.resources.updateConfiguration(config, context.resources.displayMetrics)
        } catch (a: NullPointerException) {
            a.printStackTrace()
        } catch (a: RuntimeException) {
            a.printStackTrace()
        } finally {
            val editor = configFile!!.edit()
            editor.putString(LANGUAGE_KEY, language)
            editor.apply()

            return context
        }
    }

    private fun initConfigSharedPreference(context: Context) {
        configFile =
            context.getSharedPreferences("configFile", Context.MODE_PRIVATE)
    }
}