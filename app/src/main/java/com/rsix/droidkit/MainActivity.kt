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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroidKitTheme {
                AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
                    RecyclerView(context).apply {
                        adapter = TestAdapter().apply {
                            setItemList(
                                mutableListOf(
                                    TestBean(
                                        "test",
                                        "我是类型a",1
                                    ),
                                    TestBean1("test1", "我是类型b",2),
                                    TestBean2("test2", "我是类型c",3),
                                    TestBean("a","我是binding类型",4)
                                )
                            )
                        }
                        layoutManager = LinearLayoutManager(context)
                    }
                })
            }
        }
    }
}