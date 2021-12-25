# Concat Adapter Delegates

A simple implementation of the Adapter Delegates pattern based on the ConcatAdapter

## Features

- One RecyclerView supported an unlimited lists with a different view holders
- Supported Kotlin Flows
- Supported Jetpack Paging 3
- Supported ViewBinding

## How to use?

### 1. Create delegate item classes with identifier and any data
```kotlin
data class HeaderItem(val text: String) : DItem() {
    override val identifier: Any = UUID.randomUUID().toString()
    override val data: Any = text
}
```
```kotlin
data class ContentItem(val text: String) : DItem() {
    override val identifier: Any = UUID.randomUUID().toString()
    override val data: Any = text
}
```
### 2. Create delegate adapters and bind them with delegate items
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
```kotlin
class ContentAdapterDelegate : AdapterDelegate<ContentItem, LayoutItemContentBinding>() {

    override val viewType = R.layout.layout_item_content
    override val itemClass = ContentItem::class.java

    override fun createBinding(parent: ViewGroup) =
        LayoutItemContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun onBind(item: ContentItem, binding: LayoutItemContentBinding, position: Int, payloads: List<Any>) {
        binding.text.text = item.text
    }

    override fun onUnbind(binding: LayoutItemContentBinding) {
        binding.text.text = null
    }
}
```
### 3. Now we can declare an unlimited lists in the ViewModel
```kotlin
class MainViewModel : ViewModel() {

    private val _firstList = MutableStateFlow(
        listOf(
            HeaderItem("firstList, HeaderViewHolder"),
            ContentItem("firstList, ContentViewHolder")
        )
    )
    val firstList = _firstList.asStateFlow()

    private val _secondList = MutableStateFlow(
        listOf(
            HeaderItem("secondList, HeaderViewHolder"),
            ContentItem("secondList, ContentViewHolder"),
            HeaderItem("secondList, HeaderViewHolder"),
            ContentItem("secondList, ContentViewHolder")
        )
    )
    val secondList = _secondList.asStateFlow()

    private val _thirdList = MutableStateFlow(
        listOf(
            HeaderItem("thirdList, HeaderViewHolder"),
            ContentItem("thirdList, ContentViewHolder")
        )
    )
    val thirdList = _thirdList.asStateFlow()
}
```
### 4. Finally create the adapter and bind it to the RecyclerView
```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = createAdapter(
            viewLifecycleOwner,
            binding.recyclerView,
            delegateSelector {
                addDelegate(HeaderAdapterDelegate())
                addDelegate(ContentAdapterDelegate())
            }
        ) {
            addAdapters(
                viewModel.firstList,
                viewModel.secondList,
                viewModel.thirdList
            )
        }
    }
```
### 5. Result
![N|Solid](https://cdn1.savepice.ru/uploads/2021/12/25/8e918caa1da6b936b970c89b38be971a-full.png)