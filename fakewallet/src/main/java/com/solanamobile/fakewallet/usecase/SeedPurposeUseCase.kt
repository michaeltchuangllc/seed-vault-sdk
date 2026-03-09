/*
 * Copyright (c) 2022 Solana Mobile Inc.
 */

package com.solanamobile.fakewallet.usecase

import com.solanamobile.seedvault.WalletContractV1

object SeedPurposeUseCase {
    operator fun invoke(@WalletContractV1.Purpose purpose: Int): String {
        return when (purpose) {
            WalletContractV1.PURPOSE_SIGN_SOLANA_TRANSACTION -> "Sign Solana Transactions"
            WalletContractV1.PURPOSE_SIGN_ALGORAND_TRANSACTION -> "Sign Algorand Transactions"
            WalletContractV1.PURPOSE_SIGN_BITCOIN_TRANSACTION -> "Sign Bitcoin Transactions"
            WalletContractV1.PURPOSE_SIGN_ETHEREUM_TRANSACTION -> "Sign Ethereum Transactions"
            else -> "Unknown"
        }
    }
}