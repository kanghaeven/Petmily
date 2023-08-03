package com.petmily.presentation.view

import android.os.Bundle
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.petmily.R
import com.petmily.config.BaseActivity
import com.petmily.databinding.ActivityMainBinding
import com.petmily.presentation.view.board.BoardDetailFragment
import com.petmily.presentation.view.board.BoardWriteFragment
import com.petmily.presentation.view.certification.join.JoinFragment
import com.petmily.presentation.view.certification.login.LoginFragment
import com.petmily.presentation.view.certification.password.PasswordFragment
import com.petmily.presentation.view.chat.ChatDetailFragment
import com.petmily.presentation.view.chat.ChatUserListFragment
import com.petmily.presentation.view.curation.CurationDetailFragment
import com.petmily.presentation.view.curation.CurationMainFragment
import com.petmily.presentation.view.gallery.GalleryFragment
import com.petmily.presentation.view.home.HomeFragment
import com.petmily.presentation.view.info.pet.PetInfoFragment
import com.petmily.presentation.view.info.pet.PetInfoInputFragment
import com.petmily.presentation.view.info.user.UserInfoInputFragment
import com.petmily.presentation.view.mypage.MyPageFragment
import com.petmily.presentation.view.notification.NotificationFragment
import com.petmily.presentation.view.search.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(R.id.frame_layout_main, LoginFragment())
        }

        bottomNavigationView = binding.bottomNavigation
//        bottomNavigationView.isEnabled =

        bottomNavigationView.apply {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_page_home -> {
                        changeFragment("home")
                        true
                    }

                    R.id.navigation_page_curation -> {
                        changeFragment("curation")
                        true
                    }

                    R.id.navigation_page_feed_add -> {
                        changeFragment("feed add")
                        true
                    }

                    R.id.navigation_page_chatting -> {
                        changeFragment("chatting")
                        true
                    }

                    R.id.navigation_page_my_page -> {
                        changeFragment("my page")
                        true
                    }

                    else -> false
                }

                true
            }
        }
    }

    fun changeFragment(str: String) {
        when (str) {
            "login" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, LoginFragment())
                }
            }

            "password" -> {
                supportFragmentManager.commit {
                    addToBackStack("login")
                    replace(R.id.frame_layout_main, PasswordFragment())
                }
            }

            "join" -> {
                supportFragmentManager.commit {
                    addToBackStack("login")
                    replace(R.id.frame_layout_main, JoinFragment())
                }
            }

            "home" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, HomeFragment())
                }
            }

            "curation" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, CurationMainFragment())
                }
            }

            "feed add" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, BoardWriteFragment())
                }
            }

            "chatting" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, ChatUserListFragment())
                }
            }

            "my page" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, MyPageFragment())
                }
            }

            "userInfoInput" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, UserInfoInputFragment())
                }
            }

            "petInfoInput" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, PetInfoInputFragment())
                }
            }

            "curation detail" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, CurationDetailFragment())
                }
            }

            "gallery" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, GalleryFragment())
                }
            }
            
            "search" -> {
                supportFragmentManager.commit {
                    addToBackStack("search")
                    replace(R.id.frame_layout_main, SearchFragment())
                }
            }
            
            "chat detail" -> {
                supportFragmentManager.commit {
                    addToBackStack("chat detail")
                    replace(R.id.frame_layout_main, ChatDetailFragment())
                }
            }
            
            "notification" -> {
                supportFragmentManager.commit {
                    addToBackStack("notification")
                    replace(R.id.frame_layout_main, NotificationFragment())
                }
            }

            "petInfo" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, PetInfoFragment())
                }
            }
            
            "board detail" -> {
                supportFragmentManager.commit {
                    addToBackStack("board detail")
                    replace(R.id.frame_layout_main, BoardDetailFragment())
                }
            }
        }
    }
}
