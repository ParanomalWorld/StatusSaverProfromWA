package com.divineengine.statussaverprofromwa.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.divineengine.statussaverprofromwa.R
import com.divineengine.statussaverprofromwa.databinding.FragmentSettingsBinding
import com.divineengine.statussaverprofromwa.models.SettingsModel
import com.divineengine.statussaverprofromwa.views.activities.MainActivity
import com.divineengine.statussaverprofromwa.views.adapters.SettingsAdapter

class FragmentSettings : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val list = ArrayList<SettingsModel>()
    private val adapter by lazy {
        SettingsAdapter(list, requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSettingsList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.settingsRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle back press to navigate to home screen
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Check if the fragment is already added to the activity
                val activity = requireActivity()
                if (activity is MainActivity) {
                    activity.goToHomePage() // Make sure this method exists in your activity
                }
            }
        })
    }

    private fun setupSettingsList() {
        list.apply {
            add(SettingsModel("How to use", "Know how to download statuses"))
            add(SettingsModel("Save in Folder", "/internalstorage/Documents/${getString(R.string.app_name)}"))
            add(SettingsModel("Disclaimer", "Read Our Disclaimer"))
            add(SettingsModel("Privacy Policy", "Read Our Terms & Conditions"))
            add(SettingsModel("Share", "Sharing is caring"))
            add(SettingsModel("Rate Us", "Please support our work by rating on PlayStore"))
        }
    }

    // If you want to handle navigation to the home page inside the activity
    private fun navigateToHome() {
        val activity = activity
        if (activity != null) {
            // Navigate to the home activity directly, or use your navigation setup
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}

