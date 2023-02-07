package com.smchoi.matzip.mappers;

import com.smchoi.matzip.entities.data.ReviewEntity;
import com.smchoi.matzip.entities.data.ReviewImageEntity;
import com.smchoi.matzip.vos.PlaceVo;
import com.smchoi.matzip.vos.ReviewVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IDataMapper {

    PlaceVo[] selectPlacesVoExceptImage(@Param(value = "minLat") double minLat,
                                        @Param(value = "minLng") double minLng,
                                        @Param(value = "maxLat") double maxLat,
                                        @Param(value = "maxLng") double maxLng);

    PlaceVo selectPlaceVoByIndex(@Param(value = "index") int index);

    int insertReview(ReviewEntity review);

    int insertReviewImage(ReviewImageEntity reviewImage);

    ReviewVo[] selectReviewsByPlaceIndex(@Param(value = "placeIndex") int placeIndex);

    ReviewImageEntity[] selectReviewsImagesByReviewIndexExceptData(@Param(value = "reviewIndex") int reviewIndex);

    ReviewImageEntity selectReviewImageByIndex(@Param(value = "index") int index);

    int deleteReview(@Param(value = "index") int index);

    ReviewVo selectReviewsByReviewIndex(@Param(value = "index") int index);
}
