package com.divineengine.statussaverprofromwa.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.divineengine.statussaverprofromwa.databinding.ActivityImagesPreviewBinding
import com.divineengine.statussaverprofromwa.models.MediaModel
import com.divineengine.statussaverprofromwa.utils.Constants
import com.divineengine.statussaverprofromwa.views.adapters.ImagePreviewAdapter

class ImagesPreview : AppCompatActivity() {
    private val activity = this
    private val binding by lazy {
        ActivityImagesPreviewBinding.inflate(layoutInflater)
    }
    lateinit var adapter: ImagePreviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            // Safely cast the retrieved list to ArrayList<MediaModel>
            val list = intent.getSerializableExtra(Constants.MEDIA_LIST_KEY) as? ArrayList<MediaModel>
            val scrollTo = intent.getIntExtra(Constants.MEDIA_SCROLL_KEY, 0)

            // Only proceed if the cast is successful
            if (list != null) {
                adapter = ImagePreviewAdapter(list, activity)
                imagesViewPager.adapter = adapter
                imagesViewPager.currentItem = scrollTo
            } else {
                // Handle the case where the list could not be retrieved or cast
                // Optionally, show a message or handle it according to your app’s needs
                finish()  // Optionally close the activity or provide feedback to the user
            }
        }
    }
}
