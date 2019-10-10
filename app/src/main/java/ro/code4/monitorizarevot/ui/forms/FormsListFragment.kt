package ro.code4.monitorizarevot.ui.forms

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_forms.*
import org.koin.android.viewmodel.ext.android.getSharedViewModel
import ro.code4.monitorizarevot.R
import ro.code4.monitorizarevot.adapters.FormDelegationAdapter
import ro.code4.monitorizarevot.ui.base.BaseFragment


class FormsListFragment : BaseFragment<FormsViewModel>() {

    companion object {
        val TAG = FormsListFragment::class.java.simpleName
    }

    override val layout: Int
        get() = R.layout.fragment_forms
    override lateinit var viewModel: FormsViewModel
    private val formAdapter: FormDelegationAdapter by lazy {
        FormDelegationAdapter(
            viewModel::selectForm
        ) {
            viewModel.selectedNotes()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = getSharedViewModel(from = { parentFragment!! })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.forms().observe(this, Observer {
            formAdapter.items = ArrayList(it)
        })
        viewModel.syncVisibility().observe(this, Observer {
            syncGroup.visibility = it
        })
        syncButton.setOnClickListener {
            viewModel.sync()
        }
        formsList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = formAdapter
            addItemDecoration(
                HorizontalDividerItemDecoration.Builder(activity)
                    .color(Color.TRANSPARENT)
                    .sizeResId(R.dimen.small_margin).build()
            )
        }

    }
}