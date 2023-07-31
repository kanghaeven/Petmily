package com.petmily.presentation.view.home

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.CompoundButton
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentHomeBinding
import com.petmily.databinding.ItemBoardBinding
import com.petmily.databinding.ItemHomeCurationBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.repository.dto.Board
import com.petmily.repository.dto.Curation
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

private const val TAG = "Fetmily_HomeFragment"
private const val CURATION_JOB_DELAY = 3000L
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var mainActivity: MainActivity

    private lateinit var homeCurationAdapter: HomeCurationAdapter
    private lateinit var boardAdapter: BoardAdapter

    // curation ViewPager 자동 스크롤 job
    private lateinit var curationJob: Job
    
    var scaleAnimation: ScaleAnimation? = null
    var bounceInterpolator: BounceInterpolator? = null

    // 큐레이션 데이터 TODO: api 통신 후 적용되는 실제 데이터로 변경
    private val curations =
        listOf(
            Curation(curationTitle = "title1"),
            Curation(curationTitle = "title2"),
            Curation(curationTitle = "title3"),
            Curation(curationTitle = "title4"),
            Curation(curationTitle = "title5"),
        )

    // 피드 게시물 데이터 TODO: api 통신 후 적용되는 실제 데이터로 변경
    private val boards =
        listOf(
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCurations()
        initBoards()
        initViewPager()
    }

    override fun onResume() {
        super.onResume()
        curationJobCreate()
    }

    override fun onPause() {
        super.onPause()
        curationJob.cancel()
    }

    private fun initAdapter() = with(binding) {
        scaleAnimation = ScaleAnimation(
            0.7f,
            1.0f,
            0.7f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
        )
        
        scaleAnimation?.setDuration(500)
        bounceInterpolator = BounceInterpolator()
        scaleAnimation?.setInterpolator(bounceInterpolator)
        
        // 클릭 이벤트 처리
        homeCurationAdapter = HomeCurationAdapter().apply {
            setCurationClickListener(object : HomeCurationAdapter.CurationClickListener {
                override fun curationClick(
                    binding: ItemHomeCurationBinding,
                    curation: Curation,
                    position: Int,
                ) {
                    // TODO: 클릭 이벤트 처리, 큐레이션 클릭 시 해당 큐레이션 화면으로 이동
                }
            })
        }
        boardAdapter = BoardAdapter().apply {
            setBoardClickListener(object : BoardAdapter.BoardClickListener {
                override fun likeClick(compoundButton: CompoundButton, binding: ItemBoardBinding, board: Board, position: Int) {
                    compoundButton.startAnimation(scaleAnimation)
                }

                override fun commentClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    // TODO: 클릭 이벤트 처리, 댓글 버튼 클릭 시 BottomSheetDialog 띄우기
                }

                override fun bookmarkClick(compoundButton: CompoundButton, binding: ItemBoardBinding, board: Board, position: Int) {
                    compoundButton.startAnimation(scaleAnimation)
                }

                override fun profileClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    // TODO: 클릭 이벤트 처리, 프로필 이미지 혹은 이름 클릭 시 해당 유저 프로필로 이동
                }
            })
        }

        vpCuration.adapter = homeCurationAdapter
        rcvBoard.apply {
            adapter = boardAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    // 큐레이션 데이터 초기화 TODO: api 통신 코드로 변경
    private fun initCurations() {
        homeCurationAdapter.setCurations(curations)
    }

    // 피드 게시물 데이터 초기화 TODO: api 통신 코드로 변경
    private fun initBoards() {
        boardAdapter.setBoards(boards)
    }

    // ViewPager 초기 설정
    private fun initViewPager() = with(binding) {
        // 무한 스크롤을 위해 HomeCurationAdapter 내 리스트의 맨앞, 맨뒤에 반대 방향 값 추가하여 실제 데이터는 1부터 시작
        vpCuration.setCurrentItem(1, false)
        vpCuration.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                ciCuration.animatePageSelected((vpCuration.currentItem + curations.size - 1) % curations.size)
                when (vpCuration.currentItem) {
                    homeCurationAdapter.itemCount - 1 -> vpCuration.setCurrentItem(1, false)
                    0 -> vpCuration.setCurrentItem(homeCurationAdapter.itemCount - 2, false)
                }

                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        if (!curationJob.isActive) curationJobCreate()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> curationJob.cancel()
                }
            }
        })

        // ViewPager 하단 위치 표시 점
        ciCuration.createIndicators(curations.size, 0)
    }

    // 일정 시간마다 자동으로 큐레이션 이동
    private fun curationJobCreate() {
        curationJob = lifecycleScope.launchWhenResumed {
            delay(CURATION_JOB_DELAY)
            binding.vpCuration.setCurrentItem(binding.vpCuration.currentItem % curations.size + 1, true)
        }
    }
}
