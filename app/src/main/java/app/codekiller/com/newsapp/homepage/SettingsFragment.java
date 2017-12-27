package app.codekiller.com.newsapp.homepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import app.codekiller.com.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements SettingsContract.View{

    private Toolbar toolbar;
    private SettingsContract.Presenter presenter;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preference_fragment);
        initViews(getView());

        findPreference("auto_change_theme").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.setAutoChangeTheme(preference);
                return false;
            }
        });

        findPreference("clear_glide_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.cleanGlideCache();
                return false;
            }
        });
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initViews(View view) {
        toolbar = getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public void showCleanGlideCacheDone() {
        Toast.makeText(getContext(), R.string.glide_cache_clear_done, Toast.LENGTH_SHORT).show();
    }
}
