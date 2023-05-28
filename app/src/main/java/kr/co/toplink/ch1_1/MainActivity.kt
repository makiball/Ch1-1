package kr.co.toplink.ch1_1

import android.app.ActivityOptions
import android.app.SharedElementCallback
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import kr.co.toplink.ch1_1.databinding.ActivityMainBinding
import kr.co.toplink.ch1_1.TransitionAdapter

class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Ch1-1 에니메이션 변형 기본"

        val ivPhoto = binding.ivPhoto

        val imageRes = R.drawable.avatar_1_raster
        ivPhoto.setImageResource(imageRes)

        binding.cardView.setOnClickListener {
            val intent = Intent(this, Activity1_1Details::class.java)
            intent.putExtra("imageRes", imageRes)

            val options = ActivityOptions
                .makeSceneTransitionAnimation(
                    this,
                    ivPhoto,
                    ViewCompat.getTransitionName(ivPhoto)
                )

            startActivity(intent, options.toBundle())
        }

        // 변형 데이터를 받기
        addTransitionListeners()
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
                    println("🔥 $thisActivity: sharedElementExitTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementEnterTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🎃 $thisActivity: sharedElementEnterTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementReenterTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🍏 $thisActivity: sharedElementReenterTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementReturnTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("👻 $thisActivity: sharedElementReturnTransition onTransitionStart()")
                }
            }
        )

        setEnterSharedElementCallback(object: SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {

                super.onMapSharedElements(names, sharedElements)

                println(
                    "🌽 $thisActivity: setExitSharedElementCallback() " +
                            "names:$names, sharedElements: $sharedElements"
                )

                Toast.makeText(
                    applicationContext,
                    "🌽 $thisActivity: setExitSharedElementCallback() " +
                            "names:$names, sharedElements: $sharedElements",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
