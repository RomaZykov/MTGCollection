package com.andreikslpv.navigation.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.andreikslpv.common_impl.ActivityRequired
import com.andreikslpv.navigation.DestinationsProvider
import com.andreikslpv.navigation.R
import com.andreikslpv.navigation.databinding.ActivityMainBinding
import com.andreikslpv.navigation.presentation.navigation.NavComponentRouter
import com.andreikslpv.navigation.presentation.navigation.RouterHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RouterHolder {

    @Inject
    lateinit var navComponentRouterFactory: NavComponentRouter.Factory

    @Inject
    lateinit var destinationsProvider: DestinationsProvider

    @Inject
    lateinit var activityRequiredSet: Set<@JvmSuppressWildcards ActivityRequired>

    private val viewModel by viewModels<MainViewModel>()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navComponentRouter by lazy(LazyThreadSafetyMode.NONE) {
        navComponentRouterFactory.create(R.id.fragmentContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState != null) {
            navComponentRouter.onRestoreInstanceState(savedInstanceState)
        }
        navComponentRouter.addDestinationListener {
            updateCartButtonVisibility()
        }
        navComponentRouter.onCreate()
        if (savedInstanceState == null) {
            navComponentRouter.switchToStack(destinationsProvider.provideStartDestinationId())
        }
        with(binding) {
            observeUsername()
            observeCart()
            setupListeners()
        }
        activityRequiredSet.forEach {
            println("AAA $it")
            it.onActivityCreated(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (navComponentRouter.onNavigateUp()) || super.onSupportNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navComponentRouter.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        activityRequiredSet.forEach { it.onActivityStarted() }
    }

    override fun onStop() {
        super.onStop()
        activityRequiredSet.forEach { it.onActivityStopped() }
    }

    override fun onDestroy() {
        super.onDestroy()
        navComponentRouter.onDestroy()
        activityRequiredSet.forEach { it.onActivityDestroyed() }
    }

    override fun requireRouter(): NavComponentRouter {
        return navComponentRouter
    }

    @SuppressLint("SetTextI18n")
    private fun ActivityMainBinding.observeUsername() {
//        viewModel.usernameLiveValue.observe(this@MainActivity) { username ->
//            usernameTextView.isVisible = username != null
//            if (username != null) {
//                usernameTextView.text = "@$username"
//            }
//        }
    }

    private fun ActivityMainBinding.observeCart() {
//        viewModel.cartLiveValue.observe(this@MainActivity) { cartState ->
//            updateCartButtonVisibility()
//            cartCounterTextView.text = cartState.itemsCountDisplayString
//        }
    }

    private fun ActivityMainBinding.setupListeners() {
        cartImageView.setOnClickListener {
            viewModel.launchCart()
        }
    }

    private fun updateCartButtonVisibility() {
//        val showCartIcon = viewModel.cartLiveValue.getValue()?.showCartIcon ?: return
//        val isAlreadyAtCart = navComponentRouter.hasDestinationId(destinationsProvider.provideCartDestinationId())
//        binding.cartIconContainer.isVisible = showCartIcon && !isAlreadyAtCart
    }


}