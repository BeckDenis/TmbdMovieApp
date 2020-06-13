package com.denisbeck.tmdbmovieapp.screens.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.denisbeck.tmdbmovieapp.R
import com.denisbeck.tmdbmovieapp.extensions.insertImageW185
import com.denisbeck.tmdbmovieapp.models.Credit
import kotlinx.android.synthetic.main.item_credits.view.*

class CreditsAdapter(private val credits: List<Credit>) : RecyclerView.Adapter<CreditsAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_credits, null, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = credits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = credits[position]
        holder.view.run {
//            credits_name.text = person.name
            credits_name.text = "James Mangold"
            credits_department.text = person.caption
            if (person.profile_path.isNullOrBlank()) {
                credits_photo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.credits_placeholder))
            } else {
                credits_photo.insertImageW185(person.profile_path)
            }
        }
    }
}
