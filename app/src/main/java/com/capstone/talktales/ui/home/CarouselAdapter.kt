package com.capstone.talktales.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import androidx.recyclerview.widget.RecyclerView
import coil.transform.RoundedCornersTransformation
import com.capstone.talktales.data.model.StoryItem
import com.capstone.talktales.databinding.CarouselItemBinding
import com.capstone.talktales.ui.storydetail.StoryDetailActivity

class CarouselAdapter(private val items: List<Any>) :
    RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {
    class ViewHolder(private val binding: CarouselItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carouselContent: Any) {
            when (carouselContent) {
                is StoryItem -> {
                    binding.carouselImageView.load(carouselContent.imgUrl) {
                        transformations(RoundedCornersTransformation(16f))
                    }
                    binding.root.setOnClickListener {
                        binding.root.context.startActivity(
                            Intent(binding.root.context, StoryDetailActivity::class.java)
                                .putExtra(StoryDetailActivity.EXTRA_STORY_ID, carouselContent.id)
                        )
                    }
                }

                is String -> {
                    binding.carouselImageView.load(carouselContent) {
                        transformations(RoundedCornersTransformation(16f))
                    }

                    // Todo Intent to tutorial
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}