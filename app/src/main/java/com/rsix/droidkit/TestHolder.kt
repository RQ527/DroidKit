package com.rsix.droidkit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.rsix.annotation.AdapterHolder
import com.rsix.annotation.LayoutProvider
import com.rsix.droidkit.databinding.RecyclerItemBinding
import com.rsix.library.BaseBindingTypeHolder
import com.rsix.library.BaseViewTypeHolder
import com.rsix.library.DefaultListAdapter
import com.rsix.library.DefaultViewTypeAdapter

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewTypes = ["type","unknown"]
)
class TestHolder(private val composeView: ComposeView) : BaseViewTypeHolder<TestBean>(composeView) {

    override fun onBind(data: TestBean) {
        composeView.setContent {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.content,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewTypes = ["type1"]
)
class TestHolder1(private val composeView: ComposeView) :
    BaseViewTypeHolder<TestBean1>(composeView) {

    override fun onBind(data: TestBean1) {
        composeView.setContent {
            Column(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally), onClick = {
                        Toast.makeText(
                            App.mContext,
                            "点击按钮～",
                            Toast.LENGTH_SHORT
                        ).show()}
                ){
                    Text(text = data.btnContent)
                }
            }
        }
    }
}

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewTypes = ["type2"]
)
class TestHolder2(private val composeView: ComposeView) :
    BaseViewTypeHolder<TestBean2>(composeView) {
    override fun onBind(data: TestBean2) {
        composeView.setContent {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = data.title,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Text(text = data.subtitle,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp)
            }
        }
    }
}

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewTypes = ["type3"],
    layoutProvider = TestHolder3.HolderLayoutProvider::class
)
class TestHolder3(itemView: View) :
    BaseViewTypeHolder<TestBean3>(itemView) {

    private val mTitleTv: TextView = itemView.findViewById(R.id.rv_item_tv)
    private val mPictureIv: ImageView = itemView.findViewById(R.id.rv_item_iv)
    class HolderLayoutProvider() : LayoutProvider {
        override fun getLayoutView(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

    }

    override fun onBind(data: TestBean3) {
        mTitleTv.text = data.content
        mPictureIv.setImageResource(data.src)
    }

}
@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewTypes = ["type4"],
    layoutProvider = TestHolder4.HolderLayoutProvider::class
)
class TestHolder4(itemView: View) :
    BaseViewTypeHolder<TestBean4>(itemView) {
    private val binding: RecyclerItemBinding = RecyclerItemBinding.bind(itemView)

    class HolderLayoutProvider() : LayoutProvider {
        override fun getLayoutView(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

    }

    override fun onBind(data: TestBean4) {
        binding.rvItemTv.text = data.title
        binding.rvItemIv.setImageResource(data.drawableSrc)
    }
}

@AdapterHolder(
    adapters = [TestAdapter::class,DefaultListAdapter::class,DefaultViewTypeAdapter::class],
    viewTypes = ["unknownType"]
)
class TestHolder5(private val composeView: ComposeView) : BaseViewTypeHolder<TestBean>(composeView) {

    override fun onBind(data: TestBean) {
        composeView.setContent {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.content,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}

@AdapterHolder(
    adapters = [DefaultListAdapter::class],
    viewTypes = ["type5"],
    layoutProvider = TestHolder6.HolderLayoutProvider::class
)
class TestHolder6(itemView: View) :
    BaseBindingTypeHolder<RecyclerItemBinding,TestBean4>(itemView) {

    class HolderLayoutProvider() : LayoutProvider {
        override fun getLayoutView(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

    }

    override fun onBind(data: TestBean4) {
        binding.rvItemTv.text = data.title
        binding.rvItemIv.setImageResource(data.drawableSrc)
    }
}