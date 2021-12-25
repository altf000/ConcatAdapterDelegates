# Concat Adapter Delegates

A simple implementation of the Adapter Delegates pattern based on the Kotlin Flows and ConcatAdapter

## Features

- One RecyclerView supported an unlimited lists with a different view holders
- Supported Kotlin Flows
- Supported Jetpack Paging 3
- Supported ViewBinding

## How to use?

### 1. Create delegate item classes with a identifier and any data

##### HeaderItem:

```kotlin
data class HeaderItem(val text: String) : DItem() {
    override val identifier = text.hashCode()
    override val data = text
}
```

##### ContentItem:

```kotlin
data class ContentItem(val text: String) : DItem() {
    override val identifier = text.hashCode()
    override val data = text
}

```

##### FooterItem:

```kotlin
object FooterItem : DItem() {
    override val identifier = FooterItem::javaClass.hashCode()
    override val data: Any? = null
}

```

### 2. Create delegate adapters and bind them with delegate items

##### HeaderDelegate:

```kotlin
class HeaderAdapterDelegate : AdapterDelegate<HeaderItem, LayoutItemHeaderBinding>() {

    override val viewType = R.layout.layout_item_header
    override val itemClass = HeaderItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: HeaderItem, binding: LayoutItemHeaderBinding, position: Int, payloads: List<Any>) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemHeaderBinding) {
        binding.text.text = null
    }
}
```

##### ContentDelegate:

```kotlin
class ContentAdapterDelegate1 : AdapterDelegate<ContentItem, LayoutItemContent1Binding>() {

    override val viewType = R.layout.layout_item_content1
    override val itemClass = ContentItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemContent1Binding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: ContentItem, binding: LayoutItemContent1Binding, position: Int, payloads: List<Any>) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemContent1Binding) {
        binding.text.text = null
    }
}
```

> Note: Also create ContentAdapterDelegate2 and ContentAdapterDelegate3 which will display different view holders for the same class

##### FooterDelegate:

```kotlin
class FooterAdapterDelegate : AdapterDelegate<FooterItem, LayoutItemFooterBinding>() {

    override val viewType = R.layout.layout_item_footer
    override val itemClass = FooterItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: FooterItem, binding: LayoutItemFooterBinding, position: Int, payloads: List<Any>) {}

    override fun onUnbind(binding: LayoutItemFooterBinding) {}
}
```

### 3. Create ContentAdapterDelegateSelector which will return different delegates for the Content class depending on our logic

```kotlin
class ContentAdapterDelegateSelector : AdapterDelegatesSelector<ContentItem>() {

    override val itemClass = ContentItem::class.java

    override fun getDelegate(item: ContentItem) = when {
        item.text.contains("1") -> ContentAdapterDelegate1()
        item.text.contains("2") -> ContentAdapterDelegate2()
        item.text.contains("3") -> ContentAdapterDelegate3()
        else -> FallbackAdapterDelegate()
    }
}
```

### 4. Declare an lists in the ViewModel

```kotlin
class MainViewModel : ViewModel() {

    private val _list1 = MutableStateFlow(
        mutableListOf<DItem>().apply {
            add(HeaderItem("List 1"))
            add(ContentItem("1"))
            add(FooterItem)
        }
    )
    val list1 = _list1.asStateFlow()

    private val _list2 = MutableStateFlow(
        mutableListOf<DItem>().apply {
            add(HeaderItem("List 2"))
            repeat(3) { add(ContentItem("${it + 1}")) }
            add(FooterItem)
        }
    )
    val list2 = _list2.asStateFlow()

    private val _list3 = MutableStateFlow<List<DItem>>(
        value = mutableListOf<DItem>().apply {
            add(HeaderItem("List 3"))
            add(ContentItem("3"))
            add(FooterItem)
        }
    )
    val list3 = _list3.asStateFlow()
}
```

### 5. To use a Jetpack Paging we need to create a PagingDataSource and declare a PagingData flow in the ViewModel

```kotlin
class PagingDataSource : PagingSource<Int, DItem>() {

    private var page: Int = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DItem> {
        return LoadResult.Page(
            data = mutableListOf<DItem>().apply {
                if (page == 0) {
                    add(HeaderItem("PagingList"))
                }
                (0 until params.loadSize).forEach {
                    add(ContentItem("page = ${page}, item = $it"))
                }
            },
            prevKey = null,
            nextKey = ++page
        )
    }

    override fun getRefreshKey(state: PagingState<Int, DItem>) = 0
}
```

##### And declare PagingData flow in the ViewModel

```kotlin
    val pager = Pager(
    config = PagingConfig(
        pageSize = 20
    )
) { PagingDataSource() }.flow
```

### 6. Finally create the adapter, register delegates and and bind it to the RecyclerView

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.recyclerView.adapter = createAdapter(
        viewLifecycleOwner,
        binding.recyclerView,
        delegateSelector {
            addDelegate(HeaderAdapterDelegate())
            addDelegateSelector(ContentAdapterDelegateSelector())
            addDelegate(FooterAdapterDelegate())
        }
    ) {
        addAdapters(
            viewModel.list1,
            viewModel.list2,
            viewModel.list3
        )
        addPagingAdapter(viewModel.pager)
    }
}
```

### 7. Result

![N|Solid](https://cdn1.savepice.ru/uploads/2021/12/25/8e918caa1da6b936b970c89b38be971a-full.png)