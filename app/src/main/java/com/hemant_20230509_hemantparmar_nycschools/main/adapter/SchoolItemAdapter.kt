package com.hemant_20230509_hemantparmar_nycschools.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hemant_20230509_hemantparmar_nycschools.data.entity.School
import com.hemant_20230509_hemantparmar_nycschools.databinding.SchoolItemBinding


/*
    RecyclerView Adapter:
    Display each school with title
 */
class SchoolItemAdapter(
    private val dataSet: List<School>,
    private val schoolClickListener: SchoolClickListener
): RecyclerView.Adapter<SchoolItemAdapter.ViewHolder>() {

    //region view binding
    class ViewHolder(
        val binding: SchoolItemBinding
    ): RecyclerView.ViewHolder(binding.root)
    //endregion view binding

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder =
        ViewHolder(
            SchoolItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    // Replace the contents of a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schoolName = holder.binding.schoolName
        schoolName.text = dataSet[position].schoolName

        holder.binding.schoolItem.setOnClickListener {
//            schoolListener.onClick(dataSet[position].dbn)
            schoolClickListener.onSchool_item_click(dataSet[position])
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
