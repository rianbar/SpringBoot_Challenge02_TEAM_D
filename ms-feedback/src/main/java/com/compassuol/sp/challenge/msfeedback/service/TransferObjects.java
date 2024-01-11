package com.compassuol.sp.challenge.msfeedback.service;

import com.compassuol.sp.challenge.msfeedback.dto.FeedbackRequestDTO;
import com.compassuol.sp.challenge.msfeedback.dto.FeedbackResponseDTO;
import com.compassuol.sp.challenge.msfeedback.model.FeedbackModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferObjects {

    public FeedbackResponseDTO parseToDTO(FeedbackModel model) {
        return new FeedbackResponseDTO(model.getId(), model.getScale(), model.getComment(), model.getOrderId());
    }

    public FeedbackModel parseToModel(FeedbackRequestDTO request) {
        return new FeedbackModel(request.getScale(), request.getComment(),
                request.getOrderId());
    }

    public FeedbackModel updateFeedbackModel(FeedbackModel model, FeedbackRequestDTO dto) {
        model.setOrderId(dto.getOrderId());
        model.setScale(dto.getScale());
        model.setComment(dto.getComment());
        return model;
    }
}
