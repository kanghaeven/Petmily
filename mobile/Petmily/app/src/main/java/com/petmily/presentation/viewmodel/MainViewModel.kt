package com.petmily.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petmily.repository.dto.Photo

class MainViewModel : ViewModel() {

    private var fromGalleryFragment: String // GalleryFragment를 호출한 Fragment를 기록
    private var selectProfileImage: String // 갤러리에서 선택한 사진 한장

    private val _galleryList = MutableLiveData<MutableList<Photo>>()
    val galleryList: LiveData<MutableList<Photo>> get() = _galleryList

    private val _addPhotoList = MutableLiveData<MutableList<Photo>>()
    val addPhotoList: LiveData<MutableList<Photo>> get() = _addPhotoList
    
    private val _connectException = MutableLiveData<Boolean>()
    val connectException: LiveData<Boolean> get() = _connectException

    // GalleryFragment에서 선택된 사진 add
    fun addToAddPhotoList(photo: Photo) {
        _addPhotoList.value?.add(photo)
        _addPhotoList.value = _addPhotoList.value
    }

    // GalleryFragment에서 선택된 사진 초기화
    fun clearAddPhotoList() {
        _addPhotoList.value?.clear()
        _addPhotoList.value = _addPhotoList.value
    }

    // 핸드폰 갤러리에서 불러온 사진을 초기화
    fun emptyGalleryList() {
        _galleryList.value!!.clear()
    }

    // 핸드폰 갤러리에서 불러온 사진을 add
    fun addToGalleryList(photo: Photo) {
        _galleryList.value?.add(photo)
        _galleryList.value = _galleryList.value
    }

    fun getSelectProfileImage(): String {
        return selectProfileImage
    }
    fun setSelectProfileImage(selectProfileImage: String) {
        this.selectProfileImage = selectProfileImage
    }

    fun getFromGalleryFragment(): String {
        return fromGalleryFragment
    }
    fun setFromGalleryFragment(fragmentName: String) {
        fromGalleryFragment = fragmentName
    }
    
    fun setConnectException() {
        _connectException.value = true
    }

    init {
        _galleryList.value = mutableListOf()
        _addPhotoList.value = mutableListOf()
        fromGalleryFragment = ""
        selectProfileImage = ""
    }
}