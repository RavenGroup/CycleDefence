/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.roompointssample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roompointssample.PointListAdapter.PointViewHolder
import com.example.prototype.R
import com.example.prototype.database.points_core.Point

class PointListAdapter : ListAdapter<Point, PointViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        return PointViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.toString())
    }

    class PointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pointItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            pointItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): PointViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return PointViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Point>() {
            override fun areItemsTheSame(oldItem: Point, newItem: Point): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Point, newItem: Point): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    }
}
