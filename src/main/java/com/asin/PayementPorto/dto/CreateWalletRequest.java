package com.asin.PayementPorto.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateWalletRequest(
        @NotBlank(message = "Nomor Akun wajib diisi")
        String accountNumber,
        @NotBlank(message ="Id User harus diisi")
        String userId
) {
}
