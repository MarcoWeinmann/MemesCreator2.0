package de.syntax_institut.funappsvorlage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import de.syntax_institut.funappsvorlage.adapter.MemeAdapter
import de.syntax_institut.funappsvorlage.databinding.FragmentMemesBinding

class MemesFragment : Fragment() {

    // Hier wird das ViewModel, in dem die Logik stattfindet, geholt
    private val viewModel: MemesViewModel by viewModels()

    // Das binding für das QuizFragment wird deklariert
    private lateinit var binding: FragmentMemesBinding

    /**
     * Lifecycle Funktion onCreateView
     * Hier wird das binding initialisiert und das Layout gebaut
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Hier wird der Informationsabruf gestartet
        viewModel.loadData()

        binding = FragmentMemesBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Lifecycle Funktion onViewCreated
     * Hier werden die Elemente eingerichtet und z.B. onClickListener gesetzt
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bei einem Klick auf btnRefresh sollen die Informationen erneut abgerufen werden
        binding.btnRefresh.setOnClickListener {
            viewModel.loadData()
        }

        // Verbesserte Performance bei fixer Listengröße
        binding.rvMemes.setHasFixedSize(true)

        // Der SnapHelper sorgt dafür,
        // dass die RecyclerView immer auf das aktuelle List Item springt
        val helper: SnapHelper = PagerSnapHelper()
        helper.attachToRecyclerView(binding.rvMemes)

        // Die Variable memes wird beobachtet und bei einer Änderung wird der Adapter der
        // Recyclerview neu gesetzt.
        viewModel.memes.observe(
            viewLifecycleOwner
        ) {
            binding.rvMemes.adapter = MemeAdapter(it)
        }
    }
}
