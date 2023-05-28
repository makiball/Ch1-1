package kr.co.toplink.ch1_1

import android.app.SharedElementCallback
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kr.co.toplink.ch1_1.databinding.Activity11detailsBinding

class Activity1_1Details : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: Activity11detailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity11detailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "변형 에니메에션 상세보기"


        val ivPhoto = binding.ivPhoto

        addTransitionListeners()
        postponeEnterTransition()

        val imageRes = intent.getIntExtra("imageRes", -1)
        if (imageRes != -1) {

            Glide
                .with(this)
                .load(imageRes)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(
                            applicationContext,
                            "Glide onLoadFailed()",
                            Toast.LENGTH_SHORT
                        ).show()

                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(
                            applicationContext,
                            "Glide onResourceReady()",
                            Toast.LENGTH_SHORT
                        ).show()
                        startPostponedEnterTransition()
                        return false
                    }

                })
                .into(ivPhoto)
        }

        val fade: Transition = Fade()
        val decor = window.decorView

        val view = decor.findViewById<View>(androidx.appcompat.R.id.action_bar_container)
        fade.excludeTarget(view, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = fade
        window.exitTransition = fade
    }

    private fun addTransitionListeners() {
        val thisActivity = this::class.java.simpleName

        window.sharedElementExitTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.move)
        window.sharedElementEnterTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.move)
        window.sharedElementReenterTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.move)
        window.sharedElementReturnTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.move)

        window.sharedElementExitTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🚀 $thisActivity: sharedElementExitTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementEnterTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🚌 $thisActivity: sharedElementEnterTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementReenterTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🚙 $thisActivity: sharedElementReenterTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementReturnTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🚕 $thisActivity: sharedElementReturnTransition onTransitionStart()")
                }
            }
        )

        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)

                println(
                    "🍒 $thisActivity: setEnterSharedElementCallback() " +
                            "names:$names, sharedElements: $sharedElements"
                )

                Toast.makeText(
                    applicationContext,
                    "🍒 $thisActivity: setEnterSharedElementCallback() " +
                            "names:$names, sharedElements: $sharedElements",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        setExitSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)

                Toast.makeText(
                    applicationContext,
                    "🤔 Activity1_1DetailActivity setExitSharedElementCallback() " +
                            "names:$names, sharedElements: $sharedElements",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onBackPressed() {
        finishAfterTransition()
        super.onBackPressed()
    }
}