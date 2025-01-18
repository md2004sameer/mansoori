package com.mansoori.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mansoori.Service.QRCodeService;

@RestController
@RequestMapping("/qr")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateQRCode(@RequestBody QRCodeRequest qrCodeRequest) {
        byte[] qrCode = qrCodeService.generateQRCode(qrCodeRequest.getText());

        // Set headers for image download
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/png");
        headers.add("Content-Disposition", "attachment; filename=qrcode.png");

        return ResponseEntity.ok()
                .headers(headers)
                .body(qrCode);
    }
}

class QRCodeRequest {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}