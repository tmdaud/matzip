package com.smchoi.matzip.services;

import com.smchoi.matzip.entities.data.ReviewEntity;
import com.smchoi.matzip.entities.data.ReviewImageEntity;
import com.smchoi.matzip.entities.member.UserEntity;
import com.smchoi.matzip.enums.data.AddReviewResult;
import com.smchoi.matzip.exceptions.RollbackException;
import com.smchoi.matzip.interfaces.IResult;
import com.smchoi.matzip.mappers.IDataMapper;
import com.smchoi.matzip.vos.PlaceVo;
import com.smchoi.matzip.vos.ReviewVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service(value = "com.smchoi.matzip.services.DataService")
public class DataService {
    private final IDataMapper dataMapper;

    public DataService(IDataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }

    public PlaceVo[] getPlacesVo(double minLat, double minLng, double maxLat, double maxLng) {
        return this.dataMapper.selectPlacesVoExceptImage(minLat, minLng, maxLat, maxLng);
    }

    public PlaceVo getPlace(int index) {
        return this.dataMapper.selectPlaceVoByIndex(index);
    }

    @Transactional
    public Enum<? extends IResult> addReview(UserEntity user, ReviewEntity review, MultipartFile[] images) throws IOException, RollbackException {
        if (user == null) {
            return AddReviewResult.NOT_SIGNED;
        }
        review.setUserId(user.getId());
        if (this.dataMapper.insertReview(review) == 0) {
            return AddReviewResult.FAILURE;
        }
        if (images != null && images.length > 0) {
            for (MultipartFile image : images) {
                ReviewImageEntity reviewImage = new ReviewImageEntity();
                reviewImage.setReviewIndex(review.getIndex());
                reviewImage.setData(image.getBytes());
                reviewImage.setType(image.getContentType());
                if (this.dataMapper.insertReviewImage(reviewImage) == 0) {
                    throw new RollbackException();
                }
            }
        }
        return AddReviewResult.SUCCESS;
    }

    public ReviewVo[] getReviews(int placeIndex) {
        ReviewVo[] reviews = this.dataMapper.selectReviewsByPlaceIndex(placeIndex);
        for(ReviewVo review : reviews) {
            ReviewImageEntity[] reviewImages = this.dataMapper.selectReviewsImagesByReviewIndexExceptData(review.getIndex());
            int[] reviewImageIndexes = Arrays.stream(reviewImages).mapToInt(ReviewImageEntity::getIndex).toArray();
//            int[] reviewImageIndexes = new int[reviewImages.length];
//            for(int i = 0; i < reviewImages.length; i++) {
//                reviewImageIndexes[i] = reviewImages[i].getIndex();
//            }
            review.setImageIndexes(reviewImageIndexes);
        }
        return reviews;
    }

    public ReviewImageEntity getReviewImage(int index) {
        return this.dataMapper.selectReviewImageByIndex(index);
    }

//    public Enum<? extends IResult> deleteReview(int reviewIndex) {
//
//        return this.dataMapper.deleteReview(reviewIndex) > 0
//                ? CommonResult.SUCCESS
//                : CommonResult.FAILURE;
//    }

    public Enum<? extends IResult> deleteReview(Integer reviewIndex) {
        ReviewVo existingReview = this.dataMapper.selectReviewsByReviewIndex(reviewIndex);
        if (existingReview == null) {
            return AddReviewResult.FAILURE;
        }
        System.out.println(reviewIndex);

        return this.dataMapper.deleteReview(reviewIndex) > 0
                ? AddReviewResult.SUCCESS
                : AddReviewResult.FAILURE;
    }
}
