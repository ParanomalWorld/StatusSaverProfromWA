package com.divineengine.statussaverprofromwa.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.divineengine.statussaverprofromwa.R
import com.divineengine.statussaverprofromwa.databinding.ItemImagePreviewBinding
import com.divineengine.statussaverprofromwa.databinding.ItemMediaBinding
import com.divineengine.statussaverprofromwa.models.MEDIA_TYPE_IMAGE
import com.divineengine.statussaverprofromwa.models.MediaModel
import com.divineengine.statussaverprofromwa.utils.saveStatus

class ImagePreviewAdapter (private val list: ArrayList<MediaModel>, val context: Context) :
    RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemImagePreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaModel: MediaModel) {
            binding.apply {
                Glide.with(context)
                    .load(mediaModel.pathUri.toUri())
                    .into(zoomableImageView)

                val downloadImage = if (mediaModel.isDownloaded) {
                    R.drawable.ic_downloaded
                } else {
                    R.drawable.ic_download
                }
                tools.statusDownload.setImageResource(downloadImage)



                tools.download.setOnClickListener {
                    val isDownloaded = context.saveStatus(mediaModel)
                    if (isDownloaded) {
                        // status is downloaded
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        mediaModel.isDownloaded = true
                        tools.statusDownload.setImageResource(R.drawable.ic_downloaded)
                    } else {
                        // unable to download status
                        Toast.makeText(context, "Unable to Save", Toast.LENGTH_SHORT).show()
                    }
                }
                tools.share.setOnClickListener {
                    if (mediaModel.isDownloaded) {
                        // Create a share intent
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_STREAM, mediaModel.pathUri.toUri())
                            type = "image/*"
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
                    } else {
                        Toast.makeText(context, "Please download the image before sharing", Toast.LENGTH_SHORT).show()
                    }
                }

                tools.repost.setOnClickListener {
                    if (mediaModel.isDownloaded) {
                        val repostIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_STREAM, mediaModel.pathUri.toUri())
                            type = "image/*"
                            `package` = "com.whatsapp"  // Set to WhatsApp's package name for direct repost
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        try {
                            context.startActivity(repostIntent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "Please download the image before reposting", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImagePreviewAdapter.ViewHolder {
        return ViewHolder(ItemImagePreviewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ImagePreviewAdapter.ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)
    }

    override fun getItemCount() = list.size

}











