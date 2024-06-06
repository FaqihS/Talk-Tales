package com.capstone.talktales.ui.storydetail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.capstone.talktales.R
import com.capstone.talktales.data.model.Story
import com.capstone.talktales.data.remote.response.DetailStoryResponse
import com.capstone.talktales.data.remote.response.ResponseResult
import com.capstone.talktales.databinding.ActivityStoryDetailBinding
import com.capstone.talktales.factory.UserViewModelFactory

class StoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryDetailBinding
    private val viewModel by viewModels<StoryDetailViewModel> {
        UserViewModelFactory.getInstance(this)
    }
    private lateinit var storyId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        storyId = intent.getStringExtra(EXTRA_STORY_ID).toString()

        viewModel.getStoryDetail(storyId).observe(this) {
            handleStoryDetail(it)
        }

        viewModel.pageTitle.observe(this) {
            binding.pageTitle.text = it
        }

    }

    private fun handleStoryDetail(result: ResponseResult<DetailStoryResponse>) {
        when (result) {
            is ResponseResult.Error -> {
                // Todo: Handle Error
            }

            is ResponseResult.Loading -> {
                // Todo: Handle Loading
            }
            is ResponseResult.Success -> {
                handleStoryDetailSuccess(result.data)
            }
        }
    }

    private fun handleStoryDetailSuccess(data: DetailStoryResponse) {
       // todo: hide loading
        // Todo: hide error
        showGlossary(data.story)
        showTitleAndThumbnail(data.story)


    }

    private fun showTitleAndThumbnail(story: Story) {
        with(binding){
            storyBanner.load(story.imgUrl)
            title.text = story.title
            province.text = story.city
        }
    }

    private fun showGlossary(story: Story) {
        val glossaryFragment = GlossaryFragment.newInstance(story)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, glossaryFragment)
            .commit()
    }

    companion object {
        const val EXTRA_STORY_ID = "story-id"
    }
}