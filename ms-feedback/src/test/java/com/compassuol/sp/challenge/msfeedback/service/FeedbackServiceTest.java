package com.compassuol.sp.challenge.msfeedback.service;

import com.compassuol.sp.challenge.msfeedback.dto.FeedbackRequestDTO;
import com.compassuol.sp.challenge.msfeedback.dto.FeedbackResponseDTO;
import com.compassuol.sp.challenge.msfeedback.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msfeedback.dto.ScaleEnum;
import com.compassuol.sp.challenge.msfeedback.errors.BusinessErrorException;
import com.compassuol.sp.challenge.msfeedback.errors.FeedbackNotFoundException;
import com.compassuol.sp.challenge.msfeedback.model.FeedbackModel;
import com.compassuol.sp.challenge.msfeedback.proxy.OrdersProxy;
import com.compassuol.sp.challenge.msfeedback.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    public static FeedbackRequestDTO feedbackRequestDTO;
    public static OrderResponseDTO orderResponseDTO;
    public static FeedbackModel feedbackModel;
    public static FeedbackResponseDTO feedbackResponseDTO;

    @InjectMocks
    FeedbackService feedbackService;

    @Mock
    FeedbackRepository feedbackRepository;

    @Mock
    OrdersProxy proxy;

    @Mock
    TransferObjects transferObjects;

    @BeforeEach
    void setUp() {
        feedbackRequestDTO = new FeedbackRequestDTO();
        feedbackRequestDTO.setComment("test");
        feedbackRequestDTO.setOrderId(1L);
        feedbackRequestDTO.setScale(ScaleEnum.SATISFIED);

        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setStatus("CONFIRMED");

        feedbackModel = new FeedbackModel();
        feedbackModel.setScale(ScaleEnum.SATISFIED);
        feedbackModel.setOrderId(1L);
        feedbackModel.setComment("comment");
        feedbackModel.setId(12L);

        feedbackResponseDTO = new FeedbackResponseDTO();
        feedbackResponseDTO.setId(1L);
        feedbackResponseDTO.setOrderId(1L);
        feedbackResponseDTO.setComment("very good my boy");
        feedbackResponseDTO.setScale(ScaleEnum.SATISFIED);
    }

    @Test
    void updateFeedbacks_withValidData_ReturnsFeedback() {
        var feedbackModelCaptor = ArgumentCaptor.forClass(FeedbackModel.class);
        when(transferObjects.parseToDTO(feedbackModelCaptor.capture())).thenReturn(feedbackResponseDTO);
        when(feedbackRepository.findById(anyLong())).thenReturn(Optional.of(feedbackModel));
        when(proxy.getOrderById(anyLong())).thenReturn(orderResponseDTO);
        when(feedbackRepository.save(feedbackModelCaptor.capture())).thenReturn(feedbackModel);

        FeedbackResponseDTO response = feedbackService.updateFeedbackService(1L, feedbackRequestDTO);
        assertNotNull(response);
    }

    @Test
    void updateFeedbacks_withInValidId_ReturnsException() {
        when(feedbackRepository.findById(1L)).thenThrow(FeedbackNotFoundException.class);
        assertThatThrownBy(() -> feedbackService.updateFeedbackService(1L, feedbackRequestDTO))
                .isInstanceOf(FeedbackNotFoundException.class);
    }

    @Test
    void updateFeedbacks_withInValidOrderId_ReturnsException() {
        FeedbackRequestDTO request = feedbackRequestDTO;
        request.setOrderId(3L);
        when(feedbackRepository.findById(anyLong())).thenReturn(Optional.of(feedbackModel));
        when(proxy.getOrderById(3L)).thenThrow(FeedbackNotFoundException.class);

        assertThatThrownBy(() -> feedbackService.updateFeedbackService(1L, request))
                .isInstanceOf(FeedbackNotFoundException.class);
    }

    @Test
    void CreateFeedback_withValidData_ReturnsObject() {
        when(proxy.getOrderById(anyLong())).thenReturn(orderResponseDTO);
        when(feedbackRepository.save(any())).thenReturn(feedbackModel);
        when(transferObjects.parseToDTO(any())).thenReturn(feedbackResponseDTO);

        FeedbackResponseDTO response = feedbackService.createFeedbackService(feedbackRequestDTO);

        assertNotNull(response);
    }

    @Test
    void CreateFeedback_withInvalidData_ReturnsException() {
        var canceledFeedback = orderResponseDTO;
        canceledFeedback.setStatus("canceled");
        when(proxy.getOrderById(anyLong())).thenReturn(canceledFeedback);

        assertThatThrownBy(() -> feedbackService.createFeedbackService(feedbackRequestDTO))
                .isInstanceOf(BusinessErrorException.class);
    }
}
