package com.rsix.droidkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rsix.droidkit.ui.theme.DroidKitTheme
import com.rsix.library.BaseViewTypeItem
import com.rsix.library.DefaultListAdapter
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroidKitTheme {
                AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
                    RecyclerView(context).apply {
                        adapter = DefaultListAdapter().apply {
                            val random = Random(System.currentTimeMillis())
                            val data = mutableListOf<BaseViewTypeItem>()
                            for (i in 0..200){
                                data.add(generateRandomBean(random,i))
                            }
                            submitList(data)
                        }
                        layoutManager = LinearLayoutManager(context)
                        addItemDecoration(DividerItemDecoration(this@MainActivity,R.drawable.divider))
                    }
                })
            }
        }
    }
}
fun generateRandomBean(random: Random,position:Int):BaseViewTypeItem{
    return when(random.nextInt(IntRange(0,5))){
        0->TestBean("type", "我是个文本item", position)
        1->TestBean1("type1", "我是个按钮，别点我", position)
        2->TestBean2("type2", "我是主标题",position,"我是子标题")
        3->TestBean3("type3", "我是xml布局，下面是张图片", position,R.drawable.ic_launcher_background)
        4->TestBean4("type4", position, "我是binding布局，下面是张图片",R.drawable.ic_launcher_background)
        5->TestBean("unknown","未知类型",position)
        else -> throw RuntimeException("unknown random value")
    }
}