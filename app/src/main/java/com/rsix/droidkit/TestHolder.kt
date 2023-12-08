package com.rsix.droidkit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import com.rsix.annotation.AdapterHolder
import com.rsix.annotation.LayoutProvider
import com.rsix.droidkit.databinding.RecyclerItemBinding
import com.rsix.library.DefaultListAdapter
import com.rsix.library.DefaultViewTypeAdapter

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewType = "test"
)
class TestHolder(private val composeView: ComposeView) : com.rsix.library.BaseViewTypeHolder<TestBean>(composeView) {

    override fun onBind(data: TestBean) {
        composeView.setContent {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.content,
                textAlign = TextAlign.Center
            )
        }
    }
}

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewType = "test1"
)
class TestHolder1(private val composeView: ComposeView) :
    com.rsix.library.BaseViewTypeHolder<TestBean1>(composeView) {

    override fun onBind(data: TestBean1) {
        composeView.setContent {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.content,
                textAlign = TextAlign.Center
            )
        }
    }
}

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewType = "test2"
)
class TestHolder2(private val composeView: ComposeView) :
    com.rsix.library.BaseViewTypeHolder<TestBean2>(composeView) {
    override fun onBind(data: TestBean2) {
        composeView.setContent {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.content,
                textAlign = TextAlign.Center
            )
        }
    }
}

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewType = "binding",
    layoutProvider = TestHolder3.HolderLayoutProvider::class
)
class TestHolder3(itemView: View) :
    com.rsix.library.BaseViewTypeHolder<TestBean>(itemView) {
    private val binding: RecyclerItemBinding = RecyclerItemBinding.bind(itemView)

    class HolderLayoutProvider() : LayoutProvider {
        override fun getLayoutView(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

    }

    override fun onBind(data: TestBean) {
        binding.rvItemTv.textSize = 15f
        binding.rvItemTv.text = data.content
    }

}
