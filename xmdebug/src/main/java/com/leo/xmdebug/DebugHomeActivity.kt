package com.leo.xmdebug

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.leo.baseui.mutiType.base.Items
import com.leo.baseui.ui.BaseActivity
import com.leo.litang.db.TestAccessor
import com.leo.xmdebug.home.DebugSwitchMultiProvider
import com.leo.xmdebug.home.model.DebugLocalDataMultiModel
import com.leo.xmdebug.home.model.DebugLogMultiModel
import com.leo.xmdebug.home.model.DebugSwitchMultiModel
import com.leo.xmdebug.home.provider.DebugLocalDataMultiProvider
import com.leo.xmdebug.home.provider.DebugLogMultiProvider
import com.leo.xmdebug.main.adapter.DebugListAdapter
import com.leo.xmdebug.utils.CommonStatusPrefManager
import com.leo.xmdebug.widget.floating.model.DebugBaseInfoMultiModel
import com.leo.xmdebug.widget.floating.provider.DebugBaseInfoMultiProvider


class DebugHomeActivity : BaseActivity() {
    private lateinit var contentRv: RecyclerView
    private lateinit var adapter: DebugListAdapter

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun init() {
        this.initToolBar()
        this.initViews()
        this.initData()
    }

    private fun initToolBar() {
        this.toolbar.setMiddleTitle("Debug")

    }

    private fun initViews() {
        this.contentRv = this.findViewById(R.id.content_rv)
    }

    private fun initData() {
        saveData()


        this.adapter = DebugListAdapter()
        this.adapter.register(DebugBaseInfoMultiModel::class.java, DebugBaseInfoMultiProvider(this))
        this.adapter.register(DebugSwitchMultiModel::class.java, DebugSwitchMultiProvider(this))
        this.adapter.register(DebugLocalDataMultiModel::class.java, DebugLocalDataMultiProvider(this))
        this.adapter.register(DebugLogMultiModel::class.java, DebugLogMultiProvider(this))
        this.contentRv.layoutManager = LinearLayoutManager(this)
        this.contentRv.adapter = this.adapter
        val items = Items()
        items.add(DebugBaseInfoMultiModel())
        items.add(DebugSwitchMultiModel())
        items.add(DebugLocalDataMultiModel())
        items.add(DebugLogMultiModel())
        this.adapter.items = items
    }

    private fun saveData() {
        CommonStatusPrefManager.saveOpenTime(this)
        TestAccessor(this).insert()
    }

    companion object {

        @JvmStatic
        fun enterActivity(context: Context) {
            val intent = Intent(context, DebugHomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}