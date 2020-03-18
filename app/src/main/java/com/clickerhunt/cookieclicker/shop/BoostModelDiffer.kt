package com.clickerhunt.cookieclicker.shop

import androidx.recyclerview.widget.DiffUtil
import com.clickerhunt.cookieclicker.model.BoostModel

class BoostModelDiffer : DiffUtil.ItemCallback<BoostModel>() {
    override fun areItemsTheSame(oldItem: BoostModel, newItem: BoostModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BoostModel, newItem: BoostModel) =
        oldItem == newItem
}