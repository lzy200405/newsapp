package com.example.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.Button;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.newsapp.MainActivity;
import com.example.newsapp.R;
import com.example.newsapp.activity.LoginActivity;
import com.example.newsapp.utils.SharedPrefsUtils;

public class SettingsFragment extends Fragment {

    private Switch switchNight;
    private Button btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        switchNight = v.findViewById(R.id.switchNight);
        btnLogout = v.findViewById(R.id.btnLogout);

        boolean isNight = SharedPrefsUtils.isNightMode(getContext());
        switchNight.setChecked(isNight);

        int currentMode = AppCompatDelegate.getDefaultNightMode();
        int targetMode = isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        if (currentMode != targetMode) {
            AppCompatDelegate.setDefaultNightMode(targetMode);
        }

        switchNight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 保存用户选择的模式
            SharedPrefsUtils.setNightMode(requireContext(), isChecked);

            // 设置 App 的夜间模式
            AppCompatDelegate.setDefaultNightMode(isChecked ?
                    AppCompatDelegate.MODE_NIGHT_YES :
                    AppCompatDelegate.MODE_NIGHT_NO);

            // 重启 MainActivity，并清空回退栈
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });



        btnLogout.setOnClickListener(v1 -> {
            SharedPrefsUtils.saveLoginStatus(getContext(), false, true);
            startActivity(new Intent(getActivity(), LoginActivity.class));
            requireActivity().finish();
        });

        return v;
    }
}
