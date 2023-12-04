package com.rsix.droidkit


class TestAdapter(
    override val holderFactory: HolderFactory = TestAdapterFactory.instance
) :
    BaseViewTypeAdapter<BaseViewTypeItem>() {
}