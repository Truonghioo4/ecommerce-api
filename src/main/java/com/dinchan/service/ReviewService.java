package com.dinchan.service;

import com.dinchan.model.Product;
import com.dinchan.model.Review;
import com.dinchan.model.User;
import com.dinchan.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(
            CreateReviewRequest req,
            User user,
            Product product
    );

    List<Review> getReviewByProductId(Long productId);

    Review updateReview(
            Long reviewId,
            String reviewText,
            double rating,
            Long userId
    ) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;
}
