# DroidKit

自用工具库

## 功能：

- [x] 多类型Adapter组装 (已完成，优化中)
- [ ] mvi，mvvm封装
- [ ] Base层封装
- [ ] 页面快速搭起框架
- [ ] others

## 使用介绍：

### AdapterHolder使用姿势：

首先创建Bean类，继承自BaseViewTypeItem：

```kotlin
data class TestBean(
    override val viewType: String,
    val content:String, override val id: Int
):BaseViewTypeItem()
```

实现BaseViewTypeAdapter：

```kotlin
class TestAdapter(
    override val holderFactory: HolderFactory = TestAdapterFactory.instance
) :
    BaseViewTypeAdapter<BaseViewTypeItem>() {
        //其他逻辑
        //....
}
```

实现BaseViewTypeHolder，给adapter注解传参：

```kotlin
@AdapterHolder(adapters = [TestAdapter::class],viewType = "test")
class TestHolder(private val composeView: ComposeView) : BaseViewTypeHolder<TestBean>(composeView) {

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
```

holder的rootView有三种，普通xml的itemView，composeView，viewBinding。以上是需要composeView的实现方式。当你需要itemView时，请提供LayoutProvider：

```kotlin
@AdapterHolder(
    adapters = [TestAdapter::class],
    viewType = "text_type",
    layoutProvider = TestHolder.HolderLayoutProvider::class
)
class TestHolder(itemView: View) :
    BaseViewTypeHolder<TestBean>(itemView) {
    private val mTextView: TextView = itemView.findViewById(R.id.rv_item_tv)

    class HolderLayoutProvider() : LayoutProvider {
        override fun getLayoutView(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)

    }

    override fun onBind(data: TestBean) {
        mTextView.text = data.content
    }

}
```

需要注意的是内部会调用LayoutProvider的无参构造请保证其实现类有一个无参构造函数，LayoutProvider作为内部类外部类都无关系，只需有一个无参构造函数即可。如果你需要viewBinding，只需将其绑定到itemView。因为内部使用了ksp生成代码，无法为你提供在kapt生成的viewBinding。使用方式：

```kotlin
@AdapterHolder(
    adapters = [TestAdapter::class],
    viewType = "text_type",
    layoutProvider = TestHolder.HolderLayoutProvider::class
)
class TestHolder(itemView: View) :
    BaseViewTypeHolder<TestBean>(itemView) {
    private val binding: RecyclerItemBinding = RecyclerItemBinding.bind(itemView)

    class HolderLayoutProvider() : LayoutProvider {
        override fun getLayoutView(parent: ViewGroup): View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
    }

    override fun onBind(data: TestBean) {
        binding.rvItemTv.text = data.content
    }

}
```

然后在Recyclerview层面（这里就暂且使用compose作为示例）：

```kotlin
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
                                    TestBean("test","我是item1",1),
                                    TestBean("test", "我是item2",2),
                                    TestBean("text_type", "我是item3",3),
                                    TestBean("text_type","我是item4",4)
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
```

当然这里的dataList可以是任何BaseViewTypeItem的子类。每个holder可以有自己的BaseViewTypeItem实现类。

AdapterHolder支持一个Holder对多个Adapter，将其适用的Adapter全部传入adapters即可。

```kotlin
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class AdapterHolder (
    val viewType:String = "",
    val adapters:Array<KClass<out RecyclerView.Adapter<out RecyclerView.ViewHolder>>> = [],
    val layoutProvider:KClass<out LayoutProvider> = LayoutProvider::class,
)
```

## TODO

..........
