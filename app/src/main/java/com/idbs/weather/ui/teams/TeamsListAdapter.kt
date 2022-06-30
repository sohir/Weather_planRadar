package com.idbs.weather.ui.teams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.idbs.weather.R
import com.idbs.weather.databinding.TeamsListItemBinding


class TeamsListAdapter (private val actionsInterface:ComponentActionsClickListener ?=null) : RecyclerView.Adapter<TeamsListAdapter.ViewHolder>() {
    val Diff_CallBack = object : DiffUtil.ItemCallback<TeamsListResponsModel.Team> (){
        override fun areItemsTheSame(oldItem: TeamsListResponsModel.Team, newItem: TeamsListResponsModel.Team): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: TeamsListResponsModel.Team, newItem: TeamsListResponsModel.Team): Boolean {
            return oldItem==newItem
        }

    }
    private val differ = AsyncListDiffer(this,Diff_CallBack)
   fun updateDate(newList:List<TeamsListResponsModel.Team>){
       differ.submitList(newList)
   }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = DataBindingUtil.inflate<TeamsListItemBinding>(inflater,
            R.layout.teams_list_item, parent,false)
        return ViewHolder(rootView,actionsInterface)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.team = differ.currentList[position]
        holder.bind(differ.currentList[position])
    }

    class ViewHolder (var view:TeamsListItemBinding,private val characterActions: ComponentActionsClickListener?):
        RecyclerView.ViewHolder(view.root){
        fun bind(itemL1: TeamsListResponsModel.Team){
            view.team = itemL1
            view.listener = characterActions
            view.executePendingBindings()
        }
    }



    interface ComponentActionsClickListener {
        fun onItemClicked(v: View,item: TeamsListResponsModel.Team)

    }
}