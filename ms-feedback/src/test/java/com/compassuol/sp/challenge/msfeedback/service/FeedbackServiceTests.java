package com.compassuol.sp.challenge.msfeedback.service;

import com.compassuol.sp.challenge.msfeedback.controller.exception.ResponseErrorTemplate;
import com.compassuol.sp.challenge.msfeedback.controller.exception.errorTypes.BusinessErrorException;
import com.compassuol.sp.challenge.msfeedback.controller.exception.errorTypes.ProductNotFoundException;
import com.compassuol.sp.challenge.msfeedback.dto.FeedbackRequestDTO;
import com.compassuol.sp.challenge.msfeedback.dto.OrderResponseDTO;
import com.compassuol.sp.challenge.msfeedback.dto.ScaleEnum;
import com.compassuol.sp.challenge.msfeedback.model.FeedbackModel;
import com.compassuol.sp.challenge.msfeedback.proxy.OrdersProxy;
import com.compassuol.sp.challenge.msfeedback.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTests {

    @InjectMocks
    FeedbackService feedbackService;

    @Mock
    FeedbackRepository feedbackRepository;

    @Mock
    OrdersProxy proxy;

    @Test
    public void CreateFeedback_withValidData_returnsFeedback() {
        var feedbackRequestDTO = new FeedbackRequestDTO();
        feedbackRequestDTO.setComment("test");
        feedbackRequestDTO.setOrder_id(1L);
        feedbackRequestDTO.setScale(ScaleEnum.SATISFIED);

        var orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setStatus("CONFIRMED");

        var feedbackModel = new FeedbackModel();
        feedbackModel.setScale(ScaleEnum.SATISFIED);
        feedbackModel.setOrder_id(1L);
        feedbackModel.setComment("comment");
        feedbackModel.setId(12L);



        var feedbackModelCaptor = ArgumentCaptor.forClass(FeedbackModel.class);

        when(proxy.getOrderById(anyLong())).thenReturn(orderResponseDTO);
        when(feedbackRepository.save(feedbackModelCaptor.capture()))
                .thenReturn(feedbackModel);

        FeedbackModel feedbackResponse = feedbackService.createFeedbackService(feedbackRequestDTO);
        assertNotNull(feedbackResponse);
    }

    @Test
    public void CreateFeedback_withInvalidId_returnsException() {
        var feedbackRequestDTO = new FeedbackRequestDTO();
        feedbackRequestDTO.setComment("test");
        feedbackRequestDTO.setOrder_id(1L);
        feedbackRequestDTO.setScale(ScaleEnum.SATISFIED);

        when(proxy.getOrderById(1L)).thenThrow(ProductNotFoundException.class);

        assertThatThrownBy(() -> feedbackService.createFeedbackService(feedbackRequestDTO))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    public void CreateFeedback_withInvalidStatus_returnsException() {
        var feedbackRequestDTO = new FeedbackRequestDTO();
        feedbackRequestDTO.setComment("test");
        feedbackRequestDTO.setOrder_id(1L);
        feedbackRequestDTO.setScale(ScaleEnum.SATISFIED);

        var orderResponseDTO = new OrderResponseDTO();
        when(proxy.getOrderById(anyLong())).thenReturn(orderResponseDTO);
        orderResponseDTO.setStatus("CANCELED");

        assertThatThrownBy(() -> feedbackService.createFeedbackService(feedbackRequestDTO))
                .isInstanceOf(BusinessErrorException.class);
    }
    @Test
    void testGetFeedbackByIdFound() {
        int feedbackId = 1;
        FeedbackModel feedbackModel = new FeedbackModel();
        feedbackModel.setId((long) feedbackId);
        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.of(feedbackModel));

        ResponseEntity<Object> response = feedbackService.getFeedbackById(feedbackId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(feedbackModel, response.getBody());
    }
    @Test
    void testGetFeedbackByIdNotFound() {
        int feedbackId = 1;
        when(feedbackRepository.findById(feedbackId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = feedbackService.getFeedbackById(feedbackId);

        assertEquals(404, response.getStatusCodeValue());
        ResponseErrorTemplate errorResponse = (ResponseErrorTemplate) response.getBody();
        assertEquals("Feedback com o ID 1 não encontrado.", errorResponse.getMessage());
    }
}
