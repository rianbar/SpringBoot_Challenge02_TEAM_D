package com.compassuol.sp.challenge.msfeedback.service;

import com.compassuol.sp.challenge.msfeedback.dto.FeedbackRequestDTO;
import com.compassuol.sp.challenge.msfeedback.dto.FeedbackResponseDTO;
import com.compassuol.sp.challenge.msfeedback.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msfeedback.errors.BusinessErrorException;
import com.compassuol.sp.challenge.msfeedback.errors.FeedbackNotFoundException;
import com.compassuol.sp.challenge.msfeedback.model.FeedbackModel;
import com.compassuol.sp.challenge.msfeedback.proxy.OrdersProxy;
import com.compassuol.sp.challenge.msfeedback.repository.FeedbackRepository;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final OrdersProxy proxy;
    private final TransferObjects transferObjects;

    public List<FeedbackModel> getAllFeedbacksService() {
        try {
            return feedbackRepository.findAll();
        } catch (Exception e) {
            throw new FeedbackNotFoundException("Feedbacks not found");
        }
    }

    public FeedbackResponseDTO getFeedbackById(long feedbackId) {
        FeedbackModel feedback = feedbackRepository.findById(feedbackId).orElse(null);

        if (feedback != null) {
            return transferObjects.parseToDTO(feedback);
        } else {
            throw new FeedbackNotFoundException("Feedback not found.");
        }
    }

    public FeedbackResponseDTO createFeedbackService(FeedbackRequestDTO request) {
        try {
            OrderResponseDTO feedbackResponse = proxy.getOrderById(request.getOrderId());
            if (feedbackResponse.getStatus().equalsIgnoreCase("canceled"))
                throw new BusinessErrorException("cannot create feedbacks for canceled orders");
        } catch (FeignException ex) {
            throw new FeedbackNotFoundException("order_id not found in database");
        }
        FeedbackModel model = feedbackRepository.save(transferObjects.parseToModel(request));
        return transferObjects.parseToDTO(model);
    }

    public FeedbackResponseDTO updateFeedbackService(Long id, FeedbackRequestDTO dto) {
        FeedbackModel model = feedbackRepository.findById(id)
                .orElseThrow(() -> new FeedbackNotFoundException("feedback not found"));
        try {
            proxy.getOrderById(dto.getOrderId());
        } catch (FeignException ex) {
            throw new FeedbackNotFoundException("order_id doesn't exists");
        }

        FeedbackModel response = feedbackRepository.save(transferObjects.updateFeedbackModel(model, dto));
        return transferObjects.parseToDTO(response);
    }

    public void deleteFeedbackService(Long id) {

        if (feedbackRepository.findById(id).isPresent()) {
            feedbackRepository.deleteById(id);
        } else {
            throw new FeedbackNotFoundException("feedback not found");
        }
    }
}
