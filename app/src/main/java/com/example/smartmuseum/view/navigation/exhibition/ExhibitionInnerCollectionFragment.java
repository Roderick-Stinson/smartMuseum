package com.example.smartmuseum.view.navigation.exhibition;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.smartmuseum.R;
import com.example.smartmuseum.databinding.ExhibitionInnerCollectorPopwindowViewBinding;
import com.example.smartmuseum.databinding.FragmentExhibitionInnerCollectionBinding;
import com.example.smartmuseum.handler.ViewChainedBinding;
import com.example.smartmuseum.util.LazyLocationFragment;
import com.example.smartmuseum.view.GlobalVariables;
import com.example.smartmuseum.view.otherview.NoScrollViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExhibitionInnerCollectionFragment extends LazyLocationFragment implements ViewChainedBinding {

    private FragmentExhibitionInnerCollectionBinding mBinding;
    private ImageView iv;
    private Matrix normalMatrix;
    private float imgX,imgY;
    private PopupWindow popupWindow;
    private int seekBarProgress;

    public ExhibitionInnerCollectionFragment() {
        // Required empty public constructor
    }

    public static ExhibitionInnerCollectionFragment getInstance(){
        return new ExhibitionInnerCollectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_exhibition_inner_collection,
                container,
                false);
        this.bindData().bindView().bindEvent();
        return mBinding.getRoot();
    }

    @Override
    public ExhibitionInnerCollectionFragment bindView() {
        // ??????seekBar???????????????
        mBinding.exhibitionInnerCollectionSeekbar.setEnabled(false);
        mBinding.exhibitionInnerCollectionSeekbar.setClickable(false);
        mBinding.exhibitionInnerCollectionSeekbar.setFocusable(false);
        return this;
    }

    @Override
    public ExhibitionInnerCollectionFragment bindData() {
        // ???????????????????????????????????????
        normalMatrix = mBinding.exhibitionInnerCollectionMap.getImageMatrix();

        // ??????seekBar????????????
        seekBarProgress = 3;
        return this;
    }

    // ?????????????????????????????????????????????????????????????????????????????????????????????
    @Override
    public ExhibitionInnerCollectionFragment bindEvent() {

        // ????????????
        mBinding.exhibitionInnerCollectionExpandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBarProgress += 3;
                if(seekBarProgress > 6)
                    return;
                mBinding.exhibitionInnerCollectionSeekbar.setProgress(seekBarProgress);
                // ???????????????
                Matrix matrix = normalMatrix;
                matrix.postScale((float)1.6,(float)1.6);   // ????????????1.6?????????????????????????????????????????????
                mBinding.exhibitionInnerCollectionMap.setImageMatrix(matrix);
            }
        });


        // ????????????
        mBinding.exhibitionInnerCollectionSmallImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBarProgress -= 3;
                if(seekBarProgress <= 0){
                    View parent = mBinding.getRoot().getRootView();
                    NoScrollViewPager noScrollViewPager = (NoScrollViewPager)parent.findViewById(R.id.mainpage_navigation_sv);
                    noScrollViewPager.setCurrentItem(1,false);
                }else {
                    mBinding.exhibitionInnerCollectionSeekbar.setProgress(seekBarProgress);
                    // ????????????
                    Matrix matrix = normalMatrix;
                    Matrix changeMatrix = new Matrix();
                    changeMatrix.postScale((float)0.625,(float)0.625); // ??????????????????????????????
                    matrix.postScale((float)0.625,(float)0.625);  // ???????????????????????????
                    mBinding.exhibitionInnerCollectionMap.setImageMatrix(matrix);
                }
            }
        });
        return this;
    }

    @Override
    protected void onSubscribe() {
    }

    @Override
    protected void cancelSubscribe() {
    }
}
