package com.compassuol.sp.challenge.msorders.service;

import com.compassuol.sp.challenge.msorders.constant.PaymentTypeEnum;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class OrderDataConstraints {

    public double formatDoubles(double value) throws ParseException {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String stringDouble = decimalFormat.format(value);
        return decimalFormat.parse(stringDouble).doubleValue();
    }

    public Map<String, Double> checkPromotion(PaymentTypeEnum paymentType, double subtotal)
            throws ParseException {
        double percentage;
        double discount = 0.0;
        double totalValue;

        if (paymentType == PaymentTypeEnum.PIX) {
            discount = 0.05;
            percentage = subtotal*discount;
            totalValue = subtotal - percentage;
        } else {
            totalValue = subtotal;
        }

        double formattedTotalValue = this.formatDoubles(totalValue);
        HashMap<String, Double> valuesPerPromotion = new HashMap<>();
        valuesPerPromotion.put("discount", discount);
        valuesPerPromotion.put("total_value", formattedTotalValue);

        return valuesPerPromotion;
    }
}
