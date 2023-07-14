package com.example.notetakingapp.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapp.Model.Note
import com.example.notetakingapp.R
import com.example.notetakingapp.View.MainActivity
import com.example.notetakingapp.View.UpdateActivity

class NoteAdapter(
    private val activity: MainActivity
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var notes: List<Note> = ArrayList()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.TitleTextview)
        val descriptionTextView: TextView = itemView.findViewById(R.id.DescriptionTextview)
        val cardView: CardView = itemView.findViewById(R.id.CardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)

        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote: Note = notes[position]
        holder.titleTextView.text = currentNote.title
        holder.descriptionTextView.text = currentNote.description

        holder.cardView.setOnClickListener {
            val intent = Intent(activity,  UpdateActivity::class.java)
                .putExtra("title", currentNote.title)
                .putExtra("description", currentNote.description)
                .putExtra("id", currentNote.id)

            activity.updateActivityResultLauncher.launch(intent)
        }
    }

    fun setNote(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }

    fun getNote(position: Int): Note{
        return notes[position]
    }
}