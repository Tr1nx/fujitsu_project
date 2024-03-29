package com.example.fujitsuproject.delivery;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController {

    private final DeliveryFeeCalculationService deliveryFeeCalculationService;

    public DeliveryController(DeliveryFeeCalculationService deliveryFeeCalculationService) {
        this.deliveryFeeCalculationService = deliveryFeeCalculationService;
    }

    /**
     * Endpoint for calculating the delivery fee based on the specified city and vehicle type.
     *
     * @param city        The city for which the delivery fee is calculated.
     * @param vehicleType The type of vehicle used for delivery.
     * @return The calculated delivery fee.
     */
    @GetMapping("/delivery")
    public double calculateDeliveryFee(@RequestParam String city, @RequestParam String vehicleType) {
        return deliveryFeeCalculationService.calculateDeliveryFee(city, vehicleType);
    }
}
