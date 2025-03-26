package com.project.dicodingplayground.practice_modul.sqlite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.dicodingplayground.databinding.ItemNoteBinding


class NoteAdapter(
    private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var listNotes = ArrayList<Note>()
        set(listNotes) {
            if (listNotes.isNotEmpty()) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
        }

    fun addItem(note: Note) {
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int, note: Note) {
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                binding.tvItemTitle.text = note.title
                binding.tvItemDate.text = note.date
                binding.tvItemDescription.text = note.description
                binding.cvItemNote.setOnClickListener {
                    onItemClickCallback.onItemClicked(note, adapterPosition)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Note?, position: Int?)
    }
}