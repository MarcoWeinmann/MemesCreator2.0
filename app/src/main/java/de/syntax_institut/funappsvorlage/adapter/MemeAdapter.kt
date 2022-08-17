package de.syntax_institut.funappsvorlage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import de.syntax_institut.funappsvorlage.R
import de.syntax_institut.funappsvorlage.data.datamodels.Meme

/**
 * Diese Klasse organisiert mithilfe der ViewHolder Klasse das Recycling
 */
class MemeAdapter(
    private val dataset: List<Meme>
) : RecyclerView.Adapter<MemeAdapter.ItemViewHolder>() {

    /**
     * der ViewHolder umfasst die View uns stellt einen Listeneintrag dar
     */
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivMeme: ImageView = itemView.findViewById(R.id.ivMeme)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val btnSave: Button = itemView.findViewById(R.id.btnSave)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    /**
     * hier werden neue ViewHolder erstellt
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_meme, parent, false)

        return ItemViewHolder(itemLayout)
    }

    /**
     * hier findet der Recyclingprozess statt
     * die vom ViewHolder bereitgestellten Parameter erhalten die Information des Listeneintrags
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // hole das memeItem aus dem dataset
        val meme = dataset[position]

        // baue eine URI aus der Bild URL
        val imgUri = meme.url.toUri().buildUpon().scheme("https").build()

        // lade das Bild mithilfe der URI in die ImageView und runde die Ecken ab
        holder.ivMeme.load(imgUri) {
            error(R.drawable.ic_broken_image)
            transformations(RoundedCornersTransformation(10f))
        }
        // Lade den Titel aus dem memeItem in das XML Element
        holder.tvTitle.text = meme.name
        // Setze einen Click Listener auf btnSave,
        // der den aktuellen Titel in das meme Objekt speichert
        holder.btnSave.setOnClickListener {
            meme.name = holder.tvTitle.text.toString()
        }
    }

    /**
     * damit der LayoutManager weiß, wie lang die Liste ist
     */
    override fun getItemCount(): Int {
        return dataset.size
    }
}
