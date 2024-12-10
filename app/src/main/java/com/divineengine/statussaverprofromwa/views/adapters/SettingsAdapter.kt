package com.divineengine.statussaverprofromwa.views.adapters

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import com.divineengine.statussaverprofromwa.R
import com.divineengine.statussaverprofromwa.databinding.DialogGuideBinding
import com.divineengine.statussaverprofromwa.databinding.ItemSettingsBinding
import com.divineengine.statussaverprofromwa.models.SettingsModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsAdapter(var list: ArrayList<SettingsModel>, var context: Context) :
    RecyclerView.Adapter<SettingsAdapter.viewHolder>() {

    inner class viewHolder(var binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: SettingsModel, position: Int) {
            binding.apply {
                settingsTitle.text = model.title
                settingsDesc.text = model.desc

                root.setOnClickListener {
                    when (position) {
                        0 -> {
                            // Guide dialog for the first item
                            val dialog = Dialog(context)
                            val dialogBinding =
                                DialogGuideBinding.inflate((context as Activity).layoutInflater)
                            dialogBinding.okayBtn.setOnClickListener {
                                dialog.dismiss()
                            }
                            dialog.setContentView(dialogBinding.root)
                            dialog.window?.setLayout(
                                ActionBar.LayoutParams.MATCH_PARENT,
                                ActionBar.LayoutParams.WRAP_CONTENT
                            )
                            dialog.show()
                        }



                        2 -> {
                            // Disclaimer dialog
                            MaterialAlertDialogBuilder(context).apply {
                                setTitle("Disclaimer")
                                setMessage(context.getString(R.string.disclaimer_message))
                                setPositiveButton("Okay", null)
                                show()
                            }
                        }

                        3 -> {
                            // Privacy Policy - Load from assets
                            val webView = WebView(context).apply {
                                settings.javaScriptEnabled = true
                                webViewClient = WebViewClient()
                                loadUrl("file:///android_asset/privacy_policy.html")
                            }

                            MaterialAlertDialogBuilder(context)
                                .setTitle("Privacy Policy")
                                .setView(webView)
                                .setPositiveButton("Close", null)
                                .show()
                        }

                        4 -> {
                            // Share app link
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
                                putExtra(Intent.EXTRA_TEXT, "My App is so cool! Please download it: https://play.google.com/store/apps/details?id=${context.packageName}")
                                context.startActivity(this)
                            }
                        }

                        5 -> {
                            // Rate app on Play Store
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
                            ).apply {
                                context.startActivity(this)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(ItemSettingsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(model = list[position], position)
    }
}
