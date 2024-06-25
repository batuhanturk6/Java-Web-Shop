package com.turkproject.controller;


import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.turkproject.global.GlobalData;
import com.turkproject.service.PaypalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Random;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

    private final PaypalService paypalService;

    @PostMapping("/payment/create")
    public RedirectView createPayment() {
        try {
            String cancelUrl = "http://localhost:8080/payment/cancel";
            String successUrl = "http://localhost:8080/payment/success";

            Payment payment = paypalService.createPayment(
                    GlobalData.cart.stream()
                            .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                            .sum(),
                    "EUR",
                    "paypal",
                    "sale",
                    "Payment test",
                    cancelUrl,
                    successUrl
            );

            for(Links links: payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
            System.out.println("apres boucle");
        } catch (PayPalRESTException e) {
            log.error("Error: ", e);
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId,
            Model model
    ) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);

            if(payment.getState().equals("approved")) {
                return "redirect:/ordered/PAYPAL";
            }
        } catch (PayPalRESTException e) {
            log.error("Error: ", e);
        }

        return "redirect:/ordered/PAYPAL";
    }

    private String generateNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(7);

        for (int i = 0; i < 7; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }
}
