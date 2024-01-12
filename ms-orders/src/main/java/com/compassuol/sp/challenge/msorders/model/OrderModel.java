package com.compassuol.sp.challenge.msorders.model;

import com.compassuol.sp.challenge.msorders.constant.PaymentTypeEnum;
import com.compassuol.sp.challenge.msorders.constant.StatusOrderEnum;
import com.compassuol.sp.challenge.msorders.service.OrderDataConstraints;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "order_tb")
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "order_products_tb", joinColumns = @JoinColumn(name = "principal_class_id"))
    private List<OrderProductsModel> products;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "foreign_address_id")
    private AddressModel address;
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentMethod;
    private Double subtotalValue;
    private Double discount;
    private Double totalValue;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Enumerated(EnumType.STRING)
    private StatusOrderEnum status;
    private String cancelReason;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime cancelDate;

    public OrderModel(List<OrderProductsModel> products, AddressModel address,
                      PaymentTypeEnum paymentMethod, Double subtotalValue,
                      StatusOrderEnum status, String cancelReason) throws ParseException {
        Map<String, Double> checkPromo = new OrderDataConstraints().checkPromotion(paymentMethod, subtotalValue);
        this.products = products;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.subtotalValue = new OrderDataConstraints().formatDoubles(subtotalValue);
        this.status = status;
        this.cancelReason = cancelReason;
        this.createDate = LocalDateTime.now();
        this.discount = checkPromo.get("discount");
        this.totalValue = checkPromo.get("total_value");

        if (!this.cancelReason.isEmpty()) {
            this.cancelDate = LocalDateTime.now();
            this.status = StatusOrderEnum.CANCELED;
        }
    }
}
